package com.gdsc.festivalholic.controller;

import com.gdsc.festivalholic.config.response.ResponseDto;
import com.gdsc.festivalholic.config.response.ResponseUtil;
import com.gdsc.festivalholic.controller.dto.likes.LikesRequestDto;
import com.gdsc.festivalholic.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "좋아요", description = "Likes Controller")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "API 정상 작동"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/likes")
public class LikesController {

    private final LikesService likesService;

    @Operation(summary = "좋아요", description = "")
    @PostMapping("")
    public ResponseDto<Long> like(@RequestBody LikesRequestDto likeRequestDto) {
        return ResponseUtil.SUCCESS("좋아요 성공", likesService.insert(likeRequestDto));
    }

    @Operation(summary = "좋아요 취소", description = "좋아요 취소 API")
    @DeleteMapping("/{likesId}")
    public ResponseDto delete(@Parameter(description = "좋아요 인덱스 번호") @PathVariable Long likesId) {
        likesService.delete(likesId);
        return ResponseUtil.SUCCESS("삭제 완료", likesId);
    }


}
