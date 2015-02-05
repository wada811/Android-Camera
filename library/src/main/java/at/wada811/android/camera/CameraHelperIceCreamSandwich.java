package at.wada811.android.camera;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Build.VERSION_CODES;

@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public class CameraHelperIceCreamSandwich extends CameraHelperHoneycomb{

    public CameraHelperIceCreamSandwich(Context context, int cameraId, int displayRotationDegrees){
        super(context, cameraId, displayRotationDegrees);
    }

    @Override
    public boolean isCameraDisabled(Context context){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)context.getSystemService(
            Context.DEVICE_POLICY_SERVICE
        );
        return devicePolicyManager.getCameraDisabled(null);
    }
}
