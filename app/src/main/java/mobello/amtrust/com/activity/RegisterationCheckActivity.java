package mobello.amtrust.com.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.easydialog.EasyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.UserExist;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterationCheckActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_READ_PHONE_STATE = 100;
    private static final int LOGIN_REQUEST_CODE = 200;

    private TextView title,check;
    private EditText etEmail, etCountryCode, etMobile, etImei;
    private ImageView question;

    public static void start(Activity activity,int requestCode){
        activity.startActivityForResult(new Intent(activity,RegisterationCheckActivity.class),requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_check);
        initViews();
        title.setText("REGISTER");
        etCountryCode.setText(Helper.getCountryCode(this));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        } else {
            etImei.setText(Helper.getIMEINumber(this));
        }
    }

    private void initViews() {
        title = _findViewById(R.id.title);
        question = _findViewById(R.id.question);
        check = _findViewById(R.id.check);
        etEmail = _findViewById(R.id.email);
        etCountryCode = _findViewById(R.id.country_code);
        etMobile = _findViewById(R.id.mobile);
        etImei = _findViewById(R.id.imei_number);
        check.setOnClickListener(this);
        question.setOnClickListener(this);
    }

    public <T extends View> T _findViewById(int viewId){
        return (T) findViewById(viewId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check:
                userCheck();
//                RegisterActivity.start(RegisterationCheckActivity.this);
                break;
            case R.id.question:
                showPopUp(question);
                break;
        }
    }

    private void userCheck(){
        if(TextUtils.isEmpty(etEmail.getText().toString())){
            Toast.makeText(RegisterationCheckActivity.this, "Email should not be empty", Toast.LENGTH_LONG).show();
        }else if(!Helper.isValidEmail(etEmail.getText().toString())){
            Toast.makeText(RegisterationCheckActivity.this, "Email id is not valid", Toast.LENGTH_LONG).show();
        } else if(TextUtils.isEmpty(etMobile.getText().toString())){
            Toast.makeText(RegisterationCheckActivity.this, "Mobile number should not be empty", Toast.LENGTH_LONG).show();
        }else if(etMobile.getText().toString().length() < 7) {
            Toast.makeText(RegisterationCheckActivity.this,"Mobile number should be minimum 7 numbers",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etImei.getText().toString())){
            Toast.makeText(RegisterationCheckActivity.this, "Can not found IMEI number", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etCountryCode.getText().toString())){
            Toast.makeText(RegisterationCheckActivity.this, "Can't find the country code. Please make sure whether sim card is inserted", Toast.LENGTH_LONG).show();
        }else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", etEmail.getText().toString());
                jsonObject.put("mobile", etCountryCode.getText().toString() + etMobile.getText().toString());
                jsonObject.put("imei", etImei.getText().toString());
                jsonObject.put("channel", "AMS");
                jsonObject.put("cf_1283", "70*134");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Helper.showProgress(this);
            RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
            Call<UserExist> userExistsCall = apiInterface.checkUserExists(WebConstant.USER_EXIST, AppPreference.getString(this, AppPreference.SESSION_NAME),
                    jsonObject.toString(), WebConstant.AMS_COUNTRY_MODULE);
            userExistsCall.enqueue(new Callback<UserExist>() {
                @Override
                public void onResponse(Call<UserExist> call, Response<UserExist> response) {
                    Helper.dismissProgress();
                    UserExist userExist = response.body();
                    Log.i("response", userExist.getSuccess() + "");
                    Log.i("message", userExist.getResult().getMessage() + "");
                    Toast.makeText(RegisterationCheckActivity.this, userExist.getResult().getMessage(), Toast.LENGTH_LONG).show();
                    if (userExist.getResult().getIsExistUser() == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("email", etEmail.getText().toString());
                        bundle.putString("mobile", etCountryCode.getText().toString() + etMobile.getText().toString());
                        bundle.putString("imei", etImei.getText().toString());
                        RegisterActivity.start(RegisterationCheckActivity.this, bundle,LOGIN_REQUEST_CODE);
                    }
                }

                @Override
                public void onFailure(Call<UserExist> call, Throwable t) {
                    Helper.dismissProgress();
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_READ_PHONE_STATE){
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                etImei.setText(Helper.getIMEINumber(this));
            }
        }else{
            Toast.makeText(this,"Need your permission to get IMEI information",Toast.LENGTH_LONG).show();
        }
    }

    private void showPopUp(final View widgetView) {
        View view = this.getLayoutInflater().inflate(R.layout.dialog_text_only, null);
        final EasyDialog dialog = new EasyDialog(RegisterationCheckActivity.this)
                .setLayout(view)
                .setBackgroundColor(Color.parseColor("#ffffff"))
                .setLocationByAttachedView(widgetView)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 500, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(24, 24)
                .setOutsideColor(Color.parseColor("#80000000"))
                .show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == LOGIN_REQUEST_CODE){
            setResult(RESULT_OK);
            finish();
        }

    }
}
