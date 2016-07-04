package Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class NewEventFragment extends PostFragment {

    String startDate="",endDate="";

    OnWebserviceFinishListener webserviceFinishListener;

    public void setWebserviceFinishListener(OnWebserviceFinishListener webserviceFinishListener) {

        this.webserviceFinishListener = webserviceFinishListener;

    }

    public NewEventFragment() {


    }

    @Override
    public void onResume() {

        super.onResume();

    }

    @Override
    public void prepareFragment() {

        super.prepareFragment();


        rootView.findViewById(R.id.due_date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                final Dialog dialog = new Dialog(getContext(),R.style.AppTheme_NoActionBar);

                dialog.setContentView(R.layout.event_from_to_date);

                dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        startDate = "";

                        endDate = "";

                        dialog.dismiss();

                    }
                });

                ((TextView)dialog.findViewById(R.id.startDate)).setText(startDate);

                ((TextView)dialog.findViewById(R.id.endDate)).setText(endDate);

                dialog.findViewById(R.id.startDate).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View view) {

                        UIUtil.showDatePickerDialog(new OnDateSelectedListener(){

                            @Override
                            public void onDateSelected(String selectedDate) {

                                startDate = selectedDate;

                                ((TextView)view).setText(selectedDate);

                            }
                        });

                    }
                });

                dialog.findViewById(R.id.endDate).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View view) {

                        UIUtil.showDatePickerDialog(new OnDateSelectedListener(){

                            @Override
                            public void onDateSelected(String selectedDate) {

                                endDate = selectedDate;

                                ((TextView)view).setText(selectedDate);

                            }
                        });

                    }
                });

                dialog.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        ((ImageView)rootView.findViewById(R.id.due_date)).setImageResource(R.drawable.calendar);

                        dialog.dismiss();

                    }
                });

                dialog.show();

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

        WebserviceRequestUtil.addEvent(title, description, startDate, endDate, model, webserviceFinishListener);

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

        return rootView = inflater.inflate(R.layout.fragment_new_event, null);
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
