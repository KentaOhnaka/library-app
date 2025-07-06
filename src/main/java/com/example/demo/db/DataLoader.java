//package com.example.demo.db;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.example.demo.entity.Users;
//import com.example.demo.repository.UsersRepository;
//
//import lombok.RequiredArgsConstructor;
//
///**
// * アプリケーション起動時に、初期データを投入するためのクラス
// */
//@Component
//@RequiredArgsConstructor
//@Profile("dev")
//public class DataLoader implements CommandLineRunner {
//
//    private final UsersRepository usersRepository;
//    private final PasswordEncoder passwordEncoder; // SecurityConfigで定義した暗号解読器
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("データローダーを実行します...");
//
//        // テスト用の一般ユーザーを作成
//        Users user = new Users();
//        user.setUserId("user01");
//        // ★★★ パスワードをBCryptで暗号化！ ★★★
//        user.setUserPassword(passwordEncoder.encode("password"));
//        user.setUserName("一般ユーザー");
//        user.setUserAdminFlg(false);
//        usersRepository.save(user); // データベースに保存
//
//        // テスト用の管理者ユーザーを作成
//        Users admin = new Users();
//        admin.setUserId("admin01");
//        admin.setUserPassword(passwordEncoder.encode("password"));
//        admin.setUserName("管理者ユーザー");
//        admin.setUserAdminFlg(true);
//        usersRepository.save(admin);
//        
//        System.out.println("データ投入が完了しました。");
//    }
//}


package com.example.demo.db;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
import com.example.demo.entity.Reserve;
import com.example.demo.entity.Users;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

/**
 * アプリケーション起動時に、初期データを投入するためのクラス
 */
@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    // --- ★★★ 必要な全てのRepositoryと部品を注入する ★★★ ---
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReserveRepository reserveRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    @Override
    public void run(String... args) throws Exception {

        // --- ★★★ ユーザー作成処理 (君が持っていたコード) ★★★ ---
        System.out.println("ユーザーデータの投入を開始します...");
        // テスト用の一般ユーザーを作成
        Users user = new Users();
        user.setUserId("user01");
        user.setUserPassword(passwordEncoder.encode("password"));
        user.setUserName("一般ユーザー");
        user.setUserAdminFlg(false);
        usersRepository.save(user); // データベースに保存

        // テスト用の管理者ユーザーを作成
        Users admin = new Users();
        admin.setUserId("admin01");
        admin.setUserPassword(passwordEncoder.encode("password"));
        admin.setUserName("管理者ユーザー");
        admin.setUserAdminFlg(true);
        usersRepository.save(admin);
        System.out.println("ユーザーデータ投入が完了しました。");


        // --- ★★★ 予約データ投入処理 (拙者が授けたコード) ★★★ ---
        System.out.println("予約データの投入を開始します...");
        // 予約に必要な「人物」「本」「場所」の情報を、まずDBから取得する
        Users user01 = usersRepository.findById("user01").orElse(null);
        Book bookKokoro = bookRepository.findById("978-4-10-101001-6").orElse(null);
        Book bookBotchan = bookRepository.findById("978-4-10-101003-0").orElse(null);
        Library libraryChuo = libraryRepository.findById("L001").orElse(null);

        // 全ての材料が揃っていることを確認
        if (user01 != null && bookKokoro != null && bookBotchan != null && libraryChuo != null) {

            // 予約データ1を作成
            Reserve reserve1 = new Reserve();
            reserve1.setUsers(user01);
            reserve1.setBook(bookKokoro);
            reserve1.setLibrary(libraryChuo); // 中央図書館で受け取る
            reserve1.setReserveDate(LocalDateTime.now().minusDays(5)); // 5日前に予約
            reserveRepository.save(reserve1); // データベースに保存

            // 予約データ2を作成
            Reserve reserve2 = new Reserve();
            reserve2.setUsers(user01);
            reserve2.setBook(bookBotchan);
            reserve2.setLibrary(libraryChuo);
            reserve2.setReserveDate(LocalDateTime.now().minusDays(2)); // 2日前に予約
            reserve2.setReserveMemo("第2希望");
            reserveRepository.save(reserve2);

            System.out.println("予約データ投入が完了しました。");
        } else {
            System.err.println("予約データの投入に必要な、利用者、書籍、または図書館の基本データが見つかりませんでした。");
        }
    }
}