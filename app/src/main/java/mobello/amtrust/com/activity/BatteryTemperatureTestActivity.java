package mobello.amtrust.com.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mobello.amtrust.com.R;

public class BatteryTemperatureTestActivity extends AppCompatActivity {

    private TextView title;
    private TextView batteryTemperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_to_all_test);
        initUI();
    }

    private void initUI() {
        title = (TextView) findViewById(R.id.title);
        title.setText("Battery Temperature");
    }

    public void onTestClick(View view) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, filter);
        int batteryTemperature = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        if (batteryTemperature != -1) {
//            int batTemp = batteryTemperature / 10;
            Toast.makeText(BatteryTemperatureTestActivity.this, "Temperature : " + (batteryTemperature / 10) + " Degree Centigrade", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }

    public void onStopClick(View view) {
    }
}
