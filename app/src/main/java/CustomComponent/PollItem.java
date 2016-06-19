package CustomComponent;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class PollItem extends LinearLayout {

    ImageView radioImage;

    boolean isSelected = false;

    String optionId;

    public void setOptionId(String optionId) {

        this.optionId = optionId;

    }

    public String getOptionId() {

        return optionId;

    }

    ProgressBar progressBar;

    String answer = "answer number 1";

    TextView answerTextView;

    @Override
    public void setSelected(boolean selected) {

        isSelected = selected;

        radioImage.setImageResource(isSelected?R.drawable.selected_state:R.drawable.unselected_state);

    }

    public void setAnswer(String answer) {

        this.answer = answer;

        answerTextView.setText(answer);

        answerTextView.setTypeface(FontUtil.getFont(FontsType.LIGHT));
    }

    public void setProgress(int progress){

        progressBar.setProgress(progress);

    }

    @Override
    public boolean isSelected() {

        return isSelected;

    }

    public PollItem(Context context) {

        super(context);

        initializeItem();

    }

    public PollItem(Context context, AttributeSet attrs) {

        super(context, attrs);

        initializeItem();

    }

    public PollItem(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        initializeItem();

    }

    private void initializeItem(){

        setOrientation(HORIZONTAL);

        int dp = UIUtil.dpToPx(20);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0,dp,0,0);

        radioImage = new ImageView(getContext());

        LinearLayout.LayoutParams params = new LayoutParams(dp,dp);

        params.setMargins(0,dp/2,0,0);

        setLayoutParams(layoutParams);

        radioImage.setImageResource(R.drawable.unselected_state);

        radioImage.setLayoutParams(params);

        setGravity(Gravity.TOP);

        addView(radioImage);

        LinearLayout answerLayout = new LinearLayout(getContext());

        answerLayout.setOrientation(VERTICAL);

        LinearLayout.LayoutParams answersParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        answersParams.setMargins(dp,0,0,0);

        answerLayout.setLayoutParams(answersParams);

        answerTextView = new TextView(getContext());

        LinearLayout.LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        answerTextView.setLayoutParams(textParams);

        answerTextView.setTextColor(Color.BLACK);

        answerTextView.setText(answer);

        answerTextView.setTextSize(19);

        answerTextView.setTypeface(FontUtil.getFont(FontsType.LIGHT));

        answerLayout.addView(answerTextView);

        progressBar = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleHorizontal);

        LinearLayout.LayoutParams progressParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp/2);

        progressBar.setProgress(70);

        Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progressbar);

        progressBar.setProgressDrawable(progressDrawable);

        progressBar.setLayoutParams(progressParams);

        answerLayout.addView(progressBar);

        addView(answerLayout);
    }
}
