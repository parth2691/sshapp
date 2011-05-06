package org.ssh.app.common.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.modules.utils.reflection.ConvertUtils;
import org.ssh.app.orm.hibernate.AuditableEntity;

import com.google.common.collect.Lists;

/**
 * 用户.
 *
 */
@Entity
@Table(name = "T_USERS")
public class User extends AuditableEntity {
    private String loginName;
    private String plainPassword;
    private String shaPassword;
    private String name;
    private String email;
    private String status;
    private Integer version;

    //有序的关联对象集合
    private List<Role> roleList = Lists.newArrayList();

    //Hibernate自动维护的Version字段
    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(nullable = false, unique = true)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 演示用明文密码.
     */
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    /**
     * 演示用SHA1散列密码.
     */
    public String getShaPassword() {
        return shaPassword;
    }

    public void setShaPassword(String shaPassword) {
        this.shaPassword = shaPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //多对多定义
    @ManyToMany
    //中间表定义,表名采用默认命名规则
    @JoinTable(name = "T_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    //Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)
    //集合按id排序
    @OrderBy("id ASC")
    public List<Role> getRoleList() {
        return roleList;
    }

    //@Fetch(FetchMode.JOIN) 会使用left join查询  只产生一条sql语句
    //@Fetch(FetchMode.SELECT)   会产生N+1条sql语句
    //@Fetch(FetchMode.SUBSELECT)  产生两条sql语句 第二条语句使用id in (.....)查询出所有关联的数据

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Transient
    @JsonIgnore
    public String getRoleNames() {
        return ConvertUtils.convertElementPropertyToString(roleList, "name", ", ");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
