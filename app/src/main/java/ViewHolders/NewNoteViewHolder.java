package ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import DataModels.AddNewPostDataModel;
import DataModels.Post;
import DataModels.GenericViewHolderDataContainer;
import Enums.ResponseCode;
import Interfaces.OnWebserviceFinishListener;
import Interfaces.PostFactory;
import Managers.FragmentManager;
import UserUtils.FontUtil;
import UserUtils.FontsType;
import UserUtils.UIUtil;
import UserUtils.WebService;
import de.hdodenhof.circleimageview.CircleImageView;
import edubook.edubook.R;
import Managers.SessionManager;

/**
 * Created by lap on 6/12/16.
 */
public class NewNoteViewHolder extends GenericViewHolder {

    RecyclerView recyclerView;

    List<PostFactory> postList;

    public void setPostList(List<PostFactory> postList) {

        this.postList = postList;

    }

    public void setRecyclerView(RecyclerView recyclerView) {

        this.recyclerView = recyclerView;

    }

    private OnWebserviceFinishListener listener = new OnWebserviceFinishListener() {

        @Override
        public void onFinish(WebService webService) {

            UIUtil.hideSweetLoadingView();

            if(webService.getResponseCode() == ResponseCode.SUCCESS.getCode()){

                Post note = new Gson().fromJson(webService.getStrResponse().toString(),Post.class);

                note.setPostList(postList);

                note.setRecyclerView(recyclerView);

                postList.add(0,note);

                note.getRecyclerView().setAdapter(note.getRecyclerView().getAdapter());

                Managers.FragmentManager.popCurrentVisibleFragment();

            }
            else{

                UIUtil.showErrorDialog();

            }

        }
    };

    public NewNoteViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView,container);
    }

    public void initializeView(){

        AddNewPostDataModel dataModel = (AddNewPostDataModel) container.getValue();

        setRecyclerView(dataModel.getRecyclerView());

        setPostList(dataModel.getPostList());

        CircleImageView imageView = (CircleImageView)itemView.findViewById(R.id.profile_image);

        String imageUrl = SessionManager.getInstance().getCurrentUser().getThumb();

        UIUtil.loadImageFromUrl(imageView,imageUrl);

        ((TextView) itemView.findViewById(R.id.note_content)).setTypeface(FontUtil.getFont(FontsType.LIGHT));

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showNewNoteFragment(listener);

            }
        });

        itemView.findViewById(R.id.note_content).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager.showNewNoteFragment(listener);

            }
        });

    }
}
