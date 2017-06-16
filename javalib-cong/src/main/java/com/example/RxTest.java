package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;

public class RxTest {
    public static void main(String[] args) {
//        testConcat();
        testDelay();
    }

    private static void testDelay() {
        List<String> strings = Arrays.asList("a", "b", "a", "d", "d", "d", "e");
        Observable.range(0, 10)
                .distinctUntilChanged()
                .subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("onComplete"));
    }

    private static void testConcat() {
        List<Integer> originalList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Observable.concat(
                Observable.just(originalList.subList(0, 3))
                        .flatMap(RxTest::format),
                Observable.from(originalList.subList(3, 6))
                        .map(integer -> integer *= 3),
                Observable.from(originalList.subList(6, 9))
                        .map(integer -> integer *= 4)
        ).toList().subscribe(System.out::print);
    }

    private static Observable<? extends Integer> format(List<Integer> integers) {
        List<Integer> temp = new ArrayList<>();
        temp.addAll(integers);
        temp.remove(1);
        temp.add(integers.get(1) * 2);
        return Observable.from(temp);
    }
}

