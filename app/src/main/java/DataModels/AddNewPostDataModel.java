package DataModels;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import Fragments.BaseFragment;
import Interfaces.PostFactory;

public class AddNewPostDataModel {

    BaseFragment fragment;

    RecyclerView recyclerView;

    List<PostFactory> postList;

    public void setFragment(BaseFragment fragment) {

        this.fragment = fragment;

    }

    public BaseFragment getFragment() {

        return fragment;

    }

    public void setPostList(List<PostFactory> postList) {

        this.postList = postList;

    }

    public List<PostFactory> getPostList() {

        return postList;

    }

    public RecyclerView getRecyclerView() {

        return recyclerView;

    }

    public void setRecyclerView(RecyclerView recyclerView) {

        this.recyclerView = recyclerView;

    }
}
