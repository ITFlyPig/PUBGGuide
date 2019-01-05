package com.igameguide.pubg.weapon.bean;

import java.io.Serializable;

public class WeaponBean implements Serializable{
    //枪使用的字段
    public String name;
    public String hitDamage;
    public String initBulletSpeed;
    public String bodyHitImpactPower;
    public String zeroRange;
    public String ammoPerMag;
    public String timeBetweenShots;
    public String firingModes;
    public String shotCount;
    public String category;
    public String logoId;
    public String lanuage;

    //投掷物使用的字段
    public String throwTime;
    public String throwCooldownDuration;
    public String fireDelay;
    public String timeLimit;
    public String detonation;
    public String explosionDelay;

    //近战武器使用的字段
    public String damage;
    public String impact;
    public String hitRangeLeeway;



}
