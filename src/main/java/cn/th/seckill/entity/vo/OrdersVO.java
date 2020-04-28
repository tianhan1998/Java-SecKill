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
    private String info;//订单的组合地址
    private String address;//用户的收货地址
    private String nickName;
    private Integer goodsCount;//订单购买量
    private String trueName;
    private String phone;

    @Override
    public String toString() {
        return "OrdersVO{" +
                "orderId=" + orderId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsId=" + goodsId +
                ", goodsPrice=" + goodsPrice +
                ", status=" + status +
                ", createDate=" + createDate +
                ", info='" + info + '\'' +
                ", address='" + address + '\'' +
                ", nickName='" + nickName + '\'' +
                ", goodsCount=" + goodsCount +
                ", trueName='" + trueName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OrdersVO() {
    }
    public OrdersVO(User user){
        this.trueName=user.getTrueName();
        this.info =user.getAddress();
        this.phone=user.getPhone();
        this.nickName = user.getNickname();
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
        this.info = info.getInfo();
    }
    public OrdersVO(Goods good, OrderInfo info, User user) {
        this.orderId=info.getId();
        this.goodsId=good.getId();
        this.trueName=user.getTrueName();
        this.nickName = user.getNickname();
        this.status = info.getStatus();
        this.createDate = info.getCreateDate();
        this.info = info.getInfo();
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
