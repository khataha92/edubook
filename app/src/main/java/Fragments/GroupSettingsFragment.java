package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import DataModels.Group;
import Enums.Lang;
import Enums.ResponseCode;
import Interfaces.AbstractCallback;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;

public class GroupSettingsFragment extends BaseFragment {

    Group group;

    public void setGroup(Group group) {

        this.group = group;

    }

    public Group getGroup() {

        return group;

    }

    public GroupSettingsFragment() {


    }

    private void prepareFragment(){

        rootView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.popCurrentVisibleFragment();

            }

        });

        ((EditText)rootView.findViewById(R.id.groupName)).setText(group.getName());

        ((EditText)rootView.findViewById(R.id.description)).setText(group.getDescription());

        ((EditText)rootView.findViewById(R.id.years)).setText(group.getGroupClass());

        rootView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String description = ((EditText)rootView.findViewById(R.id.description)).getText().toString();

                UIUtil.showSweetLoadingView();

                WebserviceRequestUtil.changeGroupDescription(group, description, new OnWebserviceFinishListener() {

                    @Override
                    public void onFinish(WebService webService) {

                        UIUtil.hideSweetLoadingView();

                        if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                            String success = Application.getContext().getString(R.string.saved_successfully);

                            Toast.makeText(Application.getContext(), success , Toast.LENGTH_SHORT).show();

                            group.setDescription(description);

                            FragmentManager.popCurrentVisibleFragment();

                        }
                        else {

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

        return rootView = inflater.inflate(R.layout.fragment_group_settings, null);
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
