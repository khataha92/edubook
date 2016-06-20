package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import UserUtils.Application;
import UserUtils.UIUtil;
import edubook.edubook.R;
import Fragments.BaseFragment;
import Fragments.HomeFragment;
import Fragments.MoreFragment;
import Managers.FragmentManager;

public class Home extends FragmentActivity
{
    private static final String TAG = Home.class.getName();

    private View.OnClickListener showMoreFragment = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            FragmentManager.showMoreFragment();

        }
    };

    private View.OnClickListener showHomeFragment = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            FragmentManager.popToHome();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        UIUtil.changeStatusBarColor(this);

        Application.setContext(this);

        Application.setCurrentActivity(this);

        prepareActivity();

        FragmentManager.showHomeFragment();

    }


    private void prepareActivity() {

        findViewById(R.id.more_container).setOnClickListener(showMoreFragment);

        findViewById(R.id.home_container).setOnClickListener(showHomeFragment);
    }

    public void replaceIcon(){

        BaseFragment fragment = FragmentManager.getBeforeCurrentVisibleFragment();

        if(fragment instanceof HomeFragment){

            ((ImageView)findViewById(R.id.home)).setImageResource(R.drawable.home_unselected);

        }
        else if(fragment instanceof MoreFragment){

            ((ImageView)findViewById(R.id.more)).setImageResource(R.drawable.menu);

        }
    }

    @Override
    public void onBackPressed() {

        FragmentManager.popCurrentVisibleFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

}
