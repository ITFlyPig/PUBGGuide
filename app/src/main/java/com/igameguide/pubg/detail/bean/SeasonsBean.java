package com.igameguide.pubg.detail.bean;

import java.util.List;

public class SeasonsBean {
    public List<Data> data;


    public static class Attributes {

        public boolean isCurrentSeason;
        public boolean isOffseason;

    }

    public class Data {
        public String type;
        public String id;
        public Attributes attributes;

    }
}
