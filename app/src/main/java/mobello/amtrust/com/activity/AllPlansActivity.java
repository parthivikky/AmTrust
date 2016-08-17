package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mobello.amtrust.com.R;

public class AllPlansActivity extends AppCompatActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,AllPlansActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ((TextView)findViewById(R.id.title)).setText("Protection Plans");
    }

    public void click(View view){
        ProductPurchaseActivity.start(AllPlansActivity.this);
    }
}
