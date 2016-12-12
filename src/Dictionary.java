import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {

    private BufferedReader dictReader;
    private File dictionaryFile;
    private BufferedWriter dictWriter;
    private Set<String> dictionary = new HashSet<String>();

    public Dictionary() throws IOException {
        String word;
        dictionaryFile = new File("./resources/dictionary.txt");

        if (!dictionaryFile.exists()) {
            dictionaryFile.createNewFile();
        }

        dictReader = new BufferedReader(new FileReader(dictionaryFile));

        try {
            word = dictReader.readLine();
            while (word != null) {
                dictionary.add(word.trim());
                word = dictReader.readLine();
            }
        } finally {
            dictReader.close();
        }
    }

    public void addWordToDict(String word) {
        String newWord = word.trim();
        dictionary.add(newWord);
        try {
            dictWriter = new BufferedWriter(new FileWriter(dictionaryFile, true));
            dictWriter.write('\n' + newWord);
            dictWriter.close();
        } catch (IOException e) {
            System.out.println("Error: Cannot write to dictionaryfile");
        } 
    }

    public boolean isInDict(String word) {
        return dictionary.contains(word);
    }

}
