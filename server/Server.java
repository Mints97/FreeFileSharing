import java.security.SecureRandom;
import java.math.BigInteger;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.net.URL;
import java.net.HttpURLConnection;

public class Server {
    
    public final static String MAIN_SERVER_IP = "128.61.119.119";
    public final static int MAIN_SERVER_PORT = 3000;
    
    private static SecureRandom random = new SecureRandom();

    private static String nextFileId() {
        return new BigInteger(130, random).toString(32);
    }
    
    private static File createFile() {
        File file;
        
        do {
            file = new File("./FileStorage/" + nextFileId());
        } while (file.exists());
        
        return file;
    }
    
    public static String sendDataToMainServer(String data) {
        try {
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
    
    private static void uploadHandler(DataInputStream clientData,
                                      DataOutputStream response)
                                        throws IOException {
        File newFile = createFile();
        try (FileOutputStream output = new FileOutputStream(newFile)) {
            int bytesRead;
            
            long size = clientData.readLong();
                    
            byte[] buffer = new byte[1024 * 8];

            // Build new file
            while (size > 0
                    && (bytesRead = clientData.read(buffer, 0,
                        (int) Math.min(buffer.length,
                            size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            
            response.writeUTF(newFile.getName());
        }
    }
    
    private static void reportToServer(final int port) {
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (true) {
                    try {
                        sendDataToMainServer("port=" + port + "&updateSpace=" + new File(".").getFreeSpace());
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                }
            }
        }, "report").start();
    }
    
    private static void fileNameHandler(DataInputStream clientData,
                                    DataOutputStream response)
                                        throws IOException {
        File file = new File("./FileStorage/" + clientData.readUTF());
        
        if (!file.exists()) {
            response.writeUTF("");
        }
        
        try (BufferedReader fileReader
            = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)))) {
            response.writeUTF(fileReader.readLine());
        }
    }
    
    private static void downloadHandler(DataInputStream clientData,
                                    DataOutputStream response)
                                        throws IOException {
        File file = new File("./FileStorage/" + clientData.readUTF());
        String fileName;
        
        if (!file.exists()) {
            response.writeUTF("");
        }
        
        try (BufferedReader fileReader
                = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file)));
                DataInputStream inputStream = new DataInputStream(
                    new FileInputStream(file))) {
            fileName = fileReader.readLine();
            
            response.write(ByteBuffer
                    .allocate(Long.SIZE / Byte.SIZE)
                        .putLong(file.length()
                            - fileName.length() - 1).array(),
                        0, Long.SIZE / Byte.SIZE);
                        
            inputStream.skip(fileName.length() + 1);
                
            byte[] arr = new byte[1024 * 8];
                        
            int len = 0;
            while ((len = inputStream.read(arr)) != -1) {
                response.write(arr, 0, len);
            }
        }
    }

    // Request types: FUPLOAD, DOWNREQ, NAMEREQ
    private static void start(int port) throws IOException {
        int bytesRead;
        
        ServerSocket sock = new ServerSocket(port);
        
        reportToServer(port);

        while (true) {
            Socket connection = sock.accept();
            
            try (DataInputStream clientData
                    = new DataInputStream(
                        connection.getInputStream());
                DataOutputStream response
                    = new DataOutputStream(
                        connection.getOutputStream())) {
                byte[] commandBuffer = new byte[7];
                if ((bytesRead = clientData.read(commandBuffer,
                        0, commandBuffer.length)) != -1) {
                    String command = new String(commandBuffer);
                    
                    if (command.equals("FUPLOAD")) { // Upload is starting
                        uploadHandler(clientData, response);
                    } else if (command.equals("DOWNREQ")) { // Download is requested
                        downloadHandler(clientData, response);
                    } else if (command.equals("NAMEREQ")) { // File name is requested
                        fileNameHandler(clientData, response);
                    }
                }
            }
        }
    }
    
    public static void main(String... args) throws IOException {
        int port = 8080;
        
        start(port);
    }
}