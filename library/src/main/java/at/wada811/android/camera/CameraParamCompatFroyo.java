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

@TargetApi(Build.VERSION_CODES.FROYO)
class CameraParamCompatFroyo extends CameraParamCompatEclair{

    CameraParamCompatFroyo(Parameters param){
        super(param);
    }

    @Override
    public List<CameraSize> getSupportedJpegThumbnailSizes(){
        List<Size> supportedJpegThumbnailSizes = param.getSupportedJpegThumbnailSizes();
        if(supportedJpegThumbnailSizes == null || supportedJpegThumbnailSizes.isEmpty()){
            return Collections.emptyList();
        }
        List<CameraSize> supportedJpegThumbnailCameraSizes = new ArrayList<CameraSize>(
            supportedJpegThumbnailSizes.size()
        );
        for(Size size : supportedJpegThumbnailSizes){
            supportedJpegThumbnailCameraSizes.add(new CameraSize(size.width, size.height));
        }
        Collections.sort(supportedJpegThumbnailCameraSizes, new CameraSizeComparator());
        return supportedJpegThumbnailCameraSizes;
    }

    @Override
    public void setGpsProcessingMethod(String processing_method){
        param.setGpsProcessingMethod(processing_method);
    }

    @Override
    public boolean isZoomSupported(){
        return param.isZoomSupported();
    }

    @Override
    public boolean isSmoothZoomSupported(){
        return param.isSmoothZoomSupported();
    }

    @Override
    public void setZoom(int value){
        param.setZoom(value);
    }

    @Override
    public int getZoom(){
        return param.getZoom();
    }

    @Override
    public List<Integer> getZoomRatios(){
        return param.getZoomRatios();
    }

    @Override
    public int getMaxZoom(){
        return param.getMaxZoom();
    }

    @Override
    public float getHorizontalViewAngle(){
        return param.getHorizontalViewAngle();
    }

    @Override
    public float getVerticalViewAngle(){
        return param.getVerticalViewAngle();
    }

    @Override
    public float getFocalLength(){
        return param.getFocalLength();
    }

    @Override
    public void setExposureCompensation(int value){
        param.setExposureCompensation(value);
    }

    @Override
    public int getExposureCompensation(){
        return param.getExposureCompensation();
    }

    @Override
    public float getExposureCompensationStep(){
        return param.getExposureCompensationStep();
    }

    @Override
    public int getMaxExposureCompensation(){
        return param.getMaxExposureCompensation();
    }

    @Override
    public int getMinExposureCompensation(){
        return param.getMinExposureCompensation();
    }

}
