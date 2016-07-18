package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int LOGIN_REQUEST_CODE = 1;

    private TextView login, register;
    private String token;

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getChallengeToken();
    }

    private void initViews() {
        login = _findViewById(R.id.login);
        register = _findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

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
}
