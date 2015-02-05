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

@TargetApi(Build.VERSION_CODES.ECLAIR)
class CameraParamCompatEclair extends CameraParamCompatBase{

    CameraParamCompatEclair(Parameters param){
        super(param);
    }

    @Override
    public List<Integer> getSupportedPictureFormats(){
        return param.getSupportedPictureFormats();
    }

    @Override
    public List<Integer> getSupportedPreviewFormats(){
        return param.getSupportedPreviewFormats();
    }

    @Override
    public List<CameraSize> getSupportedPictureSizes(){
        List<Size> supportedPictureSizes = param.getSupportedPictureSizes();
        if(supportedPictureSizes == null || supportedPictureSizes.isEmpty()){
            return Collections.emptyList();
        }
        List<CameraSize> supportedPictureCameraSizes = new ArrayList<CameraSize>(
            supportedPictureSizes.size()
        );
        for(Size size : supportedPictureSizes){
            supportedPictureCameraSizes.add(new CameraSize(size.width, size.height));
        }
        Collections.sort(supportedPictureCameraSizes, new CameraSizeComparator());
        return supportedPictureCameraSizes;
    }

    @Override
    public List<Integer> getSupportedPreviewFrameRates(){
        return param.getSupportedPreviewFrameRates();
    }

    @Override
    public List<CameraSize> getSupportedPreviewSizes(){
        List<Size> supportedPreviewSizes = param.getSupportedPreviewSizes();
        if(supportedPreviewSizes == null || supportedPreviewSizes.isEmpty()){
            return Collections.emptyList();
        }
        List<CameraSize> supportedPreviewCameraSizes = new ArrayList<CameraSize>(
            supportedPreviewSizes.size()
        );
        for(Size size : supportedPreviewSizes){
            supportedPreviewCameraSizes.add(new CameraSize(size.width, size.height));
        }
        Collections.sort(supportedPreviewCameraSizes, new CameraSizeComparator());
        return supportedPreviewCameraSizes;
    }

    @Override
    public void setRotation(int rotation){
        param.setRotation(rotation);
    }

    @Override
    public void setJpegQuality(int quality){
        param.setJpegQuality(quality);
    }

    @Override
    public int getJpegQuality(){
        return param.getJpegQuality();
    }

    @Override
    public void setJpegThumbnailQuality(int quality){
        param.setJpegThumbnailQuality(quality);
    }

    @Override
    public int getJpegThumbnailQuality(){
        return param.getJpegThumbnailQuality();
    }

    @Override
    public void setJpegThumbnailSize(int width, int height){
        param.setJpegThumbnailSize(width, height);
    }

    @Override
    public CameraSize getJpegThumbnailSize(){
        Size jpegThumbnailSize = param.getJpegThumbnailSize();
        return new CameraSize(jpegThumbnailSize.width, jpegThumbnailSize.height);
    }

    @Override
    public void setGpsAltitude(double altitude){
        param.setGpsAltitude(altitude);
    }

    @Override
    public void setGpsLatitude(double latitude){
        param.setGpsLatitude(latitude);
    }

    @Override
    public void setGpsLongitude(double longitude){
        param.setGpsLongitude(longitude);
    }

    @Override
    public void setGpsTimestamp(long timestamp){
        param.setGpsTimestamp(timestamp);
    }

    @Override
    public void removeGpsData(){
        param.removeGpsData();
    }

    @Override
    public List<String> getSupportedAntibanding(){
        return param.getSupportedAntibanding();
    }

    @Override
    public void setAntibanding(String antibanding){
        param.setAntibanding(antibanding);
    }

    @Override
    public String getAntibanding(){
        return param.getAntibanding();
    }

    @Override
    public List<String> getSupportedColorEffects(){
        return param.getSupportedColorEffects();
    }

    @Override
    public void setColorEffect(String value){
        param.setColorEffect(value);
    }

    @Override
    public String getColorEffect(){
        return param.getColorEffect();
    }

    @Override
    public List<String> getSupportedFlashModes(){
        return param.getSupportedFlashModes();
    }

    @Override
    public void setFlashMode(String value){
        param.setFlashMode(value);
    }

    @Override
    public String getFlashMode(){
        return param.getFlashMode();
    }

    @Override
    public List<String> getSupportedFocusModes(){
        return param.getSupportedFocusModes();
    }

    @Override
    public void setFocusMode(String value){
        param.setFocusMode(value);
    }

    @Override
    public String getFocusMode(){
        return param.getFocusMode();
    }

    @Override
    public List<String> getSupportedSceneModes(){
        return param.getSupportedSceneModes();
    }

    @Override
    public void setSceneMode(String value){
        param.setSceneMode(value);
    }

    @Override
    public String getSceneMode(){
        return param.getSceneMode();
    }

    @Override
    public List<String> getSupportedWhiteBalance(){
        return param.getSupportedWhiteBalance();
    }

    @Override
    public void setWhiteBalance(String value){
        param.setWhiteBalance(value);
    }

    @Override
    public String getWhiteBalance(){
        return param.getWhiteBalance();
    }

}
