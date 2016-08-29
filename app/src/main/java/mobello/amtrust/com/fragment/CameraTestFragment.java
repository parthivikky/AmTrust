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
public class CameraTestFragment extends Fragment {


    public CameraTestFragment() {
        // Required empty public constructor
    }

    private ListView cameraTestListView;
    private ArrayList<Test> cameraTests;
    private TestListViewAdapter testListViewAdapter;
    private ProgressBar progressBarCamera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cameraTestListView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCameraTests();
        initProgressBar();
        initListView();
    }

    private void initCameraTests() {
        cameraTests = new ArrayList<Test>();
        cameraTests.add(new Test("Back Camera", FontAwesomeIcons.fa_camera,  AppPreference.getBoolean(getActivity(), AppPreference.BACK_CAMERA_TEST)));
        cameraTests.add(new Test("Camera Flash", FontAwesomeIcons.fa_flash, AppPreference.getBoolean(getActivity(), AppPreference.CAMERA_FLASH_TEST)));
        cameraTests.add(new Test("Front Camera", FontAwesomeIcons.fa_eye,AppPreference.getBoolean(getActivity(), AppPreference.FRONT_CAMERA_TEST)));
        cameraTests.add(new Test("Video",FontAwesomeIcons.fa_video_camera, AppPreference.getBoolean(getActivity(), AppPreference.VIDEO_TEST)));
        cameraTests.add(new Test("Auto Focus",FontAwesomeIcons.fa_square_o, AppPreference.getBoolean(getActivity(), AppPreference.AUTO_FOCUS_TEST)));

    }

    private void initProgressBar() {
        progressBarCamera = (ProgressBar) getActivity().findViewById(R.id.progressBarCamera);
        progressBarCamera.setMax(cameraTests.size());
    }

    private void initListView() {
        testListViewAdapter = new TestListViewAdapter(getActivity(), cameraTests, progressBarCamera);
        cameraTestListView.setAdapter(testListViewAdapter);
    }

}
