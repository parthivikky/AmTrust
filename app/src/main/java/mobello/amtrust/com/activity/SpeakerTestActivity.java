package mobello.amtrust.com.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import mobello.amtrust.com.R;

public class SpeakerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_test);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.isSpeakerphoneOn())
            Toast.makeText(SpeakerTestActivity.this, "Working", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(SpeakerTestActivity.this, "Not Working", Toast.LENGTH_SHORT).show();

    }
}
