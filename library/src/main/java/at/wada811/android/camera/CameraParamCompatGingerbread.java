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
import android.hardware.Camera.Parameters;
import android.os.Build;
import java.util.List;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
class CameraParamCompatGingerbread extends CameraParamCompatFroyo{

    CameraParamCompatGingerbread(Parameters param){
        super(param);
    }

    @Override
    public List<int[]> getSupportedPreviewFpsRange(){
        return param.getSupportedPreviewFpsRange();
    }

    @Override
    public void setPreviewFpsRange(int min, int max){
        param.setPreviewFpsRange(min, max);
    }

    @Override
    public void getPreviewFpsRange(int[] range){
        param.getPreviewFpsRange(range);
    }

    @Override
    public void getFocusDistances(float[] output){
        param.getFocusDistances(output);
    }

}
