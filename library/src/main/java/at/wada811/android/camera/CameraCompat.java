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

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.AutoFocusMoveCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.OnZoomChangeListener;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;
import android.view.SurfaceHolder;
import java.io.IOException;

public abstract class CameraCompat{

    public static String TAG = CameraCompat.class.getSimpleName();

    Camera camera;

    public static CameraCompat newInstance(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            return new CameraCompatJellyBeanMR1();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            return new CameraCompatJellyBean();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            return new CameraCompatIceCreamSandwich();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            return new CameraCompatHoneycomb();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return new CameraCompatGingerbread();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO){
            return new CameraCompatFroyo();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR){
            return new CameraCompatEclair();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE){
            return new CameraCompatCupcake();
        }else{
            return new CameraCompatBase();
        }
    }

    public Camera getCamera(){
        return camera;
    }

    /**
     * Returns the number of physical cameras available on this device.
     */
    public int getNumberOfCameras(){
        return 0;
    }

    /**
     * Returns the information about a particular camera.
     * If {@link #getNumberOfCameras()} returns N, the valid id is 0 to N-1.
     */
    public void getCameraInfo(int cameraId, CameraInfo cameraInfo){

    }

    /**
     * Creates a new Camera object to access a particular hardware camera. If
     * the same camera is opened by other applications, this will throw a
     * RuntimeException.
     * <p/>
     * <p/>
     * You must call {@link #release()} when you are done using the camera, otherwise it will remain
     * locked and be unavailable to other applications.
     * <p/>
     * <p/>
     * Your application should only have one Camera object active at a time for a particular
     * hardware camera.
     * <p/>
     * <p/>
     * Callbacks from other methods are delivered to the event loop of the thread which called
     * open(). If this thread has no event loop, then callbacks are delivered to the main
     * application event loop. If there is no main application event loop, callbacks are not
     * delivered.
     * <p/>
     * <p class="caution">
     * <b>Caution:</b> On some devices, this method may take a long time to complete. It is best to
     * call this method from a worker thread (possibly using {@link android.os.AsyncTask}) to avoid
     * blocking the main application UI thread.
     *
     * @param cameraId the hardware camera to access, between 0 and {@link #getNumberOfCameras()}-1.
     *
     * @return a new Camera object, connected, locked and ready for use.
     *
     * @throws RuntimeException if opening the camera fails (for example, if the
     * camera is in use by another process or device policy manager has
     * disabled the camera).
     * @see android.app.admin.DevicePolicyManager#getCameraDisabled(android.content.ComponentName)
     */
    public Camera open(int cameraId){
        return null;
    }

    /**
     * Creates a new Camera object to access the first back-facing camera on the
     * device. If the device does not have a back-facing camera, this returns
     * null.
     *
     * @see #open(int)
     */
    public Camera open(){
        return null;
    }

    /**
     * Disconnects and releases the Camera object resources.
     * <p/>
     * <p>
     * You must call this as soon as you're done with the Camera object.
     * </p>
     */
    public void release(){
    }

    /**
     * Unlocks the camera to allow another process to access it.
     * Normally, the camera is locked to the process with an active Camera
     * object until {@link #release()} is called. To allow rapid handoff
     * between processes, you can call this method to release the camera
     * temporarily for another process to use; once the other process is done
     * you can call {@link #reconnect()} to reclaim the camera.
     * <p/>
     * <p/>
     * This must be done before calling {@link android.media.MediaRecorder#setCamera(android.hardware.Camera)}. This
     * cannot be called after recording starts.
     * <p/>
     * <p/>
     * If you are not recording video, you probably do not need this method.
     *
     * @throws RuntimeException if the camera cannot be unlocked.
     */
    public void unlock(){

    }

    ;

    /**
     * Re-locks the camera to prevent other processes from accessing it.
     * Camera objects are locked by default unless {@link #unlock()} is
     * called. Normally {@link #reconnect()} is used instead.
     * <p/>
     * <p/>
     * Since API level 14, camera is automatically locked for applications in
     * {@link android.media.MediaRecorder#start()}. Applications can use the camera (ex: zoom) after
     * recording starts. There is no need to call this after recording starts or stops.
     * <p/>
     * <p/>
     * If you are not recording video, you probably do not need this method.
     *
     * @throws RuntimeException if the camera cannot be re-locked (for
     * example, if the camera is still in use by another process).
     */
    public void lock(){

    }

    ;

    /**
     * Reconnects to the camera service after another process used it.
     * After {@link #unlock()} is called, another process may use the
     * camera; when the process is done, you must reconnect to the camera,
     * which will re-acquire the lock and allow you to continue using the
     * camera.
     * <p/>
     * <p/>
     * Since API level 14, camera is automatically locked for applications in
     * {@link android.media.MediaRecorder#start()}. Applications can use the camera (ex: zoom) after
     * recording starts. There is no need to call this after recording starts or stops.
     * <p/>
     * <p/>
     * If you are not recording video, you probably do not need this method.
     *
     * @throws java.io.IOException if a connection cannot be re-established (for
     * example, if the camera is still in use by another process).
     */
    public void reconnect() throws IOException{

    }

    ;

    /**
     * Sets the {@link android.view.Surface} to be used for live preview.
     * Either a surface or surface texture is necessary for preview, and
     * preview is necessary to take pictures. The same surface can be re-set
     * without harm. Setting a preview surface will un-set any preview surface
     * texture that was set via {@link #setPreviewTexture}.
     * <p/>
     * <p/>
     * The {@link android.view.SurfaceHolder} must already contain a surface when this method is called. If you
     * are using {@link android.view.SurfaceView}, you will need to register a
     * {@link android.view.SurfaceHolder.Callback} with {@link android.view.SurfaceHolder#addCallback(android.view.SurfaceHolder.Callback)}
     * and wait for {@link android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)} before calling
     * setPreviewDisplay() or starting preview.
     * <p/>
     * <p/>
     * This method must be called before {@link #startPreview()}. The one exception is that if the
     * preview surface is not set (or set to null) before startPreview() is called, then this method
     * may be called once with a non-null parameter to set the preview surface. (This allows camera
     * setup and surface creation to happen in parallel, saving time.) The preview surface may not
     * otherwise change while preview is running.
     *
     * @param holder containing the Surface on which to place the preview,
     * or null to remove the preview surface
     *
     * @throws java.io.IOException if the method fails (for example, if the surface
     * is unavailable or unsuitable).
     */
    public void setPreviewDisplay(SurfaceHolder holder) throws IOException{
    }

    /**
     * Sets the {@link android.graphics.SurfaceTexture} to be used for live preview.
     * Either a surface or surface texture is necessary for preview, and
     * preview is necessary to take pictures. The same surface texture can be
     * re-set without harm. Setting a preview surface texture will un-set any
     * preview surface that was set via {@link #setPreviewDisplay}.
     * <p/>
     * <p>
     * This method must be called before {@link #startPreview()}. The one exception is that if the
     * preview surface texture is not set (or set to null) before startPreview() is called, then
     * this method may be called once with a non-null parameter to set the preview surface. (This
     * allows camera setup and surface creation to happen in parallel, saving time.) The preview
     * surface texture may not otherwise change while preview is running.
     * <p/>
     * <p>
     * The timestamps provided by {@link android.graphics.SurfaceTexture#getTimestamp()} for a SurfaceTexture set as
     * the preview texture have an unspecified zero point, and cannot be directly compared between
     * different cameras or different instances of the same camera, or across multiple runs of the
     * same program.
     * <p/>
     * <p>
     * If you are using the preview data to create video or still images, strongly consider using
     * {@link android.media.MediaActionSound} to properly indicate image capture or recording
     * start/stop to the user.
     * </p>
     *
     * @param surfaceTexture the {@link android.graphics.SurfaceTexture} to which the preview
     * images are to be sent or null to remove the current preview surface
     * texture
     *
     * @throws java.io.IOException if the method fails (for example, if the surface
     * texture is unavailable or unsuitable).
     * @see android.media.MediaActionSound
     * @see android.graphics.SurfaceTexture
     * @see android.view.TextureView
     */
    public void setPreviewTexture(SurfaceTexture surfaceTexture) throws IOException{

    }

    ;

    /**
     * Starts capturing and drawing preview frames to the screen.
     * Preview will not actually start until a surface is supplied
     * with {@link #setPreviewDisplay(android.view.SurfaceHolder)} or {@link #setPreviewTexture(android.graphics.SurfaceTexture)}.
     * <p/>
     * <p/>
     * If {@link #setPreviewCallback(android.hardware.Camera.PreviewCallback)},
     * {@link #setOneShotPreviewCallback(android.hardware.Camera.PreviewCallback)}, or
     * {@link #setPreviewCallbackWithBuffer(android.hardware.Camera.PreviewCallback)} were called,
     * {@link android.hardware.Camera.PreviewCallback#onPreviewFrame(byte[], android.hardware.Camera)} will be called when preview
     * data becomes available.
     */
    public void startPreview(){

    }

    ;

    /**
     * Stops capturing and drawing preview frames to the surface, and
     * resets the camera for a future call to {@link #startPreview()}.
     */
    public void stopPreview(){

    }

    /**
     * <p>
     * Installs a callback to be invoked for every preview frame in addition to displaying them on
     * the screen. The callback will be repeatedly called for as long as preview is active. This
     * method can be called at any time, even while preview is live. Any other preview callbacks are
     * overridden.
     * </p>
     * <p/>
     * <p>
     * If you are using the preview data to create video or still images, strongly consider using
     * {@link android.media.MediaActionSound} to properly indicate image capture or recording
     * start/stop to the user.
     * </p>
     *
     * @param cb a callback object that receives a copy of each preview frame,
     * or null to stop receiving callbacks.
     *
     * @see android.media.MediaActionSound
     */
    public void setPreviewCallback(PreviewCallback cb){

    }

    /**
     * <p>
     * Installs a callback to be invoked for the next preview frame in addition to displaying it on
     * the screen. After one invocation, the callback is cleared. This method can be called any
     * time, even when preview is live. Any other preview callbacks are overridden.
     * </p>
     * <p/>
     * <p>
     * If you are using the preview data to create video or still images, strongly consider using
     * {@link android.media.MediaActionSound} to properly indicate image capture or recording
     * start/stop to the user.
     * </p>
     *
     * @param cb a callback object that receives a copy of the next preview frame,
     * or null to stop receiving callbacks.
     *
     * @see android.media.MediaActionSound
     */
    public void setOneShotPreviewCallback(PreviewCallback cb){

    }

    /**
     * <p>
     * Installs a callback to be invoked for every preview frame, using buffers supplied with
     * {@link #addCallbackBuffer(byte[])}, in addition to displaying them on the screen. The
     * callback will be repeatedly called for as long as preview is active and buffers are
     * available. Any other preview callbacks are overridden.
     * </p>
     * <p/>
     * <p>
     * The purpose of this method is to improve preview efficiency and frame rate by allowing
     * preview frame memory reuse. You must call {@link #addCallbackBuffer(byte[])} at some point --
     * before or after calling this method -- or no callbacks will received.
     * </p>
     * <p/>
     * <p>
     * The buffer queue will be cleared if this method is called with a null callback,
     * {@link #setPreviewCallback(android.hardware.Camera.PreviewCallback)} is called, or
     * {@link #setOneShotPreviewCallback(android.hardware.Camera.PreviewCallback)} is called.
     * </p>
     * <p/>
     * <p>
     * If you are using the preview data to create video or still images, strongly consider using
     * {@link android.media.MediaActionSound} to properly indicate image capture or recording
     * start/stop to the user.
     * </p>
     *
     * @param cb a callback object that receives a copy of the preview frame,
     * or null to stop receiving callbacks and clear the buffer queue.
     *
     * @see #addCallbackBuffer(byte[])
     * @see android.media.MediaActionSound
     */
    public void setPreviewCallbackWithBuffer(PreviewCallback cb){

    }

    /**
     * Adds a pre-allocated buffer to the preview callback buffer queue.
     * Applications can add one or more buffers to the queue. When a preview
     * frame arrives and there is still at least one available buffer, the
     * buffer will be used and removed from the queue. Then preview callback is
     * invoked with the buffer. If a frame arrives and there is no buffer left,
     * the frame is discarded. Applications should add buffers back when they
     * finish processing the data in them.
     * <p/>
     * <p/>
     * For formats besides YV12, the size of the buffer is determined by multiplying the preview
     * image width, height, and bytes per pixel. The width and height can be read from
     * {@link android.hardware.Camera.Parameters#getPreviewSize()}. Bytes per pixel can be computed from
     * {@link android.graphics.ImageFormat#getBitsPerPixel(int)} / 8, using the image format from
     * {@link android.hardware.Camera.Parameters#getPreviewFormat()}.
     * <p/>
     * <p/>
     * If using the {@link android.graphics.ImageFormat#YV12} format, the size can be calculated
     * using the equations listed in {@link android.hardware.Camera.Parameters#setPreviewFormat}.
     * <p/>
     * <p/>
     * This method is only necessary when {@link #setPreviewCallbackWithBuffer(android.hardware.Camera.PreviewCallback)} is
     * used. When {@link #setPreviewCallback(android.hardware.Camera.PreviewCallback)} or
     * {@link #setOneShotPreviewCallback(android.hardware.Camera.PreviewCallback)} are used, buffers are automatically
     * allocated. When a supplied buffer is too small to hold the preview frame data, preview
     * callback will return null and the buffer will be removed from the buffer queue.
     *
     * @param callbackBuffer the buffer to add to the queue. The size of the
     * buffer must match the values described above.
     *
     * @see #setPreviewCallbackWithBuffer(android.hardware.Camera.PreviewCallback)
     */
    public void addCallbackBuffer(byte[] callbackBuffer){
    }

    /**
     * Starts camera auto-focus and registers a callback function to run when
     * the camera is focused. This method is only valid when preview is active
     * (between {@link #startPreview()} and before {@link #stopPreview()}).
     * <p/>
     * <p>
     * Callers should check {@link android.hardware.Camera.Parameters#getFocusMode()} to determine
     * if this method should be called. If the camera does not support auto-focus, it is a no-op and
     * {@link android.hardware.Camera.AutoFocusCallback#onAutoFocus(boolean, android.hardware.Camera)} callback will be called immediately.
     * <p/>
     * <p>
     * If your application should not be installed on devices without auto-focus, you must declare
     * that your application uses auto-focus with the <a href="
     * guide/topics/manifest/uses-feature-element.html">&lt;uses-feature&gt;</a> manifest element.
     * </p>
     * <p/>
     * <p>
     * If the current flash mode is not {@link android.hardware.Camera.Parameters#FLASH_MODE_OFF},
     * flash may be fired during auto-focus, depending on the driver and camera hardware.
     * <p>
     * <p/>
     * <p>
     * Auto-exposure lock {@link android.hardware.Camera.Parameters#getAutoExposureLock()} and
     * auto-white balance locks {@link android.hardware.Camera.Parameters#getAutoWhiteBalanceLock()}
     * do not change during and after autofocus. But auto-focus routine may stop auto-exposure and
     * auto-white balance transiently during focusing.
     * <p/>
     * <p>
     * Stopping preview with {@link #stopPreview()}, or triggering still image capture with
     * {@link #takePicture(android.hardware.Camera.ShutterCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback)},
     * will not change the the focus position. Applications must call cancelAutoFocus to reset the
     * focus.
     * </p>
     * <p/>
     * <p>
     * If autofocus is successful, consider using {@link android.media.MediaActionSound} to properly
     * play back an autofocus success sound to the user.
     * </p>
     *
     * @param cb the callback to run
     *
     * @see #cancelAutoFocus()
     * @see android.hardware.Camera.Parameters#setAutoExposureLock(boolean)
     * @see android.hardware.Camera.Parameters#setAutoWhiteBalanceLock(boolean)
     * @see android.media.MediaActionSound
     */
    public void autoFocus(AutoFocusCallback cb){
    }

    /**
     * Cancels any auto-focus function in progress.
     * Whether or not auto-focus is currently in progress,
     * this function will return the focus position to the default.
     * If the camera does not support auto-focus, this is a no-op.
     *
     * @see #autoFocus(android.hardware.Camera.AutoFocusCallback)
     */
    public void cancelAutoFocus(){
    }

    /**
     * Sets camera auto-focus move callback.
     *
     * @param cb the callback to run
     */
    public void setAutoFocusMoveCallback(AutoFocusMoveCallback cb){
    }

    /**
     * Equivalent to takePicture(shutter, raw, null, jpeg).
     *
     * @see #takePicture(android.hardware.Camera.ShutterCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback)
     */
    public void takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback jpeg){
    }

    /**
     * Triggers an asynchronous image capture. The camera service will initiate
     * a series of callbacks to the application as the image capture progresses.
     * The shutter callback occurs after the image is captured. This can be used
     * to trigger a sound to let the user know that image has been captured. The
     * raw callback occurs when the raw image data is available (NOTE: the data
     * will be null if there is no raw image callback buffer available or the
     * raw image callback buffer is not large enough to hold the raw image).
     * The postview callback occurs when a scaled, fully processed postview
     * image is available (NOTE: not all hardware supports this). The jpeg
     * callback occurs when the compressed image is available. If the
     * application does not need a particular callback, a null can be passed
     * instead of a callback method.
     * <p/>
     * <p/>
     * This method is only valid when preview is active (after {@link #startPreview()}). Preview
     * will be stopped after the image is taken; callers must call {@link #startPreview()} again if
     * they want to re-start preview or take more pictures. This should not be called between
     * {@link android.media.MediaRecorder#start()} and {@link android.media.MediaRecorder#stop()}.
     * <p/>
     * <p/>
     * After calling this method, you must not call {@link #startPreview()} or take another picture
     * until the JPEG callback has returned.
     *
     * @param shutter the callback for image capture moment, or null
     * @param raw the callback for raw (uncompressed) image data, or null
     * @param postview callback with postview image data, may be null
     * @param jpeg the callback for JPEG image data, or null
     */
    public void takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback postview, PictureCallback jpeg){

    }

    /**
     * Zooms to the requested value smoothly. The driver will notify {@link android.hardware.Camera.OnZoomChangeListener} of
     * the zoom value and whether zoom is stopped at
     * the time. For example, suppose the current zoom is 0 and startSmoothZoom
     * is called with value 3. The
     * {@link android.hardware.Camera.OnZoomChangeListener#onZoomChange(int, boolean, android.hardware.Camera)} method will be called
     * three times with zoom values 1, 2, and 3.
     * Applications can call {@link #stopSmoothZoom} to stop the zoom earlier.
     * Applications should not call startSmoothZoom again or change the zoom
     * value before zoom stops. If the supplied zoom value equals to the current
     * zoom value, no zoom callback will be generated. This method is supported
     * if {@link android.hardware.Camera.Parameters#isSmoothZoomSupported} returns true.
     *
     * @param value zoom value. The valid range is 0 to
     * {@link android.hardware.Camera.Parameters#getMaxZoom}.
     *
     * @throws IllegalArgumentException if the zoom value is invalid.
     * @throws RuntimeException if the method fails.
     * @see #setZoomChangeListener(android.hardware.Camera.OnZoomChangeListener)
     */
    public void startSmoothZoom(int value){

    }

    /**
     * Stops the smooth zoom. Applications should wait for the {@link android.hardware.Camera.OnZoomChangeListener} to know
     * when the zoom is actually stopped. This
     * method is supported if {@link android.hardware.Camera.Parameters#isSmoothZoomSupported} is
     * true.
     *
     * @throws RuntimeException if the method fails.
     */
    public void stopSmoothZoom(){

    }

    /**
     * Set the clockwise rotation of preview display in degrees. This affects
     * the preview frames and the picture displayed after snapshot. This method
     * is useful for portrait mode applications. Note that preview display of
     * front-facing cameras is flipped horizontally before the rotation, that
     * is, the image is reflected along the central vertical axis of the camera
     * sensor. So the users can see themselves as looking into a mirror.
     * <p/>
     * <p/>
     * This does not affect the order of byte array passed in {@link android.hardware.Camera.PreviewCallback#onPreviewFrame}
     * , JPEG pictures, or recorded videos. This method is not allowed to be called during preview.
     * <p/>
     * <p/>
     * If you want to make the camera image show in the same orientation as the display, you can use
     * the following code.
     * <p/>
     * <pre>
     * public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera){
     *     android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
     *     android.hardware.Camera.getCameraInfo(cameraId, info);
     *     int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
     *     int degrees = 0;
     *     switch(rotation){
     *         case Surface.ROTATION_0:
     *             degrees = 0;
     *             break;
     *         case Surface.ROTATION_90:
     *             degrees = 90;
     *             break;
     *         case Surface.ROTATION_180:
     *             degrees = 180;
     *             break;
     *         case Surface.ROTATION_270:
     *             degrees = 270;
     *             break;
     *     }
     *
     *     int result;
     *     if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
     *         result = (info.orientation + degrees) % 360;
     *         result = (360 - result) % 360; // compensate the mirror
     *     }else{ // back-facing
     *         result = (info.orientation - degrees + 360) % 360;
     *     }
     *     camera.setDisplayOrientation(result);
     * }
     * </pre>
     * <p/>
     * <p/>
     * Starting from API level 14, this method can be called when preview is active.
     *
     * @param degrees the angle that the picture will be rotated clockwise.
     * Valid values are 0, 90, 180, and 270. The starting
     * position is 0 (landscape).
     *
     * @see #setPreviewDisplay(android.view.SurfaceHolder)
     */
    public void setDisplayOrientation(int degrees){

    }

    /**
     * <p>
     * Enable or disable the default shutter sound when taking a picture.
     * </p>
     * <p/>
     * <p>
     * By default, the camera plays the system-defined camera shutter sound when
     * {@link #takePicture} is called. Using this method, the shutter sound can be disabled. It is
     * strongly recommended that an alternative shutter sound is played in the
     * {@link android.hardware.Camera.ShutterCallback} when the system shutter sound is disabled.
     * </p>
     * <p/>
     * <p>
     * Note that devices may not always allow disabling the camera shutter sound. If the shutter
     * sound state cannot be set to the desired value, this method will return false.
     * {@link android.hardware.Camera.CameraInfo#canDisableShutterSound} can be used to determine whether the device will
     * allow the shutter sound to be disabled.
     * </p>
     *
     * @param enabled whether the camera should play the system shutter sound
     * when {@link #takePicture takePicture} is called.
     *
     * @return {@code true} if the shutter sound state was successfully
     * changed. {@code false} if the shutter sound state could not be
     * changed. {@code true} is also returned if shutter sound playback
     * is already set to the requested state.
     *
     * @see #takePicture
     * @see android.hardware.Camera.CameraInfo#canDisableShutterSound
     * @see android.hardware.Camera.ShutterCallback
     */
    public boolean enableShutterSound(boolean enabled){
        return false;
    }

    /**
     * Registers a listener to be notified when the zoom value is updated by the
     * camera driver during smooth zoom.
     *
     * @param listener the listener to notify
     *
     * @see #startSmoothZoom(int)
     */
    public void setZoomChangeListener(OnZoomChangeListener listener){
    }

    /**
     * Registers a listener to be notified about the faces detected in the
     * preview frame.
     *
     * @param listener the listener to notify
     *
     * @see #startFaceDetection()
     */
    public void setFaceDetectionListener(FaceDetectionListener listener){
    }

    /**
     * Starts the face detection. This should be called after preview is started.
     * The camera will notify {@link android.hardware.Camera.FaceDetectionListener} of the detected
     * faces in the preview frame. The detected faces may be the same as the
     * previous ones. Applications should call {@link #stopFaceDetection} to
     * stop the face detection. This method is supported if
     * {@link android.hardware.Camera.Parameters#getMaxNumDetectedFaces()} returns a number larger than 0.
     * If the face detection has started, apps should not call this again.
     * <p/>
     * <p>
     * When the face detection is running, {@link android.hardware.Camera.Parameters#setWhiteBalance(String)},
     * {@link android.hardware.Camera.Parameters#setFocusAreas(java.util.List)}, and {@link android.hardware.Camera.Parameters#setMeteringAreas(java.util.List)} have no
     * effect. The camera uses the detected faces to do auto-white balance, auto exposure, and
     * autofocus.
     * <p/>
     * <p>
     * If the apps call {@link #autoFocus(android.hardware.Camera.AutoFocusCallback)}, the camera will stop sending face
     * callbacks. The last face callback indicates the areas used to do autofocus. After focus
     * completes, face detection will resume sending face callbacks. If the apps call
     * {@link #cancelAutoFocus()}, the face callbacks will also resume.
     * </p>
     * <p/>
     * <p>
     * After calling
     * {@link #takePicture(android.hardware.Camera.ShutterCallback, android.hardware.Camera.PictureCallback, android.hardware.Camera.PictureCallback)}
     * or {@link #stopPreview()}, and then resuming preview with {@link #startPreview()}, the apps
     * should call this method again to resume face detection.
     * </p>
     *
     * @throws IllegalArgumentException if the face detection is unsupported.
     * @throws RuntimeException if the method fails or the face detection is
     * already running.
     * @see android.hardware.Camera.FaceDetectionListener
     * @see #stopFaceDetection()
     * @see android.hardware.Camera.Parameters#getMaxNumDetectedFaces()
     */
    public void startFaceDetection(){
    }

    /**
     * Stops the face detection.
     *
     * @see #startFaceDetection()
     */
    public void stopFaceDetection(){
    }

    /**
     * Registers a callback to be invoked when an error occurs.
     *
     * @param cb The callback to run
     */
    public void setErrorCallback(ErrorCallback cb){
    }

    /**
     * Changes the settings for this Camera service.
     *
     * @param params the Parameters to use for this Camera service
     *
     * @throws RuntimeException if any parameter is invalid or not supported.
     * @see #getParameters()
     */
    public void setParameters(CameraParamCompat params){
    }

    /**
     * Returns the current settings for this Camera service.
     * If modifications are made to the returned Parameters, they must be passed
     * to {@link android.hardware.Camera#setParameters(android.hardware.Camera.Parameters)} to take effect.
     *
     * @see android.hardware.Camera#setParameters(android.hardware.Camera.Parameters)
     */
    public CameraParamCompat getParameters(){
        return null;
    }

}
