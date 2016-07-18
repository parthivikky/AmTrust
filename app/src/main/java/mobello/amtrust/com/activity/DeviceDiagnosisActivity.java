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

    private String[] scan_names = {"Nexus 6 - Quick Scan","Nexus 6 - Full Scan","Nexus 6 - Quick Scan","Nexus 6 - Full Scan"};
    private String[] scan_dates= {"27 May 2016","27 May 2016","27 May 2016","27 May 2016"};

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
        setScanHistory();
    }

    private void initViews() {
        txtTitle = _findViewById(R.id.title);
        txtScanHistoryContainer = _findViewById(R.id.scan_history_container);
    }

    private void setScanHistory(){
        ViewHolder holder;
        for (int i = 0; i < scan_names.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.scan_history_row,null);
            holder = new ViewHolder();
            holder.txtScanName = (TextView)view.findViewById(R.id.scan_name);
            holder.txtScanDate = (TextView)view.findViewById(R.id.scan_date);
            holder.txtScanName.setText(scan_names[i]);
            holder.txtScanDate.setText(scan_dates[i]);
            txtScanHistoryContainer.addView(view);
        }
    }

    private <T extends View>T _findViewById(int resId){
        return (T)findViewById(resId);
    }

    private class ViewHolder{
        TextView txtScanName,txtScanDate;
    }

    public void quickScan(View view){
        QuickScanActivity.start(this);
    }
}
