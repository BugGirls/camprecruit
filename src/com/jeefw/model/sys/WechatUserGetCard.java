package com.jeefw.model.sys;

import com.jeefw.model.sys.param.WechatUserGetCardParameter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户领取卡券信息
 *
 * @author Hystar
 * @date 2018/7/23
 */
@Entity
@Table(name = "wechat_user_get_card")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class WechatUserGetCard extends WechatUserGetCardParameter {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    /**
     * 用户领取的卡券状态
     */
    @Column(name = "card_status", nullable = false, length = 32)
    private String cardStatus;

    /**
     * 领券方openid
     */
    @Column(name = "openid", length = 64, nullable = false)
    private String openid;

    /**
     * 卡券ID
     */
    @Column(name = "card_id", length = 128, nullable = false)
    private String cardId;

    /**
     * 是否为转赠领取，1-是 0-否
     */
    @Column(name = "give_by_friend", nullable = false)
    private Integer giveByFriend;

    /**
     * 当IsGiveByFriend为1时填入的字段，表示发起转赠用户的openid
     */
    @Column(name = "friend_user_name", length = 64)
    private String friendUserName;

    /**
     * code序列号
     */
    @Column(name = "user_card_code", nullable = false, length = 128)
    private String userCardCode;

    /**
     * 为保证安全，微信会在转赠发生后变更该卡券的code号，该字段表示转赠前的code
     */
    @Column(name = "old_user_card_code", length = 128)
    private String oldUserCardCode;

    /**
     * 领取场景值，用于领取渠道数据统计。可在生成二维码接口及添加Addcard接口中自定义该字段的字符串值
     */
    @Column(name = "outer_str", length = 128)
    private String outerStr;

    /**
     * 用户删除会员卡后可重新找回，当用户本次操作为找回时，该值为1，否则为0
     */
    @Column(name = "restore_member_card", nullable = false)
    private Integer restoreMemberCard;

    /**
     * 	领券用户的UnionId
     */
    @Column(name = "union_id", length = 128, nullable = false)
    private String unionId;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    public WechatUserGetCard() {
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getGiveByFriend() {
        return giveByFriend;
    }

    public void setGiveByFriend(Integer giveByFriend) {
        this.giveByFriend = giveByFriend;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }

    public String getUserCardCode() {
        return userCardCode;
    }

    public void setUserCardCode(String userCardCode) {
        this.userCardCode = userCardCode;
    }

    public String getOldUserCardCode() {
        return oldUserCardCode;
    }

    public void setOldUserCardCode(String oldUserCardCode) {
        this.oldUserCardCode = oldUserCardCode;
    }

    public String getOuterStr() {
        return outerStr;
    }

    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr;
    }

    public Integer getRestoreMemberCard() {
        return restoreMemberCard;
    }

    public void setRestoreMemberCard(Integer restoreMemberCard) {
        this.restoreMemberCard = restoreMemberCard;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
