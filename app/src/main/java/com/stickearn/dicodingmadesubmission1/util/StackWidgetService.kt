package com.stickearn.dicodingmadesubmission1.util

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by devis on 2020-01-12
 */

class StackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)

}