package com.api.restfull.ecommerce.domain.repository;

import com.api.restfull.ecommerce.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
