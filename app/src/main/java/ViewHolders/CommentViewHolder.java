package ViewHolders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import DataModels.Comment;
import DataModels.PostDataContainer;
import UserUtils.Application;
import UserUtils.UIUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

public class CommentViewHolder extends GenericViewHolder {

    public CommentViewHolder(View itemView, PostDataContainer container) {

        super(itemView, container);

    }

    @Override
    public void initializeView() {

        super.initializeView();

        Comment cmnt = (Comment) container.getValue();

        CircleImageView imageView = (CircleImageView) itemView.findViewById(R.id.profile_image);

        TextView name = (TextView) itemView.findViewById(R.id.name);

        TextView age = (TextView) itemView.findViewById(R.id.age);

        TextView comment = (TextView) itemView.findViewById(R.id.comment);

        Drawable defaultDrawable = UIUtil.getDefaultProfileImage();

        Picasso.with(Application.getContext()).load(cmnt.getCreator().getThumb()).error(defaultDrawable).into(imageView);

        name.setText(cmnt.getCreator().getName());

        age.setText(cmnt.getAge());

        comment.setText(cmnt.getComment());

    }
}
