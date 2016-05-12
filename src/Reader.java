import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private  String[] studentName;
    private  double[] possibility, starting, RTT;
    private  int[] maxOperations;
    public Reader() {
        BufferedReader br = null;
        int i = 0;
        studentName = new String[4];
        possibility = new double[4];
        starting = new double[4];
        maxOperations = new int[4];
        RTT = new double[4];
        try {
            String line;
            br = new BufferedReader(new java.io.FileReader("strategy.txt"));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                studentName[i] = tokens[0];
                possibility[i] = Double.parseDouble(tokens[1]);
                starting[i] = Double.parseDouble(tokens[2]);
                maxOperations[i] = Integer.parseInt(tokens[3]);
                RTT[i] = Double.parseDouble(tokens[4]);
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
    public String[] getStudentName(){return studentName;}
    public double[] getPossibility(){return possibility;}
    public double[] getStarting(){return starting;}
    public double[] getRTT(){return RTT;}
    public int[] getMaxOperations(){return maxOperations;}
}
