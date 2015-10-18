import java.io.File;

/**
 *  Represents a client. Is a singleton
 */
public enum ClientData {
    INSTANCE;
    
    public final static int NUM_SERVERS_PER_PAGE = 10;
    
    private ServerData[] servers = new ServerData[NUM_SERVERS_PER_PAGE];
    
    public ServerData getServer(int i) {
        return this.servers[i];
    }
    
    public ClientData getClientData() {
        return INSTANCE;
    }
    
    private ClientData() {
        /* ... */
    }
    
    public static void startUpdateThread() {
        /* ... */
    }
    
    public static void uploadFile(File file, ServerData... servers) {
        /* ... */
    }
    
    public static void updateServers(int pageNumber) {
        /* ... */
    }
    
    public File downloadFile(int fileID, ServerData... servers) {
        /* ... */
        return null;
    }
}