import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class Server {
    public static void main(String[] args) throws Exception{
        System.out.println("You are the Server: \n"
            + "Echo any incoming message...\n"
                + "Please wait while the program starts up......\n\n");
        InetAddress ip = InetAddress.getLocalHost();

        DatagramSocket ds = new DatagramSocket(9999);
        byte[] receive;
        while (true){


            receive = new byte[1024];
            DatagramPacket dp = new DatagramPacket(receive, receive.length);
            ds.receive(dp);

            String received = new String(dp.getData(),"UTF-8").replaceAll("\0", "");
            if (received.equals("end")){
                System.err.println("\nThe client has sent an abort request. \n"
                        + "The program will terminate now..");
                break;
            }
            System.out.print("Client: " + received);
            //String reply = new String(dp.getData(), "UTF-8").replaceAll("\0", "");

            
            Scanner in = new Scanner(System.in);
            String reply = in.nextLine();
            byte[] response = reply.getBytes();
            in.close();
            DatagramPacket dps = new DatagramPacket(response, response.length, ip, dp.getPort());
            System.out.println("\n>>>>>: " + reply+"\n");
            ds.send(dps);
        }

    }
}