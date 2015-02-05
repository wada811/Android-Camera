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
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
class CameraHelperGingerbread extends CameraHelperBase{

    public CameraHelperGingerbread(Context context, int cameraId, int displayRotationDegrees){
        super(context, cameraId, displayRotationDegrees);
    }

    @Override
    public int getPreviewRotation(int displayRotationDegrees){
        int previewRotationDegrees;
        if(isFacingBack){
            previewRotationDegrees = (360 - displayRotationDegrees + cameraInfo.orientation) % 360;
        }else{
            previewRotationDegrees = (360 - displayRotationDegrees - cameraInfo.orientation) % 360;
        }
        previewRotationDegrees = (360 + previewRotationDegrees) % 360;
        return previewRotationDegrees;
    }

    public int getPictureRotation(int previewRotationDegrees){
        int pictureRotationDegrees;
        if(isFacingBack){
            pictureRotationDegrees = previewRotationDegrees;
        }else{
            pictureRotationDegrees = (360 - previewRotationDegrees) % 360;
        }
        return pictureRotationDegrees;
    }

    public int getVideoRotation(int previewRotationDegrees){
        int videoRotationDegrees;
        if(isFacingBack){
            videoRotationDegrees = previewRotationDegrees;
        }else{
            videoRotationDegrees = (360 - previewRotationDegrees) % 360;
        }
        return videoRotationDegrees;
    }

}
