package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import org.w3c.dom.Text;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.QuickScanPagerAdapter;
import mobello.amtrust.com.utility.ResourceUtils;

public class DeviceDiagnosisActivity extends AppCompatActivity {


    private TextView txtTitle;
    private LinearLayout txtScanHistoryContainer;

    IconTextView textView;

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,DeviceDiagnosisActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_diagnosis);
        initViews();
        txtTitle.setText(R.string.device_health_check);
    }

    private void initViews() {
        txtTitle = _findViewById(R.id.title);
    }


    private <T extends View>T _findViewById(int resId){
        return (T)findViewById(resId);
    }

    public void fullScan(View view) {
        FullScanActivity.start(this);
    }

    private class ViewHolder{
        TextView txtScanName,txtScanDate;
    }

    public void quickScan(View view){
        QuickScanActivity.start(this);
    }
}
