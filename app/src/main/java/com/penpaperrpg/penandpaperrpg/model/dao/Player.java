package com.penpaperrpg.penandpaperrpg.model.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.penpaperrpg.penandpaperrpg.model.data.PnPRpgContract.PlayerEntry;

import java.io.Serializable;

/**
 * Created by dangal on 5/1/15.
 */
public class Player implements Serializable {

    // Fields
    private String nickname;
    private String role;

    public Player(String nickname,
                  String role
    ) {
        this.nickname = nickname;
        this.role = role;
    }

    // region Properties

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

        values.put(PlayerEntry.COLUMN_NICKNAME, nickname);
        values.put(PlayerEntry.COLUMN_ROLE, role);

        return values;
    }

    // Create a TvShow object from a cursor
    public static Player fromCursor(Cursor curRoom) {

        String nickname = curRoom.getString(curRoom.getColumnIndex(PlayerEntry.COLUMN_NICKNAME));
        String role = curRoom.getString(curRoom.getColumnIndex(PlayerEntry.COLUMN_ROLE));

        return new Player( nickname,
                role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

//        if (name != pack.name) return false;
        if (!nickname.equals(player.nickname)) return false;

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
        return nickname;
    }
}
