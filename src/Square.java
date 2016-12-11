
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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((img == null) ? 0 : img.hashCode());
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Square other = (Square) obj;
        if (img == null) {
            if (other.img != null)
                return false;
        } else if (!img.equals(other.img))
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        return true;
    }

    public void getcoords(){
        System.out.println(pos.x + "," + pos.y);
    }

}
