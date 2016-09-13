package de.exb.interviews.shalabi.api.storage;

import de.exb.interviews.shalabi.api.service.FileOperation;
import de.exb.interviews.shalabi.api.service.UserService;

import javax.validation.constraints.NotNull;

/**
 *
 */
public class AuthorizationServiceImp implements AuthorizationService  {
    @Override
    public boolean isAuthorized(FileOperation fileOperation, UserService userService, File file) {
        return true;
    }
}
