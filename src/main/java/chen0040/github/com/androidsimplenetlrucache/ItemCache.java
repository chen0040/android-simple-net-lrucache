package chen0040.github.com.androidsimplenetlrucache;

import android.util.Log;

import com.alibaba.fastjson.JSON;

/**
 * Created by chen0 on 18/7/2017.
 */

public class ItemCache<T> {
    private T data = null;
    private long lastUpdated = -1L;
    private final long updateInterval;
    private final Class<T> typeOfT;
    private static final String TAG = "ItemCache";
    private final HttpClient httpClient;

    public ItemCache(HttpClient httpClient, Class<T> typeOfT) {
        this.typeOfT = typeOfT;
        this.httpClient = httpClient;
        this.updateInterval =  60000L;
    }

    public ItemCache(HttpClient httpClient, Class<T> typeOfT, long updateInterval) {
        this.updateInterval = updateInterval;
        this.typeOfT = typeOfT;
        this.httpClient = httpClient;
    }

    public T getData() {
        return data;
    }

    public T getOrDownloadData(String url){
        boolean shouldUpdate = false;
        if(lastUpdated == -1L || data == null) {
            shouldUpdate = true;
        } else if(System.currentTimeMillis() - lastUpdated > updateInterval) {
            shouldUpdate = true;
        }

        if(shouldUpdate) {
            Log.i(TAG, url);
            String json = httpClient.get(url);
            Log.v(TAG, json);
            data = JSON.parseObject(json, typeOfT);
            lastUpdated = System.currentTimeMillis();
        }

        return data;

    }

    public long getLastUpdated(){
        return lastUpdated;
    }
}
