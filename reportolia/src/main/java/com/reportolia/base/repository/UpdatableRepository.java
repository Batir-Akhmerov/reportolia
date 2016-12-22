/**
 * 
 */
package com.reportolia.base.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * The UpdatableRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Repository
public interface UpdatableRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, PagingAndSortingRepository<T, ID> {

	  T findById(ID id);

	  List<T> findAll();
	  
	  Page<T> findAll(Pageable pageable);
}
