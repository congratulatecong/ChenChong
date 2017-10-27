package com.example.test;

/**
 * Created by chenchong-bt on 2017/10/20.
 */

public abstract class AbsClazz {

    static {
        System.out.println("static init in abstract class...");
    }

//    public abstract void doIt();

    public static void doOther() {
        System.out.println("AbsClazz" + Holder.so);
    }

    public abstract static class Holder {
        public static int so = 10;

        static {
            System.out.println("Holder" + so);
        }

        public static void staticHolder() {
            System.out.println("static holder");
        }

        public abstract void doSomethingInStaticAbs();
    }
}
