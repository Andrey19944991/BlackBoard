package ru.chekov.server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class InputData extends Thread {

    public InputData() {
        this.start();
    }

    @Override
    public void run() {
        try(Scanner scanner1 = new Scanner(new FileReader(new File(System.getProperty("user.home"),
                "data/testData.txt")))) {

            File file = new File(System.getProperty("user.home"), "data/output.txt");
            while (scanner1.hasNextLine()) {
                FileWriter fileWriter = new FileWriter(file,true);
                String current = scanner1.nextLine();

                Thread.sleep(1000);
                fileWriter.write(current+"\n");
                fileWriter.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
