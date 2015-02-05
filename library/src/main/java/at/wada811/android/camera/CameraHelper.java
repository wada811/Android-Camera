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
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ShutterCallback;
import android.media.MediaRecorder;
import android.os.Build;
import android.view.SurfaceHolder;
import at.wada811.android.camera.callback.AutoFocusCallback;
import at.wada811.android.camera.callback.ErrorCallback;
import at.wada811.android.camera.callback.TakePictureCallback;
import at.wada811.android.camera.callback.TakePicturesCallback;

public abstract class CameraHelper{

    Context context;
    int cameraId;
    int displayRotationDegrees;
    CameraCompat cameraManager;
    CameraInfo cameraInfo;
    boolean isPortrait;
    boolean isFacingBack;
    int orientation;
    boolean canDisableShutterSound;
    int numberOfCameras;
    int previewRotationDegrees;
    int pictureRotationDegrees;
    int videoRotationDegrees;

    boolean isCameraOpen;
    public ErrorCallback errorCallback;
    public CameraSize displaySize;
    public CameraSize pictureSize;
    public CameraSize videoSize;
    public CameraSize previewSize;

    public boolean isRecording;
    MediaRecorder mediaRecorder;
    RecordingConfig recordingConfig;

    public static CameraHelper newInstance(Context context, int cameraId, int displayRotationDegrees){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            return new CameraHelperJellyBeanMR1(context, cameraId, displayRotationDegrees);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            return new CameraHelperHoneycomb(context, cameraId, displayRotationDegrees);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return new CameraHelperGingerbread(context, cameraId, displayRotationDegrees);
        }else{
            return new CameraHelperBase(context, cameraId, displayRotationDegrees);
        }
    }

    public CameraHelper(Context context, int cameraId, int displayRotationDegrees){
        boolean hasFeatureCamera = CameraHelper.hasFeatureCamera(context);
        if(!hasFeatureCamera){
            throw new RuntimeException("This device has not support camera.");
        }
        if(isCameraDisabled(context)){
            throw new RuntimeException("This device has disabled camera.");
        }
        this.context = context;
        this.cameraId = cameraId;
        this.displayRotationDegrees = displayRotationDegrees;
        cameraManager = CameraCompat.newInstance();
        cameraInfo = getCameraInfo(cameraId);
        isPortrait = context.getResources()
            .getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        isFacingBack = cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK;
        orientation = cameraInfo.orientation;
        canDisableShutterSound = canDisableShutterSound(cameraInfo);
        numberOfCameras = cameraManager.getNumberOfCameras();
        previewRotationDegrees = getPreviewRotation(displayRotationDegrees);
        pictureRotationDegrees = getPictureRotation(previewRotationDegrees);
        videoRotationDegrees = getVideoRotation(previewRotationDegrees);
        LogUtils.v("cameraId: " + cameraId);
        LogUtils.v("displayRotationDegrees: " + displayRotationDegrees);
        LogUtils.v("isPortrait: " + isPortrait);
        LogUtils.v("isFacingBack: " + isFacingBack);
        LogUtils.v("orientation: " + orientation);
        LogUtils.v("canDisableShutterSound: " + canDisableShutterSound);
        LogUtils.v("numberOfCameras: " + numberOfCameras);
        LogUtils.v("previewRotationDegrees: " + previewRotationDegrees);
        LogUtils.v("pictureRotationDegrees: " + pictureRotationDegrees);

    }

    /**
     * Check whether or not have camera feature.
     *
     * @param context
     *
     * @return hasCamera
     */
    public static boolean hasFeatureCamera(Context context){
        boolean hasCamera = context.getPackageManager()
            .hasSystemFeature(PackageManager.FEATURE_CAMERA);
        return hasCamera;
    }

    /**
     * Check whether or not have auto-focus feature.
     *
     * @param context
     *
     * @return hasAutoFocus
     */
    public static boolean hasFeatureAutoFocus(Context context){
        boolean hasAutoFocus = context.getPackageManager()
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS);
        return hasAutoFocus;
    }

    public boolean isCameraDisabled(Context context){
        return false;
    }

    public CameraInfo getCameraInfo(int cameraId){
        CameraInfo info = new CameraInfo();
        cameraManager.getCameraInfo(cameraId, info);
        return info;
    }

    public boolean canDisableShutterSound(CameraInfo cameraInfo){
        return false;
    }

    public int getNumberOfCameras(){
        return cameraManager.getNumberOfCameras();
    }

    public int getPreviewRotation(int displayRotationDegrees){
        return displayRotationDegrees;
    }

    public int getPictureRotation(int previewRotationDegrees){
        return previewRotationDegrees;
    }

    public int getVideoRotation(int videoRotationDegrees){
        return previewRotationDegrees;
    }

    public boolean openCamera(SurfaceHolder holder){
        return false;
    }

    protected void preOpenCamera(SurfaceHolder holder){

    }

    protected void openCamera(){
    }

    protected void postOpenCamera(SurfaceHolder holder){
    }

    public void closeCamera(){
    }

    public boolean switchCamera(SurfaceHolder holder){
        return false;
    }

    public void setErrorCallback(ErrorCallback errorCallback){
    }

    public void surfaceCreated(SurfaceHolder holder, int width, int height){

    }

    public void setParam(CameraParamCompat param) throws RuntimeException{

    }

    public CameraParamCompat getParam() throws RuntimeException{
        return null;
    }

    public CameraSize getPictureSize(){
        return null;
    }

    public CameraSize getPreviewSize(){
        return null;
    }

    public void setPictureSize(CameraSize pictureSize){

    }

    public void setVideoSize(CameraSize videoSize){

    }

    protected CameraSize getOptimalPreviewSize(CameraSize targetSize){
        return null;
    }

    public CameraSize getSurfaceSize(CameraSizePolicy policy){
        return null;
    }

    public void autoFocus(AutoFocusCallback autoFocusCallback){

    }

    public void takePicture(ShutterCallback shutterCallback, TakePictureCallback pictureCallback){

    }

    public void takePreviewPicture(TakePictureCallback takePictureCallback){

    }

    public void takePictures(final int count, final TakePicturesCallback takePicturesCallback){

    }

    public void takePreviewPictures(int count, TakePicturesCallback takePicturesCallback){

    }

    public boolean prepareRecording(RecordingConfig recordingConfig){
        return false;
    }

    public void startRecording(){

    }

    public void stopRecording(){

    }

    protected void releaseMediaPlayer(){

    }
}
