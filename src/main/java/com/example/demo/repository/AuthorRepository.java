package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Author;
@Repository
public interface AuthorRepository extends JpaRepository <Author,String>{
    // ここにメソッドを定義するだけで、Spring Data JPAが自動で実装してくれる！
    // 例：findByAuthorName(String name) など
	Optional <Author> findByAuthorCode(String authorCode);

}
