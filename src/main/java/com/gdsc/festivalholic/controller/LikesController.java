package com.gdsc.festivalholic.controller;

import com.gdsc.festivalholic.SessionManager;
import com.gdsc.festivalholic.config.exception.ApiException;
import com.gdsc.festivalholic.config.exception.ErrorCode;
import com.gdsc.festivalholic.config.response.ResponseDto;
import com.gdsc.festivalholic.config.response.ResponseUtil;
import com.gdsc.festivalholic.controller.dto.likes.LikesRequestDto;
import com.gdsc.festivalholic.domain.users.Users;
import com.gdsc.festivalholic.service.LikesService;
import com.gdsc.festivalholic.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private final UsersService usersService;

    @Operation(summary = "좋아요", description = "")
    @GetMapping("/beers/{beerId}")
    public ResponseDto<Long> like(@PathVariable Long beerId, @RequestHeader("Accesstoken") String accessToken) {
        return ResponseUtil.SUCCESS("좋아요 성공", likesService.insert(accessToken, beerId));
    }

    @Operation(summary = "좋아요 취소", description = "좋아요 취소 API")
    @DeleteMapping("/beers/{beerId}")
    public ResponseDto delete(@Parameter(description = "맥주 인덱스 번호") @PathVariable Long beerId, @RequestHeader("Accesstoken") String accessToken) {
        likesService.delete(beerId, accessToken);
        return ResponseUtil.SUCCESS("삭제 완료", beerId);
    }
    
}
