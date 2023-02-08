package team.standalone.fumiya.service

import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.onesignal.OSNotification
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import team.standalone.core.common.util.Result
import team.standalone.feature.billing.BillingSubscriptionActivity
import team.standalone.fumiya.R
import team.standalone.fumiya.ui.MainActivity
import team.standalone.fumiya.ui.auth.signin.AuthActivity

@DelicateCoroutinesApi
class OneSignalService : OneSignal.OSRemoteNotificationReceivedHandler {

    override fun remoteNotificationReceived(
        context: Context,
        notificationReceivedEvent: OSNotificationReceivedEvent?
    ) {
        notificationReceivedEvent?.let {
            when (val action = getPayload(ACTION, it.notification)) {
                ACTION_CHAT -> onActionBase(context, it.notification, it, action)
                ACTION_PHOTO, ACTION_VIDEO, ACTION_LIVE -> onActionModule(context, it.notification, it, action)
                else -> onActionMain(context, it.notification, it)
            }
        }
    }

    /**
     * Handle base related notifications.
     */
    private fun onActionBase(
        context: Context,
        notification: OSNotification,
        notificationReceivedEvent: OSNotificationReceivedEvent,
        actionKey: String
    ) {
        notificationReceivedEvent.complete(updateNotification(context, notification))
        OneSignal.setNotificationOpenedHandler {
            context.startActivity(Intent(context, getActivity(context)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(ACTION, actionKey)
                putExtra(CHAT_ID, getPayload(CHAT_ID, notification))
                putExtra(GROUP_ID, getPayload(GROUP_ID, notification))
            })
        }
    }

    /**
     * Handle park and station related notifications.
     */
    private fun onActionModule(
        context: Context,
        notification: OSNotification,
        notificationReceivedEvent: OSNotificationReceivedEvent,
        actionKey: String
    ) {
        notificationReceivedEvent.complete(updateNotification(context, notification))
        OneSignal.setNotificationOpenedHandler {
            context.startActivity(Intent(context, getActivity(context)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(ACTION, actionKey)
            })
        }
    }

    /**
     * Handle general notifications.
     * */
    private fun onActionMain(
        context: Context,
        notification: OSNotification,
        notificationReceivedEvent: OSNotificationReceivedEvent,
    ) {
        notificationReceivedEvent.complete(updateNotification(context, notification))
        OneSignal.setNotificationOpenedHandler {
            context.startActivity(
                Intent(
                    context,
                    when (getUser(context)) {
                        null -> AuthActivity::class.java
                        else -> MainActivity::class.java
                    }
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(ACTION, getPayload(ANCHOR, notification))
                }
            )
        }
    }

    /**
     * Identify which screen to open based on user's status.
     * */
    private fun getActivity(context: Context) = when (val result = getUser(context)) {
        null -> AuthActivity::class.java
        else -> when (result.subscription.subscribed) {
            true -> MainActivity::class.java
            else -> BillingSubscriptionActivity::class.java
        }
    }

    /**
     * Get current user data.
     * */
    private fun getUser(context: Context) = runBlocking {
        val utilitiesEntryPoint = EntryPointAccessors
            .fromApplication(
                context,
                OneSignalEntryPoint::class.java
            )
        when (val result = utilitiesEntryPoint.checkAuthenticatedUserUseCase()) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }

    /**
     * Update OSNotification notification by including design config.
     * */
    private fun updateNotification(
        context: Context,
        notification: OSNotification
    ): OSNotification {
        val mutableNotification = notification.mutableCopy()
        mutableNotification.setExtender { builder: NotificationCompat.Builder ->
            builder.setColor(
                ContextCompat.getColor(context, R.color.colorFumiyaTheme)
            )
        }
        return mutableNotification
    }

    /**
     * Retrieve action additional data from onesignal notification.
     */
    private fun getPayload(
        payloadKey: String,
        notification: OSNotification
    ): String {
        return if (notification.additionalData == null) {
            ""
        } else try {
            notification.additionalData.get(payloadKey).toString()
        } catch (e: JSONException) {
            ""
        }
    }

    companion object {
        const val ACTION_CHAT = "chat"
        const val ACTION_PHOTO = "gallery"
        const val ACTION_VIDEO = "video"
        const val ACTION_LIVE = "liveStart"
        const val ACTION = "action"
        const val ANCHOR = "anchor"
        const val CHAT_ID = "chatId"
        const val GROUP_ID = "groupId"
    }
}