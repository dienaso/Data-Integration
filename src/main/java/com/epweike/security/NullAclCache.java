/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Oct 13, 2015 7:30:45 PM 
 * 
 */
package com.epweike.security;

import java.io.Serializable;

import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;

/**
 * @author wuxp
 *
 */
// imports omitted
public class NullAclCache implements AclCache {
	@Override
	public void clearCache() {
	}

	@Override
	public void evictFromCache(Serializable arg0) {
	}

	@Override
	public void evictFromCache(ObjectIdentity arg0) {
	}

	@Override
	public MutableAcl getFromCache(ObjectIdentity arg0) {
		return null;
	}

	@Override
	public MutableAcl getFromCache(Serializable arg0) {
		return null;
	}

	@Override
	public void putInCache(MutableAcl arg0) {
	}
}
