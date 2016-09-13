package de.exb.interviews.shalabi.storage;

import de.exb.interviews.shalabi.api.storage.AuthorizationException;
import de.exb.interviews.shalabi.api.storage.AuthorizationService;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.api.service.*;
import de.exb.interviews.shalabi.api.storage.Folder;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class StorageServiceImp implements StorageService {
    //@Inject
    private FileService fileService;

    private AuthorizationService authorizationService;

    private StorageServiceImp() {
    }
    public static StorageService create(@NotNull AuthorizationService authorizationService, @NotNull FileService fileService) {
        //This can be avoided through dependency injection.
        StorageServiceImp storage = new StorageServiceImp();
        storage.fileService = fileService;
        storage.authorizationService = authorizationService;

        return storage;
    }
    @Override
    public List<File> list( UserService userService, File folder) throws FileServiceException {
        if(!authorizationService.isAuthorized(FileOperation.list, userService, folder)) {
            //TODO: should we return an empty list or throw an exception.
            return new ArrayList<File>();
        }
        return folder.list();
    }

    @Override
    /**
     * add a new file or folder to current path.
     */
    public boolean add(@NotNull UserService userService, @NotNull File file) throws FileServiceException, AuthorizationException {
        if(!authorizationService.isAuthorized(FileOperation.Add, userService, file)) {
            throw new AuthorizationException("Not authorized to do add");
        }

        return file.add();
    }

    @Override
    public void delete(@NotNull UserService userService, @NotNull File file) throws FileServiceException, AuthorizationException {
        if(!authorizationService.isAuthorized(FileOperation.Delete, userService, file)) {
            throw new AuthorizationException("Not authorized to do add");
        }

        file.delete();
    }
}
