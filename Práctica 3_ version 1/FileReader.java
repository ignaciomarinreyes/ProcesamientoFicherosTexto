package textprocessing;

public class FileReader extends Thread {

    private final FileNames fileName;
    private final FileContents fileContent;

    public FileReader(FileNames fn, FileContents fc) {
        this.fileName = fn;
        this.fileContent = fc;
    }

    @Override
    public void run() {
        String path;
        fileContent.registerWriter();
        while ((path = fileName.getName()) != null) {
            fileContent.addContents(Tools.getContents(path));
        }
        fileContent.unregisterWriter();
    }
}
