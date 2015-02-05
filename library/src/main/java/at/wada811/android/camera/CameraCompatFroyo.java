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
import android.hardware.Camera.OnZoomChangeListener;
import android.hardware.Camera.PreviewCallback;
import android.os.Build;
import java.io.IOException;

@TargetApi(Build.VERSION_CODES.FROYO)
class CameraCompatFroyo extends CameraCompatEclair{

    @Override
    public void setDisplayOrientation(int degrees){
        camera.setDisplayOrientation(degrees);
    }

    @Override
    public void addCallbackBuffer(byte[] callbackBuffer){
        camera.addCallbackBuffer(callbackBuffer);
    }

    @Override
    public void setPreviewCallbackWithBuffer(PreviewCallback cb){
        camera.setPreviewCallbackWithBuffer(cb);
    }

    @Override
    public void setZoomChangeListener(OnZoomChangeListener listener){
        camera.setZoomChangeListener(listener);
    }

    @Override
    public void startSmoothZoom(int value){
        camera.startSmoothZoom(value);
    }

    @Override
    public void stopSmoothZoom(){
        camera.stopSmoothZoom();
    }

    @Override
    public void reconnect() throws IOException{
        camera.reconnect();
    }
}
