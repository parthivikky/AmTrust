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
import mobello.amtrust.com.activity.BackKeyTestActivity;
import mobello.amtrust.com.adapter.TestListViewAdapter;
import mobello.amtrust.com.model.Test;
import mobello.amtrust.com.utility.AppPreference;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceTestFragment extends Fragment {


    public DeviceTestFragment() {
        // Required empty public constructor
    }

    private ListView deviceTestListView;
    private ArrayList<Test> deviceTests;
    private TestListViewAdapter testListViewAdapter;
    private ProgressBar progressBarDevice;
    public static final int BACK_KEY_TEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        deviceTestListView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDeviceTests();
        initProgressBar();
        initListView();
    }

    private void initDeviceTests() {
        deviceTests = new ArrayList<Test>();

        deviceTests.add(new Test("Back Key", FontAwesomeIcons.fa_arrow_left ,  AppPreference.getBoolean(getActivity(), AppPreference.BACK_KEY_TEST)));
        deviceTests.add(new Test("Power Key",FontAwesomeIcons.fa_power_off , AppPreference.getBoolean(getActivity(), AppPreference.POWER_KEY_TEST)));
        deviceTests.add(new Test("IMEI Validation",FontAwesomeIcons.fa_mobile, AppPreference.getBoolean(getActivity(), AppPreference.IMEI_VALIDATION_TEST)));
        deviceTests.add(new Test("IMEI Presence",FontAwesomeIcons.fa_mobile_phone , AppPreference.getBoolean(getActivity(), AppPreference.IMEI_PRESENCE_TEST)));
        deviceTests.add(new Test("IMEI2 Presence",FontAwesomeIcons.fa_mobile_phone , AppPreference.getBoolean(getActivity(), AppPreference.IMEI2_PRESENCE_TEST)));
        deviceTests.add(new Test("MAC Validation",FontAwesomeIcons.fa_mobile , AppPreference.getBoolean(getActivity(), AppPreference.MAC_VALIDATION_TEST)));
        deviceTests.add(new Test("Menu Key",FontAwesomeIcons.fa_list , AppPreference.getBoolean(getActivity(), AppPreference.MENU_KEY_TEST)));
        deviceTests.add(new Test("NFC",FontAwesomeIcons.fa_barcode , AppPreference.getBoolean(getActivity(), AppPreference.NFC_TEST)));
        deviceTests.add(new Test("Volume Keys",FontAwesomeIcons.fa_volume_up , AppPreference.getBoolean(getActivity(), AppPreference.VOLUME_KEYS_TEST)));
        deviceTests.add(new Test("Recent App Keys",FontAwesomeIcons.fa_file_text_o , AppPreference.getBoolean(getActivity(), AppPreference.RECENT_APP_KEY_TEST)));
        deviceTests.add(new Test("Search Key",FontAwesomeIcons.fa_search , AppPreference.getBoolean(getActivity(), AppPreference.SEARCH_KEY_TEST)));
        deviceTests.add(new Test("Performance",FontAwesomeIcons.fa_tasks , AppPreference.getBoolean(getActivity(), AppPreference.PERFORMANCE_TEST)));
        deviceTests.add(new Test("Ram Size",FontAwesomeIcons.fa_hdd_o , AppPreference.getBoolean(getActivity(), AppPreference.RAM_SIZE_TEST)));
        deviceTests.add(new Test("SD Card",FontAwesomeIcons.fa_tablet , AppPreference.getBoolean(getActivity(), AppPreference.SD_CARD_TEST)));
        deviceTests.add(new Test("Vibration",FontAwesomeIcons.fa_mobile , AppPreference.getBoolean(getActivity(), AppPreference.VIBRATION_TEST)));
        deviceTests.add(new Test("Storage",FontAwesomeIcons.fa_archive , AppPreference.getBoolean(getActivity(), AppPreference.STORAGE_TEST)));

    }

    private void initProgressBar() {
        progressBarDevice = (ProgressBar) getActivity().findViewById(R.id.progressBarDevice);
        progressBarDevice.setMax(deviceTests.size());
    }

    private void initListView() {
        testListViewAdapter = new TestListViewAdapter(getActivity(), deviceTests, progressBarDevice);
        deviceTestListView.setAdapter(testListViewAdapter);
        deviceTestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivityForResult(new Intent(getContext(), BackKeyTestActivity.class),BACK_KEY_TEST_CODE);
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
                case BACK_KEY_TEST_CODE:
                    updateUI(0, AppPreference.BACK_KEY_TEST);
                    break;
            }
        }
    }

    private void updateUI(int position, String deviceTest) {
        if (!AppPreference.getBoolean(getActivity(), deviceTest)) {
            testListViewAdapter.setSelected(position);
            AppPreference.setBoolean(getActivity(), deviceTest, true);
        }
    }
}
