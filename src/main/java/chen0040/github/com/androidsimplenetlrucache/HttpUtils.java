package chen0040.github.com.androidsimplenetlrucache;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chen0 on 17/7/2017.
 */

public class HttpUtils implements HttpClient {
    @Override
    public String get(String uri) {
        String result = null;
        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                sb.append(line);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result;
    }

    @Override
    public String post(String uri, String body){
        String result = null;
        HttpURLConnection urlConnection = null;
        try {


            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestMethod("POST");

            urlConnection.connect();

            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));


            writer.write(body);

            writer.close();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                sb.append(line);
            }
            reader.close();

            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result;

    }

    @Override
    public String delete(String uri) {
        String result = null;
        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("DELETE");

            urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                sb.append(line);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result;
    }
}
