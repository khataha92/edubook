package ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import DataModels.Group;
import Managers.FragmentManager;
import UserUtils.Application;
import edubook.edubook.R;

/**
 * Created by mac on 6/20/16.
 */
public class GroupItemViewHolder extends RecyclerView.ViewHolder {

    Group group;

    private View itemView;

    public GroupItemViewHolder(View itemView,Group group) {

        super(itemView);

        this.itemView = itemView;

        this.group = group;

        initializeView();

    }

    private void initializeView(){

        String label = "";

        if(group != null) {

            label = group.getName();

        }
        else{

            label = Application.getContext().getString(R.string.all_groups);

        }

        ((TextView) itemView.findViewById(R.id.group_name)).setText(label);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showGroupStream(group.getId());

            }
        });

    }
}
