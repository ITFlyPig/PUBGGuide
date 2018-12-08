package com.igameguide.pubg.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.igameguide.pubg.R;
import com.igameguide.pubg.detail.DetailActivity;
import com.igameguide.pubg.util.ConstantValue;
import com.igameguide.pubg.util.ToastUtil;


@SuppressLint("ValidFragment")
public class StandingsFragment extends Fragment implements View.OnClickListener {
    TextView tvQuery;
    EditText etInput;


    public static StandingsFragment getInstance() {
        StandingsFragment sf = new StandingsFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_standings, null);
        tvQuery = v.findViewById(R.id.tv_query);
        tvQuery.setOnClickListener(this);
        etInput = v.findViewById(R.id.et_input_role);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query:
                String playerName = etInput.getText().toString();
                if (TextUtils.isEmpty(playerName)) {
                    ToastUtil.showToas("角色名称不能为空");
                    return;
                }

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(ConstantValue.IntentKey.PLAYER_NAME, playerName);
                startActivity(intent);
                break;
        }
    }
}