package com.inflearn.catalogservice.repository;

import com.inflearn.catalogservice.jpa.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    CatalogEntity findByProductId(String productId);
}
