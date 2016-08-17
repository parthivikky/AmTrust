package mobello.amtrust.com.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.pageindicator.indicator.FlycoPageIndicaor;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.QuickScanPagerAdapter;
import mobello.amtrust.com.fragment.SemiAutomaticTestingFragment;
import mobello.amtrust.com.fragment.VolumeFragment;
import mobello.amtrust.com.utility.DBHelper;
import mobello.amtrust.com.widget.NonSwipableViewPager;

public class QuickScanActivity extends AppCompatActivity {

    private TextView txtTitle;
    private NonSwipableViewPager viewPager;

    private QuickScanPagerAdapter adapter;

    private DBHelper dbHelper;

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,QuickScanActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_scan);
        initViews();
        txtTitle.setText(R.string.quick_check);
        dbHelper.clearQuickScanData();
        adapter = new QuickScanPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 100);
        }
    }

    private void initViews() {
        txtTitle = _findViewById(R.id.title);
        viewPager = _findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(pageChangeListener);
        dbHelper = DBHelper.getInstance();
    }

    private <T extends View>T _findViewById(int resID){
        return (T)findViewById(resID);
    }

    public void moveToNextPage(int currentPosition){
        viewPager.setCurrentItem(currentPosition + 1);
    }

    public int getPagerCurrentPosition(){
        return  viewPager.getCurrentItem();
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                ((SemiAutomaticTestingFragment)adapter.getItem(1)).keyDown(keyCode);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                ((SemiAutomaticTestingFragment)adapter.getItem(1)).keyDown(keyCode);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
