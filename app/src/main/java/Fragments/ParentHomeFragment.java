package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import Adapters.PostListAdapter;
import DataModels.Group;
import DataModels.NotificationCount;
import DataModels.Post;
import DataModels.Recipient;
import DataModels.RecipientsResponse;
import DataModels.StreamBookResponse;
import DataModels.User;
import Enums.ResponseCode;
import Interfaces.OnWebserviceFinishListener;
import Interfaces.PostFactory;
import Managers.SessionManager;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import activities.Home;
import edubook.edubook.R;
import jp.wasabeef.recyclerview.animators.LandingAnimator;


public class ParentHomeFragment extends HomeFragment {

    private String TAG = getClass().getSimpleName();

    public ParentHomeFragment() {

    }

    @Override
    public void onResume() {

        super.onResume();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_parent_posts, null);

        return rootView;

    }

    @Override
    public void getNotifications(){

    }

    public void initializeComponents(){

        super.initializeComponents();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }
    @Override
    public void onDetach() {

        super.onDetach();

    }

}
