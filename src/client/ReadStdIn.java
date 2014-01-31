package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadStdIn {
	 
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean goOn = true;
        String line = null;
        try {
            while( (line = br.readLine())!=null && goOn){
                System.out.println("StdIn: " + line); 
                if (line.toUpperCase().equals("EXIT")) {
                	goOn = false;
                	System.out.println("Thread will be terminated!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}