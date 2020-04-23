package cn.th.seckill.entity.vo;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.SeckillGoods;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class GoodsVo {
    private Long goodsId;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer stockCount;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date endDate;
    private Double seckillPrice;

    public GoodsVo(){

    }
    public GoodsVo(Goods goods, SeckillGoods sgoods){
        this.goodsId=goods.getId();
        this.goodsName=goods.getGoodsName();
        this.goodsTitle=goods.getGoodsTitle();
        this.goodsImg=goods.getGoodsImg();
        this.goodsDetail=goods.getGoodsDetail();
        this.goodsPrice=goods.getGoodsPrice();
        this.stockCount=sgoods.getStockCount();
        this.startDate=sgoods.getStartDate();
        this.endDate=sgoods.getEndDate();
        this.seckillPrice=sgoods.getSeckillPrice();
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", stockCount=" + stockCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", seckillPrice=" + seckillPrice +
                '}';
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }
}
