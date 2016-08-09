package broadcastReceivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import java.util.Locale;

import activities.PostRetriever;
import edubook.edubook.R;

public class GcmMessageHandler extends GcmListenerService {
    public static int MESSAGE_NOTIFICATION_ID = 435345;

    private String itemId = "";
    @Override
    public void onMessageReceived(String from, Bundle data) {
        from = "Edubook";
        String custom = data.getString("custom");

        try {
            JSONObject jsonObject = new JSONObject(custom);
            JSONObject type = jsonObject.getJSONObject("type");
            String msgAr = type.getString("notifications_text_ar");
            String msgEn = type.getString("notification_text");
            itemId = jsonObject.getString("item_id");
            MESSAGE_NOTIFICATION_ID = Integer.valueOf(itemId);
            Locale current = getResources().getConfiguration().locale;
            if(current.getLanguage().equalsIgnoreCase("en")){

                createNotification(from,msgEn);
            }
            else{

                createNotification(from,msgAr);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        //createNotification(from, message);
    }

    private void createNotification(String title, String body) {
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setContentText(body);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(getBaseContext(), PostRetriever.class).putExtra("postId",itemId), PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

}
