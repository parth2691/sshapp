#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.dao.security.AuthorityDao;
import ${package}.dao.security.ResourceDao;
import ${package}.dao.security.RoleDao;
import ${package}.dao.security.UserDao;
import ${package}.entity.security.Authority;
import ${package}.entity.security.Resource;
import ${package}.entity.security.Role;
import ${package}.entity.security.User;
import ${package}.service.ServiceException;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class SecurityManager {

	private static Logger logger = LoggerFactory.getLogger(SecurityManager.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private ResourceDao resourceDao;

	// User Manager //
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userDao.get(id);
	}

	public void saveUser(User entity) {
		userDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	@Transactional(readOnly = true)
	public Page<User> searchUser(final Page<User> page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String loginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", loginName, oldLoginName);
	}

	// Role Manager //
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll();
	}

	public void saveRole(Role entity) {
		roleDao.save(entity);
	}

	public void deleteRole(Long id) {
		roleDao.delete(id);
	}

	// Resource Manager //
	@Transactional(readOnly = true)
	public List<Resource> getUrlResourceWithAuthorities() {
		return resourceDao.getUrlResourceWithAuthorities();
	}

	// Authority Manager //
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}
}
