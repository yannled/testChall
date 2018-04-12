import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {

        String command;
        try {
            Socket echoSocket = new Socket("localhost", 1313);
            PrintWriter out =
                    new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            command = in.readLine();
            System.out.println(command);

            out.println("getInfo");
            out.flush();

            command = in.readLine();
            System.out.println(command);

            CommandExemple commande = JsonObjectMapper.parseJson(command, CommandExemple.class);
            out.println(JsonObjectMapper.toJson(commande));
            out.flush();

            command = in.readLine();
            System.out.println(command);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
