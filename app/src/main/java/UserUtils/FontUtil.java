package UserUtils;

import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by mohammad on 4/19/15.
 * This is the util that is responsible fot fonts
 */
public class FontUtil {

    private static final String TAG = FontUtil.class.getSimpleName();

    /**
     * Get font from font type
     * @param fontsType font type
     * @param language The string language
     * @return Typeface
     */

    private static Typeface font ;
    public static Typeface getFont(final FontsType fontsType, String language) {

        if (language != null) {

            if (language.equalsIgnoreCase("ar")) {

                return getArabicFont(fontsType);

            } else {

                return getEnglishFont(fontsType);

            }
        }

        if (UserDefaultUtil.deviceLanguageIsArabic()) {

            return getArabicFont(fontsType);

        }

        return getEnglishFont(fontsType);

    }

    /**
     * Get font from name
     */
    public static Typeface getFont(final FontsType fontsType) {

        if(font == null){

            font = getArabicFont(fontsType);
        }

        return font;

        /*

        if (UserDefaultUtil.deviceLanguageIsArabic()) {

            return getArabicFont(fontsType);

        }

        return getEnglishFont(fontsType);

        */

    }

    /**
     * Get english font
     *
     * @param fontsType Font type
     * @return TypeFace
     */
    private static Typeface getEnglishFont(FontsType fontsType) {

        if (fontsType == FontsType.LIGHT) {

            return Typeface.createFromAsset(Application.getContext().getAssets(), "fonts/Roboto/Roboto-Light.ttf");

        }

        if (fontsType == FontsType.MEDIUM) {

            return Typeface.createFromAsset(Application.getContext().getAssets(), "fonts/Roboto/Roboto-Medium.ttf");

        }

        return Typeface.createFromAsset(Application.getContext().getAssets(), "fonts/Roboto/Roboto-Regular.ttf");

    }

    /**
     * Get arabic font
     *
     * @param fontsType Font type
     * @return TypeFace
     */
    private static Typeface getArabicFont(FontsType fontsType) {

        if (fontsType == FontsType.LIGHT) {

            return Typeface.createFromAsset(Application.getContext().getAssets(), "fonts/Frutiger/FrutigerLTArabic-45Light.ttf");

        }

        return Typeface.createFromAsset(Application.getContext().getAssets(), "fonts/Frutiger/FrutigerLTArabic-65Bold.ttf");

    }



    /**
     * Set Font for all elements in TextInputLayput View
     * @param mFont FontType to use
     * @param textInputLayout TextInputLayout object
     */
    public static void setTypefaceForTextInputLayout(FontsType mFont, TextInputLayout textInputLayout){

        if (textInputLayout == null || mFont == null) return;

        final TextInputLayout til = textInputLayout;

        til.getEditText().setTypeface(FontUtil.getFont(mFont));

        try {

            final Field cthf = til.getClass().getDeclaredField("mCollapsingTextHelper");

            cthf.setAccessible(true);

            final Object cth = cthf.get(til);

            final Field tpf = cth.getClass().getDeclaredField("mTextPaint");

            tpf.setAccessible(true);

            ((TextPaint) tpf.get(cth)).setTypeface(FontUtil.getFont(mFont));

        } catch (Exception ignored) {

            Log.e(TAG, "error",ignored);

        }
    }


    public static void setTypefaceForAllTextViews(ViewGroup rootView, Typeface typeface) {

        if (rootView == null) return;

        for (int i = rootView.getChildCount() - 1; i >= 0; i--) {

            final View child = rootView.getChildAt(i);

            if (child instanceof ViewGroup) {

                setTypefaceForAllTextViews((ViewGroup) child, typeface);

            } else if (child != null && child instanceof TextView) {

                ((TextView) child).setTypeface(typeface);
            }
        }

    }


    public static String arabicToDecimal(String number) {

        char[] chars = new char[number.length()];

        for(int i=0;i<number.length();i++) {

            char ch = number.charAt(i);

            if (ch >= 0x0660 && ch <= 0x0669)
            {
                ch -= 0x0660 - '0';
            }
            else if (ch >= 0x06f0 && ch <= 0x06F9)
            {

                ch -= 0x06f0 - '0';
            }

            chars[i] = ch;
        }

        return new String(chars);
    }


    public static void appendFontForLanguage(TextView textView, String str, FontsType fontType) {

        textView.setText(str);

        if (StringUtil.isStringArabic(str)) {

            textView.setTypeface(FontUtil.getFont(fontType, "ar"));

        } else {

            textView.setTypeface(FontUtil.getFont(fontType, "en"));

        }

    }

}
