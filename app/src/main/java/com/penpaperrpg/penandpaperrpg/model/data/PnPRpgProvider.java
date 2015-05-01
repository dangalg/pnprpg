package com.penpaperrpg.penandpaperrpg.model.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by dangal on 4/1/15.
 */
public class PnPRpgProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private PnPRpgDBHelper mOpenHelper;

    static final int ROOM = 100;
    static final int ROOM_WITH_ID = 110;
    static final int ROOM_WITH_NAME = 120;
    static final int PLAYER = 200;
    static final int PLAYER_WITH_ID = 210;
    static final int PLAYER_WITH_NAME = 220;
    static final int PLAYERS_WITH_ROOM_NAME = 230;

    private static final SQLiteQueryBuilder sRoomQueryBuilder;
    private static final SQLiteQueryBuilder sPlayerQueryBuilder;

    static{
        sRoomQueryBuilder = new SQLiteQueryBuilder();

        sRoomQueryBuilder.setTables(
                PnPRpgContract.RoomEntry.TABLE_NAME);

        sPlayerQueryBuilder = new SQLiteQueryBuilder();

        sPlayerQueryBuilder.setTables(
                PnPRpgContract.PlayerEntry.TABLE_NAME);
    }

    // Room by name
    private static final String sRoomByNameSelection =
            PnPRpgContract.RoomEntry.TABLE_NAME+
                    "." + PnPRpgContract.RoomEntry.COLUMN_NAME + " = ? ";

    // Room by ID
    private static final String sRoomByIdSelection =
            PnPRpgContract.RoomEntry.TABLE_NAME+
                    "." + PnPRpgContract.PlayerEntry._ID + " = ? ";

    // Player by ID selection ?
    private static final String sPlayerByIdSelection =
            PnPRpgContract.PlayerEntry.TABLE_NAME+
                    "." + PnPRpgContract.PlayerEntry._ID + " = ? ";

    // Player by name selection ?
    private static final String sPlayerByRoomNameSelection =
            PnPRpgContract.RoomEntry.TABLE_NAME+
                    "." + PnPRpgContract.RoomEntry.COLUMN_NAME + " = ? ";

    // Player by name selection ?
    private static final String sPlayerByNameSelection =
            PnPRpgContract.PlayerEntry.TABLE_NAME+
                    "." + PnPRpgContract.PlayerEntry.COLUMN_NICKNAME + " = ? ";

    private Cursor getRoomById(Uri uri, String[] projection, String sortOrder) {
        String id = PnPRpgContract.RoomEntry.getIdFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sRoomByIdSelection;
        selectionArgs = new String[]{id};


        return sRoomQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getRoomByName(Uri uri, String[] projection, String sortOrder) {
        String name = PnPRpgContract.RoomEntry.getNameFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sRoomByNameSelection;
        selectionArgs = new String[]{name};


        return sRoomQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getPlayerById(Uri uri, String[] projection, String sortOrder) {
        String id = PnPRpgContract.PlayerEntry.getIdFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sPlayerByIdSelection;
        selectionArgs = new String[]{id};


        return sPlayerQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getPlayerByName(Uri uri, String[] projection, String sortOrder) {
        String playerName = PnPRpgContract.PlayerEntry.getEmuticonNameFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sPlayerByNameSelection;
        selectionArgs = new String[]{playerName};


        return sPlayerQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getPlayersByRoomName(Uri uri, String[] projection, String sortOrder) {
        String roomName = PnPRpgContract.RoomEntry.getNameFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sPlayerByRoomNameSelection;
        selectionArgs = new String[]{roomName};


        return sPlayerQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PnPRpgContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, PnPRpgContract.PATH_ROOM, ROOM);
        matcher.addURI(authority, PnPRpgContract.PATH_ROOM + "/*", ROOM_WITH_ID);
        matcher.addURI(authority, PnPRpgContract.PATH_ROOM + "/#", ROOM_WITH_NAME);
        matcher.addURI(authority, PnPRpgContract.PATH_PLAYER, PLAYER);
        matcher.addURI(authority, PnPRpgContract.PATH_PLAYER + "/*", PLAYER_WITH_ID);
        matcher.addURI(authority, PnPRpgContract.PATH_PLAYER + "/#", PLAYER_WITH_NAME);
        matcher.addURI(authority, PnPRpgContract.PATH_PLAYER + "/#", PLAYERS_WITH_ROOM_NAME);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PnPRpgDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "room"
            case ROOM:
            {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PnPRpgContract.RoomEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "room/#"
            case ROOM_WITH_ID: {
                retCursor = getRoomById(uri, projection, sortOrder);
                break;
            }
            // "room/#"
            case ROOM_WITH_NAME: {
                retCursor = getRoomByName(uri, projection, sortOrder);
                break;
            }

            // "player"
            case PLAYER: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PnPRpgContract.PlayerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "player/*"
            case PLAYER_WITH_ID: {
                retCursor = getPlayerById(uri, projection, sortOrder);
                break;
            }

            // "player/#"
            case PLAYER_WITH_NAME: {
                retCursor = getPlayerByName(uri, projection, sortOrder);
                break;
            }

            // "player/#"
            case PLAYERS_WITH_ROOM_NAME: {
                retCursor = getPlayersByRoomName(uri, projection, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case ROOM:
                return PnPRpgContract.RoomEntry.CONTENT_TYPE;
            case ROOM_WITH_ID:
                return PnPRpgContract.RoomEntry.CONTENT_ITEM_TYPE;
            case ROOM_WITH_NAME:
                return PnPRpgContract.RoomEntry.CONTENT_ITEM_TYPE;
            case PLAYER:
                return PnPRpgContract.PlayerEntry.CONTENT_TYPE;
            case PLAYER_WITH_ID:
                return PnPRpgContract.PlayerEntry.CONTENT_ITEM_TYPE;
            case PLAYER_WITH_NAME:
                return PnPRpgContract.PlayerEntry.CONTENT_ITEM_TYPE;
            case PLAYERS_WITH_ROOM_NAME:
                return PnPRpgContract.PlayerEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ROOM: {
                long _id = db.insert(PnPRpgContract.RoomEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = PnPRpgContract.RoomEntry.buildPackagesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case PLAYER: {
                long _id = db.insert(PnPRpgContract.PlayerEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = PnPRpgContract.PlayerEntry.buildEmuticonUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case ROOM:
                rowsDeleted = db.delete(
                        PnPRpgContract.RoomEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PLAYER:
                rowsDeleted = db.delete(
                        PnPRpgContract.PlayerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ROOM:
                rowsUpdated = db.update(PnPRpgContract.RoomEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case PLAYER:
                rowsUpdated = db.update(PnPRpgContract.PlayerEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case ROOM:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PnPRpgContract.RoomEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case PLAYER:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PnPRpgContract.PlayerEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
