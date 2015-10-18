import java.io.File;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *  Represents a client. Is a singleton
 */
public enum ClientData {
    INSTANCE;
    
    public final static int NUM_SERVERS_PER_PAGE = 10;
    
    
    public final static String MAIN_SERVER_IP = "128.61.119.119";
    public final static int MAIN_SERVER_PORT = 300;
    
    
    private int pageNumber = 0;
    
    private ServerData[] servers = new ServerData[NUM_SERVERS_PER_PAGE];
    
    private ClientData() {
        /* ... */
    }
    
    public ServerData getServer(int i) {
        return this.servers[i];
    }
    
    public ClientData getClientData() {
        return INSTANCE;
    }
    
    public static String sendDataToMainServer(String data) {
        try {
            Socket client = new Socket(MAIN_SERVER_IP, MAIN_SERVER_PORT);
            
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(data);
            
            DataInputStream in = new DataInputStream(client.getInputStream());
            String result = in.readUTF();
            
            client.close();
            
            return result;
        } catch(IOException e) {
            return "error";
        }
    }
    
    public static void startUpdateThread() {
        /* ... */
    }
    
    public static void uploadFile(File file, ServerData server) {
        /* ... */
    }
    
    public static void uploadFile(File file,
                                  String key,
                                  ServerData server) {
        /* ... */
    }
    
    public static void updateServers(int pageNumber) {
        /* ... */
    }
    
    public File downloadFile(int fileID,
                             String key,
                             ServerData server) {
        /* ... */
        return null;
    }
}