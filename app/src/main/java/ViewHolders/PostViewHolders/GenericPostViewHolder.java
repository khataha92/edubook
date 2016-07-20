package ViewHolders.PostViewHolders;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import CustomComponent.ToggleLike;
import DataModels.Post;
import DataModels.GenericViewHolderDataContainer;
import Interfaces.PostFactory;
import Managers.FragmentManager;
import UserUtils.Application;
import UserUtils.CallBackUtils;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import ViewHolders.GenericViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;
import Interfaces.AbstractCallback;

public class GenericPostViewHolder extends GenericViewHolder {

    Post post;

    int indexInList = -1;

    public void setIndexInList(int indexInList) {

        this.indexInList = indexInList;

    }

    public GenericPostViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView, container);

    }

    @Override
    public void initializeView() {

        if(post == null) {

            post = (Post) container.getValue();

        }

        initComponents();

    }

    private void setLikeCount(Post post){

        String likes = Application.getContext().getString(R.string.likes);

        ((TextView) itemView.findViewById(R.id.likes)).setText(post.getLikeCount() + " " + likes);

        ((TextView) itemView.findViewById(R.id.likes)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

    }

    private void initComponents(){

        View.OnClickListener viewProgress = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showProgressFragment(post.getCreator());

            }
        };

        itemView.findViewById(R.id.profile_image).setOnClickListener(viewProgress);

        itemView.findViewById(R.id.name).setOnClickListener(viewProgress);

        ((TextView) itemView.findViewById(R.id.name)).setText(post.getCreator().getName());

        ((TextView) itemView.findViewById(R.id.name)).setTypeface(FontUtil.getFont(FontsType.REGULAR));

        ((TextView) itemView.findViewById(R.id.age)).setText(post.getAge());

        ((TextView) itemView.findViewById(R.id.age)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        setLikeCount(post);

        final String comments = Application.getContext().getString(R.string.comments);

        ((TextView) itemView.findViewById(R.id.comments)).setText(post.getCommentCount() + " " + comments);

        ((TextView) itemView.findViewById(R.id.comments)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        ((TextView) itemView.findViewById(R.id.comment)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        ((TextView) itemView.findViewById(R.id.share)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        CircleImageView imageView = (CircleImageView) itemView.findViewById(R.id.profile_image);

        UIUtil.loadImageFromUrl(imageView,post.getCreator().getThumb());

        final ToggleLike toggleLike = (ToggleLike) itemView.findViewById(R.id.toggleLike);

        toggleLike.setPost(post);

        if(post.getLiked() == 0){

            toggleLike.unLike();

        }
        else {

            toggleLike.like();

        }

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showPostViewFragment(String.valueOf(post.getId()), new AbstractCallback() {

                    @Override
                    public void onResult(boolean isSuccess, Object result) {

                        if(isSuccess){

                            int postIndex = getPostIndex(post);

                            Post tempPost = (Post) result;

                            tempPost.setPostList(post.getPostList());

                            tempPost.setRecyclerView(post.getRecyclerView());

                            post.getPostList().set(postIndex,tempPost);

                            post = tempPost;

                            initComponents();

                            if(GenericPostViewHolder.this instanceof PollViewHolder){

                                String optionId = post.getPoll().getVoted().getId();

                                ((PollViewHolder)GenericPostViewHolder.this).getPollLayout().refreshVotes(optionId);

                            }

                        }

                    }
                });

            }
        });

        itemView.findViewById(R.id.postMenu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CallBackUtils.processDeleteMenu(post,1);

            }
        });

        toggleLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(post.getLiked() == 1){

                    CallBackUtils.unlikePost(post, toggleLike, new AbstractCallback() {

                        @Override
                        public void onResult(boolean isSuccess, Object result) {

                            if(isSuccess){

                                setLikeCount(post);

                            }

                        }
                    });

                }
                else{

                    CallBackUtils.likePost(post, toggleLike, new AbstractCallback() {

                        @Override
                        public void onResult(boolean isSuccess, Object result) {

                            if(isSuccess){

                                setLikeCount(post);

                            }

                        }
                    });

                }

            }

        });
    }

    private int getPostIndex(Post post){

        List<PostFactory> postList = post.getPostList();

        if(postList == null) return  -1;

        int index = -1;

        for(int i = 0 ; i < postList.size() ; i ++){

            if(((Post)postList.get(i)).getId() == post.getId()){

                return i;

            }

        }

        return index;

    }
}
