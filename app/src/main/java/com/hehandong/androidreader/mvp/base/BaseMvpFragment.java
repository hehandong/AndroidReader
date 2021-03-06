package com.hehandong.androidreader.mvp.base;


public abstract class BaseMvpFragment<T extends BasePresenter>  extends BaseFragment implements BaseView{

    protected T mPresenter;

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }
}
