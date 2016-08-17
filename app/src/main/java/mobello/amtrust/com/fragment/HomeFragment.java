package mobello.amtrust.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.DeviceDiagnosisActivity;
import mobello.amtrust.com.activity.AllPlansActivity;
import mobello.amtrust.com.activity.ProtectionPlansActivity;
import mobello.amtrust.com.activity.TechSupportActivity;
import mobello.amtrust.com.activity.TradeInActivity;
import mobello.amtrust.com.adapter.HomeAdapter;
import mobello.amtrust.com.model.TradeIn;
import mobello.amtrust.com.widget.BlurredImageView;
import mobello.amtrust.com.widget.ParallaxScollListView;


public class HomeFragment extends Fragment {


    private ParallaxScollListView listView;
    private BlurredImageView blurredImageView;
    private View view, headerView;

    private int screenHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_parallex, container, false);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.home_parallex_header, null);
        initViews(view);
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();

        blurredImageView.setImageResource(R.drawable.home_header);
        listView.setParallaxImageView(blurredImageView);
        listView.setZoomRatio(ParallaxScollListView.ZOOM_X2);
        listView.addHeaderView(headerView);
        listView.setAdapter(new HomeAdapter(getActivity()));
        listView.setOnItemClickListener(itemClickListener);
        return view;
    }

    private void initViews(View view) {
        listView = (ParallaxScollListView) view.findViewById(R.id.list_view);
        blurredImageView = (BlurredImageView) headerView.findViewById(R.id.header_profile_image);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
//                    PolicyActivity.start(getActivity());
                    break;
                case 1:
                    ProtectionPlansActivity.start(getActivity());
                    break;
                case 2:
                    DeviceDiagnosisActivity.start(getActivity());
                    break;
                case 3:
                    TradeInActivity.start(getActivity());
                    break;
                case 4:
                    TechSupportActivity.start(getActivity());
                    break;
            }
        }
    };


    @Override
    public void onStart() {
        super.onStart();
    }
}
