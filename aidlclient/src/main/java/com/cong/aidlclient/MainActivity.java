package com.cong.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cong.aidlserver.IMyAidlInterface;
import com.cong.aidlserver.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface iMyAidlInterface;

    ServiceConnection conn = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            if(iMyAidlInterface == null){
               return;
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = new Intent();
//        intent.setClass(this, "com.cong.aidlserver.MyAidlService");
        intent.setComponent(new ComponentName("com.cong.aidlserver", "com.cong.aidlserver.MyAidlService"));
        bindService(intent, conn, BIND_AUTO_CREATE);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String helloStr = iMyAidlInterface.sayHello();
                    Toast.makeText(MainActivity.this, helloStr, Toast.LENGTH_SHORT).show();

                    Person person = new Person("zhangsan", 18);
                    List<Person> mPersons = iMyAidlInterface.getPersonList(person);
                    Toast.makeText(MainActivity.this, mPersons.get(0).toString(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
