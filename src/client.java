import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class client extends chat {
  private DataOutputStream dos;
  private DataInputStream dis;
  Socket socket;
  Thread t1;
  Thread t2;
  String status = "";

  public client(String ip, int port) {
    try {
      socket = new Socket(ip, port);
      System.out.println("connection created");
      dos = new DataOutputStream(socket.getOutputStream());
      dis = new DataInputStream(socket.getInputStream());
     // super.setName("Client");
      this.stratThreads();
    } catch (Exception e) {
      System.out.println("server not strate\n" + e.getMessage());
    }
  }

  @Override
  public void closeCon() throws Exception {
    dos.close();
    dis.close();
    socket.close();
  }

  public void stratThreads() {
    Runnable thread1 = () -> {
      while (true) {
        try {
          if (this.status.equals("exit")) {
            dos.writeUTF("bye");
            dos.flush();
            break;
          }
          super.sendMessage(dos);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    t1 = new Thread(thread1);


    Runnable thread2 = () -> {
      while (true) {
        try {
          super.recieveMessage(dis);
          if (getmsg().equals("bye")) {
            this.status = "exit";
            break;
          }
        } catch (Exception e) {
          System.out.println("server get offline");
          e.printStackTrace();
        }
      }
    };
    t2 = new Thread(thread2);
    try{
    t1.join();
        t2.join();
        this.closeCon();
    }catch(Exception e){ e.printStackTrace();}
  }

  public static void main(String args[]) throws Exception {
    new client("localhost", 6666);

    // c.closeCon();
  }
}