package codeu.chat.util.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angus on 4/6/17.
 */
public abstract class FlatFilePersistence {
    private Path filePath;
    private FileWriter fw;
    private BufferedWriter bw;

    public FlatFilePersistence(String fileLoc) {
        filePath = Paths.get(fileLoc);

        // Check to see if the file exists, and attempt to create it if it
        // doesn't exist.
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
                filePath = Paths.get(fileLoc);

            } catch (IOException e) {
                System.out.println("There is an internal error regarding FileFilePersistence.");
                System.out.println("Persistence through FileFilePersistence will not work until it is resolved.");
                filePath = null;
            }
        }

        // Create the
        if (filePath != null) {
            try {
                fw = new FileWriter(new File(fileLoc), true);

            } catch (IOException e) {
                System.out.println("There is an internal error regarding FileFilePersistence.");
                System.out.println("Persistence through FileFilePersistence will not work until it is resolved.");
                filePath = null;
            }
        }

        if (fw != null) {
            bw = new BufferedWriter(fw);
        }
    }

    /**
     * Checks if the function is ready
     * @return
     */
    public boolean isReady() {
        return filePath != null && fw != null && bw != null;
    }

    public boolean append(String str) {
        if (!isReady()) return false;

        try {
            bw.write(str);
        } catch (IOException e) {
            System.out.println("There is an internal error regarding FileFilePersistence.");
            System.out.println("The BufferedWriter cannot be resolved in append.");
            filePath = null;
        }

        return true;
    }

    public List<String> readAllLines() {
        if (!isReady()) return null;

        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean close() {
        try {
            bw.close();
            fw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
