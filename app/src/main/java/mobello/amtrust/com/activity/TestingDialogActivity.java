package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

import mobello.amtrust.com.R;
import mobello.amtrust.com.utility.Constant;
import mobello.amtrust.com.utility.Version;

public class TestingDialogActivity extends AppCompatActivity {

    public static void startForResult(Activity activity, int request_code) {
        activity.startActivityForResult(new Intent(activity, TestingDialogActivity.class)
                .putExtra("request_code", request_code), request_code);
    }

    private TextView txtTitle, txtInfo, txtCounter;
    private Button btnStart, btnOk;
    private EditText edInput;
    private ImageView imgHeader;
    private LinearLayout tickContainer;
    private TextToSpeech textToSpeech = null;
    private int random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_testing_dialog);
        initViews();
        int request_code = getIntent().getIntExtra("request_code", 0);
        switch (request_code) {
            case Constant.CALL_RECEIVER_REQUEST_CODE:
                callReceiverProcess();
                break;
        }
    }

    private void callReceiverProcess() {
        txtTitle.setText("Call Receiver");
        textToSpeech = new TextToSpeech(TestingDialogActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int result) {
                if (result == TextToSpeech.SUCCESS)
                    textToSpeech.setLanguage(Locale.US);
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnOk.setVisibility(View.VISIBLE);
                edInput.setVisibility(View.VISIBLE);
                txtCounter.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.GONE);
                random = (int) (Math.random() * 9000) + 1000;
                new CountDownTimer(4000, 1000) {
                    @Override
                    public void onTick(long l) {
                        txtCounter.setText("" + l / 1000);
                    }

                    @Override
                    public void onFinish() {
                        txtCounter.setText("0");
                        txtCounter.setVisibility(View.GONE);
                        if (Version.isBelowLollipop()) {
                            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                            audioManager.setSpeakerphoneOn(false);
                            textToSpeech.speak(String.valueOf(random), TextToSpeech.QUEUE_FLUSH, null);
                        }
                        else {
                            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                            audioManager.setSpeakerphoneOn(false);
                            textToSpeech.speak(String.valueOf(random), TextToSpeech.QUEUE_FLUSH, null, null);
                        }
                    }
                }.start();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int userInput = Integer.parseInt(edInput.getText().toString());
                    if(random == userInput){
                        showTick();
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(TestingDialogActivity.this,"Please enter number only",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initViews() {
        txtTitle = _findViewById(R.id.txt_title);
        txtCounter = _findViewById(R.id.txt_counter);
        txtInfo = _findViewById(R.id.info);
        btnStart = _findViewById(R.id.start);
        btnOk = _findViewById(R.id.ok);
        edInput = _findViewById(R.id.input);
        imgHeader = _findViewById(R.id.top_image);
        tickContainer = _findViewById(R.id.tickContainer);
    }

    private <T extends View> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

    public void showTick() {
        tickContainer.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tickContainer.setVisibility(View.GONE);
                setResult(RESULT_OK);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.stop();
    }
}
