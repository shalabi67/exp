package de.exb.interviews.shalabi.tests.mocks;

import de.exb.interviews.shalabi.api.service.FileOperation;
import de.exb.interviews.shalabi.api.service.UserService;
import de.exb.interviews.shalabi.api.storage.AuthorizationService;
import de.exb.interviews.shalabi.api.storage.File;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 *
 */
public class AuthorizationServiceMock {
    public static AuthorizationService create(FileOperation fileOperation, UserService userService, File file,  boolean isAuthoritzed) {
        AuthorizationService authorizationService = Mockito.mock(AuthorizationService.class);
        when(authorizationService.isAuthorized(fileOperation, userService, file)).thenReturn(true);

        return authorizationService;
    }
}
