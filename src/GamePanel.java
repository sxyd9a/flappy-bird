import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360, boardHeight = 640;

    //Images
    Image backgroundImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Game objects
    Bird bird;
    ArrayList<Pipe> pipes;

    //game logic
    int velocityX = -4; //rate at which pipes move left
    int velocityY = 0; //rate at which bird moves up
    int gravity = 1;

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;

    Random random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        //send key event to the JPanel and check key functions     
        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("/images/flappybirdbg.png")).getImage();
        Image birdImg = new ImageIcon(getClass().getResource("/images/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/images/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/images/bottompipe.png")).getImage();

        //create bird
        bird = new Bird(birdImg, boardWidth, boardHeight);
        pipes = new ArrayList<>();

        //place pipes timer
        placePipesTimer = new Timer(1500, e -> placePipes()); //place pipes every 1500 ms
        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000 / 60, this); //60 fps
        gameLoop.start();
    }

    public void placePipes() {
        int pipeHeight = Pipe.PIPE_HEIGHT;
        int pipeY = 0;
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4; //allocate space for bird to fly through

        Pipe topPipe = new Pipe(topPipeImg, boardWidth, randomPipeY);
        pipes.add(topPipe); //add a new pipe every 1500 ms

        Pipe bottomPipe = new Pipe(bottomPipeImg, boardWidth, randomPipeY + pipeHeight + openingSpace); //add start pos of top pipe, the full pipe length, and open space to get where bottom pipe starts
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //draw score
        g.setColor(Color.white);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 32)); //use a game-like font
        String scoreText = gameOver ? "Game Over: " + (int) score : String.valueOf((int) score);
        int stringWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, (boardWidth - stringWidth) / 2, 60); //display slightly below the top middle
    }

    public void move() {
        //bird
        velocityY += gravity; //account for downward force at every motion
        bird.y += velocityY; //move upwards at a rate of -9 pixels/frame
        bird.y = Math.max(bird.y, 0); //ensure bird position can only ever be current position or top of screen

        //pipes
        for (Pipe pipe : pipes) {
            pipe.x += velocityX; //every frame, move each pipe -4 left

            if (!pipe.passed && bird.x > pipe.x + pipe.width) { //check if bird passed right side of pipe
                pipe.passed = true;
                score += 0.5; //0.5 represents the point split between each pipe segment
            }

            if (bird.collidesWith(pipe)) { //end game if bird collides with pipe
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) { //end game if bird falls off bottom of screen
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); 
        repaint();
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { //for all keys
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { //check if pressed key is space bar
            velocityY = -9;
            if (gameOver) {
                //restart game via resetting conditions to defaults
                bird.reset();
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) {} //for keys labelled by a char
    @Override public void keyReleased(KeyEvent e) {} //press and let go of key
}
