import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class chat {
    private String Name = "";
    private String msg = null;
    protected BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    protected void sendMessage(DataOutputStream dos) throws Exception {
        String msg = br.readLine();
        dos.writeUTF(this.Name + msg);
        dos.flush();
    }

    protected void setName(String Name) {
        this.Name = Name + " : ";
    }
    protected String  getName(){
        return this.Name;
    }
    protected void recieveMessage(DataInputStream dis) throws Exception {
        this.msg = dis.readUTF();
        System.out.print("\t\t");
        System.out.println(msg);
    }

    protected String getmsg() {
        return this.msg;
    }

    protected void closeCon() throws Exception {
        this.br.close();
    }
}
