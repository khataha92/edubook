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

import Adapters.GroupListAdapter;
import Enums.Lang;
import Interfaces.AbstractCallback;
import Managers.FragmentManager;
import Managers.SessionManager;
import UserUtils.UIUtil;
import activities.Home;
import activities.Login;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

public class AccountSettingsFragment extends BaseFragment {

    public AccountSettingsFragment() {


    }

    private void prepareFragment(){

        final TextView language = (TextView) rootView.findViewById(R.id.lang);

        final String lang = SessionManager.getInstance().getUserLanguage();

        language.setText(lang);

        final TextView emailAddress = (TextView) rootView.findViewById(R.id.email);

        String email = SessionManager.getInstance().getCurrentUser().getEmail();

        emailAddress.setText(email);

        TextView editLanguage = (TextView) rootView.findViewById(R.id.editLanguage);

        editLanguage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showLanguageDialog(new AbstractCallback() {

                    @Override
                    public void onResult(boolean isSuccess, Object result) {

                        if(isSuccess) {

                            language.setText(((Lang) result).getValue());

                        }

                    }
                });

            }
        });

        TextView editEmail = (TextView) rootView.findViewById(R.id.editEmail);

        editEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showEditEmailDialog(new AbstractCallback() {

                    @Override
                    public void onResult(boolean isSuccess, Object result) {

                        if(isSuccess){

                            String email = (String)result;

                            SessionManager.getInstance().getCurrentUser().setEmail(email);

                            emailAddress.setText(email);

                        }

                    }
                });

            }
        });

        TextView editPass = (TextView) rootView.findViewById(R.id.changePass);

        editPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showEditPassDialog(new AbstractCallback() {

                    @Override
                    public void onResult(boolean isSuccess, Object result) {

                        if(isSuccess){

                            String newPassword = (String )result;

                            SessionManager.getInstance().saveString("password",newPassword);

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

        return rootView = inflater.inflate(R.layout.fragment_settings, null);
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
