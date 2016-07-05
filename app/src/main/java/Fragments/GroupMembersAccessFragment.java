package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import DataModels.Group;
import DataModels.User;
import Enums.GroupUserStatus;
import Enums.ResponseCode;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;

public class GroupMembersAccessFragment extends BaseFragment {

    int selectedRole = -1;

    List<User> groupMembers;

    Group group;

    public void setGroupMembers(List<User> groupMembers) {

        this.groupMembers = groupMembers;

    }

    public void setGroup(Group group) {

        this.group = group;

    }

    public GroupMembersAccessFragment() {


    }

    private void prepareFragment(){

        rootView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.popCurrentVisibleFragment();

            }
        });

        rootView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(((RadioGroup)rootView.findViewById(R.id.roles)).getCheckedRadioButtonId() == R.id.contributor){

                    selectedRole = 2;

                }
                else{

                    selectedRole = 3;

                }

                UIUtil.showSweetLoadingView();

                final GroupUserStatus status = GroupUserStatus.getStatusWithCode(selectedRole);

                WebserviceRequestUtil.setGroupMembersToStatus(group.getId(), status, new OnWebserviceFinishListener() {

                    @Override
                    public void onFinish(WebService webService) {

                        UIUtil.hideSweetLoadingView();

                        if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                            for(int i = 0 ; i < groupMembers.size() ; i ++){

                                groupMembers.get(i).getRole().setId(status.getStatus());

                            }

                            FragmentManager.popCurrentVisibleFragment();

                        }
                        else{

                            UIUtil.showErrorDialog();

                        }

                    }
                });

            }
        });

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

        return rootView = inflater.inflate(R.layout.member_options, null);
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
