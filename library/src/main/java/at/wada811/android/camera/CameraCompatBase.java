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
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;
import android.view.SurfaceHolder;
import java.io.IOException;

@TargetApi(Build.VERSION_CODES.BASE)
class CameraCompatBase extends CameraCompat{

    @Override
    public Camera open(){
        camera = Camera.open();
        return camera;
    }

    @Override
    public void release(){
        camera.release();
    }

    @Override
    public void setErrorCallback(ErrorCallback cb){
        camera.setErrorCallback(cb);
    }

    @Override
    public void setPreviewCallback(PreviewCallback cb){
        camera.setPreviewCallback(cb);
    }

    @Override
    public void setPreviewDisplay(SurfaceHolder holder) throws IOException{
        camera.setPreviewDisplay(holder);
    }

    @Override
    public void setParameters(CameraParamCompat params){
        try{
            camera.setParameters(params.getParam());
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    @Override
    public CameraParamCompat getParameters(){
        return CameraParamCompat.newInstance(camera.getParameters());
    }

    @Override
    public void startPreview(){
        camera.startPreview();
    }

    @Override
    public void stopPreview(){
        camera.stopPreview();
    }

    @Override
    public void autoFocus(AutoFocusCallback cb){
        camera.autoFocus(cb);
    }

    @Override
    public void takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback jpeg){
        camera.takePicture(shutter, raw, jpeg);
    }
}
