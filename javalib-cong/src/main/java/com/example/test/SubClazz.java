package com.example.test;

/**
 * Created by chenchong-bt on 2017/10/20.
 */

public class SubClazz extends AbsClazz.Holder {
//    @Override
//    public void doIt() {
//        System.out.println("doIt");
//    }

    static {
        System.out.println("static init in subject class...");
    }

    public static void test() {
        so = 8;
        System.out.println("SubClazz test" + so);
    }

    @Override
    public void doSomethingInStaticAbs() {
        so = 9;
        System.out.println("SubClazz do something" + so);
        test();
    }
}
