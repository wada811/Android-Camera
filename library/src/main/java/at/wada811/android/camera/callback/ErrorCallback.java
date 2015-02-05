package at.wada811.android.camera.callback;

import android.hardware.Camera;

public interface ErrorCallback{

    public static final int ERROR_SERVER_DIED = Camera.CAMERA_ERROR_SERVER_DIED; // 100
    public static final int ERROR_AUTO_FOCUS_FAILED = 120;
    public static final int ERROR_TAKE_PICTURE = 130;
    public static final int ERROR_RECORDING_ILLEGAL_STATE = 140;
    public static final int ERROR_RECORDING_PREPARE_FAILED = 141;
    public static final int ERROR_RECORDING_START_FAILED = 142;
    public static final int ERROR_RECORDING_STOP_FAILED = 143;
    public static final int ERROR_UNKNOWN = Camera.CAMERA_ERROR_UNKNOWN; // 1

    public void onError(int error, Exception e);
}
