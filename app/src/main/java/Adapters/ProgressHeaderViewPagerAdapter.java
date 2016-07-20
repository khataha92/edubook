package Adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import DataModels.User;
import UserUtils.Application;
import UserUtils.UIUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

/**
 * Created by KhalidTaha on 7/20/16.
 */
public class ProgressHeaderViewPagerAdapter extends PagerAdapter {

    User user;

    public ProgressHeaderViewPagerAdapter(User user){

        this.user = user;

    }

    @Override
    public int getCount() {

        return 2;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = null;

        if(position == 0){

            view = LayoutInflater.from(Application.getContext()).inflate(R.layout.first_profile_page,container,false);

            ((TextView)view.findViewById(R.id.name)).setText(user.getName());

            ((TextView)view.findViewById(R.id.type)).setText(user.getType().getName());

            CircleImageView imageView = (CircleImageView)view.findViewById(R.id.profile_image);

            Picasso.with(Application.getContext()).load(user.getThumb()).error(UIUtil.getDefaultProfileImage()).into(imageView);

            container.addView(view);

        }
        else{

            view = LayoutInflater.from(Application.getContext()).inflate(R.layout.second_profile_page,container,false);

            String description = user.getName() + ": \n"+user.getType().getName();

            if(user.getRole() != null){

                description += " , "+ user.getRole();

            }

            ((TextView)view.findViewById(R.id.description)).setText(description);

            container.addView(view);

        }

        return view;

    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
