package textprocessing;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileNames {

    private Queue<String> paths = new ConcurrentLinkedQueue<String>();
    private boolean noMoreNames = false;

    public synchronized void addName(String fileName) {
        paths.add(fileName);
        notifyAll();
    }

    public synchronized String getName() {
        while (paths.isEmpty() && !noMoreNames) { // la cola puede estar vacia porque el main no añade más, o porque los filereader han leido más rapido que lo que el main ha añadido, entonces hay que esperar, dormir filereader. Si el main acaba de añadir no se tienen que dormir más filereader, ya que no hay Main para que los despierte por eso hay que poner NoMoreNames verdadero, para que no duerma más filereader. Puede darse el caso, de que el main ha terminado hizo el notifyAll, a continuación se duerme un file reader ya que está vació la cola vacía y no se ha puesto el NoMoreNames, en lo que hizo el último notifyAll y llega a activar NoMoreNames se puede meter un fileReader a dormir y no hay ningun Main pra despertarlo. Por, eso desde que se activia NoMoreNames, despues hay que hacer un interrup para despertar al filereader dormido que se metio en ese peridood breve de tiempo. Esto ocurre, porque el número de operaciones es distinto el main ejecuta 100 inserciones de ficheros de texto por ejemplo, y los fileReader realizan aleatoriamente cogidas de ficheros. Por tanto, el main despertara a 100 file readers, pero en ese tiempo hasta que cierra el NoMoreNames puede meterse un filereaderm ás que no podría despertar.
            try {
                wait();
            } catch (InterruptedException ex) {
                return null;
            }
        }
        return paths.poll();
    }

    public void noMoreNames() {
        noMoreNames = true;
        Main.interruptFileReaders();
    }
}
