package mobello.amtrust.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.SemiAutomaticPagerAdapter;
import mobello.amtrust.com.widget.NonSwipableViewPager;

public class SemiAutomaticTestingFragment extends Fragment {

    private int[] tabIcons = {R.drawable.ic_volume,
            R.drawable.ic_screenshot,
            R.drawable.ic_front_camera,
            R.drawable.ic_back_camera,
            R.drawable.ic_touch
    };

    private View rootView;
    private TabLayout tabLayout;
    private NonSwipableViewPager viewPager;
    private LinearLayout tickContainer;

    private SemiAutomaticPagerAdapter adapter;

    public SemiAutomaticTestingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_semi_automatic, container, false);
        initViews();

        adapter = new SemiAutomaticPagerAdapter(getChildFragmentManager());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcons();
        return rootView;
    }

    private void initViews() {
        tabLayout = _findViewById(R.id.tab_layout);
        viewPager = _findViewById(R.id.view_pager);
        tickContainer = _findViewById(R.id.tickContainer);
    }

    private <T extends View> T _findViewById(int viewId) {
        return (T) rootView.findViewById(viewId);
    }

    public void setTabIcons() {
        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    public void moveToNextPage(int currentPosition) {
        viewPager.setCurrentItem(currentPosition + 1);
    }

    public int getPagerCurrentPosition() {
        return viewPager.getCurrentItem();
    }

    public void keyDown(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                ((VolumeFragment) adapter.getItem(0)).keyDown(keyCode);
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                ((VolumeFragment) adapter.getItem(0)).keyDown(keyCode);
                break;
        }
    }

    public void showTick() {
        tickContainer.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tickContainer.setVisibility(View.GONE);
            }
        }, 2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
