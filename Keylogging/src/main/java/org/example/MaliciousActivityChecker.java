package org.example;

import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MaliciousActivityChecker {

    private static final String FILENAME = "keystrokes.txt";
    private static final String[] MALICIOUS_STRINGS = {"password", "credit card", "social security"};

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new MaliciousActivityTask(), 0, 4000);
    }

    private static class MaliciousActivityTask extends TimerTask {
        public void run() {
            try {
                BufferedReader br = new BufferedReader(new FileReader(FILENAME));
                String line;
                while ((line = br.readLine()) != null) {
                    for (String s : MALICIOUS_STRINGS) {
                        if (line.contains(s)) {
                            System.out.println("Malicious Activity Detected!");
                            break;
                        }
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
