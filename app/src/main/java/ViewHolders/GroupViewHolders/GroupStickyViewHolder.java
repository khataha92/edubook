package ViewHolders.GroupViewHolders;

import android.view.View;
import android.widget.Button;

import DataModels.Group;
import DataModels.GenericViewHolderDataContainer;
import Interfaces.FunctionCaller;
import Interfaces.OnWebserviceFinishListener;
import Managers.FragmentManager;
import UserUtils.WebserviceRequestUtil;
import ViewHolders.GenericViewHolder;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class GroupStickyViewHolder extends GenericViewHolder {

    public GroupStickyViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Group group = (Group) container.getValue();

        Button getGroupLibrary = (Button) itemView.findViewById(R.id.getGroupLibrary);

        Button getGroupMembers = (Button) itemView.findViewById(R.id.getGroupMembers);

        getGroupLibrary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showLibraryFragment(null, new FunctionCaller() {

                    @Override
                    public void callFunction(Object object) {

                        OnWebserviceFinishListener listener = (OnWebserviceFinishListener)object;

                        WebserviceRequestUtil.getGroupLibrary(group.getId(),listener);

                    }
                });

            }

        });

    }


}
