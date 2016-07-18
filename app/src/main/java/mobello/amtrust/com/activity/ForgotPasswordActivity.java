package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.Status;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,ForgotPasswordActivity.class));
    }

    private TextView title,submit;
    private EditText etEmail,etMobile,etLName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        title.setText("Forget Password");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassword();
            }
        });
    }

    private void initViews() {
        title = _findViewById(R.id.title);
        submit = _findViewById(R.id.submit);
        etEmail = _findViewById(R.id.email);
        etMobile = _findViewById(R.id.mobile);
        etLName = _findViewById(R.id.lname);
    }

    private <T extends View>T _findViewById(int viewId){
        return  (T)findViewById(viewId);
    }

    private void forgetPassword(){
        if(TextUtils.isEmpty(etEmail.getText().toString())){
            Toast.makeText(this, "Please enter the E-Mail address", Toast.LENGTH_LONG).show();
        }else if(!Helper.isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(this, "Email id is not valid", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etMobile.getText().toString())){
            Toast.makeText(this, "Please enter the mobile number", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etLName.getText().toString())){
            Toast.makeText(this, "Please enter the last name", Toast.LENGTH_LONG).show();
        }else{
            Helper.showProgress(this);
            Call<Status> fpCall = RetrofitApi.getApiInterfaceInstance().forgetPassword(WebConstant.FORGET_PASSWORD,
                    AppPreference.getString(this,AppPreference.SESSION_NAME),etEmail.getText().toString(),
                    etMobile.getText().toString(),etLName.getText().toString());
            fpCall.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    Helper.dismissProgress();
                    Status status = response.body();
                    if(status.getSuccess()){
                        Toast.makeText(ForgotPasswordActivity.this, status.getResult().getMessage(), Toast.LENGTH_LONG).show();
                        if(status.getResult().getStatus().equalsIgnoreCase(WebConstant.SUCCESS)) {
                            finish();
                        }
                    }else{
                        Toast.makeText(ForgotPasswordActivity.this, status.getError().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
