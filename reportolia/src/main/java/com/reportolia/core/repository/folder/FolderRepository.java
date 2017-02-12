package com.reportolia.core.repository.folder;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reportolia.base.repository.UpdatableRepository;
import com.reportolia.core.model.folder.Folder;

/**
 * 
 * The FolderRepository class
 *
 * @author Batir Akhmerov
 * Created on Jan 2, 2017
 */
public interface FolderRepository extends UpdatableRepository<Folder, Long> {
	
	@Query(
			"select f FROM Folder f WHERE f.name = :name AND f.system = true ORDER BY f.name"
    )
    public Folder findOneBySystemName(@Param("name") String name);
	
}
