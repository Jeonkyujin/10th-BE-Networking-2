package cotato.backend.domains.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
