package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Adapters.PostListAdapter;
import DataModels.NotificationCount;
import DataModels.Post;
import DataModels.StreamBookResponse;
import Enums.ResponseCode;
import Interfaces.OnWebserviceFinishListener;
import Interfaces.PostFactory;
import Managers.SessionManager;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import activities.Home;
import edubook.edubook.R;
import jp.wasabeef.recyclerview.animators.LandingAnimator;


public class HomeFragment extends BaseFragment {

    PostListAdapter adapter;

    RecyclerView recyclerView ;

    List<PostFactory> posts= new ArrayList<>();

    String nextPageNumber = "";

    boolean isGettingNextPage = false;

    OnWebserviceFinishListener listener = new OnWebserviceFinishListener() {

        @Override
        public void onFinish(WebService webService) {

            isGettingNextPage = false;

            String response = webService.getStrResponse().toString();

            StreamBookResponse streamBookResponse = new Gson().fromJson(response,StreamBookResponse.class);

            initializeRecyclerView(streamBookResponse);
        }
    };

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

        return rootView;

    }

    public void getUserInfo(){

        UserDefaultUtil.getUserInfo(listener);
    }

    public void getNotifications(){

        WebserviceRequestUtil.getNotificationCount(new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()) {

                    TextView notificationCount = (TextView) getActivity().findViewById(R.id.notif_number);

                    if (notificationCount != null) {

                        int count = new Gson().fromJson(webService.getStrResponse().toString(),NotificationCount.class).getCount();

                        if(count > 0) {

                            notificationCount.setText(String.valueOf(count));

                        }
                        else{

                            notificationCount.setVisibility(View.GONE);

                        }

                    }

                }

            }
        });

    }

    public void initializeComponents(){

        getUserInfo();

        try {

            rootView.findViewById(R.id.bottomShadow).bringToFront();

            rootView.findViewById(R.id.addPost).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    UIUtil.showNewPostDialog(HomeFragment.this);

                }
            });

            recyclerView = (RecyclerView) rootView.findViewById(R.id.post_list);

            recyclerView.setItemAnimator(new LandingAnimator());

        }
        catch (Exception e){

            Log.e(TAG,"error !",e);

        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initializeComponents();

        getNotifications();

    }

    public void initializeRecyclerView(StreamBookResponse streamBookResponse){

        if(posts == null) {

            posts = new ArrayList<>();

        }

        posts.addAll(streamBookResponse.getPostFactory());

        nextPageNumber = streamBookResponse.getNextPageNumber();

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

    public void setListAdapter(){

        if(adapter == null) {

            adapter = new PostListAdapter();

            adapter.setRecyclerView(recyclerView);

            adapter.setPostFactories(posts);

            Log.d("status","new Adapter ");

        }

        if(recyclerView.getLayoutManager() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }

        if(recyclerView.getAdapter() == null) {

            recyclerView.setAdapter(adapter);

            Log.d("status","setAdapter");

        }
        else{

            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    Log.d("status","notifyDataSetChanged");

                    int position = recyclerView.computeVerticalScrollOffset();

                    //recyclerView.getAdapter().notifyDataSetChanged();

                    recyclerView.scrollToPosition(position);

                }
            });



        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int position = manager.findLastVisibleItemPosition();

                if(position == adapter.getItemCount() -1 ){

                    getNextPagePosts();

                }

            }
        });

    }

    private void getNextPagePosts(){

        if(!isGettingNextPage){

            isGettingNextPage = true;

            if(nextPageNumber.equalsIgnoreCase("1")){

                isGettingNextPage = false;

                return;

            }

            WebserviceRequestUtil.getStreamBook(nextPageNumber,listener);

        }

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
