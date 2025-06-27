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

-- 逆引き：テーブルが存在する場合、一度すべて削除する（何度も実行できるようにするため）
DROP TABLE IF EXISTS NOTICE;
DROP TABLE IF EXISTS RESERVE;
DROP TABLE IF EXISTS STOCK;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS LIBRARY;
DROP TABLE IF EXISTS WARD;


---
-- ■■■ テーブル作成 (CREATE TABLE) ■■■
---

-- 区画テーブル (親)
CREATE TABLE WARD (
    ward_code CHAR(4) PRIMARY KEY,
    ward_name VARCHAR(50) NOT NULL
);

-- 図書館テーブル (子: WARD)
CREATE TABLE LIBRARY (
    library_code CHAR(6) PRIMARY KEY,
    library_name VARCHAR(100) NOT NULL,
    library_tel VARCHAR(20),
    library_fax VARCHAR(20),
    library_address TEXT,
    opening_hours VARCHAR(255),
    ward_code CHAR(4) NOT NULL,
    FOREIGN KEY (ward_code) REFERENCES WARD(ward_code)
);

-- 著者テーブル (親)
CREATE TABLE AUTHOR (
    author_code CHAR(5) PRIMARY KEY,
    author_name VARCHAR(100) NOT NULL,
    author_mail VARCHAR(100),
    author_homepage VARCHAR(255),
    author_belong VARCHAR(100)
);

-- 書籍テーブル (子: AUTHOR)
CREATE TABLE BOOK (
    isbn VARCHAR(13) PRIMARY KEY,
    book_name VARCHAR(255) NOT NULL,
    author_code CHAR(5) NOT NULL,
    publish_date DATE,
    publish_company VARCHAR(100),
    book_summary TEXT,
    FOREIGN KEY (author_code) REFERENCES AUTHOR(author_code)
);

-- 在庫テーブル (中間テーブル: BOOKとLIBRARYを繋ぐ)
CREATE TABLE STOCK (
    stock_id SERIAL PRIMARY KEY, -- 在庫ごとのユニークID
    isbn VARCHAR(13) NOT NULL,
    library_code CHAR(6) NOT NULL,
    status VARCHAR(20) NOT NULL, -- '在庫あり', '貸出中', '予約中'など
    FOREIGN KEY (isbn) REFERENCES BOOK(isbn),
    FOREIGN KEY (library_code) REFERENCES LIBRARY(library_code)
);

-- 利用者テーブル
CREATE TABLE USERS (
    user_id VARCHAR(50) PRIMARY KEY,
    user_password VARCHAR(255) NOT NULL, -- パスワードは本来ハッシュ化して保存します
    user_name VARCHAR(100) NOT NULL,
    user_admin_flg BOOLEAN NOT NULL DEFAULT FALSE -- FALSE: 一般, TRUE: 管理者
);

-- 予約テーブル (子: USERS, BOOK, LIBRARY)
CREATE TABLE RESERVE (
    reserve_id SERIAL PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    library_code CHAR(6) NOT NULL, -- 受取希望の図書館
    reserve_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT '予約受付中', -- '予約受付中', '準備完了', '受取済'
    FOREIGN KEY (user_id) REFERENCES USERS(user_id),
    FOREIGN KEY (isbn) REFERENCES BOOK(isbn),
    FOREIGN KEY (library_code) REFERENCES LIBRARY(library_code)
);

-- お知らせテーブル (子: LIBRARY)
CREATE TABLE NOTICE (
    notice_id SERIAL PRIMARY KEY,
    notice_title VARCHAR(255) NOT NULL,
    library_code CHAR(6) NOT NULL,
    notice_date DATE NOT NULL,
    notice_contents TEXT,
    FOREIGN KEY (library_code) REFERENCES LIBRARY(library_code)
);


---
-- ■■■ ダミーデータ投入 (INSERT) ■■■
---

-- 区画データ
INSERT INTO WARD (ward_code, ward_name) VALUES
('W001', '千代田区'),
('W002', '中央区'),
('W003', '港区'),
('W004', '新宿区');

-- 図書館データ
INSERT INTO LIBRARY (library_code, library_name, library_tel, library_address, opening_hours, ward_code) VALUES
('LIB001', '千代田図書館', '03-5211-4290', '東京都千代田区九段南1-2-1', '月-金 10:00-22:00 / 土 10:00-19:00', 'W001'),
('LIB002', '日比谷図書文化館', '03-3502-3340', '東京都千代田区日比谷公園1-4', '月-金 10:00-22:00 / 土 10:00-19:00', 'W001'),
('LIB003', '京橋図書館', '03-3543-9224', '東京都中央区築地1-1-1', '月-土 10:00-21:00', 'W002'),
('LIB004', 'みなと図書館', '03-3437-6621', '東京都港区芝公園3-2-25', '月-土 9:00-20:00', 'W003'),
('LIB005', '高輪図書館', '03-5421-7617', '東京都港区高輪1-16-25', '月-土 9:00-20:00', 'W003'),
('LIB006', '中央図書館', '03-3364-1421', '東京都新宿区大久保3-1-22', '火-日 9:00-21:30', 'W004');

-- 著者データ
INSERT INTO AUTHOR (author_code, author_name, author_belong) VALUES
('A0001', 'Java Zamurai', 'Springの道場'),
('A0002', 'Uncle Bob', 'Clean Architecture Inc.'),
('A0003', '結城 浩', '数学とデザインパターンの森'),
('A0004', '夏目 漱石', '近代文学の巨匠'),
('A0005', '芥川 龍之介', '短編の名手');

-- 書籍データ
INSERT INTO BOOK (isbn, book_name, author_code, publish_date, publish_company, book_summary) VALUES
('9784798157579', 'Spring徹底入門 Spring FrameworkによるJavaアプリケーション開発', 'A0001', '2018-10-22', '翔泳社', 'Springの基礎から応用までを網羅した一冊。'),
('9784866360822', 'Clean Architecture 達人に学ぶソフトウェアの構造と設計', 'A0002', '2017-09-20', 'KADOKAWA', '保守性の高いソフトウェアをいかにして作るか。その答えがここにある。'),
('9784797397227', '増補改訂版Java言語で学ぶデザインパターン入門', 'A0003', '2018-06-23', 'SBクリエイティブ', '23のデザインパターンをJavaで学ぶ。'),
('9784041062909', 'こころ', 'A0004', '2017-07-25', 'KADOKAWA', '先生と私、そしてK。近代人の孤独とエゴイズムを描く。'),
('9784101025018', '羅生門・鼻', 'A0005', '2005-08-30', '新潮社', '極限状態に置かれた人間のエゴイズムを鋭く描いた表題作を含む短編集。'),
('9784774191139', 'リーダブルコード ―より良いコードを書くためのシンプルで実践的なテクニック', 'A0002', '2012-06-15', 'オライリージャパン', 'コードは理解しやすくなければならない。そのための具体的な方法論を提示する。');

-- 在庫データ
-- Spring徹底入門は3つの図書館に
INSERT INTO STOCK (isbn, library_code, status) VALUES
('9784798157579', 'LIB001', '在庫あり'),
('9784798157579', 'LIB004', '貸出中'),
('9784798157579', 'LIB006', '在庫あり');
-- Clean Architectureは2つの図書館に
INSERT INTO STOCK (isbn, library_code, status) VALUES
('9784866360822', 'LIB002', '在庫あり'),
('9784866360822', 'LIB006', '貸出中');
-- デザインパターン本は1つの図書館に
INSERT INTO STOCK (isbn, library_code, status) VALUES
('9784797397227', 'LIB001', '在庫あり');
-- こころは全ての図書館に
INSERT INTO STOCK (isbn, library_code, status) VALUES
('9784041062909', 'LIB001', '貸出中'),
('9784041062909', 'LIB002', '在庫あり'),
('9784041062909', 'LIB003', '在庫あり'),
('9784041062909', 'LIB004', '貸出中'),
('9784041062909', 'LIB005', '予約中'),
('9784041062909', 'LIB006', '在庫あり');
-- 羅生門は3つの図書館に
INSERT INTO STOCK (isbn, library_code, status) VALUES
('9784101025018', 'LIB003', '在庫あり'),
('9784101025018', 'LIB004', '在庫あり'),
('9784101025018', 'LIB005', '貸出中');
-- リーダブルコードは2つの図書館に
INSERT INTO STOCK (isbn, library_code, status) VALUES
('9784774191139', 'LIB002', '貸出中'),
('9784774191139', 'LIB006', '在庫あり');


-- 利用者データ
INSERT INTO USERS (user_id, user_password, user_name, user_admin_flg) VALUES
('user01', 'password123', '田中 太郎', FALSE),
('user02', 'password456', '鈴木 花子', FALSE),
('admin01', 'adminpass', '管理者 様', TRUE);

-- 予約データ
INSERT INTO RESERVE (user_id, isbn, library_code, status) VALUES
('user01', '9784798157579', 'LIB004', '準備完了'), -- 貸出中のSpring本を受け取りたい
('user02', '9784041062909', 'LIB005', '予約受付中'); -- 予約中のこころを受け取りたい

-- お知らせデータ
INSERT INTO NOTICE (notice_title, library_code, notice_date, notice_contents) VALUES
('夏季休館日のお知らせ', 'LIB001', '2025-06-20', '8月10日から8月15日まで、蔵書整理のため休館いたします。'),
('プログラミング講座開催！', 'LIB006', '2025-06-25', '7月15日(土) 14:00より、「初めてのPython」講座を開催します。詳細は館内ポスターをご覧ください。');

-- 動作確認用 SELECT 文 (実行後、下のクエリを個別に実行してデータを確認せよ)
-- SELECT * FROM WARD;
-- SELECT * FROM LIBRARY;
-- SELECT * FROM AUTHOR;
-- SELECT * FROM BOOK;
-- SELECT * FROM STOCK;
-- SELECT * FROM USERS;
-- SELECT * FROM RESERVE;
-- SELECT * FROM NOTICE;