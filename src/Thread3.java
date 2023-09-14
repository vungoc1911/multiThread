import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Thread3 extends Thread{
    SharedData sharedData;

    public Thread3(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream("unvalid.txt");

            while (sharedData.isAlive()) {
                synchronized(sharedData) {
                    sharedData.notifyAll();
                    try {
                        while(sharedData.getCurrentThread() != SharedData.THREAD_3 && sharedData.isAlive()) {
                            sharedData.wait();
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Thread3.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    int length = sharedData.getUnvalidRollNumber().size();
                    String rollNo = sharedData.getUnvalidRollNumber().get(length - 1);

                    System.out.println("Unvalid roll number: " + rollNo);

                    //ghi unvalid roll number vao file
                    rollNo += "\n";
                    byte[] b = rollNo.getBytes();
                    fos.write(b);

                    //chuyen thread
                    sharedData.setCurrentThread(SharedData.THREAD_1);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Thread3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Thread3.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Thread3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //ket thuc thread 3

        //ket thuc doc du lieu tu file student.txt
        synchronized(sharedData) {
            sharedData.notifyAll();
        }
    }
}
