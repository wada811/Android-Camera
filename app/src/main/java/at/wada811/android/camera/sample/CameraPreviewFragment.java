package at.wada811.android.camera.sample;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.io.File;
import java.util.List;
import at.wada811.android.camera.CameraHelper;
import at.wada811.android.camera.CameraPreview;
import at.wada811.android.camera.CameraSize;
import at.wada811.android.camera.CameraSizePolicy;
import at.wada811.android.camera.LogUtils;
import at.wada811.android.camera.PictureFormat;
import at.wada811.android.camera.RecordingConfig;
import at.wada811.android.camera.RecordingQuality;
import at.wada811.android.camera.callback.AutoFocusCallback;
import at.wada811.android.camera.callback.ErrorCallback;
import at.wada811.android.camera.callback.TakePictureCallback;
import at.wada811.android.camera.callback.TakePicturesCallback;
import at.wada811.android.camera.sample.SaveIntentService.SaveResultReceiver;
import at.wada811.android.camera.sample.SaveIntentService.SaveResultReceiver.OnSaveResultListener;

public class CameraPreviewFragment extends Fragment
    implements SurfaceHolder.Callback, ErrorCallback{

    public static final String TAG = CameraPreviewFragment.class.getSimpleName();
    private static final String KEY_CAMERA_ID = "cameraId";
    private static final String KEY_CAMERA_SIZE_POLICY = "cameraSizePolicy";
    private CameraPreviewCallback callback;

    private SurfaceHolder holder;
    private CameraHelper cameraHelper;
    private CameraPreview cameraPreview;

    public static ByteArrayCache<Integer> sByteArrayCache = new ByteArrayCache<Integer>();


    public interface CameraPreviewCallbackProvider{

        public CameraPreviewCallback getCameraPreviewCallback();
    }

    public interface CameraPreviewCallback{

        public void onCameraOpen();

        public void onCameraOpenFailed();

        public void onCameraClose();

        public void onTakePictureError();

        public void onPictureTaken();
    }

    public static CameraPreviewFragment newInstance(int cameraId, CameraSizePolicy policy){
        CameraPreviewFragment fragment = new CameraPreviewFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CAMERA_ID, cameraId);
        args.putSerializable(KEY_CAMERA_SIZE_POLICY, policy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        if(activity instanceof CameraPreviewCallbackProvider == false){
            throw new ClassCastException(
                activity.getLocalClassName() + " must implements " + CameraPreviewCallbackProvider.class
                    .getSimpleName()
            );
        }
        CameraPreviewCallbackProvider provider = (CameraPreviewCallbackProvider)activity;
        callback = provider.getCameraPreviewCallback();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        LogUtils.v();
        cameraPreview = new CameraPreview(getActivity());
        return cameraPreview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        LogUtils.v();
        int cameraId = getArguments().getInt(KEY_CAMERA_ID);
        int displayRotationDegrees = getRotationDegrees();
        cameraHelper = CameraHelper.newInstance(getActivity(), cameraId, displayRotationDegrees);
    }

    private int getRotationDegrees(){
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch(rotation){
            case Surface.ROTATION_0: // Portrait
                degrees = 0;
                break;
            case Surface.ROTATION_90: // Landscape
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        return degrees;
    }

    @Override
    public void onResume(){
        super.onResume();
        LogUtils.v();
        holder = cameraPreview.getHolder();
        holder.addCallback(this);
        if(cameraHelper.openCamera(holder)){
            callback.onCameraOpen();
            cameraHelper.setErrorCallback(this);
        }else{
            callback.onCameraOpenFailed();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        LogUtils.v();
        cameraHelper.closeCamera();
        if(holder != null){
            holder.removeCallback(this);
            holder = null;
        }
        callback.onCameraClose();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        LogUtils.v();
        int width = cameraPreview.getWidth();
        int height = cameraPreview.getHeight();
        LogUtils.v("width: " + width);
        LogUtils.v("height: " + height);
        cameraHelper.surfaceCreated(holder, cameraPreview.getWidth(), cameraPreview.getHeight());
        CameraSize cameraSize = cameraHelper.getPictureSize();
//        CameraSize cameraSize = cameraHelper.getParam().getSupportedPictureSizes().get(1);
        LogUtils.v("PictureSize.width: " + cameraSize.width);
        LogUtils.v("PictureSize.height: " + cameraSize.height);
        cameraHelper.setPictureSize(cameraSize);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        LogUtils.v("width: " + width);
        LogUtils.v("height: " + height);
        CameraSizePolicy policy = (CameraSizePolicy)getArguments().getSerializable(
            KEY_CAMERA_SIZE_POLICY
        );
        cameraPreview.surfaceChanged(cameraHelper, policy);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        LogUtils.v();
    }

    @Override
    public void onError(int error, Exception e){
        switch(error){
            case ErrorCallback.ERROR_SERVER_DIED:
                Toast.makeText(getActivity(), "ERROR_SERVER_DIED", Toast.LENGTH_LONG).show();
                break;
            case ErrorCallback.ERROR_AUTO_FOCUS_FAILED:
                Toast.makeText(getActivity(), "ERROR_AUTO_FOCUS_FAILED", Toast.LENGTH_LONG).show();
                callback.onTakePictureError();
                break;
            case ErrorCallback.ERROR_TAKE_PICTURE:
                Toast.makeText(getActivity(), "ERROR_TAKE_PICTURE", Toast.LENGTH_LONG).show();
                callback.onTakePictureError();
                break;
            case ErrorCallback.ERROR_RECORDING_ILLEGAL_STATE:
                Toast.makeText(getActivity(), "ERROR_RECORDING_ILLEGAL_STATE", Toast.LENGTH_LONG)
                    .show();
                callback.onTakePictureError();
                break;
            case ErrorCallback.ERROR_RECORDING_PREPARE_FAILED:
                Toast.makeText(getActivity(), "ERROR_RECORDING_PREPARE_FAILED", Toast.LENGTH_LONG)
                    .show();
                callback.onTakePictureError();
                break;
            case ErrorCallback.ERROR_RECORDING_START_FAILED:
                Toast.makeText(getActivity(), "ERROR_RECORDING_START_FAILED", Toast.LENGTH_LONG)
                    .show();
                callback.onTakePictureError();
                break;
            case ErrorCallback.ERROR_RECORDING_STOP_FAILED:
                Toast.makeText(getActivity(), "ERROR_RECORDING_STOP_FAILED", Toast.LENGTH_LONG)
                    .show();
                callback.onTakePictureError();
                break;
            case ErrorCallback.ERROR_UNKNOWN:
            default:
                Toast.makeText(getActivity(), "ERROR_UNKNOWN", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void autoFocus(){
        cameraHelper.autoFocus(
            new AutoFocusCallback(){
                @Override
                public void onAutoFocus(boolean success){

                }
            }
        );
    }

    public void takePicture(){
        cameraHelper.takePicture(
            new ShutterCallback(){
                @Override
                public void onShutter(){

                }
            }, new TakePictureCallback(){
                @Override
                public void onPictureTaken(byte[] data, int rotation){
                    callback.onPictureTaken();
                    save(data, rotation, new Bundle());
                }
            }
        );
    }

    public void takePreviewPicture(){
        cameraHelper.takePreviewPicture(
            new TakePictureCallback(){
                @Override
                public void onPictureTaken(byte[] data, int rotation){
                    callback.onPictureTaken();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(SaveIntentService.IS_PREVIEW, true);
                    CameraSize previewSize = cameraHelper.getPreviewSize();
                    bundle.putInt(SaveIntentService.WIDTH, previewSize.width);
                    bundle.putInt(SaveIntentService.HEIGHT, previewSize.height);
                    save(data, rotation, bundle);
                }
            }
        );
    }


    public void takePictures(int count){
        cameraHelper.takePictures(
            count, new TakePicturesCallback(){
                @Override
                public void onPictureTaken(int index, byte[] data, int rotation){
                    save(data, rotation, new Bundle());
                }

                @Override
                public void onPicturesTaken(){
                    callback.onPictureTaken();
                }
            }
        );
    }

    public void takePreviewPictures(int count){
        cameraHelper.takePreviewPictures(
            count, new TakePicturesCallback(){
                @Override
                public void onPictureTaken(int index, byte[] data, int rotation){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(SaveIntentService.IS_PREVIEW, true);
                    CameraSize previewSize = cameraHelper.getPreviewSize();
                    bundle.putInt(SaveIntentService.WIDTH, previewSize.width);
                    bundle.putInt(SaveIntentService.HEIGHT, previewSize.height);
                    save(data, rotation, bundle);
                }

                @Override
                public void onPicturesTaken(){
                    callback.onPictureTaken();
                }
            }
        );
    }

    public boolean isVideoSnapshotSupported(){
        return cameraHelper.getParam().isVideoSnapshotSupported();
    }


    public void toggleRecordingVideo(){
        if(cameraHelper.isRecording){
            cameraHelper.stopRecording();
        }else{
            if(cameraHelper.prepareRecording(getRecordingConfig(false))){
                cameraHelper.startRecording();
            }
        }
    }

    public void toggleRecordingTimeLapseVideo(){
        if(cameraHelper.isRecording){
            cameraHelper.stopRecording();
        }else{
            if(cameraHelper.prepareRecording(getRecordingConfig(true))){
                cameraHelper.startRecording();
            }
        }
    }

    private RecordingConfig getRecordingConfig(boolean isTimeLapse){
        File dir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        );
        String saveFilePath = dir.getAbsolutePath() + "/test";
        RecordingQuality recordingQuality = RecordingQuality.QUALITY_HIGH;
        int cameraId = getArguments().getInt(KEY_CAMERA_ID);
        List<int[]> fpsRange = cameraHelper.getParam().getSupportedPreviewFpsRange();
        double captureRate = fpsRange.get(0)[Parameters.PREVIEW_FPS_MIN_INDEX];
        RecordingConfig recordingConfig = new RecordingConfig.Builder().setCameraId(cameraId)
            .setSaveFilePath(saveFilePath)
            .setRecordingQuality(recordingQuality)
            .setTimeLapse(isTimeLapse)
            .setCaptureRate(captureRate / 1000)
            .build();
        int videoFrameWidth = recordingConfig.getCamcorderProfile().videoFrameWidth;
        int videoFrameHeight = recordingConfig.getCamcorderProfile().videoFrameHeight;
        LogUtils.i("videoFrameWidth: " + videoFrameWidth);
        LogUtils.i("videoFrameHeight: " + videoFrameHeight);
        return recordingConfig;
    }

    public void save(byte[] data, int rotation, Bundle bundle){
        File dir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        );
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file;
        do{
            String fileName = String.valueOf(System.currentTimeMillis()) + PictureFormat.JPEG.extension;
            file = new File(dir, fileName);
        }while(file.exists());
        int index = sByteArrayCache.size();
        LogUtils.i("data.length: " + data.length);
        sByteArrayCache.put(index, data);
        bundle.putInt(SaveIntentService.INDEX, index);
        bundle.putString(SaveIntentService.FILEPATH, file.getAbsolutePath());
        bundle.putInt(SaveIntentService.ROTATION, rotation);
        Intent intent = new Intent(getActivity(), SaveIntentService.class);
        intent.putExtra(SaveIntentService.class.getSimpleName(), bundle);
        intent.putExtra(
            SaveResultReceiver.class.getSimpleName(),
            new SaveResultReceiver(new Handler()).setOnSaveResultListener(
                new OnSaveResultListener(){
                    @Override
                    public void onReceiveResult(int resultCode, Bundle resultData){
                        if(resultCode == Activity.RESULT_OK){
                            LogUtils.i("Success");
                        }else{
                            LogUtils.i("Failed!");
                        }
                    }
                }
            )
        );
        getActivity().startService(intent);
    }

    public boolean isMultiCameraSupported(){
        return cameraHelper.getNumberOfCameras() > 1;
    }

    public void switchCamera(){
        boolean result = cameraHelper.switchCamera(holder);
        Toast.makeText(getActivity(), "switchCamera: " + result, Toast.LENGTH_LONG).show();
    }

    public List<CameraSize> getSupportedPictureSizes(){
        return cameraHelper.getParam().getSupportedPictureSizes();
    }

    public void changePictureSize(CameraSize pictureSize){
        cameraHelper.setPictureSize(pictureSize);
        CameraSizePolicy policy = (CameraSizePolicy)getArguments().getSerializable(
            KEY_CAMERA_SIZE_POLICY
        );
        cameraPreview.surfaceChanged(cameraHelper, policy);
    }

    public List<CameraSize> getSupportedVideoSizes(){
        return cameraHelper.getParam().getSupportedVideoSizes();
    }

    public void changeVideoSize(CameraSize videoSize){
        cameraHelper.setVideoSize(videoSize);
        CameraSizePolicy policy = (CameraSizePolicy)getArguments().getSerializable(
            KEY_CAMERA_SIZE_POLICY
        );
        cameraPreview.surfaceChanged(cameraHelper, policy);
    }
}
