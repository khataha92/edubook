package broadcastReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import java.util.Locale;

import activities.Home;
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
    }

    private void createNotification(String title, String body) {

        Context context = this.getApplicationContext();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.edubook_logo).setContentTitle(title).setContentText(body);

        Intent intent =new Intent(getApplicationContext(), Home.class).putExtra("postId",itemId);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_NO_CREATE);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        Notification notification = mBuilder.build();

        notification.defaults = Notification.DEFAULT_ALL;

        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, notification);

    }

}
