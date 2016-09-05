package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import Adapters.GroupStreamListAdapter;
import Adapters.PostViewAdapter;
import DataModels.Comment;
import DataModels.Post;
import DataModels.StreamBookResponse;
import Enums.ResponseCode;
import Interfaces.AbstractCallback;
import Interfaces.OnWebserviceFinishListener;
import Interfaces.PostFactory;
import Managers.FragmentManager;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;


public class PostViewFragment extends BaseFragment {

    RecyclerView recyclerView ;

    String postId;

    Post post;

    boolean success = true;

    EditText commentText;

    AbstractCallback postListener;

    public void setPostListener(AbstractCallback postListener) {

        this.postListener = postListener;

    }

    public void setPostId(String postId) {

        this.postId = postId;

    }

    private String TAG = getClass().getSimpleName();

    public PostViewFragment() {

    }

    public RecyclerView getRecyclerView() {

        return recyclerView;

    }


    @Override
    public void onResume() {

        super.onResume();

        UIUtil.hideTabsView();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_post_view, null);

        UIUtil.showLoadingView(rootView);

        initializeComponents();

        return rootView;
    }

    private void initializeComponents(){

        recyclerView = (RecyclerView) rootView.findViewById(R.id.postItems);

        rootView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager.popCurrentVisibleFragment();

            }
        });

        commentText = (EditText) rootView.findViewById(R.id.comment_text);

        Button addComment = (Button) rootView.findViewById(R.id.addComment);

        addComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showSweetLoadingView();

                String comment = commentText.getText().toString();

                WebserviceRequestUtil.addComment(postId, comment, new OnWebserviceFinishListener() {

                    @Override
                    public void onFinish(WebService webService) {

                        UIUtil.hideSweetLoadingView();

                        if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                            Comment cmt = new Gson().fromJson(webService.getStrResponse().toString(),Comment.class);

                            if(post != null){

                                post.getComments().add(cmt);

                                post.setCommentCount(post.getCommentCount()+1);

                            }

                            initializeRecyclerView(post);

                            commentText.setText("");

                        }

                        else {

                            UIUtil.showErrorDialog();
                        }

                    }
                });

            }
        });

        getPostDetails();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }

    private void getPostDetails(){

        WebserviceRequestUtil.getPostDetails(postId, new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    post = new Gson().fromJson(webService.getStrResponse().toString(),Post.class);

                    initializeRecyclerView(post);

                }
                else {

                    success = false;

                    UIUtil.showErrorDialog();

                }

            }
        });

    }

    private void initializeRecyclerView(Post post){

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setListAdapter(post);

        recyclerView.post(new Runnable() {

            @Override
            public void run() {

                UIUtil.hideLoadingView();

            }

        });

    }

    private void setListAdapter(Post post){

        PostViewAdapter adapter = new PostViewAdapter();

        adapter.setPost(post);

        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }
    @Override
    public void onDetach() {

        super.onDetach();

        if(postListener != null) {

            postListener.onResult(success, post);

        }

    }

}
