package de.exb.interviews.shalabi.api.storage;

import de.exb.interviews.shalabi.api.service.FileOperation;
import de.exb.interviews.shalabi.api.service.UserService;
import de.exb.interviews.shalabi.api.storage.File;

import javax.validation.constraints.NotNull;

/**
 * Is the specified user is authorized to do a specified operation of a file or folder.
 */
public interface AuthorizationService {
    boolean isAuthorized(@NotNull FileOperation fileOperation, @NotNull UserService userService, @NotNull File file);
}
