package com.yogesh.ecom.responseDto;

import com.yogesh.ecom.enums.ImageType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageResponse {
	private int imageId;
	private String image;
	private ImageType imageType;

}
