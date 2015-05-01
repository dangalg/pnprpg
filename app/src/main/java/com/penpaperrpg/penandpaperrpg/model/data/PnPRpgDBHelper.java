package com.penpaperrpg.penandpaperrpg.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.penpaperrpg.penandpaperrpg.model.data.PnPRpgContract.RoomEntry;
import com.penpaperrpg.penandpaperrpg.model.data.PnPRpgContract.PlayerEntry;

/**
 * Created by dangal on 4/1/15.
 */
public class PnPRpgDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "penpaperrpgdb.db";

    public enum Role {
        DM,
        PLAYER,
        GUEST
    }

    public PnPRpgDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_ROOM_TABLE = "CREATE TABLE " + RoomEntry.TABLE_NAME + " (" +
                RoomEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RoomEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                RoomEntry.COLUMN_MAX_PLAYERS + " INTEGER NOT NULL, " +
                RoomEntry.COLUMN_PLAYER_NUM + " INTEGER NOT NULL, " +
                RoomEntry.COLUMN_PASSWORD + " TEXT, " +
                "UNIQUE (" + RoomEntry.COLUMN_NAME +") ON CONFLICT IGNORE"+
                " );";

        final String SQL_CREATE_PLAYER_TABLE = "CREATE TABLE " + PlayerEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                PlayerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                PlayerEntry.COLUMN_NICKNAME + " TEXT NOT NULL, " +
                PlayerEntry.COLUMN_ROLE + " TEXT NOT NULL, " +

//                // Set up the location column as a foreign key to location table.
//                " FOREIGN KEY (" + WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
//                LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + PlayerEntry.COLUMN_NICKNAME + ") ON CONFLICT IGNORE);";

        sqLiteDatabase.execSQL(SQL_CREATE_ROOM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PLAYER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RoomEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}


