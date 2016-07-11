package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import DataModels.AddNewPostDataModel;
import DataModels.GenericViewHolderDataContainer;
import Enums.HolderType;
import ViewHolders.GenericViewHolder;
import Interfaces.PostFactory;

/**
 * Created by lap on 6/12/16.
 */
public class PostListAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    List<PostFactory> postFactories;

    RecyclerView recyclerView;

    boolean shouldReload = true;

    public void setRecyclerView(RecyclerView recyclerView) {

        this.recyclerView = recyclerView;

    }

    public void setPostFactories(List<PostFactory> postFactories) {

        this.postFactories = postFactories;

    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GenericViewHolderDataContainer container = new GenericViewHolderDataContainer();

        switch (viewType){

            case 0:

                container.setType(HolderType.NEW_NOTE);

                AddNewPostDataModel dataModel = new AddNewPostDataModel();

                dataModel.setRecyclerView(recyclerView);

                dataModel.setPostList(postFactories);

                container.setValue(dataModel);

                break;

            default:

                container.setType(postFactories.get(viewType-1).getPostFactory().getType());

                container.setValue(postFactories.get(viewType-1).getPostFactory().getValue());

                break;
        }





        return GenericViewHolder.getViewHolder(parent,container);
    }

    public void setShouldReload(boolean shouldReload) {

        this.shouldReload = shouldReload;

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
