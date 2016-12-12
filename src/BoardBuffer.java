import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

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
        board[pos.x][pos.y] = new Tile(null, 0);
        newPositions.remove(pos);
    }

    public boolean isValidWord(Set<String> dict) { // spellchecker

        if (dict.contains(currentWord())) {
            return true;
        }
        return false;

    }

    public String currentWord() {

        String word = "";

        if (isConstant(getYVals())) {
            int y = getYVals().get(0);
            for (int x = Collections.min(getXVals()); board[x][y].getLetter() != null; x++) {
                word += board[x][y].getLetter();
            }

            return word;
        }
        if (isConstant(getXVals())) {
            int x = getXVals().get(0);
            for (int y = Collections.min(getYVals()); board[x][y].getLetter() != null; y++) {
                word += board[x][y].getLetter();
            }
            // for (int y : getYVals()) {
            // word += board[x][y].getLetter();
            // }
            return word;
        }
        return word;

    }

    public boolean checkIfValidBoardState() {

        if (isConstant(getXVals()) || isConstant(getYVals())) {
            for (Point pos : newPositions) {
                if ((pos.x == 7 && pos.y == 7) || isTouchingValidTile(pos)) {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean posAreInLine() { // returns if the tiles are all next to
                                     // each other and in the same line

        ArrayList<Integer> xvals = getXVals();
        ArrayList<Integer> yvals = getYVals();
        Collections.sort(xvals);
        Collections.sort(yvals);

        if (!isConstant(xvals) && !isConstant(yvals)) {
            return false;
        } else if (!isConstant(xvals) && isConstant(yvals)) {
            return isAscending(xvals);
        } else if (isConstant(xvals) && !isConstant(yvals)) {
            return isAscending(yvals);
        }

        return true;

    }

    public ArrayList<Integer> getXVals() {
        ArrayList<Integer> xvals = new ArrayList<Integer>();
        for (Point pos : newPositions) {
            xvals.add(pos.x);
        }
        return xvals;
    }

    public ArrayList<Integer> getYVals() {
        ArrayList<Integer> yvals = new ArrayList<Integer>();
        for (Point pos : newPositions) {
            yvals.add(pos.y);
        }
        return yvals;
    }

    private boolean isConstant(ArrayList<Integer> ints) {
        try {
            int first = ints.get(0);
            for (int num : ints) {
                if (num != first) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
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

        if (isConstant(getYVals())) {

            int y = getYVals().get(0);
            int minx = Collections.min(getXVals());

            while (board[minx][y].getLetter() != null) {
                minx--;
            }

            for (int x = minx + 1; board[x][y].getLetter() != null; x++) {
                score += board[x][y].getPointVal();
            }

            return score;

        } else if (isConstant(getXVals())) {
            int x = getXVals().get(0);
            int miny = Collections.min(getYVals());

            while (board[x][miny].getLetter() != null) {
                miny--;
            }

            for (int y = miny + 1; board[x][y].getLetter() != null; y++) {
                score += board[x][y].getPointVal();
            }

            return score;
        }
        return score;
    }

    public Tile getTileAtPoint(Point p) {
        return board[p.x][p.y];
    }

    public Tile[][] getBoardState() {
        return board;
    }

    public Tile[][] endTurn() {
        for (Point pt : newPositions) {
            board[pt.x][pt.y].fix();
        }
        return getBoardState();
    }

}
