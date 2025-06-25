package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BookSearchResultDto;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

@Service
@Transactional(readOnly = true) // 読み取り専用のトランザクション。検索処理なのでこれで良い。
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * 書籍名であいまい検索を行い、結果をDTOのリストで返す
     * @param keyword 検索キーワード
     * @return 検索結果DTOのリスト
     */
    public List<BookSearchResultDto> searchByBookName(String keyword) {
        // Repositoryを呼び出してEntityのリストを取得
        List<Book> bookList = bookRepository.findByBookNameContaining(keyword);

        // EntityのリストをDTOのリストに変換して返す
        return bookList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * EntityからDTOへの変換メソッド
     * @param book Bookエンティティ
     * @return BookSearchResultDto
     */
    private BookSearchResultDto convertToDto(Book book) {
        BookSearchResultDto dto = new BookSearchResultDto();
        dto.setIsbn(book.getIsbn());
        dto.setBookName(book.getBookName());
        dto.setPublishCompany(book.getPublishCompany());

        // 関連付けられたAuthorエンティティから著者名を取得する
        if (book.getAuthor() != null) {
            dto.setAuthorName(book.getAuthor().getAuthorName());
        } else {
            dto.setAuthorName("著者情報なし");
        }

        return dto;
    }
}