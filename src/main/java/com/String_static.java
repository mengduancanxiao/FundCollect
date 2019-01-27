package com;

import static com.AAA.*;

public class String_static {
    static String str1 = new String("abc");
    static String str4 = new String("abc");
    String str7 = "abc";
    static String str8 = "abc";
    final String str9 = "abc";

    public static void main(String[] args) {
        String str3 = new String("abc");
        String str6 = new String("abc");

        String str2 = "abc";
        String str5 = "abc";

        System.out.println(str1.equals(str2)); // equals方法比较的是指向的对象，也就是其中的内容，与地址无关
        System.out.println(str2 == str5); // str2 和 str5 的对象存于数据区，但由于内容相同，故指向了同一块内存
        System.out.println(str3 == str6); // str3 和 str6 new出来 的对象都存于堆区中，虽然对象内容相同，但在堆中指向并不是一块内存
        System.out.println(str1 == str4);//str1 和 str4 因为是静态的，虽然也是new出来的，且其对象内容也相同，也都存于数据区，但是地址指向完全不同   与str2 和str5 的情况形成比较；  与str3和str6 虽然不在同一区，但是情况类似
        System.out.println(str1 == str3);//str1 存在于数据区 ，str3 存在于堆区，地址指向完全不同
        System.out.println(str1 == str2);//str1 因为是静态的 存于数据区   str2是字符串常量 也存于数据区  且对象内容等，但地址指向并不相同；与str2 和str5 的情况形成比较；，与str1 和 str4 、str3和str6类似
        System.out.println(str2 == new String_static().str7);
        System.out.println(str2 == str8);
        System.out.println(new String_static().str7 == str8);
        System.out.println(str1 == str8);
        System.out.println(str8 == new String_static().str9);

    }
}
