package com.moment.the.uncomfortable.controller;

import com.moment.the.response.ResponseService;
import com.moment.the.response.result.CommonResult;
import com.moment.the.response.result.ListResult;
import com.moment.the.response.result.SingleResult;
import com.moment.the.uncomfortable.dto.UncomfortableSetDto;
import com.moment.the.uncomfortable.dto.UncomfortableGetDto;
import com.moment.the.uncomfortable.service.UncomfortableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UncomfortableController {

    private final UncomfortableService uncomfortableService;
    private final ResponseService responseService;

    /**
     * 학교의 불편함을 작성합니다.
     * @param uncomfortableSetDto
     * @return getSuccessResult
     * @author 전지환, 정시원
     */
    @PostMapping("/uncomfortable")
    public CommonResult addUncomfortable(@Valid @RequestBody UncomfortableSetDto uncomfortableSetDto){
        uncomfortableService.addUncomfortable(uncomfortableSetDto);
        return responseService.getSuccessResult();
    }

    /**
     * 많은 학생들이 공감한 글 상위 30개를 선별하여 가져옵니다.
     * @return getListResult
     * @author 전지환, 정시원
     */
    @GetMapping("/uncomfortable/top30")
    public ListResult<UncomfortableGetDto> getTop30(){
        return responseService.getListResult(uncomfortableService.getTop30());
    }

    /**
     * 학교의 불편함 전체를 가져옵니다.
     * @return getListResult
     * @author 전지환, 정시원
     */
    @GetMapping("/uncomfortable")
    public ListResult<UncomfortableGetDto> getAllUncomfortable(){
        return responseService.getListResult(uncomfortableService.getAllUncomfortable());
    }

    /**
     * 해당 불편함의 좋아요를 증가시킵니다.
     * @param boardIdx
     * @return getSuccessResult
     * @author 전지환, 정시원
     */
    @PutMapping("/uncomfortable/like/increase/{boardIdx}")
    public CommonResult increaseLike(@PathVariable Long boardIdx){
        uncomfortableService.increaseLike(boardIdx);
        return responseService.getSuccessResult();
    }

    /**
     * 해당 불편함의 좋아요를 감소시킵니다.
     * @param boardIdx
     * @return getSuccessResult
     * @author 전지환, 정시원
     */
    @PutMapping("/uncomfortable/like/decrease/{boardIdx}")
    public CommonResult decreaseLike(@PathVariable Long boardIdx){
        uncomfortableService.decreaseLike(boardIdx);
        return responseService.getSuccessResult();
    }

    /**
     * 해당 불편함을 삭제합니다.
     * @param boardIdx
     * @return getSuccessResult
     * @author 전지환, 정시원
     */
    @DeleteMapping("/uncomfortable/{boardIdx}")
    public CommonResult deleteUncomfortable(@PathVariable Long boardIdx){
        uncomfortableService.deleteUncomfortable(boardIdx);
        return responseService.getSuccessResult();
    }

    /**
     * 불편함의 개수를 세어 가져옵니다.
     * @return getSingleResult
     * @author 전지환, 정시원
     */
    @GetMapping("/uncomfortable/amount")
    public SingleResult<Long> getNumberOfUncomfortable(){
        return responseService.getSingleResult(uncomfortableService.getNumberOfUncomfortable());
    }

    /**
     * 프로젝트 D-day를 세어 가져옵니다.
     * @return getSingleResult
     * @author 전지환, 정시원
     */
    @GetMapping("/uncomfortable/dateSinceProjectStart")
    public SingleResult<Integer> getDateSinceProjectStart(){
        return responseService.getSingleResult(uncomfortableService.getDateSinceProjectStart());
    }
}