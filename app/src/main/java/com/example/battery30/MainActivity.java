package com.example.battery30;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import com.example.battery30.globes;

public class MainActivity extends AppCompatActivity {
    private Switch switch1;
    Context mcontext;
    ProgressBar simpleprogressbar;
    EditText et1;
    TextView tv1;
    int x=0;
    public void setx(int a){
        x=a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=(TextView)findViewById(R.id.textView1);
        mcontext=this;
        simpleprogressbar =(ProgressBar)findViewById(R.id.progressBar3);
        simpleprogressbar.setMax(100);
        simpleprogressbar.setIndeterminate(false);
        simpleprogressbar.setProgress(0);
        broadcast();
        et1=(EditText)findViewById(R.id.edit_text1);
        switch1=(Switch)findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    openDialog();
                    Toast.makeText(mcontext.getApplicationContext(),
                            "notifier is on", Toast.LENGTH_LONG).show();
                }
    }
});


    }
    public void openDialog() {
        final ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
        exampleDialog.mcontext=mcontext;
        final Timer t = new Timer();

        t.schedule(new TimerTask() {
            public void run() {
                int p;
                p=exampleDialog.getop();
                setx(p);


                t.cancel();
            }
        }, 5000);

    }
    public void broadcast(){

        BroadcastReceiver broadcastReceiverBattrery = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
                simpleprogressbar.setProgress(integerBatteryLevel);
                String str= integerBatteryLevel+"%";
                tv1.setText(str);
                setoff(integerBatteryLevel);

            }
        };
        registerReceiver(broadcastReceiverBattrery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
    public void setoff(int a){
        if(x==a){
            switch1.setChecked(false);
        }
    }
}