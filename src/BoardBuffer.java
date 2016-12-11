import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class BoardBuffer {

    private Tile[][] board = new Tile[15][15];
    private ArrayList<Point> newPositions = new ArrayList<Point>();

    public BoardBuffer(Tile[][] boardState) {
        board = boardState;
    }

    public void addLetter(Tile tile, Point pos) {
        board[pos.x][pos.y] = tile;
        newPositions.add(pos);
    }

    public void removeLetter(Point pos) {
        board[pos.x][pos.y] = new Tile('0', 0);
        newPositions.remove(pos);
    }

    public boolean isValidWord(HashSet<String> dict) { // spellchecker

        if (dict.contains(currentWord())) {
            return true;
        }
        return false;

    }

    private String currentWord() {

        String word = "";

        if (posAreInLine()) {
            if (isConstant(getYVals())) {
                int y = getYVals().get(0);
                for (int x : getXVals()) {
                    word += board[x][y].getLetter();
                }
                return word;
            } else if (isConstant(getXVals())) {
                int x = getYVals().get(0);
                for (int y : getYVals()) {
                    word += board[x][y].getLetter();
                }
                return word;
            }
        }
        return word;

    }

    public boolean checkIfValidBoardState() {

        if (posAreInLine()) {

            for (Point pos : newPositions) {
                if (!isTouchingValidTile(pos)) {
                    return false;
                }
            }

        }
        return true;

    }

    private boolean posAreInLine() { // returns if the tiles are all next to
                                     // each other and in the same line

        ArrayList<Integer> xvals = getXVals();
        ArrayList<Integer> yvals = getYVals();
        Collections.sort(xvals);
        Collections.sort(yvals);

        if (isConstant(xvals) == false && isConstant(yvals) == false) {
            return false;
        } else if (isConstant(xvals) == false && isConstant(yvals) == true) {
            if (!isAscending(xvals)) {
                return false;
            }
        } else if (isConstant(xvals) == true && isConstant(yvals) == false) {
            if (!isAscending(yvals)) {
                return false;
            }
        }

        return true;

    }

    private ArrayList<Integer> getXVals() {
        ArrayList<Integer> xvals = new ArrayList<Integer>();
        for (Point pos : newPositions) {
            xvals.add(pos.x);
        }
        return xvals;
    }

    private ArrayList<Integer> getYVals() {
        ArrayList<Integer> yvals = new ArrayList<Integer>();
        for (Point pos : newPositions) {
            yvals.add(pos.x);
        }
        return yvals;
    }

    private boolean isConstant(ArrayList<Integer> ints) {
        int first = ints.get(0);
        for (int num : ints) {
            if (num != first) {
                return false;
            }
        }
        return true;
    }

    private boolean isAscending(ArrayList<Integer> ints) {
        int i = ints.get(0);
        for (int j : ints) {
            if (i != j) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean isTouchingValidTile(Point pos) {
        int x = pos.x;
        int y = pos.y;
        if (board[x + 1][y].isFixed()) {
            return true;
        } else if (board[x - 1][y].isFixed()) {
            return true;
        } else if (board[x][y + 1].isFixed()) {
            return true;
        } else if (board[x][y - 1].isFixed()) {
            return true;
        }
        return false;
    }

    public boolean isValidSpace(Point pos) {
        if (board[pos.x][pos.y].getLetter() == null) {
            return false;
        }
        return true;
    }

    public int getScoreOnBoard() {
        int score = 0;
        for (Point pt : newPositions) {
            score += board[pt.x][pt.y].getPointVal();
        }
        return score;
    }
    
    public Tile getTileAtPoint(Point p){
        return board[p.x][p.y];
    }

    public Tile[][] getBoardState() {
        return board;
    }
    
    public Tile[][] endTurn(){
        for (Point pt : newPositions){
            board[pt.x][pt.y].fix();
        }
        return getBoardState();
    }

}
