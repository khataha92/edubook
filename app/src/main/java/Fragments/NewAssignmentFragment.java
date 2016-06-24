package Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import DataModels.LibraryFile;
import DataModels.Post;
import DataModels.RecieversModel;
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

public class NewAssignmentFragment extends PostFragment {


    String dueDate = "";

    boolean lockAfterDueDate = true;

    String fileId = "";

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

                FragmentManager.showLibraryFragment(callback);

            }
        });

        rootView.findViewById(R.id.due_date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                UIUtil.showDatePickerDialog(new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) {

                        String year = String.valueOf(selectedYear);

                        String month = String.valueOf(selectedMonth + 1);

                        String day = String.valueOf(selectedDay);

                        dueDate = year+"-"+month+"-"+day;

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

        WebserviceRequestUtil.addAssignment(title, description,dueDate,lockAfterDueDate, model, new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                UIUtil.hideSweetLoadingView();

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    Post assignment = new Gson().fromJson(webService.getStrResponse().toString(),Post.class);

                    SessionManager.getInstance().getPosts().add(0,assignment);

                    ((HomeFragment)FragmentManager.getBeforeCurrentVisibleFragment()).updatePostList();

                    FragmentManager.popCurrentVisibleFragment();

                    Activity activity = Application.getCurrentActivity();

                    activity.findViewById(R.id.bottom_tabs).setVisibility(View.VISIBLE);

                }
                else{

                    UIUtil.showErrorDialog();

                }

            }
        });

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
