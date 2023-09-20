import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;

public class UDPClient {
    public final static int MCAST_PORT = 50001;

    public static void main(String[] args) {
        try {
            // 1. Create a DatagramSocket object
            DatagramSocket clientSocket = new DatagramSocket();

            // 2. Find the IP address of the server by name
            InetAddress IPAddress = null;
            try {
                IPAddress = InetAddress.getByName("localhost");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            // 3. Create Buffers for storing datagram data in DatagramPacket object
            byte[] buffReceiveData = new byte[1024]; // for incoming data
            byte[] buffSendData = new byte[1024]; // for outgoing data

            // 4. Data to send to the server
            String sentence = "Hello World from MCast client...";
            buffSendData = sentence.getBytes();

            // 5. Create DatagramPacket object wrapping the outgoing packet (datagram)
            DatagramPacket sendPacket = new DatagramPacket(buffSendData, buffSendData.length, IPAddress, MCAST_PORT);
            try {
                // 6. Send the datagram to the above-specified destination
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 7. Create DatagramPacket object wrapping the incoming packet (datagram)
            DatagramPacket receivePacket = new DatagramPacket(buffReceiveData, buffReceiveData.length);

            // 8. Receive incoming datagram into DatagramPacket object.
            try {
                // This is a blocking system call.
                clientSocket.receive(receivePacket); // Program blocks here
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 9. Display the received data
            String strReceived = new String(receivePacket.getData());
            System.out.println("CLIENT RECEIVED DATA: " + strReceived);

            // 10. Close the DatagramSocket
            clientSocket.close();
        } catch (SocketException e) { // Top Level Try-Catch
            e.printStackTrace();
        }
    }
}
