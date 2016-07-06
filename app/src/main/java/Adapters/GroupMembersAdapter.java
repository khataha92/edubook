package Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import DataModels.Group;
import DataModels.User;
import Managers.FragmentManager;
import UserUtils.Application;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

/**
 * Created by mac on 7/5/16.
 */
public class GroupMembersAdapter extends BaseAdapter {

    List<User> groupMembers;

    Group group;

    public void setGroup(Group group) {

        this.group = group;

    }

    public GroupMembersAdapter(List<User> users){

        groupMembers = users;

    }

    @Override
    public int getCount() {
        return groupMembers.size();
    }

    @Override
    public Object getItem(int i) {

        return null;

    }

    @Override
    public long getItemId(int i) {

        return 0;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(Application.getContext()).inflate(R.layout.member_layout,viewGroup,false);

        ((TextView)view.findViewById(R.id.name)).setText(groupMembers.get(i).getName());

        ((TextView)view.findViewById(R.id.type)).setText(groupMembers.get(i).getType().getName());

        Drawable defDrawable = UIUtil.getDefaultProfileImage();

        CircleImageView imageView = (CircleImageView)view.findViewById(R.id.profile_image);

        Picasso.with(Application.getContext()).load(groupMembers.get(i).getThumb()).error(defDrawable).into(imageView);

        view.findViewById(R.id.member_settings).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showGroupMemberSettingsFragment(groupMembers.get(i),group,groupMembers);

            }
        });

        return view;

    }
}
