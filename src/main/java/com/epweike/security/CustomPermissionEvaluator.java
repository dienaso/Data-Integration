/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Nov 23, 2015 11:33:49 AM 
 * 
 */
package com.epweike.security;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author wuxp
 *
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {
	 
	   public boolean hasPermission(Authentication authentication,
	         Object targetDomainObject, Object permission) {
	      if ("user".equals(targetDomainObject)) {
	         return this.hasPermission(authentication, permission);
	      }
	      return false;
	   }
	 
	   /**
	    * 总是认为有权限
	    */
	   public boolean hasPermission(Authentication authentication,
	         Serializable targetId, String targetType, Object permission) {
	      return true;
	   }
	  
	   /**
	    * 简单的字符串比较，相同则认为有权限
	    */
	   private boolean hasPermission(Authentication authentication, Object permission) {
	      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	      for (GrantedAuthority authority : authorities) {
	         if (authority.getAuthority().equals(permission)) {
	            return true;
	         }
	      }
	      return false;
	   }
	 
	}
