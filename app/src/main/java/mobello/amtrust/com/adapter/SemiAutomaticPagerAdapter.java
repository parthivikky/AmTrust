package mobello.amtrust.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import mobello.amtrust.com.fragment.BackCameraFragment;
import mobello.amtrust.com.fragment.FrontCameraFragment;
import mobello.amtrust.com.fragment.ScreenShotFragment;
import mobello.amtrust.com.fragment.TouchFragment;
import mobello.amtrust.com.fragment.VolumeFragment;

/**
 * Created by Parthi on 06-Jul-16.
 */
public class SemiAutomaticPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public SemiAutomaticPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new VolumeFragment());
        fragments.add(new ScreenShotFragment());
        fragments.add(new FrontCameraFragment());
        fragments.add(new BackCameraFragment());
        fragments.add(new TouchFragment());
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
