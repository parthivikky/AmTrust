package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mobello.amtrust.com.R;

public class ProductPurchaseActivity extends AppCompatActivity {


    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,ProductPurchaseActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_purchase);
    }
}
