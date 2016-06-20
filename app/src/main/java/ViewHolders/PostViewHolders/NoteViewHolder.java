package ViewHolders.PostViewHolders;

import android.view.View;
import android.widget.TextView;

import DataModels.Post;
import DataModels.PostDataContainer;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import ViewHolders.PostViewHolders.GenericPostViewHolder;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class NoteViewHolder extends GenericPostViewHolder {

    public NoteViewHolder(View itemView, PostDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Post note = (Post) container.getValue();

        ((TextView) itemView.findViewById(R.id.description)).setText(note.getNote().getDescription());

        ((TextView) itemView.findViewById(R.id.description)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

    }
}
