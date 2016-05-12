import java.io.*;
import java.net.*;

/**
 * Created by Mohammad Mehdi on 12/17/2015.
 */

public class student extends Thread {
    private String studentName;
    private double possibility, starting, RTT;
    private int maxOperations;
    private boolean isRegister1, isRegister2;
    private boolean start;
    String course1IsRegister, course2IsRegister;


    public student(String _studentName, double _possibility, double _starting,
                   int _maxOperations, double _RTT, boolean _isRegister1, boolean _isRegister2) {
        this.studentName = _studentName;
        this.possibility = _possibility;
        this.starting = _starting;
        this.maxOperations = _maxOperations;
        this.RTT = _RTT;
        this.isRegister1 = _isRegister1;
        this.isRegister2 = _isRegister2;
        this.start = true;
    }

    public String chooseCourse() {
        double rand;
        rand = Math.random();
        if (rand < possibility)
            return Constant.course1;
        return Constant.course2;
    }

    public void run() {
        String serverName = "localhost";
        String course = "";
        try {
            int counter = 0;
            String courseIsRegister = "";
            Socket client = new Socket(serverName, Constant.TCP_PORT);
            Packet packetFromServer;
            while (true) {
                if (start == true) {
                    Thread.sleep((long) (starting * 1000));
                    new ObjectOutputStream(client.getOutputStream()).writeObject(new Packet
                            (studentName, Constant.hello, "option", "payload", Constant.receiving_hello_from_student, 0));
                    start = false;
                }
                while ((packetFromServer = (Packet) new ObjectInputStream(client.getInputStream()).readObject()) == null) {
                }

                if (packetFromServer.getId() == 1) {
                    course = chooseCourse();
                    if (packetFromServer.getMessage().equals(Constant.receiving_hello_from_regServer)) {
                        System.out.println(Constant.ANSI_CYAN + "start up ...                         " +
                                Constant.space + studentName + " TCP TCP_PORT : " + client.getLocalPort() + "Ip Address : " +
                                client.getLocalAddress() + Constant.ANSI_RESET);
                        System.out.println
                                (Constant.ANSI_CYAN + packetFromServer.getMessage() + "      " + Constant.space + studentName +
                                        " connected to regserver " + Constant.ANSI_RESET);
                    }
                    if (packetFromServer.getMessage().equals(Constant.receiving_ack_for_add)) {
                        System.out.println(Constant.ANSI_GREEN + Constant.receiving_ack_for_add + Constant.space +
                                "                " + studentName + " : have added " + packetFromServer.getOption()
                                + Constant.ANSI_RESET);
                    }
                    if (packetFromServer.getMessage().equals(Constant.receiving_nack_for_add)) {
                        System.out.println(Constant.ANSI_RED + Constant.receiving_nack_for_add + Constant.space +
                                "               " + studentName + " : fail to add " + packetFromServer.getOption() +
                                Constant.ANSI_RESET);
                    }
                    if (packetFromServer.getMessage().equals(Constant.receving_ack_for_drop)) {
                        System.out.println(Constant.ANSI_GREEN + Constant.receving_ack_for_drop + Constant.space +
                                "               " + studentName + " : have dropped " + packetFromServer.getOption() +
                                Constant.ANSI_RESET);
                    }
                    if (course.equals(Constant.course1) && isRegister1 == false && counter < maxOperations) {
                        Thread.sleep((long) (RTT * 1000));
                        new ObjectOutputStream(client.getOutputStream()).writeObject(new Packet
                                (studentName, Constant.add, course, "payload", Constant.receiving_add_from_a_student, 2));
                        System.out.println(Constant.choose_a_course_randomaly + Constant.space +
                                "         " + studentName + " add " + course);
                        isRegister1 = true;
                        counter++;
                        continue;
                    }
                    if (course.equals(Constant.course2) && isRegister2 == false && counter < maxOperations) {
                        Thread.sleep((long) (RTT * 1000));
                        new ObjectOutputStream(client.getOutputStream()).writeObject(new Packet
                                (studentName, Constant.add, course, "payload", Constant.receiving_add_from_a_student, 2));
                        System.out.println(Constant.choose_a_course_randomaly + Constant.space +
                                "         " + studentName + " add " + course);
                        isRegister2 = true;
                        counter++;
                        continue;
                    }
                    if (course.equals(Constant.course1) && isRegister1 == true && counter < maxOperations) {
                        Thread.sleep((long) (RTT * 1000));
                        new ObjectOutputStream(client.getOutputStream()).writeObject(new Packet
                                (studentName, Constant.drop, course, "payload", Constant.receiving_drop_from_a_student, 2));
                        System.out.println(Constant.choose_a_course_randomaly + Constant.space +
                                "         " + studentName + " drop " + course);
                        isRegister1 = false;
                        counter++;
                        continue;
                    }
                    if (course.equals(Constant.course2) && isRegister2 == true && counter < maxOperations) {
                        Thread.sleep((long) (RTT * 1000));
                        new ObjectOutputStream(client.getOutputStream()).writeObject(new Packet
                                (studentName, Constant.drop, course, "payload", Constant.receiving_drop_from_a_student, 2));
                        System.out.println(Constant.choose_a_course_randomaly + Constant.space +
                                "         " + studentName + " drop " + course);
                        isRegister2 = false;
                        counter++;
                        continue;
                    }
                    if (maxOperations == counter) {

                        if (isRegister1 == true) {
                            course1IsRegister = Constant.course1;
                            courseIsRegister += Constant.course1;
                        }

                        if (isRegister2 == true) {
                            course2IsRegister = Constant.course2;
                            courseIsRegister += Constant.course2;

                        }
                        new ObjectOutputStream(client.getOutputStream()).writeObject(new Packet
                                (studentName, Constant.submit, courseIsRegister, "payload", courseIsRegister, 3));
                        continue;
                    }
                }
                if (packetFromServer.getId() == 4) {
                    client.close();
                    break;
                }
            }
            if (course1IsRegister != null && course1IsRegister.equals(Constant.course1)) {
                BufferedReader inFromUser = new BufferedReader(new StringReader(studentName));
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress IPAddress = InetAddress.getByName("localhost");
                byte[] sendData = new byte[1024];
                byte[] receiveData = new byte[1024];
                System.out.println(Constant.student_create_udp_socket + Constant.space + "            " + studentName +
                        " : " + Constant.UDP_PORT_COURSE1);
                sendData = inFromUser.readLine().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Constant.UDP_PORT_COURSE1);
                clientSocket.send(sendPacket);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                System.out.println(Constant.receiving_course_from_a_course_server +
                        "                        " + new String(receivePacket.getData()));
                System.out.println(Constant.close_the_udp_socket + Constant.space + "                "
                        + studentName + " : The End ...");
                clientSocket.close();
            }
            if (course2IsRegister != null && course2IsRegister.equals(Constant.course2)) {
                BufferedReader inFromUser = new BufferedReader(new StringReader(studentName));
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress IPAddress = InetAddress.getByName("localhost");
                byte[] sendData = new byte[1024];
                byte[] receiveData = new byte[1024];
                System.out.println(Constant.student_create_udp_socket + Constant.space + "            " + studentName +
                        " : " + Constant.UDP_PORT_COURSE2);
                sendData = inFromUser.readLine().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Constant.UDP_PORT_COURSE2);
                clientSocket.send(sendPacket);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                System.out.println(Constant.receiving_course_from_a_course_server + "                        "
                        + new String(receivePacket.getData()));
                System.out.println(Constant.close_the_udp_socket + Constant.space + "                "
                        + studentName + " : The End ...");
                clientSocket.close();
            }
            if (course1IsRegister != null && course2IsRegister != null) {
                System.out.println("unfortunately " + studentName + " cannot be registered to any courses ");
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(Constant.ANSI_PURPLE + "___________________________________________________________" +
                "_________________________________" + Constant.ANSI_RESET);
        System.out.println(Constant.ANSI_PURPLE + "Event                                                       " +
                "Message                         " + Constant.ANSI_RESET);

        Reader reader = new Reader();
        student st1 = new student(reader.getStudentName()[0], reader.getPossibility()[0],
                reader.getStarting()[0], reader.getMaxOperations()[0], reader.getRTT()[0], false, false);
        st1.start();
        student st2 = new student(reader.getStudentName()[1], reader.getPossibility()[1],
                reader.getStarting()[1], reader.getMaxOperations()[1], reader.getRTT()[1], false, false);
        st2.start();

        student st3 = new student(reader.getStudentName()[2], reader.getPossibility()[2],
                reader.getStarting()[2], reader.getMaxOperations()[2], reader.getRTT()[2], false, false);
        st3.start();

        student st4 = new student(reader.getStudentName()[3], reader.getPossibility()[3],
                reader.getStarting()[3], reader.getMaxOperations()[3], reader.getRTT()[3], false, false);
        st4.start();
    }
}
