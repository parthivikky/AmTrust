package mobello.amtrust.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import mobello.amtrust.com.fragment.AutomaticTestingFragment;
import mobello.amtrust.com.fragment.DamageFragment;
import mobello.amtrust.com.fragment.SemiAutomaticTestingFragment;

/**
 * Created by Parthi on 30-May-16.
 */
public class QuickScanPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public QuickScanPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new AutomaticTestingFragment());
        fragments.add(new SemiAutomaticTestingFragment());
        fragments.add(new DamageFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
