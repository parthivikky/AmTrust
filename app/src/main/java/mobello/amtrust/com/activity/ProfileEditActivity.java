package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.ProfileUpdate;
import mobello.amtrust.com.model.ProfileInfo;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_GALLERY = 1;

    public static void start(Activity activity, ProfileInfo.Result_ info) {
        activity.startActivity(new Intent(activity, ProfileEditActivity.class)
                .putExtra("profile_info", info));
    }

    private EditText etFirstName, etLastName, etAddress, etCity, etState, etCountry;
    private TextView txtCountryCode;
    private ImageView imgProfilePic, imgGallery, imgCamera, imgDone;
    private ProfileInfo.Result_ profileInfo;
    private File file = null;
    private String imagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        initViews();
        profileInfo = getIntent().getParcelableExtra("profile_info");

        Glide.with(this).load(profileInfo.getProfile_picture()).placeholder(R.drawable.placeholder).into(imgProfilePic);
        etFirstName.setText(profileInfo.getFirstname());
        etLastName.setText(profileInfo.getLastname());
        etAddress.setText(profileInfo.getAddress());
        etCity.setText(profileInfo.getCity());
        etState.setText(profileInfo.getState());
        etCountry.setText(profileInfo.getCountry());
        txtCountryCode.setText(profileInfo.getCountrycode());
    }

    private void initViews() {
        etFirstName = _findViewById(R.id.first_name);
        etLastName = _findViewById(R.id.last_name);
        etAddress = _findViewById(R.id.address);
        etCity = _findViewById(R.id.city);
        etState = _findViewById(R.id.state);
        etCountry = _findViewById(R.id.country);
        txtCountryCode = _findViewById(R.id.country_code);
        imgProfilePic = _findViewById(R.id.profile_image);
        imgGallery = _findViewById(R.id.gallery);
        imgDone = _findViewById(R.id.done);
        imgDone.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
    }

    private <T extends View> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gallery:
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture From"), REQUEST_GALLERY);
                break;
            case R.id.done:
                updateToServer();
                break;
        }
    }

    private void updateToServer() {
        Helper.showProgress(this);
        JSONObject jsonObject = formData();
        RequestBody reqFile = null;
        if (imagePath != null) {
            Log.i("path",imagePath);
            file = new File(imagePath);
            reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("filedata",file.getName(), reqFile);
        RequestBody operationBody = RequestBody.create(MediaType.parse("text/plain"), WebConstant.PROFILE_UPDATE);
        RequestBody sessionBody = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getString(this, AppPreference.SESSION_NAME));
        RequestBody elementBody = RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString());
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<ProfileUpdate> updateCall = apiInterface.updateProfile(operationBody, sessionBody, elementBody, fileBody);
        updateCall.enqueue(new Callback<ProfileUpdate>() {
            @Override
            public void onResponse(Call<ProfileUpdate> call, Response<ProfileUpdate> response) {
                Helper.dismissProgress();
                ProfileUpdate profileUpdate = response.body();
                if (profileUpdate.getSuccess()) {
                    if (profileUpdate.getResult().getStatus().equalsIgnoreCase(WebConstant.SUCCESS)) {
                        Toast.makeText(ProfileEditActivity.this, profileUpdate.getResult().getMessage(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileUpdate> call, Throwable t) {
                Helper.dismissProgress();
                t.printStackTrace();
            }
        });
    }

    private JSONObject formData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", profileInfo.getEmail());
            jsonObject.put("first_name", etFirstName.getText().toString());
            jsonObject.put("last_name", etLastName.getText().toString());
            jsonObject.put("country_code", etCountry.getText().toString());
            jsonObject.put("mobile_number", profileInfo.getMobile());
            jsonObject.put("country", etCountry.getText().toString());
            jsonObject.put("state", etState.getText().toString());
            jsonObject.put("city", etCity.getText().toString());
            jsonObject.put("address", etAddress.getText().toString());
            jsonObject.put("filename", "profile_pic");
            jsonObject.put("contactid", profileInfo.getContactid());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_GALLERY:
                    Uri uri = data.getData();
                    imagePath = Helper.getPath(getApplicationContext(),uri);
                    Log.i("path",imagePath);
                    Bitmap bitmap = Helper.rotateBitmap(ProfileEditActivity.this, imagePath);
                    imgProfilePic.setImageBitmap(bitmap);
                    /*try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                        imagePath = selectedImage.getPath();
                        Log.i("path",imagePath);
                        imgProfilePic.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    break;
            }
        }
    }
}
