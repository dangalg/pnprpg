package com.penpaperrpg.penandpaperrpg.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by dangal on 5/1/15.
 */
public class PnPRpgSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static PnPRpgSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new PnPRpgSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
