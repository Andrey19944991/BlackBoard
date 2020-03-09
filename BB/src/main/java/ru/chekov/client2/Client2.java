package ru.chekov.client2;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client2 extends JFrame {

    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;

    static int x1;
    static int y1;
    static int x2;
    static int y2;

    public static void main(String[] args) throws IOException {
        int width = 1920;
        int length = 1024;
        JFrame frame = new JFrame();

        JPanel panel = new JPanel(null);

        frame.add(panel);
        frame.setSize(width, length);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Graphics2D g = (Graphics2D) panel.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); //сглаживание
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        panel.setBackground(Color.white);

        try {
            clientSocket = new Socket("localhost", 29288);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String line;
            String [] arrData;
            while (true) {
                line = in.readLine();
                if (line.equals("stop")) {
                    break;
                } else {
                    if (!line.isEmpty()) {
                        arrData = line.split(";");
                        if (arrData[1].equals("start")) {
                            x1 = (int) Math.round(Double.parseDouble(arrData[2])*width);
                            y1 = (int) Math.round(Double.parseDouble(arrData[3])*length);
                        } else {
                            x2 = (int) Math.round(Double.parseDouble(arrData[2])*width);
                            y2 = (int) Math.round(Double.parseDouble(arrData[3])*length);
                            g.drawLine(x1,y1,x2,y2);
                            x1 = x2;
                            y1 = y2;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}
