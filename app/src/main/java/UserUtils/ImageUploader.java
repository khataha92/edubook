package UserUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;

import Interfaces.OnWebserviceFinishListener;
import Managers.SessionManager;


public class ImageUploader extends AsyncTask<Object,Object,Object> {

    private String imagePath = "";

    String server,port,service;

    private OnWebserviceFinishListener listener;

    public ImageUploader(String server,String port,String service){

        this.server = server;

        this.port = port;

        this.service = service;
    }

    public void upload(){

        this.execute();

    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setListener(OnWebserviceFinishListener listener) {
        this.listener = listener;
    }

    @Override
    protected Object doInBackground(Object... paramz) {

        String url = server + ":" + port + service ;

        SyncHttpClient client = new SyncHttpClient();

        client.addHeader("Authorization","bearer "+ SessionManager.getInstance().getToken());

        RequestParams params = new RequestParams();

        File imageFile = null;

        try{

            imageFile = new File(imagePath);

            params.put("image", imageFile);

        }

        catch (Exception e){

            e.printStackTrace();

        }

        client.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, String s, Throwable throwable) {

                Log.d("error",s);

            }

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, final String s) {

                if(listener!=null){

                    Application.getCurrentActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            listener.onFinish(null);

                        }
                    });

                }
            }
        });

        return null;
    }
}
