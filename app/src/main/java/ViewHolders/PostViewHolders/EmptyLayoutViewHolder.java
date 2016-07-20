package ViewHolders.PostViewHolders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import DataModels.GenericViewHolderDataContainer;
import ViewHolders.GenericViewHolder;

/**
 * Created by lap on 6/12/16.
 */
public class EmptyLayoutViewHolder extends GenericViewHolder {

    public EmptyLayoutViewHolder(View itemView,GenericViewHolderDataContainer container) {

        super(itemView,container);
    }

    @Override
    public void initializeView() {

        super.initializeView();

        int height = (int) container.getValue();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height);

        itemView.setLayoutParams(params);

        itemView.requestLayout();

    }
}
