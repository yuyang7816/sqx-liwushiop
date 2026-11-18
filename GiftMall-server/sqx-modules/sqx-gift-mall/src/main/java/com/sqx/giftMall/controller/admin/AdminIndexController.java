package com.sqx.giftMall.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.RechargeRecordBo;
import com.sqx.domain.repository.domain.giftMall.dto.RechargeRecordVo;
import com.sqx.giftMall.domain.bo.StatisticsBo;
import com.sqx.giftMall.domain.vo.IndexStatisticsVo;
import com.sqx.giftMall.domain.vo.OrderStatisticsVo;
import com.sqx.giftMall.domain.vo.RechargeStatisticsVo;
import com.sqx.giftMall.domain.vo.UserStatisticsVo;
import com.sqx.giftMall.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2025/6/27
 */
@Api(value = "管理端-首页", tags = "管理端-首页")
@RestController
@RequestMapping("admin/index")
public class AdminIndexController {

    @Resource
    private IndexService indexService;

    @ApiOperation("充值统计")
    @PostMapping("rechargeStatistics")
    public Result<RechargeStatisticsVo> rechargeStatistics(@RequestBody StatisticsBo statisticsBo) {
        RechargeStatisticsVo vo = indexService.rechargeStatistics(statisticsBo);
        return Result.success(vo);
    }

    @ApiOperation("充值记录")
    @PostMapping("rechargeRecord")
    public Result<IPage<RechargeRecordVo>> rechargeRecord(@RequestBody RechargeRecordBo rechargeRecordBo) {
        IPage<RechargeRecordVo> voIPage = indexService.rechargeRecord(rechargeRecordBo);
        return Result.success(voIPage);
    }

    @ApiOperation("首页统计")
    @GetMapping("indexStatistics")
    public Result<IndexStatisticsVo> indexStatistics() {
        IndexStatisticsVo vo = indexService.indexStatistics();
        return Result.success(vo);
    }

    @ApiOperation("用户分析")
    @PostMapping("userStatistics")
    public Result<UserStatisticsVo> userStatistics(@RequestBody StatisticsBo statisticsBo) {
        UserStatisticsVo vo = indexService.userStatistics(statisticsBo);
        return Result.success(vo);
    }

    @ApiOperation("订单统计")
    @PostMapping("orderStatistics")
    public Result<OrderStatisticsVo> orderStatistics(@RequestBody StatisticsBo statisticsBo) {
        OrderStatisticsVo vo = indexService.orderStatistics(statisticsBo);
        return Result.success(vo);
    }
}
