package ViewHolders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import DataModels.PostDataContainer;
import Managers.FragmentManager;
import UserUtils.Application;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;
import Managers.SessionManager;

/**
 * Created by lap on 6/12/16.
 */
public class NewNoteViewHolder extends GenericViewHolder {

    private View.OnClickListener newNoteListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            FragmentManager.showNewNoteFragment();

        }
    };

    public NewNoteViewHolder(View itemView, PostDataContainer container) {

        super(itemView,container);
    }

    public void initializeView(){

        CircleImageView imageView = (CircleImageView)itemView.findViewById(R.id.profile_image);

        String imageUrl = SessionManager.getInstance().getCurrentUser().getThumb();

        UIUtil.loadImageFromUrl(imageView,imageUrl);

        ((TextView) itemView.findViewById(R.id.note_content)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showNewNoteFragment();

            }
        });

        itemView.findViewById(R.id.note_content).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showNewNoteFragment();

            }
        });

    }
}
