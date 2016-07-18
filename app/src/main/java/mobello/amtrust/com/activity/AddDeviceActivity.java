package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobello.amtrust.com.R;

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_DEVICE_CATEGORY  = 100;
    private RelativeLayout categoryContainer;
    private TextView txtCategory;

    public static void start(Activity activity,String category){
        activity.startActivity(new Intent(activity,AddDeviceActivity.class)
            .putExtra("category",category));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ((TextView)findViewById(R.id.title)).setText(getString(R.string.add_new_device));
        initViews();

        txtCategory.setText(getIntent().getStringExtra("category"));
    }

    private void initViews() {
        categoryContainer = _findViewById(R.id.category_container);
        txtCategory = _findViewById(R.id.category);
        categoryContainer.setOnClickListener(this);
    }

    private <T extends View>T _findViewById(int resId){
        return (T) findViewById(resId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.category_container:
                DeviceCategoryActivity.startForResult(AddDeviceActivity.this,REQUEST_DEVICE_CATEGORY,true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_DEVICE_CATEGORY && resultCode == RESULT_OK){
            txtCategory.setText(data.getStringExtra("category"));

        }
    }
}
