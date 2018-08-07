package com.jeefw.model.sys;

import com.jeefw.model.sys.param.WechatMaterialImageParameter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * 微信素材图片
 *
 * @author Empress
 * @data 2018/7/8
 */
@Entity
@Table(name = "wechat_material_image")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class WechatMaterialImage extends WechatMaterialImageParameter {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "alliance_id")
    private Integer allianceId;

    /**
     * 素材使用场景
     * 对应的有枚举
     */
    @Column(name = "use_scene", nullable = false)
    private Integer useScene;

    @Column(name = "media_id", length = 256)
    private String mediaId;

    @Column(name = "wechat_img_url", nullable = false, length = 256)
    private String wechatImgUrl;

    @Column(name = "title", nullable = false, length = 64)
    private String title;

    @Column(name = "local_img_url", nullable = false, length = 128)
    private String localImgUrl;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public WechatMaterialImage() {
    }

    public Integer getUseScene() {
        return useScene;
    }

    public void setUseScene(Integer useScene) {
        this.useScene = useScene;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(Integer allianceId) {
        this.allianceId = allianceId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWechatImgUrl() {
        return wechatImgUrl;
    }

    public void setWechatImgUrl(String wechatImgUrl) {
        this.wechatImgUrl = wechatImgUrl;
    }

    public String getLocalImgUrl() {
        return localImgUrl;
    }

    public void setLocalImgUrl(String localImgUrl) {
        this.localImgUrl = localImgUrl;
    }
}
