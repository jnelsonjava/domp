package com.jnelsonjava.domp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private Activity activity;

    private ConstraintLayout rootLayout;
    private Button triggerMusicButton;

    private MediaPlayer mediaPlayer;

    private static final int MY_CODE = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        activity = MainActivity.this;

        rootLayout = findViewById(R.id.main_activity_layout);

        triggerMusicButton = findViewById(R.id.music_trigger_button);
        triggerMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    System.out.println("entering check permission");
                    checkPermission();
                } else {
                    playMusic();
                }
            }
        });

//        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.hm);
//        mediaPlayer.start();

        //-----------------------------------------------------

//        System.out.println(MediaStore.Audio.Media.getContentUri(""));


//        ContentResolver contentResolver = getContentResolver();
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Cursor cursor = contentResolver.query(uri, null, null, null, null);
//        if (cursor == null) {
//            // query failed, handle error.
//        } else if (!cursor.moveToFirst()) {
//            // no media on the device
//        } else {
//            int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
//            int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
//            do {
//                long thisId = cursor.getLong(idColumn);
//                String thisTitle = cursor.getString(titleColumn);
//                // ...process entry...
//            } while (cursor.moveToNext());
//        }
//
//        long id = MediaStore.Audio.Media.;
//        Uri contentUri = ContentUris.withAppendedId(
//                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
//
//        AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .setUsage(AudioAttributes.USAGE_MEDIA)
//                .build();
//
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioAttributes(
//                audioAttributes
//        );
//        try {
//            mediaPlayer.setDataSource(getApplicationContext(), contentUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        mediaPlayer.start();

        //------------------------------------------------------------

    }

    protected void playMusic() {
        File file = new File("/storage/emulated/0/Music/OrangeForge/09 Semi-Permanent Makeup.mp3");
        System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
//        File file1 = Environment.getExternalStorageDirectory();
        System.out.println("in play music!!!!");
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);

            mediaPlayer = MediaPlayer.create(context, uri);

            mediaPlayer.start();;

            Toast.makeText(context, "Music playing", Toast.LENGTH_SHORT).show();
        }
    }

    protected void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        System.out.println(PackageManager.PERMISSION_GRANTED);
        System.out.println(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED);
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Need permission to read external storage");
                    builder.setTitle("Please give permission");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_CODE
                            );
                        }
                    });
                    builder.setNeutralButton("Cancel", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    ActivityCompat.requestPermissions(
                            activity,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_CODE
                    );
                }
            } else {
                System.out.println("this should go to play music");
                playMusic();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                playMusic();
            }
        }
    }
}