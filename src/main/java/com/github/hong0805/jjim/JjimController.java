package com.github.hong0805.jjim;

import com.github.hong0805.bbs.Bbs;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jjim")
public class JjimController {

	private final JjimService jjimService;

	public JjimController(JjimService jjimService) {
		this.jjimService = jjimService;
	}

	@GetMapping("/{userID}/list")
	public ResponseEntity<Page<Bbs>> getJjimList(@PathVariable String userID,
			@RequestParam(defaultValue = "1") int page) {
		Page<Bbs> jjimList = jjimService.getJjimList(userID, page);
		return ResponseEntity.ok(jjimList);
	}

	@PostMapping("/{userID}/add/{bbsID}")
	public ResponseEntity<Void> addJjim(@PathVariable String userID, @PathVariable int bbsID) {
		jjimService.addJjim(userID, bbsID);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{userID}/delete/{bbsID}")
	public ResponseEntity<Void> deleteJjim(@PathVariable String userID, @PathVariable int bbsID) {
		jjimService.deleteJjim(userID, bbsID);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{userID}/total-count")
	public ResponseEntity<Long> getTotalCount(@PathVariable String userID) {
		long totalCount = jjimService.getTotalCount(userID);
		return ResponseEntity.ok(totalCount);
	}

	@GetMapping("/{userID}/has-next-page")
	public ResponseEntity<Boolean> hasNextPage(@PathVariable String userID, @RequestParam int page) {
		boolean hasNextPage = jjimService.hasNextPage(userID, page);
		return ResponseEntity.ok(hasNextPage);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}