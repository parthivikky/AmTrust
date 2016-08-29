package mobello.amtrust.com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.Test;

/**
 * Created by Vicky on 11-Aug-16.
 */
public class TestListViewAdapter extends BaseAdapter {

    public int count = 0;
    private Context context;
    private ArrayList<Test> list;
    private ProgressBar progressBar;

    public TestListViewAdapter(Context context, ArrayList<Test> list, ProgressBar progressBar) {
        this.context = context;
        this.list = list;
        this.progressBar = progressBar;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.test_row, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.testTextView.setText(list.get(position).getName());
        mViewHolder.icons.setText("{" + list.get(position).getIcon().key() + "}");
        if (list.get(position).isCompleted()) {
            mViewHolder.angleRight.setVisibility(View.GONE);
            mViewHolder.check.setVisibility(View.VISIBLE);
            progressBar.setProgress(++count);
            mViewHolder.icons.setTextColor(Color.parseColor("#16A085"));
        } else {
            mViewHolder.angleRight.setVisibility(View.VISIBLE);
            mViewHolder.check.setVisibility(View.GONE);
            mViewHolder.icons.setTextColor(Color.parseColor("#AAAAAA"));
        }
        return convertView;
    }

    private class MyViewHolder {
        TextView testTextView;
        IconTextView angleRight;
        IconTextView icons;
        IconTextView check;


        public MyViewHolder(View item) {
            testTextView = (TextView) item.findViewById(R.id.test_name);
            angleRight = (IconTextView) item.findViewById(R.id.angle_right);
            check = (IconTextView) item.findViewById(R.id.check);
            icons = (IconTextView) item.findViewById(R.id.image);
        }
    }

    public void setSelected(int position) {
        list.get(position).setCompleted(true);
        count = 0;
        notifyDataSetChanged();
    }
}
