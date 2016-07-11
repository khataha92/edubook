package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import DataModels.AddNewPostDataModel;
import DataModels.GenericViewHolderDataContainer;
import Enums.HolderType;
import Fragments.BaseFragment;
import Fragments.GroupFragment;
import Interfaces.PostFactory;
import ViewHolders.GenericViewHolder;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class GroupStreamListAdapter extends RecyclerView.Adapter<GenericViewHolder> implements StickyRecyclerHeadersAdapter {

    List<PostFactory> posts;

    public void setPosts(List<PostFactory> posts) {

        this.posts = posts;

    }

    RecyclerView recyclerView;

    BaseFragment fragment;

    public void setFragment(BaseFragment fragment) {

        this.fragment = fragment;

    }

    public void setRecyclerView(RecyclerView recyclerView) {

        this.recyclerView = recyclerView;

    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GenericViewHolderDataContainer container = new GenericViewHolderDataContainer();

        switch (viewType){

            case 0:

                container.setType(HolderType.GROUP_HEADER);

                AddNewPostDataModel dataModel = new AddNewPostDataModel();

                dataModel.setRecyclerView(recyclerView);

                dataModel.setPostList(posts);

                dataModel.setFragment(fragment);

                container.setValue(dataModel);

                break;

            case 1:

                container.setType(HolderType.EMPTY);

                container.setValue(null);

                break;

            default:

                container.setType(posts.get(viewType-2).getPostFactory().getType());

                container.setValue(posts.get(viewType-2).getPostFactory().getValue());

                break;

        }





        return GenericViewHolder.getViewHolder(parent,container);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {

        if(position == 0){

            ((TextView)holder.itemView.findViewById(R.id.group_desc)).setText(((GroupFragment)fragment).getGroup().getDescription());

        }

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public long getHeaderId(int position) {

        if(position == 0){

            return  -1;
        }

        return 999;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {

        GenericViewHolderDataContainer container = new GenericViewHolderDataContainer();

        container.setType(HolderType.GROUP_STICKY);

        container.setValue(null);

        return GenericViewHolder.getViewHolder(parent,container);

    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return posts.size()+2;
    }
}
