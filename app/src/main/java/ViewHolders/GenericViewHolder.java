package ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import DataModels.PostDataContainer;
import UserUtils.Application;
import ViewHolders.PostViewHolders.AssignmentViewHolder;
import ViewHolders.PostViewHolders.EmptyLayoutViewHolder;
import ViewHolders.PostViewHolders.EventViewHolder;
import ViewHolders.PostViewHolders.NoteViewHolder;
import ViewHolders.PostViewHolders.PollViewHolder;
import edubook.edubook.R;

/**
 * Created by lap on 6/12/16.
 */
public class GenericViewHolder extends RecyclerView.ViewHolder {

    public View itemView;

    public PostDataContainer container;

    public GenericViewHolder(View itemView,PostDataContainer container) {

        super(itemView);

        this.itemView = itemView;

        this.container = container;

        initializeView();

    }

    public static GenericViewHolder getViewHolder(ViewGroup parent, PostDataContainer container){

        View view;

        switch (container.getType()){

            case NOTE:

                view = LayoutInflater.from(Application.getContext()).inflate(R.layout.note_view,parent,false);

                return new NoteViewHolder(view,container);

            case ASSIGNMENT:

                view = LayoutInflater.from(Application.getContext()).inflate(R.layout.assignment,parent,false);

                return new AssignmentViewHolder(view,container);

            case EVENT:

                view = LayoutInflater.from(Application.getContext()).inflate(R.layout.event_view,parent,false);

                return new EventViewHolder(view,container);

            case POLL:

                view = LayoutInflater.from(Application.getContext()).inflate(R.layout.poll_view,parent,false);

                return new PollViewHolder(view,container);

            case NEW_NOTE:

                view = LayoutInflater.from(Application.getContext()).inflate(R.layout.new_post,parent,false);

                return new NewNoteViewHolder(view,container);

            default:

                view = LayoutInflater.from(Application.getContext()).inflate(R.layout.empty_layout,parent,false);

                return new EmptyLayoutViewHolder(view);

        }

    }

    public void initializeView(){

    }
}
