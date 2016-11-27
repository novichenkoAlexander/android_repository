package my.com.friendsrequest.utils;

import java.util.Random;

public class RandomGenerator {

    private static final Random random = new Random();

    public static String generator(){
        String randomNumber;
        randomNumber = String.valueOf(random.nextInt(100000000));
        return randomNumber;
    }
}
