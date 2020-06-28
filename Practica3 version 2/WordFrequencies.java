package textprocessing;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class WordFrequencies {
    
    private Map<String,Integer> mapa;
    
    public WordFrequencies(){
        mapa = new HashMap<String,Integer>();
    }
    
    public synchronized void addFrequencies(Map<String,Integer> f, String identificador){
        Set <Map.Entry<String,Integer>> parejas = f.entrySet();
        for (Map.Entry<String,Integer> pareja: parejas){
            String clave = pareja.getKey();
            Integer valor = pareja.getValue();
            mapa.put(clave, mapa.containsKey(clave) ? mapa.get(clave) + valor : valor);
        }
        System.out.println(identificador + " acumula en el mapa");
    }
    
    public Map<String,Integer> getFrequencies(){
        return new HashMap<String,Integer>(mapa);
    }
}
