import java.util.ArrayList;
import java.util.List;

public class FileManagement {
    private final List<String> fileList = new ArrayList<>();

    public void addFile(String file) {
        fileList.add(file);
    }

    public List<String> listFiles() {
        return fileList;
    }

    public boolean lookupFile(String filename) {
        return fileList.stream().filter(file -> file.equals(filename)).findFirst().isPresent();
        // System.out.println(bb);
        // return bb.equals(Optional.of(filename));
    }

}
