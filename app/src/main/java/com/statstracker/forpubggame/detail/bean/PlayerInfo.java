package com.statstracker.forpubggame.detail.bean;

import java.util.Date;
import java.util.List;

/**
 * 角色信息bean
 */
public class PlayerInfo {

    public List<Player> data;


    public static class Matches {

        public List<MatcheBean> data;


    }

    public class Relationships {

        public Matches matches;

    }

    public static class Player {

        public String type;
        public String id;
        public Attributes attributes;
//        public Relationships relationships;

    }

    public static class MatcheBean {

        public String type;
        public String id;

    }

    public static class Attributes {

        public String titleId;
        public String shardId;
        public Date createdAt;
        public Date updatedAt;
        public String patchVersion;
        public String name;
        public String stats;

    }

}
