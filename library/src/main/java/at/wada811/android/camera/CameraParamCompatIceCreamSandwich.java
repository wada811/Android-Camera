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
import android.hardware.Camera.Area;
import android.hardware.Camera.Parameters;
import android.os.Build;
import java.util.List;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class CameraParamCompatIceCreamSandwich extends CameraParamCompatHoneycomb{

    CameraParamCompatIceCreamSandwich(Parameters param){
        super(param);
    }

    @Override
    public void setRecordingHint(boolean hint){
        param.setRecordingHint(hint);
    }

    @Override
    public boolean isVideoSnapshotSupported(){
        return param.isVideoSnapshotSupported();
    }

    @Override
    public int getMaxNumDetectedFaces(){
        return param.getMaxNumDetectedFaces();
    }

    @Override
    public void setFocusAreas(List<Area> focusAreas){
        param.setFocusAreas(focusAreas);
    }

    @Override
    public List<Area> getFocusAreas(){
        return param.getFocusAreas();
    }

    @Override
    public int getMaxNumFocusAreas(){
        return param.getMaxNumFocusAreas();
    }

    @Override
    public void setMeteringAreas(List<Area> meteringAreas){
        param.setMeteringAreas(meteringAreas);
    }

    @Override
    public List<Area> getMeteringAreas(){
        return param.getMeteringAreas();
    }

    @Override
    public int getMaxNumMeteringAreas(){
        return param.getMaxNumMeteringAreas();
    }

    @Override
    public boolean isAutoExposureLockSupported(){
        return param.isAutoExposureLockSupported();
    }

    @Override
    public void setAutoExposureLock(boolean toggle){
        param.setAutoExposureLock(toggle);
    }

    @Override
    public boolean getAutoExposureLock(){
        return param.getAutoExposureLock();
    }

    @Override
    public boolean isAutoWhiteBalanceLockSupported(){
        return param.isAutoWhiteBalanceLockSupported();
    }

    @Override
    public void setAutoWhiteBalanceLock(boolean toggle){
        param.setAutoWhiteBalanceLock(toggle);
    }

    @Override
    public boolean getAutoWhiteBalanceLock(){
        return param.getAutoWhiteBalanceLock();
    }

}
