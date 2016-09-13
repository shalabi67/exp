package de.exb.interviews.shalabi.client;

/**
 * simple client that list home directory folders. this is exactly the same as unit test. have a look at unit test
 * which covers al the cases.
 */
public class ClientApp {
    public static final String API_FOLDERS = "http://localhost/application/api/folders";
    public static void main(String[] args) {
        FolderResource folder = new FolderResource();
        folder.getHomeFolder();

    }
}
