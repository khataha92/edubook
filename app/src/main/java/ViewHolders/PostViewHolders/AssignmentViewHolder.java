package ViewHolders.PostViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import DataModels.Post;
import DataModels.PostDataContainer;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class AssignmentViewHolder extends GenericPostViewHolder {

    public AssignmentViewHolder(View itemView, PostDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Post assignment = (Post) container.getValue();

        ((TextView) itemView.findViewById(R.id.title)).setText(assignment.getAssignment().getTitle());

        ((TextView) itemView.findViewById(R.id.title)).setTypeface(FontUtil.getFont(FontsType.REGULAR));

        ((TextView) itemView.findViewById(R.id.description)).setText(assignment.getAssignment().getDescription());

        ((TextView) itemView.findViewById(R.id.description)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        ((TextView) itemView.findViewById(R.id.due_date)).setText(assignment.getAssignment().getDuedate());

        ((TextView) itemView.findViewById(R.id.due_date)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        if(assignment.getAttachments().size() > 0) {

            ((TextView) itemView.findViewById(R.id.attachment_name)).setText(assignment.getAttachments().get(0).getName());

            ((TextView) itemView.findViewById(R.id.attachment_name)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

            String type = assignment.getAttachments().get(0).getName();

            type = type.substring(type.lastIndexOf(".")+1);

            ((TextView) itemView.findViewById(R.id.attachment_type)).setText(type);

            ((TextView) itemView.findViewById(R.id.attachment_type)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

            int attachmentResourceId = getTypeResourceId(type);

            ((ImageView) itemView.findViewById(R.id.attachment_icon)).setImageResource(attachmentResourceId);

        }
        else{

            itemView.findViewById(R.id.attachment_layout).setVisibility(View.GONE);

            itemView.findViewById(R.id.line).setVisibility(View.GONE);

        }
    }



    private int getTypeResourceId(String type){

        int attachmentResourceId = R.drawable.folder;

        Map<String,Integer> icons = new HashMap<>();

        icons.put("doc",R.drawable.docx);

        icons.put("docx",R.drawable.docx);

        icons.put("pdf",R.drawable.pdf);

        icons.put("xls",R.drawable.xlsx);

        icons.put("xlsx",R.drawable.xlsx);

        icons.put("ppt",R.drawable.pptx);

        icons.put("pptx",R.drawable.pptx);

        if(icons.containsKey(type)){

            attachmentResourceId = icons.get(type);
        }

        return attachmentResourceId;

    }
}
