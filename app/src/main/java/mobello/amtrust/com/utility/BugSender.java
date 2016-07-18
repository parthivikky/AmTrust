package mobello.amtrust.com.utility;

import android.content.Context;
import android.provider.SyncStateContract;

import org.acra.ACRA;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import mobello.amtrust.com.model.ApiStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BugSender implements ReportSender {

    private Context context;

    public BugSender(Context context, Exception errorLog) {
        this.context = context;
        ACRA.getErrorReporter().handleException(errorLog);
    }

    @Override
    public void send(Context context, CrashReportData errorContent) throws ReportSenderException {
        try {
//            bugReport(errorContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bugReport(String crashReport) {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<ApiStatus> reportCall = apiInterface.sendBugReport("1","Parthi", crashReport, "Android");
        reportCall.enqueue(new Callback<ApiStatus>() {
            @Override
            public void onResponse(Call<ApiStatus> call, Response<ApiStatus> response) {
                if(response.isSuccessful()){
                    ApiStatus apiStatus = response.body();
                    if(apiStatus.getStatus().equalsIgnoreCase("success")){
                        Helper.showMessageToast(context,"Bug report sent successfully");
                    }
                    else{
                        Helper.showMessageToast(context,"Bug report sending failed");
                    }
                }
                else
                    Helper.showMessageToast(context,"Bug report sending failed");
            }

            @Override
            public void onFailure(Call<ApiStatus> call, Throwable t) {
                Helper.showMessageToast(context,"Bug report sending failed");
            }
        });
    }
}
