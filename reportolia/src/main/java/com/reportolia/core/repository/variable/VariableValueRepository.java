package com.reportolia.core.repository.variable;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reportolia.core.model.variable.Variable;
import com.reportolia.core.model.variable.VariableValue;
import com.reportolia.core.model.variable.VariableValueConsumerType;
import com.reportolia.core.repository.base.UpdatableRepository;


/**
 * 
 * The DbTableColumnRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface VariableValueRepository extends UpdatableRepository<VariableValue, Long> {
    
    List<VariableValue> findByVariableAndConsumerTypeAndConsumerIdAndUserId(Variable variable, VariableValueConsumerType consumerType, Long consumerId, Long userId);
    
    @Query(
			"SELECT vv FROM VariableValue vv WHERE "
			+ "vv.variable.id = :variableId "
			+ "AND vv.consumerType = :type "
			+ "AND vv.consumerId = :consumerId "
			+ "AND ( vv.userId IS NULL OR vv.userId = :userId )"
    )
    List<VariableValue> findByConsumer(@Param("variableId") Long variableId, @Param("type") VariableValueConsumerType type, @Param("consumerId") Long consumerId, @Param("userId") Long userId);
    
}
