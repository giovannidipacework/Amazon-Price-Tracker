import java.net.*;
import java.io.*;
import java.nio.file.*;

public class ServerLog {
    
    public static void main(String[] args) {
        String pathFile = "log.txt";
        String pathSchema = "evento.xsd";
        while (true) {
            try (ServerSocket servs = new ServerSocket(8080);
                    Socket sd = servs.accept();
                    DataInputStream din = new DataInputStream(sd.getInputStream());) {
                String evento = din.readUTF();
                System.out.println(evento);
                ValidatoreXML.valida(evento, pathSchema);
                try 
                {
                    if(!Files.exists(Paths.get(pathFile)))
                        Files.createFile(Paths.get(pathFile));
                    Files.write(Paths.get(pathFile), evento.getBytes(), StandardOpenOption.APPEND);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
