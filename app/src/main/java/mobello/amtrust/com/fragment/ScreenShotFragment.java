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

import java.io.File;

import mobello.amtrust.com.R;
import mobello.amtrust.com.utility.FileTracker;


public class ScreenShotFragment extends Fragment {

    private View rootView;
    private ContentObserver contentObserver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_screen_shot, container, false);

        HandlerThread handlerThread = new HandlerThread("content_observer");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        final String TAG = "screent shot";
        contentObserver = new ContentObserver(handler) {
            @Override
            public boolean deliverSelfNotifications() {
                Log.d(TAG, "deliverSelfNotifications");
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Log.d(TAG, "onChange " + uri.toString());
//                if (uri.toString().matches(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString() + "/[0-9]+")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((SemiAutomaticTestingFragment) getParentFragment()).showTick();
                            int currentItem = ((SemiAutomaticTestingFragment) getParentFragment()).getPagerCurrentPosition();
                            ((SemiAutomaticTestingFragment) getParentFragment()).moveToNextPage(currentItem);
                        }
                    });
                    getActivity().getContentResolver().unregisterContentObserver(contentObserver);
            }
        };

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
