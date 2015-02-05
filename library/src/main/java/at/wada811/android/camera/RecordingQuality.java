package at.wada811.android.camera;

import android.media.CamcorderProfile;

public enum RecordingQuality{

    QUALITY_1080P("1080P"),
    QUALITY_720P("720P"),
    QUALITY_480P("480P"),
    QUALITY_CIF("CIF"),
    QUALITY_QVGA("QVGA"),
    QUALITY_QCIF("QCIF"),
    QUALITY_HIGH("HIGH"),
    QUALITY_LOW("LOW");

    String quality;
    String profileName;

    RecordingQuality(String quality){
        this.quality = quality;
    }

    public int getQuality(boolean isTimeLapse){
        if(isTimeLapse){
            profileName = "QUALITY_TIME_LAPSE_" + quality;
            if(profileName.equals("QUALITY_TIME_LAPSE_1080P")){
                return CamcorderProfile.QUALITY_TIME_LAPSE_1080P;
            }else if(profileName.equals("QUALITY_TIME_LAPSE_720P")){
                return CamcorderProfile.QUALITY_TIME_LAPSE_720P;
            }else if(profileName.equals("QUALITY_TIME_LAPSE_480P")){
                return CamcorderProfile.QUALITY_TIME_LAPSE_480P;
            }else if(profileName.equals("QUALITY_TIME_LAPSE_CIF")){
                return CamcorderProfile.QUALITY_TIME_LAPSE_CIF;
            }else if(profileName.equals("QUALITY_TIME_LAPSE_QVGA")){
                return CamcorderProfile.QUALITY_TIME_LAPSE_QVGA;
            }else if(profileName.equals("QUALITY_TIME_LAPSE_QCIF")){
                return CamcorderProfile.QUALITY_TIME_LAPSE_QCIF;
            }else if(profileName.equals("QUALITY_TIME_LAPSE_HIGH")){
                return CamcorderProfile.QUALITY_TIME_LAPSE_HIGH;
            }else if(profileName.equals("QUALITY_TIME_LAPSE_LOW")){
                return CamcorderProfile.QUALITY_TIME_LAPSE_LOW;
            }else{
                return CamcorderProfile.QUALITY_TIME_LAPSE_LOW;
            }
        }else{
            profileName = "QUALITY_" + quality;
            if(profileName.equals("QUALITY_1080P")){
                return CamcorderProfile.QUALITY_1080P;
            }else if(profileName.equals("QUALITY_720P")){
                return CamcorderProfile.QUALITY_720P;
            }else if(profileName.equals("QUALITY_480P")){
                return CamcorderProfile.QUALITY_480P;
            }else if(profileName.equals("QUALITY_CIF")){
                return CamcorderProfile.QUALITY_CIF;
            }else if(profileName.equals("QUALITY_QVGA")){
                return CamcorderProfile.QUALITY_QVGA;
            }else if(profileName.equals("QUALITY_QCIF")){
                return CamcorderProfile.QUALITY_QCIF;
            }else if(profileName.equals("QUALITY_HIGH")){
                return CamcorderProfile.QUALITY_HIGH;
            }else if(profileName.equals("QUALITY_LOW")){
                return CamcorderProfile.QUALITY_LOW;
            }else{
                return CamcorderProfile.QUALITY_LOW;
            }
        }
    }

}
