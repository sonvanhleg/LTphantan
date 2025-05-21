import java.io.*;
import java.net.*;
import java.util.Random;
public class client {
	public static void main(String argv[]) throws Exception {
        int N = 2;
        int[] A = new int[N];
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            A[i] = rand.nextInt(100); 
            sb.append(A[i]);
            if (i != N - 1) sb.append(" "); 
        }

        String sentence_to_server = sb.toString();

        System.out.println("Sending to server: " + sentence_to_server);

        Socket clientSocket = new Socket("10.2.68.168", 6543); 
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        outToServer.writeBytes(sentence_to_server + "\n");

        String sentence_from_server = inFromServer.readLine();
        System.out.println("Result from server: " + sentence_from_server);

        clientSocket.close();
    }
}
