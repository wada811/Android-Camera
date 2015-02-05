/*
 * Copyright 2014 wada811<at.wada811@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.wada811.android.camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.view.SurfaceHolder;
import java.io.IOException;
import java.util.List;
import at.wada811.android.camera.callback.AutoFocusCallback;
import at.wada811.android.camera.callback.ErrorCallback;
import at.wada811.android.camera.callback.TakePictureCallback;
import at.wada811.android.camera.callback.TakePicturesCallback;

class CameraHelperBase extends CameraHelper{

    public CameraHelperBase(Context context, int cameraId, int displayRotationDegrees){
        super(context, cameraId, displayRotationDegrees);
    }

    @Override
    public boolean openCamera(SurfaceHolder holder){
        preOpenCamera(holder);
        try{
            openCamera();
        }catch(RuntimeException e){
            e.printStackTrace();
        }
        if(isCameraOpen){
            postOpenCamera(holder);
        }
        return isCameraOpen;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void preOpenCamera(SurfaceHolder holder){
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void openCamera() throws RuntimeException{
        cameraManager.open(cameraId);
        isCameraOpen = true;
    }

    @Override
    public void postOpenCamera(SurfaceHolder holder){
        try{
            cameraManager.camera.setPreviewDisplay(holder);
        }catch(IOException e){
            e.printStackTrace();
        }
        cameraManager.camera.setDisplayOrientation(previewRotationDegrees);
    }

    @Override
    public void closeCamera(){
        if(cameraManager.camera != null || isCameraOpen){
            cameraManager.cancelAutoFocus();
            cameraManager.setPreviewCallback(null);
            cameraManager.stopPreview();
            cameraManager.release();
            cameraManager.camera = null;
            isCameraOpen = false;
        }
    }

    @Override
    public boolean switchCamera(SurfaceHolder holder){
        closeCamera();
        cameraId = ++cameraId % numberOfCameras;
        isCameraOpen = openCamera(holder);
        if(isCameraOpen){
            cameraManager.startPreview();
        }
        return isCameraOpen;
    }

    @Override
    public void setErrorCallback(final ErrorCallback errorCallback){
        this.errorCallback = errorCallback;
        cameraManager.setErrorCallback(
            new Camera.ErrorCallback(){
                @Override
                public void onError(int error, Camera camera){
                    errorCallback.onError(error, null);
                }
            }
        );
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder, int width, int height){
        postOpenCamera(holder);
        displaySize = new CameraSize(width, height);
    }

    @Override
    public void setParam(CameraParamCompat param) throws RuntimeException{
        try{
            cameraManager.setParameters(param);
        }catch(RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public CameraParamCompat getParam() throws RuntimeException{
        try{
            return cameraManager.getParameters();
        }catch(RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public CameraSize getPictureSize(){
        return getParam().getPictureSize();
    }

    @Override
    public CameraSize getPreviewSize(){
        return getParam().getPreviewSize();
    }

    @Override
    public void setPictureSize(CameraSize pictureSize){
        this.pictureSize  = pictureSize;
        cameraManager.stopPreview();
        CameraParamCompat params = getParam();
        params.setPictureSize(pictureSize.width, pictureSize.height);
        setParam(params);
        previewSize = getOptimalPreviewSize(pictureSize);
        params.setPreviewSize(previewSize.width, previewSize.height);
        setParam(params);
        cameraManager.startPreview();
    }

    @Override
    public void setVideoSize(CameraSize videoSize){
        this.videoSize = videoSize;
        cameraManager.stopPreview();
        CameraParamCompat params = getParam();
        CameraSize preferredPreviewSizeForVideo = getParam().getPreferredPreviewSizeForVideo();
        LogUtils.v("preferredPreviewSizeForVideo: " + preferredPreviewSizeForVideo);
        if(preferredPreviewSizeForVideo == null){
            previewSize = getOptimalPreviewSize(videoSize);
        }else{
            previewSize = preferredPreviewSizeForVideo;
        }
        params.setPreviewSize(previewSize.width, previewSize.height);
        setParam(params);
        cameraManager.startPreview();
    }

    @Override
    protected CameraSize getOptimalPreviewSize(CameraSize targetSize){
        final List<CameraSize> sizes = getParam().getSupportedPreviewSizes();
        final double ASPECT_TOLERANCE = 0.07;
        final double targetRatio = (double)targetSize.width / targetSize.height;
        CameraSize optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        LogUtils.i("isPortrait: " + isPortrait);
        LogUtils.i("targetSize.width: " + targetSize.width);
        LogUtils.i("targetSize.height: " + targetSize.height);
        LogUtils.i("targetRatio: " + targetRatio);

        for(CameraSize size : sizes){
            double previewRatio = (double)size.width / size.height;
            LogUtils.i("size.width: " + size.width);
            LogUtils.i("size.height: " + size.height);
            LogUtils.i("previewRatio: " + previewRatio);
            if(Math.abs(previewRatio - targetRatio) > ASPECT_TOLERANCE){
                continue;
            }
            if(Math.abs(size.height - targetSize.height) < minDiff){
                optimalSize = size;
                minDiff = Math.abs(size.height - targetSize.height);
            }
        }
        if(optimalSize == null){
            minDiff = Double.MAX_VALUE;
            for(CameraSize size : sizes){
                if(Math.abs(size.height - targetSize.height) < minDiff){
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetSize.height);
                }
            }
        }
        LogUtils.i("optimalSize.width: " + optimalSize.width);
        LogUtils.i("optimalSize.height: " + optimalSize.height);
        return optimalSize;
    }


    @Override
    public CameraSize getSurfaceSize(CameraSizePolicy policy){
        CameraSize previewSize = getParam().getPreviewSize();
        int previewWidth = isPortrait ? previewSize.height : previewSize.width;
        int previewHeight = isPortrait ? previewSize.width : previewSize.height;
        int scaledPreviewWidth = isPortrait ? displaySize.height : displaySize.width;
        int scaledPreviewHeight = isPortrait ? displaySize.width : displaySize.height;
        double displayRatio = displaySize.height > displaySize.width ? (double)displaySize.height / displaySize.width : (double)displaySize.width / displaySize.height;
        double previewRatio = (double)previewSize.width / previewSize.height;
        switch(policy){
            case FILL:
                if(displayRatio <= previewRatio){
                    if(isPortrait){
                        scaledPreviewWidth = displaySize.width;
                        scaledPreviewHeight = Math.round(scaledPreviewWidth * previewHeight / (float)previewWidth);
                    }else{
                        scaledPreviewHeight = displaySize.height;
                        scaledPreviewWidth = Math.round(scaledPreviewHeight * previewWidth / (float)previewHeight);
                    }
                }else{
                    if(isPortrait){
                        scaledPreviewHeight = displaySize.height;
                        scaledPreviewWidth = Math.round(scaledPreviewHeight * previewWidth / (float)previewHeight);
                    }else{
                        scaledPreviewWidth = displaySize.width;
                        scaledPreviewHeight = Math.round(scaledPreviewWidth * previewHeight / (float)previewWidth);
                    }
                }
                break;
            case INSIDE:
                if(displayRatio <= previewRatio){
                    if(isPortrait){
                        scaledPreviewHeight = displaySize.height;
                        scaledPreviewWidth = Math.round(scaledPreviewHeight * previewWidth / (float)previewHeight);
                    }else{
                        scaledPreviewWidth = displaySize.width;
                        scaledPreviewHeight = Math.round(scaledPreviewWidth * previewHeight / (float)previewWidth);
                    }
                }else{
                    if(isPortrait){
                        scaledPreviewWidth = displaySize.width;
                        scaledPreviewHeight = Math.round(scaledPreviewWidth * previewHeight / (float)previewWidth);
                    }else{
                        scaledPreviewHeight = displaySize.height;
                        scaledPreviewWidth = Math.round(scaledPreviewHeight * previewWidth / (float)previewHeight);
                    }
                }
                break;
            case SQUARE:
                scaledPreviewHeight = scaledPreviewWidth = Math.min(
                    displaySize.width,
                    displaySize.height
                );
                break;
        }
        LogUtils.i("policy: " + policy);
        LogUtils.i("isPortrait: " + isPortrait);
        LogUtils.i("previewWidth: " + previewWidth);
        LogUtils.i("previewHeight: " + previewHeight);
        LogUtils.i("displayWidth: " + displaySize.width);
        LogUtils.i("displayHeight: " + displaySize.height);
        LogUtils.i("displayRatio: " + displayRatio);
        LogUtils.i("previewRatio: " + previewRatio);
        LogUtils.i("scaledPreviewWidth: " + scaledPreviewWidth);
        LogUtils.i("scaledPreviewHeight: " + scaledPreviewHeight);
        return new CameraSize(scaledPreviewWidth, scaledPreviewHeight);
    }

    @Override
    public void autoFocus(final AutoFocusCallback autoFocusCallback){
        if(cameraManager.camera != null){
            cameraManager.cancelAutoFocus();
            try{
                cameraManager.autoFocus(
                    new Camera.AutoFocusCallback(){
                        @Override
                        public void onAutoFocus(boolean success, Camera camera){
                            if(autoFocusCallback != null){
                                autoFocusCallback.onAutoFocus(success);
                            }
                        }
                    }
                );
            }catch(RuntimeException e){
                e.printStackTrace();
                errorCallback.onError(ErrorCallback.ERROR_AUTO_FOCUS_FAILED, e);
            }
        }
    }

    @Override
    public void takePicture(ShutterCallback shutterCallback, final TakePictureCallback takePictureCallback){
        try{
            cameraManager.takePicture(
                shutterCallback, new PictureCallback(){
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera){
                        // no-op
                    }
                }, new PictureCallback(){
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera){
                        camera.startPreview();
                        takePictureCallback.onPictureTaken(data, pictureRotationDegrees);
                    }
                }
            );
        }catch(RuntimeException e){
            errorCallback.onError(ErrorCallback.ERROR_TAKE_PICTURE, e);
        }
    }

    @Override
    public void takePreviewPicture(final TakePictureCallback takePictureCallback){
        cameraManager.setOneShotPreviewCallback(null);
        cameraManager.setOneShotPreviewCallback(
            new PreviewCallback(){
                @Override
                public void onPreviewFrame(byte[] data, Camera camera){
                    autoFocus(null);
                    takePictureCallback.onPictureTaken(data, pictureRotationDegrees);
                }
            }
        );
    }

    @Override
    public void takePictures(final int count, final TakePicturesCallback takePicturesCallback){
        takePictures(count, 0, takePicturesCallback);
    }

    private void takePictures(final int count, final int index, final TakePicturesCallback takePicturesCallback){
        takePicture(
            new ShutterCallback(){
                @Override
                public void onShutter(){
                    autoFocus(null);
                }
            }, new TakePictureCallback(){
                @Override
                public void onPictureTaken(byte[] data, int rotation){
                    takePicturesCallback.onPictureTaken(index, data, rotation);
                    if(count > index + 1){
                        takePictures(count, index + 1, takePicturesCallback);
                    }else{
                        takePicturesCallback.onPicturesTaken();
                    }
                }
            }
        );
    }

    @Override
    public void takePreviewPictures(final int count, final TakePicturesCallback takePicturesCallback){
        cameraManager.setPreviewCallback(
            new PreviewCallback(){
                int index = 0;

                @Override
                public void onPreviewFrame(byte[] data, Camera camera){
                    if(index < count - 1){
                        takePicturesCallback.onPictureTaken(index, data, pictureRotationDegrees);
                        index++;
                    }else{
                        takePicturesCallback.onPicturesTaken();
                        cameraManager.setPreviewCallback(null);
                    }
                }
            }
        );
    }

}
