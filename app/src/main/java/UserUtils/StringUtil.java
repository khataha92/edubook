package UserUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mohammad on 5/17/15.
 * This is the class that will contain strings func. utils
 */
public class StringUtil {

    /**
     * Check if the specified string is english
     *
     * @param input The string to test
     * @return If the string is english
     */
    public static boolean isStringEnglish(String input) {

        if (input == null) {

            return true;

        }

        final Pattern RTL_CHARACTERS =
                Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]");

        Matcher matcher = RTL_CHARACTERS.matcher(input);

        return !matcher.find();

    }

    /**
     * Check if the specified string is arabic
     *
     * @param input The string to test
     * @return If the string is arabic
     */
    public static boolean isStringArabic(String input) {

        if (input == null) {

            return true;

        }

        final Pattern RTL_CHARACTERS =
                Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]");

        Matcher matcher = RTL_CHARACTERS.matcher(input);

        return matcher.find();

    }

    public static String setImageSize(String imageUrl, String size) {

        return imageUrl.replace("[size]", size);

    }


    public static String commaSeparatedString(List<Object> list) {

        String str = "";

        if (list != null && list.size() > 0) {

            for (int i=0; i<list.size(); i++) {

                str += list.get(i).toString();

                if (i<list.size()-1) {

                    str += ",";

                }

            }

        }

        return str;

    }

}
