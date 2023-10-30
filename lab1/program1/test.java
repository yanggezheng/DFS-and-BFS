package AI.program1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
    static List<String> al1 = new ArrayList<>();
    static List<String> al = new ArrayList<>();
    public static void main(String[] args)
    {
        String s = "abcd";
        find(s, "",3); // Calling a function
        for (int i = 1; i < 4; i++) for (String value : al) if (value.length() == i) al1.add(value);
        System.out.println(al);
        String a = " ";
        String[] b = a.split("");
        System.out.println(b.length);
    }

    private static void find(String s, String ans, int depth)
    {
        if (s.length() == 0) {
            al.add(ans);
            return;
        }
        find(s.substring(1), ans + s.charAt(0),depth);
        find(s.substring(1), ans,depth);
    }

}
