/**
 * Created by Mohammad Mehdi on 12/18/2015.
 */
public class Constant {


    public static final int TCP_PORT = 2053;
    public static final int UDP_PORT_COURSE1 = 4053;
    public static final int UDP_PORT_COURSE2 = 5053;
    //------------------options--------------------------------------------
    public static final String hello = "HELLO";
    public static final String welcome = "WELCOME";
    public static final String add = "ADD";
    public static final String drop = "DROP";
    public static final String ack = "ACK";
    public static final String nack = "NACK";
    public static final String submit = "SUBMIT";
    public static final String port="PORT";
    public static final String info="INFO";
    public static final String req="REQ";
    public static final String course="COURSE";
    public static final String connected = "CONNECTED";
//---------------------------------------------------------------------

    public static final String course1 = "course1";
    public static final String course2 = "course2";
    public static final String regserver = "REGSERVER";
    public static final String space = "                        ";


    //----------------------------server output messages  -----------------------------------------------------
    public static final String receiving_hello_from_student = "Receiving HELLO from a student.";
    public static final String receiving_add_from_a_student = "Receiving ADD from a student.";
    public static final String receiving_drop_from_a_student = "Receiving DROP from a student";
    public static final String sending_ack_to_add_request_from_a_student = "Sending ACK to an ADD request";
    public static final String sending_nack_to_add_request_from_a_student = "Sending NACK to an ADD request";
    public static final String sending_ack_to_drop_request_from_a_student = "Sending ACK to a DROP request";
    public static final String sending_port_to_a_student = "Sending PORT to a student";
//------------------------------------------------------------------------

//---------------------------student output messages----------------------

    public static final String receiving_hello_from_regServer = "Receiving HELLO from RegServer.";
    public static final String choose_a_course_randomaly = "Choosing a course (randomly)";
    public static final String receiving_ack_for_add = "Receiving ACK for ADD";
    public static final String receiving_nack_for_add = "Receiving NACK for ADD";
    public static final String receving_ack_for_drop = "Receiving ACK for DROP";
    public static final String receving_port_from_regserver = "Receiving PORT from RegServer";

//---------------------------------------------------------------------------------------

    //-------------------------------------course server messages---------------
    public static final String student_create_udp_socket = "Student create UDP socket";
    public static final String receiving_course_from_a_course_server = "Receiving COURSE from a course server";
    public static final String close_the_udp_socket = "Close the UDP sockets";
    public static final String receving_info_from_a_student="Receiving INFO from a student";


    //----------------------color------------------------------
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

}
