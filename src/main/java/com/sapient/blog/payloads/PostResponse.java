package com.sapient.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
	
	private List<PostDto>content;
	private int pageNumber;
	private int pageSize;
	private long totalRecords;
	private int totalPages;
	boolean lastPage;
	

}
