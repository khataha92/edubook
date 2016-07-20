package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import DataModels.GenericViewHolderDataContainer;
import DataModels.Progress;
import DataModels.User;
import Enums.HolderType;
import ViewHolders.GenericViewHolder;

/**
 * Created by mac on 7/11/16.
 */
public class ProgressAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    List<Progress> progress;

    User user ;

    public ProgressAdapter(List<Progress> progresses,User user){

        progress = progresses;

        this.user = user;

    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GenericViewHolderDataContainer container;

        switch (viewType){

            case 0:

                container = new GenericViewHolderDataContainer();

                container.setType(HolderType.PROGRESS_HEADER);

                container.setValue(user);

                return GenericViewHolder.getViewHolder(parent,container);

            case 1:

                container = new GenericViewHolderDataContainer();

                container.setType(HolderType.PROGRESS_STICKY);

                return GenericViewHolder.getViewHolder(parent,container);

            case 2:

                container = new GenericViewHolderDataContainer();

                container.setType(HolderType.EMPTY);

                container.setValue(20);

                return GenericViewHolder.getViewHolder(parent,container);

            default:

                container = new GenericViewHolderDataContainer();

                container.setType(HolderType.PROGRESS);

                container.setValue(progress.get(viewType - 3));

                return GenericViewHolder.getViewHolder(parent,container);

        }

    }

    @Override
    public int getItemViewType(int position) {

        return position;

    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 2 + progress.size();

    }
}
