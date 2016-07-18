package mobello.amtrust.com.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import mobello.amtrust.com.R;
import mobello.amtrust.com.activity.QuickScanActivity;
import mobello.amtrust.com.utility.Version;

public class AutomaticTestingFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int BLUETOOTH_REQUEST = 0;
    private static final int GPS_REQUEST = 1;
    private static final int REQUEST_LOCATION = 2;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private TextView continues, txtWifiState, txtDataState, txtBluetoothState, txtBatteryState, txtAccelerometerState, txtGPSState;
    private ImageView imgWifi, imgData, imgBluetooth, imgBattery, imgAccelerometer, imgGPS;

    private GoogleApiClient googleApiClient;

    private IntentFilter batteryFilter, batteryTempFilter;
    private BroadcastReceiver batteryReceiver, batteryTempReceiver;

    public AutomaticTestingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AutomaticTestingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AutomaticTestingFragment newInstance(String param1, String param2) {
        AutomaticTestingFragment fragment = new AutomaticTestingFragment();
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
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_automatic_testing, container, false);
        initViews();

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = ((QuickScanActivity) getActivity()).getPagerCurrentPosition();
                ((QuickScanActivity) getActivity()).moveToNextPage(currentItem);
            }
        });

        setWifiState();

        if (Version.isBelowLollipop())
            setMobileState();
        else
            imgData.setImageResource(R.drawable.ic_data_red);
//            setMobileDataEnableAfterLollipop();


        setBluetoothState();

        if (isGPSEnabled()) {
            gpsState(true);
        } else {
            if (Version.isBelowKitkat())
                setGPSEnableBeforeKitkat();
            else
                setGPSEnableAfterKitkat();
        }

        setBatteryState();

        setGyroScopeState();

        /*setIntentFilter();
        setBroadCastReceiver();
        getActivity().registerReceiver(batteryReceiver,batteryFilter);
        getActivity().registerReceiver(batteryTempReceiver,batteryTempFilter);*/
        return rootView;
    }

    private <T extends View> T _findViewById(int viewId) {
        return (T) rootView.findViewById(viewId);
    }

    private void initViews() {
        continues = _findViewById(R.id.continues);
        txtWifiState = _findViewById(R.id.wifi_state);
        txtDataState = _findViewById(R.id.data_state);
        txtBluetoothState = _findViewById(R.id.bluetooth_state);
        txtBatteryState = _findViewById(R.id.battery_stae);
        txtAccelerometerState = _findViewById(R.id.accelerometer_state);
        txtGPSState = _findViewById(R.id.gps_state);
        imgWifi = _findViewById(R.id.ic_wifi);
        imgData = _findViewById(R.id.ic_data);
        imgBluetooth = _findViewById(R.id.ic_bluetooth);
        imgBattery = _findViewById(R.id.ic_battery);
        imgAccelerometer = _findViewById(R.id.ic_accelerometer);
        imgGPS = _findViewById(R.id.ic_gps);
    }

    private void setWifiState() {
        final WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (wifiManager.isWifiEnabled()) {
                    imgWifi.setImageResource(R.drawable.ic_wifi_green);
                    txtWifiState.setVisibility(View.VISIBLE);
                } else {
                    imgWifi.setImageResource(R.drawable.ic_wifi_red);
                }
            }
        }, 1000);
    }

    private void setMobileDataEnableAfterLollipop(){
        String command = null;
        try {
            boolean state = Settings.Global.getInt(getActivity().getContentResolver(), "mobile_data") == 1;
            Log.i("state","" + state);
            int stateInt = state ? 0 : 1;
            String transactionCode = getTransactionCode(getContext());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                SubscriptionManager mSubscriptionManager = (SubscriptionManager) getActivity().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                // Loop through the subscription list i.e. SIM list.
                for (int i = 0; i < mSubscriptionManager.getActiveSubscriptionInfoCountMax(); i++) {
                    if (transactionCode != null && transactionCode.length() > 0) {
                        // Get the active subscription ID for a given SIM card.
                        int subscriptionId = mSubscriptionManager.getActiveSubscriptionInfoList().get(i).getSubscriptionId();
                        // Execute the command via `su` to turn off
                        // mobile network for a subscription service.
                        command = "service call phone " + transactionCode + " i32 " + subscriptionId + " i32 " + stateInt;
                        executeCommandViaSu(getActivity(), "-c", command);
                    }
                }
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                // Android 5.0 (API 21) only.
                if (transactionCode != null && transactionCode.length() > 0) {
                    // Execute the command via `su` to turn off mobile network.
                    command = "service call phone " + transactionCode + " i32 " + stateInt;
                    executeCommandViaSu(getActivity(), "-c", command);
                }
            }


        }
        catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception ex)
        {
//            Log.e(TAG, "Error setting mobile data state", ex);
        }
    }

    private static void executeCommandViaSu(Context context, String option, String command) {
        boolean success = false;
        String su = "su";
        for (int i=0; i < 3; i++) {
            // Default "su" command executed successfully, then quit.
            if (success) {
                break;
            }
            // Else, execute other "su" commands.
            if (i == 1) {
                su = "/system/xbin/su";
            } else if (i == 2) {
                su = "/system/bin/su";
            }
            try {
                // Execute command as "su".
                Runtime.getRuntime().exec(new String[]{su, option, command});
            } catch (IOException e) {
                success = false;
                // Oops! Cannot execute `su` for some reason.
                // Log error here.
            } finally {
                success = true;
            }
        }
    }

    private static String getTransactionCode(Context context) throws Exception {
        try {
            final TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final Class<?> mTelephonyClass = Class.forName(mTelephonyManager.getClass().getName());
            final Method mTelephonyMethod = mTelephonyClass.getDeclaredMethod("getITelephony");
            mTelephonyMethod.setAccessible(true);
            final Object mTelephonyStub = mTelephonyMethod.invoke(mTelephonyManager);
            final Class<?> mTelephonyStubClass = Class.forName(mTelephonyStub.getClass().getName());
            final Class<?> mClass = mTelephonyStubClass.getDeclaringClass();
            final Field field = mClass.getDeclaredField("TRANSACTION_setDataEnabled");
            field.setAccessible(true);
            return String.valueOf(field.getInt(null));
        } catch (Exception e) {
            // The "TRANSACTION_setDataEnabled" field is not available,
            // or named differently in the current API level, so we throw
            // an exception and inform users that the method is not available.
            throw e;
        }
    }

    private void setMobileState() {
        try {
            final ConnectivityManager conman =
                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(
                    iConnectivityManager.getClass().getName());
            /*final Method[] methods = iConnectivityManagerClass.getDeclaredMethods();
            for (final Method method : methods) {
                if (method.toGenericString().contains("set")) {
                    Log.i("TESTING", "Method: " + method.getName());
                }
            }*/
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                    .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            if (null != setMobileDataEnabledMethod) {
                setMobileDataEnabledMethod.setAccessible(true);
                setMobileDataEnabledMethod.invoke(iConnectivityManager, true);
                imgData.setImageResource(R.drawable.ic_data_green);
                txtDataState.setVisibility(View.VISIBLE);
            } else {
                imgData.setImageResource(R.drawable.ic_data_red);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void setBluetoothState() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {
            imgBluetooth.setImageResource(R.drawable.ic_bluetooth_green);
            txtBluetoothState.setVisibility(View.VISIBLE);
        } else {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BLUETOOTH_REQUEST);
        }
    }

    private void setGPSEnableBeforeKitkat() {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        getActivity().sendBroadcast(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGPSEnabled()) {
                    gpsState(true);
                } else {
                    gpsState(false);
                }
            }
        }, 1000);
    }

    private void setGPSEnableAfterKitkat() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(AutomaticTestingFragment.this)
                    .addOnConnectionFailedListener(AutomaticTestingFragment.this).build();
            googleApiClient.connect();
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ? true : false;
    }

    private void gpsState(boolean isEnabled) {
        if (isEnabled) {
            imgGPS.setImageResource(R.drawable.ic_gps_green);
            txtGPSState.setVisibility(View.VISIBLE);
        } else {
            imgGPS.setImageResource(R.drawable.ic_gps_red);
        }
    }

    private void setGyroScopeState(){
        SensorManager sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            imgAccelerometer.setImageResource(R.drawable.ic_speedometer_green);
            txtAccelerometerState.setVisibility(View.VISIBLE);
        }else{
            imgAccelerometer.setImageResource(R.drawable.ic_speedometer_red);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case BLUETOOTH_REQUEST:
                    imgBluetooth.setImageResource(R.drawable.ic_bluetooth_green);
                    txtBluetoothState.setVisibility(View.VISIBLE);
                    break;
                case GPS_REQUEST:
                    if (isGPSEnabled()) {
                        gpsState(true);
                    } else
                        gpsState(false);
                    break;
                case REQUEST_LOCATION:
                    if (isGPSEnabled()) {
                        gpsState(true);
                    } else
                        gpsState(false);
                    break;
                case REQUEST_RESOLVE_ERROR:
                    mResolvingError = false;
                    if (!googleApiClient.isConnecting() &&
                            !googleApiClient.isConnected()) {
                        googleApiClient.connect();
                    }
                    break;
            }
        }
    }

    private void setBatteryState(){
        if (batteryTempFilter == null) {
            batteryTempFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        }
        if (batteryTempReceiver == null) {
            batteryTempReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                        /*float temperature = (float) (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
                        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
                        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                        int percentage = (level/ scale) * 100;
                        Log.i("scale", ""+ scale);
                        Log.i("level", ""+ level);
                        Log.i("percentage", ""+ percentage);*/
                        int status = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
                        if (status == BatteryManager.BATTERY_HEALTH_GOOD){
                            imgBattery.setImageResource(R.drawable.ic_battery_green);
                            txtBatteryState.setVisibility(View.VISIBLE);
                        }else {
                            imgBattery.setImageResource(R.drawable.ic_battery_red);
                        }

                        getActivity().unregisterReceiver(batteryTempReceiver);
                    }
                }
            };
        }
        getActivity().registerReceiver(batteryTempReceiver,batteryTempFilter);
    }
/*

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MOBILEDATA_REQUEST:
                Log.i("Permission","No need");
                setMobileState();
                break;
        }
    }
*/

    private void setIntentFilter() {
        if (batteryFilter == null) {
            batteryFilter = new IntentFilter();
            batteryFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        }
        if (batteryTempFilter == null) {
            batteryTempFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        }
    }

    private void setBroadCastReceiver() {
        if (batteryReceiver == null) {
            batteryReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                        /*batteryCharging.setBackgroundColor(Color.parseColor("#A4E785"));
                        batteryCharging.setTextColor(Color.parseColor("#ffffff"));*/
                        getActivity().unregisterReceiver(batteryReceiver);
                    }
                }
            };
        }
        if (batteryTempReceiver == null) {
            batteryTempReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                        float temperature = (float) (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
                        /*batteryTemp.setBackgroundColor(Color.parseColor("#A4E785"));
                        batteryTemp.setTextColor(Color.parseColor("#ffffff"));
                        batteryTemp.setText(batteryTemp.getText() + "         " + temperature);*/
                        getActivity().unregisterReceiver(batteryTempReceiver);
                    }
                }
            };
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    getActivity(), REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("connection", "suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(getActivity(), REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                googleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), "errordialog");
    }

    public void onDialogDismissed() {
        mResolvingError = false;
    }

    public class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            onDialogDismissed();
        }
    }

}
