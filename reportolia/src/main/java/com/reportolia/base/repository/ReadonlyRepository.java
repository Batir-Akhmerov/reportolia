/**
 * 
 */
package com.reportolia.base.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//import org.springframework.data.repository.Repository;

/**
 * The ReadonlyRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Repository
public interface ReadonlyRepository<T, ID extends Serializable> extends org.springframework.data.repository.Repository<T, ID>, PagingAndSortingRepository<T, ID> {

	  T findById(ID id);

	  List<T> findAll();
	  
	  Page<T> findAll(Pageable pageable);
}
