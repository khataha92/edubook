package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import DataModels.GenericViewHolderDataContainer;
import DataModels.NotificationModel;
import Enums.HolderType;
import ViewHolders.GenericViewHolder;
import ViewHolders.NotificationViewHolder;

/**
 * Created by KhalidTaha on 9/11/16.
 */
public class NotificationAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    List<NotificationModel> notificationModels;

    public NotificationAdapter(List<NotificationModel> notificationModels){

        this.notificationModels = notificationModels;

    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GenericViewHolderDataContainer container = new GenericViewHolderDataContainer();

        container.setType(HolderType.NOTIFICATION);

        container.setValue(notificationModels.get(viewType));

        return GenericViewHolder.getViewHolder(parent,container);

    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notificationModels == null ? 0 : notificationModels.size();
    }
}
