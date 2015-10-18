import java.util.regex.Pattern;
import java.text.ParseException;

/**
 *  Represents a server object
 */
public class ServerData {
    
    private static final Pattern IP_PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    
    private final String name, IPAdress;
    private final int port;
    
    private long availableBytes;
    private int rating;
    private boolean isOnline;
    
    public ServerData(String name,
                      String IPAdress,
                      int port,
                      boolean isOnline,
                      long availableBytes,
                      int rating) throws ParseException {
        //if (!IP_PATTERN.matcher(IPAdress).matches()) {
        //    throw new ParseException("Invalid IP!", 0);
        //}
        
        this.name = name;
        this.IPAdress = IPAdress;
        this.port = port;
        this.availableBytes = availableBytes;
        this.rating = rating;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIPAddress() {
        return this.IPAdress;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public boolean isOnline() {
        return this.isOnline;
    }
    
    public long getAvailableBytes() {
        return availableBytes;
    }
    
    public String getAvailableStorage() {
        String[] suffixes
            = new String[] { "B", "KB", "MB", "GB", "TB" };
        long divisor = 1;
        
        for (int i = 0;
             i < suffixes.length - 1;
             i++, divisor *= 1024) {
            if (availableBytes / (divisor * 1024) == 0) {
                return availableBytes / divisor + " " + suffixes[i];
            }
        }
        
        return availableBytes / divisor
            + " " + suffixes[suffixes.length - 1];
    }
    
    public int getRating() {
        return this.rating;
    }
    
    public void updateData(boolean isOnline,
                           long availableBytes,
                           int rating) {
        this.isOnline = isOnline;
        this.availableBytes = availableBytes;
        this.rating = rating;
    }
    
    public void rate(int rating) {
        ClientData.sendDataToMainServer("rating=" + rating);
    }
}