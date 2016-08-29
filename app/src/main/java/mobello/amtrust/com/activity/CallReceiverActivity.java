package mobello.amtrust.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mobello.amtrust.com.R;
import mobello.amtrust.com.utility.Constant;

public class CallReceiverActivity extends AppCompatActivity {

    public static void startForResult(Activity activity,int request_code){
        activity.startActivityForResult(new Intent(activity,CallReceiverActivity.class),request_code);
    }

    private ImageView imgHeader;
    private TextView txtAbout, txtInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_to_all_test);
        ((TextView)findViewById(R.id.title)).setText("Call Receiver");
    }

    public void onTestClick(View view) {
        TestingDialogActivity.startForResult(this, Constant.CALL_RECEIVER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.CALL_RECEIVER_REQUEST_CODE && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
