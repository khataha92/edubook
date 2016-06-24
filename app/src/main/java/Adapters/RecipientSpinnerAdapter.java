package Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DataModels.Recipient;
import Enums.RecieverType;
import UserUtils.Application;
import UserUtils.Constants;
import UserUtils.UserDefaultUtil;
import edubook.edubook.R;

/**
 * Created by mac on 6/21/16.
 */
public class RecipientSpinnerAdapter extends BaseAdapter {

    private List<RecieverType> itemList;

    public RecipientSpinnerAdapter(List<RecieverType> itemList) {

        this.itemList=itemList;
    }

    @Override
    public int getCount() {

        return itemList.size();

    }

    @Override
    public Object getItem(int i) {

        return null;

    }

    public RecieverType getSelectedType(int index){

        if(index >=0 && index < itemList.size()) {

            return itemList.get(index);

        }

        return null;

    }

    @Override
    public long getItemId(int i) {

        return 0;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(Application.getContext());

        View row = inflater.inflate(R.layout.recipient_layout, parent, false);

        TextView name = (TextView) row.findViewById(R.id.name);

        String label = UserDefaultUtil.getRecieverLabel(itemList.get(position));

        name.setText(label);

        ImageView icon = (ImageView) row.findViewById(R.id.icon);

        icon.setImageResource(UserDefaultUtil.getRecieverIcon(itemList.get(position)));

        return row;

    }

}

