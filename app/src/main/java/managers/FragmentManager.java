package Managers;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import Fragments.BaseFragment;
import Fragments.LibraryFragment;
import Fragments.NewAssignmentFragment;
import Fragments.NewNoteFragment;
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

    public static void showNewAssignmentFragment(){

        NewAssignmentFragment fragment = new NewAssignmentFragment();

        addFragment(fragment,true);

    }

    public static void showLibraryFragment(AbstractCallback callback){

        LibraryFragment fragment = new LibraryFragment();

        fragment.setOnFileSelectListener(callback);

        addFragment(fragment,true);

    }

    public static void showNewNoteFragment(){

        NewNoteFragment fragment = new NewNoteFragment();

        addFragment(fragment,true);

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

        if(getFragmentPosition(MoreFragment.class) != -1){

            android.support.v4.app.FragmentManager manager = ((Home)Application.getCurrentActivity()).getSupportFragmentManager();

            FragmentTransaction trans = manager.beginTransaction();

            trans.remove(currentFragments.get(getFragmentPosition(MoreFragment.class)));

            trans.commit();

            manager.popBackStack();

            currentFragments.remove(getFragmentPosition(MoreFragment.class));

        }


        MoreFragment moreFragment = new MoreFragment();

        addFragment(moreFragment,true);

    }

    public static void showMessagesFragment(){

        if(getFragmentPosition(Messages.class) != -1){

            android.support.v4.app.FragmentManager manager = ((Home)Application.getCurrentActivity()).getSupportFragmentManager();

            FragmentTransaction trans = manager.beginTransaction();

            trans.remove(currentFragments.get(getFragmentPosition(Messages.class)));

            trans.commit();

            manager.popBackStack();

            currentFragments.remove(getFragmentPosition(Messages.class));

        }

        Messages messages = new Messages();

        addFragment(messages,true);

    }

    private static int getFragmentPosition(Class clazz){

        for(int i = 0 ; i < currentFragments.size() ; i++){

            if(currentFragments.get(i).getClass() == clazz){

                return i;

            }

        }

        return -1;
    }

    public static void showHomeFragment(){

        BaseFragment currentFragment = getCurrentVisibleFragment();

        if(currentFragment != null && !(currentFragment instanceof HomeFragment)){

            popCurrentVisibleFragment();

            return;

        }

        HomeFragment fragment = new HomeFragment();

        addFragment(fragment,true);
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

        if (index < 1 || currentFragments.get(index) instanceof HomeFragment || getCurrentVisibleFragment().getClass() != currentFragments.get(index).getClass()) {

            Application.getCurrentActivity().finish();

            return;

        }


        currentFragments.remove(index);

        currentFragments.get(currentFragments.size()-1).onResume();

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

            tr.replace(R.id.content_frame, newFragment);

            tr.addToBackStack(newFragment.getCustomTag());

            if (!activity.isFinishing()) {

                tr.commitAllowingStateLoss();

            }

        }

    }


    private static void addFragment(final BaseFragment newFragment, boolean enableBack) {

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
