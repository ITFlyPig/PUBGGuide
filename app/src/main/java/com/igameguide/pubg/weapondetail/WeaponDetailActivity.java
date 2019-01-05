package com.igameguide.pubg.weapondetail;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igameguide.pubg.R;
import com.igameguide.pubg.base.GlideApp;
import com.igameguide.pubg.util.ConstantValue;
import com.igameguide.pubg.util.systembar.SystemBarHelper;
import com.igameguide.pubg.weapon.bean.WeaponBean;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeaponDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_weapon)
    ImageView ivWeapon;
    @BindView(R.id.whole)
    LinearLayout whole;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tl_title)
    RelativeLayout tlTitle;
    private WeaponBean mWeaponBean;
    private int mWeaponType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 设置沉浸模式
         */
        SystemBarHelper.setAdaptStatusBar(getWindow());

        setContentView(R.layout.activity_weapondetail);
        ButterKnife.bind(this);
        SystemBarHelper.updateAdaptStatusBar(tlTitle);
        mWeaponBean = (WeaponBean) getIntent().getSerializableExtra("data");
        mWeaponType = getIntent().getIntExtra(ConstantValue.IntentKey.WEAPON_TYPE, 0);

        ivBack.setOnClickListener(this);

        fillData();
    }

    private void fillData() {
        if (mWeaponBean == null) {
            return;
        }
        AssetManager assetManager = getAssets();
        InputStream is = null;
        try {
            is = assetManager.open("images/" + mWeaponBean.logoId + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (is == null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        GlideApp.with(this).load(bitmap).into(ivWeapon);
        if (mWeaponType == ConstantValue.WeaponType.GUN) {
            addItem(getResources().getString(R.string.hit_damage), mWeaponBean.hitDamage);
            addItem(getResources().getString(R.string.initial_bullet_speed), mWeaponBean.initBulletSpeed);
            addItem(getResources().getString(R.string.body_hit_impact_power), mWeaponBean.bodyHitImpactPower);
            addItem(getResources().getString(R.string.zero_range), mWeaponBean.zeroRange);
            addItem(getResources().getString(R.string.ammo_per_mag), mWeaponBean.ammoPerMag);
            addItem(getResources().getString(R.string.time_between_shots), mWeaponBean.timeBetweenShots);
            addItem(getResources().getString(R.string.firing_modes), mWeaponBean.firingModes);
        } else if (mWeaponType == ConstantValue.WeaponType.MELEE_WEAPON) {
            addItem("Damage", mWeaponBean.damage);
            addItem("Impact", mWeaponBean.impact);
            addItem("Hit_Range_Leeway", mWeaponBean.hitRangeLeeway);

        } else if (mWeaponType == ConstantValue.WeaponType.THROW_WEAPON) {
            addItem("Throw_Time", mWeaponBean.throwTime);
            addItem("Throw_Cooldown_Duration", mWeaponBean.throwCooldownDuration);
            addItem("Fire_Delay", mWeaponBean.fireDelay);
            addItem("Activation_Time_Limit", mWeaponBean.timeLimit);
            addItem("Detonation", mWeaponBean.detonation);
            addItem("Explosion_Delay", mWeaponBean.explosionDelay);

        }

    }

    /**
     * 填充数据
     *
     * @param key
     * @param value
     */
    private void addItem(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        View v = LayoutInflater.from(this).inflate(R.layout.item_weapon, null);
        TextView tvName = v.findViewById(R.id.tv_name);
        TextView tvDesc = v.findViewById(R.id.tv_desc);
        tvName.setText(key);
        if (TextUtils.isEmpty(value)) {
            value = "0";
        }
        tvDesc.setText(value);

        whole.addView(v);

        View line = new View(this);
        line.setBackgroundColor(getResources().getColor(R.color.gray));
        whole.addView(line, ViewGroup.LayoutParams.MATCH_PARENT, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
