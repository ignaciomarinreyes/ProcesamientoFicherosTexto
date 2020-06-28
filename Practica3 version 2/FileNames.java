package textprocessing;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileNames {
    private Queue<String> queue;
    private boolean noMoreNames = false;
    
    public FileNames(){
        queue = new ConcurrentLinkedQueue<String> ();
    }
    
    public synchronized void addName(String fileName) {
        queue.add(fileName);
        System.out.println("Main añade una ruta");
        notify();
    }
    
    public synchronized String getName(String identificador) {
        System.out.println(identificador + " intentando conseguir una ruta");
        while(queue.isEmpty() && !noMoreNames){
            try{
                 System.out.println(identificador + " esperando conseguir una ruta");
                 wait();    
            } catch(InterruptedException e){}
        }
        if(queue.isEmpty()){
            System.out.println(identificador + " no consigue una ruta");
            return null;
        } else {
            String ruta = queue.poll();
            System.out.println(identificador + " consigue una ruta");
            notify();
            return ruta;
        }
    }
    
    public synchronized void noMoreNames() {
        noMoreNames = true;
        notify();
    }
}