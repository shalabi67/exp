package helpers;

import de.exb.interviews.shalabi.api.storage.File;

import java.util.List;

/**
 *
 */
public class Comparator {
    public static boolean areSame(List<File> files, java.io.File[] ioFiles) {
        int i = 0;
        for (File file: files) {
            if(file.getPath() != ioFiles[i].getPath())
                return false;
            i++;
        }
        return true;
    }
}
