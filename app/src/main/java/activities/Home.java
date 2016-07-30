package activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import DataModels.Progress;
import Enums.Lang;
import Fragments.BaseFragment;
import Fragments.ProgressFragment;
import Interfaces.OnWebserviceFinishListener;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.Constants;
import UserUtils.ImageUploader;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import UserUtils.WebService;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;
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

        UserDefaultUtil.setLanguage(Lang.getValueOf(SessionManager.getInstance().getUserLanguage()));

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CAMERA && resultCode == RESULT_OK) {

            ((ProgressFragment)FragmentManager.getCurrentVisibleFragment()).goCrop(Uri.parse(SessionManager.getInstance().getImagePath()));

        } else if (requestCode == Constants.REQUEST_GALLERY && resultCode == RESULT_OK) {

            ((ProgressFragment)FragmentManager.getCurrentVisibleFragment()).goCrop(data.getData());

        } else if (requestCode == Constants.REQUEST_CROP && resultCode == RESULT_OK) {

            ((ProgressFragment)FragmentManager.getCurrentVisibleFragment()).uploadImage(data);

        }
    }

    @Override
    public void onBackPressed() {

        FragmentManager.popCurrentVisibleFragment();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

}
