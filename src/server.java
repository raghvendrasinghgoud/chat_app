import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class server extends chat {
    private DataOutputStream dos;
    private DataInputStream dis;
    Socket socket;
    ServerSocket ss;
    Thread t1;
    Thread t2;
    String status = "";
    public server(int port) {
        try {
            this.ss = new ServerSocket(port);
            System.out.println("Waiting for client...");
            this.socket = ss.accept();
            this.dos = new DataOutputStream(this.socket.getOutputStream());
            this.dis = new DataInputStream(this.socket.getInputStream());
           // super.setName("Server");
            this.stratThreads();
            
        } catch (Exception e) {
            System.out.println("server not strated" + e.getMessage());
        }
    }

    @Override
    public void closeCon(){
        try{
        dos.close();
        dis.close();
        socket.close();
        ss.close();
        }catch(Exception e){ System.out.println("Chatting off successfully");}
    }

    public void stratThreads() {
        Runnable thread1 = () -> {
            try {
            while (true) {            
                if(this.status.equals("exit")){
                    break;
                }
                super.sendMessage(dos);
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        };
        t1 = new Thread(thread1);
    
        Runnable thread2 = () -> {
            try {
          while (true) {
                
                super.recieveMessage(dis);
              if(getmsg().equals("bye")){
                  this.status="exit";
                  t1.interrupt();
                  this.closeCon();
                  System.exit(0);
                  break;
                }
            }
        } catch (Exception e) {
          System.out.println("server get offline");
        }
        };
        t2 = new Thread(thread2);
            t1.start();
                t2.start();
      }

    public static void main(String args[]) throws Exception {
       new server(6666);
        // c.closeCon();
    }

}