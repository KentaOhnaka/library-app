package com.example.demo.dto;

import lombok.Data;

/**
 * 書籍検索結果を画面に渡すためのDTO
 */
@Data
public class BookSearchResultDto {

    private String isbn;
    private String bookName;
    private String authorName; // 著者名も表示したいので追加
    private String publishDate;
    private String publishCompany;
    private String authorCode;

}