package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.DeviceCategoryAdapter;

public class DeviceCategoryActivity extends AppCompatActivity {

    private GridView gridView;

    public static void start(Activity activity,boolean isRequest){
        activity.startActivity(new Intent(activity,DeviceCategoryActivity.class)
                .putExtra("is_request",isRequest));
    }

    public static void startForResult(Activity activity,int requestCode,boolean isRequest){
        activity.startActivityForResult(new Intent(activity,DeviceCategoryActivity.class)
                .putExtra("is_request",isRequest),requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_category);
        ((TextView)findViewById(R.id.title)).setText(R.string.device);
        boolean isRequest = getIntent().getBooleanExtra("is_request",false);
        gridView = (GridView)findViewById(R.id.grid_view);
        gridView.setAdapter(new DeviceCategoryAdapter(DeviceCategoryActivity.this,isRequest));

    }
}
