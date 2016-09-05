package ViewHolders.GroupViewHolders;

import android.view.View;
import android.widget.TextView;

import DataModels.AddNewPostDataModel;
import DataModels.Group;
import DataModels.GenericViewHolderDataContainer;
import Fragments.GroupFragment;
import Managers.FragmentManager;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import ViewHolders.GenericViewHolder;
import edubook.edubook.R;


public class GroupHeaderViewHolder extends GenericViewHolder {

    public GroupHeaderViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        AddNewPostDataModel dataModel = (AddNewPostDataModel)container.getValue();

        final GroupFragment fragment = (GroupFragment)dataModel.getFragment();

        final Group group = fragment.getGroup();

        final String groupId = group.getId();

        final String groupName = group.getName();

        itemView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.popCurrentVisibleFragment();

            }
        });

        itemView.findViewById(R.id.controller).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UIUtil.showNewGroupPostDialog(fragment,groupId,groupName);

            }
        });

        ((TextView)itemView.findViewById(R.id.group_name)).setText(groupName);

        String groupDescription = group.getDescription();

        ((TextView)itemView.findViewById(R.id.group_desc)).setText(groupDescription!=null ? groupDescription:"");

        itemView.findViewById(R.id.settings).setVisibility(UserDefaultUtil.isStudent()?View.GONE:View.VISIBLE);

        itemView.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showGroupSettingsFragment(group);

            }
        });

    }




}
