import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Mohammad Mehdi on 12/26/2015.
 */
public class CourseServer1 {


    public static void main(String[] args) throws IOException {

        String courseInfo = "course1 ComputerEngineering MW ROOM(132) 2";
        DatagramSocket serverSocket = new DatagramSocket(Constant.UDP_PORT_COURSE1);
        System.out.println(Constant.ANSI_PURPLE + "Event :                                             Message :                         " +
                Constant.ANSI_RESET);
        System.out.println(Constant.ANSI_PURPLE + "______________________________________________________" +
                "______________________________________" + Constant.ANSI_RESET);
        System.out.println("Start up ..." + Constant.space + "                  Course1  UDP Port : " + Constant.UDP_PORT_COURSE1
                + " Ip Address : " + serverSocket.getLocalSocketAddress());
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String received = new String(receivePacket.getData());
            System.out.println(Constant.receving_info_from_a_student+Constant.space+ " Course1 :" +received+
                    " receiving information . ");
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            sendData = courseInfo.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
}
