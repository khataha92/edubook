package ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import DataModels.GenericViewHolderDataContainer;
import UserUtils.Application;
import ViewHolders.GroupViewHolders.GroupHeaderViewHolder;
import ViewHolders.GroupViewHolders.GroupStickyViewHolder;
import ViewHolders.PostViewHolders.AssignmentViewHolder;
import ViewHolders.PostViewHolders.EmptyLayoutViewHolder;
import ViewHolders.PostViewHolders.EventViewHolder;
import ViewHolders.PostViewHolders.NoteViewHolder;
import ViewHolders.PostViewHolders.PollViewHolder;
import ViewHolders.ProgressViewHolders.ProgressHeaderViewHolder;
import ViewHolders.ProgressViewHolders.ProgressLineViewHolder;
import ViewHolders.ProgressViewHolders.ProgressStickyViewHolder;
import edubook.edubook.R;


public class GenericViewHolder extends RecyclerView.ViewHolder {

    public View itemView;

    public GenericViewHolderDataContainer container;

    public GenericViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView);

        this.itemView = itemView;

        this.container = container;

        initializeView();

    }

    public static GenericViewHolder getViewHolder(ViewGroup parent, GenericViewHolderDataContainer container){

        View view;
        
        LayoutInflater inflater=LayoutInflater.from(Application.getContext());

        switch (container.getType()){
            
            case NOTIFICATION:
                
                view = inflater.inflate(R.layout.notification,parent,false);

                return new NotificationViewHolder(view,container);

            case PROGRESS:

                view = inflater.inflate(R.layout.timeline_view,parent,false);

                return new ProgressLineViewHolder(view,container);

            case PROGRESS_HEADER:

                view = inflater.inflate(R.layout.progress_header,parent,false);

                return new ProgressHeaderViewHolder(view,container);

            case PROGRESS_STICKY:

                view = inflater.inflate(R.layout.progress_sticky,parent,false);

                return new ProgressStickyViewHolder(view,container);

            case COMMENT:

                view = inflater.inflate(R.layout.comment_layout,parent,false);

                return new CommentViewHolder(view,container);

            case GROUP_HEADER:

                view = inflater.inflate(R.layout.group_header,parent,false);

                return new GroupHeaderViewHolder(view,container);

            case GROUP_STICKY:

                view = inflater.inflate(R.layout.group_sticky_header,parent,false);

                return new GroupStickyViewHolder(view,container);

            case NOTE:

                view = inflater.inflate(R.layout.note_view,parent,false);

                return new NoteViewHolder(view,container);

            case ASSIGNMENT:

                view = inflater.inflate(R.layout.assignment,parent,false);

                return new AssignmentViewHolder(view,container);

            case EVENT:

                view = inflater.inflate(R.layout.event_view,parent,false);

                return new EventViewHolder(view,container);

            case POLL:

                view = inflater.inflate(R.layout.poll_view,parent,false);

                return new PollViewHolder(view,container);

            case NEW_NOTE:

                view = inflater.inflate(R.layout.new_post,parent,false);

                return new NewNoteViewHolder(view,container);

            default:

                view = inflater.inflate(R.layout.empty_layout,parent,false);

                return new EmptyLayoutViewHolder(view,container);

        }

    }

    public void initializeView(){

    }
}
