package at.wada811.android.camera;

import android.media.CamcorderProfile;

class CamcorderProfileCompatBase{

    static CamcorderProfile get(int cameraId, int quality){
        return CamcorderProfile.get(cameraId, quality);
    }

    static boolean has(int cameraId, int quality){
        return CamcorderProfile.get(cameraId, quality) != null;
    }

}
