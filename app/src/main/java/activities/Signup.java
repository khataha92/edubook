package activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import UserUtils.Application;
import UserUtils.StringUtil;
import UserUtils.UIUtil;
import edubook.edubook.R;

public class Signup extends AppCompatActivity {

    private static final String TAG = Signup.class.getSimpleName();

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

        setContentView(R.layout.activity_signup);

        initializeView();

    }

    private boolean isInputsValid(){

        String username = ((EditText)findViewById(R.id.username)).getText().toString();

        String email = ((EditText)findViewById(R.id.email)).getText().toString();

        String code =  ((EditText)findViewById(R.id.access_code)).getText().toString();

        if(StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(email) || StringUtil.isNullOrEmpty(code)){

            return false;

        }

        if(username.length() < 6 || !StringUtil.isEmail(email)){

            return false;

        }

        return true;

    }

    private void initializeView(){

        View next = findViewById(R.id.next);

        if(next!= null) {

            next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (!isInputsValid()) {

                        Toast.makeText(Signup.this, getString(R.string.check_inputs), Toast.LENGTH_SHORT).show();

                        return;

                    }

                    UIUtil.showSweetLoadingView();

                    new Thread() {

                        public void run() {

                            try {

                                Thread.sleep(2000);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        UIUtil.hideSweetLoadingView();

                                        UIUtil.showErrorDialog(getString(R.string.SOMETHING_WENT_WRONG));

                                    }

                                });

                            } catch (Exception e) {

                                Log.e(TAG, "Error in sleep", e);

                            }

                        }

                    }.start();

                }
            });

        }

    }


}
