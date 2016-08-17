package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mobello.amtrust.com.R;
import mobello.amtrust.com.fragment.HomeFragment;
import mobello.amtrust.com.model.CustomerLogin;
import mobello.amtrust.com.model.Status;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView login,forgotPassword;
    private EditText etEmail,etPassword;

    public static void start(Activity activity,int requestCode){
        activity.startActivityForResult(new Intent(activity,LoginActivity.class),requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        login = _findViewById(R.id.login);
        forgotPassword = _findViewById(R.id.forgot_password);
        etEmail = _findViewById(R.id.email);
        etPassword = _findViewById(R.id.password);
        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    public <T extends View> T _findViewById(int viewId){
        return (T) findViewById(viewId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
//                HomeActivity.start(LoginActivity.this);
                break;
            case R.id.forgot_password:
                ForgotPasswordActivity.start(LoginActivity.this);
                break;
        }
    }

    private void login(){

        if(TextUtils.isEmpty(etEmail.getText().toString())){
            Toast.makeText(LoginActivity.this, "Please enter the E-Mail address", Toast.LENGTH_LONG).show();
        }else if(!Helper.isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Email id is not valid", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(LoginActivity.this, "Please enter the password", Toast.LENGTH_LONG).show();
        }else {
            Helper.showProgress(this);
            Call<CustomerLogin> loginCall = RetrofitApi.getApiInterfaceInstance().customerLogin(WebConstant.CUSTOMER_LOGIN,
                    etEmail.getText().toString(),etPassword.getText().toString());
            loginCall.enqueue(new Callback<CustomerLogin>() {
                @Override
                public void onResponse(Call<CustomerLogin> call, Response<CustomerLogin> response) {
                    Helper.dismissProgress();
                    CustomerLogin customerLogin = response.body();
                    if(customerLogin.getSuccess()) {
                        if (customerLogin.getResult().getStatus().equalsIgnoreCase(WebConstant.SUCCESS)) {
                            CustomerLogin.Data data = customerLogin.getResult().getData();
                            AppPreference.setString(LoginActivity.this, AppPreference.EMAIL, data.getEmail());
                            AppPreference.setString(LoginActivity.this, AppPreference.FIRSTNAME, data.getFirstname());
                            AppPreference.setString(LoginActivity.this, AppPreference.LASTNAME, data.getLastname());
                            AppPreference.setString(LoginActivity.this, AppPreference.MOBILE, data.getMobile());
                            AppPreference.setString(LoginActivity.this, AppPreference.IS_CHANGE_PASSWORD, data.getIsChangePassword());
                            AppPreference.setBoolean(LoginActivity.this, AppPreference.IS_LOGIN, true);
                            setResult(RESULT_OK);
                            HomeActivity.start(LoginActivity.this);
                            finish();
                        } else
                            Toast.makeText(LoginActivity.this, customerLogin.getResult().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CustomerLogin> call, Throwable t) {
                }
            });
        }
    }
}
