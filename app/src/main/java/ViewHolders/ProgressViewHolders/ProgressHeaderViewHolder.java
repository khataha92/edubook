package ViewHolders.ProgressViewHolders;

import android.support.v4.view.ViewPager;
import android.view.View;

import Adapters.ProgressHeaderViewPagerAdapter;
import DataModels.GenericViewHolderDataContainer;
import DataModels.User;
import Managers.FragmentManager;
import UserUtils.UserDefaultUtil;
import ViewHolders.GenericViewHolder;
import edubook.edubook.R;

/**
 * Created by mac on 7/11/16.
 */
public class ProgressHeaderViewHolder extends GenericViewHolder {

    ViewPager viewPager;

    public ProgressHeaderViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView, container);

    }

    public ViewPager getViewPager() {

        return viewPager;

    }

    @Override
    public void initializeView() {

        super.initializeView();

        itemView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.popCurrentVisibleFragment();

            }
        });

        viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);

        viewPager.setAdapter(new ProgressHeaderViewPagerAdapter((User)container.getValue()));

    }
}
