package ViewHolders.PostViewHolders;

import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import CustomComponent.FlexibleTextView;
import DataModels.Post;
import DataModels.GenericViewHolderDataContainer;
import DataModels.YoutubeLinkDetails;
import Enums.ResponseCode;
import Interfaces.OnWebserviceFinishListener;
import UserUtils.Application;
import UserUtils.WebService;
import UserUtils.WebserviceRequestUtil;
import edubook.edubook.R;

import static UserUtils.Application.getContext;

/**
 * Created by lap on 6/12/16.
 */
public class NoteViewHolder extends GenericPostViewHolder {

    static List<String> youtubeLinks = new ArrayList<>();

    static {

        youtubeLinks.add("http://youtube.com");

        youtubeLinks.add("http://youtu.be");

        youtubeLinks.add("https://youtube.com");

        youtubeLinks.add("https://youtu.be");

        youtubeLinks.add("http://www.youtube.com");

        youtubeLinks.add("https://www.youtube.com");

        youtubeLinks.add("http://www.youtu.be");

        youtubeLinks.add("https://www.youtu.be");

    }

    public NoteViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView,container);

    }

    @Override
    public void initializeView(){

        super.initializeView();

        final Post note = (Post) container.getValue();

        FlexibleTextView description = (FlexibleTextView) itemView.findViewById(R.id.description);

        String strDescription = note.getNote().getDescription();

        description.setText(strDescription);

        boolean isYoutube = false;

        String youtubeLink = "";

        for(int i = 0 ; i < youtubeLinks.size() ; i ++){

            if(strDescription.contains(youtubeLinks.get(i))){

                isYoutube = true;

                int youtubeStartIndex = strDescription.indexOf(youtubeLinks.get(i));

                youtubeLink = strDescription.substring(youtubeStartIndex);

                StringTokenizer stringTokenizer = new StringTokenizer(youtubeLink," ");

                youtubeLink = stringTokenizer.nextToken();

                break;

            }

        }

        if(isYoutube){

            final String finalYoutubeLink = youtubeLink;
            WebserviceRequestUtil.getYoutubeLinkDetails(youtubeLink, new OnWebserviceFinishListener() {

                @Override
                public void onFinish(WebService webService) {

                    if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                        ImageView previewImage = (ImageView) itemView.findViewById(R.id.image_preview);

                        YoutubeLinkDetails youtubeLinkDetails = new Gson().fromJson(webService.getStrResponse().toString(),YoutubeLinkDetails.class);

                        String thumb = youtubeLinkDetails.getThumbnailUrl();

                        Picasso.with(getContext())
                                .load(thumb)
                                .into(previewImage, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                        itemView.findViewById(R.id.play_video).setVisibility(View.VISIBLE);

                                        View.OnClickListener listener = new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                Application.getCurrentActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(finalYoutubeLink)));

                                            }
                                        };

                                        itemView.findViewById(R.id.image_preview).setOnClickListener(listener);

                                        itemView.findViewById(R.id.play_video).setOnClickListener(listener);
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });

                    }

                }
            });
        }

    }
}
