import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Thread2 extends Thread{
    SharedData sharedData;

    public Thread2(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        while(sharedData.isAlive()) {
            synchronized(sharedData) {
                sharedData.notifyAll();
                try {
                    while(sharedData.getCurrentThread() != SharedData.THREAD_2 && sharedData.isAlive()) {
                        sharedData.wait();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Thread2.class.getName()).log(Level.SEVERE, null, ex);
                }

                int length = sharedData.getValidRollNumber().size();
                if(length > 0) {
                    Student std = sharedData.getValidRollNumber().get(length - 1);
                    System.out.println("Welcome student has roll no is: " + std.getRollNo());
                    System.out.println("Valid collection has length: " + length);

                    FileOutputStream fos = null;
                    ObjectOutputStream oos = null;
                    try {
                        //ghi vao file .dat
                        fos = new FileOutputStream(std.getRollNo() + ".dat");
                        oos = new ObjectOutputStream(fos);

                        oos.writeObject(std);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Thread2.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Thread2.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if(oos != null) {
                            try {
                                oos.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Thread2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if(fos != null) {
                            try {
                                fos.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Thread2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                } else {
                    System.out.println("Error!!!");
                }

                sharedData.setCurrentThread(SharedData.THREAD_1);
            }
        }
        //ket thuc doc du lieu tu file student.txt
        synchronized(sharedData) {
            sharedData.notifyAll();
        }
    }
}