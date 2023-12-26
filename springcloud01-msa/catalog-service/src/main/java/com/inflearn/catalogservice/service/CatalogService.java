package com.inflearn.catalogservice.service;

import com.inflearn.catalogservice.jpa.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
