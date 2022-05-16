package com.kdt.exercise.item.controller;

import com.kdt.exercise.ApiResponse;
import com.kdt.exercise.item.dto.ItemDto;
import com.kdt.exercise.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ItemController {

    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ApiResponse<String> notFoundHandle(NoSuchElementException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    private final ItemService itemService;

    @PostMapping("/items")
    public ApiResponse<Long> save(
            @RequestBody ItemDto itemDto
    ) {
        Long id = itemService.save(itemDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/items/{id}")
    public ApiResponse<ItemDto> getItem(
            @PathVariable Long id
    ) {
        ItemDto item = itemService.getItem(id);
        return ApiResponse.ok(item);
    }
}