package mobello.amtrust.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.Choose;

/**
 * Created by Parthi on 11-Aug-16.
 */
public class ChooserAdapter extends BaseAdapter {

    private Context context;
    private LinkedHashMap<String, Boolean> list;
    private ArrayList<String> keys;
    private ArrayList<Boolean> values;

    public ChooserAdapter(Context context, LinkedHashMap<String, Boolean> list) {
        this.context = context;
        this.list = list;
        keys = new ArrayList<>(list.keySet());
        values = new ArrayList<>(list.values());
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.chooser_row,null);
        }else{
            view = convertView;
        }
        viewHolder = new ViewHolder();
        viewHolder.imageView = (ImageView)view.findViewById(R.id.image_view);
        viewHolder.textView = (TextView)view.findViewById(R.id.text_view);
        viewHolder.textView.setText(keys.get(i));
        if(values.get(i))
            viewHolder.imageView.setVisibility(View.VISIBLE);
        else
            viewHolder.imageView.setVisibility(View.GONE);
        return view;
    }

    public class ViewHolder{
        public TextView textView;
        public ImageView imageView;
    }

    public void setSelected(int position){
        Collections.fill(values,false);
        values.set(position,true);
        notifyDataSetChanged();
    }
}
