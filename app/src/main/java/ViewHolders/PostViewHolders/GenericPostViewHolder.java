package ViewHolders.PostViewHolders;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import CustomComponent.ToggleLike;
import DataModels.Post;
import DataModels.PostDataContainer;
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


    public GenericPostViewHolder(View itemView, PostDataContainer container) {

        super(itemView, container);

    }

    @Override
    public void initializeView() {

        final Post post = (Post) container.getValue();

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

        itemView.findViewById(R.id.postMenu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CallBackUtils.processDeleteMenu(post, 1, new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {



                    }
                });

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

    private void setLikeCount(Post post){

        String likes = Application.getContext().getString(R.string.likes);

        ((TextView) itemView.findViewById(R.id.likes)).setText(post.getLikeCount() + " " + likes);

        ((TextView) itemView.findViewById(R.id.likes)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

    }
}
