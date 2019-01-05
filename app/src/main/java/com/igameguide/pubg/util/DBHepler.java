package com.igameguide.pubg.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.igameguide.pubg.R;
import com.igameguide.pubg.pic.bean.WallPaperBean;
import com.igameguide.pubg.video.bean.VideoItemBean;
import com.igameguide.pubg.weapon.bean.WeaponBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHepler {
    private static final String DATABASE_NAME = "pubg_guide";
    private static final String TABLE_VIDEOS = "videos";
    private SQLiteDatabase mSQLiteDatabase;
    private static DBHepler instance;


    public static DBHepler getInstance() {
        if (instance == null) {
            synchronized (DBHepler.class) {
                if (instance == null) {
                    instance = new DBHepler();
                }

            }

        }
        return instance;
    }


    /**
     * 将asset中的文件拷贝到本地私有文件
     *
     * @param context
     * @param raw
     * @param destName
     */
    private synchronized void  copyFromAssetTo(Context context, int raw, String destName) {
        if (TextUtils.isEmpty(destName) || raw < 0 || context == null) {
            return;
        }

        File f = context.getFileStreamPath(destName);
        if (f.exists()) {
            return;
        }

        // 打开静态数据库文件的输入流
        InputStream is = context.getResources().openRawResource(raw);
        // 通过Context类来打开目标数据库文件的输出流
        FileOutputStream os = null;
        try {
            os = context.openFileOutput(destName, Context.MODE_PRIVATE);
            byte[] buffer = new byte[512];
            int count = 0;
            // 将静态数据库文件拷贝到目的地
            while ((count = is.read(buffer)) > 0) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拷贝本地的数据库文件
     *
     * @param context
     */
    private void copyDBFile(Context context) {
        if (context == null) {
            return;
        }
        copyFromAssetTo(context, R.raw.data, DATABASE_NAME);
    }


    /**
     * 打开数据库文件
     *
     * @param context
     */
    public synchronized void OpenDatabase(Context context) {
        if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen() || context == null) {
            return;
        }
        copyDBFile(context);
        mSQLiteDatabase = SQLiteDatabase.openDatabase(context.getFileStreamPath(DATABASE_NAME).getAbsolutePath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }


    /**
     * 据语言查询
     * @param language
     * @return
     */
    public ArrayList<VideoItemBean> queryByLanguage(String language) {

        String sql="select * from videos where language='" + language +"'";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        try {
            return parseToBeans(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 据游标查询得到对应的bean
     * @param cursor
     * @return
     */
    public ArrayList<VideoItemBean> parseToBeans(Cursor cursor) {
        ArrayList<VideoItemBean> beans = new ArrayList<VideoItemBean>();
        while (cursor.moveToNext()) {
            VideoItemBean bean = new VideoItemBean();
            if (cursor != null && cursor.getCount() != 0) {

                bean.title = cursor.getString(cursor.getColumnIndex(VideoTable.TITLE));
                bean.link = cursor.getString(cursor.getColumnIndex(VideoTable.LINK));
                bean.coverUrl = cursor.getString(cursor.getColumnIndex(VideoTable.LOGO_URL));

                beans.add(bean);
            }
        }
        Log.d("wyl", "查询到的数据：" + beans.size());
        if (cursor != null) {
            cursor.close();
        }
        return beans;
    }


    /**
     * 获取全部的壁纸
     * @return
     */
    public List<WallPaperBean> getAllWallPapers() {
        String sql = "select * from wallpaper";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        try {
            return parseToWallPaperList(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * 获取壁纸的list
     * @param cursor
     * @return
     */
    private List<WallPaperBean> parseToWallPaperList(Cursor cursor){
        if (cursor == null) {
            return null;
        }
        List<WallPaperBean> wallPaperBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
            WallPaperBean bean = new WallPaperBean();
            if (cursor.getCount() != 0) {
                bean.id = cursor.getString(cursor.getColumnIndex(WallPaperTable.ID));
                bean.downloadUrl = cursor.getString(cursor.getColumnIndex(WallPaperTable.DOWNLOAD));
                bean.preview = cursor.getString(cursor.getColumnIndex(WallPaperTable.PREVIEW));
                bean.title = cursor.getString(cursor.getColumnIndex(WallPaperTable.TITLE));
                wallPaperBeans.add(bean);
            }
        }

        cursor.close();
        return wallPaperBeans;
    }



    /**
     * 据语言查询枪
     * @param language
     * @return
     */
    public ArrayList<WeaponBean> queryWeaponByLanguage(String language) {

        String sql="select * from guns where language='" + language +"'";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        try {
            return parseToWeaponBeans(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 据游标查询得到对应的bean
     * @param cursor
     * @return
     */
    public ArrayList<WeaponBean> parseToWeaponBeans(Cursor cursor) {
        ArrayList<WeaponBean> beans = new ArrayList<WeaponBean>();
        while (cursor.moveToNext()) {
            WeaponBean bean = new WeaponBean();
            if (cursor != null && cursor.getCount() != 0) {
                bean.name = cursor.getString(cursor.getColumnIndex(GunsTable.NAME));
                bean.hitDamage = cursor.getString(cursor.getColumnIndex(GunsTable.HIT_DAMAGE));
                bean.initBulletSpeed = cursor.getString(cursor.getColumnIndex(GunsTable.INITIAL_BULLET_SPEED));
                bean.bodyHitImpactPower = cursor.getString(cursor.getColumnIndex(GunsTable.BODY_HIT_IMPACT_POWER));
                bean.zeroRange = cursor.getString(cursor.getColumnIndex(GunsTable.ZERO_RANGE));
                bean.ammoPerMag = cursor.getString(cursor.getColumnIndex(GunsTable.AMMO_PER_MAG));
                bean.timeBetweenShots = cursor.getString(cursor.getColumnIndex(GunsTable.TIME_BETWEEN_SHOTS));
                bean.firingModes = cursor.getString(cursor.getColumnIndex(GunsTable.FIRING_MODES));
                bean.shotCount = cursor.getString(cursor.getColumnIndex(GunsTable.SHOT_COUNT));
                bean.category = cursor.getString(cursor.getColumnIndex(GunsTable.CATEGORY));
                bean.logoId = cursor.getString(cursor.getColumnIndex(GunsTable.LOGO_ID));
                bean.lanuage = cursor.getString(cursor.getColumnIndex(GunsTable.LANGUAGE));
                beans.add(bean);
            }
        }
        Log.d("wyl", "查询到的数据：" + beans.size());
        if (cursor != null) {
            cursor.close();
        }
        return beans;
    }



    /**
     * 据语言查投掷物
     * @param language
     * @return
     */
    public ArrayList<WeaponBean> queryThrowWeaponByLanguage(String language) {

        String sql="select * from throwable where language='" + language +"'";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        try {
            return parseToThrowWeaponBeans(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 据游标查询得到对应的bean
     * @param cursor
     * @return
     */
    public ArrayList<WeaponBean> parseToThrowWeaponBeans(Cursor cursor) {
        ArrayList<WeaponBean> beans = new ArrayList<WeaponBean>();
        while (cursor.moveToNext()) {
            WeaponBean bean = new WeaponBean();
            if (cursor != null && cursor.getCount() != 0) {
                bean.name = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.NAME));
                bean.category = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.CATEGORY));
                bean.logoId = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.LOGO_ID));
                bean.lanuage = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.LANGUAGE));
                bean.throwTime = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.THROW_TIME));
                bean.throwCooldownDuration = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.THROW_COOLDOWN_DURATION));
                bean.fireDelay = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.FIRE_DELAY));
                bean.timeLimit = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.TIME_LIMIT));
                bean.detonation = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.DETONATION));
                bean.explosionDelay = cursor.getString(cursor.getColumnIndex(ThrowableWeaponTable.EXPLOSION_DELAY));
                beans.add(bean);
            }
        }
        Log.d("wyl", "查询到的数据：" + beans.size());
        if (cursor != null) {
            cursor.close();
        }
        return beans;
    }


    /**
     * 据语言查投掷物
     * @param language
     * @return
     */
    public ArrayList<WeaponBean> queryMeleeByLanguage(String language) {

        String sql="select * from melee where language='" + language +"'";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        try {
            return parseToMeleeBeans(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 据游标查询得到对应的bean
     * @param cursor
     * @return
     */
    public ArrayList<WeaponBean> parseToMeleeBeans(Cursor cursor) {
        ArrayList<WeaponBean> beans = new ArrayList<WeaponBean>();
        while (cursor.moveToNext()) {
            WeaponBean bean = new WeaponBean();
            if (cursor != null && cursor.getCount() != 0) {
                bean.name = cursor.getString(cursor.getColumnIndex(MeleeWeapon.NAME));
                bean.category = cursor.getString(cursor.getColumnIndex(MeleeWeapon.CATEGORY));
                bean.logoId = cursor.getString(cursor.getColumnIndex(MeleeWeapon.LOGO_ID));
                bean.lanuage = cursor.getString(cursor.getColumnIndex(MeleeWeapon.LANGUAGE));
                bean.damage = cursor.getString(cursor.getColumnIndex(MeleeWeapon.DAMEGE));
                bean.impact = cursor.getString(cursor.getColumnIndex(MeleeWeapon.IMPACT));
                bean.hitRangeLeeway = cursor.getString(cursor.getColumnIndex(MeleeWeapon.HIT_RANGE_LEEWAY));

                beans.add(bean);
            }
        }
        Log.d("wyl", "查询到的数据：" + beans.size());
        if (cursor != null) {
            cursor.close();
        }
        return beans;
    }




    /**
     * videos表
     */
    public static class VideoTable {
        public static final String TITLE = "title";
        public static final String LINK = "url";
        public static final String LOGO_URL = "logo_id";
        public static final String CATEGORY = "category";
        public static final String LANGUAGE = "language";

    }

    public interface WallPaperTable {
        String ID = "ID";
        String PREVIEW = "wallpaper_preview";
        String DOWNLOAD = "wallpaper_download";
        String TITLE = "title";
    }


    public interface GunsTable {
       String NAME= "Name";
       String HIT_DAMAGE = "Hit_Damage";
       String INITIAL_BULLET_SPEED = "Initial_Bullet_Speed";
       String BODY_HIT_IMPACT_POWER = "Body_Hit_Impact_Power";
       String ZERO_RANGE = "Zero_Range";
       String AMMO_PER_MAG = "Ammo_Per_Mag";
       String TIME_BETWEEN_SHOTS = "Time_Between_Shots";
       String FIRING_MODES = "Firing_Modes";
       String SHOT_COUNT = "Shot_Count";
       String CATEGORY = "Category";
       String LOGO_ID = "logo_id";
       String LANGUAGE ="language";
    }

    public interface ThrowableWeaponTable {//投掷物表
        String NAME = "Name";
        String THROW_TIME = "Throw_Time";
        String THROW_COOLDOWN_DURATION = "Throw_Cooldown_Duration";
        String FIRE_DELAY = "Fire_Delay";
        String TIME_LIMIT = "Activation_Time_Limit";
        String DETONATION  = "Detonation";
        String EXPLOSION_DELAY = "Detonation";
        String CATEGORY = "Category";
        String LOGO_ID = "logo_id";
        String LANGUAGE = "logo_id";

    }

    public interface MeleeWeapon {//近战武器
        String NAME = "Name";
        String DAMEGE = "Damage";
        String IMPACT  = "Impact";
        String HIT_RANGE_LEEWAY  = "Hit_Range_Leeway";
        String CATEGORY = "Category";
        String LOGO_ID = "logo_id";
        String LANGUAGE = "language";


    }
}
