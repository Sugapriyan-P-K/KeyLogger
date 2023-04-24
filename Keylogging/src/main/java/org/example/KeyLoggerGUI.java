package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;


public class KeyLoggerGUI extends JFrame {
    private JTextArea textArea,textArea2;
    private Path filePath;
    private long lastModified;

    public KeyLoggerGUI() {
        // Set the title of the window
        super("Keylogger");

        // Set the layout of the window
        setLayout(new BorderLayout());

        // Create the text area
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Add the text area to a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Set the size of the window
        setSize(400, 300);

        // Set the window to be visible
        setVisible(true);

        // Set the path to the file
        filePath = Paths.get("keystrokes.txt");

        // Get the last modified time of the file
        lastModified = filePath.toFile().lastModified();

        // Create a timer to update the text area periodically
        Timer timer = new Timer(1000, e -> {
            // Check if the file has been updated
            long currentModified = filePath.toFile().lastModified();
            if (currentModified > lastModified) {
                // Read the contents of the file and display them in the text area
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("keystrokes.txt"));
                    String line;
                    textArea.setText("");
                    while ((line = reader.readLine()) != null) {
                        textArea.append(line + "\n");
                    }
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // Update the last modified time of the file
                lastModified = currentModified;
            }
        });

        // Start the timer
        timer.start();
    }

    public void warningDisplay(String s){
        setTitle("Alert!!");
        setLayout(new BorderLayout());
        textArea2 = new JTextArea();
        textArea2.setEditable(false);

        textArea2.setText("Malicious Activity Detected! Usage of \" " +s+" \" seems suspicious!");
        setVisible(true);
        setLocation(500,500);

    }

}