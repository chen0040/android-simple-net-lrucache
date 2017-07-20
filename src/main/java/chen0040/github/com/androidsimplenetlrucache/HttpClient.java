package chen0040.github.com.androidsimplenetlrucache;

/**
 * Created by chen0 on 20/7/2017.
 */

public interface HttpClient {
    String get(String uri);

    String post(String uri, String body);

    String delete(String uri);
}
