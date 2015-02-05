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

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

public class CameraPreview extends FrameLayout{

    private SurfaceView surfaceView;
    private CameraSize surfaceSize;
    private CameraSizePolicy policy;
    private CameraHelper cameraHelper;

    public CameraPreview(Context context){
        super(context);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context){
        LogUtils.v();
        setClipChildren(false);
        setClipToPadding(false);
        setKeepScreenOn(true);
        surfaceView = new SurfaceView(context);
        addView(surfaceView);
    }

    public SurfaceHolder getHolder(){
        LogUtils.v();
        return surfaceView.getHolder();
    }

    public void surfaceChanged(CameraHelper cameraHelper, CameraSizePolicy policy){
        this.cameraHelper = cameraHelper;
        this.surfaceSize = cameraHelper.getSurfaceSize(policy);
        this.policy = policy;
        LogUtils.v("surfaceSize.width: " + surfaceSize.width);
        LogUtils.v("surfaceSize.height: " + surfaceSize.height);
        LogUtils.v("policy: " + policy);
        requestLayout();
        getHolder().setFixedSize(surfaceSize.width, surfaceSize.height);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        LogUtils.v();
        if(surfaceSize != null){
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(surfaceSize.width, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                surfaceSize.height, MeasureSpec.EXACTLY
            );
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
        LogUtils.v("changed: " + changed);
        if(cameraHelper == null || surfaceSize == null){
            final View child = getChildAt(0);
            final int previewWidth = r - l;
            final int previewHeight = b - t;
            child.layout(0, 0, previewWidth, previewHeight);
            return;
        }
        if(changed && getChildCount() > 0){
            final View child = getChildAt(0);
            switch(policy){
                case SQUARE:{
                    CameraSize previewSize = cameraHelper.getSurfaceSize(CameraSizePolicy.INSIDE);
                    final int previewWidth = previewSize.width;
                    final int previewHeight = previewSize.height;
                    LogUtils.d("previewWidth: " + previewWidth);
                    LogUtils.d("previewHeight: " + previewHeight);
                    int paddingWidth = (surfaceSize.width - previewWidth) / 2;
                    int paddingHeight = (surfaceSize.height - previewHeight) / 2;
                    child.layout(
                        paddingWidth,
                        paddingHeight,
                        previewWidth + paddingWidth,
                        previewHeight + paddingHeight
                    );
                }
                break;
                default:{
                    final int previewWidth = surfaceSize.width;
                    final int previewHeight = surfaceSize.height;
                    LogUtils.d("previewWidth: " + previewWidth);
                    LogUtils.d("previewHeight: " + previewHeight);
                    child.layout(0, 0, previewWidth, previewHeight);
                }
                break;
            }
        }
    }
}
