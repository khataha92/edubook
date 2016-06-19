package ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import CustomComponent.ToggleLike;
import DataModels.Post;
import DataModels.PostDataContainer;
import UserUtils.Application;
import UserUtils.CallBackUtils;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

public class EventViewHolder extends GenericPostViewHolder {

    public EventViewHolder(View itemView, PostDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Post event = (Post) container.getValue();

        ((TextView) itemView.findViewById(R.id.title)).setText(event.getEvent().getTitle());

        ((TextView) itemView.findViewById(R.id.title)).setTypeface(FontUtil.getFont(FontsType.REGULAR));

        ((TextView) itemView.findViewById(R.id.description)).setText(event.getEvent().getDescription());

        ((TextView) itemView.findViewById(R.id.description)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        ((TextView) itemView.findViewById(R.id.from_date)).setText(event.getEvent().getStartDate());

        ((TextView) itemView.findViewById(R.id.from_date)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        ((TextView) itemView.findViewById(R.id.to_date)).setText(event.getEvent().getEndDate());

        ((TextView) itemView.findViewById(R.id.to_date)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

    }
}
