import java.io.Serializable;

/**
 * Created by Mohammad Mehdi on 12/18/2015.
 */
public class Packet implements Serializable {

    private static final long serialVersionUID = 5950169519310163575L;
    private String identifierString;
    private String type;
    private String option;
    private String payload;
    private String message;
    private int id;


    public Packet(String _identifierString, String _type, String _option, String _payload,String _message, int _id) {
        this.identifierString = _identifierString;
        this.type = _type;
        this.option = _option;
        this.payload = _payload;
        this.message=_message;
        this.id = _id;
    }

    public String getIdentifierString() {
        return identifierString;
    }

    public void setIdentifierString(String _identifierString) {
        this.identifierString = _identifierString;
    }

    public String getType() {
        return type;
    }

    public void setType(String _type) {
        this.type = _type;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String _option) {
        this.option = _option;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String _paylaod) {
        this.payload = _paylaod;
    }

    public String getMessage(){return message;}
    public void setMessage(String _message){this.message=_message;}

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public int hashCode() {
        return id;
    }

    public String toString() {
        return Integer.toString(getId()) + " " + getIdentifierString() +
                " " + getType() + " " + getOption() + " " + getPayload();
    }

}
