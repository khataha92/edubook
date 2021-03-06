package Fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import Adapters.GroupStreamListAdapter;
import DataModels.Group;
import DataModels.Post;
import DataModels.StreamBookResponse;
import Interfaces.FunctionCaller;
import Interfaces.OnWebserviceFinishListener;
import Interfaces.PostFactory;
import Interfaces.StickyRecyclerHeadersTouchListener;
import Managers.FragmentManager;
import UserUtils.Application;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;


public class GroupFragment extends BaseFragment {

    RecyclerView recyclerView ;

    Group group;

    public void setGroup(Group group) {

        this.group = group;

    }

    public Group getGroup() {

        return group;

    }

    List<PostFactory> posts;

    StickyRecyclerHeadersDecoration headersDecor;

    private String TAG = getClass().getSimpleName();

    public GroupFragment() {

    }

    public RecyclerView getRecyclerView() {

        return recyclerView;

    }

    public List<PostFactory> getPosts() {

        return posts;

    }

    @Override
    public void onResume() {

        super.onResume();

        UIUtil.hideTabsView();

        if(recyclerView != null && recyclerView.getAdapter() != null){

            recyclerView.getAdapter().notifyDataSetChanged();

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_group, null);

        UIUtil.showLoadingView(rootView);

        initializeComponents();

        return rootView;
    }

    private void initializeComponents(){

        recyclerView = (RecyclerView) rootView.findViewById(R.id.groupItemsList);

        recyclerView.setItemAnimator(new SlideInRightAnimator());

        Button getGroupLibrary = (Button) rootView.findViewById(R.id.getGroupLibrary);

        Button getGroupMembers = (Button) rootView.findViewById(R.id.getGroupMembers);

        getGroupLibrary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showLibraryFragment(null, new FunctionCaller() {

                    @Override
                    public void callFunction(Object object) {

                        OnWebserviceFinishListener listener = (OnWebserviceFinishListener)object;

                        WebserviceRequestUtil.getGroupLibrary(group.getId(),listener);

                    }
                });

            }

        });

        getGroupMembers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager.showGroupMembersFragment(group);

            }
        });

        getGroupStream();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }

    private void getGroupStream(){

        OnWebserviceFinishListener listener = new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                UIUtil.hideLoadingView();

                String response = webService.getStrResponse().toString();

                StreamBookResponse bookResponse = new Gson().fromJson(response,StreamBookResponse.class);

                initializeRecyclerView(bookResponse);
            }
        };

        WebserviceRequestUtil.getGroupStream(group.getId(),listener);

    }

    private void initializeRecyclerView(StreamBookResponse streamBookResponse){

        recyclerView.setHasFixedSize(true);


        posts = streamBookResponse.getPostFactory();

        for(PostFactory post:posts){

            ((Post)post).setRecyclerView(recyclerView);

        }

        setListAdapter(posts);

        recyclerView.post(new Runnable() {

            @Override
            public void run() {

                UIUtil.hideLoadingView();

            }

        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                View view = recyclerView.getChildAt(0);

                if(view != null && recyclerView.getChildAdapterPosition(view) == 0)  {

                    view.setTranslationY(-view.getTop() / 2);// or use view.animate().translateY();
                }


            }
        });

    }

    private void setListAdapter(List<PostFactory> posts){

        GroupStreamListAdapter adapter = new GroupStreamListAdapter();

        adapter.setRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(linearLayoutManager.findFirstVisibleItemPosition() >= 1){

                    rootView.findViewById(R.id.sticky).setVisibility(View.VISIBLE);

                }
                else {

                    rootView.findViewById(R.id.sticky).setVisibility(View.GONE);

                }

            }
        });

        adapter.setFragment(GroupFragment.this);

        for(int i=0; i<posts.size(); i++){

            Post post = (Post) posts.get(i);

            post.setPostList(this.posts);

        }

//        headersDecor = new StickyRecyclerHeadersDecoration(adapter);

//        recyclerView.addItemDecoration(headersDecor);

//        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//
//            @Override public void onChanged() {
//
//                headersDecor.invalidateHeaders();
//            }
//        });


        adapter.setPosts(posts);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        final StickyRecyclerHeadersTouchListener touchListener = new StickyRecyclerHeadersTouchListener(recyclerView, headersDecor);

//        touchListener.setOnHeaderClickListener(new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
//
//            @Override
//            public void onHeaderClick(MotionEvent event,final View header, int position, long headerId) {
//
//                int screenWidth = UIUtil.getScreenSize().x;
//
//                if(UserDefaultUtil.getUserLanguage().equalsIgnoreCase("en")) {
//
//                    if (event.getX() < screenWidth / 2) {
//
//                        FragmentManager.showLibraryFragment(null, new FunctionCaller() {
//
//                            @Override
//                            public void callFunction(Object object) {
//
//                                OnWebserviceFinishListener listener = (OnWebserviceFinishListener) object;
//
//                                WebserviceRequestUtil.getGroupLibrary(group.getId(), listener);
//
//                            }
//                        });
//
//                    } else {
//
//                        FragmentManager.showGroupMembersFragment(group);
//
//                    }
//
//                }
//                else{
//                    if (event.getX() < screenWidth / 2) {
//
//                        FragmentManager.showGroupMembersFragment(group);
//
//                    } else {
//
//                        FragmentManager.showLibraryFragment(null, new FunctionCaller() {
//
//                            @Override
//                            public void callFunction(Object object) {
//
//                                OnWebserviceFinishListener listener = (OnWebserviceFinishListener) object;
//
//                                WebserviceRequestUtil.getGroupLibrary(group.getId(), listener);
//
//                            }
//                        });
//
//                    }
//
//
//                }
//
//            }
//        });

//        recyclerView.addOnItemTouchListener(touchListener);

        recyclerView.setAdapter(adapter);

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
