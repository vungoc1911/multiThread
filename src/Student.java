import java.io.Serializable;
import java.util.regex.Pattern;

public class Student implements Serializable{
    String rollNo;

    public Student() {
    }

    public Student(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public static boolean checkValidRollNo(String rollNo) {
        String pattern = "[CTN][0-9]{4}[G-M][V]?[0-9]{4}";
        boolean matches = Pattern.matches(pattern, rollNo);
        return matches;
    }
}
