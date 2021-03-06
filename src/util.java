import java.util.Scanner;

public class util {
    static int getInt(String intro, int min, int max) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===> " + intro);
        while (true) {
            //System.out.print("Please input a number in ["+min+", "+max+"]: ");
            try {
                System.out.print("> ");
                int n = sc.nextInt();
                if (n >= min && n <= max) return n;
            } catch (RuntimeException e) {
                sc.reset();
                sc.next();
            }
            System.out.println("Invalid Number!");
        }
    }

    static void cls(){
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}
