package cn.th.seckill.entity.vo;

import cn.th.seckill.entity.Goods;
import cn.th.seckill.entity.OrderInfo;
import cn.th.seckill.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class OrdersVO {

    private Long orderId;
    private String goodsName;
    private String goodsImg;
    private Long goodsId;
    private Double goodsPrice;
    private Integer status;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDate;
    private String address;
    private String nickName;
    private Integer goodsCount;//订单购买量

    public OrdersVO() {
    }
    public OrdersVO(Goods good){
        this.goodsId=good.getId();
        this.goodsName = good.getGoodsName();
        this.goodsImg = good.getGoodsImg();
        this.goodsPrice = good.getGoodsPrice();
    }
    public OrdersVO(OrderInfo info){
        this.goodsId=info.getGoodsId();
        this.goodsCount=info.getGoodsCount();
        this.goodsName=info.getGoodsName();
        this.goodsPrice=info.getGoodsPrice();
        this.orderId=info.getId();
        this.status = info.getStatus();
        this.createDate = info.getCreateDate();
        this.address = info.getAddress();
    }
    public OrdersVO(User user){
        this.nickName = user.getNickname();
    }
    public OrdersVO(Goods good, OrderInfo info, User user) {
        this.goodsId=good.getId();
        this.nickName = user.getNickname();
        this.status = info.getStatus();
        this.createDate = info.getCreateDate();
        this.address = info.getAddress();
        this.goodsName = good.getGoodsName();
        this.goodsImg = good.getGoodsImg();
        this.goodsPrice = good.getGoodsPrice();
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "OrdersVO{" +
                "orderId='" + orderId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", status=" + status +
                ", createDate=" + createDate +
                ", address='" + address + '\'' +
                ", nickName='" + nickName + '\'' +
                ", goodsCount=" + goodsCount +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
