package Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import DataModels.LibraryFile;
import DataModels.Post;
import DataModels.RecieversModel;
import Enums.ResponseCode;
import Interfaces.AbstractCallback;
import Interfaces.FunctionCaller;
import Interfaces.OnDateSelectedListener;
import Interfaces.OnWebserviceFinishListener;
import Interfaces.PostFactory;
import Managers.FragmentManager;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;

public class NewAssignmentFragment extends PostFragment {


    String dueDate = "";

    boolean lockAfterDueDate = true;

    String fileId = "";

    RecyclerView recyclerView;

    List<PostFactory> postList;

    OnWebserviceFinishListener webserviceFinishListener;

    public void setWebserviceFinishListener(OnWebserviceFinishListener webserviceFinishListener) {

        this.webserviceFinishListener = webserviceFinishListener;

    }

    public void setPostList(List<PostFactory> postList) {

        this.postList = postList;

    }

    public void setRecyclerView(RecyclerView recyclerView) {

        this.recyclerView = recyclerView;

    }

    public NewAssignmentFragment() {


    }

    @Override
    public void onResume() {

        super.onResume();

    }

    @Override
    public void prepareFragment() {

        super.prepareFragment();

        rootView.findViewById(R.id.library).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                AbstractCallback callback = new AbstractCallback() {

                    @Override
                    public void onResult(boolean isSuccess, Object result) {

                        if(isSuccess){

                            fileId = ((LibraryFile)result).getId();

                            FragmentManager.popCurrentVisibleFragment();

                            ((ImageView)view).setImageResource(R.drawable.library);

                        }

                    }
                };

                FragmentManager.showLibraryFragment(callback, new FunctionCaller() {

                    @Override
                    public void callFunction(Object object) {

                        OnWebserviceFinishListener listener = (OnWebserviceFinishListener)object;

                        WebserviceRequestUtil.getLibrary(listener);

                    }
                });

            }
        });

        rootView.findViewById(R.id.due_date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                UIUtil.showDatePickerDialog(new OnDateSelectedListener() {

                    @Override
                    public void onDateSelected(String selectedDate) {

                        dueDate = selectedDate;

                        ((ImageView)view).setImageResource(R.drawable.calendar);

                    }
                });

            }
        });

    }

    @Override
    public void addPost() {

        String description = ((TextView)rootView.findViewById(R.id.description)).getText().toString();

        String title = ((TextView)rootView.findViewById(R.id.title)).getText().toString();

        RecieversModel model = new RecieversModel();

        model.setRecipientList(targetRecipients);

        model.setType(type);

        UIUtil.showSweetLoadingView();

        WebserviceRequestUtil.addAssignment(title, description,dueDate,lockAfterDueDate, model, webserviceFinishListener);

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

        return rootView = inflater.inflate(R.layout.fragment_new_assignment, null);
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
