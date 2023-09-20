import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    public static final int MCAST_PORT = 50001;

    public static void main(String[] args) {
        try {
            // 1. Create a DatagramSocket object
            DatagramSocket serverSocket = new DatagramSocket(MCAST_PORT);

            // 2. Create Buffers for storing datagram data in DatagramPacket object
            byte[] buffReceiveData = new byte[1024]; // for incoming data
            byte[] buffSendData = new byte[1024]; // for outgoing data

            // 3. Create DatagramPacket object wrapping the incoming packet (datagram)
            DatagramPacket packetIn = new DatagramPacket(buffReceiveData, buffReceiveData.length);

            // 4. Receive incoming datagram into DatagramPacket object.
            try {
                // This is a blocking system call.
                serverSocket.receive(packetIn); // Program blocks here
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 5. Get the data from the received packet
            String strInData = new String(packetIn.getData());
            System.out.println("SERVER RECEIVED DATA: " + strInData);

            buffSendData = strInData.toUpperCase().getBytes();

            // 6. Find sender's address and port from the received packet
            InetAddress inAddress = packetIn.getAddress();
            int inPort = packetIn.getPort();

            // 7. Create datagram to send
            DatagramPacket packetOut = new DatagramPacket(buffSendData, buffSendData.length, inAddress, inPort);

            // 8. Send the response datagram
            try {
                serverSocket.send(packetOut);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 9. Close the DatagramSocket
            serverSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
