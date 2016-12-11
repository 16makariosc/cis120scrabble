
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

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + (checked ? 1231 : 1237);
//        result = prime * result + (fixed ? 1231 : 1237);
//        result = prime * result + ((letter == null) ? 0 : letter.hashCode());
//        result = prime * result + pointVal;
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Tile other = (Tile) obj;
//        if (checked != other.checked)
//            return false;
//        if (fixed != other.fixed)
//            return false;
//        if (letter == null) {
//            if (other.letter != null)
//                return false;
//        } else if (!letter.equals(other.letter))
//            return false;
//        if (pointVal != other.pointVal)
//            return false;
//        return true;
//    }

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
