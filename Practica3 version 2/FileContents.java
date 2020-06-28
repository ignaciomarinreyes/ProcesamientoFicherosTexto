package textprocessing;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileContents {
    private Queue<String> queue;
    private int registerCount = 0;
    private boolean closed = false;
    private int maxFiles;
    private int maxChars;
    private int numberChars = 0;
    
    public FileContents(int maxFiles, int maxChars) {
        this.maxFiles = maxFiles;
        this.maxChars = maxChars;
        queue = new ConcurrentLinkedQueue<String>();
    }
    
    public synchronized void  registerWriter() {
        registerCount++;    
    }
    
    public synchronized void unregisterWriter() {
        registerCount--;
        if(registerCount == 0){
            closed = true;
            notify();
        }
    }
    
    public synchronized void addContents(String contents, String identificador) {
        System.out.println(identificador + " intentando añadir contenido");
        while (queue.size() == maxFiles || (!queue.isEmpty() && ((contents.length() + numberChars) > maxChars))){
            try{
                System.out.println(identificador + " esperando añadir contenido");
                wait();
            }catch (InterruptedException e){}
        }
        queue.add(contents);
        System.out.println(identificador + " añade contenido");
        numberChars += contents.length();
        notify();    
    }
    
    public synchronized String getContents(String identificador) {
        System.out.println(identificador + " intentando conseguir contenido");
        while(queue.isEmpty() && !closed){
            try{
                System.out.println(identificador + " esperando conseguir contenido");
                wait();
            } catch (InterruptedException e){}
        }
        if(queue.isEmpty()){
            System.out.println(identificador + " no consigue contenido");
            return null;
        } else {
           String contenido = queue.poll();
           System.out.println(identificador + " consigue contenido");
           numberChars -= contenido.length();
           notifyAll();
           return contenido;
        }
        
    }
}
