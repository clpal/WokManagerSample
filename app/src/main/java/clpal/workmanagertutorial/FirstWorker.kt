package clpal.workmanagertutorial

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class FirstWorker(context: Context, workParams: WorkerParameters) : Worker(context, workParams) {
    override fun doWork(): Result {
        Thread.sleep(3000)
        makeNotification("First Worker", applicationContext)
        return Result.success()
    }

    private fun makeNotification(s: String, context: Context) {
        val applicationContext = context
        val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "first_worker",
                "worker",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationChannel = NotificationCompat.Builder(applicationContext, "first_worker").setContentTitle(s)
                .setSubText("Hello my first work is done").setSmallIcon(R.drawable.ic_launcher_background)
                .build()
        notificationManager.notify(1, notificationChannel)
    }

}