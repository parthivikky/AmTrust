package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.ChooserAdapter;
import mobello.amtrust.com.model.Choose;

public class ChooserActivity extends AppCompatActivity {

    public static void startForResult(Activity activity, String list,int requestCode){
        activity.startActivityForResult(new Intent(activity,ChooserActivity.class)
                .putExtra("list",list),requestCode);
    }

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        listView = (ListView)findViewById(R.id.list_view);
        ((TextView)findViewById(R.id.title)).setText("Select");
        final String chooses = getIntent().getStringExtra("list");
        Type entityType = new TypeToken<LinkedHashMap<String, Boolean>>(){}.getType();
        final LinkedHashMap<String, Boolean> mLinkedHashMap = new Gson().fromJson(chooses, entityType);
        final ChooserAdapter adapter = new ChooserAdapter(this,mLinkedHashMap);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelected(i);
                Intent intent = new Intent();
                intent.putExtra("name",new ArrayList<String>(mLinkedHashMap.keySet()).get(i));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }


}
