package at.wada811.android.camera.callback;

public interface TakePicturesCallback{

    public void onPictureTaken(int index, byte[] data, int rotation);

    public void onPicturesTaken();

}
