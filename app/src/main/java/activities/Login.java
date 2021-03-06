package activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import UserUtils.Constants;
import io.fabric.sdk.android.Fabric;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import Enums.ResponseCode;
import UserUtils.Application;
import Managers.SessionManager;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;
import Interfaces.AbstractCallback;
import Interfaces.OnWebserviceFinishListener;

public class Login extends AppCompatActivity {
    String logout = null;

    String name,password,regId;

    public void doLogin(View btn) {

        name = ((EditText)findViewById(R.id.username)).getText().toString();

        password = ((EditText)findViewById(R.id.password)).getText().toString();

        OnWebserviceFinishListener listener = new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                JSONObject response = null;

                try {

                    response = new JSONObject(webService.getStrResponse().toString());

                    if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()) {

                        String token = response.getString("token");

                        saveUserData(token);

                        startActivity(new Intent(Login.this,Home.class));

                        finish();
                    }

                    else{

                        displayErrorMessage();

                        enableInputs();
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        };

        disableInputs();

        WebserviceRequestUtil.login(name,password,regId,listener);

    }

    private void displayErrorMessage(){

        ((ImageView)findViewById(R.id.imageView2)).setImageResource(R.drawable.incorrect_pass_face);

        ((TextView)findViewById(R.id.bellow_logo_text)).setText("Incorrect username or password!");

        ((TextView)findViewById(R.id.bellow_logo_text)).setTextSize(25);

        ((TextView)findViewById(R.id.bellow_logo_text)).setTypeface(Typeface.DEFAULT);

    }

    private void saveUserData(String token){

        SessionManager.getInstance().saveString("token",token);

        SessionManager.getInstance().saveString("username",name);

        SessionManager.getInstance().saveString("password",password);

    }

    private void disableInputs() {

        findViewById(R.id.login_btn).setClickable(false);

        ((Button) findViewById(R.id.login_btn)).setTextColor(Color.parseColor("#DDDDDD"));

        findViewById(R.id.password).setEnabled(false);

        findViewById(R.id.password).setEnabled(false);

    }

    private void enableInputs() {

        findViewById(R.id.password).setEnabled(true);

        findViewById(R.id.password).setEnabled(true);

        findViewById(R.id.login_btn).setClickable(true);

        ((Button) findViewById(R.id.login_btn)).setTextColor(getResources().getColor(R.color.background));
    }

    public void forgotPassword(View v) {

    }

    public void signup(View v) {

        startActivity(new Intent(this,Signup.class));

    }

    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);

        Resources res = getResources();

        DisplayMetrics dm = res.getDisplayMetrics();

        Configuration conf = res.getConfiguration();

        conf.locale = myLocale;

        res.updateConfiguration(conf, dm);

    }

    protected void onCreate(Bundle savedInstanceState) {

        Application.setCurrentActivity(this);

        Application.setContext(this);

        String locale = PreferenceManager.getDefaultSharedPreferences(this).getString("locale","en");

        setLocale(locale);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        Bundle b = getIntent().getExtras();

        if (b != null && b.getString("logout") != null && b.getString("logout").length() > 0 ) {

            String logout = b.getString("logout");

            if (logout != null && logout.length() == 0) {

                getIntent().putExtra("logout", "");

                startSplash();

            } else {

                setContentView(R.layout.activity_login);

            }

        }
        else if( b!=null && b.getString("logout") != null && b.getString("logout").length() == 0){

            getIntent().putExtra("logout", "");

            startSplash();

        }

        else {

            startSplash();
        }


        registerGcm();

    }

    private void registerGcm(){

        try {
            new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... params) {

                    String msg = "";

                    GoogleCloudMessaging gcm=null;

                    try {

                        if (gcm == null) {

                            gcm = GoogleCloudMessaging.getInstance(Login.this);

                        }

                        String regid = gcm.register(Constants.SENDER_ID);

                        msg = "Device registered, registration ID=" + regid;

                        Login.this.regId = regid;

                    } catch (IOException ex) {

                        msg = "Error :" + ex.getMessage();

                    }

                    return msg;

                }

                @Override
                protected void onPostExecute(String msg) {

                    Log.d("message",msg);

                }

            }.execute(null, null, null);

        }catch (Exception e) {

            e.printStackTrace();

        }

    }

    private boolean checkLogin() {

        boolean loggedIn = false;

        String token = SessionManager.getInstance().getString("token");

        if (token.length() != 0) {

            loggedIn = true;

        }

        return loggedIn;
    }

    private void startSplash() {

        setContentView(R.layout.splash);

        new Thread() {

            public void run() {

                try {

                    Thread.sleep(1000);

                }

                catch (Exception e) {

                    e.printStackTrace();

                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        redirectUser();

                    }
                });

            }
        }.start();
    }

    private void redirectUser(){

        if(Application.isNetworkConnected()) {

            UIUtil.hideInternetConnectionErrorDialog();

            boolean loggedIn = checkLogin();

            if (!loggedIn) {

                setContentView(R.layout.activity_login);

            } else {

                startActivity(new Intent(Login.this, Home.class));

                finish();
            }

        }
        else{

            UIUtil.showInternetConnectionErrorDialog(Login.this, new AbstractCallback() {

                @Override
                public void onResult(boolean isSuccess, Object result) {

                    redirectUser();

                }
            });
        }
    }

}
