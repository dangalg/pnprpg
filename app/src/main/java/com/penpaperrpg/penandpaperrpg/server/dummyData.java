package com.penpaperrpg.penandpaperrpg.server;

/**
 * Created by dangal on 5/1/15.
 */
public class dummyData {

    public String getRoomsDummyJsonString()
    {
        return "{\"rooms\":[{\"name\": \"AD&D2E\",\"max_players\": 5,\"player_num\": 2,\"password\": \"1234\"},{\"name\": \"D&D3E\",\"max_players\": 5,\"player_num\": 3,\"password\": \"1234\"}]}";
    }

    public String getPlayersDummyJsonString()
    {
        return "{\"rooms\":[{\"name\": \"AD&D2E\",\"max_players\": 5,\"player_num\": 2,\"password\": \"1234\"},{\"name\": \"D&D3E\",\"max_players\": 5,\"player_num\": 3,\"password\": \"1234\"}]}";
    }
}
