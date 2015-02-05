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

import android.hardware.Camera.Area;
import android.hardware.Camera.Parameters;
import android.os.Build;
import java.util.List;

public abstract class CameraParamCompat{

    public static String TAG = CameraParamCompat.class.getSimpleName();

    Parameters param;

    public static CameraParamCompat newInstance(Parameters param){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            return new CameraParamCompatIceCreamSandwichMR1(param);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            return new CameraParamCompatIceCreamSandwich(param);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            return new CameraParamCompatHoneycomb(param);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return new CameraParamCompatGingerbread(param);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO){
            return new CameraParamCompatFroyo(param);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR){
            return new CameraParamCompatEclair(param);
        }else{
            return new CameraParamCompatBase(param);
        }
    }

    public CameraParamCompat(Parameters param){
        this.param = param;
    }

    public Parameters getParam(){
        return param;
    }

    /**
     * Creates a single string with all the parameters set in
     * this Parameters object.
     * <p>
     * The {@link #unflatten(String)} method does the reverse.
     * </p>
     *
     * @return a String with all values from this Parameters object, in
     * semi-colon delimited key-value pairs
     */
    public String flatten(){
        return null;
    }

    /**
     * Takes a flattened string of parameters and adds each one to
     * this Parameters object.
     * <p>
     * The {@link #flatten()} method does the reverse.
     * </p>
     *
     * @param flattened a String of parameters (key-value paired) that
     * are semi-colon delimited
     */
    public void unflatten(String flattened){
    }

    public void remove(String key){
    }

    /**
     * Sets a String parameter.
     *
     * @param key the key name for the parameter
     * @param value the String value of the parameter
     */
    public void set(String key, String value){
    }

    /**
     * Sets an integer parameter.
     *
     * @param key the key name for the parameter
     * @param value the int value of the parameter
     */
    public void set(String key, int value){
    }

    /**
     * Returns the value of a String parameter.
     *
     * @param key the key name for the parameter
     *
     * @return the String value of the parameter
     */
    public String get(String key){
        return null;
    }

    /**
     * Returns the value of an integer parameter.
     *
     * @param key the key name for the parameter
     *
     * @return the int value of the parameter
     */
    public int getInt(String key){
        return 0;
    }

    /**
     * Sets the dimensions for preview pictures. If the preview has already
     * started, applications should stop the preview first before changing
     * preview size.
     * <p/>
     * The sides of width and height are based on camera orientation. That
     * is, the preview size is the size before it is rotated by display
     * orientation. So applications need to consider the display orientation
     * while setting preview size. For example, suppose the camera supports
     * both 480x320 and 320x480 preview sizes. The application wants a 3:2
     * preview ratio. If the display orientation is set to 0 or 180, preview
     * size should be set to 480x320. If the display orientation is set to
     * 90 or 270, preview size should be set to 320x480. The display
     * orientation should also be considered while setting picture size and
     * thumbnail size.
     *
     * @param width the width of the pictures, in pixels
     * @param height the height of the pictures, in pixels
     *
     * @see android.hardware.Camera#setDisplayOrientation(int)
     * @see android.hardware.Camera#getCameraInfo(int, android.hardware.Camera.CameraInfo)
     * @see #setPictureSize(int, int)
     * @see #setJpegThumbnailSize(int, int)
     */
    public void setPreviewSize(int width, int height){
    }

    /**
     * Returns the dimensions setting for preview pictures.
     *
     * @return a Size object with the width and height setting
     * for the preview picture
     */
    public CameraSize getPreviewSize(){
        return null;
    }

    /**
     * Gets the supported preview sizes.
     *
     * @return a list of Size object. This method will always return a list
     * with at least one element.
     */
    public List<CameraSize> getSupportedPreviewSizes(){
        return null;
    }

    /**
     * <p>
     * Gets the supported video frame sizes that can be used by MediaRecorder.
     * </p>
     * <p/>
     * <p>
     * If the returned list is not null, the returned list will contain at least one Size and one of
     * the sizes in the returned list must be passed to MediaRecorder.setVideoSize() for camcorder
     * application if camera is used as the video source. In this case, the size of the preview can
     * be different from the resolution of the recorded video during video recording.
     * </p>
     *
     * @return a list of Size object if camera has separate preview and
     * video output; otherwise, null is returned.
     *
     * @see #getPreferredPreviewSizeForVideo()
     */
    public List<CameraSize> getSupportedVideoSizes(){
        return null;
    }

    /**
     * Returns the preferred or recommended preview size (width and height)
     * in pixels for video recording. Camcorder applications should
     * set the preview size to a value that is not larger than the
     * preferred preview size. In other words, the product of the width
     * and height of the preview size should not be larger than that of
     * the preferred preview size. In addition, we recommend to choose a
     * preview size that has the same aspect ratio as the resolution of
     * video to be recorded.
     *
     * @return the preferred preview size (width and height) in pixels for
     * video recording if getSupportedVideoSizes() does not return
     * null; otherwise, null is returned.
     *
     * @see #getSupportedVideoSizes()
     */
    public CameraSize getPreferredPreviewSizeForVideo(){
        return null;
    }

    /**
     * <p>
     * Sets the dimensions for EXIF thumbnail in Jpeg picture. If applications set both width and
     * height to 0, EXIF will not contain thumbnail.
     * </p>
     * <p/>
     * <p>
     * Applications need to consider the display orientation. See {@link #setPreviewSize(int, int)}
     * for reference.
     * </p>
     *
     * @param width the width of the thumbnail, in pixels
     * @param height the height of the thumbnail, in pixels
     *
     * @see #setPreviewSize(int, int)
     */
    public void setJpegThumbnailSize(int width, int height){
    }

    /**
     * Returns the dimensions for EXIF thumbnail in Jpeg picture.
     *
     * @return a Size object with the height and width setting for the EXIF
     * thumbnails
     */
    public CameraSize getJpegThumbnailSize(){
        return null;
    }

    /**
     * Gets the supported jpeg thumbnail sizes.
     *
     * @return a list of Size object. This method will always return a list
     * with at least two elements. Size 0,0 (no thumbnail) is always
     * supported.
     */
    public List<CameraSize> getSupportedJpegThumbnailSizes(){
        return null;
    }

    /**
     * Sets the quality of the EXIF thumbnail in Jpeg picture.
     *
     * @param quality the JPEG quality of the EXIF thumbnail. The range is 1
     * to 100, with 100 being the best.
     */
    public void setJpegThumbnailQuality(int quality){
    }

    /**
     * Returns the quality setting for the EXIF thumbnail in Jpeg picture.
     *
     * @return the JPEG quality setting of the EXIF thumbnail.
     */
    public int getJpegThumbnailQuality(){
        return 0;
    }

    /**
     * Sets Jpeg quality of captured picture.
     *
     * @param quality the JPEG quality of captured picture. The range is 1
     * to 100, with 100 being the best.
     */
    public void setJpegQuality(int quality){
    }

    /**
     * Returns the quality setting for the JPEG picture.
     *
     * @return the JPEG picture quality setting.
     */
    public int getJpegQuality(){
        return 0;
    }

    /**
     * Sets the rate at which preview frames are received. This is the
     * target frame rate. The actual frame rate depends on the driver.
     *
     * @param fps the frame rate (frames per second)
     *
     * @deprecated replaced by {@link #setPreviewFpsRange(int, int)}
     */
    @Deprecated
    public void setPreviewFrameRate(int fps){
    }

    /**
     * Returns the setting for the rate at which preview frames are
     * received. This is the target frame rate. The actual frame rate
     * depends on the driver.
     *
     * @return the frame rate setting (frames per second)
     *
     * @deprecated replaced by {@link #getPreviewFpsRange(int[])}
     */
    @Deprecated
    public int getPreviewFrameRate(){
        return 0;
    }

    /**
     * Gets the supported preview frame rates.
     *
     * @return a list of supported preview frame rates. null if preview
     * frame rate setting is not supported.
     *
     * @deprecated replaced by {@link #getSupportedPreviewFpsRange()}
     */
    @Deprecated
    public List<Integer> getSupportedPreviewFrameRates(){
        return null;
    }

    /**
     * Sets the minimum and maximum preview fps. This controls the rate of
     * preview frames received in {@link android.hardware.Camera.PreviewCallback}. The minimum and
     * maximum preview fps must be one of the elements from {@link #getSupportedPreviewFpsRange}.
     *
     * @param min the minimum preview fps (scaled by 1000).
     * @param max the maximum preview fps (scaled by 1000).
     *
     * @throws RuntimeException if fps range is invalid.
     * @see android.hardware.Camera#setPreviewCallbackWithBuffer(android.hardware.Camera.PreviewCallback)
     * @see #getSupportedPreviewFpsRange()
     */
    public void setPreviewFpsRange(int min, int max){
    }

    /**
     * Returns the current minimum and maximum preview fps. The values are
     * one of the elements returned by {@link #getSupportedPreviewFpsRange}.
     *
     * @return range the minimum and maximum preview fps (scaled by 1000).
     *
     * @see android.hardware.Camera.Parameters#PREVIEW_FPS_MIN_INDEX
     * @see android.hardware.Camera.Parameters#PREVIEW_FPS_MAX_INDEX
     * @see #getSupportedPreviewFpsRange()
     */
    public void getPreviewFpsRange(int[] range){
    }

    /**
     * Gets the supported preview fps (frame-per-second) ranges. Each range
     * contains a minimum fps and maximum fps. If minimum fps equals to
     * maximum fps, the camera outputs frames in fixed frame rate. If not,
     * the camera outputs frames in auto frame rate. The actual frame rate
     * fluctuates between the minimum and the maximum. The values are
     * multiplied by 1000 and represented in integers. For example, if frame
     * rate is 26.623 frames per second, the value is 26623.
     *
     * @return a list of supported preview fps ranges. This method returns a
     * list with at least one element. Every element is an int array
     * of two values - minimum fps and maximum fps. The list is
     * sorted from small to large (first by maximum fps and then
     * minimum fps).
     *
     * @see android.hardware.Camera.Parameters#PREVIEW_FPS_MIN_INDEX
     * @see android.hardware.Camera.Parameters#PREVIEW_FPS_MAX_INDEX
     */
    public List<int[]> getSupportedPreviewFpsRange(){
        return null;
    }

    /**
     * Sets the image format for preview pictures.
     * <p>
     * If this is never called, the default format will be {@link android.graphics.ImageFormat#NV21}
     * , which uses the NV21 encoding format.
     * </p>
     * <p/>
     * <p>
     * Use {@link android.hardware.Camera.Parameters#getSupportedPreviewFormats} to get a list of the available preview
     * formats.
     * <p/>
     * <p>
     * It is strongly recommended that either {@link android.graphics.ImageFormat#NV21} or
     * {@link android.graphics.ImageFormat#YV12} is used, since they are supported by all camera
     * devices.
     * </p>
     * <p/>
     * <p/>
     * For YV12, the image buffer that is received is not necessarily tightly packed, as there may
     * be padding at the end of each row of pixel data, as described in
     * {@link android.graphics.ImageFormat#YV12}. For camera callback data, it can be assumed that
     * the stride of the Y and UV data is the smallest possible that meets the alignment
     * requirements. That is, if the preview size is <var>width x height</var>, then the following
     * equations describe the buffer index for the beginning of row <var>y</var> for the Y plane and
     * row <var>c</var> for the U and V planes:
     * <p/>
     * {@code
     * <pre>
     * yStride   = (int) ceil(width / 16.0) * 16;
     * uvStride  = (int) ceil( (yStride / 2) / 16.0) * 16;
     * ySize     = yStride * height;
     * uvSize    = uvStride * height / 2;
     * yRowIndex = yStride * y;
     * uRowIndex = ySize + uvSize + uvStride * c;
     * vRowIndex = ySize + uvStride * c;
     * size      = ySize + uvSize * 2;</pre>
     * }
     *
     * @param pixel_format the desired preview picture format, defined by
     * one of the {@link android.graphics.ImageFormat} constants. (E.g.,
     * <var>ImageFormat.NV21</var> (default), or
     * <var>ImageFormat.YV12</var>)
     *
     * @see android.graphics.ImageFormat
     * @see android.hardware.Camera.Parameters#getSupportedPreviewFormats
     */
    public void setPreviewFormat(int pixel_format){
    }

    /**
     * Returns the image format for preview frames got from {@link android.hardware.Camera.PreviewCallback}.
     *
     * @return the preview format.
     *
     * @see android.graphics.ImageFormat
     * @see #setPreviewFormat
     */
    public int getPreviewFormat(){
        return 0;
    }

    /**
     * Gets the supported preview formats. {@link android.graphics.ImageFormat#NV21} is always
     * supported. {@link android.graphics.ImageFormat#YV12} is always supported since API level 12.
     *
     * @return a list of supported preview formats. This method will always
     * return a list with at least one element.
     *
     * @see android.graphics.ImageFormat
     * @see #setPreviewFormat
     */
    public List<Integer> getSupportedPreviewFormats(){
        return null;
    }

    /**
     * <p>
     * Sets the dimensions for pictures.
     * </p>
     * <p/>
     * <p>
     * Applications need to consider the display orientation. See {@link #setPreviewSize(int, int)}
     * for reference.
     * </p>
     *
     * @param width the width for pictures, in pixels
     * @param height the height for pictures, in pixels
     *
     * @see #setPreviewSize(int, int)
     */
    public void setPictureSize(int width, int height){
    }

    /**
     * Returns the dimension setting for pictures.
     *
     * @return a Size object with the height and width setting
     * for pictures
     */
    public CameraSize getPictureSize(){
        return null;
    }

    /**
     * Gets the supported picture sizes.
     *
     * @return a list of supported picture sizes. This method will always
     * return a list with at least one element.
     */
    public List<CameraSize> getSupportedPictureSizes(){
        return null;
    }

    /**
     * Sets the image format for pictures.
     *
     * @param pixel_format the desired picture format
     * (<var>ImageFormat.NV21</var>,
     * <var>ImageFormat.RGB_565</var>, or
     * <var>ImageFormat.JPEG</var>)
     *
     * @see android.graphics.ImageFormat
     */
    public void setPictureFormat(int pixel_format){
    }

    /**
     * Returns the image format for pictures.
     *
     * @return the picture format
     *
     * @see android.graphics.ImageFormat
     */
    public int getPictureFormat(){
        return 0;
    }

    /**
     * Gets the supported picture formats.
     *
     * @return supported picture formats. This method will always return a
     * list with at least one element.
     *
     * @see android.graphics.ImageFormat
     */
    public List<Integer> getSupportedPictureFormats(){
        return null;
    }

    /**
     * Sets the clockwise rotation angle in degrees relative to the
     * orientation of the camera. This affects the pictures returned from
     * JPEG {@link android.hardware.Camera.PictureCallback}. The camera driver may set orientation
     * in the EXIF header without rotating the picture. Or the driver may
     * rotate the picture and the EXIF thumbnail. If the Jpeg picture is
     * rotated, the orientation in the EXIF header will be missing or 1 (row
     * #0 is top and column #0 is left side).
     * <p/>
     * <p/>
     * If applications want to rotate the picture to match the orientation of what users see, apps
     * should use {@link android.view.OrientationEventListener} and
     * {@link android.hardware.Camera.CameraInfo}. The value from OrientationEventListener is
     * relative to the natural orientation of the device. CameraInfo.orientation is the angle
     * between camera orientation and natural device orientation. The sum of the two is the rotation
     * angle for back-facing camera. The difference of the two is the rotation angle for
     * front-facing camera. Note that the JPEG pictures of front-facing cameras are not mirrored as
     * in preview display.
     * <p/>
     * <p/>
     * For example, suppose the natural orientation of the device is portrait. The device is rotated
     * 270 degrees clockwise, so the device orientation is 270. Suppose a back-facing camera sensor
     * is mounted in landscape and the top side of the camera sensor is aligned with the right edge
     * of the display in natural orientation. So the camera orientation is 90. The rotation should
     * be set to 0 (270 + 90).
     * <p/>
     * <p/>
     * The reference code is as follows.
     * <p/>
     * <pre>
     * public void onOrientationChanged(int orientation){
     *     if(orientation == ORIENTATION_UNKNOWN)
     *         return;
     *     android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
     *     android.hardware.Camera.getCameraInfo(cameraId, info);
     *     orientation = (orientation + 45) / 90 * 90;
     *     int rotation = 0;
     *     if(info.facing == CameraInfo.CAMERA_FACING_FRONT){
     *         rotation = (info.orientation - orientation + 360) % 360;
     *     }else{ // back-facing camera
     *         rotation = (info.orientation + orientation) % 360;
     *     }
     *     mParameters.setRotation(rotation);
     * }
     * </pre>
     *
     * @param rotation The rotation angle in degrees relative to the
     * orientation of the camera. Rotation can only be 0,
     * 90, 180 or 270.
     *
     * @throws IllegalArgumentException if rotation value is invalid.
     * @see android.view.OrientationEventListener
     * @see android.hardware.Camera#getCameraInfo(int, android.hardware.Camera.CameraInfo)
     */
    public void setRotation(int rotation){
    }

    /**
     * Sets GPS latitude coordinate. This will be stored in JPEG EXIF
     * header.
     *
     * @param latitude GPS latitude coordinate.
     */
    public void setGpsLatitude(double latitude){
    }

    /**
     * Sets GPS longitude coordinate. This will be stored in JPEG EXIF
     * header.
     *
     * @param longitude GPS longitude coordinate.
     */
    public void setGpsLongitude(double longitude){
    }

    /**
     * Sets GPS altitude. This will be stored in JPEG EXIF header.
     *
     * @param altitude GPS altitude in meters.
     */
    public void setGpsAltitude(double altitude){
    }

    /**
     * Sets GPS timestamp. This will be stored in JPEG EXIF header.
     *
     * @param timestamp GPS timestamp (UTC in seconds since January 1,
     * 1970).
     */
    public void setGpsTimestamp(long timestamp){
    }

    /**
     * Sets GPS processing method. It will store up to 32 characters
     * in JPEG EXIF header.
     *
     * @param processing_method The processing method to get this location.
     */
    public void setGpsProcessingMethod(String processing_method){
    }

    /**
     * Removes GPS latitude, longitude, altitude, and timestamp from the
     * parameters.
     */
    public void removeGpsData(){
    }

    /**
     * Gets the current white balance setting.
     *
     * @return current white balance. null if white balance setting is not
     * supported.
     *
     * @see android.hardware.Camera.Parameters#WHITE_BALANCE_AUTO
     * @see android.hardware.Camera.Parameters#WHITE_BALANCE_INCANDESCENT
     * @see android.hardware.Camera.Parameters#WHITE_BALANCE_FLUORESCENT
     * @see android.hardware.Camera.Parameters#WHITE_BALANCE_WARM_FLUORESCENT
     * @see android.hardware.Camera.Parameters#WHITE_BALANCE_DAYLIGHT
     * @see android.hardware.Camera.Parameters#WHITE_BALANCE_CLOUDY_DAYLIGHT
     * @see android.hardware.Camera.Parameters#WHITE_BALANCE_TWILIGHT
     * @see android.hardware.Camera.Parameters#WHITE_BALANCE_SHADE
     */
    public String getWhiteBalance(){
        return null;
    }

    /**
     * Sets the white balance. Changing the setting will release the
     * auto-white balance lock. It is recommended not to change white
     * balance and AWB lock at the same time.
     *
     * @param value new white balance.
     *
     * @see #getWhiteBalance()
     * @see #setAutoWhiteBalanceLock(boolean)
     */
    public void setWhiteBalance(String value){
    }

    /**
     * Gets the supported white balance.
     *
     * @return a list of supported white balance. null if white balance
     * setting is not supported.
     *
     * @see #getWhiteBalance()
     */
    public List<String> getSupportedWhiteBalance(){
        return null;
    }

    /**
     * Gets the current color effect setting.
     *
     * @return current color effect. null if color effect
     * setting is not supported.
     *
     * @see android.hardware.Camera.Parameters#EFFECT_NONE
     * @see android.hardware.Camera.Parameters#EFFECT_MONO
     * @see android.hardware.Camera.Parameters#EFFECT_NEGATIVE
     * @see android.hardware.Camera.Parameters#EFFECT_SOLARIZE
     * @see android.hardware.Camera.Parameters#EFFECT_SEPIA
     * @see android.hardware.Camera.Parameters#EFFECT_POSTERIZE
     * @see android.hardware.Camera.Parameters#EFFECT_WHITEBOARD
     * @see android.hardware.Camera.Parameters#EFFECT_BLACKBOARD
     * @see android.hardware.Camera.Parameters#EFFECT_AQUA
     */
    public String getColorEffect(){
        return null;
    }

    /**
     * Sets the current color effect setting.
     *
     * @param value new color effect.
     *
     * @see #getColorEffect()
     */
    public void setColorEffect(String value){
    }

    /**
     * Gets the supported color effects.
     *
     * @return a list of supported color effects. null if color effect
     * setting is not supported.
     *
     * @see #getColorEffect()
     */
    public List<String> getSupportedColorEffects(){
        return null;
    }

    /**
     * Gets the current antibanding setting.
     *
     * @return current antibanding. null if antibanding setting is not
     * supported.
     *
     * @see android.hardware.Camera.Parameters#ANTIBANDING_AUTO
     * @see android.hardware.Camera.Parameters#ANTIBANDING_50HZ
     * @see android.hardware.Camera.Parameters#ANTIBANDING_60HZ
     * @see android.hardware.Camera.Parameters#ANTIBANDING_OFF
     */
    public String getAntibanding(){
        return null;
    }

    /**
     * Sets the antibanding.
     *
     * @param antibanding new antibanding value.
     *
     * @see #getAntibanding()
     */
    public void setAntibanding(String antibanding){
    }

    /**
     * Gets the supported antibanding values.
     *
     * @return a list of supported antibanding values. null if antibanding
     * setting is not supported.
     *
     * @see #getAntibanding()
     */
    public List<String> getSupportedAntibanding(){
        return null;
    }

    /**
     * Gets the current scene mode setting.
     *
     * @return one of SCENE_MODE_XXX string constant. null if scene mode
     * setting is not supported.
     *
     * @see android.hardware.Camera.Parameters#SCENE_MODE_AUTO
     * @see android.hardware.Camera.Parameters#SCENE_MODE_ACTION
     * @see android.hardware.Camera.Parameters#SCENE_MODE_PORTRAIT
     * @see android.hardware.Camera.Parameters#SCENE_MODE_LANDSCAPE
     * @see android.hardware.Camera.Parameters#SCENE_MODE_NIGHT
     * @see android.hardware.Camera.Parameters#SCENE_MODE_NIGHT_PORTRAIT
     * @see android.hardware.Camera.Parameters#SCENE_MODE_THEATRE
     * @see android.hardware.Camera.Parameters#SCENE_MODE_BEACH
     * @see android.hardware.Camera.Parameters#SCENE_MODE_SNOW
     * @see android.hardware.Camera.Parameters#SCENE_MODE_SUNSET
     * @see android.hardware.Camera.Parameters#SCENE_MODE_STEADYPHOTO
     * @see android.hardware.Camera.Parameters#SCENE_MODE_FIREWORKS
     * @see android.hardware.Camera.Parameters#SCENE_MODE_SPORTS
     * @see android.hardware.Camera.Parameters#SCENE_MODE_PARTY
     * @see android.hardware.Camera.Parameters#SCENE_MODE_CANDLELIGHT
     * @see android.hardware.Camera.Parameters#SCENE_MODE_BARCODE
     */
    public String getSceneMode(){
        return null;
    }

    /**
     * Sets the scene mode. Changing scene mode may override other
     * parameters (such as flash mode, focus mode, white balance). For
     * example, suppose originally flash mode is on and supported flash
     * modes are on/off. In night scene mode, both flash mode and supported
     * flash mode may be changed to off. After setting scene mode,
     * applications should call getParam to know if some parameters are
     * changed.
     *
     * @param value scene mode.
     *
     * @see #getSceneMode()
     */
    public void setSceneMode(String value){
    }

    /**
     * Gets the supported scene modes.
     *
     * @return a list of supported scene modes. null if scene mode setting
     * is not supported.
     *
     * @see #getSceneMode()
     */
    public List<String> getSupportedSceneModes(){
        return null;
    }

    /**
     * Gets the current flash mode setting.
     *
     * @return current flash mode. null if flash mode setting is not
     * supported.
     *
     * @see android.hardware.Camera.Parameters#FLASH_MODE_OFF
     * @see android.hardware.Camera.Parameters#FLASH_MODE_AUTO
     * @see android.hardware.Camera.Parameters#FLASH_MODE_ON
     * @see android.hardware.Camera.Parameters#FLASH_MODE_RED_EYE
     * @see android.hardware.Camera.Parameters#FLASH_MODE_TORCH
     */
    public String getFlashMode(){
        return null;
    }

    /**
     * Sets the flash mode.
     *
     * @param value flash mode.
     *
     * @see #getFlashMode()
     */
    public void setFlashMode(String value){
    }

    /**
     * Gets the supported flash modes.
     *
     * @return a list of supported flash modes. null if flash mode setting
     * is not supported.
     *
     * @see #getFlashMode()
     */
    public List<String> getSupportedFlashModes(){
        return null;
    }

    /**
     * Gets the current focus mode setting.
     *
     * @return current focus mode. This method will always return a non-null
     * value. Applications should call {@link android.hardware.Camera#autoFocus(android.hardware.Camera.AutoFocusCallback)} to start the
     * focus if focus
     * mode is FOCUS_MODE_AUTO or FOCUS_MODE_MACRO.
     *
     * @see android.hardware.Camera.Parameters#FOCUS_MODE_AUTO
     * @see android.hardware.Camera.Parameters#FOCUS_MODE_INFINITY
     * @see android.hardware.Camera.Parameters#FOCUS_MODE_MACRO
     * @see android.hardware.Camera.Parameters#FOCUS_MODE_FIXED
     * @see android.hardware.Camera.Parameters#FOCUS_MODE_EDOF
     * @see android.hardware.Camera.Parameters#FOCUS_MODE_CONTINUOUS_VIDEO
     */
    public String getFocusMode(){
        return null;
    }

    /**
     * Sets the focus mode.
     *
     * @param value focus mode.
     *
     * @see #getFocusMode()
     */
    public void setFocusMode(String value){
    }

    /**
     * Gets the supported focus modes.
     *
     * @return a list of supported focus modes. This method will always
     * return a list with at least one element.
     *
     * @see #getFocusMode()
     */
    public List<String> getSupportedFocusModes(){
        return null;
    }

    /**
     * Gets the focal length (in millimeter) of the camera.
     *
     * @return the focal length. This method will always return a valid
     * value.
     */
    public float getFocalLength(){
        return 0.0f;
    }

    /**
     * Gets the horizontal angle of view in degrees.
     *
     * @return horizontal angle of view. This method will always return a
     * valid value.
     */
    public float getHorizontalViewAngle(){
        return 0.0f;
    }

    /**
     * Gets the vertical angle of view in degrees.
     *
     * @return vertical angle of view. This method will always return a
     * valid value.
     */
    public float getVerticalViewAngle(){
        return 0.0f;
    }

    /**
     * Gets the current exposure compensation index.
     *
     * @return current exposure compensation index. The range is {@link #getMinExposureCompensation}
     * to {@link #getMaxExposureCompensation}. 0 means exposure is not
     * adjusted.
     */
    public int getExposureCompensation(){
        return 0;
    }

    /**
     * Sets the exposure compensation index.
     *
     * @param value exposure compensation index. The valid value range is
     * from {@link #getMinExposureCompensation} (inclusive) to
     * {@link #getMaxExposureCompensation} (inclusive). 0 means exposure is
     * not adjusted. Application should call
     * getMinExposureCompensation and getMaxExposureCompensation to
     * know if exposure compensation is supported.
     */
    public void setExposureCompensation(int value){
    }

    /**
     * Gets the maximum exposure compensation index.
     *
     * @return maximum exposure compensation index (>=0). If both this
     * method and {@link #getMinExposureCompensation} return 0,
     * exposure compensation is not supported.
     */
    public int getMaxExposureCompensation(){
        return 0;
    }

    /**
     * Gets the minimum exposure compensation index.
     *
     * @return minimum exposure compensation index (<=0). If both this
     * method and {@link #getMaxExposureCompensation} return 0,
     * exposure compensation is not supported.
     */
    public int getMinExposureCompensation(){
        return 0;
    }

    /**
     * Gets the exposure compensation step.
     *
     * @return exposure compensation step. Applications can get EV by
     * multiplying the exposure compensation index and step. Ex: if
     * exposure compensation index is -6 and step is 0.333333333, EV
     * is -2.
     */
    public float getExposureCompensationStep(){
        return 0;
    }

    /**
     * <p>
     * Sets the auto-exposure lock state. Applications should check
     * {@link #isAutoExposureLockSupported} before using this method.
     * </p>
     * <p/>
     * <p>
     * If set to true, the camera auto-exposure routine will immediately pause until the lock is set
     * to false. Exposure compensation settings changes will still take effect while auto-exposure
     * is locked.
     * </p>
     * <p/>
     * <p>
     * If auto-exposure is already locked, setting this to true again has no effect (the driver will
     * not recalculate exposure values).
     * </p>
     * <p/>
     * <p>
     * Stopping preview with {@link android.hardware.Camera#stopPreview()}, or triggering still image capture with
     * {@link android.hardware.Camera#takePicture(android.hardware.Camera.ShutterCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback)},
     * will not change the lock.
     * </p>
     * <p/>
     * <p>
     * Exposure compensation, auto-exposure lock, and auto-white balance lock can be used to capture
     * an exposure-bracketed burst of images, for example.
     * </p>
     * <p/>
     * <p>
     * Auto-exposure state, including the lock state, will not be maintained after camera
     * {@link android.hardware.Camera#release()} is called. Locking auto-exposure after {@link android.hardware.Camera#open()} but before the
     * first call to {@link android.hardware.Camera#startPreview()} will not allow the auto-exposure routine to run at all,
     * and may result in severely over- or under-exposed images.
     * </p>
     *
     * @param toggle new state of the auto-exposure lock. True means that
     * auto-exposure is locked, false means that the auto-exposure
     * routine is free to run normally.
     *
     * @see #getAutoExposureLock()
     */
    public void setAutoExposureLock(boolean toggle){
    }

    /**
     * Gets the state of the auto-exposure lock. Applications should check
     * {@link #isAutoExposureLockSupported} before using this method. See
     * {@link #setAutoExposureLock} for details about the lock.
     *
     * @return State of the auto-exposure lock. Returns true if
     * auto-exposure is currently locked, and false otherwise.
     *
     * @see #setAutoExposureLock(boolean)
     */
    public boolean getAutoExposureLock(){
        return false;
    }

    /**
     * Returns true if auto-exposure locking is supported. Applications
     * should call this before trying to lock auto-exposure. See {@link #setAutoExposureLock} for
     * details about the lock.
     *
     * @return true if auto-exposure lock is supported.
     *
     * @see #setAutoExposureLock(boolean)
     */
    public boolean isAutoExposureLockSupported(){
        return false;
    }

    /**
     * <p>
     * Sets the auto-white balance lock state. Applications should check
     * {@link #isAutoWhiteBalanceLockSupported} before using this method.
     * </p>
     * <p/>
     * <p>
     * If set to true, the camera auto-white balance routine will immediately pause until the lock
     * is set to false.
     * </p>
     * <p/>
     * <p>
     * If auto-white balance is already locked, setting this to true again has no effect (the driver
     * will not recalculate white balance values).
     * </p>
     * <p/>
     * <p>
     * Stopping preview with {@link android.hardware.Camera#stopPreview()}, or triggering still image capture with
     * {@link android.hardware.Camera#takePicture(android.hardware.Camera.ShutterCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback)},
     * will not change the the lock.
     * </p>
     * <p/>
     * <p>
     * Changing the white balance mode with {@link #setWhiteBalance} will release the auto-white
     * balance lock if it is set.
     * </p>
     * <p/>
     * <p>
     * Exposure compensation, AE lock, and AWB lock can be used to capture an exposure-bracketed
     * burst of images, for example. Auto-white balance state, including the lock state, will not be
     * maintained after camera {@link android.hardware.Camera#release()} is called. Locking auto-white balance after
     * {@link android.hardware.Camera#open()} but before the first call to {@link android.hardware.Camera#startPreview()} will not allow the
     * auto-white balance routine to run at all, and may result in severely incorrect color in
     * captured images.
     * </p>
     *
     * @param toggle new state of the auto-white balance lock. True means
     * that auto-white balance is locked, false means that the
     * auto-white balance routine is free to run normally.
     *
     * @see #getAutoWhiteBalanceLock()
     * @see #setWhiteBalance(String)
     */
    public void setAutoWhiteBalanceLock(boolean toggle){
    }

    /**
     * Gets the state of the auto-white balance lock. Applications should
     * check {@link #isAutoWhiteBalanceLockSupported} before using this
     * method. See {@link #setAutoWhiteBalanceLock} for details about the
     * lock.
     *
     * @return State of the auto-white balance lock. Returns true if
     * auto-white balance is currently locked, and false
     * otherwise.
     *
     * @see #setAutoWhiteBalanceLock(boolean)
     */
    public boolean getAutoWhiteBalanceLock(){
        return false;
    }

    /**
     * Returns true if auto-white balance locking is supported. Applications
     * should call this before trying to lock auto-white balance. See
     * {@link #setAutoWhiteBalanceLock} for details about the lock.
     *
     * @return true if auto-white balance lock is supported.
     *
     * @see #setAutoWhiteBalanceLock(boolean)
     */
    public boolean isAutoWhiteBalanceLockSupported(){
        return false;
    }

    /**
     * Gets current zoom value. This also works when smooth zoom is in
     * progress. Applications should check {@link #isZoomSupported} before
     * using this method.
     *
     * @return the current zoom value. The range is 0 to {@link #getMaxZoom}. 0 means the camera is
     * not zoomed.
     */
    public int getZoom(){
        return 0;
    }

    /**
     * Sets current zoom value. If the camera is zoomed (value > 0), the
     * actual picture size may be smaller than picture size setting.
     * Applications can check the actual picture size after picture is
     * returned from {@link android.hardware.Camera.PictureCallback}. The preview size remains the
     * same in zoom. Applications should check {@link #isZoomSupported} before using this method.
     *
     * @param value zoom value. The valid range is 0 to {@link #getMaxZoom}.
     */
    public void setZoom(int value){
    }

    /**
     * Returns true if zoom is supported. Applications should call this
     * before using other zoom methods.
     *
     * @return true if zoom is supported.
     */
    public boolean isZoomSupported(){
        return false;
    }

    /**
     * Gets the maximum zoom value allowed for snapshot. This is the maximum
     * value that applications can set to {@link #setZoom(int)}.
     * Applications should call {@link #isZoomSupported} before using this
     * method. This value may change in different preview size. Applications
     * should call this again after setting preview size.
     *
     * @return the maximum zoom value supported by the camera.
     */
    public int getMaxZoom(){
        return 0;
    }

    /**
     * Gets the zoom ratios of all zoom values. Applications should check {@link #isZoomSupported}
     * before using this method.
     *
     * @return the zoom ratios in 1/100 increments. Ex: a zoom of 3.2x is
     * returned as 320. The number of elements is {@link #getMaxZoom} + 1. The list is
     * sorted from small to large. The
     * first element is always 100. The last element is the zoom
     * ratio of the maximum zoom value.
     */
    public List<Integer> getZoomRatios(){
        return null;
    }

    /**
     * Returns true if smooth zoom is supported. Applications should call
     * this before using other smooth zoom methods.
     *
     * @return true if smooth zoom is supported.
     */
    public boolean isSmoothZoomSupported(){
        return false;
    }

    /**
     * <p>
     * Gets the distances from the camera to where an object appears to be in focus. The object is
     * sharpest at the optimal focus distance. The depth of field is the far focus distance minus
     * near focus distance.
     * </p>
     * <p/>
     * <p>
     * Focus distances may change after calling {@link android.hardware.Camera#autoFocus(android.hardware.Camera.AutoFocusCallback)},
     * {@link android.hardware.Camera#cancelAutoFocus}, or {@link android.hardware.Camera#startPreview()}. Applications can call
     * {@link android.hardware.Camera#getParameters()} and this method anytime to get the latest focus distances. If the
     * focus mode is FOCUS_MODE_CONTINUOUS_VIDEO, focus distances may change from time to time.
     * </p>
     * <p/>
     * <p>
     * This method is intended to estimate the distance between the camera and the subject. After
     * autofocus, the subject distance may be within near and far focus distance. However, the
     * precision depends on the camera hardware, autofocus algorithm, the focus area, and the scene.
     * The error can be large and it should be only used as a reference.
     * </p>
     * <p/>
     * <p>
     * Far focus distance >= optimal focus distance >= near focus distance. If the focus distance is
     * infinity, the value will be {@code Float.POSITIVE_INFINITY}.
     * </p>
     *
     * @param output focus distances in meters. output must be a float
     * array with three elements. Near focus distance, optimal focus
     * distance, and far focus distance will be filled in the array.
     *
     * @see android.hardware.Camera.Parameters#FOCUS_DISTANCE_NEAR_INDEX
     * @see android.hardware.Camera.Parameters#FOCUS_DISTANCE_OPTIMAL_INDEX
     * @see android.hardware.Camera.Parameters#FOCUS_DISTANCE_FAR_INDEX
     */
    public void getFocusDistances(float[] output){
    }

    /**
     * Gets the maximum number of focus areas supported. This is the maximum
     * length of the list in {@link #setFocusAreas(java.util.List)} and {@link #getFocusAreas()}.
     *
     * @return the maximum number of focus areas supported by the camera.
     *
     * @see #getFocusAreas()
     */
    public int getMaxNumFocusAreas(){
        return 0;
    }

    /**
     * <p>
     * Gets the current focus areas. Camera driver uses the areas to decide focus.
     * </p>
     * <p/>
     * <p>
     * Before using this API or {@link #setFocusAreas(java.util.List)}, apps should call
     * {@link #getMaxNumFocusAreas()} to know the maximum number of focus areas first. If the value
     * is 0, focus area is not supported.
     * </p>
     * <p/>
     * <p>
     * Each focus area is a rectangle with specified weight. The direction is relative to the sensor
     * orientation, that is, what the sensor sees. The direction is not affected by the rotation or
     * mirroring of {@link android.hardware.Camera#setDisplayOrientation(int)}. Coordinates of the rectangle range from
     * -1000 to 1000. (-1000, -1000) is the upper left point. (1000, 1000) is the lower right point.
     * The width and height of focus areas cannot be 0 or negative.
     * </p>
     * <p/>
     * <p>
     * The weight must range from 1 to 1000. The weight should be interpreted as a per-pixel weight
     * - all pixels in the area have the specified weight. This means a small area with the same
     * weight as a larger area will have less influence on the focusing than the larger area. Focus
     * areas can partially overlap and the driver will add the weights in the overlap region.
     * </p>
     * <p/>
     * <p>
     * A special case of a {@code null} focus area list means the driver is free to select focus
     * targets as it wants. For example, the driver may use more signals to select focus areas and
     * change them dynamically. Apps can set the focus area list to {@code null} if they want the
     * driver to completely control focusing.
     * </p>
     * <p/>
     * <p>
     * Focus areas are relative to the current field of view ({@link #getZoom()}). No matter what
     * the zoom level is, (-1000,-1000) represents the top of the currently visible camera frame.
     * The focus area cannot be set to be outside the current field of view, even when using zoom.
     * </p>
     * <p/>
     * <p>
     * Focus area only has effect if the current focus mode is {@link android.hardware.Camera.Parameters#FOCUS_MODE_AUTO},
     * {@link android.hardware.Camera.Parameters#FOCUS_MODE_MACRO}, {@link android.hardware.Camera.Parameters#FOCUS_MODE_CONTINUOUS_VIDEO}, or
     * {@link android.hardware.Camera.Parameters#FOCUS_MODE_CONTINUOUS_PICTURE}.
     * </p>
     *
     * @return a list of current focus areas
     */
    public List<Area> getFocusAreas(){
        return null;
    }

    /**
     * Sets focus areas. See {@link #getFocusAreas()} for documentation.
     *
     * @param focusAreas the focus areas
     *
     * @see #getFocusAreas()
     */
    public void setFocusAreas(List<Area> focusAreas){
    }

    /**
     * Gets the maximum number of metering areas supported. This is the
     * maximum length of the list in {@link #setMeteringAreas(java.util.List)} and {@link #getMeteringAreas()}
     * .
     *
     * @return the maximum number of metering areas supported by the camera.
     *
     * @see #getMeteringAreas()
     */
    public int getMaxNumMeteringAreas(){
        return 0;
    }

    /**
     * <p>
     * Gets the current metering areas. Camera driver uses these areas to decide exposure.
     * </p>
     * <p/>
     * <p>
     * Before using this API or {@link #setMeteringAreas(java.util.List)}, apps should call
     * {@link #getMaxNumMeteringAreas()} to know the maximum number of metering areas first. If the
     * value is 0, metering area is not supported.
     * </p>
     * <p/>
     * <p>
     * Each metering area is a rectangle with specified weight. The direction is relative to the
     * sensor orientation, that is, what the sensor sees. The direction is not affected by the
     * rotation or mirroring of {@link android.hardware.Camera#setDisplayOrientation(int)}. Coordinates of the rectangle
     * range from -1000 to 1000. (-1000, -1000) is the upper left point. (1000, 1000) is the lower
     * right point. The width and height of metering areas cannot be 0 or negative.
     * </p>
     * <p/>
     * <p>
     * The weight must range from 1 to 1000, and represents a weight for every pixel in the area.
     * This means that a large metering area with the same weight as a smaller area will have more
     * effect in the metering result. Metering areas can partially overlap and the driver will add
     * the weights in the overlap region.
     * </p>
     * <p/>
     * <p>
     * A special case of a {@code null} metering area list means the driver is free to meter as it
     * chooses. For example, the driver may use more signals to select metering areas and change
     * them dynamically. Apps can set the metering area list to {@code null} if they want the driver
     * to completely control metering.
     * </p>
     * <p/>
     * <p>
     * Metering areas are relative to the current field of view ({@link #getZoom()}). No matter what
     * the zoom level is, (-1000,-1000) represents the top of the currently visible camera frame.
     * The metering area cannot be set to be outside the current field of view, even when using
     * zoom.
     * </p>
     * <p/>
     * <p>
     * No matter what metering areas are, the final exposure are compensated by
     * {@link #setExposureCompensation(int)}.
     * </p>
     *
     * @return a list of current metering areas
     */
    public List<Area> getMeteringAreas(){
        return null;
    }

    /**
     * Sets metering areas. See {@link #getMeteringAreas()} for
     * documentation.
     *
     * @param meteringAreas the metering areas
     *
     * @see #getMeteringAreas()
     */
    public void setMeteringAreas(List<Area> meteringAreas){
    }

    /**
     * Gets the maximum number of detected faces supported. This is the
     * maximum length of the list returned from {@link android.hardware.Camera.FaceDetectionListener}.
     * If the return value is 0, face detection of the specified type is not
     * supported.
     *
     * @return the maximum number of detected face supported by the camera.
     *
     * @see android.hardware.Camera#startFaceDetection()
     */
    public int getMaxNumDetectedFaces(){
        return 0;
    }

    /**
     * Sets recording mode hint. This tells the camera that the intent of
     * the application is to record videos {@link android.media.MediaRecorder#start()}, not to take
     * still pictures
     * {@link android.hardware.Camera#takePicture(android.hardware.Camera.ShutterCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback)}
     * . Using this hint can
     * allow MediaRecorder.start() to start faster or with fewer glitches on
     * output. This should be called before starting preview for the best
     * result, but can be changed while the preview is active. The default
     * value is false.
     * <p/>
     * The app can still call takePicture() when the hint is true or call
     * MediaRecorder.start() when the hint is false. But the performance may
     * be worse.
     *
     * @param hint true if the apps intend to record videos using
     * {@link android.media.MediaRecorder}.
     */
    public void setRecordingHint(boolean hint){
    }

    /**
     * <p>
     * Returns true if video snapshot is supported. That is, applications can call
     * {@link android.hardware.Camera#takePicture(android.hardware.Camera.ShutterCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback)}
     * during recording. Applications do not need to call {@link android.hardware.Camera#startPreview()} after taking a
     * picture. The preview will be still active. Other than that, taking a picture during recording
     * is identical to taking a picture normally. All settings and methods related to takePicture
     * work identically. Ex: {@link #getPictureSize()}, {@link #getSupportedPictureSizes()},
     * {@link #setJpegQuality(int)}, {@link #setRotation(int)}, and etc. The picture will have an
     * EXIF header. {@link android.hardware.Camera.Parameters#FLASH_MODE_AUTO} and {@link android.hardware.Camera.Parameters#FLASH_MODE_ON} also still work, but the
     * video will record the flash.
     * </p>
     * <p/>
     * <p>
     * Applications can set shutter callback as null to avoid the shutter sound. It is also
     * recommended to set raw picture and post view callbacks to null to avoid the interrupt of
     * preview display.
     * </p>
     * <p/>
     * <p>
     * Field-of-view of the recorded video may be different from that of the captured pictures. The
     * maximum size of a video snapshot may be smaller than that for regular still captures. If the
     * current picture size is set higher than can be supported by video snapshot, the picture will
     * be captured at the maximum supported size instead.
     * </p>
     *
     * @return true if video snapshot is supported.
     */
    public boolean isVideoSnapshotSupported(){
        return false;
    }

    /**
     * <p>
     * Enables and disables video stabilization. Use {@link #isVideoStabilizationSupported} to
     * determine if calling this method is valid.
     * </p>
     * <p/>
     * <p>
     * Video stabilization reduces the shaking due to the motion of the camera in both the preview
     * stream and in recorded videos, including data received from the preview callback. It does not
     * reduce motion blur in images captured with {@link android.hardware.Camera#takePicture takePicture}.
     * </p>
     * <p/>
     * <p>
     * Video stabilization can be enabled and disabled while preview or recording is active, but
     * toggling it may cause a jump in the video stream that may be undesirable in a recorded video.
     * </p>
     *
     * @param toggle Set to true to enable video stabilization, and false to
     * disable video stabilization.
     *
     * @see #isVideoStabilizationSupported()
     * @see #getVideoStabilization()
     */
    public void setVideoStabilization(boolean toggle){
    }

    /**
     * Get the current state of video stabilization. See {@link #setVideoStabilization} for details
     * of video stabilization.
     *
     * @return true if video stabilization is enabled
     *
     * @see #isVideoStabilizationSupported()
     * @see #setVideoStabilization(boolean)
     */
    public boolean getVideoStabilization(){
        return false;
    }

    /**
     * Returns true if video stabilization is supported. See {@link #setVideoStabilization} for
     * details of video stabilization.
     *
     * @return true if video stabilization is supported
     *
     * @see #setVideoStabilization(boolean)
     * @see #getVideoStabilization()
     */
    public boolean isVideoStabilizationSupported(){
        return false;
    }
}
