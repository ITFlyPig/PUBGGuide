package com.statstracker.forpubggame.detail.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class SeasonDetail {

    public Paiwei duo;
    public Paiwei duoFpp;
    public Paiwei solo;
    public Paiwei soloFpp;
    public Paiwei squad;
    public Paiwei squadFpp;


    public static SeasonDetail parse(String resp) {
        if (TextUtils.isEmpty(resp)) {
            return null;
        }
        JSONObject respJson = JSON.parseObject(resp);
        if (respJson == null) {
            return null;
        }

        JSONObject dataObj = respJson.getJSONObject("data");
        if (dataObj == null) {
            return null;
        }
        JSONObject attrsObj = dataObj.getJSONObject("attributes");
        if (attrsObj == null) {
            return null;
        }
        JSONObject modesObj = attrsObj.getJSONObject("gameModeStats");
        if (modesObj == null) {
            return null;
        }

        SeasonDetail seasonDetail = new SeasonDetail();
        seasonDetail.duo = parseBean(modesObj.getJSONObject("duo"));
        seasonDetail.duoFpp = parseBean(modesObj.getJSONObject("duo-fpp"));
        seasonDetail.solo = parseBean(modesObj.getJSONObject("solo"));
        seasonDetail.soloFpp = parseBean(modesObj.getJSONObject("solo-fpp"));
        seasonDetail.squad = parseBean(modesObj.getJSONObject("squad"));
        seasonDetail.squadFpp = parseBean(modesObj.getJSONObject("squad-fpp"));
        return seasonDetail;

    }


    private static Paiwei parseBean(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        Paiwei paiwei = new Paiwei();
        paiwei.assists = jsonObject.getFloat("assists");
        paiwei.bestRankPoint = jsonObject.getFloat("bestRankPoint");
        paiwei.boosts = jsonObject.getFloat("boosts");
        paiwei.dailyKills = jsonObject.getFloat("dailyKills");
        paiwei.dailyWins  = jsonObject.getFloat("dailyWins");
        paiwei.damageDealt = jsonObject.getFloat("damageDealt");
        paiwei.days = jsonObject.getFloat("days");
        paiwei.dBNOs = jsonObject.getFloat("dBNOs");
        paiwei.headshotKills = jsonObject.getFloat("headshotKills");
        paiwei.heals = jsonObject.getFloat("heals");
        paiwei.killPoints = jsonObject.getFloat("killPoints");
        paiwei.longestKill = jsonObject.getFloat("longestKill");
        paiwei.boosts = jsonObject.getFloat("boosts");
        paiwei.longestTimeSurvived = jsonObject.getFloat("longestTimeSurvived");
        paiwei.kills = jsonObject.getFloat("kills");
        paiwei.longestKill = jsonObject.getFloat("longestKill");
        paiwei.losses = jsonObject.getFloat("losses");
        paiwei.maxKillStreaks = jsonObject.getFloat("maxKillStreaks");
        paiwei.mostSurvivalTime = jsonObject.getFloat("mostSurvivalTime");
        paiwei.rankPoints = jsonObject.getFloat("rankPoints");
        paiwei.revives = jsonObject.getFloat("revives");
        paiwei.rideDistance = jsonObject.getFloat("rideDistance");
        paiwei.roadKills = jsonObject.getFloat("roadKills");
        paiwei.roundsPlayed = jsonObject.getFloat("roundsPlayed");
        paiwei.suicides = jsonObject.getFloat("suicides");
        paiwei.swimDistance = jsonObject.getFloat("swimDistance");
        paiwei.teamKills = jsonObject.getFloat("teamKills");
        paiwei.timeSurvived = jsonObject.getFloat("timeSurvived");
        paiwei.top10s = jsonObject.getFloat("top10s");
        paiwei.vehicleDestroys = jsonObject.getFloat("vehicleDestroys");
        paiwei.walkDistance = jsonObject.getFloat("walkDistance");
        paiwei.weaponsAcquired = jsonObject.getFloat("weaponsAcquired");
        paiwei.weeklyKills = jsonObject.getFloat("weeklyKills");
        paiwei.weeklyWins = jsonObject.getFloat("weeklyWins");
        paiwei.winPoints = jsonObject.getFloat("winPoints");
        paiwei.wins = jsonObject.getFloat("wins");

        return paiwei;


    }


}
