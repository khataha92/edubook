package ViewHolders.GroupViewHolders;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import DataModels.AddNewPostDataModel;
import DataModels.Group;
import DataModels.Post;
import DataModels.PostDataContainer;
import Enums.ResponseCode;
import Fragments.GroupFragment;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import UserUtils.Application;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import ViewHolders.GenericViewHolder;
import ViewHolders.PostViewHolders.GenericPostViewHolder;
import edubook.edubook.R;


public class GroupHeaderViewHolder extends GenericViewHolder {

    public GroupHeaderViewHolder(View itemView, PostDataContainer container) {

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

        itemView.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showGroupSettingsFragment(group);

            }
        });

    }




}
