package CustomComponent;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import edubook.edubook.R;

/**
 * Created by mac on 5/26/16.
 */
public class FlexibleTextView extends RelativeLayout {


    int textSize = 15;

    int maxLinesBeforeExpand = 1;

    int buttonTextSize = 13;

    TextView textView;

    int oldHeight = 0;

    Button readMore;

    int textColor;

    String text="";

    public int getMaxLinesBeforeExpand(){

        return maxLinesBeforeExpand;
    }

    int readMoreColor;
    OnClickListener readMoreOnClick = new OnClickListener() {
        @Override
        public void onClick(View view) {

            textView.setMaxLines(9999);

            oldHeight = textView.getMeasuredHeight();

            ((Button)view).setBackgroundResource(android.R.color.transparent);

            ((Button)view).setText(getContext().getString(R.string.SHOW_LESS));

            ((Button)view).setOnClickListener(readLessOnClick);

            textView.post(new Runnable() {
                @Override
                public void run() {

                    int newHeight = textView.getMeasuredHeight();

                    if(newHeight == oldHeight){

                        hideReadMoreBtn();
                    }
                }
            });

        }
    };

    OnClickListener readLessOnClick = new OnClickListener() {
        @Override
        public void onClick(View view) {

            textView.setMaxLines(maxLinesBeforeExpand);

            ((Button)view).setBackgroundResource(R.drawable.gradient);

            ((Button)view).setText(getContext().getString(R.string.SHOW_MORE));

            ((Button)view).setOnClickListener(readMoreOnClick);

        }
    };

    public void setButtonTextSize(int buttonTextSize) {
        this.buttonTextSize = buttonTextSize;
    }

    public void setMaxLinesBeforeExpand(int maxLinesBeforeExpand) {
        this.maxLinesBeforeExpand = maxLinesBeforeExpand;

        refreshLayout();
    }


    public void setReadMoreColor(int readMoreColor) {
        this.readMoreColor = readMoreColor;
    }

    public void setText(String text) {

        if(text == null) text = "";

        this.text = text.trim().replaceAll("\n","<br>").trim();

        String htmlText= Html.fromHtml(this.text).toString();

        this.text = htmlText.trim();

        textView.setText(htmlText);

        refreshLayout();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public FlexibleTextView(Context context) {
        super(context);

        initLayout();
    }

    public FlexibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initLayout();
    }

    public FlexibleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initLayout();
    }

    public void initLayout(){

        textColor = getContext().getResources().getColor(R.color.ACCENT_COLOR_DARK_NAVY);

        readMoreColor = getContext().getResources().getColor(R.color.ACCENT_COLOR_BLUE);

        textView = new TextView(getContext());

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(10,10,10,0);

        int bottomPadding = UIUtil.dpToPx(60);

        textView.setPadding(15,15,15,bottomPadding);

        textView.setText(text);

        textView.setLayoutParams(params);

        textView.setTypeface(FontUtil.getFont(FontsType.LIGHT));

        readMore = new Button(getContext());

        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);

        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        readMore.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);

        readMore.setText(getContext().getString(R.string.SHOW_MORE));

        readMore.setLayoutParams(params);

        readMore.setBackgroundResource(R.drawable.gradient);

        readMore.setTypeface(FontUtil.getFont(FontsType.LIGHT));

        addView(textView);

        addView(readMore);

        readMore.setOnClickListener(readMoreOnClick);

        refreshLayout();

    }

    public void refreshLayout(){

        int numberOfLines = UIUtil.getNumberOfLinesForTextView(text,textView);

        textView.setMaxLines(maxLinesBeforeExpand);

        readMore.setTextColor(readMoreColor);

        textView.setTextSize(textSize);

        textView.setTextColor(textColor);

        readMore.setTextSize(buttonTextSize);

        if(numberOfLines <= maxLinesBeforeExpand){

            hideReadMoreBtn();

        }
        else{

            showReadMoreBtn();

        }

    }


    public void showReadMoreBtn(){

        readMore.setVisibility(VISIBLE);

        int bottomPadding = UIUtil.dpToPx(60);

        textView.setPadding(15,15,15,bottomPadding);

        textView.requestLayout();

        textView.setMaxLines(maxLinesBeforeExpand);

    }

    public void hideReadMoreBtn(){

        readMore.setVisibility(GONE);

        int bottomPadding = UIUtil.dpToPx(15);

        textView.setPadding(15,15,15,bottomPadding);

        textView.requestLayout();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlexibleTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
