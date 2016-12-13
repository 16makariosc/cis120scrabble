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
        bb.addLetter(new Tile('b', 1), new Point(6, 7));
        assertEquals("ba", bb.currentWords()[0]);
        bb.addLetter(new Tile('d', 1), new Point(8, 7));
        assertEquals("bad", bb.currentWords()[0]);
        bb.addLetter(new Tile('s', 1), new Point(9, 9));

    }

    @Test
    public void testDictionary() {
        try {
            Dictionary d = new Dictionary();
            d.addWordToDict(new String[] { "string" });
            d.addWordToDict(new String[] { "clay" });
            assertTrue(d.isInDict(new String[] { "string" }));
            assertTrue(d.isInDict(new String[] { "clay" }));
        } catch (IOException ioe) {
            System.out.println("Could not create Dictionary");
        }

        try {
            Dictionary d1 = new Dictionary();
            assertTrue(d1.isInDict(new String[] { "string" }));
            assertTrue(d1.isInDict(new String[] { "clay" }));
        } catch (IOException ioe) {
            System.out.println("Could not create Dictionary");
        }
    }

}
