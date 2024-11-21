package cotato.backend.domains.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/excel")
	public ResponseEntity<DataResponse<Void>> savePostsByExcel(@RequestBody SavePostsByExcelRequest request) {
		postService.saveEstatesByExcel(request.getPath());

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping("/single")
	public ResponseEntity<DataResponse<Void>> savePostBySingle(@RequestBody PostDTO postDTO){
		postService.savePostBySingle(postDTO);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<Post> readPostBySingle(@PathVariable Long id){
		Post post = postService.readPostBySingle(id);

		return ResponseEntity.ok(post);
	}

	@GetMapping("/readByViews")
	public ResponseEntity<Map<String, Object>> readByView(@RequestParam(defaultValue = "0") int page){
		int size = 10;
		Page<Post> postPage = postService.getPosts(page, size);
		List<Map<String, Object>> filteredPosts = postPage.getContent().stream().map(post -> {
			Map<String, Object> postMap = new HashMap<>();
			postMap.put("id", post.getId());
			postMap.put("title", post.getTitle());
			postMap.put("name", post.getName());
			return postMap;
		}).collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("posts", filteredPosts); // 게시글 리스트
		response.put("currentPage", postPage.getNumber()); // 현재 페이지
		response.put("totalPages", postPage.getTotalPages());

		return ResponseEntity.ok(response);
	}


}
