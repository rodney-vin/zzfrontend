package com.zz.backend.dao;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zz.backend.domain.DocUser;

public interface ElasticUserRepository extends ElasticsearchRepository<DocUser, Long> {

}
