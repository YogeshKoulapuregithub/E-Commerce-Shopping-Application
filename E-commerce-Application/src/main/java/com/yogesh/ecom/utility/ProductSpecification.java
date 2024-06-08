package com.yogesh.ecom.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.yogesh.ecom.model.Product;
import com.yogesh.ecom.requestDto.SearchFilter;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class ProductSpecification {
	private SearchFilter searchFilter;
	
	
	public Specification<Product> buildSpecification(){
	   return(root,query,criteriaBuilder)->
	   {
		   List<Predicate> predicates=new ArrayList<>();
		   if(searchFilter.getMinPrice()>0)
		   {
			   predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"),
					   searchFilter.getMinPrice()));
		   }
		   if(searchFilter.getMaxPrice()>0)
		   {
			  predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"),
					  searchFilter.getMaxPrice()));   
		   }
		   if(searchFilter.getCategory()!=(null))
		   {
			 predicates.add(criteriaBuilder.equal(root.get("category"), searchFilter.getCategory()));  
		   }
		   if(searchFilter.getAvailability()!=(null))
		   {
			   predicates.add(criteriaBuilder.equal(root.get("availability"),searchFilter.getAvailability()));
		   }
//		   if(searchFilter.getRating()>0)
//		   {
//			   predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), searchFilter.getAvailability()));
//		   }
//		   if(searchFilter.getDiscount()>0)
//		   {
//			  
//		   }
		     Predicate[] predicates2 = predicates.toArray(new Predicate[0]);
		     return criteriaBuilder.and(predicates2);
	   };
	
	}

}
