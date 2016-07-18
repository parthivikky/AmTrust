package mobello.amtrust.com.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.TechSupport;

/**
 * Created by Parthi on 25-May-16.
 */
public class TechSupportStepPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<TechSupport.Detail> details;
    private View convertView;

    public TechSupportStepPagerAdapter(Context context, ArrayList<TechSupport.Detail> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder = new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.tech_support_step_row,null);
        holder.imageView = (ImageView)convertView.findViewById(R.id.image_view);
        holder.textView = (TextView) convertView.findViewById(R.id.text);
        holder.textView.setText(details.get(position).getDescription());
        Log.i("desc",details.get(position).getDescription());
        Glide.with(context).load(details.get(position).getPhoto()).into(holder.imageView);
        container.addView(convertView);
        return convertView;
    }

    public class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
