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
import android.hardware.Camera.Size;
import android.os.Build;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class CameraParamCompatHoneycomb extends CameraParamCompatGingerbread{

    CameraParamCompatHoneycomb(Parameters param){
        super(param);
    }

    @Override
    public List<CameraSize> getSupportedVideoSizes(){
        List<Size> supportedVideoSizes = param.getSupportedVideoSizes();
        if(supportedVideoSizes == null || supportedVideoSizes.isEmpty()){
            return Collections.emptyList();
        }
        List<CameraSize> supportedVideoCameraSizes = new ArrayList<CameraSize>(supportedVideoSizes.size());
        for(Size size : supportedVideoSizes){
            supportedVideoCameraSizes.add(new CameraSize(size.width, size.height));
        }
        Collections.sort(supportedVideoCameraSizes, new CameraSizeComparator());
        return supportedVideoCameraSizes;

    }

    @Override
    public CameraSize getPreferredPreviewSizeForVideo(){
        Size preferredPreviewSizeForVideo = param.getPreferredPreviewSizeForVideo();
        if(preferredPreviewSizeForVideo != null){
            return new CameraSize(
                preferredPreviewSizeForVideo.width, preferredPreviewSizeForVideo.height
            );
        }else {
            return null;
        }
    }
}
