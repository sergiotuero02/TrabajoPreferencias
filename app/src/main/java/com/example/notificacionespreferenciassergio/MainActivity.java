package com.example.notificacionespreferenciassergio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button defPrefeBTN;
    private Button recPrefeBTN;
    private Button notiBTN;

    private static final String CHANNEL_ID = "29";
    private NotificationManager noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        noti = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        defPrefeBTN = findViewById(R.id.defPrefeBTN);
        recPrefeBTN = findViewById(R.id.recuPrefeBTN);
        notiBTN = findViewById(R.id.notificacionBTN);

        defPrefeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PreferenciasActivity.class);
                startActivity(intent);
            }
        });

        recPrefeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                Log.i("Unico", "Unico = " + preferencias.getBoolean("Unico", true));
                Log.i("Sistema Operarivo", "Sistema Operarivo = " + preferencias.getString("Sistema Operativo", "Windows"));
                Log.i("Version", "Version = " + preferencias.getString("Version", "0"));
            }
        });

        notiBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle("Mensaje de alerta")
                        .setContentText("Prueba")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setTicker("Aviso");

                Intent intent = new Intent(MainActivity.this, NotiActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(intent);
                PendingIntent result = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_MUTABLE);
                builder.setContentIntent(result);
                builder.setAutoCancel(true);
                noti.notify(1, builder.build());

            }
        });

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.canal);
            String description = getString(R.string.descripcion);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}