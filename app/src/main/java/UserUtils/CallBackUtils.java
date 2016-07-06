package UserUtils;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Objects;

import Adapters.GroupStreamListAdapter;
import Adapters.PostListAdapter;
import CustomComponent.PollLayout;
import CustomComponent.ToggleLike;
import DataModels.Post;
import Enums.ResponseCode;
import Interfaces.AbstractCallback;
import Interfaces.OnWebserviceFinishListener;
import Interfaces.PostFactory;
import Interfaces.YesNoDialogListener;
import Managers.FragmentManager;
import Managers.SessionManager;

public class CallBackUtils {

    public static void unlikePost(final Post post, final ToggleLike toggleLike, final AbstractCallback callback){

        toggleLike.setClickable(false);

        WebserviceRequestUtil.unlikePost(String.valueOf(post.getId()), new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                toggleLike.setClickable(true);

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    toggleLike.unLike();

                    post.setLikeCount(post.getLikeCount() - 1);

                    callback.onResult(true,null);

                }
                else{

                    UIUtil.showErrorDialog();

                    callback.onResult(false,null);

                }

            }
        });
    }

    public static void vote(PollLayout pollLayout, String postId, String voteId, final AbstractCallback callback){

        pollLayout.setClickable(false);

        WebserviceRequestUtil.votePoll(postId, voteId, new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    callback.onResult(true,null);

                }

                else{

                    callback.onResult(false,null);

                }

            }
        });

    }

    public static void showDeletePostMenu(final Post post, final YesNoDialogListener listener){

        UIUtil.showDeleteDialog( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                UIUtil.dismissSweetDialog();

                listener.no();
            }
        }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                UIUtil.showSweetLoadingView();

                WebserviceRequestUtil.deletePost(String.valueOf(post.getId()), new OnWebserviceFinishListener() {

                    @Override
                    public void onFinish(WebService webService) {

                        UIUtil.hideSweetLoadingView();

                        if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                            UIUtil.dismissSweetDialog();

                            listener.yes(null);

                        }
                        else {

                            UIUtil.showErrorDialog();

                        }

                    }
                });

            }
        },listener);

    }

    public static void processDeleteMenu(final Post post,int offset){

        CallBackUtils.showDeletePostMenu(post, new YesNoDialogListener() {

            @Override
            public void yes(Object result) {

                List<PostFactory> postFactoryList = post.getPostList();

                if(postFactoryList == null){

                    FragmentManager.popCurrentVisibleFragment();

                    return;

                }

                int index = -1;

                for(int i =0; i< postFactoryList.size(); i ++){

                    if(post.getId() == ((Post)postFactoryList.get(i)).getId()){

                        index = i;

                        break;

                    }

                }

                if(index != -1) {

                    postFactoryList.remove(index);

                    if(post.getRecyclerView().getAdapter() instanceof PostListAdapter){

                        ((PostListAdapter)post.getRecyclerView().getAdapter()).setPostFactories(postFactoryList);

                        post.getRecyclerView().getAdapter().notifyItemRemoved(index + 1);

                    }
                    else if(post.getRecyclerView().getAdapter() instanceof GroupStreamListAdapter){

                        ((GroupStreamListAdapter)post.getRecyclerView().getAdapter()).setPosts(postFactoryList);

                        post.getRecyclerView().getAdapter().notifyItemRemoved(index + 2);

                    }
                }

            }

            @Override
            public void no() {

            }

        });

    }

    public static void likePost(final Post post, final ToggleLike toggleLike, final AbstractCallback callback){

        toggleLike.setClickable(false);

        WebserviceRequestUtil.likePost(String.valueOf(post.getId()), new OnWebserviceFinishListener() {

            @Override
            public void onFinish(WebService webService) {

                toggleLike.setClickable(true);

                if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                    toggleLike.like();

                    post.setLikeCount(post.getLikeCount()+1);

                    callback.onResult(true,null);

                }
                else{

                    UIUtil.showErrorDialog();

                    callback.onResult(false,null);

                }

            }
        });

    }
}
