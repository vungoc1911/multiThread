import java.util.ArrayList;
import java.util.List;

public class SharedData {
    public static final int THREAD_1 = 1;
    public static final int THREAD_2 = 2;
    public static final int THREAD_3 = 3;

    List<Student> validRollNumber;
    List<String> unvalidRollNumber;
    int currentThread;
    boolean alive;

    public SharedData() {
        validRollNumber = new ArrayList<>();
        unvalidRollNumber = new ArrayList<>();
        currentThread = 0;
        alive = true;
    }

    public List<Student> getValidRollNumber() {
        return validRollNumber;
    }

    public void setValidRollNumber(List<Student> validRollNumber) {
        this.validRollNumber = validRollNumber;
    }

    public List<String> getUnvalidRollNumber() {
        return unvalidRollNumber;
    }

    public void setUnvalidRollNumber(List<String> unvalidRollNumber) {
        this.unvalidRollNumber = unvalidRollNumber;
    }

    public int getCurrentThread() {
        return currentThread;
    }

    public void setCurrentThread(int currentThread) {
        this.currentThread = currentThread;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
