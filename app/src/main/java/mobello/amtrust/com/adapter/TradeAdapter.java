package mobello.amtrust.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.TradeIn;

/**
 * Created by Parthi on 12-Aug-16.
 */
public class TradeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TradeIn> tradeIns;
    private ViewHolder viewHolder;

    public TradeAdapter(Context context, ArrayList<TradeIn> tradeIns) {
        this.context = context;
        this.tradeIns = tradeIns;
    }

    @Override
    public int getCount() {
        return tradeIns.size();
    }

    @Override
    public TradeIn getItem(int i) {
        return tradeIns.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = null;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.tradi_in_row, null);
        } else {
            view = convertView;
        }
        viewHolder = new ViewHolder();
        viewHolder.imgIcons = (ImageView) view.findViewById(R.id.icon);
        viewHolder.txtName = (TextView) view.findViewById(R.id.name);
        viewHolder.txtLocation = (TextView) view.findViewById(R.id.location);
        viewHolder.txtPrice = (TextView) view.findViewById(R.id.price);
        TradeIn tradeIn = getItem(i);
        Glide.with(context).load(tradeIn.getImage()).into(viewHolder.imgIcons);
        viewHolder.txtName.setText(tradeIn.getName());
        viewHolder.txtLocation.setText(tradeIn.getLocation());
        viewHolder.txtPrice.setText(tradeIn.getPrice());
        return view;
    }

    public class ViewHolder {
        ImageView imgIcons;
        TextView txtName, txtPrice, txtLocation;
    }
}
