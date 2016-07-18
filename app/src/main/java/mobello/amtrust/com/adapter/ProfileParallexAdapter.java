package mobello.amtrust.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mobello.amtrust.com.R;

/**
 * Created by Parthi on 25-May-16.
 */
public class ProfileParallexAdapter extends BaseAdapter {

    private String[] headers = {"Name","Email","Address","City","Country","Postal Code"};
    private String[] values = {"Steve Jobs","steve@apple.com","402 Parkan Street","Kuala Lumpur","Malaysia","382938"};

    private Context context;
    private ViewHolder holder;

    public ProfileParallexAdapter(Context context){
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
        View view = null;
        holder = new ViewHolder();
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.profile_parallex_row,null);
        }else{
            view = convertView;
        }
        holder.header = (TextView)view.findViewById(R.id.header);
        holder.content = (TextView)view.findViewById(R.id.content);
        holder.header.setText(headers[position]);
        holder.content.setText(values[position]);
        return view;
    }

    public class ViewHolder{
        TextView header, content;
    }
}
