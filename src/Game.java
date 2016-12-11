
// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {

    private BoardState boardState;
    private BoardBuffer boardBuffer;
    private Board board = new Board();
    private Mode mode = Mode.DEFAULT;
    private Tile currentWorkingTile = new Tile('a', 5);
    private Map<Character, BufferedImage> charToSquareMap = new HashMap<Character, BufferedImage>();

    public Game() {
        boardState = new BoardState();
        boardBuffer = new BoardBuffer(boardState.getBoardstate());
        
        //importing letter tiles into charToSquareMap
        try {
            charToSquareMap.put('a', ImageIO.read(new File("resources/lettertiles/a.png")));
//            for (char alphabet = 'a'; alphabet <= 'z'; alphabet ++){
//                charToSquareMap.put(alphabet, ImageIO.read(new File("resources/lettertiles/" + alphabet + ".png")));
//            }
        } catch (IOException e){
            System.out.println("IOException when loading letterTiles");
        }
        
    }

    public void run() {
        final JFrame frame = new JFrame("Scrabble");
        frame.setLocation(300, 300);

        // Main playing area
        board.addMouseListener(new BoardMouse());
        frame.add(board, BorderLayout.CENTER);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void step() {

    }

    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
    
    private void addTileToBoard(Tile tile, Point pt){
        boardBuffer.addLetter(tile, pt);
        board.paintSquare(pt, new Square(pt, charToSquareMap.get(Character.toLowerCase(tile.getLetter()))));
        
    }
    
    private void removeTileFromBoard(Point pt){
        boardBuffer.removeLetter(pt);
        board.paintSquareToDefault(pt);
    }

    private class BoardMouse extends MouseAdapter implements MouseListener {

        private Point p;

        @Override
        public void mouseClicked(MouseEvent arg0) {
            Point p = arg0.getPoint();
            p.x = p.x / (Square.WIDTH);
            p.y = p.y / (Square.HEIGHT);

            switch (mode) {
            case DEFAULT:
                if (boardBuffer.getBoardState()[p.x][p.y].getLetter() != null
                        && !boardBuffer.getBoardState()[p.x][p.y].isFixed()) {
                    removeTileFromBoard(p);
                }
                System.out.println("Clicked square at (" + p.x + "," + p.y + ")");
                break;
            case PLACETILE:
                if (boardBuffer.getBoardState()[p.x][p.y].getLetter() == null
                        && !boardBuffer.getBoardState()[p.x][p.y].isFixed()) {
                    addTileToBoard(currentWorkingTile, p);
                }
                mode = Mode.DEFAULT;
                System.out.println("Clicked square at (" + p.x + "," + p.y + ")");
                break;

            }

        }

    }
}
