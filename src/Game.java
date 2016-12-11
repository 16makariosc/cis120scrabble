
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
    private Bag bag;

    private Board board = new Board();
    private ControlsGUI controlsgui;

    private Mode mode = Mode.DEFAULT;
    private Tile currentWorkingTile = new Tile('0', 5);
    private int currentWorkingTileIndex = 0;

    public static Map<Character, BufferedImage> charToSquareMap = new HashMap<Character, BufferedImage>();

    public Game() {
        boardState = new BoardState();
        boardBuffer = new BoardBuffer(boardState.getBoardstate());

        try {
            bag = new Bag();
        } catch (IOException e) {
            System.out.println("IOException when loading bag");
        }

        // importing letter tiles into charToSquareMap
        try {
            // charToSquareMap.put('a', ImageIO.read(new
            // File("resources/lettertiles/a.png")));
            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                charToSquareMap.put(alphabet,
                        ImageIO.read(new File("resources/lettertiles/" + alphabet + ".png")));
            }
            charToSquareMap.put('0', ImageIO.read(new File("resources/lettertiles/spacer.png")));
        } catch (IOException e) {
            System.out.println("IOException when loading letterTiles");
        }

    }

    public void run() {

        final JFrame frame = new JFrame("Scrabble");
        frame.setLocation(300, 300);

        // Main playing area
        board.addMouseListener(new BoardMouse());
        frame.add(board, BorderLayout.CENTER);

        controlsgui = new ControlsGUI(bag);
        // the border is messed up and looks ugly I screwed up by not making
        // Square a jpanel
        // controlsgui.setBorder(BorderFactory.createTitledBorder("Inventory"));
        controlsgui.addMouseListener(new InventoryMouse());
        frame.add(controlsgui, BorderLayout.SOUTH);

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

    private void addTileToBoard(Tile tile, Point pt) {
        boardBuffer.addLetter(tile, pt);
        board.paintSquare(pt,
                new Square(pt, charToSquareMap.get(Character.toLowerCase(tile.getLetter()))));
        controlsgui.removeTile(currentWorkingTileIndex);

    }

    private void removeTileFromBoard(Point pt) {
        controlsgui.addTile(boardBuffer.getTileAtPoint(pt));
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
                    currentWorkingTile = new Tile('0', 0);
                    mode = Mode.DEFAULT;
                }
                System.out.println("Clicked square at (" + p.x + "," + p.y + ")");
                break;

            }

        }

    }

    private class InventoryMouse extends MouseAdapter implements MouseListener {

        private Point p;

        @Override
        public void mouseClicked(MouseEvent arg0) {
            Point p = arg0.getPoint();
            p.x = p.x / (Square.WIDTH);
            p.y = p.y / (Square.HEIGHT);

            switch (mode) {
            case DEFAULT:
                currentWorkingTile = new Tile(controlsgui.getLetterOfTile(p.x), 0);
                currentWorkingTileIndex = p.x;
                mode = Mode.PLACETILE;
                System.out.println(controlsgui.getLetterOfTile(p.x));
                System.out.println("Clicked square at (" + p.x + "," + p.y + ")");
                break;
            case PLACETILE:
                currentWorkingTile = new Tile(controlsgui.getLetterOfTile(p.x), 0);
                currentWorkingTileIndex = p.x;
                mode = Mode.PLACETILE;
                System.out.println("Clicked square at (" + p.x + "," + p.y + ")");
                break;
            }

        }

    }
}
