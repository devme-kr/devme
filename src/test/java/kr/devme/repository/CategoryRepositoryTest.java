package kr.devme.repository;

import kr.devme.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save() {
        Category category = Category.builder()
                .name("test")
                .description("test")
                .totalDays(100)
                .build();

        categoryRepository.save(category);
    }
}