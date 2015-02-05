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
import android.hardware.Camera.CameraInfo;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
class CameraCompatGingerbread extends CameraCompatFroyo{

    @Override
    public void getCameraInfo(int cameraId, CameraInfo cameraInfo){
        Camera.getCameraInfo(cameraId, cameraInfo);
    }

    @Override
    public int getNumberOfCameras(){
        return Camera.getNumberOfCameras();
    }

    @Override
    public Camera open(int cameraId) throws RuntimeException{
        camera = Camera.open(cameraId);
        return camera;
    }

}
