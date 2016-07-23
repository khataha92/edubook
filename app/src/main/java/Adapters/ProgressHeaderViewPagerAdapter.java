package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import DataModels.User;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;

/**
 * Created by KhalidTaha on 7/20/16.
 */
public class ProgressHeaderViewPagerAdapter extends PagerAdapter {

    User user;

    CircleImageView imageView;

    public ProgressHeaderViewPagerAdapter(User user){

        this.user = user;

        UserDefaultUtil.getListeners().clear();

        UserDefaultUtil.getListeners().put(user.getId(), new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                performCrop();

            }

        });

    }

    public CircleImageView getImageView() {

        return imageView;

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

            imageView = (CircleImageView)view.findViewById(R.id.profile_image);

            Picasso.with(Application.getContext()).load(user.getThumb()).error(UIUtil.getDefaultProfileImage()).into(imageView);

            imageView.setOnClickListener(UserDefaultUtil.getChangeImageListener());

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

    private void performCrop() {

        Context context = Application.getCurrentActivity();

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        String camera = context.getString(R.string.camera);

        String galary = context.getString(R.string.galary);

        String cancel = context.getString(R.string.cancel);

        dialog.setItems(new String[]{camera, galary, cancel}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                switch (which) {

                    case 0:

                        String imagePath = UserDefaultUtil.dispatchTakePictureIntent();

                        SessionManager.getInstance().setImagePath(imagePath);

                        break;

                    case 1:

                        UserDefaultUtil.startGalleryIntent();

                        break;
                }

            }

        });
        dialog.show();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
