package de.exb.interviews.shalabi.client;



import de.exb.interviews.shalabi.api.storage.File;
import de.exb.interviews.shalabi.api.storage.Folder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class FolderResource {
    Client client;
    public FolderResource() {
        client = ClientBuilder.newClient(  );
    }
    public void getHomeFolder() {
        List<File> files = client.target(ClientApp.API_FOLDERS).request(MediaType.APPLICATION_JSON).get(new GenericType<List<File>>() {});

        printList(files);
    }

    private void printList(List<File> files) {
        for (File file : files) {
            System.out.println(file.getPath());
        }
    }
}
