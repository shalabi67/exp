package de.exb.interviews.shalabi.tests.mocks;

import de.exb.interviews.shalabi.api.service.FileService;
import de.exb.interviews.shalabi.api.service.UserService;
import de.exb.interviews.shalabi.api.storage.AuthorizationService;
import de.exb.interviews.shalabi.storage.StorageService;
import de.exb.interviews.shalabi.storage.StorageServiceImp;

/**
 *
 */
public class StorgaeServiceMock {
    public static StorageService create(AuthorizationService authorizationService, FileService fileService) {
        return StorageServiceImp.create(authorizationService, fileService);
    }
}
