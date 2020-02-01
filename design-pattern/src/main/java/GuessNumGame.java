import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author jiangsj
 */
public class GuessNumGame {

    private static Pattern pattern = Pattern.compile("^[\\\\+]?([0-9]+\\\\.?)?[0-9]+$");

    private static boolean isNum(String str) {
        return pattern.matcher(str).matches();
    }

    public static void main(String[] args) {
        System.out.println("请输入你想猜的数字的上限值（正整数）: ");
        int guessTimes = 1;

        Scanner scan = new Scanner(System.in);
        String guessStr = scan.nextLine();
        while (!isNum(guessStr)) {
            System.out.println("你输入的正整数有误, 请重新输入, 退出请按 0: ");
            guessStr = scan.nextLine();
        }

        int upperLimit = Integer.parseInt(guessStr);
        int num = (int) (Math.random() * upperLimit + 1);
        System.out.printf("第 %d 次, 请输入你要猜的数字(1 ~ %d), 退出请按0: \n", guessTimes, upperLimit);
        guessTimes++;
        guessStr = scan.nextLine();
        while (!isNum(guessStr)) {
            System.out.println("你输入的数字有误, 请重新输入, 退出请按 0: ");
            guessStr = scan.nextLine();
        }

        int guess = Integer.parseInt(guessStr);
        while (guess != num) {
            if (guess == 0) {
                break;
            }

            if (guess > num) {
                System.out.println("大了");
            } else {
                System.out.println("小了");
            }

            System.out.printf("第 %d 次, 请输入你要猜的数字(1 ~ %d), 退出请按0: \n", guessTimes, upperLimit);
            guessTimes++;
            guessStr = scan.nextLine();
            while (!isNum(guessStr)) {
                System.out.println("你输入的数字有误, 请重新输入, 退出请按 0: ");
                guessStr = scan.nextLine();
            }
            guess = Integer.parseInt(guessStr);
        }

        if (guess == num) {
            System.out.printf("恭喜你, 猜了 %d 次猜对了, 答案是:\t%d\n", guessTimes - 1, num);
        } else {
            System.out.printf("下次再挑战吧, 答案是:\t%d\n", num);
        }
    }

}