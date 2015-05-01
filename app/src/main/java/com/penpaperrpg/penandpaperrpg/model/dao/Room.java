package com.penpaperrpg.penandpaperrpg.model.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import com.penpaperrpg.penandpaperrpg.model.data.PnPRpgContract.RoomEntry;
/**
 * Created by dangal on 5/1/15.
 */
public class Room implements Serializable {

    // Fields
    private String name;
    private int max_players;
    private int player_num;
    private String password;

    public Room(String name,
                int max_players,
                   int player_num,
                   String password
                   ) {
        this.name = name;
        this.max_players = max_players;
        this.player_num = player_num;
        this.password = password;
    }

    // region Properties

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }

    public int getPlayer_num() {
        return player_num;
    }

    public void setPlayer_num(int player_num) {
        this.player_num = player_num;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // endregion Properties

    /**
     * Convenient method to get the objects data members in ContentValues object.
     * This will be useful for Content Provider operations,
     * which use ContentValues object to represent the data.
     *
     * @return
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(RoomEntry.COLUMN_NAME, name);
        values.put(RoomEntry.COLUMN_MAX_PLAYERS, max_players);
        values.put(RoomEntry.COLUMN_PLAYER_NUM, player_num);
        values.put(RoomEntry.COLUMN_PASSWORD, password);

        return values;
    }

    // Create a TvShow object from a cursor
    public static Room fromCursor(Cursor curRoom) {

        String name = curRoom.getString(curRoom.getColumnIndex(RoomEntry.COLUMN_NAME));
        int max_players = curRoom.getInt(curRoom.getColumnIndex(RoomEntry.COLUMN_MAX_PLAYERS));
        int player_num = curRoom.getInt(curRoom.getColumnIndex(RoomEntry.COLUMN_PLAYER_NUM));
        String password = curRoom.getString(curRoom.getColumnIndex(RoomEntry.COLUMN_PASSWORD));

        return new Room( name,
                max_players,
                player_num,
                password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

//        if (name != pack.name) return false;
        if (!name.equals(room.name)) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        int result = name.hashCode();
//        result = 31 * result + year;
//        return result;
//    }

    @Override
    public String toString() {
        return name;
    }
}
