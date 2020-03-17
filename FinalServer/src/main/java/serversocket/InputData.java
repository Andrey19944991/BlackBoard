package serversocket;

import java.io.*;
import java.util.Scanner;


public class InputData extends Thread {

    public InputData() {
        this.start();
    }

    @Override
    public void run() {
        String separ = File.separator;
        try(Scanner scanner1 = new Scanner(new FileReader(new File("data"+separ+"testData.txt")))) {

            File file = new File("data"+separ+"output.txt");

            while (scanner1.hasNextLine()) {
                FileWriter fileWriter = new FileWriter(file,true);
                String current = scanner1.nextLine();

                Thread.sleep(2000);
                fileWriter.write(current+"\n");
                fileWriter.close();
            }
            Thread.sleep(3000);
            FileWriter fstream1 = new FileWriter("data"+
                    separ+"output.txt");
            BufferedWriter out1 = new BufferedWriter(fstream1);
            out1.write("");
            out1.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
