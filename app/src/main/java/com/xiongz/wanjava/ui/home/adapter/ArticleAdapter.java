package com.xiongz.wanjava.ui.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.xiongz.android.core.ui.recycler.ViewHolder;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.ui.home.entity.ArticleEntity;

import java.util.List;

/**
 * 文章适配器
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class ArticleAdapter extends BaseMultiItemQuickAdapter<ArticleEntity, ViewHolder> {

    private final Context mContext;
    private boolean mShowTag; //是否展示标签 tag 一般主页才用的到

    public ArticleAdapter(Context context, boolean showTag, List<ArticleEntity> datas) {
        super(datas);
        addItemType(ArticleEntity.ARTICLE, R.layout.item_ariticle);
        addItemType(ArticleEntity.PROJECT, R.layout.item_project);
        mContext = context;
        mShowTag = showTag;
    }

    @Override
    protected void convert(ViewHolder helper, ArticleEntity item) {
        switch (helper.getItemViewType()) {
            case ArticleEntity.ARTICLE:
                setArticleView(helper, item);
                break;
            case ArticleEntity.PROJECT:
                setProjectView(helper, item);
                break;
        }
    }

    /**
     * 设置文章布局
     */
    private void setArticleView(ViewHolder helper, ArticleEntity item) {
        helper.setText(R.id.item_home_date, item.getNiceDate());
        helper.setText(R.id.item_home_content, item.getTitle());
        helper.setText(R.id.item_home_type2, item.getSuperChapterName() + "·" + item.getChapterName());
        if (TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.item_home_author, item.getShareUser());
        } else {
            helper.setText(R.id.item_home_author, item.getAuthor());
        }
        if (mShowTag) {
            // 展示标签
            helper.setGone(R.id.item_home_new, item.isFresh());
            helper.setGone(R.id.item_home_top, item.getType() == 1);
            if (item.getTags() != null && item.getTags().size() > 0) {
                helper.setGone(R.id.item_home_type1, true);
                helper.setText(R.id.item_home_type1, item.getTags().get(0).getName());
            } else {
                helper.setGone(R.id.item_home_type1, false);
            }
        } else {
            // 隐藏所有标签
            helper.setGone(R.id.item_home_top, false);
            helper.setGone(R.id.item_home_type1, false);
            helper.setGone(R.id.item_home_new, false);
        }
    }

    /**
     * 设置项目布局
     */
    private void setProjectView(ViewHolder helper, ArticleEntity item) {
        helper.setText(R.id.item_project_date, item.getNiceDate());
        helper.setText(R.id.item_project_title, item.getTitle());
        helper.setText(R.id.item_project_content, item.getDesc());
        helper.setText(R.id.item_project_type, item.getSuperChapterName() + "·" + item.getChapterName());
        if (TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.item_project_author, item.getShareUser());
        } else {
            helper.setText(R.id.item_project_author, item.getAuthor());
        }
        ImageView imageView = helper.getView(R.id.item_project_imageview);
        if (TextUtils.isEmpty(item.getEnvelopePic())) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getEnvelopePic())
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(imageView);
        }
        if (mShowTag) {
            // 展示标签
            helper.setGone(R.id.item_project_new, item.isFresh());
            helper.setGone(R.id.item_project_top, item.getType() == 1);
            if (item.getTags() != null && item.getTags().size() > 0) {
                helper.setGone(R.id.item_project_type1, true);
                helper.setText(R.id.item_project_type1, item.getTags().get(0).getName());
            } else {
                helper.setGone(R.id.item_project_type1, false);
            }
        } else {
            // 隐藏所有标签
            helper.setGone(R.id.item_project_top, false);
            helper.setGone(R.id.item_project_type1, false);
            helper.setGone(R.id.item_project_new, false);
        }
    }
}
