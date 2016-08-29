package mobello.amtrust.com.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.UserLog;

/**
 * Created by Parthi on 25-Aug-16.
 */
public class UserLogAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserLog.Data> userLogs;

    public UserLogAdapter(Context context, ArrayList<UserLog.Data> userLogs) {
        this.context = context;
        this.userLogs = userLogs;
    }

    @Override
    public int getCount() {
        return userLogs.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.user_log_row,null);
        }else{
            view = convertView;
        }
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.title = (TextView)view.findViewById(R.id.title);
        viewHolder.date = (TextView)view.findViewById(R.id.date);
        viewHolder.description = (TextView)view.findViewById(R.id.desc);

        viewHolder.title.setText(userLogs.get(i).getTitle());
        viewHolder.date.setText(userLogs.get(i).getDate());
        viewHolder.description.setText(userLogs.get(i).getDescription());
        return view;
    }

    private class ViewHolder{
        TextView title,date,description;
    }
}
