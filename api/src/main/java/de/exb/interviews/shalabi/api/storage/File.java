package de.exb.interviews.shalabi.api.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.exb.interviews.shalabi.api.Logger;
import de.exb.interviews.shalabi.api.service.FileService;
import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.service.FileServiceImpl;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a composite design patter that build File and Folder relationship.
 * @author mohammad
 */
//@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = Folder.class)})
public class File {
    public static final String homeDirectory = "./";
    protected static final String protocol = "file:";

    //-----------------------  creation
    protected File() throws FileServiceException{
        path = homeDirectory;
        file = FileServiceImpl.create(getUrl());
    }
    protected File(@NotNull final String path) throws FileServiceException  {
        this.path = path;
        file = FileServiceImpl.create(getUrl());
    }
    protected File(@NotNull final java.io.File file) {
        this.file = file;
        path = this.file.getPath();
        Logger.LogInfo("File path:" + path);
    }

    public static File create(@NotNull final String path) throws FileServiceException  {
        String actualPath = File.decodePath(path);
        return create(new java.io.File(actualPath));
    }

    /**
     * This method will create either a Folder or File object depending on if the java.io.File is a file or directory
     * this help us to mock File. this could be replaced by Debendancy Injection
     */
    public static File create(@NotNull final java.io.File file)  {
        if(file.isFile()) {
            return createFile(file);
        }
        else {
            return createFolder(file);
        }
    }
    public static File createFile(@NotNull final java.io.File file)  {
        return new File(file);
    }
    public static File createFolder(@NotNull final java.io.File file)  {
        return new Folder(file);
    }



    //-------------- file operations
    public boolean add() throws FileServiceException {
        return fileService.createNewFile(file);
    }
    public void delete() throws FileServiceException {
        fileService.delete(file, false);
    }
    public List<File> list() throws FileServiceException {
        ArrayList<File> files = new ArrayList<File>();
        files.add(this);
        return files;
    }
    public static String decodePath(final String path) throws FileServiceException {
        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new FileServiceException("cannot decode path", e);
        }
    }




    //----------------------   Properties
    @JsonIgnore
    public URL getUrl() throws FileServiceException {
        try {
            return new URL(protocol + path);
        }catch(MalformedURLException e) {
            throw new FileServiceException("Invalid URL", e);
        }
    }



    private String  path;
    public String getPath() {
        return path;
    }
    public void setPath(String path) throws FileServiceException {
        this.path = path;
        file = FileServiceImpl.create(getUrl());
    }

    protected java.io.File file;


    //this will be replaced by DI.
    public static FileService fileService = new FileServiceImpl();


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        File file = (File) o;
        return path.equals(file.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
