package textprocessing;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileContents {

    private final Queue<String> queue;
    private int registerCount = 0;
    private final int maxFiles;
    private final int maxChars;
    private int numChars;

    public FileContents(int maxFiles, int maxChars) {
        this.maxFiles = maxFiles;
        this.maxChars = maxChars;
        queue = new ConcurrentLinkedQueue<String>();
    }

    public synchronized void registerWriter() {
        registerCount++;
    }

    public synchronized void unregisterWriter() {
        registerCount--;
        if (registerCount == 0) {
            Main.interruptFileProcessors(); // En getContens puede darse el caso de que la cosa está vacía y register Count es distinto a cero, se duerme fileproccesor, pero el file reader ya habia notificado, esta haciendo el unregister, pone ahora el unregister a 0 para que no se duerma nadie más, pero ya hay uno dormido, hay que despertarlo con el interrupt
        }
    }

    public synchronized void addContents(String contents) {
        while ((queue.size() == maxFiles) || (!queue.isEmpty() && ((numChars + contents.length()) >= maxChars))) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        queue.add(contents);
        numChars += contents.length();
        notifyAll();
    }

    public synchronized String getContents() {
        while ((queue.isEmpty()) && !(registerCount == 0)) { // la cola puede estar vacía porque se acabo y no hay más para añadir, o por los fileproccesor han consumido más rapido que los filereader añaden, hay que esperar. Por eso, si llegamos al final que los filereader han terminado la cola está vacía pero no hay que esperar más, losfileprocessor tienen que terminar, por eso, el regitserCount se establece a 0 para que no duerman más fileproccesor, ya que no hay ningun filereader que lo despierte, terminaron todos
            try {
                wait();
            } catch (InterruptedException ex) {
                return null;
            }
        }
        String contents = queue.poll();
        if (contents != null) {
            numChars -= contents.length();
        }
        notifyAll();
        return contents;
    }

}
