package mobello.amtrust.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mobello.amtrust.com.R;

/**
 * Created by Parthi on 19-May-16.
 */
public class HomeAdapter extends BaseAdapter {

    private String[] headers = {"Protection Plans","Device Health Check","Trade In","Tech Support","Membership Plus"};

    private int[] icons = {R.drawable.ic_home_protection_plan,R.drawable.ic_home_health_device,
                    R.drawable.ic_home_trade_in,R.drawable.ic_home_tech_support,R.drawable.ic_home_membership};

    private Context context;
    private ViewHolder viewHolder;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headers.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        viewHolder = new ViewHolder();
        if(convertView == null){
             view = LayoutInflater.from(context).inflate(R.layout.home_row,null);
        }else {
            view = convertView;
        }
        viewHolder.header = (TextView)view.findViewById(R.id.header);
        viewHolder.icon = (ImageView)view.findViewById(R.id.icon);
        viewHolder.icon.setImageResource(icons[position]);
        viewHolder.header.setText(headers[position]);
        return view;
    }

    public class ViewHolder{
        TextView header;
        ImageView icon;
    }
}
