package Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import DataModels.Note;
import DataModels.Post;
import DataModels.RecieversModel;
import Enums.ResponseCode;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.Constants;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;
public class NewNoteFragment extends PostFragment {


    OnWebserviceFinishListener webserviceFinishListener;

    public void setWebserviceFinishListener(OnWebserviceFinishListener webserviceFinishListener) {

        this.webserviceFinishListener = webserviceFinishListener;

    }

    public NewNoteFragment() {


    }

    @Override
    public void onResume() {

        super.onResume();

    }

    @Override
    public void prepareFragment() {

        super.prepareFragment();

        loadImage();

    }

    @Override
    public void addPost() {

        String description = ((TextView)rootView.findViewById(R.id.description)).getText().toString();

        RecieversModel model = new RecieversModel();

        model.setRecipientList(targetRecipients);

        model.setType(type);

        UIUtil.showSweetLoadingView();

        WebserviceRequestUtil.addNote(null, description, model, webserviceFinishListener);

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

        return rootView = inflater.inflate(R.layout.fragment_new_note, null);
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
