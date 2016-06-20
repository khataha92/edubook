package Managers;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import Fragments.BaseFragment;
import Interfaces.AbstractCallback;
import UserUtils.Application;
import UserUtils.UIUtil;
import edubook.edubook.R;
import activities.Home;
import Fragments.HomeFragment;
import Fragments.Messages;
import Fragments.MoreFragment;


public class FragmentManager {

    private static final String TAG = FragmentManager.class.getName();

    private static ArrayList<BaseFragment> currentFragments = new ArrayList<>();

    public static void popFragmentsArray() {

        int index = getFragmentCount() - 1;

        if (index < 1) {

            return;

        }

        currentFragments.remove(index);
    }

    public static void popToHome(){

        if (Application.getCurrentActivity() instanceof Home) {

            Home activity = (Home) Application.getCurrentActivity();

            android.support.v4.app.FragmentManager manager = activity.getSupportFragmentManager();

            if (manager.getBackStackEntryCount() > 1) {

                int rootFragment = manager.getBackStackEntryAt(1).getId();

                manager.popBackStack(rootFragment, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }

            for (int i = currentFragments.size() - 1; i > 0; i--) {

                currentFragments.remove(i);

            }
        }
    }

    public static void showMoreFragment(){

        BaseFragment currentFragment = getCurrentVisibleFragment();

        if(currentFragment != null && !(currentFragment instanceof HomeFragment)){

            MoreFragment moreFragment = new MoreFragment();

            replaceFragment(moreFragment,true);

            return;

        }

        MoreFragment moreFragment = new MoreFragment();

        replaceFragment(moreFragment,true);

    }

    public static void showMessagesFragment(){

        BaseFragment currentFragment = getCurrentVisibleFragment();

        if(currentFragment != null && !(currentFragment instanceof HomeFragment)){

            Messages messages = new Messages();

            replaceFragment(messages,true);

            return;

        }

        Messages messages = new Messages();

        replaceFragment(messages,true);

    }

    public static void showHomeFragment(){

        BaseFragment currentFragment = getCurrentVisibleFragment();

        if(currentFragment != null && !(currentFragment instanceof HomeFragment)){

            popCurrentVisibleFragment();

            return;

        }

        HomeFragment fragment = new HomeFragment();

        replaceFragment(fragment,true);
    }

    public static BaseFragment getBeforeCurrentVisibleFragment() {

        int index = currentFragments.size() - 2;

        if (index < 0) {

            return null;

        }

        return currentFragments.get(index);
    }

    public static BaseFragment getCurrentVisibleFragment() {

        int index = currentFragments.size() - 1;

        if (index < 0) {

            return null;

        }

        return currentFragments.get(index);
    }

    public static void popCurrentVisibleFragment() {

        int index = currentFragments.size() - 1;

        if (index < 1) {

            Application.getCurrentActivity().finish();

            return;

        }


        currentFragments.remove(index);

        UIUtil.hideSoftKeyboard();

        if (Application.getCurrentActivity() instanceof Home) {

            try {

                ((Home) Application.getCurrentActivity()).getSupportFragmentManager().popBackStackImmediate();

            } catch (Throwable t) {

                Log.e("FragmentManager", "Illegal sate exception in popCurrentVisibleFragment ", t);

            }

        }

    }

    public static void popCurrentVisibleAndBeforeCurrentVisibleFragment() {

        int index = currentFragments.size() - 1;

        if (index < 1) {

            return;

        }

        currentFragments.remove(index);

        index = currentFragments.size() - 1;

        if (index < 1) {

            return;

        }

        currentFragments.remove(index);

        UIUtil.hideSoftKeyboard();

        if (Application.getCurrentActivity() instanceof Home) {

            ((Home) Application.getCurrentActivity()).getSupportFragmentManager().popBackStack();

        }
    }

    public static void popCurrentVisibleFragment(AbstractCallback callback) {

        if (callback != null) {

            callback.onResult(true, null);

        }

        popCurrentVisibleFragment();

    }
    private static void replaceFragment(final BaseFragment newFragment, boolean enableBack) {

        if (Application.getCurrentActivity() instanceof Home) {

            // Hide soft keyboard if it is visible
            InputMethodManager inputManager = (InputMethodManager) Application.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            Home activity = (Home) Application.getCurrentActivity();

            View v = activity.getCurrentFocus();

            if (v != null) {

                inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }

            FragmentTransaction tr = activity.getSupportFragmentManager().beginTransaction();

            tr.setCustomAnimations(0, 0, 0, 0);

            tr.add(R.id.content_frame, newFragment, newFragment.getCustomTag());

            if (enableBack) {

                tr.addToBackStack(null);

                currentFragments.add(newFragment);

            }

            if (!activity.isFinishing()) {

                tr.commitAllowingStateLoss();

            }

        }

    }


    public static int getFragmentCount() {

        return currentFragments.size();
    }

    public static BaseFragment getFragmentWithTag(String tag) {

        for (BaseFragment baseFragment : currentFragments) {

            if (baseFragment.getCustomTag().equals(tag)) {
                return baseFragment;
            }
        }
        return null;
    }

    public static int numberOfFragmentsWithTag(String tag) {

        int count = 0;

        for (BaseFragment baseFragment : currentFragments) {

            if (baseFragment.getCustomTag().equals(tag)) {

                count++;
            }
        }

        return count;
    }

}
