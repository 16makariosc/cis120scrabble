import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel {
    // There is no boardstate managed in this class.
    // This class is GUI Only!

    private BufferedImage normalWordTile;
    private BufferedImage tripleWordTile;
    private BufferedImage tripleLettTile;
    private BufferedImage doubleWordTile;
    private BufferedImage doubleLettTile;
    private BufferedImage starTile;

    private static final Point[] tripleWordLocations = { new Point(0, 0), new Point(7, 0),
            new Point(14, 0), new Point(0, 7), new Point(14, 7), new Point(0, 14), new Point(7, 14),
            new Point(14, 14) };

    private static final Point[] tripleLettLocations = { new Point(5, 1), new Point(9, 1),
            new Point(1, 5), new Point(5, 5), new Point(9, 5), new Point(13, 5), new Point(1, 9),
            new Point(5, 9), new Point(9, 9), new Point(13, 9), new Point(5, 13),
            new Point(9, 13) };

    private static final Point[] doubleWordLocations = { new Point(1, 1), new Point(2, 2),
            new Point(3, 3), new Point(4, 4), new Point(10, 10), new Point(11, 11),
            new Point(12, 12), new Point(13, 13), new Point(13, 1), new Point(12, 2),
            new Point(11, 3), new Point(10, 4), new Point(3, 11), new Point(2, 12),
            new Point(1, 13), new Point(4, 10) };

    private static final Point[] doubleLettLocations = { new Point(3, 0), new Point(11, 0),
            new Point(6, 2), new Point(8, 2), new Point(0, 3), new Point(7, 3), new Point(14, 3),
            new Point(2, 6), new Point(6, 6), new Point(8, 6), new Point(12, 6), new Point(3, 7),
            new Point(11, 7), new Point(2, 8), new Point(6, 8), new Point(8, 8), new Point(12, 8),
            new Point(0, 11), new Point(7, 11), new Point(14, 11), new Point(6, 12),
            new Point(8, 12), new Point(3, 14), new Point(11, 14) };

    private Point[] starLocations = { new Point(7, 7) };
    private Square[][] board = new Square[15][15];

    public Board() {

        try {
            normalWordTile = ImageIO.read(new File("resources/boardtiles/normal.png"));
            tripleWordTile = ImageIO.read(new File("resources/boardtiles/tripleword.png"));
            tripleLettTile = ImageIO.read(new File("resources/boardtiles/triplelett.png"));
            doubleWordTile = ImageIO.read(new File("resources/boardtiles/doubleword.png"));
            doubleLettTile = ImageIO.read(new File("resources/boardtiles/doublelett.png"));
            starTile = ImageIO.read(new File("resources/boardtiles/star.png"));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        // initialize the board with normalWordTiles
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                board[x][y] = new Square(new Point(x, y), normalWordTile);
            }
        }

        // layer on tripleWordTiles
        for (Point pt : tripleWordLocations) {
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), tripleWordTile);
        }

        // layer on doubleWordTiles
        for (Point pt : doubleWordLocations) {
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), doubleWordTile);
        }

        // layer on tripleLettTiles
        for (Point pt : tripleLettLocations) {
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), tripleLettTile);
        }

        // layer on doubleLettTiles
        for (Point pt : doubleLettLocations) {
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), doubleLettTile);
        }

        // shine like a star (I know this is only one point but consistancy)
        for (Point pt : starLocations) {
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), starTile);
        }

    }

    public void paintSquare(Point pos, Square square) {
        board[pos.x][pos.y] = square;
        repaint();
    }

    public void paintSquareToDefault(Point pt) {
        if (Arrays.asList(tripleWordLocations).contains(pt)){
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), tripleWordTile);
        } else if (Arrays.asList(doubleWordLocations).contains(pt)){
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), doubleWordTile);
        } else if (Arrays.asList(tripleLettLocations).contains(pt)){
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), tripleLettTile);
        } else if (Arrays.asList(doubleLettLocations).contains(pt)){
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), doubleLettTile);
        } else if (Arrays.asList(starLocations).contains(pt)){
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), starTile);
        } else {
            board[pt.x][pt.y] = new Square(new Point(pt.x, pt.y), normalWordTile);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                board[x][y].draw(g);
            }
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Square.WIDTH * 15, Square.HEIGHT * 15);
    }

}
