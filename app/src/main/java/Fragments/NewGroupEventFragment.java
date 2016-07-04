package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import DataModels.RecieversModel;
import Interfaces.OnDateSelectedListener;
import Interfaces.OnWebserviceFinishListener;
import UserUtils.UIUtil;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;

public class NewGroupEventFragment extends NewEventFragment {

    @Override
    public void onResume() {

        super.onResume();

        processGroupStaff();

    }

}
