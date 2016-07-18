package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import mobello.amtrust.com.R;
import mobello.amtrust.com.utility.Version;

public class FullTouchActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final int TOTAL_CELLS = 35;
    private static final int ROWS = 7;
    private static final int COLUMNS = 5;

    public static void start(Activity activity,int requestCode) {
        activity.startActivityForResult(new Intent(activity, FullTouchActivity.class),requestCode);
    }

    public static int height, width;

    private GridView gridView;
    private LinearLayout layout;
    private LinkedHashMap<Integer, Boolean> linkedHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_touch);

        linkedHashMap = new LinkedHashMap<>();
        for (int i = 0; i < TOTAL_CELLS; i++) {
            linkedHashMap.put(i, false);
        }

        layout = (LinearLayout) findViewById(R.id.layout);
        ViewTreeObserver observer = layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                height = layout.getHeight();
                width = layout.getWidth();
                gridView = (GridView) findViewById(R.id.grid_view);
                gridView.setAdapter(new TouchAdapter());
                gridView.setOnTouchListener(FullTouchActivity.this);
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        /*DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;*/
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return false;
    }

    private class TouchAdapter extends BaseAdapter {
        private int itemWidth, itemHeight;
        private View view;
        private ViewHolder viewHolder;
        private int count = 0;

        public TouchAdapter() {
            itemWidth = Math.round(width / COLUMNS);
            itemHeight = Math.round(height / ROWS);
        }

        @Override
        public int getCount() {
            return linkedHashMap.size();
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
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                view = LayoutInflater.from(FullTouchActivity.this).inflate(R.layout.touch_view_row, null);
            } else {
                view = convertView;
            }
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.textView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackgroundResource(R.color.green);
                    if(!linkedHashMap.get(i)) {
                        linkedHashMap.put(i, true);
                        count++;
                    }
                    if(count == TOTAL_CELLS){
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
            return view;
        }

        public class ViewHolder {
            TextView textView;
        }
    }
}
