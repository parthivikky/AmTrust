package mobello.amtrust.com.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.BatteryChargingTestActivity;
import mobello.amtrust.com.activity.BatteryTemperatureTestActivity;
import mobello.amtrust.com.adapter.TestListViewAdapter;
import mobello.amtrust.com.model.Test;
import mobello.amtrust.com.utility.AppPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryTestFragment extends Fragment {


    public BatteryTestFragment() {
        // Required empty public constructor
    }


    private ListView batteryTestListView;
    private ArrayList<Test> batteryTests;
    private TestListViewAdapter testListViewAdapter;
    public static final int BATTERY_CHARGING_TEST_CODE = 1;
    public static final int BATTERY_TEMPERATURE_TEST_CODE = 2;
    private ProgressBar progressBarBattery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        batteryTestListView = (ListView) view.findViewById(R.id.list_view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBatteryTests();
        initProgressBar();
        initListView();
    }

    private void initBatteryTests() {
        batteryTests = new ArrayList<Test>();
        batteryTests.add(new Test("Battery Charging", FontAwesomeIcons.fa_battery_full , AppPreference.getBoolean(getActivity(), AppPreference.BATTERY_CHARGING_TEST)));
        batteryTests.add(new Test("Battery Temperature",FontAwesomeIcons.fa_battery_three_quarters , AppPreference.getBoolean(getActivity(), AppPreference.BATTERY_TEMPERATURE_TEST)));
    }

    private void initProgressBar() {
        progressBarBattery = (ProgressBar) getActivity().findViewById(R.id.progressBarBattery);
        progressBarBattery.setMax(batteryTests.size());
    }

    private void initListView() {
        testListViewAdapter = new TestListViewAdapter(getActivity(), batteryTests, progressBarBattery);
        batteryTestListView.setAdapter(testListViewAdapter);
        batteryTestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivityForResult(new Intent(getContext(), BatteryChargingTestActivity.class), BATTERY_CHARGING_TEST_CODE);
                        break;
                    case 1:
                        startActivityForResult(new Intent(getContext(), BatteryTemperatureTestActivity.class), BATTERY_TEMPERATURE_TEST_CODE);
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case BATTERY_CHARGING_TEST_CODE:
                    //Toast.makeText(getActivity(), "Battery", Toast.LENGTH_SHORT).show();
                    updateUI(0, AppPreference.BATTERY_CHARGING_TEST);
                    break;
                case BATTERY_TEMPERATURE_TEST_CODE:
                    //Toast.makeText(getActivity(), "Temperature", Toast.LENGTH_SHORT).show();
                    updateUI(1, AppPreference.BATTERY_TEMPERATURE_TEST);
                    break;
            }
        }
    }

    private void updateUI(int position, String batteryTest) {
        if (!AppPreference.getBoolean(getActivity(), batteryTest)) {
            testListViewAdapter.setSelected(position);
            AppPreference.setBoolean(getActivity(), batteryTest, true);
        }
    }
}
