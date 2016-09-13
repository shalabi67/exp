package de.exb.interviews.shalabi.resources;

import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.api.users.User;


import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * This will identify the user resources, permission and attache them to Folders and files resources.
 */
public class UserServiceResource {
    public UserServiceResource(File file) {
        this.file = file;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> list()  {
        return new ArrayList<User>();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@NotNull User user){
        return Response.status(401).entity("not implemented").build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") String userId)  {
        return new User();
    }

    @POST
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@PathParam("userId") String userId, @NotNull User user) {
        return Response.status(401).entity("not implemented").build();
    }

    @DELETE
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("userId") String userId) throws FileServiceException{
        return Response.status(401).entity("not implemented").build();
    }

    File file;

}
