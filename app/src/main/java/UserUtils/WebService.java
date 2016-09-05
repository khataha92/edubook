package UserUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Interfaces.OnWebserviceFinishListener;

public class WebService extends AsyncTask <StringBuffer,StringBuffer,StringBuffer> {

    private static final String TAG = WebService.class.getSimpleName();
    private String version = "v1";

    private StringBuffer strResponse =null;

    private String service;

    private int responseCode ;

    String token="";

    private HttpPost httppost;

    private HttpGet httpGet;

    private DefaultHttpClient client = new DefaultHttpClient();

    private String method = "post";

    private Activity context;

    private String port;

    private OnWebserviceFinishListener onWebserviceFinishListener;

    private ArrayList<NameValuePair> inputs = new ArrayList<NameValuePair>();

    private String server="";

    public String getMethod() {

        return method;

    }

    public String getService() {

        return service;

    }

    public void setVersion(String v){
        version = v;
    }

    public WebService(String url){
        server = url;
    }

    public void addParams(String name, String val){

        inputs.add(new BasicNameValuePair(name,val));

    }

    public OnWebserviceFinishListener getWebServiceListener(){

        return onWebserviceFinishListener != null ? onWebserviceFinishListener : new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {


            }

        };

    }

    public int getResponseCode() {

        return responseCode;

    }

    @Override
    protected StringBuffer doInBackground(StringBuffer...params) {

        StringBuffer res = null;

        if(method.equalsIgnoreCase("Post")) {

            res = processPostRequest();

        }

        else if(method.equalsIgnoreCase("Get")) {

            res = processGetRequest();

        }

        else if(method.equalsIgnoreCase("Delete")){

            res = processDeleteRequest();

        }

        Log.e(TAG,res.toString());

        return res;
    }

    private StringBuffer processDeleteRequest(){

        StringBuffer res = null;

        String url = server + ":" + port + "/api/" + version + "/" + service;

        Log.d("server", url);

        Log.d("token",token);
        HttpDelete delete = new HttpDelete(url);

        try {

            HttpResponse response = client.execute(delete);

            responseCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();

            res = convertStreamToString(is);

            strResponse = res;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return res;
    }

    public Activity getContext() {

        return context;

    }

    public void setContext(Activity context) {

        this.context = context;

    }

    public void addHeader(final String key, final String val){

        if(key.equalsIgnoreCase("Authorization")){

            token = val.substring(val.indexOf(' ') + 1);

        }

        client.addRequestInterceptor(new HttpRequestInterceptor() {

            public void process(final HttpRequest request, final HttpContext context) throws HttpException,
                    IOException {

                request.addHeader(key,val);

            }
        });
    }

    public void setMethod(String m){

        method = m;

    }

    private StringBuffer processPostRequest(){

        StringBuffer res = null;

        String url = server + ":" + port + "/api/" + version + "/" + service;

        Log.d("server", url);

        Log.d("token",token);

        httppost = new HttpPost(url);

        try {

            httppost.setEntity(new UrlEncodedFormEntity(inputs,"UTF-8"));

            httppost.setHeader("Accept-Charset","utf-8");

            HttpResponse response = client.execute(httppost);

            responseCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();

            res = convertStreamToString(is);

            strResponse = res;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return res;
    }

    private StringBuffer processGetRequest(){

        StringBuffer res = null;

        String url = server + ":" + port + "/api/" + version + "/" + service;

        Log.d("server", url);

        Log.d("token",token);

        httpGet = new HttpGet(url);

        try {

            HttpResponse response = client.execute(httpGet);

            responseCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();

            res = convertStreamToString(is);

            strResponse = res;

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return res;
    }

    public StringBuffer getStrResponse(){

        return strResponse!=null?strResponse:new StringBuffer();

    }


    public void setOnWebserviceFinishListener(OnWebserviceFinishListener listener){

        onWebserviceFinishListener = listener;

    }

    public void setPort(String p){

        port = p;

    }

    public void setService(String m){

        service = m;

    }
    public void start(){

        execute();

    }

    @Override
    protected void onPostExecute(StringBuffer o) {

        super.onPostExecute(o);

        if(onWebserviceFinishListener !=null) {

            onWebserviceFinishListener.onFinish(this);

        }
    }

    private static StringBuffer convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuffer sb = new StringBuffer();

        String line = null;

        try {

            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");

            }

        }
        catch (IOException e) {

            e.printStackTrace();

        }
        finally {

            try {

                is.close();

            }
            catch (IOException e) {

                e.printStackTrace();

            }
        }

        return sb;
    }

}
