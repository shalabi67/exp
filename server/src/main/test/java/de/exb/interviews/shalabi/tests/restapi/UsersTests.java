package de.exb.interviews.shalabi.tests.restapi;

import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.api.users.User;
import de.exb.interviews.shalabi.resources.FileServiceResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class UsersTests extends JerseyTest {
    public static final String API_FOLDERS = "/api/folders";

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(FileServiceResource.class);
    }

    @Test
    public void getUsers(){
        final Response response  = target(API_FOLDERS + "/users").request().get();
        List<User> users = response.readEntity(new GenericType<List<User>>() {});

        Assert.assertNotNull(users);
        Assert.assertEquals(response.getStatus(), 200);
    }
    @Test
    public void getUser(){
        final Response response  = target(API_FOLDERS + "/users/1").request(MediaType.APPLICATION_JSON).get();
        User user = response.readEntity(User.class);

        Assert.assertNotNull(user);
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void getFileUsers() throws FileServiceException{
        String path = "testFile"+ UUID.randomUUID().toString() +".dat";
        File file = File.createFile(new java.io.File(path));
        file.add();

        final Response response  = target(API_FOLDERS + "/" + path + "/users" ).request().get();
        List<User> users = response.readEntity(new GenericType<List<User>>() {});

        Assert.assertNotNull(users);
        Assert.assertEquals(response.getStatus(), 200);
    }
}
