package com.reportolia.core.repository.operand;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reportolia.base.repository.UpdatableRepository;
import com.reportolia.core.model.operand.ContentType;
import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.model.operand.OperandOwnerType;

/**
 * 
 * The OperandColumnPathRepository class
 *
 * @author Batir Akhmerov
 * Created on Dec 1, 2015
 */
public interface OperandRepository extends UpdatableRepository<Operand, Long> {
    
    List<Operand> findByOwnerIdAndOwnerTypeAndContentType(Long ownerId, OperandOwnerType ownerType, ContentType contentType, Sort sort);
     
}
