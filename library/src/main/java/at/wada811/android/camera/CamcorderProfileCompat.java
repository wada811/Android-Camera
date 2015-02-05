package at.wada811.android.camera;

import android.media.CamcorderProfile;
import android.os.Build;
import android.os.Build.VERSION_CODES;

class CamcorderProfileCompat{

    static CamcorderProfile get(int cameraId, int quality){
        if(Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB){
            return CamcorderProfileCompatHoneycomb.get(cameraId, quality);
        }else{
            return CamcorderProfileCompatBase.get(cameraId, quality);
        }
    }

    static boolean has(int cameraId, int quality){
        if(Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB){
            return CamcorderProfileCompatHoneycomb.has(cameraId, quality);
        }else{
            return CamcorderProfileCompatBase.has(cameraId, quality);
        }
    }


}
