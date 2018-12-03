package com.igameguide.pubg.util;

import android.text.TextUtils;

public class ServerUrls {

    /**
     * 查询玩家信息的url
     * @param playerName
     * @return
     */
    public static String getQueryPlayerInfoUrl(String playerName, String region) {
        if (TextUtils.isEmpty(playerName) || TextUtils.isEmpty(region)) {
            return null;
        }
        return "https://api.pubg.com/shards/" + region + "/players?filter[playerNames]=" + playerName;
    }

    /**
     * 获取赛季的url
     * @param region
     * @return
     */
    public static String getSeasonUrl(String region) {
        if (TextUtils.isEmpty(region)) {
            return null;
        }
        return "https://api.pubg.com/shards/" + region +"/seasons";

    }

    /**
     * 获取排位信息的url
     * @param playerId
     * @param season
     * @param region
     * @return
     */
    public static String getDetailInfo(String playerId, String season, String region) {
        if (TextUtils.isEmpty(playerId) || TextUtils.isEmpty(season)) {
            return null;
        }
        return "https://api.pubg.com/shards/" + region + "/players/" + playerId +"/seasons/" + season;

    }
}
