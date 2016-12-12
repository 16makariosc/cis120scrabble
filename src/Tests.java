import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;

import org.junit.*;

public class Tests {

    @Test
    public void testAddAndRemoveTiles() {

        BoardState bs = new BoardState();
        BoardBuffer bb = new BoardBuffer(bs.getBoardstate());

        bb.addLetter(new Tile('a', 1), new Point(0, 0));
        assertTrue(bb.getBoardState()[0][0].getLetter() == 'a');
        assertTrue(bb.getTileAtPoint(new Point(0, 0)).getLetter() == 'a');
        bb.removeLetter(new Point(0, 0));
        assertTrue(bb.getBoardState()[0][0].getLetter() == null);

    }

    @Test
    public void checkValidBoardState() {

        BoardState bs = new BoardState();
        BoardBuffer bb = new BoardBuffer(bs.getBoardstate());

        bb.addLetter(new Tile('a', 1), new Point(7, 7));
        assertTrue(bb.checkIfValidBoardState());
        bb.addLetter(new Tile('a', 1), new Point(7, 8));
        assertTrue(bb.checkIfValidBoardState());
        bb.addLetter(new Tile('a', 1), new Point(8, 8));
        assertFalse(bb.checkIfValidBoardState());
        bb.removeLetter(new Point(8, 8));
        assertTrue(bb.checkIfValidBoardState());
    }

    @Test
    public void checkCurrentWord() {

        BoardState bs = new BoardState();
        BoardBuffer bb = new BoardBuffer(bs.getBoardstate());

        bb.addLetter(new Tile('a', 1), new Point(7, 7));
        assertEquals("a", bb.currentWord());
        bb.addLetter(new Tile('b', 1), new Point(6, 7));
        assertEquals("ba", bb.currentWord());
        bb.addLetter(new Tile('d', 1), new Point(8, 7));
        assertEquals("bad", bb.currentWord());
        bb.addLetter(new Tile('s', 1), new Point(9, 9));
        assertEquals("", bb.currentWord());

    }

    @Test
    public void testDictionary() {
        try {
            Dictionary d = new Dictionary();
            d.addWordToDict("string");
            d.addWordToDict("clay");
            assertTrue(d.isInDict("string"));
            assertTrue(d.isInDict("clay"));
        } catch (IOException ioe) {
            System.out.println("Could not create Dictionary");
        }

        try {
            Dictionary d1 = new Dictionary();
            assertTrue(d1.isInDict("string"));
            assertTrue(d1.isInDict("clay"));
        } catch (IOException ioe) {
            System.out.println("Could not create Dictionary");
        }
    }

}
