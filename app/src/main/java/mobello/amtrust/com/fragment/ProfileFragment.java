package mobello.amtrust.com.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.HomeActivity;
import mobello.amtrust.com.adapter.ProfileParallexAdapter;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.ResourceUtils;
import mobello.amtrust.com.widget.BlurredImageView;
import mobello.amtrust.com.widget.ParallaxScollListView;
import mobello.amtrust.com.widget.SelectableRoundedImageView;

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view, headerView;
    private ParallaxScollListView parallaxScollListView;
    private BlurredImageView blurredImageView;
    private SelectableRoundedImageView imgProfile;
    private TextView txtProfileName;
    private int screenHeight;
    private boolean isBlur = true;
    private LinearLayout profileLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.parallex_header, null);
        initViews();
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        ProfileParallexAdapter adapter = new ProfileParallexAdapter(getActivity());
        txtProfileName.setText("Steve Jobs");
        imgProfile.setImageResource(R.drawable.android);
        blurredImageView.setImageResource(R.drawable.android);
//            blurredImageView.setRadius((float) 0);
        parallaxScollListView.setParallaxImageView(blurredImageView);
        parallaxScollListView.addHeaderView(headerView);
        parallaxScollListView.setAdapter(adapter);
        parallaxScollListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    /*int imageHeight = blurredImageView.getHeight();
                    int screenScroll = (int) (screenHeight / 2.5);
                    if (imageHeight > screenScroll) {
                        if (isBlur) {
                            isBlur = false;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    blurredImageView.setRadius((float) 0);
                                    profileLayout.setAlpha(0);
                                    blurredImageView.postInvalidate();
                                }
                            });

                        }
                    } else if (imageHeight < screenScroll) {
                        if (!isBlur) {
                            isBlur = true;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    blurredImageView.setRadius((float) 1);
                                    profileLayout.setAlpha(1);
                                    blurredImageView.postInvalidate();
                                }
                            });
                        }
                    }*/
            }
        });
        return view;
    }

    private void initViews() {
        blurredImageView = _headerFindViewById(R.id.header_profile_image);
        imgProfile = _headerFindViewById(R.id.img_profile);
        txtProfileName = _headerFindViewById(R.id.txt_profile_name);
        parallaxScollListView = _findViewById(R.id.list_view);
        profileLayout = _headerFindViewById(R.id.profile_layout);
    }

    private <T extends View> T _findViewById(int resId) {
        return (T) view.findViewById(resId);
    }

    private <T extends View> T _headerFindViewById(int resId) {
        return (T) headerView.findViewById(resId);
    }


}
