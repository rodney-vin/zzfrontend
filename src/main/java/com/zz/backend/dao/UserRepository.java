package com.zz.backend.dao;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.zz.backend.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
