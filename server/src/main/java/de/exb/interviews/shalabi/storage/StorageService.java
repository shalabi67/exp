package de.exb.interviews.shalabi.storage;

import de.exb.interviews.shalabi.api.storage.AuthorizationException;
import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.api.service.UserService;
import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.storage.Folder;
import org.glassfish.jersey.spi.Contract;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 */
@Contract
public interface StorageService {
    public List<File> list(@NotNull UserService userService, @NotNull final File file) throws FileServiceException;
    public boolean add(@NotNull UserService userService, @NotNull final File file) throws FileServiceException, AuthorizationException;
    public void delete(@NotNull UserService userService, @NotNull final File file) throws FileServiceException, AuthorizationException;
}
