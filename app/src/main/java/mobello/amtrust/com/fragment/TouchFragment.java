package mobello.amtrust.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.FullTouchActivity;
import mobello.amtrust.com.activity.QuickScanActivity;
import mobello.amtrust.com.utility.DBHelper;


public class TouchFragment extends Fragment {

    private static final int TOUCH_REQUEST_CODE = 6;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_touch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = DBHelper.getInstance();
        view.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullTouchActivity.start(getActivity(),TOUCH_REQUEST_CODE);
            }
        });

        view.findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = ((QuickScanActivity) getActivity()).getPagerCurrentPosition();
                ((QuickScanActivity) getActivity()).moveToNextPage(currentItem);
                dbHelper.quickScanFeatures("Touch", 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK && requestCode == TOUCH_REQUEST_CODE) {
            ((SemiAutomaticTestingFragment) getParentFragment()).showTick();
            int currentItem = ((QuickScanActivity) getActivity()).getPagerCurrentPosition();
            ((QuickScanActivity) getActivity()).moveToNextPage(currentItem);
            dbHelper.quickScanFeatures("Touch", 1);
        }
    }
}
