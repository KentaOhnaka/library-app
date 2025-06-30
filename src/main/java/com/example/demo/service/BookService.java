package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.demo.dto.BookDetailDto;
import com.example.demo.dto.BookSearchResultDto;
import com.example.demo.entity.Book;
import com.example.demo.param.DetailSearchForm;
import com.example.demo.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 読み取り専用のトランザクション。検索処理なのでこれで良い。
public class BookService {

	private final BookRepository bookRepository;
	private static final int BOOKS_PER_PAGE = 10;

	/**
	 * 書籍名であいまい検索を行い、結果をDTOのリストで返す
	 * @param keyword 検索キーワード
	 * @return 検索結果DTOのリスト
	 */
	public Page<BookSearchResultDto> searchByBookName(String keyword, int pageNum) {

		Pageable pageable = PageRequest.of(pageNum, BOOKS_PER_PAGE);
		Page<Book> bookPage = bookRepository.findByBookNameContaining(keyword, pageable);
		//        System.out.println("DBから " + bookList.size() + " 件のデータが見つかりました！");
		//        if (!bookList.isEmpty()) {
		//            System.out.println("最初に見つかった本の名前: " + bookList.get(0).getBookName());
		//        }
		// EntityのリストをDTOのリストに変換して返す
		//通常は、for文で1つ1つadd
		//通常は、.stream().map().collect
		return bookPage.map(this::convertToDto);
	}

	public Page<BookSearchResultDto> detailSearch(DetailSearchForm form, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, BOOKS_PER_PAGE);

		// 1. まず「何もしない」という空のSpecificationを用意する。
		//    これは、全ての検索条件の土台となる。
		Specification<Book> spec = Specification.where(null);

		// 2. もし書籍名の入力があれば、specに「書籍名検索」の条件をANDで追加する
		if (StringUtils.hasText(form.getBookName())) {
			spec = spec.and(bookNameContains(form.getBookName()));
		}

		// 3. もし著者名の入力があれば、さらに「著者名検索」の条件をANDで追加する
		if (StringUtils.hasText(form.getAuthorName())) {
			spec = spec.and(authorNameContains(form.getAuthorName()));
		}

		// 4. もし出版社名の入力があれば、さらに「出版社検索」の条件をANDで追加する
		if (StringUtils.hasText(form.getPublishCompany())) {
			spec = spec.and(publishCompanyContains(form.getPublishCompany()));
		}

		// 5. 最終的に組み上がったSpecificationを使って、検索を実行する
		Page<Book> bookPage = bookRepository.findAll(spec, pageable);

		return bookPage.map(this::convertToDto);
	}

	// -- 以下、部品メソッド群の変更点 --
	// 三項演算子を使っていた部分を、単純なSpecificationを返す形にすると、より可読性が上がるぞ。

	/**
	 * 書籍名でのあいまい検索のSpecificationを返す
	 * @param bookName
	 * @return
	 */
	private Specification<Book> bookNameContains(String bookName) {
		// ラムダ式で、具体的な検索条件を定義して返す
		return (root, query, cb) -> cb.like(root.get("bookName"), "%" + bookName + "%");
	}

	/**
	 * 著者名でのあいまい検索のSpecificationを返す
	 * @param authorName
	 * @return
	 */
	private Specification<Book> authorNameContains(String authorName) {
		return (root, query, cb) -> cb.like(root.join("author").get("authorName"), "%" + authorName + "%");
	}

	/**
	 * 出版社でのあいまい検索のSpecificationを返す
	 * @param publishCompany
	 * @return
	 */
	private Specification<Book> publishCompanyContains(String publishCompany) {
		return (root, query, cb) -> cb.like(root.get("publishCompany"), "%" + publishCompany + "%");
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
			dto.setAuthorCode(book.getAuthor().getAuthorCode());
		} else {
			dto.setAuthorName("著者情報なし");
		}

		return dto;
	}

	public BookDetailDto findByIsbn(String isbn) {
		Optional<Book> bookOptional = bookRepository.findById(isbn);
		Book book = bookOptional.orElseThrow(() -> new RuntimeException("該当する書籍がありません。"));

		return convertToDetailDto(book);

	}

	private BookDetailDto convertToDetailDto(Book book) {
		BookDetailDto dto = new BookDetailDto();
		dto.setIsbn(book.getIsbn());
		dto.setBookName(book.getBookName());
		dto.setPublishCompany(book.getPublishCompany());
		dto.setBookSummary(book.getBookSummary());
		dto.setAuthorName(book.getAuthor().getAuthorName());
//		if (book.getAuthor() != null)
//			dto.setAuthorName(book.getAuthor().getAuthorName());
		return dto;
	}
}