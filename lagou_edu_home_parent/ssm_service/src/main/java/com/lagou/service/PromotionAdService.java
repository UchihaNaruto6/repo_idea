package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVo;

public interface PromotionAdService {
    /*
    分页获取所有的广告列表
    */
    public PageInfo findAllAdByPage(PromotionAdVo adVo);
    /**
     * 广告状态上下线
     */
    public void updatePromotionAdStatus(int id, int status);
    /**
     * 添加广告
     */
    public void savePromotionAd(PromotionAd promotionAd);
    /**
     * 修改广告
     */
    public void updatePromotionAd(PromotionAd promotionAd);
    /*
    回显广告信息
    */
    PromotionAd findPromotionAdById(int id);
}
