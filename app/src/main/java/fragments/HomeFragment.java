package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import Adapters.PostListAdapter;
import DataModels.Group;
import DataModels.Post;
import DataModels.Recipient;
import DataModels.RecipientsResponse;
import DataModels.StreamBookResponse;
import DataModels.User;
import Enums.ResponseCode;
import UserUtils.UserDefaultUtil;
import activities.Home;
import Managers.SessionManager;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;
import Interfaces.OnWebserviceFinishListener;
import Interfaces.PostFactory;
import Managers.FragmentManager;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class HomeFragment extends BaseFragment {

    PostListAdapter adapter;

    RecyclerView recyclerView ;

    List<PostFactory> posts;

    public List<PostFactory> getPosts() {

        return posts;

    }

    public RecyclerView getRecyclerView() {

        return recyclerView;

    }

    private String TAG = getClass().getSimpleName();

    public HomeFragment() {

    }

    public void updatePostList(){

        posts = SessionManager.getInstance().getPosts();

        //setListAdapter();

    }

    @Override
    public void onResume() {

        super.onResume();

        if(getActivity() != null) {

            ((ImageView) getActivity().findViewById(R.id.home)).setImageResource(R.drawable.home);

            ((Home) getActivity()).replaceIcon();

            UIUtil.showTabsView();

        }

        if(posts != null && recyclerView != null){

            updatePostList();

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, null);

        UIUtil.showLoadingView(rootView);

        getUserInfo();

        initializeComponents();

        return rootView;
    }

    private void initializeComponents(){

        rootView.findViewById(R.id.bottomShadow).bringToFront();

        RelativeLayout messagesLayout = (RelativeLayout) getActivity().findViewById(R.id.messages_container);

        messagesLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager.showMessagesFragment();

            }
        });

        rootView.findViewById(R.id.addPost).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showNewPostDialog(HomeFragment.this);

            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.post_list);

        recyclerView.setItemAnimator(new LandingAnimator());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }
    private void getUserInfo(){

        OnWebserviceFinishListener listener = new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                String result = webService.getStrResponse().toString();

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()) {

                    try {

                        User user = new Gson().fromJson(result, User.class);

                        saveUserInfo(user);

                        getUserRecipients();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else{

                    WebserviceRequestUtil.processWebServiceError(webService);

                }
            }
        };

        WebserviceRequestUtil.getUserInfo(listener);
    }

    private void saveUserInfo(User user){

        SessionManager.getInstance().saveUser(user);

    }

    private void getUserRecipients(){

        OnWebserviceFinishListener listener = new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()) {

                    StringBuffer strResponse = webService.getStrResponse();

                    RecipientsResponse recipientsResponse = new Gson().fromJson(strResponse.toString(), RecipientsResponse.class);

                    saveRecipients(recipientsResponse);

                    getUserGroups();

                }

            }
        };

        WebserviceRequestUtil.getUserRecipients(listener);

    }

    private void getUserGroups(){

        OnWebserviceFinishListener listener = new OnWebserviceFinishListener() {
            @Override
            public void onFinish(WebService webService) {

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    List<LinkedTreeMap> linkedTreeMaps = new Gson().fromJson(webService.getStrResponse().toString(),ArrayList.class);

                    List<Group> groups = UserDefaultUtil.convertLinkedTreeMap(linkedTreeMaps);

                    SessionManager.getInstance().saveUserGroups(groups);

                    getStreamBook();

                }

            }
        };

        WebserviceRequestUtil.getUserGroups(listener);
    }

    private void getStreamBook(){

        OnWebserviceFinishListener listener = new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                String response = webService.getStrResponse().toString();

                StreamBookResponse streamBookResponse = new Gson().fromJson(response,StreamBookResponse.class);

                initializeRecyclerView(streamBookResponse);
            }
        };

        int i = 0;

        WebserviceRequestUtil.getStreamBook(listener);

    }

    private void initializeRecyclerView(StreamBookResponse streamBookResponse){

        posts = streamBookResponse.getPostFactory();

        for(int i = 0 ; i < posts.size() ; i++){

            PostFactory post = posts.get(i);

            ((Post)post).setRecyclerView(recyclerView);

            ((Post)post).setPostList(posts);

        }

        SessionManager.getInstance().setPosts(posts);

        setListAdapter();

        recyclerView.post(new Runnable() {

            @Override
            public void run() {

                UIUtil.hideLoadingView();

            }

        });

    }

    private void setListAdapter(){

        adapter = new PostListAdapter();

        adapter.setRecyclerView(recyclerView);

        adapter.setPostFactories(posts);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

    }

    private void saveRecipients(RecipientsResponse recipients){

        for(int i=0 ; i < recipients.getGroups().size() ; i++){

            String id = recipients.getGroups().get(i).getId();

            String name = recipients.getGroups().get(i).getName();

            Recipient recipient = new Recipient(Recipient.RecipientType.GROUP,id,name);

            SessionManager.getInstance().addRecipient(recipient);

        }

        for(int i=0 ; i < recipients.getStudents().size() ; i++){

            String id = recipients.getStudents().get(i).getId();

            String name = recipients.getStudents().get(i).getName();

            Recipient recipient = new Recipient(Recipient.RecipientType.STUDENT,id,name);

            SessionManager.getInstance().addRecipient(recipient);

        }

        for(int i = 0 ; i < recipients.getContacts().size() ; i++){

            String id = recipients.getContacts().get(i).getId();

            String name = recipients.getContacts().get(i).getName();

            Recipient recipient = new Recipient(Recipient.RecipientType.CONTACT,id,name);

            SessionManager.getInstance().addRecipient(recipient);
        }

        Recipient onlyMe ;

        String id = recipients.getOnlyme().get(0).getId();

        String name = recipients.getOnlyme().get(0).getName();

        onlyMe = new Recipient(Recipient.RecipientType.ONLY_ME,id,name);

        SessionManager.getInstance().addRecipient(onlyMe);

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
