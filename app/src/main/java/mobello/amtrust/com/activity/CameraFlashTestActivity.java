package mobello.amtrust.com.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconButton;

import mobello.amtrust.com.R;

public class CameraFlashTestActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 200;
    private TextView title;
    private Camera camera;
    private IconButton flashButton;
    private boolean cameraTurnedOn = false;
    private boolean isFlashWorking = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_flash_test);

        initUI();
    }

    private void requestPermissionsBeforeTest() {

        if (ContextCompat.checkSelfPermission(CameraFlashTestActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(CameraFlashTestActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST);
        else
            turnOnFlash();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        turnOnFlash();
                    } else {
                        Toast.makeText(
                                CameraFlashTestActivity.this,
                                "Permission denied",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(
                            CameraFlashTestActivity.this,
                            "Permission denied",
                            Toast.LENGTH_SHORT).show();
                }

            }
            break;

        }
    }

    private void turnOnFlash() {
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            cameraTurnedOn = true;

        } catch (Exception e) {
            e.printStackTrace();
            isFlashWorking = false;
            Toast.makeText(getBaseContext(), "Exception throws in turning on flashlight.", Toast.LENGTH_SHORT).show();
        }

        if (isFlashWorking)
            Toast.makeText(getBaseContext(), "Flash works.", Toast.LENGTH_SHORT).show();

    }

    private boolean flashIsAvailable() {
        boolean g = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        return g;
    }

    private void initUI() {
        title = (TextView) findViewById(R.id.title);
        title.setText("Camera Flash");
        flashButton = (IconButton) findViewById(R.id.flash_test_button);
        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashIsAvailable())
                    requestPermissionsBeforeTest();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (flashIsAvailable()) {
                if (cameraTurnedOn) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception throws in turning off flashlight.", Toast.LENGTH_SHORT).show();
        }
    }
}
