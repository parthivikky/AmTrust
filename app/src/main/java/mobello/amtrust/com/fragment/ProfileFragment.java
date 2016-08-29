package mobello.amtrust.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedHashMap;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.ProfileEditActivity;
import mobello.amtrust.com.adapter.ProfileParallexAdapter;
import mobello.amtrust.com.model.ProfileInfo;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import mobello.amtrust.com.widget.BlurredImageView;
import mobello.amtrust.com.widget.ParallaxScollListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private static final int PROFILE_EDIT_REQUEST_CODE = 1;
    private View view, headerView;
    private ImageView imgEdit;
    private TextView txtDevices,txtPolicies,txtDiagnosis;
    private ParallaxScollListView parallaxScollListView;
    private BlurredImageView blurredImageView;
    private ProfileParallexAdapter adapter;

    private LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
    private ProfileInfo.Result_ result;
//    private SelectableRoundedImageView imgProfile;
//    private TextView txtProfileName;
    private int screenHeight;
    private boolean isBlur = true;
//    private LinearLayout profileLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getProfileInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.parallex_header, null);

//        blurredImageView.setRadius((float) 0);
//        imgProfile.setImageResource(R.drawable.android);
//        txtProfileName.setText("Steve Jobs");
        /*parallaxScollListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int imageHeight = blurredImageView.getHeight();
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
                    }
            }
        });*/
        return inflater.inflate(R.layout.fragment_parallex, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        imgEdit.setImageResource(R.drawable.profile_edit);
        parallaxScollListView.setZoomRatio(ParallaxScollListView.ZOOM_X2);
        parallaxScollListView.setParallaxImageView(blurredImageView);
        parallaxScollListView.addHeaderView(headerView);
        adapter = new ProfileParallexAdapter(getActivity(), linkedHashMap);
        parallaxScollListView.setAdapter(adapter);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileEditActivity.startForResult(getActivity(),result,PROFILE_EDIT_REQUEST_CODE);
            }
        });
    }

    private void getProfileInfo() {
        Helper.showProgress(getActivity());
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<ProfileInfo> profileInfoCall = apiInterface.getProfileInfo(WebConstant.PROFILE_DETAIL,
                AppPreference.getString(getActivity(), AppPreference.SESSION_NAME), AppPreference.getString(getActivity(), AppPreference.EMAIL), "AMS");
        profileInfoCall.enqueue(new Callback<ProfileInfo>() {
            @Override
            public void onResponse(Call<ProfileInfo> call, Response<ProfileInfo> response) {
                Helper.dismissProgress();
                ProfileInfo info = response.body();
                if (info.getSuccess()) {
                    if (info.getResult().getStatus().equalsIgnoreCase(WebConstant.SUCCESS)) {
                        result = info.getResult().getResult();
                        assignValues(result);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileInfo> call, Throwable t) {
                Helper.dismissProgress();
            }
        });
    }

    private void initViews() {
        blurredImageView = _headerFindViewById(R.id.header_profile_image);
        txtDevices = _headerFindViewById(R.id.devices);
        txtDiagnosis = _headerFindViewById(R.id.diagnosis);
        txtPolicies = _headerFindViewById(R.id.policies);
        parallaxScollListView = _findViewById(R.id.list_view);
        imgEdit = _findViewById(R.id.menu_right);
//        imgProfile = _headerFindViewById(R.id.img_profile);
//        txtProfileName = _headerFindViewById(R.id.txt_profile_name);
//        profileLayout = _headerFindViewById(R.id.profile_layout);
    }

    private <T extends View> T _findViewById(int resId) {
        return (T) view.findViewById(resId);
    }

    private <T extends View> T _headerFindViewById(int resId) {
        return (T) headerView.findViewById(resId);
    }

    private void assignValues(ProfileInfo.Result_ result) {
        linkedHashMap.clear();
        Glide.with(this).load(result.getProfile_picture()).placeholder(R.drawable.placeholder).into(blurredImageView);
        txtPolicies.setText(result.getNo_of_policies() + " Policies");
        txtDiagnosis.setText(result.getNo_of_diagnosis() + " Diagnosis");
        txtDevices.setText(result.getNo_of_devices() + " Decices");
        linkedHashMap.put("First Name", result.getFirstname());
        linkedHashMap.put("Last Name", result.getLastname());
        linkedHashMap.put("Email", result.getEmail());
        linkedHashMap.put("Mobile", result.getMobile());
        linkedHashMap.put("Address", result.getAddress());
        linkedHashMap.put("City", result.getCity());
        linkedHashMap.put("State", result.getState());
        linkedHashMap.put("Country", result.getCountry());
        adapter.insert(linkedHashMap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PROFILE_EDIT_REQUEST_CODE && resultCode == getActivity().RESULT_OK){
            getProfileInfo();
        }
    }
}
