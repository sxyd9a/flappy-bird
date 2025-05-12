import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel {
    int boardWidth = 360, boardHeight = 640;

    //Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipImg;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        backgroundImg = new ImageIcon(getClass().getResource("/images/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/images/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/images/toppipe.png")).getImage();
        bottomPipImg = new ImageIcon(getClass().getResource("/images/bottompipe.png")).getImage();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g) {
        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
    }
}
