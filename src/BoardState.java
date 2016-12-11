
public class BoardState{
    
    private Tile[][] board;
    

    public BoardState(){
        board = new Tile[15][15];
        
        for (int y = 0; y < 15; y++){
            for (int x = 0; x < 15; x++){
                board[x][y] = new Tile(null, 0);
            }
        }
    }
    
     
    public Tile[][] getBoardstate(){
        return board;
    }

}
