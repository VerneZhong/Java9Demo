package com.java9.part1;

/**
 * @author Mr.zxb
 * @date 2019-04-21 15:44:24
 */
public class Main {


    public static void main(String[] args) {

        test(1);

    }

    public static void test(int type) {
        switch (type) {
            case 1 -> System.out.println(1);
            case 2 -> System.out.println(2);
            case 3 -> System.out.println(3);
            case 4 -> System.out.println(4);
        }
    }




}
