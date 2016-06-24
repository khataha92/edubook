package CustomComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import edubook.edubook.R;

/**
 * Created by mac on 6/23/16.
 */
public class RecieverLayout extends RelativeLayout {

    public RecieverLayout(Context context) {

        super(context);

        initializeView();
    }

    public RecieverLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        initializeView();

    }

    public RecieverLayout(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        initializeView();

    }

    public void initializeView(){

        View view = LayoutInflater.from(getContext()).inflate(R.layout.reciever_layout,null,false);

        addView(view);
    }
}
