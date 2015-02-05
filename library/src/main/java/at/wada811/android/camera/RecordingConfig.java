package at.wada811.android.camera;

import android.media.CamcorderProfile;
import java.io.File;

public class RecordingConfig{

    private String saveFilePath;
    private int cameraId;
    private CamcorderProfile camcorderProfile;
    private boolean isTimeLapse;
    private double captureRate;

    public static class Builder{

        private String saveFilePath;
        private int cameraId;
        private RecordingQuality recordingQuality = RecordingQuality.QUALITY_LOW;
        private boolean isTimeLapse;
        private double captureRate = -1;

        public Builder setSaveFilePath(String saveFilePath){
            this.saveFilePath = saveFilePath;
            return this;
        }

        public Builder setCameraId(int cameraId){
            this.cameraId = cameraId;
            return this;
        }

        public Builder setRecordingQuality(RecordingQuality recordingQuality){
            this.recordingQuality = recordingQuality;
            return this;
        }

        public Builder setTimeLapse(boolean isTimeLapse){
            this.isTimeLapse = isTimeLapse;
            return this;
        }

        public Builder setCaptureRate(double captureRate){
            this.captureRate = captureRate;
            return this;
        }

        public RecordingConfig build(){
            if(isTimeLapse && captureRate == -1){
                throw new IllegalArgumentException("CaptureRate is unspecified.");
            }
            int quality = recordingQuality.getQuality(isTimeLapse);
            if(CamcorderProfileCompat.has(cameraId, quality)){
                CamcorderProfile camcorderProfile = CamcorderProfileCompat.get(cameraId, quality);
                this.saveFilePath += RecordingFormat.getExtention(camcorderProfile.fileFormat);
                File dir = new File(saveFilePath).getParentFile();
                if(!dir.exists()){
                    dir.mkdirs();
                }
                return new RecordingConfig(
                    saveFilePath, cameraId, camcorderProfile, isTimeLapse, captureRate
                );
            }else{
                return null;
            }
        }
    }

    private RecordingConfig(String saveFilePath, int cameraId, CamcorderProfile camcorderProfile, boolean isTimeLapse, double captureRate){
        this.saveFilePath = saveFilePath;
        this.cameraId = cameraId;
        this.camcorderProfile = camcorderProfile;
        this.isTimeLapse = isTimeLapse;
        this.captureRate = captureRate;
    }

    public void setSaveFilePath(String saveFilePath){
        this.saveFilePath = saveFilePath;
    }

    public String getSaveFilePath(){
        return saveFilePath;
    }

    public void setCamcorderProfile(CamcorderProfile camcorderProfile){
        this.camcorderProfile = camcorderProfile;
    }

    public CamcorderProfile getCamcorderProfile(){
        return camcorderProfile;
    }

    public void setTimeLapse(boolean isTimeLapse){
        this.isTimeLapse = isTimeLapse;
    }

    public boolean isTimeLapse(){
        return isTimeLapse;
    }

    public void setCaptureRate(double captureRate){
        this.captureRate = captureRate;
    }

    public double getCaptureRate(){
        return captureRate;
    }

}
