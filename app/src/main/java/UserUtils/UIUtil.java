package UserUtils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edubook.edubook.R;
import Fragments.BaseFragment;
import Interfaces.AbstractCallback;


public class UIUtil {

    private static Dialog loadingDialog = null;

    private static boolean isInternetDialogShowing = false;

    private static ImageView reloadImage = null;

    private static Dialog noInternetConnectionDialog = null;

    private static final String TAG = UIUtil.class.getSimpleName();

    private static boolean isSoftKeyboardOpened = false;

    private static SweetAlertDialog pDialog;

    private static Dialog menuDlg;

    public static int dpToPx(int dp) {

        return Math.round(dp * getPixelScaleFactor());

    }

    public static void hideInternetConnectionErrorDialog(){

        if(noInternetConnectionDialog == null || !noInternetConnectionDialog.isShowing()){

            return;

        }

        isInternetDialogShowing = false;

        noInternetConnectionDialog.dismiss();

    }

    public static void showInternetConnectionErrorDialog(final Activity activity, final AbstractCallback callback){

        if(isInternetDialogShowing) {

            return;

        }

        noInternetConnectionDialog = new Dialog(activity, android.R.style.Theme_Light_NoTitleBar);

        noInternetConnectionDialog.setCancelable(false);

        noInternetConnectionDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode ==KeyEvent.KEYCODE_BACK){

                    noInternetConnectionDialog.dismiss();

                    Application.getCurrentActivity().finish();

                }

                return false;
            }
        });

        View internetConnection = activity.getLayoutInflater().inflate(R.layout.internet_connection, null);

        reloadImage = (ImageView)internetConnection.findViewById(R.id.reload);

        reloadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                reloadImage.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.rotate_indefinitely) );

                callback.onResult(true,null);

            }
        });

        noInternetConnectionDialog.setContentView(internetConnection);

        isInternetDialogShowing = true;

        noInternetConnectionDialog.show();

    }

    private static float getPixelScaleFactor() {

        DisplayMetrics displayMetrics = Application.getContext().getResources().getDisplayMetrics();

        return (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);

    }

    public static void showErrorDialog(String ...errors){

        pDialog = new SweetAlertDialog(Application.getContext(), SweetAlertDialog.ERROR_TYPE);

        String errorMessage = Application.getContext().getString(R.string.SOMETHING_WENT_WRONG);

        if(errors.length>0){

            errorMessage = errors[0];
        }

        pDialog.setTitleText(errorMessage);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));

        pDialog.setCancelable(false);

        pDialog.show();

    }

    public static void hideSweetLoadingView(){

        if(pDialog != null && pDialog.isShowing()){

            pDialog.dismiss();

        }

    }

    public static void showSweetLoadingView(){

        pDialog = new SweetAlertDialog(Application.getContext(),SweetAlertDialog.PROGRESS_TYPE);

        pDialog.setTitleText(Application.getContext().getString(R.string.loading));

        pDialog.setCancelable(false);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));

        pDialog.show();

    }


    public static void dismissSweetDialog(){

        if(menuDlg != null && menuDlg.isShowing()){

            menuDlg.dismiss();

        }

    }

    public static void showDeleteDialog(View.OnClickListener cancelListener, View.OnClickListener deleteListener, final DialogInterface.OnDismissListener listener){

        menuDlg = new Dialog(Application.getCurrentActivity(), android.R.style.Theme_NoTitleBar);

        menuDlg.getWindow().setWindowAnimations(R.style.DialogNoAnimation);

        menuDlg.setOnDismissListener(listener);

        menuDlg.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {

                listener.onDismiss(dialog);

            }
        });

        menuDlg.setContentView(R.layout.post_menu);

        menuDlg.findViewById(R.id.button_cancel).setOnClickListener(cancelListener);

        menuDlg.findViewById(R.id.delete).setOnClickListener(deleteListener);

        menuDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99ffffff")));

        menuDlg.show();

    }

    public static View.OnClickListener getOnClickListener(View v) {

        try {

            Field listenerInfoField = Class.forName("android.view.View").getDeclaredField("mListenerInfo");

            if (listenerInfoField != null) {

                listenerInfoField.setAccessible(true);

                Object myLiObject = listenerInfoField.get(v);

                Field listenerField = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener");

                if (listenerField != null && myLiObject != null) {

                    return (View.OnClickListener) listenerField.get(myLiObject);

                }

            }

        } catch (NoSuchFieldException e) {

            Log.e(TAG, "error", e);

        } catch (ClassNotFoundException e) {

            Log.e(TAG, "error", e);

        } catch (IllegalAccessException e) {
            Log.e(TAG, "error", e);
        }

        return null;

    }

    /**
     * Hide keyboard
     */
    public static void hideSoftKeyboard() {

        if (Application.getCurrentActivity() == null || Application.getContext() == null) {

            return;

        }

        // Hide soft keyboard if it is visible
        InputMethodManager inputManager = (InputMethodManager) Application.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        View v = Application.getCurrentActivity().getCurrentFocus();

        if (v != null) {

            Application.getCurrentActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }

    }

    /**
     * Show keyboard
     */
    public static void showSoftKeyboard(final EditText yourEditText) {

        InputMethodManager imm = (InputMethodManager) Application.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * If soft keyboard is shown
     * @return boolean
     */
    public static boolean isSoftKeyboardShown() {

        return isSoftKeyboardOpened;

    }


    /**
     * Set soft keyboard is shown
     * @return boolean
     */
    public static void setSoftKeyboardShown(boolean shown) {

        isSoftKeyboardOpened = shown;

    }


    public static int getTopbarHeight() {

        TypedValue tv = new TypedValue();

        Application.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);

        return  Application.getContext().getResources().getDimensionPixelSize(tv.resourceId);

    }

    /**
     * Get device screem size
     *
     * @return Point contain screen size
     */
    public static Point getScreenSize() {

        WindowManager windowManager = (WindowManager) Application.getContext().getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        return size;
    }

    public static int getTextViewHeightBeforeRendering(TextView textView,String text){

        textView.setText(text, TextView.BufferType.SPANNABLE);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getScreenSize().x, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }



    public static void showAlertView(FragmentActivity activity, String title, String message, ArrayList<EditText> editTexts,
                                     String positiveString, DialogInterface.OnClickListener positiveOnClickListener,
                                     String negativeString, DialogInterface.OnClickListener negativeOnClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(title);

        builder.setMessage(message);

        builder.setCancelable(false);

        LinearLayout alertLinearLayout = new LinearLayout(activity);

        alertLinearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        lp.setMargins(30, 0, 30, 0);

        for (int i = 0; i < editTexts.size(); i++) {

            editTexts.get(i).setLayoutParams(lp);

            alertLinearLayout.addView(editTexts.get(i));
        }

        builder.setView(alertLinearLayout);

        builder.setPositiveButton(positiveString, positiveOnClickListener);

        builder.setNegativeButton(negativeString, negativeOnClickListener);

        AlertDialog alert = builder.create();

        alert.show();

    }




    public static void dismissAllDialogs(FragmentManager manager) {

        if (manager == null) {

            return;

        }

        List<Fragment> fragments = manager.getFragments();

        if (fragments == null)
            return;

        for (Fragment fragment : fragments) {

            if (fragment == null) continue;

            if (fragment instanceof DialogFragment) {

                DialogFragment dialogFragment = (DialogFragment) fragment;

                try {
                    dialogFragment.dismissAllowingStateLoss();
                } catch (Throwable t) {
                    Log.e(TAG, "exception in dismissing a dialog in dismissAllDialogs(FragmentManager manager) ", t);
                }
            }

            FragmentManager childFragmentManager = fragment.getChildFragmentManager();

            if (childFragmentManager != null) {

                dismissAllDialogs(childFragmentManager);

            }
        }

    }


    /**EdubookApplication
     * Hide loading view
     */
    public static void hideLoadingView(final View rootView, BaseFragment fragment) {

        if (rootView == null || fragment == null || fragment.getActivity() == null || !fragment.isAdded()) {

            if (rootView != null) rootView.clearAnimation();

            return;

        }


        rootView.post(new Runnable() {
            @Override
            public void run() {

                rootView.findViewById(R.id.loading_view).setClickable(false);

                rootView.findViewById(R.id.loading_view).animate()
                        .alpha(0f)
                        .setDuration(Application.getmShortAnimationDuration())
                        .setListener(new AnimatorListenerAdapter() {

                            @Override
                            public void onAnimationEnd(Animator animation) {

                                rootView.findViewById(R.id.loading_view).setVisibility(View.GONE);

                            }
                        });

            }
        });



    }


    /**
     * Hide loading view
     */
    public static void hideLoadingView(final View rootView, BaseFragment fragment, final Integer specificLayoutId) {

        if (rootView == null || fragment == null || fragment.getActivity() == null || !fragment.isAdded()) {

            return;

        }

        if (specificLayoutId == null) {

            hideLoadingView(rootView, fragment);

            return;

        }

        rootView.post(new Runnable() {
            @Override
            public void run() {

                rootView.findViewById(specificLayoutId).setClickable(false);

                rootView.findViewById(specificLayoutId).animate()
                        .alpha(0f)
                        .setDuration(Application.getmShortAnimationDuration())
                        .setListener(new AnimatorListenerAdapter() {

                            @Override
                            public void onAnimationEnd(Animator animation) {

                                rootView.findViewById(specificLayoutId).setVisibility(View.GONE);

                            }
                        });

            }
        });


    }

    public static void hideLoadingView(){

        if(loadingDialog == null){

            return;
        }

        loadingDialog.dismiss();
    }

    public static void changeStatusBarColor(Activity activity){

        Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.setStatusBarColor(activity.getResources().getColor(R.color.background_dark));

        }
    }

    public static void showLoadingView( View view ){

        loadingDialog = new Dialog(view.getContext(), android.R.style.Theme_Light_NoTitleBar);

        loadingDialog.setCancelable(false);

        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode ==KeyEvent.KEYCODE_BACK){

                    Managers.FragmentManager.popCurrentVisibleFragment();

                    loadingDialog.dismiss();
                }

                return false;
            }
        });

        View loadingView = LayoutInflater.from(Application.getContext()).inflate(R.layout.loading_view, (ViewGroup) view, false);

        loadingDialog.setContentView(loadingView);

        loadingDialog.show();

    }

    public static void showLoadingView(View rootView, BaseFragment fragment,String...loadingString) {

        if (rootView == null || fragment == null || fragment.getActivity() == null || !fragment.isAdded()) {

            return;

        }

        TextView loadingText = (TextView) rootView.findViewById(R.id.loading_label);

        if(loadingString.length>0){

            loadingText.setText(loadingString[0]);

        }

        if (loadingText != null) {

            loadingText.setTypeface(FontUtil.getFont(FontsType.LIGHT));

            loadingText.setTextColor(ColorUtil.getSystemColor(SystemColors.ACCENT_COLOR_LITE_NAVY));

        }

        ((View)rootView.getParent().getParent()).findViewById(R.id.loading_view).setVisibility(View.VISIBLE);

        ((View)rootView.getParent().getParent()).findViewById(R.id.loading_view).setAlpha(0);

        ((View)rootView.getParent().getParent()).findViewById(R.id.loading_view).animate()
                .alpha(1f)
                .setDuration(Application.getmShortAnimationDuration())
                .setListener(null);
    }

    public static int getNumberOfLinesForTextView(String text,TextView textView){

        int width = 0;

        Rect bounds = new Rect();

        Paint paint = new Paint();

        paint.setTextSize(textView.getTextSize());

        paint.getTextBounds(text, 0, text.length(), bounds);

        width = (int) Math.ceil( bounds.width());

        int textViewWidth = UIUtil.getScreenSize().x;

        float lineCount = (float)width/textViewWidth;

        if((int)lineCount < lineCount) lineCount =  ((int)lineCount) + 1;

        String [] lines = text.split("\n");

        int linesInEachLine = 0;

        if(lines.length > 1) {

            for (int i = 0; i < lines.length; i++) {

                int linesCount = getNumberOfLinesForTextView(lines[i], textView);

                if (linesCount > 1) {

                    linesInEachLine++;
                }
            }

        }

        int newLines = text.split("\n").length;

        int toReturn = 0;

        if(lineCount > newLines) toReturn = (int)lineCount;

        else{

            toReturn =  newLines;
        }

        return toReturn + linesInEachLine;
    }


    public static int getHightOfRecyclerView(RecyclerView recyclerView, int firstElements) {

        RecyclerView.Adapter listAdapter = recyclerView.getAdapter();

        if (listAdapter == null) return 0;

        int totalHeight = 0;

        int total = firstElements == 0 ? listAdapter.getItemCount() : firstElements;

        for (int i = 0; i < total; i++) {

            RecyclerView.ViewHolder holder = listAdapter.onCreateViewHolder(recyclerView, listAdapter.getItemViewType(i));

            listAdapter.onBindViewHolder(holder, i);

            View listItem = holder.itemView;

            if (listItem == null) continue;

            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += listItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams par = recyclerView.getLayoutParams();

        par.height = totalHeight;

        recyclerView.setLayoutParams(par);

        recyclerView.requestLayout();

        return totalHeight;

    }

}
