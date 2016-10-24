/**
 * 
 */
package com.reportolia.base.repository;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import java.util.List;

//import org.springframework.data.repository.Repository;

/**
 * The ReadonlyRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Repository
public interface ReadonlyRepository<T, ID extends Serializable> extends org.springframework.data.repository.Repository<T, ID> {

	  T findById(ID id);

	  List<T> findAll();
}
