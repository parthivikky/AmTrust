package mobello.amtrust.com.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconButton;

import java.io.File;
import java.io.IOException;

import mobello.amtrust.com.R;

public class MicrophoneTestActivity extends AppCompatActivity {

    private ImageView right;
    private TextView title;
    private IconButton testButton;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private int amplitude;
    private boolean recording = false;
    private final int MY_PERMISSIONS_REQUEST = 10;
    int i = 0;
    int j = 0;
    int time = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone_test);

        initMediaPlayer();

        initUI();
    }

    private void startRecording() {
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        Log.d("Path", outputFile);

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        try {
            myAudioRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myAudioRecorder.start();

    }

    private void stopRecording() {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.track);
    }

    private void initUI() {
        right = (ImageView) findViewById(R.id.right);
        title = (TextView) findViewById(R.id.title);
        title.setText("Microphone");
        testButton = (IconButton) findViewById(R.id.microphone_test_button);


        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermissionsBeforeTest();

            }
        });
    }

    private void requestPermissionsBeforeTest() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ActivityCompat.requestPermissions(MicrophoneTestActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        else
            startTest();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        startTest();
                    } else {
                        Toast.makeText(
                                MicrophoneTestActivity.this,
                                "Permission denied",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(
                            MicrophoneTestActivity.this,
                            "Permission denied",
                            Toast.LENGTH_SHORT).show();
                }

            }
            break;

        }
    }

    private void startTest() {
        Toast.makeText(MicrophoneTestActivity.this, "Called : " + (++j), Toast.LENGTH_SHORT).show();
        if (!recording) {
            recording = true;
            if (time > 0)
                prepareMediaPlayer();
            startPlaying();
            startRecording();
            amplitude = myAudioRecorder.getMaxAmplitude();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    amplitude = myAudioRecorder.getMaxAmplitude();
                    stopRecording();
                    stopPlaying();
                    recording = false;
                    checkRecorderFile();
                }
            }, 4000);
        }
    }

    private void prepareMediaPlayer() {
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        mediaPlayer.stop();
        time = 1;
    }

    private void startPlaying() {
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    private void checkRecorderFile() {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "recording.3gp");
        Log.d("Amplitude", String.valueOf(amplitude));
        /*f.length() == 0*/
        if (amplitude > 0) {
            //Toast.makeText(MicrophoneTestActivity.this, "Recorded.", Toast.LENGTH_SHORT).show();
            right.setImageResource(R.drawable.right);
        } else {
            Toast.makeText(MicrophoneTestActivity.this, "Nothing recorded.", Toast.LENGTH_SHORT).show();
        }
    }

}
