package ViewHolders.PostViewHolders;

import android.view.View;

import CustomComponent.FlexibleTextView;
import DataModels.Post;
import DataModels.GenericViewHolderDataContainer;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class NoteViewHolder extends GenericPostViewHolder {

    public NoteViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView,container);

    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Post note = (Post) container.getValue();

        FlexibleTextView description = (FlexibleTextView) itemView.findViewById(R.id.description);

        description.setText(note.getNote().getDescription());

    }
}
