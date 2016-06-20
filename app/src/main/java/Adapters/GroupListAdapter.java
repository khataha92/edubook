package Adapters;

import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import DataModels.Group;
import UserUtils.Application;
import ViewHolders.GroupItemViewHolder;
import edubook.edubook.R;


public class GroupListAdapter extends RecyclerView.Adapter<GroupItemViewHolder> {

    List<Group> groups;

    public GroupListAdapter(List<Group> groupList){

        groups = groupList;

    }

    @Override
    public GroupItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(Application.getContext()).inflate(R.layout.group_item,parent,false);

        if(viewType < groups.size()){

            return new GroupItemViewHolder(view,groups.get(viewType));

        }

        return new GroupItemViewHolder(view,null);

    }

    @Override
    public void onBindViewHolder(GroupItemViewHolder holder, int position) {



    }

    @Override
    public int getItemViewType(int position) {

        return position;

    }

    @Override
    public int getItemCount() {

        return groups.size() + 1;

    }
}
