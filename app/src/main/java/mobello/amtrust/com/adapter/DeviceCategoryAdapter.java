package mobello.amtrust.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.AddDeviceActivity;

/**
 * Created by Parthi on 23-May-16.
 */
public class DeviceCategoryAdapter extends BaseAdapter {

    private int[] icons = {R.drawable.device_category_mobile, R.drawable.device_category_ipad, R.drawable.device_category_laptop, R.drawable.device_category_tablet};
    private String[] names = {"MOBILE", "IPAD", "LAPTOP", "TABLET"};
    private String[] desc = {"All mobiles come under this category", "Apple iPad comes under this category", "All laptops come under this category",
            "All tablets come under this category"};

    private Activity context;
    private ViewHolder viewHolder;
    private boolean isRequest;

    public DeviceCategoryAdapter(Activity context, boolean isRequest) {
        this.context = context;
        this.isRequest = isRequest;
    }

    @Override
    public int getCount() {
        return names.length;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        viewHolder = new ViewHolder();
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.device_category_row, null);
        } else {
            view = convertView;
        }
        viewHolder.txtName = (TextView) view.findViewById(R.id.name);
        viewHolder.txtDesc = (TextView) view.findViewById(R.id.desc);
        viewHolder.txtChoose = (TextView) view.findViewById(R.id.choose);
        viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
        viewHolder.imageView.setImageResource(icons[position]);
        viewHolder.txtName.setText(names[position]);
        viewHolder.txtDesc.setText(desc[position]);

        viewHolder.txtChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRequest)
                    AddDeviceActivity.start(context);
                else {
                    context.setResult(Activity.RESULT_OK,new Intent().putExtra("category",names[position]));
                    context.finish();
                }
            }
        });
        return view;
    }

    public class ViewHolder {
        TextView txtName, txtDesc, txtChoose;
        ImageView imageView;
    }
}
