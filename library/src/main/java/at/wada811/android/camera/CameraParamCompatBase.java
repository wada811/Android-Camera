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

@TargetApi(Build.VERSION_CODES.BASE)
class CameraParamCompatBase extends CameraParamCompat{

    CameraParamCompatBase(Parameters param){
        super(param);
    }

    @Override
    public String flatten(){
        return param.flatten();
    }

    @Override
    public void unflatten(String flattened){
        param.unflatten(flattened);
    }

    @Override
    public void set(String key, int value){
        param.set(key, value);
    }

    @Override
    public void set(String key, String value){
        param.set(key, value);
    }

    @Override
    public String get(String key){
        return param.get(key);
    }

    @Override
    public int getInt(String key){
        return param.getInt(key);
    }

    @Override
    public void remove(String key){
        param.remove(key);
    }

    @Override
    public void setPictureFormat(int pixel_format){
        param.setPictureFormat(pixel_format);
    }

    @Override
    public int getPictureFormat(){
        return param.getPictureFormat();
    }

    @Override
    public void setPreviewFormat(int pixel_format){
        param.setPreviewFormat(pixel_format);
    }

    @Override
    public int getPreviewFormat(){
        return param.getPreviewFormat();
    }

    @Override
    public void setPreviewFpsRange(int min, int max){
        param.setPreviewFrameRate(min);
    }

    @Override
    public int getPreviewFrameRate(){
        return param.getPreviewFrameRate();
    }

    @Override
    public void setPictureSize(int width, int height){
        param.setPictureSize(width, height);
    }

    @Override
    public CameraSize getPictureSize(){
        Size pictureSize = param.getPictureSize();
        return new CameraSize(pictureSize.width, pictureSize.height);
    }

    @Override
    public void setPreviewSize(int width, int height){
        param.setPreviewSize(width, height);
    }

    @Override
    public CameraSize getPreviewSize(){
        Size previewSize = param.getPreviewSize();
        return new CameraSize(previewSize.width, previewSize.height);
    }

}
