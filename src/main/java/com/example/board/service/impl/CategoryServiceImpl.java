package com.example.board.service.impl;

import static com.example.board.exception.ErrorCode.NOT_FIND_CATEGORY;
import static com.example.board.exception.ErrorCode.REGISTERED_CATEGORY_BOARD;
import static com.example.board.exception.ErrorCode.UNREGISTERED_RANK;

import com.example.board.domain.dto.CategoryDto;
import com.example.board.domain.entity.Category;
import com.example.board.domain.entity.User;
import com.example.board.domain.form.CategoryForm;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.domain.repository.CategoryRepository;
import com.example.board.domain.repository.RankUpStandardRepository;
import com.example.board.exception.GlobalException;
import com.example.board.service.CategoryService;
import com.example.board.service.UserTypeCheckService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final UserTypeCheckService userTypeCheckService;
    private final RankUpStandardRepository rankUpStandardRepository;
    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;


    // 조회
    public List<CategoryDto> searchCategory() {

        List<CategoryDto> categories = new ArrayList<>();

        for (Category category : categoryRepository.findAllByCategoryUesFlagOrderByCategoryRank(
            true)) {
            CategoryDto searchCategory = CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle())
                .categoryRank(category.getCategoryRank())
                .categoryUesFlag(category.isCategoryUesFlag())
                .build();

            categories.add(searchCategory);
        }

        return categories;
    }


    // 저장
    @Transactional
    public List<CategoryDto> addCategory(Long userId, CategoryForm categoryForm) {

        if (!rankUpStandardRepository.existsByRankName(categoryForm.getCategoryRank())) {
            throw new GlobalException(UNREGISTERED_RANK);
        }

        User user = userTypeCheckService.userTypeAdmin(userId);

        Category category = Category.builder()
            .categoryTitle(categoryForm.getCategoryTitle())
            .categoryRank(categoryForm.getCategoryRank())
            .categoryUesFlag(categoryForm.isCategoryUesFlag())
            .build();

        user.getCategories().add(category);

        return searchCategory();
    }


    // 수정
    @Transactional
    public List<CategoryDto> modifyCategory(
        Long userId, Long categoryId, CategoryForm categoryForm) {

        if (!rankUpStandardRepository.existsByRankName(categoryForm.getCategoryRank())) {
            throw new GlobalException(UNREGISTERED_RANK);
        }
        if (boardRepository.existsByCategoryCategoryId(categoryId)) {
            throw new GlobalException(REGISTERED_CATEGORY_BOARD);
        }

        User user = userTypeCheckService.userTypeAdmin(userId);

        Category category = categoryRepository.findByCategoryId(categoryId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_CATEGORY));

        category.setCategoryTitle(categoryForm.getCategoryTitle());
        category.setCategoryRank(categoryForm.getCategoryRank());
        category.setCategoryUesFlag(categoryForm.isCategoryUesFlag());
        user.getCategories().add(category);

        return searchCategory();
    }


    // 삭제
    @Transactional
    public List<CategoryDto> deleteCategory(Long userId, Long standardId) {

        userTypeCheckService.userTypeAdmin(userId);

        Category category = categoryRepository.findByCategoryId(standardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_CATEGORY));

        if (boardRepository.existsByCategoryCategoryId(category.getCategoryId())) {
            throw new GlobalException(REGISTERED_CATEGORY_BOARD);
        }

        categoryRepository.delete(category);

        return searchCategory();
    }
}
