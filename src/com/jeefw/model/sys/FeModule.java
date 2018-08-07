package com.jeefw.model.sys;

import com.jeefw.model.sys.param.FeModuleParameter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @author Empress
 * @data 2018/7/6
 */
@Entity
@Table(name = "fe_module")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class FeModule extends FeModuleParameter {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "alliance_id")
    private Integer allianceId;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "image_url", length = 128, nullable = false)
    private String imageUrl;

    @Column(name = "access_url", length = 128, nullable = false)
    private String accessUrl;

    @Column(name = "seq")
    private Integer seq;

    public FeModule() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

}
