package UserUtils;

import android.content.DialogInterface;
import android.view.View;

import Adapters.PostListAdapter;
import CustomComponent.PollLayout;
import CustomComponent.ToggleLike;
import DataModels.Post;
import Enums.ResponseCode;
import Interfaces.AbstractCallback;
import Interfaces.OnWebserviceFinishListener;
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

    public static void showDeletePostMenu(final Post post, DialogInterface.OnDismissListener listener){

        UIUtil.showDeleteDialog( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                UIUtil.dismissSweetDialog();

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

                            int position = SessionManager.getInstance().getPosts().indexOf(post);

                            SessionManager.getInstance().getPosts().remove(position);

                            UIUtil.dismissSweetDialog();

                        }
                        else {

                            UIUtil.showErrorDialog();

                        }

                    }
                });

            }
        },listener);

    }

    public static void processDeleteMenu(final Post post, final int offset, DialogInterface.OnDismissListener listener){

        CallBackUtils.showDeletePostMenu(post,listener);

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
