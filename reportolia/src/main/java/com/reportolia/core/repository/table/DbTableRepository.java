package com.reportolia.core.repository.table;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reportolia.base.repository.UpdatableRepository;
import com.reportolia.core.model.table.DbTable;

/**
 * 
 * The DbTableRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface DbTableRepository extends UpdatableRepository<DbTable, Long> {
/*
    @Query(
            "Select t FROM db_table t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
    )
    public List<DbTable> search(@Param("searchTerm") String searchTerm);
*/  
	Page<DbTable> findByNameContainsAllIgnoreCase(String name, Pageable pageable);
    List<DbTable> findByName(String name);
    List<DbTable> findByNameAndDescription(String name, String description);
}
