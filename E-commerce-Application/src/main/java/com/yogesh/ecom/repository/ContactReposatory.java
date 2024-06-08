package com.yogesh.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogesh.ecom.model.Contacts;

public interface ContactReposatory extends  JpaRepository<Contacts, Integer> {

}
