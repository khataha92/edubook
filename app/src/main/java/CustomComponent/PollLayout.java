package CustomComponent;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DataModels.PollVote;
import DataModels.Post;
import UserUtils.CallBackUtils;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import edubook.edubook.R;
import Interfaces.AbstractCallback;

public class PollLayout extends LinearLayout{

    String question;

    List<PollItem> pollItems = new ArrayList<>();

    Post post;

    AbstractCallback voteChangeListener;

    public void setVoteChangeListener(AbstractCallback voteChangeListener) {

        this.voteChangeListener = voteChangeListener;

    }

    public void setPost(Post post) {

        this.post = post;

    }

    public Post getPost() {

        return post;

    }

    List<PollVote> votes = new ArrayList<>();

    String selectedOptionId="";

    LinearLayout answersLayout;

    TextView totalTextView;

    int total = 0;

    PollItem selectedPollItem = null;

    TextView tvQuestion;

    public void setTotal(int total) {

        this.total = total;

        setTotalTextView();

    }

    public int getTotal() {

        return total;

    }

    public void setSelectedOptionId(String selectedOptionId) {

        this.selectedOptionId = selectedOptionId;

    }

    public String getSelectedOptionId() {

        return selectedOptionId;

    }

    public void setVotes(List<PollVote> votes) {

        this.votes = votes;

        addAnswers();

    }

    public void setQuestion(String question) {

        this.question = question;

        tvQuestion.setText(question);

    }

    public PollLayout(Context context) {

        super(context);

        initializeLayout();

    }

    public PollLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        initializeLayout();
    }

    public PollLayout(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        initializeLayout();
    }

    private void addAnswers(){

        for(int i=0; i < votes.size() ; i++){

            final PollItem pollItem = new PollItem(getContext());

            if(votes.get(i).getOptionId().equalsIgnoreCase(selectedOptionId)){

                pollItem.setSelected(true);

                selectedPollItem = pollItem;

            }

            pollItem.setAnswer(votes.get(i).getOption());

            pollItem.setOptionId(votes.get(i).getOptionId());

            pollItems.add(pollItem);

            answersLayout.addView(pollItem);

        }

        processPollProgress();
    }

    private void processPollProgress(){

        for(int i = 0; i < pollItems.size(); i++){

            final PollItem pollItem = pollItems.get(i);

            int progress = (int)(((float)votes.get(i).getVotes()/(float)total)*100);

            pollItem.setProgress(progress);

            final String voteId = votes.get(i).getOptionId();

            pollItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(selectedPollItem != null) {

                        selectedPollItem.setSelected(false);

                    }

                    CallBackUtils.vote(PollLayout.this, String.valueOf(post.getId()), voteId, new AbstractCallback() {

                        @Override
                        public void onResult(boolean isSuccess, Object result) {

                            if(isSuccess){

                                if(voteChangeListener != null){

                                    voteChangeListener.onResult(true,pollItem.getOptionId());

                                }

                            }

                            else{

                                UIUtil.showErrorDialog();

                            }

                        }
                    });

                }
            });

        }
    }

    public void refreshVotes(String voteId){

        PollItem pollItem = getPollById(voteId);

        revotePoll(pollItem);

    }

    public PollItem getPollById(String id){

        PollItem pollItem = null;

        for(int i = 0 ; i < pollItems.size() ; i++){

            if(pollItems.get(i).getOptionId().equalsIgnoreCase(id)){

                pollItem = pollItems.get(i);

            }

        }
        return pollItem;

    }



    public void revotePoll(PollItem pollItem){

        setClickable(true);

        if(selectedPollItem == null){

            total += 1;

        }
        else{

            selectedPollItem.setSelected(false);

        }

        if(pollItem != null) {

            pollItem.setSelected(true);

            selectedPollItem = pollItem;

            for(int i=0;i<votes.size();i++){

                if(votes.get(i).getOptionId().equalsIgnoreCase(selectedOptionId)){

                    int vote = votes.get(i).getVotes()-1;

                    votes.get(i).setVotes(String.valueOf(vote));

                }
                if(votes.get(i).getOptionId().equalsIgnoreCase(String.valueOf(selectedPollItem.getOptionId()))){

                    int vote = votes.get(i).getVotes()+1;

                    votes.get(i).setVotes(String.valueOf(vote));

                }
            }

            selectedOptionId = String.valueOf(selectedPollItem.getOptionId());

            processPollProgress();

        }
    }

    private void initializeLayout(){

        setOrientation(VERTICAL);

        tvQuestion = new TextView(getContext());

        tvQuestion.setTextSize(19);

        LinearLayout.LayoutParams questionParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        questionParams.setMargins(15,15,15,15);

        tvQuestion.setTypeface(FontUtil.getFont(FontsType.REGULAR));

        tvQuestion.setTextColor(Color.BLACK);

        tvQuestion.setText("some test question ?");

        addView(tvQuestion);

        answersLayout = new LinearLayout(getContext());

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0,20,20,20);

        answersLayout.setLayoutParams(params);

        answersLayout.setOrientation(VERTICAL);

        addAnswers();

        addView(answersLayout);

        totalTextView = new TextView(getContext());

        totalTextView.setTypeface(FontUtil.getFont(FontsType.REGULAR));

        setTotalTextView();

        addView(totalTextView);
    }

    private void setTotalTextView(){

        String totalVotes = getContext().getString(R.string.total_votes) + " " + total;

        totalTextView.setText(totalVotes);

    }
}
