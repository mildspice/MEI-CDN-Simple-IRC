import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileManagement {
    private final List<String> fileList = new ArrayList<>();

    public void addFile(String file) {
        fileList.add(file);
    }

    public List<String> listFiles() {
        return fileList;
    }

    public boolean lookupFile(String filename) {
        Optional<String> bb = fileList.stream().filter(file -> file.equals(filename)).findFirst();
        System.out.println(bb);
        return bb.equals(Optional.of(filename));
    }

}
