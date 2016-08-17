package mobello.amtrust.com.fragment;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.Camera2Activity;
import mobello.amtrust.com.activity.CameraActivity;
import mobello.amtrust.com.utility.DBHelper;
import mobello.amtrust.com.utility.Version;


public class FrontCameraFragment extends Fragment {

    private static final int REQUEST_FRONT_CAMERA = 5;
    private TextView takePicture,skip;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_front_camera, container, false);
        takePicture = (TextView)view.findViewById(R.id.take_picture);
        skip = (TextView)view.findViewById(R.id.skip);
        dbHelper = DBHelper.getInstance();

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(Version.isBelowLollipop()) {
                    intent = new Intent(getActivity(), CameraActivity.class);
                    intent.putExtra("camera_id", Camera.CameraInfo.CAMERA_FACING_FRONT);
                }else{
                    intent = new Intent(getActivity(), Camera2Activity.class).putExtra("prevent_camera", CameraCharacteristics.LENS_FACING_BACK);
                }
                startActivityForResult(intent, REQUEST_FRONT_CAMERA);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = ((SemiAutomaticTestingFragment)getParentFragment()).getPagerCurrentPosition();
                ((SemiAutomaticTestingFragment)getParentFragment()).moveToNextPage(currentItem);
                dbHelper.quickScanFeatures("Front Camera", 0);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK && requestCode == REQUEST_FRONT_CAMERA) {
            ((SemiAutomaticTestingFragment) getParentFragment()).showTick();
            int currentItem = ((SemiAutomaticTestingFragment)getParentFragment()).getPagerCurrentPosition();
            ((SemiAutomaticTestingFragment)getParentFragment()).moveToNextPage(currentItem);
            dbHelper.quickScanFeatures("Front Camera", 1);
        }
    }
}
