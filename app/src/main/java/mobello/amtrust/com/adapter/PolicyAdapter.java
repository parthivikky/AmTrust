package mobello.amtrust.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mobello.amtrust.com.R;

/**
 * Created by Parthi on 24-May-16.
 */
public class PolicyAdapter extends BaseAdapter {

    private String[] name = {"Mobile Production Policy","Mobile Production Policy","Mobile Production Policy","Mobile Production Policy"};
    private String[] mobile = {"iPhone 6Plus","iPhone 6Plus","iPhone 6Plus","iPhone 6Plus"};
    private String[] bought = {"30 May 2016","30 May 2016","30 May 2016","30 May 2016"};
    private String[] expire = {"30 May 2017","30 May 2017","30 May 2017","30 May 2017"};
    private String[] annually = {"$200","$200","$200","$200"};
    private String[] monthly = {"$20","$20","$20","$20"};
    private Context context;
    private ViewHolder viewHolder;
    private View view;

    public PolicyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return name.length;
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
        viewHolder = new ViewHolder();
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.policy_row,null);
        }else{
            view = convertView;
        }
        viewHolder.name = _findViewById(R.id.policy_name);
        viewHolder.product = _findViewById(R.id.product);
        viewHolder.bought_date = _findViewById(R.id.bought_date);
        viewHolder.expire_date = _findViewById(R.id.expire_date);
        viewHolder.annually_price = _findViewById(R.id.annually);
        viewHolder.monthly_price = _findViewById(R.id.monthly);

        viewHolder.name.setText(name[position]);
        viewHolder.product.setText(mobile[position]);
        viewHolder.bought_date.setText("Bought : " + bought[position]);
        viewHolder.expire_date.setText("Expire : " + expire[position]);
        viewHolder.annually_price.setText(annually[position] + " Annually");
        viewHolder.monthly_price.setText(monthly[position] + " Monthly");
        return view;
    }

    private class ViewHolder{
        TextView name,product,bought_date,expire_date,annually_price,monthly_price;
    }

    public <T extends View> T _findViewById(int viewId){
        return (T) view.findViewById(viewId);
    }

}
