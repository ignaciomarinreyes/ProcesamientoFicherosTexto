package textprocessing;
public class FileReader extends Thread {
    
    private FileNames fn;
    private FileContents fc;
    private String identificador;
    
    public FileReader(FileNames fn, FileContents fc, String identificador){
        this.fn = fn;
        this.fc = fc;
        this.identificador = identificador;
    }
    
    @Override
    public void run(){
        String ruta;
        fc.registerWriter();
        while((ruta = fn.getName(identificador)) != null){
            fc.addContents(Tools.getContents(ruta), identificador);
        }
        fc.unregisterWriter();
    }
}
