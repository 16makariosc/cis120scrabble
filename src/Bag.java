import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Bag {

    private List<Character> bag = new LinkedList<Character>();
    private Random genRand = new Random();
    private File tileFile;
    private BufferedReader tileReader;

    public Bag() throws IOException {
        String letter;
        tileFile = new File("./resources/tileBag.txt");

        if (!tileFile.exists()) {
            tileFile.createNewFile();
        }

        tileReader = new BufferedReader(new FileReader(tileFile));

        try {
            letter = tileReader.readLine();
            while (letter != null) {
                bag.add(letter.trim().charAt(0));
                letter = tileReader.readLine();
            }
        } finally {
            tileReader.close();
        }
    }

    public Character getNextTile() {
        if (bag.size() == 0){
            return '0';
        }
        int randNum = genRand.nextInt(bag.size());
        Character tile = bag.get(randNum);
        bag.remove(randNum);
        return tile;
    }

}
