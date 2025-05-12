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

    //game logic
    Bird bird;
    int velocityY = 0;
    int gravity = 1;

    Timer gameLoop;

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

        //game timer
        gameLoop = new Timer(1000/60, this); //60 fps
        gameLoop.start();
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
    }

    public void move(){
        //bird
        velocityY += gravity; //account for downward force at every motion
        bird.y += velocityY; 
        bird.y = Math.max(bird.y, 0); //ensure bird position can only ever be current position or top of screen
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); //move upwards at a rate of -6 pixels/frame
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
