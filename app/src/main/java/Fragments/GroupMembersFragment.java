package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import Adapters.GroupMembersAdapter;
import DataModels.Group;
import DataModels.User;
import Enums.ResponseCode;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import UserUtils.Application;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;

public class GroupMembersFragment extends BaseFragment {

    Group group;

    ListView membersList;

    List<User> groupMembers;

    GroupMembersAdapter adapter;

    public void setGroup(Group group) {

        this.group = group;

    }

    public Group getGroup() {

        return group;

    }

    public GroupMembersFragment() {


    }

    private void prepareFragment(){

        rootView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.popCurrentVisibleFragment();

            }
        });

        membersList = (ListView)rootView.findViewById(R.id.members);

        UIUtil.showLoadingView(rootView);

        WebserviceRequestUtil.getGroupMembers(group.getId(), new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                groupMembers = new Gson().fromJson(webService.getStrResponse().toString(),new TypeToken<List<User>>(){}.getType());

                adapter = new GroupMembersAdapter(groupMembers);

                adapter.setGroup(group);

                membersList.setAdapter(adapter);

                UIUtil.hideLoadingView();

            }
        });

        rootView.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showGroupMembersAccessFragment(group,groupMembers);

            }
        });

    }

    @Override
    public void onResume() {

        super.onResume();

        UIUtil.hideTabsView();

        if(adapter != null){

            adapter.notifyDataSetChanged();

        }

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

        return rootView = inflater.inflate(R.layout.members, null);
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
