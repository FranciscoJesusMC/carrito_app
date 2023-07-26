package com.carrito.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.carrito.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRespository;
	
	private Category category;
	
	
	@BeforeEach
	void init() {
		
		category = new Category();
		category.setName("Man");
	}
	
	@Test
	void saveCategory() {
		
		Category saveCategory = categoryRespository.save(category);
		
		assertNotNull(saveCategory);
		assertThat(saveCategory.getId()).isNotEqualTo(0);
	}
}
