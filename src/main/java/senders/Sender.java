package senders;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sender implements Runnable {
    private int messages;
    public Sender(int messages) {
        this.messages = messages;
    }


    public void run() {
        for (int i=0 ; i < messages ; i++) {
            try {
                URL url = new URL("localhost:8080");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes("message");
                out.flush();
                out.close();
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("error");
            }
        }
}
}




