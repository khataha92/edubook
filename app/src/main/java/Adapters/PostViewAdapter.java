package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import DataModels.Post;
import DataModels.GenericViewHolderDataContainer;
import Enums.HolderType;
import UserUtils.UserDefaultUtil;
import ViewHolders.GenericViewHolder;

public class PostViewAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    Post post;

    public void setPost(Post post) {

        this.post = post;

    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GenericViewHolderDataContainer container = new GenericViewHolderDataContainer();

        switch (viewType){

            case 0:

                container.setType(UserDefaultUtil.getPostType(post));

                container.setValue(post);

                break;

            default:

                container.setType(HolderType.COMMENT);

                container.setValue(post.getComments().get(viewType-1));

        }

        return GenericViewHolder.getViewHolder(parent,container);

    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {

        return post.getComments().size() + 1;

    }

    @Override
    public int getItemViewType(int position) {

        return position;

    }
}
