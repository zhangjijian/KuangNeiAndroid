package me.kuangneipro.util;

import me.kuangneipro.R;
import me.kuangneipro.activity.MainActivity;
import me.kuangneipro.core.KuangNeiApplication;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public class NotificationUtil {

	public static void makeANotification(Context context, String content){
		Intent mIntent = new Intent(context, MainActivity.class);  
        //主要在于PendingIntent的getActivity方法中的参数  
		PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);  
        //如果使用下面注释掉的代码，将会出现上面说讲到的问题,当然在SecondActivi
		
		Notification notification = new NotificationCompat.Builder(KuangNeiApplication.getInstance())
        .setLargeIcon(BitmapFactory.decodeResource( KuangNeiApplication.getInstance().getResources(), R.drawable.ic_launcher)).setSmallIcon(R.drawable.ic_launcher)
        .setTicker(content).setContentInfo("用户名")
        .setContentTitle("框内").setContentText(content)
        .setNumber(1)
        .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
        .setContentIntent(mPendingIntent)
        .build();
		NotificationManager nm = (NotificationManager)KuangNeiApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE); 
		nm.notify(0, notification);
		
		

	}
	
}
