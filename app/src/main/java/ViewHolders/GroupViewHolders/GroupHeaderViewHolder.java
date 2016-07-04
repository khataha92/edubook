package ViewHolders.GroupViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import DataModels.AddNewPostDataModel;
import DataModels.Post;
import DataModels.PostDataContainer;
import Fragments.GroupFragment;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import ViewHolders.GenericViewHolder;
import ViewHolders.PostViewHolders.GenericPostViewHolder;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class GroupHeaderViewHolder extends GenericViewHolder {

    public GroupHeaderViewHolder(View itemView, PostDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        itemView.findViewById(R.id.controller).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AddNewPostDataModel dataModel = (AddNewPostDataModel)container.getValue();

                GroupFragment fragment = (GroupFragment)dataModel.getFragment();

                String groupId = fragment.getGroupId();

                String groupName = fragment.getGroupName();

                UIUtil.showNewGroupPostDialog(fragment,groupId,groupName);

            }
        });
    }




}
