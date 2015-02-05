package at.wada811.android.camera.sample;

import android.support.v4.util.LruCache;
import at.wada811.android.camera.LogUtils;

public class ByteArrayCache<K> extends LruCache<K, byte[]>{

    public ByteArrayCache(){
        super((int)(Runtime.getRuntime().maxMemory() / 8));
        LogUtils.v("maxSize: " + (int)(Runtime.getRuntime().maxMemory() / 8));
    }

    @Override
    protected int sizeOf(K key, byte[] value){
        return value.length;
    }
}
