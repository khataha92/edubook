package ViewHolders;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import CustomComponent.ToggleLike;
import DataModels.GenericViewHolderDataContainer;
import DataModels.NotificationModel;
import DataModels.Post;
import Interfaces.AbstractCallback;
import Interfaces.PostFactory;
import Managers.FragmentManager;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.CallBackUtils;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import ViewHolders.PostViewHolders.PollViewHolder;
import activities.Home;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

public class NotificationViewHolder extends GenericViewHolder {

    public NotificationViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView, container);

    }

    @Override
    public void initializeView() {

        final NotificationModel model = (NotificationModel) container.getValue();

        TextView text = (TextView) itemView.findViewById(R.id.name);

        TextView age = (TextView) itemView.findViewById(R.id.age);

        CircleImageView imageView = (CircleImageView) itemView.findViewById(R.id.profile_image);

        text.setText(model.getNotificationText());

        Picasso.with(Application.getContext()).load(model.getCreator().getThumb()).error(UIUtil.getDefaultProfileImage()).into(imageView);

        age.setText(model.getAge());

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Application.getCurrentActivity(), Home.class);

                intent.putExtra("postId", ""+model.getItemId());

                Application.getCurrentActivity().startActivity(intent);

            }
        });

    }

}
