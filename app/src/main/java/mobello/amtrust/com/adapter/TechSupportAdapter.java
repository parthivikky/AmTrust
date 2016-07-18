package mobello.amtrust.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.TechSupport;

/**
 * Created by Parthi on 25-May-16.
 */
public class TechSupportAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TechSupport.Result> results;
    private ViewHolder holder;

    public TechSupportAdapter(Context context, ArrayList<TechSupport.Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.tech_support_row,null);
        }else {
            view = convertView;
        }
        holder.name = (TextView)view.findViewById(R.id.tech_support_name);
        holder.name.setText(results.get(position).getQuestion());
        return view;
    }

    public class ViewHolder{
        TextView name;
    }
}
