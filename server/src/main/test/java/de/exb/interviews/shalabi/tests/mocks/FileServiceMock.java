package de.exb.interviews.shalabi.tests.mocks;

import de.exb.interviews.shalabi.api.service.FileService;
import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.service.FileServiceImpl;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.api.storage.Folder;
import org.mockito.Mockito;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 *
 */
public class FileServiceMock {
    private static FileService create() {
        return Mockito.mock(FileService.class);
    }
    public static java.io.File createFile(URL url) throws FileServiceException{
        return FileServiceImpl.create(url);
    }
    public static FileService createForList(Folder folder, List<File> list) throws FileServiceException {
        FileService fileService = FileServiceMock.create();
        java.io.File file  = createFile(folder.getUrl());
        when(fileService.list(file)).thenReturn(list);

        return fileService;
    }
}
