import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Thread1 extends Thread{
    SharedData sharedData;

    public Thread1(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
        }

        FileReader reader = null;
        BufferedReader bReader = null;

        try {
            reader = new FileReader("Student.txt");
            bReader = new BufferedReader(reader);
            String line = null;

            while(sharedData.isAlive()) {
                //sync du lieu tu day
                synchronized(sharedData) {
                    line = bReader.readLine();
                    if(line == null) {
                        sharedData.setAlive(false);
                        break;
                    }

                    boolean isValid = Student.checkValidRollNo(line);
                    if(isValid) {
                        Student std = new Student(line);
                        sharedData.getValidRollNumber().add(std);
                        sharedData.setCurrentThread(SharedData.THREAD_2);
                    } else {
                        sharedData.getUnvalidRollNumber().add(line);
                        sharedData.setCurrentThread(SharedData.THREAD_3);
                    }

                    sharedData.notifyAll();
                    try {
                        sharedData.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(bReader != null) {
                try {
                    bReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //ket thuc doc du lieu tu file student.txt
        synchronized(sharedData) {
            sharedData.notifyAll();
        }
    }
}