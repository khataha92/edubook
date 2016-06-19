package DataModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import interfaces.PostFactory;

/**
 * Created by lap on 6/12/16.
 */
public class StreamBookResponse {

    @SerializedName("data")
    List<Post> posts;

    @SerializedName("next_page_url")
    String nextPageURL;

    public String getNextPageNumber(){

        String number = nextPageURL.substring(nextPageURL.lastIndexOf("=")+1);

        return number;
    }

    public List<PostFactory> getPostFactory() {

        List<PostFactory> postFactories = new ArrayList<>();

        for(int i=0; i<posts.size() ; i++){

            postFactories.add(posts.get(i));
        }

        return postFactories;
    }
}
