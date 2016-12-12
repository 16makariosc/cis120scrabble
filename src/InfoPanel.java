import java.awt.Dimension;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Square.WIDTH * 4, Square.HEIGHT * 15);
    }


}
