package de.exb.interviews.shalabi.tests.mocks;

import de.exb.interviews.shalabi.api.service.UserService;
import org.mockito.Mockito;

/**
 *
 */
public class UserServiceMock {
    public static UserService create() {
        return Mockito.mock(UserService.class);
    }
}
