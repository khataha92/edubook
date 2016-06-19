package fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import UserUtils.UIUtil;
import edubook.edubook.R;
import edubook.edubook.activities.Home;

public class MoreFragment extends BaseFragment {

    public MoreFragment() {


    }
    public void viewLibrary(View v){

    }
    public void logout(View s) {

    }
    private void prepareFragment(){

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

        return inflater.inflate(R.layout.fragment_more, null);
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
