package mobello.amtrust.com.fragment;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

import mobello.amtrust.com.R;
import mobello.amtrust.com.utility.DBHelper;
import mobello.amtrust.com.utility.FileTracker;


public class ScreenShotFragment extends Fragment {

    private View rootView;
    private TextView skip;
    private ContentObserver contentObserver;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_screen_shot, container, false);
        dbHelper = DBHelper.getInstance();
        HandlerThread handlerThread = new HandlerThread("content_observer");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        contentObserver = new ContentObserver(handler) {
            @Override
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((SemiAutomaticTestingFragment) getParentFragment()).showTick();
                            int currentItem = ((SemiAutomaticTestingFragment) getParentFragment()).getPagerCurrentPosition();
                            ((SemiAutomaticTestingFragment) getParentFragment()).moveToNextPage(currentItem);
                            dbHelper.quickScanFeatures("Screen Shot", 1);
                        }
                    });
                    getActivity().getContentResolver().unregisterContentObserver(contentObserver);
            }
        };

        rootView.findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.quickScanFeatures("Screen Shot",0);
                int currentItem = ((SemiAutomaticTestingFragment)getParentFragment()).getPagerCurrentPosition();
                ((SemiAutomaticTestingFragment)getParentFragment()).moveToNextPage(currentItem);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true, contentObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
