package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.DeviceCategoryAdapter;
import mobello.amtrust.com.adapter.PolicyAdapter;
import mobello.amtrust.com.model.MyPolicy;
import mobello.amtrust.com.utility.Helper;

public class PolicyActivity extends AppCompatActivity {

    private ListView policy_list_view;


    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,PolicyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        ((TextView)findViewById(R.id.title)).setText(R.string.my_policy);
        Type listType = new TypeToken<ArrayList<MyPolicy>>() {}.getType();
        final ArrayList<MyPolicy> myPolicys = new Gson().fromJson(Helper.loadJSONFromAsset("myPolicy.json"),listType);
        policy_list_view = ((ListView)findViewById(R.id.list_view));
        policy_list_view.setAdapter(new PolicyAdapter(PolicyActivity.this,myPolicys));
        policy_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
