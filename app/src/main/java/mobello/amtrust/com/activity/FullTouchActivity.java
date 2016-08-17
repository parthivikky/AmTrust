package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
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

    private static final int ROWS = 8;
    private static final int COLUMNS = 6;
    private static final int TOTAL_CELLS = ROWS * COLUMNS;
    private int count = 0;

    public static void start(Activity activity,int requestCode) {
        activity.startActivityForResult(new Intent(activity, FullTouchActivity.class),requestCode);
    }

    public static int height, width;

    private GridView gridView;
    private LinearLayout layout;
    private LinkedHashMap<Integer, Boolean> linkedHashMap;
    private TouchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_touch);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
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
                gridView.setNumColumns(COLUMNS);
                adapter = new TouchAdapter();
                gridView.setAdapter(adapter);
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
            // Find the index of the active pointer and fetch its position
            int x = (int)motionEvent.getX();
            int y = (int)motionEvent.getY();
            int position = gridView.pointToPosition(x,y);
            if(position !=  -1){
                if(!linkedHashMap.get(position)) {
                    Log.i("position",position + "");
                    adapter.updateView(position);
                    count++;
                }
                if(count == TOTAL_CELLS){
                    setResult(RESULT_OK);
                    finish();
                }
            }
            return true;
        }
        return false;
    }

    private class TouchAdapter extends BaseAdapter {
        private int itemWidth, itemHeight;
        private View view;
        private ViewHolder viewHolder;


        public TouchAdapter() {
            Log.i("" + COLUMNS , "" + ROWS);
            Log.i("" + width , "" + height);
            itemWidth = Math.round(width / COLUMNS);
            itemHeight = Math.round(height / ROWS);
            Log.i("" + itemWidth , "" + itemHeight);
        }

        @Override
        public int getCount() {
            Log.i("size","" + linkedHashMap.size());
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
            Log.i("get view" ," " + i);
            if (convertView == null) {
                view = LayoutInflater.from(FullTouchActivity.this).inflate(R.layout.touch_view_row, null);
            } else {
                view = convertView;
            }
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.textView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));
            if(linkedHashMap.get(i)){
                viewHolder.textView.setBackgroundResource(R.color.green);
            }else{
                viewHolder.textView.setBackgroundResource(android.R.color.white);
            }

            /*viewHolder.textView.setOnClickListener(new View.OnClickListener() {
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
            });*/
            return view;
        }

        public void updateView(int position){
            /*View itemview = gridView.getAdapter().getView(position, null, gridView);
            itemview.setBackgroundResource(R.color.green);*/
            linkedHashMap.put(position, true);
            notifyDataSetChanged();
        }

        public class ViewHolder {
            TextView textView;
        }
    }
}
