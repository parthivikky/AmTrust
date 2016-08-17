package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.TechSupportAdapter;
import mobello.amtrust.com.model.TechSupport;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.ResourceUtils;

public class TechSupportActivity extends AppCompatActivity {

    private ListView listView;

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,TechSupportActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_support);
        initviews();
        ((TextView)findViewById(R.id.title)).setText(R.string.tech_support);
        ((ImageView)findViewById(R.id.menu_right)).setImageResource(R.drawable.filter);

        final TechSupport techSupport = new Gson().fromJson(Helper.loadJSONFromAsset("tech_listing.json"),TechSupport.class);
        TechSupportAdapter adapter = new TechSupportAdapter(this,techSupport.getResult());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<TechSupport.Detail> details = techSupport.getResult().get(position).getDetails();
                try {
                    if (details.size() > 0) {
                        TechSupportStepActivity.start(TechSupportActivity.this, details);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void initviews() {
        listView = (ListView)findViewById(R.id.list_view);
    }


}
