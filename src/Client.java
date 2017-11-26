import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author kderrick
 */
public class Client {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        System.out.println("You are the client: \n"
                    + "At any time you may end the session, reply with 'end'\n"
                + "Please wait while the program starts up......\n\n");
        String Message = "Start";
        byte[] message;
        InetAddress ip = InetAddress.getLocalHost();
        Scanner kbd = new Scanner (System.in);
        
        while (true){
            System.out.print(">>>>: ");
            Message = kbd.nextLine();
           
            //Create datagram to send the message
            DatagramSocket ds = new DatagramSocket();
            if (Message.equals("end")){
                message = "end".getBytes();
                DatagramPacket dp = new DatagramPacket(message, message.length, ip,9999 );
                ds.send(dp);
                break;
            }

            message = Message.getBytes();
            DatagramPacket dp = new DatagramPacket(message, message.length, ip,9999 );
            ds.send(dp);


            //Create datagram to receive the response
            byte[] received = new byte[1024];
            DatagramPacket dpr = new DatagramPacket(received, received.length );
            ds.receive(dpr);

            String response = new String(dpr.getData(), "UTF-8").replaceAll("\0", "");
            System.out.println("\nServer: " + response+"\n");
            kbd.close();
        }
    }
}
