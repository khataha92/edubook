package DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lap on 6/13/16.
 */
public class PollVote {

    String votes;

    String option;

    @SerializedName("option_id")
    String optionId;

    public int getVotes() {

        return Integer.valueOf(votes);

    }

    public void setVotes(String votes) {

        this.votes = votes;

    }

    public String getOption() {

        return option;

    }

    public void setOption(String option) {

        this.option = option;

    }

    public String getOptionId() {

        return optionId;

    }

    public void setOptionId(String optionId) {

        this.optionId = optionId;

    }
}
