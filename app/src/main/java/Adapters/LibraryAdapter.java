package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DataModels.LibraryFile;
import Managers.SessionManager;
import UserUtils.Application;
import UserUtils.UIUtil;
import UserUtils.UserDefaultUtil;
import edubook.edubook.R;

/**
 * Created by mac on 6/24/16.
 */
public class LibraryAdapter extends BaseAdapter {

    List<LibraryFile> files = SessionManager.getInstance().getLibraryFiles();


    public LibraryAdapter(List<LibraryFile> files){

        if(files != null) {

            this.files = files;

        }

    }

    @Override
    public int getCount() {

        return files.size();

    }

    @Override
    public Object getItem(int i) {

        return null;

    }

    @Override
    public long getItemId(int i) {

        return 0;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(Application.getContext()).inflate(R.layout.file_layout,viewGroup,false);

        ImageView icon = (ImageView) view.findViewById(R.id.icon);

        icon.setImageResource(getIcon(files.get(i)));

        TextView name = (TextView)view.findViewById(R.id.name);

        name.setText(UserDefaultUtil.getStringWithMaxLength(files.get(i).getName(),25));

        ((TextView)view.findViewById(R.id.type)).setText(files.get(i).getType());

        return view;

    }

    private int getIcon(LibraryFile file){

        int resource = R.drawable.doc;

        if(file.getFileList() != null){

            resource = R.drawable.folder;
        }
        else{

            String extention = file.getName().substring(file.getName().lastIndexOf(".")+1);

            resource = UIUtil.getTypeResourceId(extention);
        }

        return resource;
    }
}
