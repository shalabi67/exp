package de.exb.interviews.shalabi.tests.restapi;

import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.resources.FileServiceResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * This will test POST operation on API_FOLDERS and API_FOLDERS/{path}
 * Test adding files and folders. it test the following cases:
 * add new file
 * add new folder
 * add exiting file
 * add exiting folder
 *
 * add new file to specific path
 */
public class AddTests  extends JerseyTest {

    public static final int EXISING_RESULT = 409;
    public static final int CREATED_RESULT = 201;
    public static final String API_FOLDERS = "/api/folders";

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(FileServiceResource.class);
    }

    @Test
    public void addFile(){
        File file = File.createFile(new java.io.File("testFile"+ UUID.randomUUID().toString() +".dat"));
        addFile(API_FOLDERS, file, CREATED_RESULT);
    }
    @Test
    public void addFolder(){
        File file = File.createFolder(new java.io.File("testFolder"+ UUID.randomUUID().toString()));
        addFile(API_FOLDERS, file, CREATED_RESULT);
    }
    @Test
    public void addExitingFile(){
        File file = File.createFolder(new java.io.File("testFolder"+ UUID.randomUUID().toString()));
        addFile(API_FOLDERS, file, CREATED_RESULT);
        addFile(API_FOLDERS, file, EXISING_RESULT);
    }
    @Test
    public void addExitingFolder(){
        File file = File.createFolder(new java.io.File("testFolder"+ UUID.randomUUID().toString()));
        addFile(API_FOLDERS, file, CREATED_RESULT);
        addFile(API_FOLDERS, file, EXISING_RESULT);
    }

    @Test
    public void addFileToPath() throws FileServiceException{
        String path = "testFolder"+ UUID.randomUUID().toString();
        File file = File.createFolder(new java.io.File(path));
        file.add();

        file = File.createFile(new java.io.File("testFile"+ UUID.randomUUID().toString() +".dat"));
        addFile(API_FOLDERS + "/" + path, file, CREATED_RESULT);
    }

    @Test
    public void addFolderToPath() throws FileServiceException{
        String path = "testFolder"+ UUID.randomUUID().toString();
        File file = File.createFolder(new java.io.File(path));
        file.add();

        file = File.createFolder(new java.io.File("testFolder"+ UUID.randomUUID().toString()));
        addFile(API_FOLDERS + "/" + path, file, CREATED_RESULT);
    }

    private void addFile(String path, File file, int expectedResult) {
        Entity<File> fileEntity = Entity.entity(file, MediaType.APPLICATION_JSON);

        final Response response  = target(path).request().post(fileEntity);

        Assert.assertEquals(response.getStatus(), expectedResult);
    }
}
