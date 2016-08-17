package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shinelw.library.ColorArcProgressBar;

import org.acra.ReportField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mobello.amtrust.com.R;
import mobello.amtrust.com.model.ApiStatus;
import mobello.amtrust.com.model.RetailersPrice;
import mobello.amtrust.com.utility.DBHelper;
import mobello.amtrust.com.utility.Helper;
import mobello.amtrust.com.utility.RetrofitApi;
import mobello.amtrust.com.utility.WebConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthResultActivity extends AppCompatActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,HealthResultActivity.class));
    }

    private ColorArcProgressBar progressBar;
    private TextView txtTitle;
    private LinearLayout retailerContainer;
    private RelativeLayout testingResultContainer;

    private DBHelper dbHelper;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_result);
        initViews();
        txtTitle.setText(R.string.health_check_result);
        jsonArray = dbHelper.getQuickScanFeatures();
        jsonObject = new JSONObject();
        Log.i("json array",jsonArray.toString());
        for (int i = 0; i < jsonArray.length() ; i++){
            try {
                String status;

                JSONObject object = jsonArray.getJSONObject(i);
                if(object.getInt("is_working") == 1) {
                    count++;
                    status = "Working";
                }else{
                    status = "Not Working";
                }
                jsonObject.put(object.getString("name"),status);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        progressBar.setCurrentValues(count);
        getRetailerPrice();

        testingResultContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SummaryActivity.start(HealthResultActivity.this,jsonArray);
            }
        });
    }

    private void getRetailerPrice() {
        Helper.showProgress(this);
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance(RetrofitApi.PRICE_BASE_URL);
        Call<RetailersPrice> retailPrice = apiInterface.calculatePrice("iPhone 6S",
                "Malaysia", jsonObject.toString());
        retailPrice.enqueue(new Callback<RetailersPrice>() {
            @Override
            public void onResponse(Call<RetailersPrice> call, Response<RetailersPrice> response) {
                Helper.dismissProgress();
                RetailersPrice price = response.body();
                if(price.getStatus().equalsIgnoreCase(WebConstant.SUCCESS)){
                    Log.i("size",price.getRetailers().size() + "");
                    setRetailersDetail(price.getRetailers());
                }
            }

            @Override
            public void onFailure(Call<RetailersPrice> call, Throwable t) {
                Helper.dismissProgress();
                t.printStackTrace();
            }
        });
    }

    private void setRetailersDetail(ArrayList<RetailersPrice.Retailer> retailers){
        for(RetailersPrice.Retailer retailer : retailers){
            View view = LayoutInflater.from(this).inflate(R.layout.retailer_row,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.image_view);
            TextView name = (TextView)view.findViewById(R.id.name);
            TextView country = (TextView)view.findViewById(R.id.country);
            TextView price = (TextView)view.findViewById(R.id.price);
            name.setText(retailer.getName());
            country.setText("India");
            price.setText(retailer.getCurrencySymbol() + " " + retailer.getPrice());
            Glide.with(this).load(retailer.getLogo()).into(imageView);
            retailerContainer.addView(view);
        }
    }

    private void initViews() {
        txtTitle = _findViewById(R.id.title);
        progressBar = _findViewById(R.id.progress_bar);
        dbHelper = DBHelper.getInstance();
        retailerContainer = _findViewById(R.id.retailer_container);
        testingResultContainer = _findViewById(R.id.testing_result);
    }

    private <T extends View>T _findViewById(int resID){
        return (T)findViewById(resID);
    }
}
