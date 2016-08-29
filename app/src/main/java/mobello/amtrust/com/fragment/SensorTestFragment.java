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
public class SensorTestFragment extends Fragment {


    public SensorTestFragment() {
        // Required empty public constructor
    }

    private ListView sensorTestListView;
    private ArrayList<Test> sensorTests;
    private TestListViewAdapter testListViewAdapter;
    private ProgressBar progressBarSensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorTestListView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSensorTests();
        initProgressBar();
        initListView();
    }

    private void initSensorTests() {
        sensorTests = new ArrayList<Test>();
        sensorTests.add(new Test("Accelerometer", FontAwesomeIcons.fa_ticket, AppPreference.getBoolean(getActivity(), AppPreference.ACCELEROMETER_TEST)));
        sensorTests.add(new Test("Compass",FontAwesomeIcons.fa_compass, AppPreference.getBoolean(getActivity(), AppPreference.COMPASS_TEST)));
        sensorTests.add(new Test("Light",FontAwesomeIcons.fa_lightbulb_o, AppPreference.getBoolean(getActivity(), AppPreference.LIGHT_TEST)));
        sensorTests.add(new Test("Proximity",FontAwesomeIcons.fa_chain_broken, AppPreference.getBoolean(getActivity(), AppPreference.PROXIMITY_TEST)));
    }

    private void initProgressBar() {
        progressBarSensor = (ProgressBar) getActivity().findViewById(R.id.progressBarSensor);
        progressBarSensor.setMax(sensorTests.size());
    }

    private void initListView() {
        testListViewAdapter = new TestListViewAdapter(getActivity(), sensorTests, progressBarSensor);
        sensorTestListView.setAdapter(testListViewAdapter);
    }
}
