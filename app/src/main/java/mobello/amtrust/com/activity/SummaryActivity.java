package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;
import java.util.TreeMap;

import mobello.amtrust.com.R;

public class SummaryActivity extends AppCompatActivity {

    public static void start(Activity activity, JSONArray jsonArray){
        activity.startActivity(new Intent(activity,SummaryActivity.class).putExtra("array",jsonArray.toString()));
    }

    private static final String[] features = {"Back Camera", "Battery", "Bluetooth", "Data Connection" , "Front Camera" ,"GPS",
            "Gyroscope", "Screen Touch" , "Screen Shot" , "Volume Button" , "Wifi"};

    private TextView txtTitle;
    private ListView listView;
    private TreeMap<String,String> treeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        initViews();
        txtTitle.setText(R.string.summary);

        try {
            JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("array"));
            treeMap = new TreeMap<>();
            for (int i = 0; i < jsonArray.length() ; i++){
                try {
                    String status;
                    JSONObject object = jsonArray.getJSONObject(i);
                    if(object.getInt("is_working") == 1) {
                        status = "Working";
                    }else{
                        status = "Not Working";
                    }
                    treeMap.put(object.getString("name"),status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setAdapter(new SummartAdapter());
    }

    private void initViews() {
        txtTitle = _findViewById(R.id.title);
        listView = _findViewById(R.id.list_view);
    }

    private <T extends View>T _findViewById(int resID){
        return (T)findViewById(resID);
    }

    private class SummartAdapter extends BaseAdapter{

        private ViewHolder holder;

        @Override
        public int getCount() {
            return treeMap.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            holder = new ViewHolder();
            View view;
            if(convertView == null){
                view = LayoutInflater.from(SummaryActivity.this).inflate(R.layout.summary_row,null);
            }else{
                view = convertView;
            }
            holder.txtName = (TextView)view.findViewById(R.id.name);
            holder.txtStatus = (TextView)view.findViewById(R.id.status);
            String name = (String)treeMap.keySet().toArray()[i];
            String status = treeMap.get(name);
            holder.txtName.setText( (i + 1 ) + "." + name);
            holder.txtStatus.setText(status);
            if(status.equalsIgnoreCase("Working"))
                holder.txtStatus.setTextColor(Color.parseColor("#94E0D1"));
            else
                holder.txtStatus.setTextColor(Color.parseColor("#B1744C"));
            return view;
        }
    }

    public class ViewHolder{
        TextView txtName,txtStatus;
    }

}
