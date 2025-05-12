import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360, boardHeight = 640;

    //Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipImg;

    //Bird
    int birdX = boardWidth/8, birdY = boardHeight/2;
    int birdWidth = 34, birdHeight = 24;

    class Bird {
        int x = birdX, y = birdY;
        int width = birdWidth, height = birdHeight;
        Image img;

        Bird (Image img) {
            this.img = img;
        }
    }

    //Pipes
    int pipeX = boardWidth, pipeY = 0;
    int pipeWidth = 64, pipeHeight = 512;

    class Pipe {
        int x = pipeX, y = pipeY;
        int width = pipeWidth, height = pipeHeight; 
        Image img;
        boolean passed = false; //check if bird has passed pipe

        Pipe(Image img) {
            this.img = img;
        }
    }

    //game logic
    Bird bird;
    int velocityX = -4; //rate at which pipes move left
    int velocityY = 0; //rate at which bird moves up
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        //send key event to the JPanel and check key functions     
        setFocusable(true);
        addKeyListener(this);

        backgroundImg = new ImageIcon(getClass().getResource("/images/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/images/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/images/toppipe.png")).getImage();
        bottomPipImg = new ImageIcon(getClass().getResource("/images/bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() { //place pipes every 1500 ms
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this); //60 fps
        gameLoop.start();
    }

    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe); //add a new pipe every 1500 ms
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g) {
        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for(int i = 0; i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        } 
    }

    public void move(){
        //bird
        velocityY += gravity; //account for downward force at every motion
        bird.y += velocityY; //move upwards at a rate of -9 pixels/frame
        bird.y = Math.max(bird.y, 0); //ensure bird position can only ever be current position or top of screen

        //pipes
        for(int i = 0; i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX; //every frame, move each pipe -4 left
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); 
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) { //for all keys
        if(e.getKeyCode() == KeyEvent.VK_SPACE){ //check if pressed key is space bar
            velocityY = -9;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {} //for keys labelled by a char

    @Override
    public void keyReleased(KeyEvent e) {} //press and let go of key
    
}
