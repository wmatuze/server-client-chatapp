import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static Map<PrintWriter, String> clients = new HashMap<>();
    private static volatile boolean isRunning = true;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started. Listening on port 5000...");

            // Add a shutdown hook to stop the server gracefully when Ctrl+C is pressed
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                isRunning = false;
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                clients.put(out, clientSocket.getInetAddress().toString());

                Thread clientHandler = new Thread(new ClientHandler(clientSocket, out));
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final PrintWriter out;
        private final BufferedReader in;
        private final String clientName;

        public ClientHandler(Socket clientSocket, PrintWriter out) throws IOException {
            this.clientSocket = clientSocket;
            this.out = out;
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.clientName = clients.get(out);
        }

        @Override
        public void run() {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println(clientName + ": " + msg);
                    broadcastMessage(clientName + ": " + msg);
                }
                // Client disconnected
                System.out.println("Client disconnected: " + clientName);
                clients.remove(out);
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcastMessage(String message) {
            for (PrintWriter client : clients.keySet()) {
                if (client != out) { // Send the message to all other clients except the sender
                    client.println(message);
                    client.flush();
                }
            }
        }
    }
}
