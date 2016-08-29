package mobello.amtrust.com.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.TestListViewAdapter;
import mobello.amtrust.com.model.Test;
import mobello.amtrust.com.utility.AppPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayTestFragment extends Fragment {


    public DisplayTestFragment() {
        // Required empty public constructor
    }

    private ListView displayTestListView;
    private ArrayList<Test> displayTests;
    private TestListViewAdapter testListViewAdapter;
    private ProgressBar progressBarDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayTestListView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDisplayTests();
        initProgressBar();
        initListView();
    }

    private void initDisplayTests() {
        displayTests = new ArrayList<Test>();
        displayTests.add(new Test("LCD Backlight", FontAwesomeIcons.fa_sun_o,  AppPreference.getBoolean(getActivity(), AppPreference.LCD_BACKLIGHT_TEST)));
        displayTests.add(new Test("LCD Color",FontAwesomeIcons.fa_paint_brush,  AppPreference.getBoolean(getActivity(), AppPreference.LCD_COLOR_TEST)));
        displayTests.add(new Test("Screen", FontAwesomeIcons.fa_desktop, AppPreference.getBoolean(getActivity(), AppPreference.SCREEN_TEST)));
        displayTests.add(new Test("Touch",FontAwesomeIcons.fa_hand_pointer_o,  AppPreference.getBoolean(getActivity(), AppPreference.TOUCH_TEST)));
    }

    private void initProgressBar() {
        progressBarDisplay = (ProgressBar) getActivity().findViewById(R.id.progressBarDisplay);
        progressBarDisplay.setMax(displayTests.size());
    }

    private void initListView() {
        testListViewAdapter = new TestListViewAdapter(getActivity(), displayTests, progressBarDisplay);
        displayTestListView.setAdapter(testListViewAdapter);
    }
}
