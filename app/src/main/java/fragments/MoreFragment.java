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

import com.squareup.picasso.Picasso;

import Adapters.GroupListAdapter;
import Interfaces.FunctionCaller;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import UserUtils.UIUtil;
import UserUtils.WebserviceRequestUtil;
import activities.Login;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;
import activities.Home;
import Managers.SessionManager;

public class MoreFragment extends BaseFragment {

    RecyclerView groupList;

    public MoreFragment() {


    }

    private void prepareFragment(){

        rootView.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showAccountSettingsFragment();

            }
        });

        groupList = (RecyclerView) rootView.findViewById(R.id.groups);

        groupList.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));

        groupList.setAdapter(new GroupListAdapter(SessionManager.getInstance().getUserGroups()));

        rootView.findViewById(R.id.showLibrary).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showLibraryFragment(null, new FunctionCaller() {

                    @Override
                    public void callFunction(Object object) {

                        OnWebserviceFinishListener listener = (OnWebserviceFinishListener)object;

                        WebserviceRequestUtil.getLibrary(listener);

                    }
                });

            }
        });

        rootView.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                logout();

            }
        });

        CircleImageView profileImage = (CircleImageView) rootView.findViewById(R.id.profile_image);

        String thumb = SessionManager.getInstance().getCurrentUser().getThumb();

        UIUtil.loadImageFromUrl(profileImage,thumb);

        ((TextView)rootView.findViewById(R.id.name)).setText(SessionManager.getInstance().getCurrentUser().getName());

        ((TextView)rootView.findViewById(R.id.type)).setText(SessionManager.getInstance().getCurrentUser().getType().getName());

    }

    public void logout(){

        SessionManager.getInstance().clearData();

        FragmentManager.clear();

        startActivity( new Intent(getContext(), Login.class ));

        getActivity().finish();

    }

    @Override
    public void onResume() {

        super.onResume();

        ((ImageView)getActivity().findViewById(R.id.more)).setImageResource(R.drawable.menu_selected);

        ((Home)getActivity()).replaceIcon();

        UIUtil.showTabsView();

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

        return rootView = inflater.inflate(R.layout.fragment_more, null);
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
