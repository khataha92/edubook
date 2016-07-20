package Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import Adapters.GroupListAdapter;
import Adapters.ProgressAdapter;
import DataModels.Progress;
import DataModels.ProgressDataModel;
import DataModels.User;
import Enums.ResponseCode;
import Interfaces.FunctionCaller;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import Managers.SessionManager;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import activities.Home;
import activities.Login;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

public class ProgressFragment extends BaseFragment {

    RecyclerView recyclerView;

    User user = SessionManager.getInstance().getCurrentUser();

    public void setUser(User user) {

        this.user = user;

    }

    public ProgressFragment() {


    }

    private void prepareFragment(){

        UIUtil.showLoadingView(rootView);

        WebserviceRequestUtil.getStudentProgress(user.getId(),new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                ProgressDataModel dataModel;

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    dataModel = new Gson().fromJson(webService.getStrResponse().toString(), ProgressDataModel.class);

                }
                else{

                    dataModel = new ProgressDataModel();

                    dataModel.setProgress(new ArrayList<Progress>());

                }

                addProgress(dataModel);

            }
        });

    }

    public void addProgress(ProgressDataModel dataModel){

        recyclerView = (RecyclerView) rootView.findViewById(R.id.progressLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new ProgressAdapter(dataModel.getProgress(),user));

        UIUtil.hideLoadingView();

    }

    @Override
    public void onResume() {

        super.onResume();

        UIUtil.hideTabsView();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        prepareFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return rootView = inflater.inflate(R.layout.fragment_progress, null);
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
