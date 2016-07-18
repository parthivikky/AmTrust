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

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.Status;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,ChangePasswordActivity.class));
    }

    private TextView title,submit;
    private EditText etOldPassword, etNewPassword, etConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initViews();
        title.setText("Change Password");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void initViews() {
        title = _findViewById(R.id.title);
        submit = _findViewById(R.id.submit);
        etOldPassword = _findViewById(R.id.old_password);
        etNewPassword = _findViewById(R.id.new_password);
        etConfirmPassword = _findViewById(R.id.confirm_password);
    }

    private void changePassword(){
        if(TextUtils.isEmpty(etOldPassword.getText().toString())){
            Toast.makeText(this,"Please enter your old password",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etNewPassword.getText().toString())){
            Toast.makeText(this,"Please enter your new password",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etConfirmPassword.getText().toString())){
            Toast.makeText(this,"Please enter your new confirm password",Toast.LENGTH_LONG).show();
        }else if(!etConfirmPassword.getText().toString().equalsIgnoreCase(etNewPassword.getText().toString())){
            Toast.makeText(this,"Please make sure your new password and confirm password should be same",Toast.LENGTH_LONG).show();
        }else {
            Helper.showProgress(this);
            Call<Status> changePasswordCall = RetrofitApi.getApiInterfaceInstance().changePassword(WebConstant.AMS_CHANGE_PASSWORD,
                    AppPreference.getString(this,AppPreference.EMAIL),
                    etOldPassword.getText().toString(), etNewPassword.getText().toString());
            changePasswordCall.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    Helper.dismissProgress();
                    Status status = response.body();
                    if(status.getResult().getStatus().equalsIgnoreCase(WebConstant.SUCCESS)){
                        Toast.makeText(ChangePasswordActivity.this,status.getResult().getMessage(),Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(ChangePasswordActivity.this, status.getResult().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                }
            });
        }
    }

    private <T extends View>T _findViewById(int viewId){
        return  (T)findViewById(viewId);
    }
}
