package fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import CustomComponent.CustomTopBar;
import UserUtils.Application;
import edubook.edubook.activities.Login;
import interfaces.AbstractCallback;

public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private AbstractCallback permissionsCallback;

    private String customTag = "";
    /**
     * Override the on back button pressed action
     *
     * @return return true to pop this fragment
     */
    public boolean onBackPressed() {



        return true;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (BuildConfig.DEBUG) {

            //AppUtil.watchMemoryLeaks(this);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public CustomTopBar getTopBar() {

        if (Application.getCurrentActivity() instanceof Login) {

            Login activity = (Login) Application.getCurrentActivity();

            //return activity.getTopBar();
        }

        return null;

    }

    public String getCustomTag(){
        return customTag;
    }

    public void setCustomTag(String customTag){
        this.customTag = customTag;
    }




}
