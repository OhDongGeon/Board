package com.example.board.service;

import com.example.board.domain.dto.CategoryDto;
import com.example.board.domain.form.CategoryForm;
import java.util.List;


public interface CategoryService {

    // 조회
    List<CategoryDto> searchCategory();


    // 저장
    List<CategoryDto> addCategory(Long userId, CategoryForm categoryForm);


    // 수정
    List<CategoryDto> modifyCategory(
        Long userId, Long categoryId, CategoryForm categoryForm);


    // 삭제
    List<CategoryDto> deleteCategory(Long userId, Long categoryId);
}
