/**
 *  Represents a server object
 */
public interface ServerData {
    
    String getIPAddress();
    int getPort();
    boolean isOnline();
    String getAvailableStorage();
    void rate(int rating);
}