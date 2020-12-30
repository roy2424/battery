package com.example.battery30;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.battery30.globes;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.NotificationCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ExampleDialog extends AppCompatDialogFragment {
    String str;
    public EditText edittext1;
    Ringtone ringtone;
    private int op=0;
    Context mcontext;
    Context pcontext;
    int count=0;
    public void setop(int a){
        op=a;
    }
    public int getop(){
        return op;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(view)
                .setTitle("Battery Input")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int p = Integer.parseInt(edittext1.getText().toString());
                        broadcast(p);
                        setop(p);
                        String s="Notifies at "+p;
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();


                    }
                });

        edittext1 = view.findViewById(R.id.edit_text1);


        return builder.create();
    }
    public void broadcast(final int a){

        final Ringtone ringtone= RingtoneManager.getRingtone(getContext().getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        BroadcastReceiver broadcastReceiverBattrery = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);

                if (integerBatteryLevel == a && count==0){
                    count+=1;
                    ringtone.play();

                    final Timer t = new Timer();

                    t.schedule(new TimerTask() {
                        public void run() {
                            ringtone.stop();
                            t.cancel();
                        }
                    }, 10000);


                }
            }
        };
        getActivity().registerReceiver(broadcastReceiverBattrery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }


}
