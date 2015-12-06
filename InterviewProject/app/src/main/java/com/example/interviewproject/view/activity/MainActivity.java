package com.example.interviewproject.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.interviewproject.R;
import com.example.interviewproject.databinding.MainActivityBinding;
import com.example.interviewproject.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mMainViewModel;
    private MainActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainViewModel = new MainViewModel(this, this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mBinding.setViewModel(mMainViewModel);
    }


}
