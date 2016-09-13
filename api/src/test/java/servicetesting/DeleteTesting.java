package servicetesting;

import de.exb.interviews.shalabi.api.service.FileServiceException;
import de.exb.interviews.shalabi.api.storage.File;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

/**
 *
 */
public class DeleteTesting {
    @Test
    public void testAddNewFile() throws FileServiceException, IOException {
        //TODO: I am not writing any test here. this is just to show the ability to call without using mocking.
        File file = File.createFile(new java.io.File("testFile"+ UUID.randomUUID().toString() +".dat"));
        file.add();

        file.delete();
    }
}
