package DataModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ProgressDataModel implements Serializable{

    @SerializedName("current_page")
    int currentPage;

    @SerializedName("next_page_url")
    String nextPageUrl;

    @SerializedName("data")
    List<Progress> progress;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<Progress> getProgress() {
        return progress;
    }

    public void setProgress(List<Progress> progress) {
        this.progress = progress;
    }
}
