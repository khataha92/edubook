package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import Adapters.GroupListAdapter;
import edubook.edubook.R;
import activities.Home;
import Managers.SessionManager;

public class MoreFragment extends BaseFragment {

    View rootView;

    RecyclerView groupList;

    public MoreFragment() {


    }
    public void viewLibrary(View v){

    }
    public void logout(View s) {

    }
    private void prepareFragment(){

        groupList = (RecyclerView) rootView.findViewById(R.id.groups);

        groupList.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));

        groupList.setAdapter(new GroupListAdapter(SessionManager.getInstance().getUserGroups()));

    }

    @Override
    public void onResume() {

        super.onResume();

        ((ImageView)getActivity().findViewById(R.id.more)).setImageResource(R.drawable.menu_selected);

        ((Home)getActivity()).replaceIcon();

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
