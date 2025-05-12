import java.awt.Image;

public class Bird {
    //Bird
    public int x, y;
    public int width = 34, height = 24;
    public Image img;

    private final int initialX, initialY;

    public Bird(Image img, int boardWidth, int boardHeight) {
        this.img = img;
        this.initialX = boardWidth / 8;
        this.initialY = boardHeight / 2;
        this.x = initialX;
        this.y = initialY;
    }

    public void reset() {
        //reset bird position to initial
        this.x = initialX;
        this.y = initialY;
    }

    public boolean collidesWith(Pipe pipe) {
        return x < pipe.x + pipe.width && 
               x + width > pipe.x &&
               y < pipe.y + pipe.height &&
               y + height > pipe.y;
    }
}
