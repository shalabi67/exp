package de.exb.interviews.shalabi;

import de.exb.interviews.shalabi.api.service.FileService;
import de.exb.interviews.shalabi.api.service.FileServiceImpl;
import de.exb.interviews.shalabi.api.storage.AuthorizationService;
import de.exb.interviews.shalabi.api.storage.AuthorizationServiceImp;
import de.exb.interviews.shalabi.storage.StorageService;
import de.exb.interviews.shalabi.storage.StorageServiceImp;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 *
 */
public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(StorageServiceImp.class).to(StorageService.class);
        bind(FileServiceImpl.class).to(FileService.class);
        bind(AuthorizationServiceImp.class).to(AuthorizationService.class);
    }
}
