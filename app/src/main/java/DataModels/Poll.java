package DataModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lap on 6/12/16.
 */
public class Poll {

    String question;

    @SerializedName("result")
    List<PollVote> votes;

    @SerializedName("voted")
    Voted voted;

    public String getQuestion() {

        return question;

    }

    public void setQuestion(String question) {

        this.question = question;

    }

    public List<PollVote> getVotes() {

        return votes;

    }

    public void setVotes(List<PollVote> votes) {

        this.votes = votes;

    }

    public Voted getVoted() {

        return voted != null ? voted : new Voted();

    }

    public void setVoted(Voted voted) {

        this.voted = voted;

    }

    public static class Voted{

        private String id="";

        public String getId() {

            return id;

        }

        public Voted setId(String id) {

            this.id = id;

            return this;

        }
    }
}
