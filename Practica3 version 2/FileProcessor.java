package textprocessing;

import java.util.Map;
import java.util.HashMap;

public class FileProcessor extends Thread {
    
    private FileContents fc;
    private WordFrequencies wf;
    private String identificador;
    
    public FileProcessor(FileContents fc, WordFrequencies wf, String identificador){
        this.fc = fc;
        this.wf = wf;
        this.identificador = identificador;
    }
    
    
    private String[] separarPalabras(String contents){
        return contents.split("[^\\x{30}-\\x{39}\\x{41}-\\x{5A}\\x{61}-\\x{7A}\\x{7B}-\\x{7F}ñÑáéíóúÁÉÍÓÚüÜ]+");
    }
    
    private Map<String,Integer> procesarPalabras(String[] palabras){
        Map<String,Integer> mapa = new HashMap<String,Integer>();
        for(int i= 0; i < palabras.length; i++){
            mapa.put(palabras[i], mapa.containsKey(palabras[i]) ? mapa.get(palabras[i]) + 1 : 1);    
        }
        return mapa;
    }
    
    
    @Override
    public void run(){
        String contents;
        while((contents = fc.getContents(identificador)) != null){
            wf.addFrequencies(procesarPalabras(separarPalabras(contents)), identificador);    
        }
    }
    
}
