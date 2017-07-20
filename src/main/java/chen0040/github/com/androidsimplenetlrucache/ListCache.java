package chen0040.github.com.androidsimplenetlrucache;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen0 on 18/7/2017.
 */

public class ListCache<T> {
    private List<T> data = new ArrayList<T>();
    private long lastUpdated = -1L;
    private final long updateInterval;
    private final Class<T> typeOfT;
    private static final String TAG = "ListCache";
    private HttpClient httpClient;

    public ListCache(HttpClient httpClient, Class<T> typeOfT) {
        this.typeOfT = typeOfT;
        this.updateInterval = 60000L;
        this.httpClient = httpClient;
    }

    public ListCache(HttpClient httpClient, Class<T> typeOfT, long updateInterval) {
        this.updateInterval = updateInterval;
        this.typeOfT = typeOfT;
        this.httpClient = httpClient;
    }

    public List<T> getData() {
        return data;
    }

    public List<T> getOrDownloadData(String url){
        boolean shouldUpdate = false;
        if(lastUpdated == -1L || data.isEmpty()) {
            shouldUpdate = true;
        } else if(System.currentTimeMillis() - lastUpdated > updateInterval) {
            shouldUpdate = true;
        }

        if(shouldUpdate) {
            Log.i(TAG, url);
            String json = httpClient.get(url);
            Log.v(TAG, json);
            List<T> downloaded = JSON.parseArray(json, typeOfT);
            data.clear();
            data.addAll(downloaded);
            lastUpdated = System.currentTimeMillis();
        }

        return data;

    }

    public long getLastUpdated(){
        return lastUpdated;
    }
}
