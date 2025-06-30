package com.example.demo.controller.mvc;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BookSearchResultDto;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search") // このコントローラは/searchで始まるURLを担当
public class EasySearchController {


    private final BookService bookService;

    /**
     * 簡単検索画面を初期表示する
     * @return View名
     */
   @GetMapping
    public String index(Model model) {
        // ★★★ 変更点！ ★★★
        // 初期表示からページネーションを適用
        // キーワードは空文字、ページ番号は0（1ページ目）で検索を実行
        Page<BookSearchResultDto> bookPage = bookService.searchByBookName("", 0);
        
        // 結果のPageオブジェクトをModelに追加
        model.addAttribute("page", bookPage);
        model.addAttribute("keyword", ""); // キーワードも渡す
        
        return "easySearch";
    }
    /**
     * 検索を実行し、結果を表示する
     * @param keyword 画面のフォームから送られてくる検索キーワード
     * @param model Viewにデータを渡すための運び箱
     * @return View名
     */
    @GetMapping("/result")
    public String search(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
    		@RequestParam(name="page", defaultValue="0") int page,
    		Model model) {
        // Serviceを呼び出して検索結果のDTOリストを取得
        Page<BookSearchResultDto> bookPage = bookService.searchByBookName(keyword,page);

        // 検索結果とキーワードをModelに追加して、Viewに渡す
        model.addAttribute("page", bookPage);
        model.addAttribute("keyword", keyword);

        // 再び "search/easySearch" のHTMLを表示する (結果表示部分だけが変わる)
        return "easySearch";
    }
    
}