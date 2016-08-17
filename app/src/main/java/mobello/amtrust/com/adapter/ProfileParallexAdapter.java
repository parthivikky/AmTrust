package mobello.amtrust.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import mobello.amtrust.com.R;

/**
 * Created by Parthi on 25-May-16.
 */
public class ProfileParallexAdapter extends BaseAdapter {

    private LinkedHashMap<String,String> linkedHashMap;
    private Context context;
    private ViewHolder holder;
    private ArrayList<String> keys;
    private ArrayList<String> values;

    public ProfileParallexAdapter(Context context, LinkedHashMap<String,String> linkedHashMap){
        this.context = context;
        this.linkedHashMap = linkedHashMap;
        keys = new ArrayList<>(linkedHashMap.keySet());
        values = new ArrayList<>(linkedHashMap.values());
    }

    @Override
    public int getCount() {
        return linkedHashMap.size();
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
        View view = null;
        holder = new ViewHolder();
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.profile_parallex_row,null);
        }else{
            view = convertView;
        }

        holder.header = (TextView)view.findViewById(R.id.header);
        holder.content = (TextView)view.findViewById(R.id.content);
        holder.header.setText(keys.get(position));
        holder.content.setText(values.get(position));
        return view;
    }

    public class ViewHolder{
        TextView header, content;
    }

    public void insert(LinkedHashMap<String,String> linkedHashMap){
        keys = new ArrayList<>(linkedHashMap.keySet());
        values = new ArrayList<>(linkedHashMap.values());
        notifyDataSetChanged();
    }
}
