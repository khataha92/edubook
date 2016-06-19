package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import DataModels.PostDataContainer;
import Enums.HolderType;
import ViewHolders.GenericViewHolder;
import ViewHolders.NoteViewHolder;
import interfaces.PostFactory;

/**
 * Created by lap on 6/12/16.
 */
public class PostListAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    List<PostFactory> postFactories;

    public void setPostFactories(List<PostFactory> postFactories) {

        this.postFactories = postFactories;

    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        PostDataContainer container = new PostDataContainer();

        switch (viewType){

            case 0:

                container.setType(HolderType.NEW_NOTE);

                container.setValue(null);

                break;

            default:

                container.setType(postFactories.get(viewType-1).getPostFactory().getType());

                container.setValue(postFactories.get(viewType-1).getPostFactory().getValue());

                break;
        }





        return GenericViewHolder.getViewHolder(parent,container);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {
        return postFactories.size()+1;
    }
}
