package UserUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Enums.RecieverType;
import edubook.edubook.R;

/**
 * Created by lap on 6/9/16.
 */
public class Constants {

    public static String server = "http://46.101.98.34";

    public static String port = "8080";

    public static final String SENDER_ID = "952306397303";

    public static final int REQUEST_GALLERY = 21;

    public static final int REQUEST_CAMERA = 20;

    public static final int REQUEST_CROP = 22;

    private static RecieverType[] recieverTypesArray = new RecieverType[]
            {
                RecieverType.USERS,
                RecieverType.ONLY_ME,
                RecieverType.GROUPS,
                RecieverType.STUDENTS
            };

    private static List<RecieverType> recieverTypes = new ArrayList<>(Arrays.asList(recieverTypesArray));

    public static List<RecieverType> getRecieverTypes() {


        return recieverTypes;


    }
}
