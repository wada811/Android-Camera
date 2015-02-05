package at.wada811.android.camera.sample;

import android.content.Intent;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import at.wada811.android.camera.CameraSizePolicy;


public class MainActivity extends ActionBarActivity implements OnClickListener{

    private RadioGroup radioFacing;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioFacing = (RadioGroup)findViewById(R.id.radioFacing);
        findViewById(R.id.openCamera).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(this, CameraActivity.class);
        int facing;
        switch(radioFacing.getCheckedRadioButtonId()){
            case R.id.radioFacingBack:
                facing = CameraInfo.CAMERA_FACING_BACK;
                break;
            case R.id.radioFacingFront:
                facing = CameraInfo.CAMERA_FACING_FRONT;
                break;
            default:
                facing = CameraInfo.CAMERA_FACING_BACK;
                break;
        }
        intent.putExtra(CameraActivity.EXTRA_FACING, facing);
        intent.putExtra(CameraActivity.EXTRA_POLICY, CameraSizePolicy.FILL);
        startActivity(intent);
    }
}
