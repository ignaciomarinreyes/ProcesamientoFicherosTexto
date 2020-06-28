package textprocessing;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class WordFrequencies {

    private final Map<String, Integer> wf = new HashMap<String, Integer>();

    public synchronized void addFrequencies(Map<String, Integer> f) {
        Set<Map.Entry<String, Integer>> set = f.entrySet();
        for (Map.Entry<String, Integer> pair : set) {
            wf.put(pair.getKey(), wf.containsKey(pair.getKey()) ? wf.get(pair.getKey()) + f.get(pair.getKey()) : f.get(pair.getKey()));
        }
    }

    public Map<String, Integer> getFrequencies() {
        return wf;
    }
}
