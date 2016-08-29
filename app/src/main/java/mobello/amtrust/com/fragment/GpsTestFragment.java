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
public class GpsTestFragment extends Fragment {


    public GpsTestFragment() {
        // Required empty public constructor
    }

    private ListView gpsTestListView;
    private ArrayList<Test> gpsTests;
    private TestListViewAdapter testListViewAdapter;
    private ProgressBar progressBarGps;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gpsTestListView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initGpsTests();
        initProgressBar();
        initListView();
    }

    private void initGpsTests() {
        gpsTests = new ArrayList<Test>();
        gpsTests.add(new Test("GPS Connect", FontAwesomeIcons.fa_location_arrow, AppPreference.getBoolean(getActivity(), AppPreference.GPS_CONNECT_TEST)));
    }

    private void initProgressBar() {
        progressBarGps = (ProgressBar) getActivity().findViewById(R.id.progressBarGPS);
        progressBarGps.setMax(gpsTests.size());
    }

    private void initListView() {
        testListViewAdapter = new TestListViewAdapter(getActivity(), gpsTests, progressBarGps);
        gpsTestListView.setAdapter(testListViewAdapter);
    }
}
