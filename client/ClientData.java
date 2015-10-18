import java.io.File;
import java.net.Socket;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONObject;
//import javax.crypto.*;

/**
 *  Represents a client. Is a singleton
 */
public enum ClientData {
    INSTANCE;
    
    public final static int NUM_SERVERS_PER_PAGE = 10;
    
    
    public final static String MAIN_SERVER_IP = "128.61.119.119";
    public final static int MAIN_SERVER_PORT = 3000;
    
    
    private int pageNumber = 0;
    
    private ServerData[] servers = new ServerData[NUM_SERVERS_PER_PAGE];
    
    private ClientData() {
        /* ... */
    }
    
    public ServerData getServer(int i) {
        return this.servers[i];
    }
    
    public static ClientData getClientData() {
        return INSTANCE;
    }
    
    //
    public static String sendDataToMainServer(String data) {
        try {
            //Socket client = new Socket(MAIN_SERVER_IP, MAIN_SERVER_PORT);
            
            //DataOutputStream out = new DataOutputStream(client.getOutputStream());
            //out.writeUTF(data);
            
            //DataInputStream in = new DataInputStream(client.getInputStream());
            //String result = in.readUTF();
            
            //client.close();
            
            //return result;
            
            HttpURLConnection conn
                = (HttpURLConnection) new URL("http://"
                    + MAIN_SERVER_IP + ":" + MAIN_SERVER_PORT
                    + "/servers/servers/?" + data)
                        .openConnection();
            
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(500);
            
            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        } catch(IOException e) {
            return e.toString();
        }
    }
    
    /**
     *  @return the new file ID on this server
     */
    public static String uploadFile(File file, ServerData server)
            throws IOException, UnsupportedOperationException,
                java.text.ParseException {
        if (file.length() > server.getAvailableBytes()) {
            throw new UnsupportedOperationException(
                "File won't fit on server!");
        }
        
        byte[] arr = new byte[1024 * 8];
        
        try(Socket socket = new Socket(server.getIPAddress(),
                server.getPort());
            DataInputStream inputStream = new DataInputStream(
                new FileInputStream(file));

            DataOutputStream outputStream = new DataOutputStream(
                socket.getOutputStream());
            DataInputStream socketInputStream = new DataInputStream(
                socket.getInputStream())) {
        
            outputStream.write("FUPLOAD".getBytes(), 0, 7);
                    
            outputStream.write(ByteBuffer
                .allocate(Long.SIZE / Byte.SIZE)
                    .putLong(file.length()
                        + file.getName().length() + 1).array(),
                    0, Long.SIZE / Byte.SIZE);
        
            outputStream.write(
                (file.getName() + "\n")
                    .getBytes(),
                0, file.getName().length() + 1);
            
            int len = 0;
            while ((len = inputStream.read(arr)) != -1) {
                outputStream.write(arr, 0, len);
            }
        
            return socketInputStream.readUTF();
        }
    }
    
    public static void uploadFile(File file,
                                  String key,
                                  ServerData server) {
        /* ... */
    }
    
    public void updateServers(int pageNumber) throws ParseException {
        JSONArray serversData = new JSONArray(sendDataToMainServer("page=" + pageNumber));
        
        for (int i = 0; i < serversData.length() && i < servers.length; i++) {
            JSONObject currServer = serversData.getJSONObject(i);
            
            servers[i] = new ServerData(currServer.getString("serverName"),
                                        currServer.getString("serverIP"),
                                        currServer.getInt("port"),
                                        true, //TODO!
                                        currServer.getInt("availableSpace"),
                                        currServer.getInt("rating"));
        }
    }
    
    public static String getFileName(String fileID,
                              ServerData server) throws IOException {
        try (Socket socket = new Socket(server.getIPAddress(),
                server.getPort());
            DataOutputStream outputStream = new DataOutputStream(
                socket.getOutputStream());
            DataInputStream socketInputStream = new DataInputStream(
                socket.getInputStream())) {
            outputStream.write("NAMEREQ".getBytes(), 0, 7);
            outputStream.writeUTF(fileID);
        
            return socketInputStream.readUTF();
        }
    }
    
    public static void downloadFile(File targetFile,
                                    String fileID,
                                    ServerData server) throws IOException {
        try (Socket socket = new Socket(server.getIPAddress(),
                server.getPort());
                DataOutputStream outputStream = new DataOutputStream(
                    socket.getOutputStream());
                DataInputStream socketInputStream = new DataInputStream(
                    socket.getInputStream());
                FileOutputStream output = new FileOutputStream(targetFile)) {
            outputStream.write("DOWNREQ".getBytes(), 0, 7);
            outputStream.writeUTF(fileID);
        
            int bytesRead;
            
            long size = socketInputStream.readLong();
                    
            byte[] buffer = new byte[1024 * 8];

            // Build new file
            while (size > 0
                    && (bytesRead = socketInputStream.read(buffer, 0,
                        (int) Math.min(buffer.length,
                            size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
        }
    }
    
    public static void downloadFile(File targetFile,
                                    String fileID,
                                    String key,
                                    ServerData server) {
        /* ... */
    }
}