package at.wada811.android.camera.sample;

import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import java.util.List;
import at.wada811.android.camera.CameraSize;
import at.wada811.android.camera.CameraSizePolicy;
import at.wada811.android.camera.sample.CameraControllerFragment.CameraControllerCallback;
import at.wada811.android.camera.sample.CameraControllerFragment.CameraControllerCallbackProvider;
import at.wada811.android.camera.sample.CameraPreviewFragment.CameraPreviewCallback;
import at.wada811.android.camera.sample.CameraPreviewFragment.CameraPreviewCallbackProvider;


public class CameraActivity extends ActionBarActivity
    implements CameraPreviewCallbackProvider, CameraControllerCallbackProvider{

    public static final String EXTRA_FACING = "facing";
    public static final String EXTRA_POLICY = "policy";

    private CameraPreviewFragment cameraPreviewFragment;
    private CameraControllerFragment cameraControllerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        int facing = getIntent().getIntExtra(EXTRA_FACING, CameraInfo.CAMERA_FACING_BACK);
        CameraSizePolicy policy = (CameraSizePolicy)getIntent().getSerializableExtra(EXTRA_POLICY);
        if(savedInstanceState == null){
            cameraPreviewFragment = CameraPreviewFragment.newInstance(facing, policy);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.preview, cameraPreviewFragment)
                .commit();
        }else{
            cameraPreviewFragment = (CameraPreviewFragment)getSupportFragmentManager().findFragmentById(
                R.id.preview
            );
        }
    }

    @Override
    public CameraPreviewCallback getCameraPreviewCallback(){
        return new CameraPreviewCallback(){
            @Override
            public void onCameraOpen(){
                cameraControllerFragment = CameraControllerFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.controller, cameraControllerFragment)
                    .commit();
            }

            @Override
            public void onCameraOpenFailed(){

            }

            @Override
            public void onCameraClose(){
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.controller);
                if(fragment != null){
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }

            @Override
            public void onTakePictureError(){
                cameraControllerFragment.onTakePictureError();
            }

            @Override
            public void onPictureTaken(){
                cameraControllerFragment.onPictureTaken();
            }
        };
    }

    @Override
    public CameraControllerCallback getCameraControllerCallback(){
        return new CameraControllerCallback(){

            @Override
            public void onClickPreview(){
                cameraPreviewFragment.autoFocus();
            }

            @Override
            public void onClickTakePicture(){
                cameraPreviewFragment.takePicture();
            }

            @Override
            public void onClickTakePreviewPicture(){
                cameraPreviewFragment.takePreviewPicture();
            }

            @Override
            public void onClickTakePictures(){
                cameraPreviewFragment.takePictures(2);
            }

            @Override
            public void onClickTakePreviewPictures(){
                cameraPreviewFragment.takePreviewPictures(2);
            }

            @Override
            public void onClickRecordVideo(){
                cameraPreviewFragment.toggleRecordingVideo();
            }

            @Override
            public void onClickRecordTimeLapseVideo(){
                cameraPreviewFragment.toggleRecordingTimeLapseVideo();
            }

            @Override
            public boolean isMultiCameraSupported(){
                return cameraPreviewFragment.isMultiCameraSupported();
            }

            @Override
            public void onClickSwitchCamera(){
                cameraPreviewFragment.switchCamera();
            }

            @Override
            public boolean isVideoSnapshotSupported(){
                return cameraPreviewFragment.isVideoSnapshotSupported();
            }

            @Override
            public List<CameraSize> getSupportedPictureSize(){
                return cameraPreviewFragment.getSupportedPictureSizes();
            }

            @Override
            public void onPictureSizeSelected(CameraSize pictureSize){
                cameraPreviewFragment.changePictureSize(pictureSize);
            }

            @Override
            public List<CameraSize> getSupportedVideoSize(){
                return cameraPreviewFragment.getSupportedVideoSizes();
            }


            @Override
            public void onVideoSizeSelected(CameraSize videoSize){
                cameraPreviewFragment.changeVideoSize(videoSize);
            }
        };
    }

}
