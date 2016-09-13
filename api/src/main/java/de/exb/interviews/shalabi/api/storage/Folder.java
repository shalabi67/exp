package de.exb.interviews.shalabi.api.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.exb.interviews.shalabi.api.Logger;
import de.exb.interviews.shalabi.api.service.FileServiceException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a folder class. folder class re implements file operations and includes a set of files.
 */
@JsonTypeName("Folder")
public class Folder extends File {
    //-------------- creation
    private Folder() throws FileServiceException {
        files = new ArrayList<File>();
    }

    private Folder(@NotNull final String path)  throws FileServiceException{
        super(path);
        files = new ArrayList<File>();
        Logger.LogInfo("Folder with Path:" + path);
    }
    protected Folder(@NotNull final java.io.File file) {
        super(file);
        files = new ArrayList<File>();
    }

    public static Folder createHome() throws FileServiceException {
        return new Folder();
    }
    public static Folder create(@NotNull final String path) throws FileServiceException  {
        return new Folder(path);
    }



    //-------------- file operations
    public boolean add() throws FileServiceException {
        return fileService.mkdir(file);
    }
    public List<File> list() throws FileServiceException {
        files = fileService.list(file);
        return files;
    }
    //TODO:we can add recursive as parameter here
    public void delete() throws FileServiceException {
        fileService.delete(file, true);
    }



    //----------- properties
    private List<File> files;
    public List<File> getFiles() {
        return files;
    }
}
