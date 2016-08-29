package mobello.amtrust.com.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.CallReceiverActivity;
import mobello.amtrust.com.activity.HeadSetMicrophoneTestActivity;
import mobello.amtrust.com.activity.HeadsetTestActivity;
import mobello.amtrust.com.adapter.TestListViewAdapter;
import mobello.amtrust.com.model.Test;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Constant;


/**
 * A simple {@link Fragment} subclass.
 */
public class AudioTestFragment extends Fragment {

    private ListView audioTestListView;
    private ArrayList<Test> audioTests;
    private TestListViewAdapter testListViewAdapter;
    private ProgressBar progressBarAudio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        audioTestListView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAudioTests();
        initProgressBar();
        initListView();
    }

    private void initAudioTests() {
        audioTests = new ArrayList<Test>();
        audioTests.add(new Test("Call Receiver", FontAwesomeIcons.fa_phone, AppPreference.getBoolean(getActivity(), AppPreference.CALL_RECEIVER_TEST)));
        audioTests.add(new Test("Headset", FontAwesomeIcons.fa_headphones, AppPreference.getBoolean(getActivity(), AppPreference.HEADSET_TEST)));
        audioTests.add(new Test("Headset Microphone", FontAwesomeIcons.fa_microphone, AppPreference.getBoolean(getActivity(), AppPreference.HEADSET_MICROPHONE_TEST)));
        audioTests.add(new Test("HeadSet Button",FontAwesomeIcons.fa_list, AppPreference.getBoolean(getActivity(), AppPreference.HEADSET_BUTTON_TEST)));
        audioTests.add(new Test("Microphone",FontAwesomeIcons.fa_microphone, AppPreference.getBoolean(getActivity(), AppPreference.MICROPHONE_TEST)));
        audioTests.add(new Test("Speaker",FontAwesomeIcons.fa_volume_up, AppPreference.getBoolean(getActivity(), AppPreference.SPEAKER)));
    }

    private void initProgressBar() {
        progressBarAudio = (ProgressBar) getActivity().findViewById(R.id.progressBarAudio);
        progressBarAudio.setMax(audioTests.size());
    }

    private void initListView() {
        testListViewAdapter = new TestListViewAdapter(getActivity(), audioTests, progressBarAudio);
        audioTestListView.setAdapter(testListViewAdapter);
        audioTestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        CallReceiverActivity.startForResult(getActivity(), Constant.CALL_RECEIVER_REQUEST_CODE);
                        break;
                    case 1:
                        HeadsetTestActivity.startForResult(getActivity(), Constant.HEADSET_REQUEST_CODE);
                        break;
                    case 2:
                        HeadSetMicrophoneTestActivity.startForResult(getActivity(), Constant.HEADSET_MICROPHONE_REQUEST_CODE);
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case Constant.CALL_RECEIVER_REQUEST_CODE:
                    updateUI(0, AppPreference.CALL_RECEIVER_TEST);
                    break;
                case Constant.HEADSET_REQUEST_CODE:
                    updateUI(1,AppPreference.HEADSET_TEST);
                    break;
                case Constant.HEADSET_MICROPHONE_REQUEST_CODE:
                    updateUI(2,AppPreference.HEADSET_MICROPHONE_TEST);
                    break;
            }
        }
    }

    private void updateUI(int position, String key) {
        if (!AppPreference.getBoolean(getActivity(), key)) {
            testListViewAdapter.setSelected(position);
            AppPreference.setBoolean(getActivity(), key, true);
        }
    }
}
