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
public class ConnectTestFragment extends Fragment {


    public ConnectTestFragment() {
        // Required empty public constructor
    }

    private ListView connectTestListView;
    private ArrayList<Test> connectTests;
    private TestListViewAdapter testListViewAdapter;
    private ProgressBar progressBarConnect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectTestListView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConnectTests();
        initProgressBar();
        initListView();
    }

    private void initConnectTests() {
        connectTests = new ArrayList<Test>();
        connectTests.add(new Test("Bluetooth", FontAwesomeIcons.fa_bluetooth_b, AppPreference.getBoolean(getActivity(), AppPreference.BLUETOOTH_TEST)));
        connectTests.add(new Test("Carrier Signal test",FontAwesomeIcons.fa_signal, AppPreference.getBoolean(getActivity(), AppPreference.CARRIER_SIGNAL_TEST)));
        connectTests.add(new Test("Carrier Signal dual sim",FontAwesomeIcons.fa_signal, AppPreference.getBoolean(getActivity(), AppPreference.CARRIER_SIGNAL_DUAL_TEST)));
        connectTests.add(new Test("Wifi",FontAwesomeIcons.fa_wifi , AppPreference.getBoolean(getActivity(), AppPreference.WIFI_TEST)));
    }

    private void initProgressBar() {
        progressBarConnect = (ProgressBar) getActivity().findViewById(R.id.progressBarConnect);
        progressBarConnect.setMax(connectTests.size());
    }

    private void initListView() {
        testListViewAdapter = new TestListViewAdapter(getActivity(), connectTests, progressBarConnect);
        connectTestListView.setAdapter(testListViewAdapter);
    }
}
