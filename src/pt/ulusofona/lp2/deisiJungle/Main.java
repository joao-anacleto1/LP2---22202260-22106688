package pt.ulusofona.lp2.deisiJungle;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random ran = new Random();
        int x = ran.nextInt(6) + 1;

        System.out.println(x);
    }

}
