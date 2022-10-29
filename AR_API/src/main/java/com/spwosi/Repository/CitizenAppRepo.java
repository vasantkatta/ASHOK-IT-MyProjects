package com.spwosi.Repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spwosi.entity.CitizenAppEntity;

@Repository
public interface CitizenAppRepo extends JpaRepository<CitizenAppEntity, Serializable> {

}
