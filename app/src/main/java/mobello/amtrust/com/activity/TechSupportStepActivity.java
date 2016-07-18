package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.pageindicator.indicator.FlycoPageIndicaor;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.TechSupportStepPagerAdapter;
import mobello.amtrust.com.model.TechSupport;

public class TechSupportStepActivity extends AppCompatActivity {

    private ViewPager viewPager;
//    private CirclePageIndicatorWithTitle indicator;
    private FlycoPageIndicaor pageIndicaor;
    private ArrayList<TechSupport.Detail> details;

    public static void start(Activity activity, ArrayList<TechSupport.Detail> details){
        Intent intent = new Intent(activity,TechSupportStepActivity.class);
        intent.putParcelableArrayListExtra("details",details);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_support_step);

        details = getIntent().getParcelableArrayListExtra("details");
        ((TextView)findViewById(R.id.title)).setText(R.string.step);
        initViews();
        TechSupportStepPagerAdapter adapter = new TechSupportStepPagerAdapter(this,details);
        viewPager.setAdapter(adapter);
        pageIndicaor.setViewPager(viewPager);
    }

    private void initViews() {
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        pageIndicaor = (FlycoPageIndicaor) findViewById(R.id.indicator_circle_anim);
    }
}
