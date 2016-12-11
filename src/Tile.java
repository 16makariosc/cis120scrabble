
public class Tile {

    private Character letter;
    private int pointVal;
    private boolean checked;
    private boolean fixed;

    public Tile(Character letter, int pointVal) {

        this.letter = letter;
        this.pointVal = pointVal;
        checked = false;
        fixed = false;
    }

    public Character getLetter() {
        return letter;
    }

    public int getPointVal() {
        return pointVal;
    }
    
    public void check(){
        checked = true;
    }
    
    public boolean isChecked(){
        return checked;
    }
    
    public void fix(){ //'fixes' tile so it cannot be removed from board
        fixed = true;
    }
    
    public boolean isFixed(){
        return fixed;
    }

}
