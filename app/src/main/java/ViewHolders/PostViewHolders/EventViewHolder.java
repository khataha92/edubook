package ViewHolders.PostViewHolders;

import android.view.View;
import android.widget.TextView;

import CustomComponent.FlexibleTextView;
import DataModels.Post;
import DataModels.GenericViewHolderDataContainer;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import edubook.edubook.R;

public class EventViewHolder extends GenericPostViewHolder {

    public EventViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Post event = (Post) container.getValue();

        ((TextView) itemView.findViewById(R.id.title)).setText(event.getEvent().getTitle());

        ((TextView) itemView.findViewById(R.id.title)).setTypeface(FontUtil.getFont(FontsType.REGULAR));

        ((FlexibleTextView) itemView.findViewById(R.id.description)).setText(event.getEvent().getDescription());

        ((TextView) itemView.findViewById(R.id.from_date)).setText(event.getEvent().getStartDate());

        ((TextView) itemView.findViewById(R.id.from_date)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        ((TextView) itemView.findViewById(R.id.to_date)).setText(event.getEvent().getEndDate());

        ((TextView) itemView.findViewById(R.id.to_date)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

    }
}
