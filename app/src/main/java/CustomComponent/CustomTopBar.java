package CustomComponent;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import UserUtils.ColorUtil;
import UserUtils.Application;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.StringUtil;
import UserUtils.SystemColors;
import UserUtils.UserDefaultUtil;
import edubook.edubook.R;

/**
 * Created by mohammad on 4/22/15.
 * This is the custom top bar
 */
public class CustomTopBar extends RelativeLayout {

    private View underLine;
    private LinearLayout rightContainer, leftContainer;
    private View rootView;

    public CustomTopBar(Context context) {
        super(context);

        init(context);

    }

    public CustomTopBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    public CustomTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {

        rootView =  LayoutInflater.from(context).inflate(R.layout.custom_view_top_bar, this);

        rightContainer = (LinearLayout) rootView.findViewById(R.id.right_container);

        leftContainer =  (LinearLayout) rootView.findViewById(R.id.left_container);

        underLine = rootView.findViewById(R.id.search_under_line);

    }

    /**
     * Set bar title and subtitle
     *
     * @param title    The title string
     * @param subTitle The subtitle to be set
     */
    public void setTitleAndSubTitle(final String title, final String subTitle) {

        rightContainer.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

        LayoutParams params = (LayoutParams) rightContainer.getLayoutParams();

        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        if (underLine != null) {

            underLine.setVisibility(GONE);
        }

        TextView headerTitle = (TextView) rootView.findViewById(R.id.header_title);

        headerTitle.setVisibility(View.VISIBLE);

        FontUtil.appendFontForLanguage(headerTitle, title, FontsType.REGULAR);

        headerTitle.setTextColor(Color.WHITE);

        headerTitle.setTextSize(UserDefaultUtil.deviceLanguageIsFrench()? 13 : 15);

        headerTitle.setGravity(Gravity.CENTER);

        headerTitle.setText(title);

        if (subTitle != null) {

            rootView.findViewById(R.id.header_sub_title).setVisibility(View.VISIBLE);

            TextView headerSubTitle = (TextView) rootView.findViewById(R.id.header_sub_title);

            if (StringUtil.isStringArabic(subTitle)) {

                headerSubTitle.setTypeface(FontUtil.getFont(FontsType.REGULAR, "ar"));

            } else {

                headerSubTitle.setTypeface(FontUtil.getFont(FontsType.REGULAR, "en"));

            }

            headerSubTitle.setTextColor(ColorUtil.getSystemColor(SystemColors.ACCENT_COLOR_BLUE));

            headerSubTitle.setTextSize(UserDefaultUtil.deviceLanguageIsFrench()? 10 : 12);

            headerSubTitle.setGravity(Gravity.CENTER);

            headerSubTitle.setText(subTitle);

        } else {

            rootView.findViewById(R.id.header_sub_title).setVisibility(View.GONE);

        }

        setTitleViewOnClickListener(null);


        // remove the header icon
        if(((LinearLayout) rootView.findViewById(R.id.header_layout)).getChildCount() > 1) {

            int indexOfImage = UserDefaultUtil.getUserLanguage().equalsIgnoreCase("ar")? 1: 0;

            ((LinearLayout) rootView.findViewById(R.id.header_layout)).removeViewAt(indexOfImage);

        }
    }

    /**
     * The call back for header view click
     *
     * @param listener On click listener
     */
    public void setTitleViewOnClickListener(final OnClickListener listener) {

        rootView.findViewById(R.id.header_layout).setOnClickListener(listener);

    }

    /**
     * Set right button in the top bar
     *
     * @param title           The button title
     * @param onClickListener The button click handle
     */
    public void setRightButton(final String title, final OnClickListener onClickListener) {

        if (rightContainer.getChildCount() > 0) {

            rightContainer.removeViewAt(0);

        }

        TextView textView = new TextView(getContext());

        textView.setTypeface(FontUtil.getFont(FontsType.REGULAR));

        textView.setTextColor(Color.WHITE);

        textView.setTextSize(UserDefaultUtil.deviceLanguageIsFrench()? 13 : 15);

        textView.setGravity(Gravity.CENTER);

        textView.setText(title);

        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        textView.setPadding(5, 5, 5, 5);

        textView.setOnClickListener(onClickListener);

        rightContainer.addView(textView);
    }

    /**
     * Set right button as image
     *
     * @param image           Drawable Button image
     * @param onClickListener Button click handle
     */
    private void setRightButton(final Drawable image, final OnClickListener onClickListener) {

        if (rightContainer.getChildCount() > 0) {

            rightContainer.removeViewAt(0);

        }

        ImageView imageView = new ImageView(getContext());

        imageView.setImageDrawable(image);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        imageView.setOnClickListener(onClickListener);

        rightContainer.addView(imageView);
    }

    /**
     * Set right button
     *
     * @param image           The image resource
     * @param onClickListener The button click handle
     */
    public void setRightButton(final int image, final OnClickListener onClickListener) {

        if (rightContainer.getChildCount() > 0) {

            rightContainer.removeViewAt(0);

        }

        ImageView imageView = new ImageView(getContext());

        imageView.setImageResource(image);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        imageView.setOnClickListener(onClickListener);

        rightContainer.addView(imageView);
    }

    /**
     * Set left button in the top bar
     *
     * @param title           The button title
     * @param onClickListener The button click handle
     */
    private void setLeftButton(final String title, final OnClickListener onClickListener) {

        LinearLayout leftContainer = (LinearLayout) rootView.findViewById(R.id.left_container);

        if (leftContainer.getChildCount() > 0) {

            leftContainer.removeViewAt(0);

        }

        TextView textView = new TextView(getContext());

        textView.setTypeface(FontUtil.getFont(FontsType.REGULAR));

        textView.setTextColor(Color.WHITE);

        textView.setTextSize(UserDefaultUtil.deviceLanguageIsFrench()? 13 : 15);

        textView.setGravity(Gravity.CENTER);

        textView.setText(title);

        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        textView.setPadding(5, 5, 5, 5);

        textView.setOnClickListener(onClickListener);

        leftContainer.addView(textView);
    }

    /**
     * Set left button as image
     *
     * @param image           Drawable Button image
     * @param onClickListener Button click handle
     */
    private void setLeftButton(final Drawable image, final OnClickListener onClickListener) {

        LinearLayout leftContainer = (LinearLayout) rootView.findViewById(R.id.left_container);

        if (leftContainer.getChildCount() > 0) {

            leftContainer.removeViewAt(0);

        }

        ImageView imageView = new ImageView(getContext());

        imageView.setImageDrawable(image);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        imageView.setOnClickListener(onClickListener);

        leftContainer.addView(imageView);
    }

    /**
     * Set left button
     *
     * @param image           The image resource
     * @param onClickListener The button click handle
     */
    private void setLeftButton(final int image, final OnClickListener onClickListener) {

        LinearLayout leftContainer = (LinearLayout) rootView.findViewById(R.id.left_container);

        if (leftContainer.getChildCount() > 0) {

            leftContainer.removeViewAt(0);

        }

        ImageView imageView = new ImageView(getContext());

        imageView.setImageResource(image);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        imageView.setOnClickListener(onClickListener);

        leftContainer.addView(imageView);
    }

    public void setLeftAndRightButton(String leftTitle, OnClickListener leftOnClickListener, String rightTitle, OnClickListener rightOnClickListener) {

        setLeftButton(leftTitle, leftOnClickListener);

        setRightButton(rightTitle, rightOnClickListener);
    }

    public void setLeftAndRightButton(String leftTitle, OnClickListener leftOnClickListener, int rightTitle, OnClickListener rightOnClickListener) {

        setLeftButton(leftTitle, leftOnClickListener);

        setRightButton(rightTitle, rightOnClickListener);
    }

    public void setLeftAndRightButton(int leftTitle, OnClickListener leftOnClickListener, int rightTitle, OnClickListener rightOnClickListener) {

        setLeftButton(leftTitle, leftOnClickListener);

        setRightButton(rightTitle, rightOnClickListener);
    }


    /**
     * Set a view on the right an a view on the left for the top bar
     * @param leftView View
     * @param rightView View
     */
    public void setLeftAndRightViews(View leftView, View rightView) {

        if (leftContainer.getChildCount() > 0) {

            leftContainer.removeViewAt(0);

        }

        leftContainer.addView(leftView);


        if (rightContainer.getChildCount() > 0) {

            rightContainer.removeViewAt(0);

        }

        rightContainer.addView(rightView);

    }

    /**
     * Set the right view for the top bar and make a dummy view have the same dimensions to be placed in the left side
     * @param rightView View to be placed on the right
     */
    public void setRightView(View rightView) {

        rightView.measure(
                MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED));

        View dummyView = new View(Application.getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(rightView.getMeasuredWidth(), rightView.getMeasuredHeight());

        dummyView.setLayoutParams(params);

        dummyView.setVisibility(INVISIBLE);

        setLeftAndRightViews(dummyView, rightView);

    }



    public void setTopBarColor(int color) {

        RelativeLayout topBar = (RelativeLayout) rootView.findViewById(R.id.topBar);

        topBar.setBackgroundColor(color);
    }

    /**
     * Add Icon to the header according to language
     * @param drawableId resource id of the drawable
     */
    public void setTopBarIcon(int drawableId){

        ImageView headerIcon =  new ImageView(getContext());

        headerIcon.setVisibility(View.VISIBLE);

        headerIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);

        headerIcon.setImageResource(drawableId);

        int indexOfImage = UserDefaultUtil.getUserLanguage().equalsIgnoreCase("ar")? 1: 0;

        ((LinearLayout) rootView.findViewById(R.id.header_layout)).addView(headerIcon, indexOfImage);


    }

}
