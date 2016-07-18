package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.utility.ResourceUtils;

public class DeviceActivity extends AppCompatActivity {

    private TextView txtAddDevice;

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,DeviceActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        ((TextView)findViewById(R.id.title)).setText(ResourceUtils.getString(R.string.my_device));
        txtAddDevice = (TextView)findViewById(R.id.add_device);
        txtAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceCategoryActivity.start(DeviceActivity.this,false);
            }
        });
    }
}
