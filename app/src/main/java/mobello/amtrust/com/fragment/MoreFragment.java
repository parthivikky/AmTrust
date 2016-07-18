package mobello.amtrust.com.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobello.amtrust.com.BuildConfig;
import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.ChangePasswordActivity;
import mobello.amtrust.com.activity.MainActivity;
import mobello.amtrust.com.model.Status;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreFragment extends Fragment {


    private View view;
    private TextView txtVersion,txtBuild,txtLogout,changePassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, container, false);
        initViews();

        txtVersion.setText(String.valueOf(BuildConfig.VERSION_NAME));
        txtBuild.setText(String.valueOf(BuildConfig.VERSION_CODE));

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.start(getActivity());
            }
        });
        return view;
    }

    private void initViews() {
        txtVersion = _findViewById(R.id.version);
        txtBuild = _findViewById(R.id.build);
        txtLogout = _findViewById(R.id.logout);
        changePassword = _findViewById(R.id.change_password);
    }

    private void logout(){
        Helper.showProgress(getActivity());
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Status> logoutCall = apiInterface.customerLogOut(WebConstant.AMS_LOGOUT,AppPreference.getString(getActivity(),AppPreference.EMAIL));
        logoutCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if(response.body().getSuccess()){
                    sessionLogout();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void sessionLogout() {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Status> sessionLogoutCall = apiInterface.sessionLogout(WebConstant.LOGOUT,AppPreference.getString(getActivity(),AppPreference.SESSION_NAME));
        sessionLogoutCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Helper.dismissProgress();
                if(response.body().getSuccess()){
                    AppPreference.clearPreference(getActivity());
                    MainActivity.start(getActivity());
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    private <T extends View> T _findViewById(int resId){
        return (T)view.findViewById(resId);
    }

}
