package at.wada811.android.camera.sample;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import at.wada811.android.camera.LogUtils;
import at.wada811.android.camera.PictureFormat;
import static at.wada811.android.camera.sample.CameraPreviewFragment.sByteArrayCache;

public class SaveIntentService extends IntentService{

    public static final String INDEX= "INDEX";
    public static final String FILEPATH = "FILEPATH";
    public static final String ROTATION = "ROTATION";
    public static final String IS_PREVIEW = "IS_PREVIEW";
    public static final String WIDTH = "WIDTH";
    public static final String HEIGHT = "HEIGHT";

    public SaveIntentService(String name){
        super(name);
    }
    public SaveIntentService(){
        this(SaveIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent){
        ResultReceiver receiver = intent.getParcelableExtra(SaveResultReceiver.class.getSimpleName());
        Bundle bundle = intent.getBundleExtra(SaveIntentService.class.getSimpleName());
        int index= bundle.getInt(INDEX);
        String filePath = bundle.getString(FILEPATH);
        int rotation = bundle.getInt(ROTATION);
        boolean isPreview = bundle.getBoolean(IS_PREVIEW);
        int width = bundle.getInt(WIDTH);
        int height = bundle.getInt(HEIGHT);

        boolean result = process(index, filePath, rotation, isPreview, width, height);
        if(result){
            Bundle resultData = new Bundle();
            receiver.send(Activity.RESULT_OK, resultData);
        }else{
            receiver.send(Activity.RESULT_CANCELED, null);
        }
    }

    public boolean process(int index, String filePath, int rotation, boolean isPreview, int width, int height){
        boolean result = false;
        // get data
        byte[] data = sByteArrayCache.get(index);
        // create bitmap from byte array
        if(isPreview){
            data = decodeYuvData(data, width, height);
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitmap = rotate(bitmap, rotation);
        // save bitmap
        if(saveBitmap(this, bitmap, filePath)){
            result = true;
        }
        return result;
    }


    public byte[] decodeYuvData(byte[] yuvData, int width, int height){
        YuvImage yuvImage = new YuvImage(yuvData, ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int quality = 100; // 一旦JPEGデータを経由するので劣化軽減のために100にする(※100でも少しは劣化する)
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), quality, stream);
        byte[] rgbData = stream.toByteArray();
        try{
            stream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return rgbData;
    }


    public Bitmap rotate(Bitmap bitmap, float degrees){
        if(degrees == 0.0f){
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees); // 回転角を設定する
        Bitmap rotateBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true
        );
        bitmap.recycle();
        return rotateBitmap;
    }

    public boolean saveBitmap(Context context, Bitmap bitmap, String filePath){
        File file = new File(filePath);
        String extension = filePath.substring(filePath.lastIndexOf("."));
        CompressFormat compressFormat = PictureFormat.getCompressFormat(extension);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            bitmap.compress(compressFormat, 100, fos);
        }catch(FileNotFoundException e){
            e.printStackTrace();
            LogUtils.e(filePath, e);
            return false;
        }catch(Exception e){
            LogUtils.e(filePath, e);
            return false;
        }finally{
            if(fos != null){
                try{
                    fos.close();
                }catch(IOException e){
                }
            }
            bitmap.recycle();
            // bitmap = null;
        }
        context.sendBroadcast(
            new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)
            )
        );
        return true;
    }


    public static class SaveResultReceiver extends ResultReceiver{

        private OnSaveResultListener listener;

        public interface OnSaveResultListener{

            public void onReceiveResult(int resultCode, Bundle resultData);
        }

        public SaveResultReceiver(Handler handler){
            super(handler);
        }

        public SaveResultReceiver setOnSaveResultListener(OnSaveResultListener listener){
            this.listener = listener;
            return this;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData){
            if(listener != null){
                listener.onReceiveResult(resultCode, resultData);
            }
        }
    }
}
