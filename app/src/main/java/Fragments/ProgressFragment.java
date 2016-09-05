package Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import Adapters.ProgressAdapter;
import Adapters.ProgressHeaderViewPagerAdapter;
import DataModels.Progress;
import DataModels.ProgressDataModel;
import DataModels.User;
import Enums.ResponseCode;
import Interfaces.OnWebserviceFinishListener;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.Constants;
import UserUtils.ImageFilePath;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import activities.CropActivity;
import edubook.edubook.R;

public class ProgressFragment extends BaseFragment {

    RecyclerView recyclerView;

    User user = SessionManager.getInstance().getCurrentUser();

    public void setUser(User user) {

        this.user = user;

    }

    public ProgressFragment() {


    }

    public void goCrop(Uri sourUri) {

        Intent intent = new Intent(getContext(), CropActivity.class);

        intent.setData(sourUri);

        getActivity().startActivityForResult(intent, Constants.REQUEST_CROP);

    }

    public void uploadImage(Intent data){

        final Uri imageUri = data.getData();

        try {

            String imagePath = ImageFilePath.getPath(getContext(),data.getData());

            WebserviceRequestUtil.changeUserProfile(imagePath, new OnWebserviceFinishListener() {

                @Override
                public void onFinish(WebService webService) {

                    try {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(Application.getCurrentActivity().getContentResolver(), imageUri);

                        ProgressHeaderViewPagerAdapter adapter = (ProgressHeaderViewPagerAdapter)

                                ((ProgressAdapter)recyclerView.getAdapter()).getHeader().getViewPager().getAdapter();

                        adapter.getImageView().setImageBitmap(bitmap);

                    }

                    catch (Exception e){

                        e.printStackTrace();

                    }

                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void prepareFragment(){

        UIUtil.showLoadingView(rootView);

        WebserviceRequestUtil.getStudentProgress(user.getId(),new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                ProgressDataModel dataModel;

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    dataModel = new Gson().fromJson(webService.getStrResponse().toString().trim(), ProgressDataModel.class);

                }
                else{

                    dataModel = new ProgressDataModel();

                    dataModel.setProgress(new ArrayList<Progress>());

                }

                addProgress(dataModel);

            }
        });

    }

    public void addProgress(ProgressDataModel dataModel){

        recyclerView = (RecyclerView) rootView.findViewById(R.id.progressLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new ProgressAdapter(dataModel.getProgress(),user));

        UIUtil.hideLoadingView();

    }

    @Override
    public void onResume() {

        super.onResume();

        UIUtil.hideTabsView();

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

        return rootView = inflater.inflate(R.layout.fragment_progress, null);
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
