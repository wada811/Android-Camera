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

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.VideoSource;
import android.os.Build;
import android.view.SurfaceHolder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import at.wada811.android.camera.callback.ErrorCallback;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class CameraHelperHoneycomb extends CameraHelperGingerbread{

    public CameraHelperHoneycomb(Context context, int cameraId, int displayRotationDegrees){
        super(context, cameraId, displayRotationDegrees);
    }

    public void preOpenCamera(SurfaceHolder holder){
        // Do not call super
    }

    @Override
    public boolean prepareRecording(RecordingConfig recordingConfig){
        if(cameraManager.camera != null){
            CameraParamCompat params = getParam();
            List<String> supportedFocusModes = params.getSupportedFocusModes();
            if(supportedFocusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)){
                params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                setParam(params);
            }
            params = getParam();
            params.setRecordingHint(true);
            setParam(params);
        }
        mediaRecorder = new MediaRecorder();
        cameraManager.camera.unlock();
        mediaRecorder.setCamera(cameraManager.camera);
        if(recordingConfig.isTimeLapse()){
            // Do not set AudioSource on TimeLapse
        }else{
            mediaRecorder.setAudioSource(AudioSource.CAMCORDER);
        }
        mediaRecorder.setVideoSource(VideoSource.CAMERA);
        CamcorderProfile camcorderProfile = recordingConfig.getCamcorderProfile();
        mediaRecorder.setProfile(camcorderProfile);
        if(recordingConfig.isTimeLapse()){
            mediaRecorder.setCaptureRate(recordingConfig.getCaptureRate());
        }
        mediaRecorder.setOutputFile(recordingConfig.getSaveFilePath());
        mediaRecorder.setOrientationHint(videoRotationDegrees);
        try{
            mediaRecorder.prepare();
        }catch(IllegalStateException e){
            e.printStackTrace();
            releaseMediaPlayer();
            errorCallback.onError(ErrorCallback.ERROR_RECORDING_ILLEGAL_STATE, e);
            return false;
        }catch(IOException e){
            e.printStackTrace();
            releaseMediaPlayer();
            errorCallback.onError(ErrorCallback.ERROR_RECORDING_PREPARE_FAILED, e);
            return false;
        }
        return true;
    }

    @Override
    public void startRecording(){
        try{
            mediaRecorder.start();
            isRecording = true;
        }catch(IllegalStateException e){
            e.printStackTrace();
            releaseMediaPlayer();
            errorCallback.onError(ErrorCallback.ERROR_RECORDING_ILLEGAL_STATE, e);
        }catch(RuntimeException e){
            e.printStackTrace();
            releaseMediaPlayer();
            errorCallback.onError(ErrorCallback.ERROR_RECORDING_START_FAILED, e);
        }
    }

    @Override
    public void stopRecording(){
        if(mediaRecorder != null){
            try{
                mediaRecorder.stop();
            }catch(IllegalStateException e){
                // Called before start()
                e.printStackTrace();
                new File(recordingConfig.getSaveFilePath()).delete();
                errorCallback.onError(ErrorCallback.ERROR_RECORDING_ILLEGAL_STATE, e);
            }catch(RuntimeException e){
                // Called immediately after start()
                e.printStackTrace();
                new File(recordingConfig.getSaveFilePath()).delete();
                errorCallback.onError(ErrorCallback.ERROR_RECORDING_STOP_FAILED, e);
            }finally{
                releaseMediaPlayer();
            }
        }
        isRecording = false;
    }

    @Override
    protected void releaseMediaPlayer(){
        if(mediaRecorder != null){
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if(cameraManager.camera != null){
            try{
                cameraManager.reconnect();
            }catch(IOException e){
                e.printStackTrace();
            }
            try{
                CameraParamCompat params = getParam();
                params.setRecordingHint(false);
                setParam(params);
            }catch(RuntimeException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeCamera(){
        if(isRecording){
            stopRecording();
        }
        super.closeCamera();
    }
}
