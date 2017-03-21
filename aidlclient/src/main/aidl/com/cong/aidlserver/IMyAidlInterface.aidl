// IMyAidlInterface.aidl
package com.cong.aidlserver;

import com.cong.aidlserver.Person;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String sayHello();

    List<Person> getPersonList(in Person person);

}
