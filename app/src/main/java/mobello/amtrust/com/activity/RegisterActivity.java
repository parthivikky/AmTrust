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

import org.json.JSONException;
import org.json.JSONObject;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.CreateEntity;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFirstName, etLastName, etAddress, etCity, etCountry, etPostal;
    private TextView title,submit;
    private String email, mobile, imei;

    public static void start(Activity activity, Bundle bundle,int requestCode){
        activity.startActivityForResult(new Intent(activity,RegisterActivity.class)
                .putExtra("email",bundle.getString("email"))
                .putExtra("mobile",bundle.getString("mobile"))
                .putExtra("imei",bundle.getString("imei")),requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        title.setText("REGISTER");
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        mobile = intent.getStringExtra("mobile");
        imei = intent.getStringExtra("imei");
    }

    private void initViews() {
        title = _findViewById(R.id.title);
        submit = _findViewById(R.id.submit);
        etFirstName = _findViewById(R.id.fname);
        etLastName = _findViewById(R.id.lname);
        etAddress = _findViewById(R.id.address);
        etCity = _findViewById(R.id.city);
        etCountry = _findViewById(R.id.country);
        etPostal = _findViewById(R.id.postal);
        submit.setOnClickListener(this);
    }

    public <T extends View> T _findViewById(int viewId){
        return (T) findViewById(viewId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                register();
                break;
        }
    }

    private void register() {
        if(TextUtils.isEmpty(etFirstName.getText().toString())){
            Toast.makeText(RegisterActivity.this,"Please enter the first name",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etLastName.getText().toString())){
            Toast.makeText(RegisterActivity.this,"Please enter the last name",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etAddress.getText().toString())){
            Toast.makeText(RegisterActivity.this,"Please enter the address",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etCity.getText().toString())){
            Toast.makeText(RegisterActivity.this,"Please enter the city",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etCountry.getText().toString())){
            Toast.makeText(RegisterActivity.this,"Please enter the country",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etPostal.getText().toString())){
            Toast.makeText(RegisterActivity.this,"Please enter the postal code",Toast.LENGTH_LONG).show();
        } else{
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("firstname",etFirstName.getText().toString());
                jsonObject.put("lastname",etLastName.getText().toString());
                jsonObject.put("mailingstreet",etAddress.getText().toString());
                jsonObject.put("mailingcity",etCity.getText().toString());
                jsonObject.put("mailingstate","");
                jsonObject.put("mailingcountry",etCountry.getText().toString());
                jsonObject.put("mailingpobox",etPostal.getText().toString());
                jsonObject.put("email",email);
                jsonObject.put("mobile",mobile);
                jsonObject.put("cf_1260",imei);
                jsonObject.put("assigned_user_id","19x11");
                jsonObject.put("cf_1283","70x134");
                jsonObject.put("cf_1277","1");
                jsonObject.put("cf_1281","6s");
                jsonObject.put("cf_1279","Iphone");
                jsonObject.put("portal","1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
            Call<CreateEntity> userExistsCall = apiInterface.register(WebConstant.CREATE, AppPreference.getString(this,AppPreference.SESSION_NAME),
                    jsonObject.toString(),WebConstant.CONTACTS_MODULE);
            userExistsCall.enqueue(new Callback<CreateEntity>() {
                @Override
                public void onResponse(Call<CreateEntity> call, Response<CreateEntity> response) {
                    CreateEntity entity = response.body();
                    if(entity.getSuccess()){
//                        AppPreference.setBoolean(RegisterActivity.this,AppPreference.IS_LOGIN,true);
                        setResult(RESULT_OK);
                        HomeActivity.start(RegisterActivity.this);
                        finish();
                    }
//                    Toast.makeText(RegisterActivity.this,entity.getResult().getEmail() + "is created",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CreateEntity> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
