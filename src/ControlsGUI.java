
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ControlsGUI extends JPanel {

    private Tile[] inventory = new Tile[7];
    private Square[] inventorygui = new Square[7];

    public ControlsGUI(Bag bag) {

        for (int i = 0; i < 7; i++) {
            addTile(new Tile(bag.getNextTile(), 0));
        }
    }

    public void addTile(Tile tile) {
        for (int i = 0; i < 7; i++){
            if (inventory[i] == null){
                System.out.println(i);
                inventory[i] = tile;
                inventorygui[i] = new Square(new Point(i, 0),
                        Game.charToSquareMap.get(tile.getLetter()));
                break;
            }
        }
        repaint();
    }

    public void removeTile(int index) {
        inventory[index] = null;
        inventorygui[index] = new Square(new Point(index, 0), Game.charToSquareMap.get('0'));
        repaint();
    }

    public Character getLetterOfTile(int index) {
        return inventory[index].getLetter();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Square sq : inventorygui) {
            sq.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Square.WIDTH * 15, 60);
    }

}
