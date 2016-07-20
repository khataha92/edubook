package ViewHolders.PostViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import CustomComponent.FlexibleTextView;
import DataModels.Post;
import DataModels.GenericViewHolderDataContainer;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class AssignmentViewHolder extends GenericPostViewHolder {

    public AssignmentViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Post assignment = (Post) container.getValue();

        ((TextView) itemView.findViewById(R.id.title)).setText(assignment.getAssignment().getTitle());

        ((TextView) itemView.findViewById(R.id.title)).setTypeface(FontUtil.getFont(FontsType.REGULAR));

        ((FlexibleTextView) itemView.findViewById(R.id.description)).setText(assignment.getAssignment().getDescription());

        ((TextView) itemView.findViewById(R.id.due_date)).setText(assignment.getAssignment().getDuedate());

        ((TextView) itemView.findViewById(R.id.due_date)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        if(assignment.getAttachments().size() > 0) {

            ((TextView) itemView.findViewById(R.id.attachment_name)).setText(assignment.getAttachments().get(0).getName());

            ((TextView) itemView.findViewById(R.id.attachment_name)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

            String type = assignment.getAttachments().get(0).getName();

            type = type.substring(type.lastIndexOf(".")+1);

            ((TextView) itemView.findViewById(R.id.attachment_type)).setText(type);

            ((TextView) itemView.findViewById(R.id.attachment_type)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

            int attachmentResourceId = UIUtil.getTypeResourceId(type);

            ((ImageView) itemView.findViewById(R.id.attachment_icon)).setImageResource(attachmentResourceId);

        }
        else{

            itemView.findViewById(R.id.attachment_layout).setVisibility(View.GONE);

            itemView.findViewById(R.id.line).setVisibility(View.GONE);

        }
    }




}
