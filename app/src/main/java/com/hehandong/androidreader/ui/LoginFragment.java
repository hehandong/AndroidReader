package com.hehandong.androidreader.ui;


import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.mvp.base.BaseMvpFragment;
import com.hehandong.androidreader.mvp.bean.BaseObjectBean;
import com.hehandong.androidreader.mvp.bean.LoginBean;
import com.hehandong.androidreader.mvp.contract.LoginContract;
import com.hehandong.androidreader.mvp.presenter.LoginPresenter;
import com.hehandong.androidreader.mvp.util.ProgressDialog;
import com.hehandong.androidreader.utils.LogUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginFragment extends BaseMvpFragment<LoginPresenter> implements LoginContract.View {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    @BindView(R.id.et_username_login)
    TextInputEditText etUsernameLogin;
    @BindView(R.id.et_password_login)
    TextInputEditText etPasswordLogin;

    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    protected void initView(View view) {

        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onSuccess(BaseObjectBean<LoginBean> bean) {
        LogUtil.i("onSuccess---");
        Toast.makeText(getActivity(), bean.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        ProgressDialog.getInstance().show(getActivity());
    }

    @Override
    public void hideLoading() {
        ProgressDialog.getInstance().dismiss();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }

    /**
     * @return 帐号
     */
    private String getUsername() {
        return etUsernameLogin.getText().toString().trim();
    }

    /**
     * @return 密码
     */
    private String getPassword() {
        return etPasswordLogin.getText().toString().trim();
    }

    @OnClick(R.id.btn_signin_login)
    public void onViewClicked() {
        if (getUsername().isEmpty() || getPassword().isEmpty()) {
            Toast.makeText(getActivity(), "帐号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.login(getUsername(), getPassword());
    }
}
