package com.uaijia.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.uaijia.entity.User;
import com.uaijia.entity.enums.LoginStatusEnum;
import com.uaijia.user.service.UserService;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-5
 */
@Service
public class UserAuthorizingRealm extends AuthorizingRealm {

//	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private UserService userService;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		// 校验用户名密码
		User user = userService.findUserByUsername(token.getUsername());
		if (user != null) {
			//账户不可用
			if (user.getState()==LoginStatusEnum.LOCK.getValue()){
				throw new LockedAccountException();
			}
			
			return new SimpleAuthenticationInfo(new Principal(user,false), 
					user.getPassword(),ByteSource.Util.bytes(user.getUsername()), getName());
		}

		return null;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		User user = userService.findUserByUsername(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
//			for (Role role : user.getRoleList()){
//				info.addRole(role.getEnname());
//			}
			// 更新登录IP和时间
//			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
//			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		}
	
		return null;
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private Long id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录
		
		public Principal(User user, boolean mobileLogin) {
			this.id = user.getId();
			this.loginName = user.getUsername();
			this.name = user.getRealname();
			this.mobileLogin = mobileLogin;
		}

		public Long getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}
		
		@Override
		public String toString() {
			return loginName;
		}

	}
	
	
	public static void main(String[] args) {
		System.err.println(new Md5Hash("haha3123456"));
	}
}
