
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Inventory extends JPanel {

    private Tile[] inventory = new Tile[7];
    private Square[] inventorygui = new Square[7];
    private int score = 0;
    private final String name;
    private boolean outOfTiles = false;

    public Inventory(Bag bag, String name) {

        this.name = name;

        for (int i = 0; i < 7; i++) {
            char c = bag.getNextTile();
            addTile(new Tile(c, Game.charToPointMap.get(c)));
        }
    }

    public void addTile(Tile tile) {
        for (int i = 0; i < 7; i++) {
            if (inventory[i] == null) {
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

    public void refillInventory(Bag bag) {
        for (int i = 0; i < 7; i++) {
            if (inventory[i] == null) {
                char c = bag.getNextTile();
                if (c == '0'){
                    outOfTiles = true;
                    break;
                }
                addTile(new Tile(c, Game.charToPointMap.get(c)));
            }
        }
    }
    
    public String getName(){
        return name;
    }
    
    public int getScore(){
        return score;
    }
    
    public void incScore(int incr){
        score = score + incr;
    }
    
    public boolean isOutOfTiles(){
        return outOfTiles;
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
