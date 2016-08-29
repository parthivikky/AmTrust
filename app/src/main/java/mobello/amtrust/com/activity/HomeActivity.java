package mobello.amtrust.com.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.HomePagerAdapter;
import mobello.amtrust.com.model.GetChallenge;
import mobello.amtrust.com.model.SessionLogin;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.FileTracker;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private int[] tabIcons = {R.drawable.home_tab,
            R.drawable.timeline_tab,
            R.drawable.profile_tab,
            R.drawable.more_tab};

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private HomePagerAdapter viewPagerAdapter;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        initViews();

        viewPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcons();
    }

    private void initViews() {
        tabLayout = _findViewById(R.id.tab_layout);
        viewPager = _findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }


    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            /*switch (position){
                case 0:
//                    settitle(R.string.home,R.drawable.ic_notification);
                    break;
                case 1:
                    settitle(R.string.timeline,0);
                    break;
                case 2:
                    settitle(R.string.profile,R.drawable.ic_edit);
                    break;
                case 3:
                    settitle(R.string.more,0);
                    break;
            }*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public <T extends View> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

    public void setTabIcons() {
        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
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
