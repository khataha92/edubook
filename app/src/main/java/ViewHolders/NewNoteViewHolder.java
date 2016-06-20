package ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import DataModels.PostDataContainer;
import Managers.FragmentManager;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;
import Managers.SessionManager;

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

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showNewNoteFragment();

            }
        });

    }
}
