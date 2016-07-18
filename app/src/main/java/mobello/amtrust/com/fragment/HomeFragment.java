package mobello.amtrust.com.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.DeviceActivity;
import mobello.amtrust.com.activity.DeviceDiagnosisActivity;
import mobello.amtrust.com.activity.PolicyActivity;
import mobello.amtrust.com.activity.ProductActivity;
import mobello.amtrust.com.activity.TechSupportActivity;
import mobello.amtrust.com.adapter.HomeAdapter;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.widget.BlurredImageView;
import mobello.amtrust.com.widget.ParallaxScollListView;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ParallaxScollListView listView;
    private BlurredImageView blurredImageView;
    private View view, headerView;

    private int screenHeight;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.home_parallex_header, null);
        initViews(view);
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();

        blurredImageView.setImageResource(R.drawable.home_header);
        listView.setParallaxImageView(blurredImageView);
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
                    ProductActivity.start(getActivity());
                    break;
                case 2:
                    DeviceDiagnosisActivity.start(getActivity());
                    break;
                case 3:
//                    TechSupportActivity.start(getActivity());
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
