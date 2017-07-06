package com.hnsi.zheng.hnti_erp_app.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.fragments.ApplyFragment2;
import com.hnsi.zheng.hnti_erp_app.fragments.ContactsFragment;
import com.hnsi.zheng.hnti_erp_app.fragments.MessageFragment;
import com.hnsi.zheng.hnti_erp_app.fragments.MineFragment;
import com.hnsi.zheng.hnti_erp_app.newcontacts.ContactsFragment2;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;

/**
 * 应用主界面，使用FragmentActivity
 */
public class HomeActivity extends MyBaseActivity{
    //用于显示界面内容的ViewPager
    Fragment mContentVp;
    //底部Tab导航条RadioGroup
    RadioGroup mRadioGroup;
    //碎片页面的按钮（信息）
    RadioButton mMsgRbtn;
    //碎片页面的按钮（通讯录）
    RadioButton mContactsRbtn;
    //碎片页面的按钮（应用）
    RadioButton mApplyRbtn;
    //碎片页面的按钮（我的）
    RadioButton mMineRbtn;
    //Fragment集合
//    ArrayList<Fragment> mFragments;

    private MessageFragment messageFragment;
    private ContactsFragment2 contactsFragment;
    private ApplyFragment2 applyFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initButtomTab();

        //刚进入主界面默认选中碎片界面（信息）
        mMsgRbtn.setChecked(true);

    }

    /**
     * 加载底部Tab导航条
     */
    private void initButtomTab() {
        mRadioGroup= (RadioGroup) findViewById(R.id.home_tab_rg);
        //为Tab导航条设置点击监听
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

                switch (checkedId){//按下Tab导航条中的哪个按钮，就把ViewPager滚动到对应的Fragment页面
                    case R.id.home_tab_rbtn_msg:{
//                        mContentVp.setCurrentItem(AppConstants.FRAGMENT_MSG_INDEX);
                        if(messageFragment==null){
                            messageFragment=new MessageFragment();
                        }
                        transaction.replace(R.id.home_vp_content,messageFragment);

                    }break;
                    case R.id.home_tab_rbtn_contacts:{
//                        mContentVp.setCurrentItem(AppConstants.FRAGMENT_CONTACTS_INDEX);
                        if(contactsFragment==null){
                            contactsFragment=new ContactsFragment2();
                        }
                        transaction.replace(R.id.home_vp_content,contactsFragment);
                    }break;
                    case R.id.home_tab_rbtn_apply:{
//                        mContentVp.setCurrentItem(AppConstants.FRAGMENT_APPLY_INDEX);
                        if(applyFragment==null){
                            applyFragment=new ApplyFragment2();
                        }
                        transaction.replace(R.id.home_vp_content,applyFragment);
                    }break;
                    case R.id.home_tab_rbtn_mine:{
//                        mContentVp.setCurrentItem(AppConstants.FRAGMENT_MINE_INDEX);
                        if(mineFragment==null){
                            mineFragment=new MineFragment();
                        }
                        transaction.replace(R.id.home_vp_content,mineFragment);
                    }break;
                }
                transaction.commit();
            }
        });
        mMsgRbtn= (RadioButton) findViewById(R.id.home_tab_rbtn_msg);
//        mContactsRbtn= (RadioButton) findViewById(R.id.home_tab_rbtn_contacts);
//        mApplyRbtn= (RadioButton) findViewById(R.id.home_tab_rbtn_apply);
//        mMineRbtn= (RadioButton) findViewById(R.id.home_tab_rbtn_mine);
    }

    @Override
    public void onBackPressed() {
        Tools.showExitDialog(this);
    }


}
