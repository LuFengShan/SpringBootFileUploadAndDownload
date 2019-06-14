package com.own.updown.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class DemoController {
	private static List<String> list = Arrays.asList("aa", "bb", "dd", "ada", "dla");

	@GetMapping("/test-request-header")
	public List<String> controllerList(@RequestHeader MultiValueMap<String, String> headers) {
		headers.forEach((key, value) -> {
			log.info(String.format("Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
		});
		return list.stream()
				.map(String::toLowerCase)
				.collect(Collectors.toList());
	}

	@GetMapping("/test-response-entity")
	public ResponseEntity<List<String>> queryByParentId() {

		if (list != null && 0 != list.size()) {
			//返回数据就为http响应体内容
			return ResponseEntity.ok(list);
		}
		//返回响应状态码204
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
