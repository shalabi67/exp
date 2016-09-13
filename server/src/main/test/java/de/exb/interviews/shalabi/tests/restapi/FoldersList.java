package de.exb.interviews.shalabi.tests.restapi;



import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.resources.FileServiceResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.JerseyTestNg;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;


/**
 * This test will test the following scenarios:
 * list files in current directory
 * list files in a specific path.
 * list files in multi level directory
 * get file
 */
public class FoldersList extends JerseyTest {

    public static final String API_FOLDERS = "/api/folders";
    public static final int EXPECTED_SUCCESS = 200;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(FileServiceResource.class);
    }

    //TODO: we need to do test cleanup.

    /***
     * test get operation on API_FOLDERS
     */
    @Test
    public void listHome(){
        final Response response  = target(API_FOLDERS).request().get();
        List<File> files = response.readEntity(new GenericType<List<File>>() {});

        Assert.assertNotNull(files);
        Assert.assertEquals(EXPECTED_SUCCESS, response.getStatus());
    }

    /***
     * test get operation on API_FOLDERS/{path}
     * where path is the first level folder path
     */
    @Test
    public void listPath() throws FileServiceException{
        String newFolder = "testFolder"+ UUID.randomUUID().toString();
        addFolderWithFile(newFolder);

        processRequest(newFolder);
    }

    /***
     * test get operation on API_FOLDERS/{path}
     * where path is multilevel folder
     */
    @Test
    public void listMultiLevelPath() throws FileServiceException, UnsupportedEncodingException{
        String newFolder = "testFolder"+ UUID.randomUUID().toString();
        addFolderWithFile(newFolder);
        newFolder += "/" + newFolder;
        addFolderWithFile(newFolder);

        newFolder = URLEncoder.encode(newFolder, "UTF-8");
        processRequest(newFolder);
    }

    /***
     * test get operation on API_FOLDERS/{path}
     * where path is file name path
     */
    @Test
    public void getFile() throws FileServiceException {
        String newFile = "testFile"+ UUID.randomUUID().toString() +".dat";
        File file = File.createFile(new java.io.File(newFile));
        file.add();

        processRequest(newFile);
    }

    private void addFolderWithFile(String newFolder) throws FileServiceException {
        File folder = File.createFolder(new java.io.File(newFolder));
        folder.add();
        File file = File.createFile(new java.io.File(newFolder + "/testFile.dat"));
        file.add();
    }
    private void processRequest(String newFolder) {
        final Response response  = target(API_FOLDERS + "/" + newFolder).request().get();
        List<File> files = response.readEntity(new GenericType<List<File>>() {});

        Assert.assertNotNull(files);
        Assert.assertEquals(1, files.size());
        Assert.assertEquals(EXPECTED_SUCCESS, response.getStatus());
    }
}
