package textprocessing;

import java.util.HashMap;
import java.util.Map;

public class FileProcessor extends Thread {

    private final FileContents fc;
    private final WordFrequencies wf;

    public FileProcessor(FileContents fc, WordFrequencies wf) {
        this.fc = fc;
        this.wf = wf;
    }

    private String[] separateWords(String content) {
        return content.split("[^\\x{30}-\\x{39}\\x{41}-\\x{5A}\\x{61}-\\x{7A}\\x{7B}-\\x{7F}ñÑáéíóúÁÉÍÓÚüÜ]+");
    }

    private Map<String, Integer> getWordsFrequenciesContent(String[] words) {
        Map<String, Integer> wordFrequenciesContent = new HashMap<>();
        for (String word : words) {
            wordFrequenciesContent.put(word, wordFrequenciesContent.containsKey(word) ? wordFrequenciesContent.get(word) + 1 : 1);
        }
        return wordFrequenciesContent;
    }

    @Override
    public void run() {
        String content;
        while ((content = fc.getContents()) != null) {
            wf.addFrequencies(getWordsFrequenciesContent(separateWords(content)));
        }
    }
}
