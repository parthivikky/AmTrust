package mobello.amtrust.com.activity;

import android.graphics.SurfaceTexture;
import android.media.ImageReader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;

import java.io.IOException;

import me.aflak.ezcam.EZCam;
import mobello.amtrust.com.R;
import mobello.amtrust.com.widget.AutoFitTextureView;

public class Camera2Activity extends AppCompatActivity {

    private EZCam cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camear2);

        cam = new EZCam(this);
        cam.selectCamera(EZCam.FRONT);
        cam.setStopPreviewOnPicture(true);
        cam.setEZCamCallback(new EZCam.EZCamCallback() {
            @Override
            public void onPicture(ImageReader reader) {
                try {
                    cam.saveImage(reader,"image.jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                Log.e("message", message);
            }
        });

        AutoFitTextureView textureView = (AutoFitTextureView)findViewById(R.id.texture);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                cam.startPreview(surfaceTexture,i,i1);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
        cam.takePicture();
        cam.stopPreview();
        cam.resumePreview();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
