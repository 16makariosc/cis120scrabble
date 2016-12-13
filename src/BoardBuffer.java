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

        if (dict.contains(currentWords())) {
            return true;
        }
        return false;

    }

    public String[] currentWords() {

        String word = "";
        ArrayList<String> words = new ArrayList<String>();

        if (newPositions.size() == 1) {
            int x = getXVals().get(0);
            int y = getYVals().get(0);
            if (board[x][y - 1].getLetter() != null || board[x][y + 1].getLetter() != null) {
                int miny = y;
                while (board[x][miny - 1].getLetter() != null) {
                    miny--;
                }
                while (board[x][miny].getLetter() != null) {
                    word += board[x][miny].getLetter();
                    miny++;
                }
                words.add(word);
                word = "";
            }

            if (board[x + 1][y].getLetter() != null || board[x - 1][y].getLetter() != null) {
                int minx = x;
                while (board[minx - 1][y].getLetter() != null) {
                    minx--;
                }
                while (board[minx][y].getLetter() != null) {
                    word += board[minx][y].getLetter();
                    minx++;
                }
                words.add(word);
                word = "";
            }
        } else if (isConstant(getYVals())) {

            int y = getYVals().get(0);
            int minx = Collections.min(getXVals());

            while (board[minx - 1][y].getLetter() != null) {
                minx--;
            }

            String mainWord = "";

            while (board[minx][y].getLetter() != null) {
                mainWord += board[minx][y].getLetter();
                if ((board[minx][y - 1].getLetter() != null
                        || board[minx][y + 1].getLetter() != null) && getXVals().contains(minx)) {
                    int miny = y;
                    while (board[minx][miny - 1].getLetter() != null) {
                        miny--;
                    }
                    String branchWord = "";
                    while (board[minx][miny].getLetter() != null) {
                        branchWord += board[minx][miny].getLetter();
                        miny++;
                    }
                    words.add(branchWord);
                }
                minx++;
            }
            words.add(mainWord);
            mainWord = "";

        } else if (isConstant(getXVals())) {
            int x = getXVals().get(0);
            int miny = Collections.min(getYVals());

            while (board[x][miny - 1].getLetter() != null) {
                miny--;
            }
            
            String mainWord = "";

            while (board[x][miny].getLetter() != null) {
                mainWord += board[x][miny].getLetter();
                if ((board[x - 1][miny].getLetter() != null
                        || board[x + 1][miny].getLetter() != null) && getYVals().contains(miny)) {
                    int minx = x;
                    while (board[minx - 1][miny].getLetter() != null) {
                        minx--;
                    }
                    String branchWord = "";
                    while (board[minx][miny].getLetter() != null) {
                        branchWord += board[minx][miny].getLetter();
                        minx++;
                    }
                    words.add(branchWord);
                }
                miny++;
            }
            words.add(mainWord);
            word = "";
        }

        return words.toArray(new String[words.size()]);

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

        if (newPositions.size() == 1) {
            int x = getXVals().get(0);
            int y = getYVals().get(0);
            if (board[x][y - 1].getLetter() != null || board[x][y + 1].getLetter() != null) {
                int miny = y;
                while (board[x][miny - 1].getLetter() != null) {
                    miny--;
                }
                while (board[x][miny].getLetter() != null) {
                    score += board[x][miny].getPointVal();
                    miny++;
                }
            }

            if (board[x + 1][y].getLetter() != null || board[x - 1][y].getLetter() != null) {
                int minx = x;
                while (board[minx - 1][y].getLetter() != null) {
                    minx--;
                }
                while (board[minx][y].getLetter() != null) {
                    score += board[minx][y].getPointVal();
                    minx++;
                }
            }

        } else if (isConstant(getYVals())) {

            int y = getYVals().get(0);
            int minx = Collections.min(getXVals());

            while (board[minx - 1][y].getLetter() != null) {
                minx--;
            }

            while (board[minx][y].getLetter() != null) {
                score += board[minx][y].getPointVal();
                if ((board[minx][y - 1].getLetter() != null
                        || board[minx][y + 1].getLetter() != null) && getXVals().contains(minx)) {
                    int miny = y;
                    while (board[minx][miny - 1].getLetter() != null) {
                        miny--;
                    }
                    while (board[minx][miny].getLetter() != null) {
                        score += board[minx][miny].getPointVal();
                        miny++;
                    }
                }
                minx++;
            }

        } else if (isConstant(getXVals())) {
            int x = getXVals().get(0);
            int miny = Collections.min(getYVals());

            while (board[x][miny - 1].getLetter() != null) {
                miny--;
            }

            while (board[x][miny].getLetter() != null) {
                score += board[x][miny].getPointVal();
                if ((board[x - 1][miny].getLetter() != null
                        || board[x + 1][miny].getLetter() != null) && getYVals().contains(miny)) {
                    int minx = x;
                    while (board[minx - 1][miny].getLetter() != null) {
                        minx--;
                    }
                    while (board[minx][miny].getLetter() != null) {
                        score += board[minx][miny].getPointVal();
                        minx++;
                    }
                }
                miny++;
            }

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
