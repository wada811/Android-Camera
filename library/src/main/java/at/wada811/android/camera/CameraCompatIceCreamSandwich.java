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
import android.hardware.Camera.FaceDetectionListener;
import android.os.Build;
import java.io.IOException;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class CameraCompatIceCreamSandwich extends CameraCompatHoneycomb{

    @Override
    public void lock(){
        // Not need to call super
    }

    @Override
    public void unlock(){
        // Not need to call super
    }

    @Override
    public void reconnect() throws IOException{
        // Not need to call super
    }

    @Override
    public void setFaceDetectionListener(FaceDetectionListener listener){
        camera.setFaceDetectionListener(listener);
    }

    @Override
    public void startFaceDetection(){
        camera.startFaceDetection();
    }

    @Override
    public void stopFaceDetection(){
        camera.stopFaceDetection();
    }

}
