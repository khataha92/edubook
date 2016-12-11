package DataModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KhalidTaha on 10/19/16.
 */

public class YoutubeLinkDetails {

    @SerializedName("thumbnail_url")
    String thumbnailUrl;

    public String getThumbnailUrl() {

        return thumbnailUrl;

    }
}
