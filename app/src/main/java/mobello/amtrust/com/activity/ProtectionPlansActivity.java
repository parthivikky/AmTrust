package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import mobello.amtrust.com.R;

public class ProtectionPlansActivity extends AppCompatActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,ProtectionPlansActivity.class));
    }

    private static final int[] icons = {R.drawable.ic_all_plan,R.drawable.ic_policy,R.drawable.ic_devices,R.drawable.ic_claims};
    private static final String[] headers= {"All Plans","My Policies","My Devices","My Claims"};
    private static final String[] desc= {"Showcases all protection plan that are available to you from Amtrust",
            "This section showcases all the protection plan that you have purchased","Add all the devices that you own",
            "Showcases all the claims that you have done"};

    private GridView gridView;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protection_plans);
        initViews();
        txtTitle.setText("Protection Plans");
        gridView.setAdapter(new ProtectionAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        AllPlansActivity.start(ProtectionPlansActivity.this);
                        break;
                    case 1:
                        PolicyActivity.start(ProtectionPlansActivity.this);
                        break;
                    case 2:
                        DeviceActivity.start(ProtectionPlansActivity.this);
                        break;
                }
            }
        });

    }

    private void initViews() {
        gridView = (GridView)findViewById(R.id.grid_view);
        txtTitle = (TextView)findViewById(R.id.title);
    }

    public class ProtectionAdapter extends BaseAdapter{

        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return headers.length;
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
            View view;
            if(convertView == null){
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.protection_plan_row,null);
            }else{
                view = convertView;
            }
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.image_view);
            viewHolder.txtHeader = (TextView)view.findViewById(R.id.header);
            viewHolder.txtDesc = (TextView)view.findViewById(R.id.desc);
            viewHolder.imageView.setImageResource(icons[i]);
            viewHolder.txtHeader.setText(headers[i]);
            viewHolder.txtDesc.setText(desc[i]);
            return view;
        }

        public class ViewHolder{
            ImageView imageView;
            TextView txtHeader,txtDesc;
        }
    }
}
