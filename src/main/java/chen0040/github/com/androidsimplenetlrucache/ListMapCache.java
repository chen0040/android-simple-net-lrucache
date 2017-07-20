package chen0040.github.com.androidsimplenetlrucache;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chen0 on 18/7/2017.
 */

public class ListMapCache<K, V> {
    private final ConcurrentHashMap<K, ListCache<V>> data = new ConcurrentHashMap<>();
    private MinPQ<K> minPQ =new MinPQ<K>(new CacheComparator());
    private final int sizeLimit;
    private final Class<V> typeOfV;
    private final long updateInterval;
    private final HttpClient httpClient;

    private final class CacheComparator implements Comparator<K> {

        @Override
        public int compare(K k1, K k2) {
            Long t1 = data.get(k1).getLastUpdated();
            Long t2 = data.get(k2).getLastUpdated();
            return t1.compareTo(t2);
        }
    }

    public ListMapCache(HttpClient httpClient, long updateInterval, int sizeLimit, Class<V> typeOfV){
        this.typeOfV = typeOfV;
        this.sizeLimit = sizeLimit;
        this.updateInterval = updateInterval;
        this.httpClient = httpClient;
    }

    public ListMapCache(HttpClient httpClient, int sizeLimit, Class<V> typeOfV) {
        this.typeOfV = typeOfV;
        this.sizeLimit = sizeLimit;
        this.updateInterval = 60000L;
        this.httpClient = httpClient;
    }

    public List<V> getOrDownloadData(K key, String url) {
        ListCache<V> cache;
        if(data.containsKey(key)) {
            cache = data.get(key);
        } else {
            cache = new ListCache<>(httpClient, typeOfV, updateInterval);
            data.put(key, cache);
            minPQ.add(key);
        }

        cache.getOrDownloadData(url);

        if(data.size() > sizeLimit) {
            K deletedKey = minPQ.delMin();
            data.remove(deletedKey);
        }

        return cache.getData();
    }
}
