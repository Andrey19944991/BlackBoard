package ru.chekov.server;

import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.util.Scanner;

public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {

        try {
            File file = new File(System.getProperty("user.home"), "data/output.txt");
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get(System.getProperty("user.home")+"\\data");
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
