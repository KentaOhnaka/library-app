package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository <Book,String>,JpaSpecificationExecutor<Book>{
//	@param bookName
//	@return
	Page<Book> findByBookNameContaining(String bookName,Pageable pageable);
}
