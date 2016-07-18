package mobello.amtrust.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import mobello.amtrust.com.fragment.HomeFragment;
import mobello.amtrust.com.fragment.MoreFragment;
import mobello.amtrust.com.fragment.ProfileFragment;
import mobello.amtrust.com.fragment.TimelineFragment;

/**
 * Created by Parthi on 19-May-16.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CharSequence[] headers = {"Home", "Timeline", "Profile", "More"};


    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new HomeFragment());
        fragments.add(new TimelineFragment());
        fragments.add(new ProfileFragment());
        fragments.add(new MoreFragment());
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

    @Override
    public CharSequence getPageTitle(int position) {
        return headers[position];
    }
}
