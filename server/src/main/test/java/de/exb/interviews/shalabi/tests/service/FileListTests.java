package de.exb.interviews.shalabi.tests.service;
import de.exb.interviews.shalabi.api.service.*;
import de.exb.interviews.shalabi.api.storage.AuthorizationService;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.api.storage.Folder;
import de.exb.interviews.shalabi.storage.StorageService;
import de.exb.interviews.shalabi.storage.StorageServiceImp;
import de.exb.interviews.shalabi.tests.mocks.AuthorizationServiceMock;
import de.exb.interviews.shalabi.tests.mocks.FileServiceMock;
import de.exb.interviews.shalabi.tests.mocks.UserServiceMock;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.Validator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * This class will provide the following test cases:
 * Empty list of files.
 * null list of files
 * Only files and no directories.
 * Only directories and no files.
 * Mix of directories and files.
 * Non exiting path.
 * Invalid Path
 *
 * It will not provide any testing for Authorization or Authentication.
 * @author mohammad
 */
public class FileListTests {
    static UserService userService;
    private static Validator validator;
    @BeforeClass
    public static void setup() {
        userService = UserServiceMock.create();

    }
    /*
    This will test list functionality that returns an empty list
     */
    @Test
    public void testEmptyList() throws MalformedURLException, FileServiceException {
        Folder folder = Folder.createHome();

        FileService fileService = FileServiceMock.createForList(folder, new ArrayList<File>());
        AuthorizationService authorizationService = AuthorizationServiceMock.create(FileOperation.list, userService, folder, true);

        StorageService storageService = StorageServiceImp.create(authorizationService, fileService);
        List<File> files = storageService.list(userService, folder);
        Assert.assertEquals(0, files.size());

    }

    //TODO:continue the remaining test cases
    @Test
    public void testNullValues() throws MalformedURLException, FileServiceException{
        Folder folder = Folder.createHome();
        FileService fileService = new FileServiceImpl();



        fileService.list(null);
    }
}
