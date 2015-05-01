package com.penpaperrpg.penandpaperrpg.server;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.OperationCanceledException;
import android.util.Log;

import java.io.IOException;

/**
 * Created by dangal on 5/1/15.
 */
public class PnPRpgSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "EmuSyncAdapter";

    private final AccountManager mAccountManager;

    public PnPRpgSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {

        // Building a print of the extras we got
        StringBuilder sb = new StringBuilder();
        if (extras != null) {
            for (String key : extras.keySet()) {
                sb.append(key + "[" + extras.get(key) + "] ");
            }
        }

        Log.d("emu", TAG + "> onPerformSync for account[" + account.name + "]. Extras: " + sb.toString());

//        try {
//            // Get the auth token for the current account and
//            // the userObjectId, needed for creating items on Parse.com account
//            String authToken = mAccountManager.blockingGetAuthToken(account,
//                    AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
//            String userObjectId = mAccountManager.getUserData(account,
//                    AccountGeneral.USERDATA_USER_OBJ_ID);
//
//            ParseComServerAccessor parseComService = new ParseComServerAccessor();
//
//            Log.d("udinic", TAG + "> Get remote TV Shows");
//            // Get shows from remote
//            List<TvShow> remoteTvShows = parseComService.getShows(authToken);
//
//            Log.d("udinic", TAG + "> Get local TV Shows");
//            // Get shows from local
//            ArrayList<TvShow> localTvShows = new ArrayList<TvShow>();
//            Cursor curTvShows = provider.query(TvShowsContract.CONTENT_URI, null, null, null, null);
//            if (curTvShows != null) {
//                while (curTvShows.moveToNext()) {
//                    localTvShows.add(TvShow.fromCursor(curTvShows));
//                }
//                curTvShows.close();
//            }
//
//            // See what Local shows are missing on Remote
//            ArrayList<TvShow> showsToRemote = new ArrayList<TvShow>();
//            for (TvShow localTvShow : localTvShows) {
//                if (!remoteTvShows.contains(localTvShow))
//                    showsToRemote.add(localTvShow);
//            }
//
//            // See what Remote shows are missing on Local
//            ArrayList<TvShow> showsToLocal = new ArrayList<TvShow>();
//            for (TvShow remoteTvShow : remoteTvShows) {
//                if (!localTvShows.contains(remoteTvShow) && remoteTvShow.year != 1) // TODO REMOVE THIS
//                    showsToLocal.add(remoteTvShow);
//            }
//
//            if (showsToRemote.size() == 0) {
//                Log.d("udinic", TAG + "> No local changes to update server");
//            } else {
//                Log.d("udinic", TAG + "> Updating remote server with local changes");
//
//                // Updating remote tv shows
//                for (TvShow remoteTvShow : showsToRemote) {
//                    Log.d("udinic", TAG + "> Local -> Remote [" + remoteTvShow.name + "]");
//                    parseComService.putShow(authToken, userObjectId, remoteTvShow);
//                }
//            }
//
//            if (showsToLocal.size() == 0) {
//                Log.d("udinic", TAG + "> No server changes to update local database");
//            } else {
//                Log.d("udinic", TAG + "> Updating local database with remote changes");
//
//                // Updating local tv shows
//                int i = 0;
//                ContentValues showsToLocalValues[] = new ContentValues[showsToLocal.size()];
//                for (TvShow localTvShow : showsToLocal) {
//                    Log.d("udinic", TAG + "> Remote -> Local [" + localTvShow.name + "]");
//                    showsToLocalValues[i++] = localTvShow.getContentValues();
//                }
//                provider.bulkInsert(TvShowsContract.CONTENT_URI, showsToLocalValues);
//            }
//
//            Log.d("udinic", TAG + "> Finished.");

//        } catch (OperationCanceledException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            syncResult.stats.numIoExceptions++;
//            e.printStackTrace();
//        } catch (AuthenticatorException e) {
//            syncResult.stats.numAuthExceptions++;
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
