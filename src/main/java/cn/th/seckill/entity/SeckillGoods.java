package cn.th.seckill.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class SeckillGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date endDate;
    private int version;
    private Double seckillPrice;

    @Override
    public String toString() {
        return "SeckillGoods{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", stockCount=" + stockCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", version=" + version +
                ", seckillPrice=" + seckillPrice +
                '}';
    }

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
