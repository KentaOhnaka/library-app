package com.example.demo.controller.mvc;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BookSearchResultDto;
// ... 他のimport
import com.example.demo.param.DetailSearchForm;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search/detail")
public class DetailSearchController {

	private final BookService bookService;

	/**
	 * 詳細検索画面を初期表示する
	 * @param model
	 * @return
	 */
	@GetMapping
	public String showForm(Model model) {
		// ★★★重要★★★
		// 空のFormオブジェクトをModelに詰めて、Viewに渡す
		// これがないと、th:objectが紐付け対象を見つけられずエラーになる
		model.addAttribute("detailSearchForm", new DetailSearchForm());

		return "detailSearch"; // detailSearch.htmlを表示
	}

	/**
	 * 詳細検索を実行する (今はまだ形だけ)
	 * @param form 画面から送られてきた検索条件が詰まったFormオブジェクト
	 * @param model
	 * @return
	 */
	@GetMapping("/result")
	public String search(@ModelAttribute DetailSearchForm form,
			@RequestParam(name = "page", defaultValue = "0") int page,
			Model model) {

		// Serviceの新しいメソッドを呼び出す！
		Page<BookSearchResultDto> bookPage = bookService.detailSearch(form, page);

		// 結果と、入力内容(Form)をModelに詰めて画面に戻す
		model.addAttribute("page", bookPage);
		model.addAttribute("detailSearchForm", form); // ← 検索条件を画面に維持するため

		return "detailSearch"; // detailSearch.htmlに結果を表示
	}

}