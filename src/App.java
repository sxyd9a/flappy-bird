import javax.swing.*;

//Java Project - Flappy Bird

public class App {
    public static void main(String[] args) {
        int boardWidth = 360, boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); //Place window at center of screen
        frame.setResizable(false); //Don't allow user to resize window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //terminate window on close

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack(); //Without this, the width and height would take the game window's title bar into account
        frame.setVisible(true); 
    }
}
