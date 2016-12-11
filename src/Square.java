
import java.awt.*;
import java.awt.image.BufferedImage;

public class Square {

    private Point pos;
    
    public static final int HEIGHT = 50;
    public static final int WIDTH = 46;

    private BufferedImage img;

    public Square(Point pos, BufferedImage img) {

        this.pos = pos;
        this.img = img;

    }

    public void draw(Graphics g){
        g.drawImage(img, pos.x * WIDTH, pos.y * HEIGHT, WIDTH, HEIGHT, null);
    }

}
