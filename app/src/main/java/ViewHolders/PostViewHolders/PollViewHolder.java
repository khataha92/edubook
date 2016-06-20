package ViewHolders.PostViewHolders;

import android.view.View;

import java.util.List;

import CustomComponent.PollLayout;
import DataModels.PollVote;
import DataModels.Post;
import DataModels.PostDataContainer;
import ViewHolders.PostViewHolders.GenericPostViewHolder;
import edubook.edubook.R;


public class PollViewHolder extends GenericPostViewHolder {

    public PollViewHolder(View itemView, PostDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Post poll = (Post) container.getValue();

        PollLayout pollLayout = (PollLayout) itemView.findViewById(R.id.poll_layout);

        pollLayout.setPost(poll);

        pollLayout.setQuestion(poll.getPoll().getQuestion());

        pollLayout.setSelectedOptionId(poll.getPoll().getVoted().getId());

        int total = computeTotalVotes(poll.getPoll().getVotes());

        pollLayout.setTotal(total);

        pollLayout.setVotes(poll.getPoll().getVotes());

    }

    private int computeTotalVotes(List<PollVote> pollVoteList){

        int total = 0;

        for(int i=0 ; i <pollVoteList.size() ; i++){

            total += pollVoteList.get(i).getVotes();

        }

        return total;

    }
}
