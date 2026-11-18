package com.sqx.feedback.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.feedback.domain.bo.FeedbackInfoBo;
import com.sqx.feedback.domain.bo.FeedbackPageBo;
import com.sqx.feedback.domain.bo.FeedbackSaveBo;
import com.sqx.feedback.domain.bo.FeedbackUpdateBo;
import com.sqx.feedback.domain.vo.FeedbackVo;
import com.sqx.feedback.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 意见反馈 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:53:00
 */
@Api(value = "管理端-意见反馈", tags = "管理端-意见反馈")
@RestController
@RequestMapping("/admin/feedback")
public class AdminFeedbackController {

    @Resource
    private FeedbackService feedbackService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated FeedbackSaveBo feedbackSaveBo) {
        feedbackService.saveInfo(feedbackSaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<FeedbackVo>> page(@RequestBody @Validated FeedbackPageBo feedbackPageBo) {
        IPage<FeedbackVo> voIPage = feedbackService.pageInfo(feedbackPageBo);
        return Result.success(voIPage);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<FeedbackVo> info(@RequestBody @Validated FeedbackInfoBo feedbackInfoBo) {
        FeedbackVo vo = feedbackService.info(feedbackInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated FeedbackUpdateBo feedbackUpdateBo) {
        feedbackService.updateInfo(feedbackUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated FeedbackInfoBo feedbackInfoBo) {
        feedbackService.delete(feedbackInfoBo);
        return Result.success();
    }
}
