package com.lagou.dao;

import com.lagou.domain.PromotionAd;

import java.util.List;

public interface PromotionAdMapper {
    /**
     * 分页查询广告列表
     */
    public List<PromotionAd> findAllPromotionAdByPage();
    /**
     * 更新广告状态
     */
    public void updatePromotionAdStatus(PromotionAd promotionAd);
    /**
     * 新建广告
     */
    public void savePromotionAd(PromotionAd promotionAd);
    /**
     * 修改广告
     */
    public void updatePromotionAd(PromotionAd promotionAd);
    /**
     * 根据id查询广告信息
     * */
    public PromotionAd findPromotionAdById(int id);
}
