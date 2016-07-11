package Managers;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import DataModels.Group;
import DataModels.User;
import Fragments.AccountSettingsFragment;
import Fragments.BaseFragment;
import Fragments.GroupFragment;
import Fragments.GroupMemberSettingsFragment;
import Fragments.GroupMembersAccessFragment;
import Fragments.GroupMembersFragment;
import Fragments.GroupSettingsFragment;
import Fragments.LibraryFragment;
import Fragments.NewAssignmentFragment;
import Fragments.NewEventFragment;
import Fragments.NewGroupAssignmentFragment;
import Fragments.NewGroupEventFragment;
import Fragments.NewGroupNoteFragment;
import Fragments.NewNoteFragment;
import Fragments.PostViewFragment;
import Fragments.ProgressFragment;
import Interfaces.AbstractCallback;
import Interfaces.FunctionCaller;
import Interfaces.OnWebserviceFinishListener;
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

    public static void clear(){

        currentFragments.clear();
    }

    public static void showNewAssignmentFragment(OnWebserviceFinishListener listener){

        NewAssignmentFragment fragment = new NewAssignmentFragment();

        fragment.setWebserviceFinishListener(listener);

        addFragment(fragment,true);

    }

    public static void showLibraryFragment(AbstractCallback callback, FunctionCaller caller){

        LibraryFragment fragment = new LibraryFragment();

        fragment.setCaller(caller);

        fragment.setOnFileSelectListener(callback);

        addFragment(fragment,true);

    }

    public static void showNewGroupAssignment(String groupId,String groupName,OnWebserviceFinishListener listener){

        NewGroupAssignmentFragment fragment = new NewGroupAssignmentFragment();

        fragment.setWebserviceFinishListener(listener);

        fragment.setGroupId(groupId);

        fragment.setGroupName(groupName);

        addFragment(fragment,true);

    }

    public static void reloadCurrentFragment(){

        BaseFragment fragment = currentFragments.get(currentFragments.size()-1);

        final FragmentTransaction ft = Application.getCurrentActivity().getSupportFragmentManager().beginTransaction();

        ft.detach(fragment);

        ft.attach(fragment);

        ft.commit();

    }

    public static void showPostViewFragment(String postId,AbstractCallback callback){

        if(getCurrentVisibleFragment() instanceof PostViewFragment) return;

        PostViewFragment fragment = new PostViewFragment();

        fragment.setPostListener(callback);

        fragment.setPostId(postId);

        addFragment(fragment,true);

    }

    public static void showNewGroupEvent(String groupId,String groupName,OnWebserviceFinishListener listener){

        NewGroupEventFragment fragment = new NewGroupEventFragment();

        fragment.setWebserviceFinishListener(listener);

        fragment.setGroupId(groupId);

        fragment.setGroupName(groupName);

        addFragment(fragment,true);

    }

    public static void showNewGroupNote(String groupId,String groupName,OnWebserviceFinishListener listener){

        NewGroupNoteFragment fragment = new NewGroupNoteFragment();

        fragment.setWebserviceFinishListener(listener);

        fragment.setGroupId(groupId);

        fragment.setGroupName(groupName);

        addFragment(fragment,true);

    }

    public static void showGroupSettingsFragment(Group group){

        GroupSettingsFragment fragment = new GroupSettingsFragment();

        fragment.setGroup(group);

        addFragment(fragment,true);

    }

    public static void showGroupStream(Group group){

        GroupFragment groupFragment = new GroupFragment();

        groupFragment.setGroup(group);

        addFragment(groupFragment,true);

    }

    public static void showGroupMembersAccessFragment(Group group,List<User> groupMembers){

        GroupMembersAccessFragment fragment = new GroupMembersAccessFragment();

        fragment.setGroup(group);

        fragment.setGroupMembers(groupMembers);

        addFragment(fragment,true);

    }

    public static void showProgressFragment(String profileId){

        ProgressFragment fragment = new ProgressFragment();

        addFragment(fragment,true);

    }

    public static void showGroupMemberSettingsFragment(User user,Group group,List<User> groupMembers){

        GroupMemberSettingsFragment fragment = new GroupMemberSettingsFragment();

        fragment.setGroupMembers(groupMembers);

        fragment.setUser(user);

        fragment.setGroup(group);

        addFragment(fragment,true);

    }

    public static void showGroupMembersFragment(Group group){

        GroupMembersFragment fragment = new GroupMembersFragment();

        fragment.setGroup(group);

        addFragment(fragment,true);

    }

    public static void showNewNoteFragment(OnWebserviceFinishListener listener){

        NewNoteFragment fragment = new NewNoteFragment();

        fragment.setWebserviceFinishListener(listener);

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

            currentFragments.get(currentFragments.size()-1).onResume();

            UIUtil.showTabsView();
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

    public static void showNewEventFragment(OnWebserviceFinishListener listener){

        NewEventFragment fragment = new NewEventFragment();

        fragment.setWebserviceFinishListener(listener);

        addFragment(fragment,true);

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

            tr.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,R.anim.enter_from_right, R.anim.exit_to_right);

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

    public static void showAccountSettingsFragment(){

        AccountSettingsFragment fragment = new AccountSettingsFragment();

        addFragment(fragment,true);

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
