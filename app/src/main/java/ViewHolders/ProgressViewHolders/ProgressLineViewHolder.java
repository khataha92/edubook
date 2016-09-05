package ViewHolders.ProgressViewHolders;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import DataModels.GenericViewHolderDataContainer;
import DataModels.Progress;
import Enums.ProgressType;
import UserUtils.Application;
import ViewHolders.GenericViewHolder;
import edubook.edubook.R;

/**
 * Created by KhalidTaha on 7/20/16.
 */
public class ProgressLineViewHolder extends GenericViewHolder {

    public ProgressLineViewHolder(View itemView, GenericViewHolderDataContainer container) {

        super(itemView, container);

    }

    @Override
    public void initializeView() {

        super.initializeView();

        Progress progress = (Progress) container.getValue();

        ((TextView)itemView.findViewById(R.id.type)).setText(progress.getTitle());

        ((TextView)itemView.findViewById(R.id.description)).setText(progress.getDetails());

        String date = progress.getDate();

        String strMonth = date.substring(5,7);

        String strYear = date.substring(0,4);

        int year = Integer.valueOf(strYear);

        String strDay = date.substring(date.lastIndexOf("-")+1,date.lastIndexOf("-")+3);

        int day = Integer.valueOf(strDay);

        int month = Integer.valueOf(strMonth);

        Locale locale = Application.getContext().getResources().getConfiguration().locale;

        Calendar c = Calendar.getInstance();

        c.set(Calendar.MONTH,month-1);

        c.set(Calendar.YEAR,year);

        c.set(Calendar.DAY_OF_MONTH,day);

        ((TextView)itemView.findViewById(R.id.date)).setText(String.format(locale,"%d %tB",day,c));

        ImageView icon = (ImageView) itemView.findViewById(R.id.icon);

        icon.setImageResource(getIcon(progress.getType()));

    }

    private int getIcon(ProgressType type){

        int icon;

        switch (type){

            case ASSIGNMENT:

                icon = R.drawable.assignment;

                break;

            case LATE:

                icon = R.drawable.late;

                break;

            case EXAM:

                icon = R.drawable.exam;

                break;

            case ABSENCE:

                icon = R.drawable.absence;

                break;

            case EXCUSED:

                icon = R.drawable.excused;

                break;

            default:

                icon = R.drawable.assignment;

                break;

        }

        return icon;
    }
}