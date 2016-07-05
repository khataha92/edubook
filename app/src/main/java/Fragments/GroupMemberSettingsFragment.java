package Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import Adapters.GroupMembersAdapter;
import DataModels.Group;
import DataModels.User;
import Enums.ResponseCode;
import Interfaces.AbstractCallback;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;

public class GroupMemberSettingsFragment extends BaseFragment {

    User user;

    int selectedRole = -1;

    List<User> groupMembers;

    Group group;

    public void setGroupMembers(List<User> groupMembers) {

        this.groupMembers = groupMembers;

    }

    OnWebserviceFinishListener removeProfileImage = new OnWebserviceFinishListener() {

        @Override
        public void onFinish(WebService webService) {

            UIUtil.hideSweetLoadingView();

            if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                showRemoveSuccessfully();

            }

            else{

                UIUtil.showErrorDialog();

            }

        }
    };

    OnWebserviceFinishListener removeUserFromGroup = new OnWebserviceFinishListener() {

        @Override
        public void onFinish(WebService webService) {

            UIUtil.hideSweetLoadingView();

            if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                showRemoveSuccessfully();

                groupMembers.remove(user);

                FragmentManager.popCurrentVisibleFragment();

            }

            else{

                UIUtil.showErrorDialog();

            }

        }
    };

    private void showRemoveSuccessfully(){

        String removed = getContext().getString(R.string.removed_successfully);

        Toast.makeText(getContext(), removed , Toast.LENGTH_SHORT).show();

    }

    public void setGroup(Group group) {

        this.group = group;

    }

    public void setUser(User user) {

        this.user = user;

    }

    public GroupMemberSettingsFragment() {


    }

    private void prepareFragment(){

        rootView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.popCurrentVisibleFragment();

            }
        });

        rootView.findViewById(R.id.removeProfilePic).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showSweetLoadingView();

                WebserviceRequestUtil.removeProfileImage(user.getId(), removeProfileImage);

            }
        });

        rootView.findViewById(R.id.removeFromGroup).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showSweetLoadingView();

                WebserviceRequestUtil.removeUserFromGroup(user.getId(), group.getId(), removeUserFromGroup);

            }
        });

        rootView.findViewById(R.id.parentCode).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showSweetLoadingView();

                WebserviceRequestUtil.getParentAccessKey(user.getId(), new OnWebserviceFinishListener() {

                    @Override
                    public void onFinish(WebService webService) {

                        UIUtil.hideSweetLoadingView();

                        if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                            try {

                                JSONObject parentCode = new JSONObject(webService.getStrResponse().toString());

                                String code = parentCode.getString("access_key");

                                UIUtil.showSweetResultView(code, new AbstractCallback() {

                                    @Override
                                    public void onResult(boolean isSuccess, Object result) {

                                        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                                        ClipData clip = ClipData.newPlainText("access_key", ((String)result));

                                        clipboard.setPrimaryClip(clip);

                                        String copied = getContext().getString(R.string.copied_to_clipboard);

                                        Toast.makeText(getContext(), copied , Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }

                            catch (Exception e){

                                e.printStackTrace();

                            }

                        }
                        else{

                            UIUtil.showErrorDialog();

                        }

                    }
                });

            }
        });

        if(user.getRole().getId() == 2){

            ((RadioGroup)rootView.findViewById(R.id.roles)).check(R.id.contributor);

            selectedRole = 2;

        }
        else{

            ((RadioGroup)rootView.findViewById(R.id.roles)).check(R.id.readOnly);

            selectedRole = 3;

        }

        rootView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(((RadioGroup)rootView.findViewById(R.id.roles)).getCheckedRadioButtonId() == R.id.contributor){

                    selectedRole = 2;

                }
                else{

                    selectedRole = 3;

                }

                if(user.getRole().getId() != selectedRole){

                    UIUtil.showSweetLoadingView();

                    String groupId = group.getId().substring(0,group.getId().indexOf("."));

                    WebserviceRequestUtil.changeUserStatus(user.getId(), groupId, new OnWebserviceFinishListener() {

                        @Override
                        public void onFinish(WebService webService) {

                            UIUtil.hideSweetLoadingView();

                            if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                                String success = getContext().getString(R.string.saved_successfully);

                                Toast.makeText(getContext(), success , Toast.LENGTH_SHORT).show();

                                user.getRole().setId(selectedRole == 3 ? 2 : 3);

                                FragmentManager.popCurrentVisibleFragment();

                            }
                            else{

                                UIUtil.showErrorDialog();

                            }

                        }
                    });

                }
                else{

                    FragmentManager.popCurrentVisibleFragment();

                }

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

        return rootView = inflater.inflate(R.layout.group_access, null);
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
