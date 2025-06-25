package com.example.demo.controller.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BookSearchResultDto;
import com.example.demo.service.BookService;

@Controller
@RequestMapping("/search") // このコントローラは/searchで始まるURLを担当
public class EasySearchController {

    @Autowired
    private BookService bookService;

    /**
     * 簡単検索画面を初期表示する
     * @return View名
     */
    @GetMapping
    public String index() {
        // "search/easySearch"という名前のHTMLファイルを表示する
        return "easySearch";
    }

    /**
     * 検索を実行し、結果を表示する
     * @param keyword 画面のフォームから送られてくる検索キーワード
     * @param model Viewにデータを渡すための運び箱
     * @return View名
     */
    @GetMapping("/result")
    public String search(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword, Model model) {
        // Serviceを呼び出して検索結果のDTOリストを取得
        List<BookSearchResultDto> bookList = bookService.searchByBookName(keyword);

        // 検索結果とキーワードをModelに追加して、Viewに渡す
        model.addAttribute("books", bookList);
        model.addAttribute("keyword", keyword);

        // 再び "search/easySearch" のHTMLを表示する (結果表示部分だけが変わる)
        return "easySearch";
    }
}