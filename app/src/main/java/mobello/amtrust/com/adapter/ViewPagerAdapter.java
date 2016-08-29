package mobello.amtrust.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import mobello.amtrust.com.fragment.AudioTestFragment;
import mobello.amtrust.com.fragment.BatteryTestFragment;
import mobello.amtrust.com.fragment.CameraTestFragment;
import mobello.amtrust.com.fragment.ConnectTestFragment;
import mobello.amtrust.com.fragment.DeviceTestFragment;
import mobello.amtrust.com.fragment.DisplayTestFragment;
import mobello.amtrust.com.fragment.GpsTestFragment;
import mobello.amtrust.com.fragment.SensorTestFragment;

/**
 * Created by Vicky on 13-Aug-16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new AudioTestFragment());
        fragments.add(new BatteryTestFragment());
        fragments.add(new CameraTestFragment());
        fragments.add(new ConnectTestFragment());
        fragments.add(new DeviceTestFragment());
        fragments.add(new DisplayTestFragment());
        fragments.add(new GpsTestFragment());
        fragments.add(new SensorTestFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
