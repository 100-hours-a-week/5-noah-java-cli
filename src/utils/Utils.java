package utils;

import java.util.Random;
import java.util.Scanner;

public class Utils {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static int getInt() {
        int i = Integer.MIN_VALUE;

        while (i == Integer.MIN_VALUE) {
            try {
                i = scanner.nextInt();
            } catch (RuntimeException e) {
                System.out.println("<<system>> 올바른 값을 입력해주세요.");
                scanner.next();
            }
        }

        return i;
    }

    public static String getString() {
        scanner.nextLine();
        return scanner.nextLine();
    }

    public static int getAction(int... ints) {
        int action = Integer.MIN_VALUE;

        while (action == Integer.MIN_VALUE) {
            try {
                action = scanner.nextInt();
            } catch (RuntimeException e) {
                System.out.println("<<system>> 올바른 값을 입력해주세요.");
                scanner.next();
                continue;
            }

            boolean flag = false;

            for (int i : ints) {
                if (action == i) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                action = Integer.MIN_VALUE;

                System.out.println("<<system>> 올바른 값을 입력해주세요.");
            }
        }

        return action;
    }

    public static void printStringArraySlowly(String[] lines, int delay) {
        for (String line : lines) {
            System.out.println(line);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static int getValueBetween0And3() {
        return random.nextInt(4);
    }

    public static int getValueBetween1And5() {
        return random.nextInt(5) + 1;
    }

    public static String getRandomString() {
        int length = random.nextInt(5) + 3;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return sb.toString();
    }

    // i% 확률로 true
    public static boolean getProbability(int i) {
        return random.nextInt(100) < i;
    }
}
