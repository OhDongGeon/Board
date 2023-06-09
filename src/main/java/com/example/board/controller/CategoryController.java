package com.example.board.controller;

import com.example.board.domain.dto.CategoryDto;
import com.example.board.domain.form.CategoryForm;
import com.example.board.security.TokenProvider;
import com.example.board.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final TokenProvider tokenProvider;
    public final String TOKEN_HEADER = "USER_TOKEN_HEADER";


    // 조회
    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> searchCategory() {

        return ResponseEntity.ok(categoryService.searchCategory());
    }


    // 저장
    @PostMapping("/category")
    public ResponseEntity<List<CategoryDto>> addCategory(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @RequestBody @Valid CategoryForm categoryForm) {

        return ResponseEntity.ok(categoryService.addCategory(
            tokenProvider.getTokenUserId(token), categoryForm));
    }


    // 수정
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<List<CategoryDto>> modifyCategory(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("categoryId") Long categoryId,
        @RequestBody @Valid CategoryForm categoryForm) {

        return ResponseEntity.ok(categoryService.modifyCategory(
            tokenProvider.getTokenUserId(token), categoryId, categoryForm));
    }


    // 삭제
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<List<CategoryDto>> deleteCategory(
        @RequestHeader(name = TOKEN_HEADER) String token,
        @PathVariable("categoryId") Long categoryId) {

        return ResponseEntity.ok(categoryService.deleteCategory(
            tokenProvider.getTokenUserId(token), categoryId));
    }
}
