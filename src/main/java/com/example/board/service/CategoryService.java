package com.example.board.service;

import com.example.board.domain.dto.CategoryDto.SearchCategory;
import com.example.board.domain.form.CategoryForm.MergeCategory;
import java.util.List;


public interface CategoryService {

    // 조회
    List<SearchCategory> searchCategory();


    // 저장
    List<SearchCategory> addCategory(Long userId, MergeCategory mergeCategory);


    // 수정
    List<SearchCategory> modifyCategory(
        Long userId, Long categoryId, MergeCategory mergeCategory);


    // 삭제
    List<SearchCategory> deleteCategory(Long userId, Long categoryId);

}
