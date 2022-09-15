package com.crm.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.company.Headquarter;

@Transactional
public interface HeadquarterRepository extends JpaRepository<Headquarter, Long> {
	
	@Modifying
	@Query("delete from Headquarter h where h.id = :id")
	public void deleteByIdCustom(@Param("id") Long id);
}
