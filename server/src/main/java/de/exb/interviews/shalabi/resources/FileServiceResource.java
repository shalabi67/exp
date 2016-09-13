package de.exb.interviews.shalabi.resources;

import de.exb.interviews.shalabi.api.Logger;
import de.exb.interviews.shalabi.api.service.*;
import de.exb.interviews.shalabi.api.storage.*;
import de.exb.interviews.shalabi.storage.StorageService;
import de.exb.interviews.shalabi.storage.StorageServiceImp;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/api/folders")
public class FileServiceResource {
    //@Inject
    //StorageService storageService;

    @Path("/users")
    public UserServiceResource getUserResource() {
        File file = File.createFolder(new java.io.File("./"));
        return new UserServiceResource(file);
    }
    @Path("/{path}/users")
    public UserServiceResource getUserResource(@PathParam("path") String path) {
        File file = File.create(new java.io.File(path));
        return new UserServiceResource(file);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<File> list()  {
        return getFiles(null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@NotNull File file) {
        return addFile("", file);
    }

    @GET
    @Path("/{path}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<File> listSubDirectory(@PathParam("path") String path){
        return getFiles(path);
    }

    @POST
    @Path("/{path}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@PathParam("path") String path, @NotNull File file) throws FileServiceException{
        return addFile(path, file);
    }

    @DELETE
    @Path("/{path}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("path") String path) throws FileServiceException{
        return deleteFile(path);
    }


    private List<File> getFiles(@QueryParam("path") String path) {
        try {
            StorageService storageService = StorageServiceImp.create(new AuthorizationServiceImp(), new FileServiceImpl());
            File folder;
            if (path == null || path.isEmpty()) {
                Logger.LogInfo("use home directory");
                folder = Folder.createHome();
            } else {
                //String actualPath = Folder.decodePath(path); no need to decode
                folder = File.create(path);
            }

            List<File> files = storageService.list(new UserServiceImpl(), folder);
            return files;
        } catch(FileServiceException e) {
            //TODO: we need to change the method signiture to responce and convert the Lis<File> to json then include
            // it in the responce budy in case of success like Response.ok(json, MediaType.APPLICATION_JSON).build();
            // in case of error return Response.status(409).entity("resource exists").build();

            return new ArrayList<File>();
        }
    }

    private Response addFile(@NotNull String path, @NotNull File file) {
        try {
            if(!path.isEmpty()) {
                path += "./";
                file.setPath(path + file.getPath());
            }

            StorageService storageService = StorageServiceImp.create(new AuthorizationServiceImp(), new FileServiceImpl());
            if(storageService.add(new UserServiceImpl(), file)) {
                return Response.status(201).entity("success").build();
            }else {
                return Response.status(409).entity("resource exists").build();
            }
        }catch(AuthorizationException e) {
            return  Response.status(401).entity(e.getMessage()).build();
        }catch(FileServiceException e1) {
            return  Response.status(404).entity(e1.getMessage()).build();
        }

    }

    private Response deleteFile(@NotNull String path) {
        File file = File.create(new java.io.File(path));

        try {
            StorageService storageService = StorageServiceImp.create(new AuthorizationServiceImp(), new FileServiceImpl());
            storageService.delete(new UserServiceImpl(), file);
            return Response.status(200).entity("success").build();
        }catch(AuthorizationException e) {
            return  Response.status(401).entity(e.getMessage()).build();
        }catch(FileServiceException e1) {
            return  Response.status(404).entity(e1.getMessage()).build();
        }
    }

}
