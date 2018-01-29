package com.qianchang.togetherlesson;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qianchang.togetherlesson.app.AppManager;
import com.qianchang.togetherlesson.fragment.ClassesFragment;
import com.qianchang.togetherlesson.fragment.FindFragment;
import com.qianchang.togetherlesson.fragment.HomeFragment;
import com.qianchang.togetherlesson.fragment.MineFragment;
import com.qianchang.togetherlesson.fragment.NewsFragment;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    public static RadioGroup radioGroup;// 选项卡按键
    private int tab = 0;
    private FragmentManager fragmentManager;// 用于对Fragment进行管理
    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private FindFragment schoolPartTime;
    private MineFragment personFragment;
    private ClassesFragment classesFragment;
    private RadioButton radio_0, radio_1, radio_2, radio_3, radio_2_new;
    public static OnR onR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(this);
        initView();
        if (savedInstanceState != null) {
            setTabSelection(savedInstanceState.getInt("tab", 0));
        } else {
            setTabSelection(0);
        }
        onR = new OnR() {
            @Override
            public void onR() {
                tab = 1;
                setTabSelection(1);
            }
        };

    }

    @Override
    protected void onResume() {

        // TODO Auto-generated method stub
        super.onResume();
    }

    public interface OnR {
        public void onR();
    }

    @SuppressLint("NewApi")
    private void initView() {
        fragmentManager = getSupportFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(this);
        radio_0 = (RadioButton) findViewById(R.id.radio_0);
        radio_1 = (RadioButton) findViewById(R.id.radio_1);
        radio_2 = (RadioButton) findViewById(R.id.radio_2);
        radio_3 = (RadioButton) findViewById(R.id.radio_3);
        radio_2_new = (RadioButton) findViewById(R.id.radio_2_new);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
            case R.id.radio_0:
                tab = 0;
                setTabSelection(0);
                break;
            case R.id.radio_2_new:
                tab = 1;
                setTabSelection(1);
                break;
            case R.id.radio_1:
                tab = 2;
                setTabSelection(2);
                break;
            case R.id.radio_2:
                tab = 3;
                setTabSelection(3);
                break;
            case R.id.radio_3:
                tab = 4;
                setTabSelection(4);
                break;

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);

    }

    @Override
    protected void onPause() {

        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    @SuppressLint("NewApi")
    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        switch (index) {
            case 0:
                hideFragments(transaction);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.id_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;


            case 1:
                hideFragments(transaction);
                if (classesFragment == null) {
                    classesFragment = new ClassesFragment();
                    transaction.add(R.id.id_content, classesFragment);
                } else {
                    transaction.show(classesFragment);
                }
                break;

            case 2:
                hideFragments(transaction);
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.id_content, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }
                break;

            case 3:
                hideFragments(transaction);
                if (schoolPartTime == null) {
                    schoolPartTime = new FindFragment();
                    transaction.add(R.id.id_content, schoolPartTime);
                } else {
                    transaction.show(schoolPartTime);
                }
                break;
            case 4:
                hideFragments(transaction);
                if (personFragment == null) {
                    personFragment = new MineFragment();
                    transaction.add(R.id.id_content, personFragment);
                } else {
                    transaction.show(personFragment);
                }
                break;
        }
        tabButton();
        transaction.commitAllowingStateLoss();
    }

    /**
     * @Title: tabButton
     * @Description: TODO 底部按钮状态
     * @author Administrator
     * @date: 2015-7-22 下午4:26:08
     */
    private void tabButton() {
        if (tab == 0) {
            radio_0.setChecked(true);
        } else if (tab == 1) {
            radio_2_new.setChecked(true);

        } else if (tab == 2) {
            radio_1.setChecked(true);

        } else if (tab == 3) {
            radio_2.setChecked(true);
        } else if (tab == 4) {
            radio_3.setChecked(true);
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }

        if (classesFragment != null) {
            transaction.hide(classesFragment);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (schoolPartTime != null) {
            transaction.hide(schoolPartTime);
        }
        if (personFragment != null) {
            transaction.hide(personFragment);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        // TODO Auto-generated method stub
        super.onAttachFragment(fragment);

        if (homeFragment == null && fragment instanceof HomeFragment) {
            homeFragment = (HomeFragment) fragment;
        } else if (classesFragment == null && fragment instanceof ClassesFragment) {
            classesFragment = (ClassesFragment) fragment;
        } else if (newsFragment == null && fragment instanceof NewsFragment) {
            newsFragment = (NewsFragment) fragment;
        } else if (schoolPartTime == null && fragment instanceof FindFragment) {
            schoolPartTime = (FindFragment) fragment;
        } else if (personFragment == null && fragment instanceof MineFragment) {
            personFragment = (MineFragment) fragment;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", tab);
    }


    @Override
    public void onBackPressed() {

        // TODO Auto-generated method stub
        AppManager.getAppManager().exitBy2Click(MainActivity.this);
    }

}
