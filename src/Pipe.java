import java.awt.Image;

public class Pipe {
    public static final int PIPE_WIDTH = 64, PIPE_HEIGHT = 512;

    //Pipe
    public int x, y;
    public int width = PIPE_WIDTH, height = PIPE_HEIGHT; 
    public Image img;
    public boolean passed = false; //check if bird has passed pipe

    public Pipe(Image img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }
}
