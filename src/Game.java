
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
    private Dictionary dict;

    private Board board = new Board();
    private InfoPanel info;
    private Inventory inventoryPlayer1;
    private Inventory inventoryPlayer2;
    private Inventory currentInventory;
    private JPanel cardFrame;
    private JPanel instructionsCardFrame;
    private JLabel instructions;
    private CardLayout cardLayout;
    private CardLayout instructionsCardLayout;

    private JButton nextTurnButton;
    private JButton addToDictButton;
    private JButton instructionButton;
    private JButton backToPlayButton;
    private JLabel player1pts;
    private JLabel player2pts;

    private Mode mode = Mode.DEFAULT;
    private Tile currentWorkingTile = new Tile('0', 0);
    private int currentWorkingTileIndex = 0;
    private int turn = 0;

    public static Map<Character, BufferedImage> charToSquareMap = new HashMap<Character, BufferedImage>();
    public static Map<Character, Integer> charToPointMap = new HashMap<Character, Integer>();
    static {
        for (char c : new char[] { 'e', 'a', 'i', 'o', 'n', 'r', 't', 'l', 's', 'u' }) {
            charToPointMap.put(c, 1);
        }
        for (char c : new char[] { 'b', 'c', 'm', 'p' }) {
            charToPointMap.put(c, 3);
        }
        for (char c : new char[] { 'f', 'h', 'v', 'w', 'y' }) {
            charToPointMap.put(c, 4);
        }
        charToPointMap.put('d', 2);
        charToPointMap.put('g', 2);
        charToPointMap.put('k', 5);
        charToPointMap.put('j', 8);
        charToPointMap.put('x', 8);
        charToPointMap.put('q', 10);
        charToPointMap.put('z', 10);

    }

    public Game() {
        boardState = new BoardState();
        boardBuffer = new BoardBuffer(boardState.getBoardstate());
        info = new InfoPanel();
        nextTurnButton = new JButton("Next Turn");
        addToDictButton = new JButton("<html>Add Current Word<br />to Dictionary</html>");
        instructionButton = new JButton("Instructions");

        try {
            bag = new Bag();
        } catch (IOException e) {
            System.out.println("IOException when loading bag");
        }

        try {
            dict = new Dictionary();
        } catch (IOException e) {
            System.out.println("IOException when loading dictionary");
        }

        // importing letter tiles into charToSquareMap
        try {
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

        cardFrame = new JPanel(new CardLayout());
        instructionsCardFrame = new JPanel(new CardLayout());
        instructions = new JLabel();
        instructions.setText(
                "<html>This is my implementation of Scrabble. <br />To play, first click the tile then click where it should go on the board.<br />"
                        + "The point detection is a bit buggy but aside from that I think that's pretty cool.<br />"
                        + "Additionally, the board can detect the word being made.</html>");
        cardLayout = (CardLayout) cardFrame.getLayout();
        instructionsCardLayout = (CardLayout) instructionsCardFrame.getLayout();

        // Main playing area
        board.addMouseListener(new BoardMouse());
        frame.add(instructionsCardFrame, BorderLayout.CENTER);
        instructionsCardFrame.add(board, "board");
        instructionsCardFrame.add(instructions, "instructions");

        inventoryPlayer1 = new Inventory(bag, "Player 1");
        inventoryPlayer2 = new Inventory(bag, "Player 2");
        currentInventory = inventoryPlayer1;

        // the border is messed up and looks ugly I screwed up by not making
        // Square a jpanel
        // inventoryPlayer1.setBorder(BorderFactory.createTitledBorder("Inventory"));

        cardFrame.add(inventoryPlayer1, "Player 1");
        cardFrame.add(inventoryPlayer2, "Player 2");

        inventoryPlayer1.addMouseListener(new InventoryMouse());
        inventoryPlayer2.addMouseListener(new InventoryMouse());
        frame.add(cardFrame, BorderLayout.SOUTH);

        info.setLayout(new GridLayout(10, 1));
        info.setBorder(BorderFactory.createTitledBorder("Game State"));

        nextTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextTurn();
            }
        });

        addToDictButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dict.addWordToDict(boardBuffer.currentWord());
            }
        });

        instructionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instructionsCardLayout.next(instructionsCardFrame);
            }
        });

        player1pts = new JLabel("Player 1: " + inventoryPlayer1.getScore());
        player2pts = new JLabel("Player 2: " + inventoryPlayer2.getScore());

        info.add(nextTurnButton);
        info.add(addToDictButton);
        info.add(instructionButton);
        info.add(player1pts);
        info.add(player2pts);
        frame.add(info, BorderLayout.EAST);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void nextTurn() {
        endTurn(getNextInventory(turn));

    }

    private void endTurn(Inventory inv) {
        if (boardBuffer.checkIfValidBoardState() && dict.isInDict(boardBuffer.currentWord())) {
            currentInventory.incScore(boardBuffer.getScoreOnBoard());
            player1pts.setText("Player 1: " + inventoryPlayer1.getScore());
            player2pts.setText("Player 2: " + inventoryPlayer2.getScore());
            currentInventory.refillInventory(bag);
            if(currentInventory.isOutOfTiles()){
                info.add(new JLabel("Game Over!"));
            }
            boardState.update(boardBuffer.endTurn());
            boardBuffer = new BoardBuffer(boardState.getBoardstate());
            cardLayout.next(cardFrame);
            currentInventory = inv;
            turn += 1;
        }
    }

    private Inventory getNextInventory(int turn) {
        if (turn % 2 == 1) {
            return inventoryPlayer1;
        }
        return inventoryPlayer2;
    }

    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

    private void addTileToBoard(Tile tile, Point pt, Inventory inv) {
        boardBuffer.addLetter(tile, pt);
        board.paintSquare(pt,
                new Square(pt, charToSquareMap.get(Character.toLowerCase(tile.getLetter()))));
        inv.removeTile(currentWorkingTileIndex);

    }

    private void removeTileFromBoard(Point pt, Inventory inv) {
        inv.addTile(boardBuffer.getTileAtPoint(pt));
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
                    removeTileFromBoard(p, currentInventory);
                    System.out.println("Validboardstate: " + boardBuffer.checkIfValidBoardState());
                    System.out.println("Current Word: " + boardBuffer.currentWord());
                    System.out.println(boardBuffer.getXVals());
                    System.out.println(boardBuffer.getYVals());
                }
                System.out.println("Validboardstate: " + boardBuffer.checkIfValidBoardState());
                System.out.println("Current Word: " + boardBuffer.currentWord());
                System.out.println(boardBuffer.getXVals());
                System.out.println(boardBuffer.getYVals());
                System.out.println("Score: " + boardBuffer.getScoreOnBoard());
                System.out.println("Clicked square at (" + p.x + "," + p.y + ")");
                break;
            case PLACETILE:
                if (boardBuffer.getBoardState()[p.x][p.y].getLetter() == null
                        && !boardBuffer.getBoardState()[p.x][p.y].isFixed()) {
                    addTileToBoard(currentWorkingTile, p, currentInventory);
                    currentWorkingTile = new Tile('0', 0);
                    mode = Mode.DEFAULT;
                }
                System.out.println("Validboardstate: " + boardBuffer.checkIfValidBoardState());
                System.out.println("Current Word: " + boardBuffer.currentWord());
                System.out.println("Score: " + boardBuffer.getScoreOnBoard());
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
                currentWorkingTile = new Tile(currentInventory.getLetterOfTile(p.x),
                        Game.charToPointMap.get(currentInventory.getLetterOfTile(p.x)));
                currentWorkingTileIndex = p.x;
                mode = Mode.PLACETILE;
                System.out.println(currentInventory.getLetterOfTile(p.x));
                System.out.println("Clicked square at (" + p.x + "," + p.y + ")");
                break;
            case PLACETILE:
                currentWorkingTile = new Tile(currentInventory.getLetterOfTile(p.x),
                        Game.charToPointMap.get(currentInventory.getLetterOfTile(p.x)));
                currentWorkingTileIndex = p.x;
                mode = Mode.PLACETILE;
                System.out.println("Clicked square at (" + p.x + "," + p.y + ")");
                break;
            }

        }

    }
}
