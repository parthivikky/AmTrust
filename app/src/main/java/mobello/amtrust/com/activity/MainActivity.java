package mobello.amtrust.com.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.GetChallenge;
import mobello.amtrust.com.model.SessionLogin;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.BugSender;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback, MediaPlayer.OnCompletionListener {

    private static final int LOGIN_REQUEST_CODE = 1;

    private TextView login, register;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private String token,targetUri;

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,MainActivity.class));
        StringBuffer buffer = new StringBuffer("Game Plan");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initViews();
        getChallengeToken();
        targetUri = "android.resource://" + getPackageName() + "/" + R.raw.welcome_video;

        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(targetUri));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    private void initViews() {
        surfaceView = _findViewById(R.id.video_view);
        login = _findViewById(R.id.login);
        register = _findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
    }

    private void getChallengeToken() {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<GetChallenge> challengeCall = apiInterface.getChallenge(WebConstant.GET_CHALLENGE, WebConstant.WEBSERVICE_USRENAME);
        challengeCall.enqueue(new Callback<GetChallenge>() {
            @Override
            public void onResponse(Call<GetChallenge> call, Response<GetChallenge> response) {
                GetChallenge getChallenge = response.body();
                if (getChallenge.getSuccess()) {
                    token = getChallenge.getResult().getToken();
                    Log.i("token", token);
                    String accessKey = Helper.convertMd5(token + WebConstant.DEFAULT_ACCESSKEY);
                    Log.i("accessKey", accessKey);
                    getLogin(accessKey);
                }
            }

            @Override
            public void onFailure(Call<GetChallenge> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getLogin(String accessKey) {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<SessionLogin> loginCall = apiInterface.login(WebConstant.LOGIN, WebConstant.WEBSERVICE_USRENAME, accessKey);
        loginCall.enqueue(new Callback<SessionLogin>() {
            @Override
            public void onResponse(Call<SessionLogin> call, Response<SessionLogin> response) {
                SessionLogin login = response.body();
                if (login.getSuccess()) {
                    Log.i("session", login.getResult().getSessionName());
                    Log.i("id", login.getResult().getUserId());
                    AppPreference.setString(getApplicationContext(), AppPreference.SESSION_NAME, login.getResult().getSessionName());
                } else {
                    Log.i("error", login.getError().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SessionLogin> call, Throwable t) {
                t.printStackTrace();
                Helper.dismissProgress();
            }
        });
    }


    public <T extends View> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                LoginActivity.start(MainActivity.this,LOGIN_REQUEST_CODE);
                break;
            case R.id.register:
                RegisterationCheckActivity.start(MainActivity.this,LOGIN_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK)
            finish();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}
