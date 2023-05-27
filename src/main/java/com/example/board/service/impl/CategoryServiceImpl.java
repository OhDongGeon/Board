package com.example.board.service.impl;

import static com.example.board.exception.ErrorCode.NOT_FIND_CATEGORY;
import static com.example.board.exception.ErrorCode.UNREGISTERED_RANK;

import com.example.board.domain.dto.CategoryDto.SearchCategory;
import com.example.board.domain.entity.Category;
import com.example.board.domain.entity.User;
import com.example.board.domain.form.CategoryForm.MergeCategory;
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


    // 조회
    public List<SearchCategory> searchCategory() {

        List<SearchCategory> categories = new ArrayList<>();

        for (Category category : categoryRepository.findAllByOrderByCategoryRank()) {
            SearchCategory searchCategory = SearchCategory.builder()
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
    public List<SearchCategory> addCategory(Long userId, MergeCategory mergeCategory) {

        if (!rankUpStandardRepository.existsByRankName(mergeCategory.getCategoryRank())) {
            throw new GlobalException(UNREGISTERED_RANK);
        }

        User user = userTypeCheckService.userTypeAdmin(userId);

        Category category = Category.builder()
            .categoryTitle(mergeCategory.getCategoryTitle())
            .categoryRank(mergeCategory.getCategoryRank())
            .categoryUesFlag(mergeCategory.isCategoryUesFlag())
            .build();

        user.getCategories().add(category);

        return searchCategory();
    }


    // 수정
    @Transactional
    public List<SearchCategory> modifyCategory(
        Long userId, Long categoryId, MergeCategory mergeCategory) {

        if (!rankUpStandardRepository.existsByRankName(mergeCategory.getCategoryRank())) {
            throw new GlobalException(UNREGISTERED_RANK);
        }

        // 삭제 전 게시글이 있는지 확인 추후 추가

        User user = userTypeCheckService.userTypeAdmin(userId);

        Category category = categoryRepository.findByCategoryId(categoryId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_CATEGORY));

        category.setCategoryTitle(mergeCategory.getCategoryTitle());
        category.setCategoryRank(mergeCategory.getCategoryRank());
        category.setCategoryUesFlag(mergeCategory.isCategoryUesFlag());
        user.getCategories().add(category);

        return searchCategory();
    }


    // 삭제
    @Transactional
    public List<SearchCategory> deleteCategory(Long userId, Long standardId) {

        userTypeCheckService.userTypeAdmin(userId);

        Category category = categoryRepository.findByCategoryId(standardId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_CATEGORY));

        // 삭제 전 게시글이 있는지 확인 추후 추가

        categoryRepository.delete(category);

        return searchCategory();
    }
}
