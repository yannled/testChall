import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientComm implements Runnable{
    static final Logger LOG = Logger.getLogger(ClientComm.class.getName());

    private Socket clientSocket = null;
    private InputStream is = null;
    private OutputStream os = null;
    private boolean done = false;
    private Server server = null;

    public ClientComm(Socket clientSocket, Server server) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        is = clientSocket.getInputStream();
        os = clientSocket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            initCommunication(is, os);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error in Client Communication  with client", ex.getMessage());
        } finally {
            done = true;
            try {
                clientSocket.close();
            } catch (IOException ex) {
                LOG.log(Level.INFO, ex.getMessage());
            }
            try {
                is.close();
            } catch (IOException ex) {
                LOG.log(Level.INFO, ex.getMessage());
            }
            try {
                os.close();
            } catch (IOException ex) {
                LOG.log(Level.INFO, ex.getMessage());
            }
        }
    }

    public void initCommunication(InputStream is, OutputStream os)throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));

        // MESSAGE DE BIENVENUE
        writer.println("Hello SEND HELP TO KNOW WHAT YOU NEED TO DO");
        writer.flush();

        String command;
        boolean done = false;
        while (!done && ((command = reader.readLine()) != null)) {
            LOG.log(Level.INFO, "COMMAND: {0}", command);

            // TO QUIT COMMUNICATION CLIENT
            if(command.equals("bye"))
                done = true;

            else if(command.equals("getInfo"))
                writer.println(JsonObjectMapper.toJson(sendCommandJson()));

            else if(command.equals("HELP")){
                writer.println("Send getInfo to get the json string, Send bye to quit");
            }

            else {
                CommandExemple commande = JsonObjectMapper.parseJson(command, CommandExemple.class);
                if(commande.equals(sendCommandJson()))
                    writer.println("PERFECT");
                else
                    writer.println("SORRY FALSE");
            }

            writer.flush();

        }
    }

    public void notifyServerShutdown() {
        try {
            is.close();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Exception while closing input stream on the server: {0}", ex.getMessage());
        }

        try {
            os.close();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Exception while closing output stream on the server: {0}", ex.getMessage());
        }

        try {
            clientSocket.close();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Exception while closing socket on the server: {0}", ex.getMessage());
        }
    }

    private CommandExemple sendCommandJson(){
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Hello");
        messages.add("Comment");
        messages.add("CA");
        messages.add("VA ?");
        CommandExemple commande = new CommandExemple(messages, 4);
        return commande;
    }
}
