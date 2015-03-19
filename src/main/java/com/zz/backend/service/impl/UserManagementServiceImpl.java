package com.zz.backend.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz.backend.dao.RoleRepository;
import com.zz.backend.dao.UserRepository;
import com.zz.backend.domain.Role;
import com.zz.backend.domain.User;
import com.zz.backend.service.UserManagementService;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	private UserRepository urepo;
	private RoleRepository rrepo;

	@Autowired
	public UserManagementServiceImpl(UserRepository urepo, RoleRepository rrepo) {
		this.urepo = urepo;
		this.rrepo = rrepo;
	}

	@Override
	public long addUser(String name, String state) {
		User user = new User(name);
		urepo.save(user);
		
		return user.getId();
	}

	@Override
	@Transactional
	public boolean addRoleToUser(long uid, long[] rids) {
		try {
			List<Long> it_rid = new ArrayList<Long>();
			for (int i = 0; i < rids.length; i++) {
				it_rid.add(rids[i]);
			}
			Iterable<Role> it_roles = rrepo.findAll(it_rid);

			User user = urepo.findOne(uid);

			Set<Role> roles = new HashSet<Role>(rids.length);

			Iterator<Role> it = it_roles.iterator();
			while (it.hasNext()) {
				Role role = it.next();
				roles.add(role);
			}
			user.setRoles(roles);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public long addRole(String name) {
		Role role = new Role(name);
		rrepo.save(role);
		return role.getId();
	}

	@Override
	public User getUser(long uid) {
		return urepo.findOne(uid);
	}

}
