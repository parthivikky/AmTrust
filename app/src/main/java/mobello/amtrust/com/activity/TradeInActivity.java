package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.TradeAdapter;
import mobello.amtrust.com.model.MyPolicy;
import mobello.amtrust.com.model.TradeIn;
import mobello.amtrust.com.utility.Helper;

public class TradeInActivity extends AppCompatActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,TradeInActivity.class));
    }

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_in);
        ((TextView)findViewById(R.id.title)).setText("Trade In");
        Type listType = new TypeToken<ArrayList<TradeIn>>() {}.getType();
        ArrayList<TradeIn> tradeIns = new Gson().fromJson(Helper.loadJSONFromAsset("retailer_dummy.json"),listType);
        listView = (ListView)findViewById(R.id.list_view);
        listView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.trade_in_header,null));
        listView.setAdapter(new TradeAdapter(this,tradeIns));
    }
}
