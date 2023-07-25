import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("127.0.0.1", 5000);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            out.println(username);
            out.flush();

            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            while (true) {
                String input = scanner.nextLine();
                out.println(input);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
