package at.wada811.android.camera;

import android.annotation.TargetApi;
import android.media.CamcorderProfile;
import android.os.Build.VERSION_CODES;

@TargetApi(VERSION_CODES.HONEYCOMB)
class CamcorderProfileCompatHoneycomb extends CamcorderProfileCompatBase{

    static boolean has(int cameraId, int quality){
        return CamcorderProfile.hasProfile(cameraId, quality);
    }

}
