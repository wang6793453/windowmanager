package com.fanwe.library.windowmanager;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/18.
 */
class SDViewHelper
{
    private WeakReference<ViewGroup> mParent;
    private ViewGroup.LayoutParams mParams;
    private int mIndex = -1;

    /**
     * 保存view的信息
     *
     * @param view
     */
    public void save(View view)
    {
        mParent = null;
        mParams = null;
        mIndex = -1;

        if (view != null)
        {
            mParams = view.getLayoutParams();

            final ViewParent viewParent = view.getParent();
            if (viewParent instanceof ViewGroup)
            {
                final ViewGroup viewGroup = (ViewGroup) viewParent;
                setParent(viewGroup);
                mIndex = viewGroup.indexOfChild(view);
            }
        }
    }

    /**
     * 是否可以还原view
     *
     * @param view
     * @return
     */
    public boolean canRestore(View view)
    {
        if (view == null)
        {
            return false;
        }
        final ViewGroup parent = getParent();
        if (parent == null)
        {
            return false;
        }
        if (view.getParent() == parent)
        {
            return false;
        }
        return true;
    }

    /**
     * 把view还原到原来的parent
     *
     * @param view
     * @return
     */
    public boolean restore(View view)
    {
        if (!canRestore(view))
        {
            return false;
        }

        try
        {
            removeViewFromParent(view);
            getParent().addView(view, mIndex, mParams);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 把View从它的Parent上移除
     *
     * @param view
     */
    public static void removeViewFromParent(View view)
    {
        if (view == null)
        {
            return;
        }
        final ViewParent viewParent = view.getParent();
        if (!(viewParent instanceof ViewGroup))
        {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) viewParent;
        viewGroup.removeView(view);
    }

    private void setParent(ViewGroup viewGroup)
    {
        if (viewGroup != null)
        {
            mParent = new WeakReference<>(viewGroup);
        } else
        {
            mParent = null;
        }
    }

    /**
     * 返回保存的Parent
     *
     * @return
     */
    public ViewGroup getParent()
    {
        if (mParent != null)
        {
            return mParent.get();
        }
        return null;
    }

    /**
     * 返回保存的LayoutParams
     *
     * @return
     */
    public ViewGroup.LayoutParams getParams()
    {
        return mParams;
    }

    /**
     * 返回保存的index
     *
     * @return
     */
    public int getIndex()
    {
        return mIndex;
    }
}
