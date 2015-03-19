package com.zz.backend.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zz.backend.domain.City;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {

}
