package com.yogesh.ecom.requestDto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class SearchFilter {
      private int minPrice;
      private int maxPrice;
      private String category;
      private String availability;
//      private int rating;
//      private int discount;
}
