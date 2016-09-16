package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Adapters.NotificationAdapter;
import Adapters.PostListAdapter;
import DataModels.NotificationCount;
import DataModels.NotificationModel;
import DataModels.NotificationResponse;
import DataModels.Post;
import DataModels.StreamBookResponse;
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


public class NotificationFragment extends BaseFragment {

    private String TAG = getClass().getSimpleName();

    RecyclerView notificationList ;

    public NotificationFragment() {

    }


    @Override
    public void onResume() {

        super.onResume();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_notification, null);

        UIUtil.showLoadingView(rootView);

        return rootView;

    }

    public void initializeComponents(){

        WebserviceRequestUtil.getUserNotifications(new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    String response = webService.getStrResponse().toString();

                    NotificationResponse notificationResponse = new Gson().fromJson(response,NotificationResponse.class);

                    List<NotificationModel> notificationModels = notificationResponse.getNotifications();

                    initializeRecyclerView(notificationModels);

                }

            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initializeComponents();


    }

    public void initializeRecyclerView(List<NotificationModel> notificationModels){

        notificationList = (RecyclerView) rootView.findViewById(R.id.notification_list);

        notificationList.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationList.setAdapter(new NotificationAdapter(notificationModels));

        notificationList.post(new Runnable() {

            @Override
            public void run() {

                UIUtil.hideLoadingView();

            }
        });

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
