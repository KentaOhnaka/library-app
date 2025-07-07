//package com.example.demo.db;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//// 全てのEntityとRepositoryをインポートする
//import com.example.demo.entity.Author;
//import com.example.demo.entity.Book;
//import com.example.demo.entity.Library;
//import com.example.demo.entity.Reserve;
//import com.example.demo.entity.Users;
//import com.example.demo.entity.Ward;
//import com.example.demo.repository.AuthorRepository;
//import com.example.demo.repository.BookRepository;
//import com.example.demo.repository.LibraryRepository;
//import com.example.demo.repository.ReserveRepository;
//import com.example.demo.repository.UsersRepository;
//import com.example.demo.repository.WardRepository;
//
//import lombok.RequiredArgsConstructor;
//
///**
// * アプリケーション起動時に、初期データを投入するためのクラス
// * 各テーブルにデータが存在しない場合のみ、データを作成する。
// */
//@Component
//@RequiredArgsConstructor
//@Profile("dev")
//public class DataLoader implements CommandLineRunner {
//
//    // --- 必要な全てのRepositoryを注入する ---
//    private final UsersRepository usersRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final WardRepository wardRepository;
//    private final LibraryRepository libraryRepository;
//    private final AuthorRepository authorRepository;
//    private final BookRepository bookRepository;
//    private final ReserveRepository reserveRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        // --- 0. 全ての土台となる基本データを作成 ---
//        // ★ 著者テーブルが空の場合のみ、基本データを全て投入する
//        if (authorRepository.count() == 0) {
//            System.out.println("基本データ（区画、図書館、著者、書籍）の投入を開始します...");
//
//            // 区画データ
//            Ward w1 = new Ward(); w1.setWardCode("W01"); w1.setWardName("中央区");
//            wardRepository.save(w1);
//
//            // 図書館データ
//            Library l1 = new Library(); l1.setLibraryCode("L001"); l1.setLibraryName("中央図書館"); l1.setWard(w1);
//            libraryRepository.save(l1);
//
//            // 著者データ
//            Author a1 = new Author(); a1.setAuthorCode("A001"); a1.setAuthorName("夏目漱石");
//            Author a2 = new Author(); a2.setAuthorCode("A002"); a2.setAuthorName("Spring Ninja");
//            authorRepository.saveAll(List.of(a1, a2));
//
//            // 書籍データ ★★★ ここで、著者オブジェクトとの縁を結ぶ！ ★★★
//            Book b1 = new Book(); b1.setIsbn("978-4-10-101001-6"); b1.setBookName("こころ"); b1.setAuthor(a1);
//            Book b2 = new Book(); b2.setIsbn("978-4-10-101003-0"); b2.setBookName("坊っちゃん"); b2.setAuthor(a1);
//            Book b3 = new Book(); b3.setIsbn("978-4-79-815758-2"); b3.setBookName("Spring Boot 2 入門"); b3.setAuthor(a2);
//            bookRepository.saveAll(List.of(b1, b2, b3));
//
//            System.out.println("基本データの投入が完了しました。");
//        }
//
//
//        // --- 1. ユーザーデータを作成 ---
//        if (usersRepository.count() == 0) {
//            System.out.println("ユーザーデータの投入を開始します...");
//            Users user = new Users(); user.setUserId("user01"); user.setUserPassword(passwordEncoder.encode("password")); user.setUserName("一般ユーザー"); user.setUserAdminFlg(false);
//            usersRepository.save(user);
//            System.out.println("ユーザーデータ投入が完了しました。");
//        }
//
//        // --- 2. 予約データを作成 ---
//        if (reserveRepository.count() == 0) {
//             System.out.println("予約データの投入を開始します...");
//            // 予約に必要な「人物」「本」「場所」の情報を、改めてDBから取得する
//            Users user01 = usersRepository.findById("user01").get();
//            Book bookKokoro = bookRepository.findById("978-4-10-101001-6").get();
//            Library libraryChuo = libraryRepository.findById("L001").get();
//            
//            Reserve reserve1 = new Reserve(); 
//            reserve1.setUsers(user01); // ★Usersエンティティをセット
//            reserve1.setBook(bookKokoro);   // ★Bookエンティティをセット
//            reserve1.setLibrary(libraryChuo); // ★Libraryエンティティをセット
//            reserve1.setReserveDate(LocalDateTime.now().minusDays(5));
//            reserveRepository.save(reserve1);
//            System.out.println("予約データ投入が完了しました。");
//        }
//    }
//}
//
//


package com.example.demo.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// 全てのEntityとRepositoryをインポートする
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
import com.example.demo.entity.Reserve;
import com.example.demo.entity.Users;
import com.example.demo.entity.Ward;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.WardRepository;

import lombok.RequiredArgsConstructor;

/**
 * アプリケーション起動時に、初期データを投入するためのクラス
 * 各テーブルにデータが存在しない場合のみ、データを作成する。
 */
@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    // --- 必要な全てのRepositoryを注入する ---
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final WardRepository wardRepository;
    private final LibraryRepository libraryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ReserveRepository reserveRepository;

    @Override
    public void run(String... args) throws Exception {

        // --- 0. 全ての土台となる基本データを作成 ---
        if (wardRepository.count() == 0) {
            System.out.println("基本データ（区画、図書館、著者、書籍）の投入を開始します...");

            // =========== 区画データ (15件) ===========
            List<Ward> wards = new ArrayList<>();
            String[] wardNames = {"千代田区", "中央区", "港区", "新宿区", "文京区", "台東区", "墨田区", "江東区", "品川区", "目黒区", "大田区", "世田谷区", "渋谷区", "中野区", "杉並区"};
            for (int i = 0; i < wardNames.length; i++) {
                Ward w = new Ward();
                w.setWardCode(String.format("W%03d", i + 1));
                w.setWardName(wardNames[i]);
                w.setWardCoordinates( (139.75 + i*0.01) + "," + (35.68 - i*0.01) );
                wards.add(w);
            }
            wardRepository.saveAll(wards);

            // =========== 図書館データ (15件) ===========
            List<Library> libraries = new ArrayList<>();
            for (int i = 0; i < wards.size(); i++) {
                Library l = new Library();
                l.setLibraryCode(String.format("L%03d", i + 1));
                l.setLibraryName(wards.get(i).getWardName() + "立中央図書館");
                l.setLibraryTel("03-1234-" + String.format("%04d", i + 1));
                l.setLibraryFax("03-5678-" + String.format("%04d", i + 1));
                l.setLibraryAddress(wards.get(i).getWardName() + "1-2-3");
                l.setOpeningHours("9:00-20:00");
                l.setWard(wards.get(i));
                libraries.add(l);
            }
            libraryRepository.saveAll(libraries);

            // =========== 著者データ (15件) ===========
            List<Author> authors = new ArrayList<>();
            String[][] authorDetails = {
                {"A001", "夏目漱石", "近代文学", "soseki@example.com"},
                {"A002", "芥川龍之介", "近代文学", "akutagawa@example.com"},
                {"A003", "太宰治", "近代文学", "dazai@example.com"},
                {"A004", "川端康成", "近代文学", "kawabata@example.com"},
                {"A005", "三島由紀夫", "戦後文学", "mishima@example.com"},
                {"A006", "谷崎潤一郎", "近代文学", "tanizaki@example.com"},
                {"A007", "村上春樹", "現代文学", "murakami@example.com"},
                {"A008", "東野圭吾", "現代ミステリー", "higashino@example.com"},
                {"A009", "宮沢賢治", "児童文学", "miyazawa@example.com"},
                {"A010", "司馬遼太郎", "歴史小説", "shiba@example.com"},
                {"A011", "J.K. Rowling", "ファンタジー", "rowling@example.com"},
                {"A012", "George Orwell", "社会派小説", "orwell@example.com"},
                {"A013", "Spring Ninja", "技術書", "ninja@spring.io"},
                {"A014", "Java Samurai", "技術書", "samurai@java.com"},
                {"A015", "アジャイル侍", "技術書", "agile@scrum.jp"}
            };
            for(String[] detail : authorDetails) {
                Author a = new Author();
                a.setAuthorCode(detail[0]);
                a.setAuthorName(detail[1]);
                a.setAuthorBelong(detail[2]);
                a.setAuthorMail(detail[3]);
                a.setAuthorHomepage("http://www." + detail[3].split("@")[1]);
                authors.add(a);
            }
            authorRepository.saveAll(authors);

            // =========== 書籍データ (15件) ===========
         // =========== 書籍データ (15件) - ★★★セッターを使う、正しい姿★★★ ===========
            List<Book> books = new ArrayList<>();

            Book b1 = new Book();
            b1.setIsbn("9784101010016");
            b1.setBookName("こころ");
            b1.setAuthor(authors.get(0)); // 夏目漱石
            b1.setPublishDate(LocalDate.of(1914, 4, 20));
            b1.setPublishCompany("新潮社");
            b1.setBookSummary("先生と私、そしてKを巡る、人間のエゴイズムを描いた物語。");
            books.add(b1);

            Book b2 = new Book();
            b2.setIsbn("9784101010030");
            b2.setBookName("坊っちゃん");
            b2.setAuthor(authors.get(0)); // 夏目漱石
            b2.setPublishDate(LocalDate.of(1906, 4, 1));
            b2.setPublishCompany("春陽堂");
            b2.setBookSummary("正義感の強い青年が、四国の旧制中学校で繰り広げる痛快活劇。");
            books.add(b2);

            Book b3 = new Book();
            b3.setIsbn("9784101028042");
            b3.setBookName("蜘蛛の糸・杜子春");
            b3.setAuthor(authors.get(1)); // 芥川龍之介
            b3.setPublishDate(LocalDate.of(1918, 4, 16));
            b3.setPublishCompany("赤い鳥");
            b3.setBookSummary("極悪人のカンダタに差し伸べられた一筋の蜘蛛の糸。");
            books.add(b3);

            Book b4 = new Book();
            b4.setIsbn("9784101006019");
            b4.setBookName("人間失格");
            b4.setAuthor(authors.get(2)); // 太宰治
            b4.setPublishDate(LocalDate.of(1948, 7, 25));
            b4.setPublishCompany("筑摩書房");
            b4.setBookSummary("主人公・大庭葉蔵の、破滅的で壮絶な生涯を描いた、太宰治の代表作。");
            books.add(b4);

            Book b5 = new Book();
            b5.setIsbn("9784101001014");
            b5.setBookName("雪国");
            b5.setAuthor(authors.get(3)); // 川端康成
            b5.setPublishDate(LocalDate.of(1937, 6, 10));
            b5.setPublishCompany("創元社");
            b5.setBookSummary("「国境の長いトンネルを抜けると雪国であった。」で知られる、美しくも儚い物語。");
            books.add(b5);

            Book b6 = new Book();
            b6.setIsbn("9784101050012");
            b6.setBookName("金閣寺");
            b6.setAuthor(authors.get(4)); // 三島由紀夫
            b6.setPublishDate(LocalDate.of(1956, 10, 30));
            b6.setPublishCompany("新潮社");
            b6.setBookSummary("美への憧れから国宝である金閣寺に火を放った青年僧の物語。");
            books.add(b6);

            Book b7 = new Book();
            b7.setIsbn("9784101005050");
            b7.setBookName("細雪");
            b7.setAuthor(authors.get(5)); // 谷崎潤一郎
            b7.setPublishDate(LocalDate.of(1948, 12, 10));
            b7.setPublishCompany("中央公論社");
            b7.setBookSummary("大阪の旧家の四姉妹を主人公に、昭和初期の上流社会を描く大長編。");
            books.add(b7);

            Book b8 = new Book();
            b8.setIsbn("9784062775624");
            b8.setBookName("ノルウェイの森");
            b8.setAuthor(authors.get(6)); // 村上春樹
            b8.setPublishDate(LocalDate.of(1987, 9, 4));
            b8.setPublishCompany("講談社");
            b8.setBookSummary("喪失と再生をテーマに、1960年代の若者たちの恋愛と人生を描く。");
            books.add(b8);

            Book b9 = new Book();
            b9.setIsbn("9784167788015");
            b9.setBookName("容疑者Xの献身");
            b9.setAuthor(authors.get(7)); // 東野圭吾
            b9.setPublishDate(LocalDate.of(2005, 8, 1));
            b9.setPublishCompany("文藝春秋");
            b9.setBookSummary("天才物理学者・湯川学が、天才数学者・石神が仕組んだ完全犯罪に挑む。");
            books.add(b9);

            Book b10 = new Book();
            b10.setIsbn("9784101090018");
            b10.setBookName("注文の多い料理店");
            b10.setAuthor(authors.get(8)); // 宮沢賢治
            b10.setPublishDate(LocalDate.of(1924, 12, 1));
            b10.setPublishCompany("盛岡書店");
            b10.setBookSummary("二人の紳士が山奥で見つけた奇妙な西洋料理店「山猫軒」での不思議な体験。");
            books.add(b10);

            Book b11 = new Book();
            b11.setIsbn("9784167105763");
            b11.setBookName("竜馬がゆく (一)");
            b11.setAuthor(authors.get(9)); // 司馬遼太郎
            b11.setPublishDate(LocalDate.of(1963, 6, 1));
            b11.setPublishCompany("文藝春秋");
            b11.setBookSummary("幕末の風雲児、坂本龍馬の生涯をダイナミックに描く歴史小説。");
            books.add(b11);

            Book b12 = new Book();
            b12.setIsbn("9784863890673");
            b12.setBookName("ハリー・ポッターと賢者の石");
            b12.setAuthor(authors.get(10)); // J.K. Rowling
            b12.setPublishDate(LocalDate.of(1999, 12, 1));
            b12.setPublishCompany("静山社");
            b12.setBookSummary("孤児の少年ハリーが、自分が魔法使いであることを知り、ホグワーツ魔法魔術学校に入学する。");
            books.add(b12);

            Book b13 = new Book();
            b13.setIsbn("9784151200225");
            b13.setBookName("1984年");
            b13.setAuthor(authors.get(11)); // George Orwell
            b13.setPublishDate(LocalDate.of(1949, 6, 8));
            b13.setPublishCompany("早川書房");
            b13.setBookSummary("全体主義国家によって管理された近未来社会を描くディストピア小説。");
            books.add(b13);

            Book b14 = new Book();
            b14.setIsbn("9784798157582");
            b14.setBookName("Spring Boot 2 入門");
            b14.setAuthor(authors.get(12)); // Spring Ninja
            b14.setPublishDate(LocalDate.of(2018, 11, 22));
            b14.setPublishCompany("翔泳社");
            b14.setBookSummary("Spring Bootの基礎から実践までを学ぶ、Java侍のための必読書。");
            books.add(b14);

            Book b15 = new Book();
            b15.setIsbn("9781234567891");
            b15.setBookName("DIとAOPの理");
            b15.setAuthor(authors.get(13)); // Java Samurai
            b15.setPublishDate(LocalDate.of(2022, 3, 1));
            b15.setPublishCompany("侍出版");
            b15.setBookSummary("Springの魂、DIとAOPの本質を理解せずして、真の侍にはなれぬ。");
            books.add(b15);
            
            bookRepository.saveAll(books);
            System.out.println("基本データの投入が完了しました。");
        }


        // --- 1. ユーザーデータを作成 ---
        if (usersRepository.count() == 0) {
            System.out.println("ユーザーデータの投入を開始します...");
            List<Users> userList = new ArrayList<>();
            for (int i = 1; i <= 15; i++) {
                Users u = new Users();
                u.setUserId(String.format("user%02d", i));
                u.setUserPassword(passwordEncoder.encode("password" + i));
                u.setUserName(String.format("一般ユーザー%02d", i));
                u.setUserAdminFlg(i % 5 == 0); // 5人に一人は管理者
                userList.add(u);
            }
            usersRepository.saveAll(userList);
            System.out.println("ユーザーデータ投入が完了しました。");
        }

        // --- 2. 予約データを作成 ---
        if (reserveRepository.count() == 0) {
             System.out.println("予約データの投入を開始します...");
             List<Users> allUsers = usersRepository.findAll();
             List<Book> allBooks = bookRepository.findAll();
             List<Library> allLibraries = libraryRepository.findAll();
             Random rand = new Random();

             List<Reserve> reserveList = new ArrayList<>();
             for (int i = 0; i < 15; i++) {
                Reserve r = new Reserve();
                r.setUsers(allUsers.get(rand.nextInt(allUsers.size())));
                r.setBook(allBooks.get(rand.nextInt(allBooks.size())));
                r.setLibrary(allLibraries.get(rand.nextInt(allLibraries.size())));
                r.setReserveDate(LocalDateTime.now().minusDays(rand.nextInt(30)));
                if (i % 3 == 0) {
                    r.setReserveMemo("メモ有り" + i);
                }
                reserveList.add(r);
             }
            reserveRepository.saveAll(reserveList);
            System.out.println("予約データ投入が完了しました。");
        }
    }
}
