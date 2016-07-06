package ViewHolders.PostViewHolders;

import android.view.View;

import java.util.List;

import CustomComponent.PollLayout;
import DataModels.Poll;
import DataModels.PollVote;
import DataModels.Post;
import DataModels.PostDataContainer;
import Interfaces.AbstractCallback;
import ViewHolders.PostViewHolders.GenericPostViewHolder;
import edubook.edubook.R;


public class PollViewHolder extends GenericPostViewHolder {

    String optionId = "";

    PollLayout pollLayout;

    public PollViewHolder(View itemView, PostDataContainer container) {

        super(itemView,container);

        initLayout();
    }

    @Override
    public void initializeView(){

        super.initializeView();

    }

    public void initLayout(){

        final Post poll = (Post) container.getValue();

        pollLayout = (PollLayout) itemView.findViewById(R.id.poll_layout);

        pollLayout.setPost(poll);

        pollLayout.setQuestion(poll.getPoll().getQuestion());

        pollLayout.setSelectedOptionId(poll.getPoll().getVoted().getId());

        int total = computeTotalVotes(poll.getPoll().getVotes());

        pollLayout.setTotal(total);

        pollLayout.setVoteChangeListener(new AbstractCallback() {

            @Override
            public void onResult(boolean isSuccess, Object result) {

                if(isSuccess){

                    optionId = (String) result;

                    poll.getPoll().setVoted(new Poll.Voted().setId(optionId));

                    pollLayout.refreshVotes(optionId);

                }

            }
        });

        pollLayout.setVotes(poll.getPoll().getVotes());

    }

    public String getOptionId() {

        return optionId;

    }

    public PollLayout getPollLayout() {

        return pollLayout;

    }

    private int computeTotalVotes(List<PollVote> pollVoteList){

        int total = 0;

        for(int i=0 ; i <pollVoteList.size() ; i++){

            total += pollVoteList.get(i).getVotes();

        }

        return total;

    }
}
