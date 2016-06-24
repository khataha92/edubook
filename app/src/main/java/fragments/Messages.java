package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edubook.edubook.R;
import Managers.FragmentManager;


public class Messages extends BaseFragment {

    public Messages() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public boolean onBackPressed() {

        FragmentManager.popCurrentVisibleFragment();

        return super.onBackPressed();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.description).setOnClickListener(messageClick);

        getActivity().findViewById(R.id.profile_image).setOnClickListener(messageClick);

        getActivity().findViewById(R.id.message_text).setOnClickListener(messageClick);

        getActivity().findViewById(R.id.name).setOnClickListener(messageClick);

    }

    View.OnClickListener messageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return rootView = inflater.inflate(R.layout.fragment_messages, null);

    }
    public void onButtonPressed(Uri uri) {

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
