package com.example.demo.controller;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name= "게시물 관리 API", description ="Swagger 테스트용") //11장 p.19 API 문서의 제목&설명
@RestController //11장 p.9 @RestController를 클래스에 사용하면, 메소드가 Rest 방식으로 처리된다.
// 11장 p.19 => Swagger는 RestController 클래스를 읽어서 API 문서를 만든다
@RequestMapping("/board") // 중간매핑
public class BoardController {

	@Autowired
	BoardService boardService;
	
// 11장 p.10 등록
	@Operation(summary="게시물 등록", description ="파라미터") // 11장 p.19
	@PostMapping
	public ResponseEntity<Integer> register(@RequestBody BoardDTO dto){
		int no = boardService.register(dto);
		return new ResponseEntity<>(no, HttpStatus.CREATED);
	}
	
// 11장 p.11	목록
	@Operation(summary="게시물 목록 조회", description ="모든 게시물 정보를 조회합니다") // 11장 p.19
	@GetMapping
	public ResponseEntity<List<BoardDTO>> getList() {
		
		List<BoardDTO> list = boardService.getList();
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
// 11장 p.12 상세조회	
	@Operation(summary="게시물 상세조회", description="파라미터로 받은 글 번호로 게시물을 조회합니다.") // 11장 p.19
	@GetMapping("/{no}")
	public ResponseEntity<BoardDTO> read(@PathVariable("no") int no){
		BoardDTO dto = boardService.read(no);
		
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
// 11장 p.13 수정
	@Operation(summary="게시물 수정", description="파라미터로 받은 게시물 정보를 교체합니다.") // 11장 p.19
	@PutMapping
	public ResponseEntity modify(@RequestBody BoardDTO dto) {
		
		boardService.modify(dto);;
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
// 11장 p.14 삭제
	@Operation(summary="게시물 삭제", description="파라미터로 받은 글번호로 게시물을 삭제합니다.") // 11장 p.19
	@DeleteMapping("/{no}")
	public ResponseEntity remove(@PathVariable("no") int no) {
		boardService.remove(no);
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	/* put, delete 매핑도 있으므로 테스트시 브라우저말고 postman사용해야 */
	
}















