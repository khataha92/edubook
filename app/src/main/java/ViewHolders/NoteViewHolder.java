package ViewHolders;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Adapters.PostListAdapter;
import CustomComponent.ToggleLike;
import DataModels.Post;
import DataModels.PostDataContainer;
import UserUtils.Application;
import UserUtils.CallBackUtils;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;
import managers.SessionManager;

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
