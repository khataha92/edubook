package CustomComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import DataModels.Post;
import Managers.SessionManager;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class ToggleLike extends LinearLayout{

    private boolean isLiked = true;

    private static final String TAG = ToggleLike.class.getSimpleName();

    ImageView icon;

    TextView text;

    Post post;

    public void setPost(Post post) {

        this.post = post;

    }

    public Post getPost() {

        return post;

    }

    public ToggleLike(Context context) {

        super(context);

        initializeView();
    }

    public ToggleLike(Context context, AttributeSet attrs) {

        super(context, attrs);

        initializeView();
    }

    public ToggleLike(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        initializeView();

    }

    private void initializeView(){

        setOrientation(HORIZONTAL);

        setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        icon = new ImageView(getContext());

        int width = UIUtil.dpToPx(20);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,width);

        if(SessionManager.getInstance().getUserLanguage().equalsIgnoreCase("en")) {

            params.setMargins(0, 0, UIUtil.dpToPx(5), 0);

        }

        else{

            params.setMargins(UIUtil.dpToPx(5), 0, 0, 0);

        }

        icon.setLayoutParams(params);

        text = new TextView(getContext());

        text.setTextSize(14);

        text.setTypeface(FontUtil.getFont(FontsType.LIGHT));

        toggleLike();

        addView(icon);

        addView(text);

    }

    public void like(){

        text.setText(getContext().getString(R.string.unlike));

        icon.setImageResource(R.drawable.like);

        isLiked = true;

        if(post != null){

            post.setLiked(1);

        }

    }

    public void unLike(){

        text.setText(getContext().getString(R.string.like));

        icon.setImageResource(R.drawable.like_grey);

        isLiked = false;

        if(post != null){

            post.setLiked(0);

        }
    }

    public void toggleLike(){

        if(isLiked){

            unLike();
        }
        else{

            like();

        }
    }

}
