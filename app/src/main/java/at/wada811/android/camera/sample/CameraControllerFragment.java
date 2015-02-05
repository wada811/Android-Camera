package at.wada811.android.camera.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;
import at.wada811.android.camera.CameraSize;
import at.wada811.android.camera.LogUtils;
import at.wada811.android.dialogfragments.AlertDialogFragment;
import at.wada811.android.dialogfragments.interfaces.DialogFragmentCallback;
import at.wada811.android.dialogfragments.interfaces.DialogFragmentCallbackProvider;
import at.wada811.android.dialogfragments.interfaces.DialogFragmentInterface;
import at.wada811.android.dialogfragments.interfaces.SimpleDialogFragmentCallback;


public class CameraControllerFragment extends Fragment
    implements OnClickListener, DialogFragmentCallbackProvider{

    public static final String TAG = CameraControllerFragment.class.getSimpleName();
    private static final String TAG_CHANGE_PICTURE_SIZE = "Picture Size";
    private static final String TAG_CHANGE_VIDEO_SIZE = "Video Size";
    private CameraControllerCallback callback;

    private ArrayList<View> BUTTONS;
    private RelativeLayout controllerContainer;
    private ImageButton buttonTakePicture;
    private ImageButton buttonTakePreviewPicture;
    private ImageButton buttonTakePictures;
    private ImageButton buttonTakePreviewPictures;
    private ImageButton buttonRecordVideo;
    private ImageButton buttonRecordTimeLapseVideo;

    private ImageButton buttonSwitchCamera;
    private ImageButton buttonChangePictureSize;
    private ImageButton buttonChangeVideoSize;

    public interface CameraControllerCallbackProvider{

        public CameraControllerCallback getCameraControllerCallback();
    }

    public interface CameraControllerCallback{

        public void onClickPreview();

        public void onClickTakePicture();

        public void onClickTakePreviewPicture();

        public void onClickTakePictures();

        public void onClickTakePreviewPictures();

        public void onClickRecordVideo();

        public void onClickRecordTimeLapseVideo();

        public boolean isMultiCameraSupported();

        public void onClickSwitchCamera();

        public boolean isVideoSnapshotSupported();

        public List<CameraSize> getSupportedPictureSize();

        public void onPictureSizeSelected(CameraSize pictureSize);

        public List<CameraSize> getSupportedVideoSize();

        public void onVideoSizeSelected(CameraSize videoSize);

    }

    public static CameraControllerFragment newInstance(){
        CameraControllerFragment fragment = new CameraControllerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        if(activity instanceof CameraControllerCallbackProvider == false){
            throw new ClassCastException(
                activity.getLocalClassName() + " must implements " + CameraControllerCallbackProvider.class
                    .getSimpleName()
            );
        }
        CameraControllerCallbackProvider provider = (CameraControllerCallbackProvider)activity;
        callback = provider.getCameraControllerCallback();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        LogUtils.v();
        View view = inflater.inflate(R.layout.fragment_camera_controller, container, false);
        controllerContainer = (RelativeLayout)view.findViewById(R.id.controllerContainer);
        controllerContainer.setOnClickListener(this);

        buttonTakePicture = (ImageButton)view.findViewById(R.id.buttonTakePicture);
        buttonTakePicture.setOnClickListener(this);
        buttonTakePreviewPicture = (ImageButton)view.findViewById(R.id.buttonTakePreviewPicture);
        buttonTakePreviewPicture.setOnClickListener(this);
        buttonTakePictures = (ImageButton)view.findViewById(R.id.buttonTakePictures);
        buttonTakePictures.setOnClickListener(this);
        buttonTakePreviewPictures = (ImageButton)view.findViewById(R.id.buttonTakePreviewPictures);
        buttonTakePreviewPictures.setOnClickListener(this);
        buttonRecordVideo = (ImageButton)view.findViewById(R.id.buttonRecordVideo);
        buttonRecordVideo.setOnClickListener(this);
        buttonRecordTimeLapseVideo = (ImageButton)view.findViewById(R.id.buttonRecordTimeLapseVideo);
        buttonRecordTimeLapseVideo.setOnClickListener(this);

        buttonSwitchCamera = (ImageButton)view.findViewById(R.id.buttonSwitchCamera);
        if(callback.isMultiCameraSupported()){
            buttonSwitchCamera.setOnClickListener(this);
        }else{
            buttonSwitchCamera.setVisibility(View.GONE);
        }
        buttonChangePictureSize = (ImageButton)view.findViewById(R.id.buttonChangePictureSize);
        buttonChangePictureSize.setOnClickListener(this);
        buttonChangeVideoSize = (ImageButton)view.findViewById(R.id.buttonChangeVideoSize);
        buttonChangeVideoSize.setOnClickListener(this);

        BUTTONS = new ArrayList<View>();
        BUTTONS.add(buttonTakePicture);
        BUTTONS.add(buttonTakePreviewPicture);
        BUTTONS.add(buttonTakePictures);
        BUTTONS.add(buttonTakePreviewPictures);
        BUTTONS.add(buttonRecordVideo);
        BUTTONS.add(buttonRecordTimeLapseVideo);
        BUTTONS.add(buttonSwitchCamera);
        BUTTONS.add(buttonChangePictureSize);
        BUTTONS.add(buttonChangeVideoSize);
        return view;
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.controllerContainer:
                callback.onClickPreview();
                break;
            case R.id.buttonTakePicture:{
                setVisibility(BUTTONS, View.INVISIBLE);
                callback.onClickTakePicture();
            }
            break;
            case R.id.buttonTakePreviewPicture:{
                setVisibility(BUTTONS, View.INVISIBLE);
                callback.onClickTakePreviewPicture();
            }
            break;
            case R.id.buttonTakePictures:{
                setVisibility(BUTTONS, View.INVISIBLE);
                callback.onClickTakePictures();
            }
            break;
            case R.id.buttonTakePreviewPictures:{
                setVisibility(BUTTONS, View.INVISIBLE);
                callback.onClickTakePreviewPictures();
            }
            break;
            case R.id.buttonRecordVideo:{
                ArrayList<View> views = new ArrayList<View>(BUTTONS);
                if(!callback.isVideoSnapshotSupported()){
                    views.remove(buttonTakePicture);
                    views.remove(buttonTakePictures);
                }
                views.remove(buttonRecordVideo);
                setVisibility(views, View.INVISIBLE);
                callback.onClickRecordVideo();
            }
            break;
            case R.id.buttonRecordTimeLapseVideo:{
                ArrayList<View> views = new ArrayList<View>(BUTTONS);
                if(!callback.isVideoSnapshotSupported()){
                    views.remove(buttonTakePicture);
                    views.remove(buttonTakePictures);
                }
                views.remove(buttonRecordVideo);
                setVisibility(views, View.INVISIBLE);
                callback.onClickRecordTimeLapseVideo();
            }
            break;
            case R.id.buttonSwitchCamera:{
                callback.onClickSwitchCamera();
            }
            break;
            case R.id.buttonChangePictureSize:{
                List<CameraSize> supportedPictureSize = callback.getSupportedPictureSize();
                String[] items = new String[supportedPictureSize.size()];
                int index = 0;
                for(CameraSize cameraSize : supportedPictureSize){
                    items[index++] = cameraSize.toString();
                }
                new AlertDialogFragment.Builder(getActivity(), AlertDialogFragment.THEME_HOLO_LIGHT)
                    .setTitle(TAG_CHANGE_PICTURE_SIZE)
                    .setItems(items)
                    .show(getChildFragmentManager(), TAG_CHANGE_PICTURE_SIZE);
            }
            break;
            case R.id.buttonChangeVideoSize:{
                List<CameraSize> supportedVideoSize = callback.getSupportedVideoSize();
                String[] items = new String[supportedVideoSize.size()];
                int index = 0;
                for(CameraSize cameraSize : supportedVideoSize){
                    items[index++] = cameraSize.toString();
                }
                new AlertDialogFragment.Builder(getActivity(), AlertDialogFragment.THEME_HOLO_LIGHT)
                    .setTitle(TAG_CHANGE_PICTURE_SIZE)
                    .setItems(items)
                    .show(getChildFragmentManager(), TAG_CHANGE_PICTURE_SIZE);
            }
            break;
        }
    }

    public void onPictureTaken(){
        setVisibility(BUTTONS, View.VISIBLE);
    }

    public void onTakePictureError(){
        setVisibility(BUTTONS, View.VISIBLE);
    }

    private void setVisibility(List<View> targets, int visibility){
        for(View target : targets){
            if(target.getVisibility() != View.GONE){
                target.setVisibility(visibility);
            }
        }
    }

    @Override
    public DialogFragmentCallback getDialogFragmentCallback(){
        return new SimpleDialogFragmentCallback(){
            @Override
            public void onItemClick(DialogFragmentInterface dialog, int position){
                String tag = dialog.getTag();
                if(tag.equals(TAG_CHANGE_PICTURE_SIZE)){
                    List<CameraSize> supportedPictureSize = callback.getSupportedPictureSize();
                    CameraSize pictureSize = supportedPictureSize.get(position);
                    callback.onPictureSizeSelected(pictureSize);
                }else if(tag.equals(TAG_CHANGE_PICTURE_SIZE)){
                    List<CameraSize> supportedVideoSize = callback.getSupportedVideoSize();
                    CameraSize videoSize = supportedVideoSize.get(position);
                    callback.onVideoSizeSelected(videoSize);
                }
            }
        };
    }

}
