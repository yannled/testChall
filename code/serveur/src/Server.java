import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    final static Logger LOG = Logger.getLogger(Server.class.getName());

    private ServerSocket serverSocket;

    List<ClientComm> ClientComms = new CopyOnWriteArrayList<>();

    private boolean shouldRun = false;

    public Server(){};

    public void startServer() throws IOException {
        if (serverSocket == null || serverSocket.isBound() == false) {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(1313));
        }

        this.run();
    }

    @Override
    public void run() {
        shouldRun = true;
        while (shouldRun) {
            try {
                LOG.log(Level.INFO, "Listening for client connection on {0}", serverSocket.getLocalSocketAddress());
                Socket clientSocket = serverSocket.accept();
                LOG.info("New client has arrived...");
                ClientComm client = new ClientComm(clientSocket,Server.this);
                ClientComms.add(client);
                LOG.info("Delegating work to client worker...");
                Thread clientThread = new Thread(client);
                clientThread.start();

            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "IOException in main server thread, exit: {0}", ex.getMessage());
                for(ClientComm client : ClientComms)
                    client.notifyServerShutdown();
                shouldRun = false;
            }
        }
    }
}
