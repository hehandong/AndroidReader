package com.hehandong.androidreader;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;


import com.hehandong.androidreader.base.BaseMvpActivity;
import com.hehandong.androidreader.bean.BaseObjectBean;
import com.hehandong.androidreader.contract.LoginContract;
import com.hehandong.androidreader.presenter.LoginPresenter;
import com.hehandong.androidreader.util.LogUtil;
import com.hehandong.androidreader.util.ProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_username_login)
    TextInputEditText etUsernameLogin;
    @BindView(R.id.et_password_login)
    TextInputEditText etPasswordLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);

        etUsernameLogin.setText("hehandong");
        etPasswordLogin.setText("123123");

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

    @Override
    public void onSuccess(BaseObjectBean bean) {
        LogUtil.i("onSuccess---");
        Toast.makeText(this, bean.getErrorMsg(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoading() {
        LogUtil.i("showLoading---");
        ProgressDialog.getInstance().show(this);
    }

    @Override
    public void hideLoading() {
        LogUtil.i("hideLoading---");
        ProgressDialog.getInstance().dismiss();
    }

    @Override
    public void onError(Throwable throwable) {
        LogUtil.i("onError---");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signin_login)
    public void onViewClicked() {
        if (getUsername().isEmpty() || getPassword().isEmpty()) {
            Toast.makeText(this, "帐号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.login(getUsername(), getPassword());
    }
}
