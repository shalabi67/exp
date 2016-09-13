package de.exb.interviews.shalabi.tests.restapi;

import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.resources.FileServiceResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * this will test DELETE on API_FOLDERS and API_FOLDERS/{path}
 * This will test the following test cases:
 * delete exiting file
 * delete exiting folder
 * delete folder recursive
 * delete file from path
 * delete folder from path
 */
public class DeleteTests extends JerseyTest {

    public static final String API_FOLDERS = "/api/folders/";

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(FileServiceResource.class);
    }

    @Test
    public void deleteFile() throws FileServiceException, UnsupportedEncodingException{
        String path = "testFile"+ UUID.randomUUID().toString() +".dat";
        File file = File.createFile(new java.io.File(path));
        file.add();

        delete(path);
    }

    @Test
    public void deleteFolder() throws FileServiceException, UnsupportedEncodingException{
        String path = "testFile"+ UUID.randomUUID().toString();
        File file = File.createFolder(new java.io.File(path));
        file.add();

        delete(path);
    }

    @Test
    public void deleteRecursiveFolder() throws FileServiceException, UnsupportedEncodingException{
        String path = "testFile"+ UUID.randomUUID().toString();
        createFolderStructure(path);

        delete(path);
    }

    @Test
    public void deleteFolderFromPath() throws FileServiceException, UnsupportedEncodingException{
        String path = "testFile"+ UUID.randomUUID().toString();
        createFolderStructure(path);

        path += "/" + path;
        delete(path);
    }

    @Test
    public void deleteFileFromPath() throws FileServiceException, UnsupportedEncodingException{
        String path = "testFile"+ UUID.randomUUID().toString();
        path = createFolderStructure(path);

        delete(path);
    }


    private String createFolderStructure(String path) throws FileServiceException {
        File file = File.createFolder(new java.io.File(path));
        file.add();

        file = File.createFolder(new java.io.File(path + "/" + path));
        file.add();

        String filePath = path + "/" + path + "/" + path + ".dat";
        file = File.createFile(new java.io.File(filePath));
        file.add();

        return filePath;
    }

    private void delete(String path) throws FileServiceException, UnsupportedEncodingException {
        path = URLEncoder.encode(path, "UTF-8");
        final Response response  = target(API_FOLDERS + path).request().delete();
        Assert.assertEquals(200, response.getStatus());
    }
}
