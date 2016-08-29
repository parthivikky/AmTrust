package mobello.amtrust.com.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.joanzapata.iconify.widget.IconTextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.ViewPagerAdapter;
import mobello.amtrust.com.widget.NonSwipableViewPager;

public class FullScanActivity extends AppCompatActivity {

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, FullScanActivity.class));
    }

    private IconTextView audioButton;
    private IconTextView batteryButton;
    private IconTextView cameraButton;
    private IconTextView connectButton;
    private IconTextView deviceButton;
    private IconTextView displayButton;
    private IconTextView gpsButton;
    private IconTextView sensorButton;
    private TextView testName;
    private NonSwipableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_scan);
        initViews();
        ((TextView)findViewById(R.id.title)).setText("Full Scan");
        viewPager.setOffscreenPageLimit(7);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        changeSelectedButton(audioButton);
    }

    private <T extends View>T _findViewById(int viewId){
        return (T)findViewById(viewId);
    }


    private void initViews() {
        viewPager = _findViewById(R.id.view_pager);
        testName = _findViewById(R.id.test_name);
        audioButton = _findViewById(R.id.audio_button);
        batteryButton = _findViewById(R.id.battery_button);
        cameraButton = _findViewById(R.id.camera_button);
        connectButton = _findViewById(R.id.connect_button);
        deviceButton = _findViewById(R.id.device_button);
        displayButton = _findViewById(R.id.display_button);
        gpsButton = _findViewById(R.id.gps_button);
        sensorButton = _findViewById(R.id.sensor_button);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedButton(audioButton);
            }
        });
        batteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedButton(batteryButton);
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedButton(cameraButton);
            }
        });
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedButton(connectButton);
            }
        });
        deviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedButton(deviceButton);
            }
        });
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedButton(displayButton);
            }
        });
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedButton(gpsButton);
            }
        });
        sensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedButton(sensorButton);
            }
        });
    }

    private void changeSelectedButton(IconTextView selectedButton) {
        audioButton.setSelected(false);
        batteryButton.setSelected(false);
        cameraButton.setSelected(false);
        connectButton.setSelected(false);
        deviceButton.setSelected(false);
        displayButton.setSelected(false);
        gpsButton.setSelected(false);
        sensorButton.setSelected(false);
        selectedButton.setSelected(true);
        changePages(selectedButton.getId());
    }

    private void changePages(int id) {
        switch (id) {
            case R.id.audio_button:
                testName.setText(getResources().getString(R.string.audio));
                viewPager.setCurrentItem(0);
                break;
            case R.id.battery_button:
                testName.setText(getResources().getString(R.string.battery));
                viewPager.setCurrentItem(1);
                break;
            case R.id.camera_button:
                testName.setText(getResources().getString(R.string.camera));
                viewPager.setCurrentItem(2);
                break;
            case R.id.connect_button:
                testName.setText(getResources().getString(R.string.connect));
                viewPager.setCurrentItem(3);
                break;
            case R.id.device_button:
                testName.setText(getResources().getString(R.string.device));
                viewPager.setCurrentItem(4);
                break;
            case R.id.display_button:
                testName.setText(getResources().getString(R.string.display));
                viewPager.setCurrentItem(5);
                break;
            case R.id.gps_button:
                testName.setText(getResources().getString(R.string.gps));
                viewPager.setCurrentItem(6);
                break;
            case R.id.sensor_button:
                testName.setText(getResources().getString(R.string.sensor));
                viewPager.setCurrentItem(7);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
