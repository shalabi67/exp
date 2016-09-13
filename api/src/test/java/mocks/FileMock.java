package mocks;

import de.exb.interviews.shalabi.api.service.FileService;
import de.exb.interviews.shalabi.api.service.FileServiceImpl;
import de.exb.interviews.shalabi.api.storage.Folder;
import org.junit.Assert;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 *
 */
public class FileMock {

    public static File create(String path) {
        File file = Mockito.mock(File.class);
        when(file.getPath()).thenReturn(path);

        return file;
    }
    private static File create(String path, boolean isFile) {
        File file = create(path);
        when(file.isFile()).thenReturn(isFile);
        when(file.isDirectory()).thenReturn(!isFile);

        return file;
    }
    public static File createFile(String path) {
       return create(path, true);
    }
    public static File createFolder(String path) {
        return create(path, false);
    }

    public static File createForList(String path, File[] files) {
        File file = createFolder(path);
        when(file.listFiles()).thenReturn(files);
        return file;
    }
    public static File createFileAdd(String path, boolean result){
        File file = createFile(path);
        try {
            when(file.createNewFile()).thenReturn(result);
        } catch(IOException e){
            Assert.fail();
        }
        return file;
    }
    public static File createFolderAdd(String path, boolean result) {
        File file = createFolder(path);
        when(file.mkdir()).thenReturn(result);

        return file;
    }
    public static File createFileAddException(String path, boolean result) throws  IOException{
        File file = createFile(path);
        doThrow(new IOException()).when(file).createNewFile();

        return file;
    }
}
