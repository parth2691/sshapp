package com.ekingsoft.core.orm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * 含审计信息的Entity基类.
 *
 * @author calvin
 */
@MappedSuperclass
public class AuditableEntity extends com.ekingsoft.core.orm.entity.IdEntity {

    protected String createTime;
    protected String createBy;
    protected String lastModifyTime;
    protected String lastModifyBy;

    /**
     * 创建时间.
     */
    //本属性只在save时有效,update时无效.
    @Column(updatable = false)
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建的操作员的登录名.
     */
    @Column(updatable = false)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 最后修改时间.
     */
    //本属性只在update时有效,save时无效.
    @Column(insertable = false)
    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * 最后修改的操作员的登录名.
     */
    @Column(insertable = false)
    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }
}
