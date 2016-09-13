package servicetesting;

import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.storage.File;
import helpers.Comparator;
import mocks.FileMock;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static de.exb.interviews.shalabi.api.storage.File.fileService;

/**
 * this will test the folowing use cases
 * adding new file
 * adding new directory
 * adding exiting file
 * adding exiting directory
 * FileServiceException is thrown
 */
public class AddFileTests {
    @Test
    public void testAddNewFile() throws FileServiceException, IOException {
        //file not exist test
        testAdd(true);

        //file not exist test
        testAdd(false);
    }
    @Test
    public void testAdd() throws FileServiceException, IOException {
        for(java.io.File file : validData) {
            testAdd(true, file);
        }
    }
    @Test(expected=FileServiceException.class)
    public void testAdNewFileException() throws FileServiceException, IOException {
        testAdd(true, FileMock.createFileAddException("", true));
    }

    private void testAdd(boolean expectedResult) throws IOException {
        java.io.File fileMock = FileMock.createFileAdd("", expectedResult);
        testAdd(expectedResult, fileMock);
    }

    private void testAdd(boolean expectedResult, java.io.File fileMock) throws FileServiceException {
        File file = File.create(fileMock);
        boolean result = file.add();
        Assert.assertEquals(expectedResult, result);
    }

    java.io.File[] validData = {FileMock.createFileAdd("", true),
            FileMock.createFileAdd("a.dat", true),
            FileMock.createFileAdd("a12.jsf", true),
            FileMock.createFolderAdd("aa", true),
            FileMock.createFolderAdd("bb", true)};



}
