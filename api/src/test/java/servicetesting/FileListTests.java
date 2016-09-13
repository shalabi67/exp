package servicetesting;

import de.exb.interviews.shalabi.api.service.FileService;
import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.service.FileServiceImpl;
import de.exb.interviews.shalabi.api.storage.File;
import helpers.Comparator;
import mocks.FileMock;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


/**
 * This will test FileService list operation. it will cover the following test cases:
 * return a mix list of files and folders.
 *
 * more test can be added but this enough to give you an idea.
 */
public class FileListTests {

    @BeforeClass
    public static void setup() {
    }
    @Test
    public void testValidBath() throws FileServiceException {
        java.io.File fileMock = FileMock.createForList("", validMix);
        List<File> files = fileService.list(fileMock);

        Assert.assertTrue(Comparator.areSame(files, validMix));
    }

    private static java.io.File[] createIOMex() {

        java.io.File[] files = Stream.concat(Arrays.stream(createIOFiles(FileListTests.files)), Arrays.stream(createIOFolders(folders)))
                .toArray(java.io.File[]::new);

        return files;

    }
    private static java.io.File[] createIOFiles(String[] paths) {
        java.io.File[] files = new java.io.File[paths.length];
        for (int i=0;i<paths.length;i++) {
            files[i] = FileMock.createFile(paths[i]);
        }

        return files;
    }
    //TODO: we could use lamda for createIOFiles and createIOFolders
    private static java.io.File[] createIOFolders(String[] paths) {
        java.io.File[] files = new java.io.File[paths.length];
        for (int i=0;i<paths.length;i++) {
            files[i] = FileMock.createFolder(paths[i]);
        }

        return files;
    }

    static String[] files = {"a.dat", "b.dat", "c.dat"};
    static String[] folders = {"./", "home", "tests"};
    FileService fileService = new FileServiceImpl();
    static  java.io.File[] validMix = createIOMex();
}
