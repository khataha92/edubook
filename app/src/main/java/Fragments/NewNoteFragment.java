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
import Managers.SessionManager;
import activities.Home;
import edubook.edubook.R;

/**
 * Created by mac on 6/20/16.
 */
public class NewNoteFragment extends BaseFragment {

    View rootView;

    RecyclerView groupList;

    public NewNoteFragment() {


    }

    private void prepareFragment(){

       getActivity().findViewById(R.id.bottom_tabs).setVisibility(View.GONE);

    }

    @Override
    public void onResume() {

        super.onResume();

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
