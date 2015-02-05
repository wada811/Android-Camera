package at.wada811.android.camera;

import android.media.MediaRecorder.OutputFormat;

public enum RecordingFormat{

    /**
     * {@link OutputFormat#THREE_GPP}
     */
    FORMAT_3GPP(OutputFormat.THREE_GPP, ".3gp"),
    /**
     * {@link OutputFormat#MPEG_4}
     */
    FORMAT_MP4(OutputFormat.MPEG_4, ".mp4");

    public int outputFormat;
    public String extension;

    RecordingFormat(int outputFormat, String extension){
        this.outputFormat = outputFormat;
        this.extension = extension;
    }

    public static String getExtention(int format){
        if(format == FORMAT_3GPP.outputFormat){
            return FORMAT_3GPP.extension;
        }else{
            return FORMAT_MP4.extension;
        }
    }

    public static int getFormat(String extention){
        if(FORMAT_3GPP.equals(extention)){
            return FORMAT_3GPP.outputFormat;
        }else{
            return FORMAT_MP4.outputFormat;
        }
    }
}
