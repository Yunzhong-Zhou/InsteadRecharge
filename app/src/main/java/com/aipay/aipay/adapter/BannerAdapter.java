package com.aipay.aipay.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aipay.aipay.R;

/**
 * Created by zyz on 2019/7/31.
 */
public class BannerAdapter extends PagerAdapter implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Context mContext;
    private ItemClickListener itemClickListener;
    private int currentPosition = 0;


    public BannerAdapter(Context context, ViewPager viewPager) {
        mContext = context;
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setOnClickListener(this);

        /*//中间
        if (currentPosition == position){
            imageView.setImageResource(R.color.blue);
        }else {
            imageView.setImageResource(R.color.red);
        }*/
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }

        container.addView(view);
        return view;
    }

  /*  @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }*/

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onClick(View v) {
        if (null != itemClickListener) {
            itemClickListener.onItemClick(currentPosition);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int p) {
        currentPosition = p;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface ItemClickListener {
        void onItemClick(int index);
    }
}
