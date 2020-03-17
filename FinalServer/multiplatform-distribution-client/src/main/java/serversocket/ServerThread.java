package serversocket;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.nio.file.*;

public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {

        String separ = File.separator;

        try {
            File file = new File("data"+separ+"output.txt");

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get("data");
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;
            long pointer = 0;
            while ((key = watchService.take()) != null) {

                RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
                randomAccessFile.seek(pointer);
                StringBuilder sb = new StringBuilder();
                int b = randomAccessFile.read();
                while(b != -1) {
                    sb = sb.append((char) b);
                    b = randomAccessFile.read();
                }
                System.out.println(sb);
                writer.println(sb);
                pointer = randomAccessFile.getFilePointer();
                randomAccessFile.close();

                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
