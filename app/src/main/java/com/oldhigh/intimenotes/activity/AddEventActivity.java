package com.oldhigh.intimenotes.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.oldhigh.intimenotes.R;
import com.oldhigh.intimenotes.bean.NewEventBean;
import com.oldhigh.intimenotes.util.RealmUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AddEventActivity extends BaseActivity {


    private Unbinder mBind;

    @BindView(R.id.edit_event)
    EditText edit_event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        mBind = ButterKnife.bind(this);

    }


    @OnClick(R.id.btn_complete)
    public void completeBtn(){

        String event = edit_event.getText().toString().trim();
        if (TextUtils.isEmpty(event)){
            Toast.makeText(mContext, "输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        RealmUtil.add(new NewEventBean(System.currentTimeMillis() ,event ));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}

