package mobello.amtrust.com.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mobello.amtrust.com.R;


public class VolumeFragment extends Fragment {


    private View rootView;
    private RelativeLayout volumeUpContainer, volumeDownContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_volume, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        volumeUpContainer = _findViewById(R.id.volume_up_container);
        volumeDownContainer = _findViewById(R.id.volume_down_container);
    }

    private <T extends View>T _findViewById(int viewId){
        return (T)rootView.findViewById(viewId);
    }

    public void keyDown(int keyCode){
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                moveToNext();
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                volumeUpContainer.setVisibility(View.GONE);
                volumeDownContainer.setVisibility(View.VISIBLE);
                ((SemiAutomaticTestingFragment)getParentFragment()).showTick();
                break;
        }
    }

    private void moveToNext() {
        ((SemiAutomaticTestingFragment)getParentFragment()).showTick();
        int currentItem = ((SemiAutomaticTestingFragment)getParentFragment()).getPagerCurrentPosition();
        ((SemiAutomaticTestingFragment)getParentFragment()).moveToNextPage(currentItem);
    }



}
