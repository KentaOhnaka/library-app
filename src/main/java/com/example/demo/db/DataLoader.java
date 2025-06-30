package com.example.demo.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

/**
 * アプリケーション起動時に、初期データを投入するためのクラス
 */
@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder; // SecurityConfigで定義した暗号解読器

    @Override
    public void run(String... args) throws Exception {
        System.out.println("データローダーを実行します...");

        // テスト用の一般ユーザーを作成
        Users user = new Users();
        user.setUserId("user01");
        // ★★★ パスワードをBCryptで暗号化！ ★★★
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
        
        System.out.println("データ投入が完了しました。");
    }
}