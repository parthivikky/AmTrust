package mobello.amtrust.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import mobello.amtrust.com.R;
import mobello.amtrust.com.adapter.UserLogAdapter;
import mobello.amtrust.com.model.UserLog;
import mobello.amtrust.com.utility.AppPreference;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimelineFragment extends Fragment {

    private RelativeLayout emptyContainer;
    private ListView listView;
    private UserLogAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserActivityLog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void getUserActivityLog() {
//        Helper.showProgress(getActivity());
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<UserLog> userLogCall = apiInterface.userLog(WebConstant.USER_ACTIVITY_LOG,
                AppPreference.getString(getActivity(), AppPreference.SESSION_NAME), AppPreference.getString(getActivity(), AppPreference.EMAIL), "AMS");
        userLogCall.enqueue(new Callback<UserLog>() {
            @Override
            public void onResponse(Call<UserLog> call, Response<UserLog> response) {
//                Helper.dismissProgress();
                UserLog userLog = response.body();
                if(userLog.getSuccess()){
                    if(userLog.getResult().getStatus().equalsIgnoreCase(WebConstant.SUCCESS)){
                        adapter = new UserLogAdapter(getActivity(),userLog.getResult().getData());
                        listView.setAdapter(adapter);
                    }
                }else{
                    Helper.showMessageToast(getActivity(),userLog.getError().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserLog> call, Throwable t) {
//                Helper.dismissProgress();
            }
        });
    }

    private void initViews(View view) {
        emptyContainer = (RelativeLayout)view.findViewById(R.id.empty_container);
        listView = (ListView)view.findViewById(R.id.list_view);
    }
}
