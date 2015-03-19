package com.zz.backend.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zz.backend.domain.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long>{}
