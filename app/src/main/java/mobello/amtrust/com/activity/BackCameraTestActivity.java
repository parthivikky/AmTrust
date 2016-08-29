package mobello.amtrust.com.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconButton;

import mobello.amtrust.com.R;

public class BackCameraTestActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 10;
    private static final int MY_PERMISSIONS_REQUEST = 100;
    private ImageView mImageView;
    private IconButton cameraButton;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_camera_test);
        initUI();

    }

    private void initUI() {
        mImageView = (ImageView) findViewById(R.id.image_taken);
        cameraButton = (IconButton) findViewById(R.id.camera_test_button);
        title = (TextView) findViewById(R.id.title);
        title.setText("Back Camera");

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backCameraIsPresent()) {
                    requestPermissionsBeforeTest();
                } else
                    Toast.makeText(BackCameraTestActivity.this, "Device does not have a rear camera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private boolean backCameraIsPresent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String backCameraId = null;
            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                for (String cameraId : manager.getCameraIdList()) {
                    CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
                    Integer facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                    if (facing == CameraMetadata.LENS_FACING_BACK) {
                        backCameraId = cameraId;
                        break;
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            return (backCameraId != null);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            int backCameraId = -1;
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    backCameraId = i;
                    break;
                }
            }
            return (backCameraId > -1);
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mImageView.setImageBitmap(imageBitmap);
                if (mImageView.getDrawable() != null)
                    Toast.makeText(BackCameraTestActivity.this, "The camera works", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED)
                Toast.makeText(BackCameraTestActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermissionsBeforeTest() {

        if (ContextCompat.checkSelfPermission(BackCameraTestActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(BackCameraTestActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST);
        else
            dispatchTakePictureIntent();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        dispatchTakePictureIntent();
                    } else {
                        Toast.makeText(
                                BackCameraTestActivity.this,
                                "Permission denied",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(
                            BackCameraTestActivity.this,
                            "Permission denied",
                            Toast.LENGTH_SHORT).show();
                }

            }
            break;

        }
    }
}
