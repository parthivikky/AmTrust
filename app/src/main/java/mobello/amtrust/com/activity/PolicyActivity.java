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

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.DeviceCategoryAdapter;
import mobello.amtrust.com.adapter.PolicyAdapter;

public class PolicyActivity extends AppCompatActivity {

    private ListView policy_list_view;


    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,PolicyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        ((TextView)findViewById(R.id.title)).setText(R.string.policy_management);
        policy_list_view = ((ListView)findViewById(R.id.list_view));
        policy_list_view.setAdapter(new PolicyAdapter(PolicyActivity.this));
        policy_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
