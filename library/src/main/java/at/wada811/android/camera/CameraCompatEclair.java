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
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.ECLAIR)
class CameraCompatEclair extends CameraCompatCupcake{

    @Override
    public void cancelAutoFocus(){
        camera.cancelAutoFocus();
    }

    @Override
    public void takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback postview, PictureCallback jpeg){
        camera.takePicture(shutter, raw, postview, jpeg);
    }

    @Override
    public void lock(){
        camera.lock();
    }

    @Override
    public void unlock(){
        camera.unlock();
    }

}
