package at.wada811.android.camera;

import android.annotation.TargetApi;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build.VERSION_CODES;

public enum PictureFormat{

    /**
     * {@link CompressFormat#JPEG}
     */
    JPEG(CompressFormat.JPEG, ".jpg"),
    /**
     * {@link CompressFormat#PNG}
     */
    PNG(CompressFormat.PNG, ".png"),
    /**
     * {@link CompressFormat#WEBP}
     */
    // WEBP(CompressFormat.WEBP, ".webp")
    //
    ;

    public CompressFormat compressFormat;
    public String extension;

    PictureFormat(CompressFormat compressFormat, String extension){
        this.compressFormat = compressFormat;
        this.extension = extension;
    }

    @TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
    public static CompressFormat getCompressFormat(String extension){
        if(extension.equals(JPEG.extension)){
            return JPEG.compressFormat;
        }else if(extension.equals(PNG.extension)){
            return PNG.compressFormat;
        }else{
            return CompressFormat.WEBP;
        }
    }

    @TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
    public String getExtention(){
        if(compressFormat == JPEG.compressFormat){
            return JPEG.extension;
        }else if(compressFormat == PNG.compressFormat){
            return PNG.extension;
        }else{
            return "." + CompressFormat.WEBP.name().toLowerCase();
        }
    }
}
