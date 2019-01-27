package com;


public class AAA {

    static String a1 = "s";
    static String a2 = "s";
    static String a3 = new String("s");

    static int a = 1;
    public static void main(String[] args) {
        Integer a = 3;
        Integer b = new Integer(3);
        System.out.println(a == b);
        System.out.println(a.hashCode() + " " + b.hashCode());


        Integer i01 = 59;
        int i02 = 59;
        Integer i03 =Integer.valueOf(59);
        Integer i04 = new Integer(59);

        System.out.println(i01 == i02);
        System.out.println(i01 == i03);
        System.out.println(i01 == i04);
        System.out.println(i03 == i04);
        System.out.println(i02 == i03);
        System.out.println(i02 == i04);

        System.out.println(new AAA().a);

        str();

    }

    public static void str(){
//        String a1 = "s";
//        String a2 = new String("s");
//        String a3 = "s";
        System.out.println(a1 == a2);
        System.out.println(a1 == a3);
    }

}
