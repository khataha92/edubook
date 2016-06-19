package ViewHolders;

import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

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
public class NewNoteViewHolder extends GenericViewHolder {

    public NewNoteViewHolder(View itemView, PostDataContainer container) {

        super(itemView,container);
    }

    public void initializeView(){

        CircleImageView imageView = (CircleImageView)itemView.findViewById(R.id.profile_image);

        String imageUrl = SessionManager.getInstance().getCurrentUser().getThumb();

        Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);

        ((TextView) itemView.findViewById(R.id.note_content)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

    }
}
