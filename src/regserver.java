import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class regserver extends Thread{
    private ServerSocket serverSocket;
    private static String[] courseNumber, courseName, day, room;
    private static int[] capacity;

    public regserver(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void fileReader() {
        BufferedReader br = null;
        int i = 0;
        try {
            String line;
            br = new BufferedReader(new FileReader("schedule.txt"));
            courseName = new String[2];
            courseNumber = new String[2];
            room = new String[2];
            day = new String[2];
            capacity = new int[2];
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                courseName[i] = tokens[0];
                courseNumber[i] = tokens[1];
                day[i] = tokens[2];
                room[i] = tokens[3];
                capacity[i] = Integer.parseInt(tokens[4]);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        fileReader();
        System.out.println(Constant.ANSI_PURPLE + "Start up" + Constant.space + " RegServer TCP TCP_PORT : "
                + serverSocket.getLocalPort() + "  ;  IP Address : " + serverSocket.getLocalSocketAddress()
                + Constant.ANSI_RESET);
        System.out.println(Constant.ANSI_PURPLE + "__________________________________________________________________" +
                "______________________________________" + Constant.ANSI_RESET);
        System.out.println(Constant.ANSI_PURPLE + "Event :                                             Message :                         " +
                Constant.ANSI_RESET);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                communication(socket);
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void communication(Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Packet packetFromStudent;
                    while (true) {
                        while ((packetFromStudent =
                                (Packet) new ObjectInputStream(socket.getInputStream()).readObject()) == null) {
                        }
                        if (packetFromStudent.getId() == 0) {
                            System.out.println(Constant.ANSI_CYAN
                                    + packetFromStudent.getMessage() + "                      "
                                    + packetFromStudent.getIdentifierString() + " is online" + Constant.ANSI_RESET);

                            new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet
                                    (Constant.regserver, Constant.welcome, "option", "payload",
                                            Constant.receiving_hello_from_regServer, 1));
                        }
                        if (packetFromStudent.getId() == 2) {

                            if (packetFromStudent.getType().equals(Constant.add)) {
                                System.out.println(packetFromStudent.getMessage() + Constant.space
                                        + packetFromStudent.getIdentifierString()
                                        + " wants to add " + packetFromStudent.getOption());
                            }
                            if (packetFromStudent.getType().equals(Constant.drop)) {
                                System.out.println(packetFromStudent.getMessage() + Constant.space
                                        + packetFromStudent.getIdentifierString()
                                        + " wants to drop " + packetFromStudent.getOption());
                            }
                            if (packetFromStudent.getType().equals(Constant.add)) {
                                if (packetFromStudent.getOption().equals(Constant.course1) && capacity[0] > 0) {
                                    new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet
                                            (Constant.regserver, Constant.ack, packetFromStudent.getOption(), "payload",
                                                    Constant.receiving_ack_for_add, 1));
                                    capacity[0]--;
                                    System.out.println(Constant.ANSI_GREEN + Constant.sending_ack_to_add_request_from_a_student
                                            + Constant.space + packetFromStudent.getOption() + " is added for "
                                            + packetFromStudent.getIdentifierString() + Constant.ANSI_RESET);
                                    continue;
                                }
                                if (packetFromStudent.getOption().equals(Constant.course2) && capacity[1] > 0) {
                                    new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet
                                            (Constant.regserver, Constant.ack, packetFromStudent.getOption(), "payload",
                                                    Constant.receiving_ack_for_add, 1));
                                    capacity[1]--;
                                    System.out.println(Constant.ANSI_GREEN + Constant.sending_ack_to_add_request_from_a_student +
                                            Constant.space + packetFromStudent.getOption() + " is added for "
                                            + packetFromStudent.getIdentifierString() + Constant.ANSI_RESET);
                                    continue;
                                }

                                if (packetFromStudent.getOption().equals(Constant.course1) && capacity[0] == 0) {
                                    new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet
                                            (Constant.regserver, Constant.nack, packetFromStudent.getOption(), "payload",
                                                    Constant.receiving_nack_for_add, 1));
                                    System.out.println(Constant.ANSI_RED + Constant.sending_nack_to_add_request_from_a_student +
                                            "                       " + packetFromStudent.getOption() + " cannot be added for "
                                            + packetFromStudent.getIdentifierString() + Constant.ANSI_RESET);
                                    continue;
                                }
                                if (packetFromStudent.getOption().equals(Constant.course2) && capacity[1] == 0) {
                                    new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet
                                            (Constant.regserver, Constant.nack, packetFromStudent.getOption(), "payload",
                                                    Constant.receiving_nack_for_add, 1));
                                    System.out.println(Constant.ANSI_RED + Constant.sending_nack_to_add_request_from_a_student +
                                            "                       " + packetFromStudent.getOption() + " cannot be added for "
                                            + packetFromStudent.getIdentifierString() + Constant.ANSI_RESET);
                                    continue;
                                }

                            }
                            if (packetFromStudent.getType().equals(Constant.drop)) {
                                if (packetFromStudent.getOption().equals(Constant.course1)) {
                                    capacity[0]++;
                                    new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet
                                            (Constant.regserver, Constant.ack, packetFromStudent.getOption(), "payload",
                                                    Constant.receving_ack_for_drop, 1));
                                    System.out.println(Constant.ANSI_GREEN + Constant.sending_ack_to_drop_request_from_a_student
                                            + Constant.space + packetFromStudent.getOption() +
                                            " is dropped for " + packetFromStudent.getIdentifierString() + Constant.ANSI_RESET);
                                    continue;
                                }
                                if (packetFromStudent.getOption().equals(Constant.course2)) {
                                    capacity[1]++;
                                    new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet
                                            (Constant.regserver, Constant.ack, packetFromStudent.getOption(), "payload",
                                                    Constant.receving_ack_for_drop, 1));
                                    System.out.println((Constant.ANSI_GREEN + Constant.sending_ack_to_drop_request_from_a_student +
                                            Constant.space + packetFromStudent.getOption() +
                                            " is dropped for " + packetFromStudent.getIdentifierString() + Constant.ANSI_RESET));
                                    continue;
                                }
                            }
                        }
                        if (packetFromStudent.getId() == 3) {
                            if (packetFromStudent.getType().equals(Constant.submit)) {

                                new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet
                                        (Constant.regserver, Constant.port, Integer.toString(Constant.UDP_PORT_COURSE1),
                                                Integer.toString(Constant.UDP_PORT_COURSE2),
                                                Constant.receving_port_from_regserver, 4));
                            }
                            socket.close();
                            break;
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }

    public static void main(String[] args) throws IOException {


        regserver regs = new regserver(Constant.TCP_PORT);
        regs.start();
    }
}
