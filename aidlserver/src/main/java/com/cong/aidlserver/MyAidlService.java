package com.cong.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {
    public MyAidlService() {
    }

    IMyAidlInterface.Stub iMyAidlInterface = new IMyAidlInterface.Stub(){

        @Override
        public String sayHello() throws RemoteException {
            return "hello AIDL";
        }

        @Override
        public List<Person> getPersonList(Person person) throws RemoteException {
            List<Person> persons = new ArrayList<Person>();
            persons.add(person);
            return persons;
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iMyAidlInterface;
    }
}
