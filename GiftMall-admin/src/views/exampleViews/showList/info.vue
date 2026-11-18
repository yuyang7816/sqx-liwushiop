<template>
    <div class="user-detail-page">
        <div class="nf-page-hero user-detail-page__hero">
            <a href="#" class="user-detail-page__back" @click.prevent="prev">← 返回</a>
            <span class="nf-page-hero__badge">用户管理</span>
            <h2 class="nf-page-hero__title">用户详情</h2>
            <p class="nf-page-hero__desc">查看与管理用户基本信息、钱包、订单及关联数据</p>
        </div>

        <div class="nf-filter-card user-detail-page__summary">
            <div class="nf-filter-card__head">
                <el-icon><User /></el-icon>
                <span>用户概要</span>
            </div>
            <div class="nf-filter-card__body user-detail-page__summary-body">
                <div class="user-detail-page__summary-item">
                    <span class="user-detail-page__summary-label">微信名称</span>
                    <span class="user-detail-page__summary-value">{{ dataContainer.tableData.userName || dataContainer.tableData.nickName || '—' }}</span>
                </div>
                <div class="user-detail-page__summary-item">
                    <span class="user-detail-page__summary-label">手机号</span>
                    <span class="user-detail-page__summary-value">{{ dataContainer.tableData.phone || '未绑定' }}</span>
                </div>
                <div class="user-detail-page__summary-item">
                    <span class="user-detail-page__summary-label">邀请码</span>
                    <span class="user-detail-page__summary-value">{{ dataContainer.tableData.invitationCode || '—' }}</span>
                </div>
                <div class="user-detail-page__summary-item">
                    <span class="user-detail-page__summary-label">用户状态</span>
                    <span class="user-detail-page__summary-value">
                        <span v-if="dataContainer.tableData.status == 1">正常</span>
                        <span v-else-if="dataContainer.tableData.status == 2" class="user-detail-page__status--danger">禁用</span>
                        <span v-else>—</span>
                    </span>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="user-detail-page__tabs demo-tabs" @tab-click="handleClick">
            <el-tab-pane label="用户详情" name="first">
                <div class="nf-table-card user-detail-page__detail-card">
                <div class="detailtabel">
                    <div class="table_main">
                        <table>
                            <tbody>
                                <tr>
                                    <th>邀请码</th>
                                    <td>
                                        {{ dataContainer.tableData.invitationCode }}
                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button>
                                    </td>
                                    <!-- <th>邀请人邀请码</th>
                                    <td>
                                        {{ dataContainer.tableData.inviterCode }}
                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button>
                                    </td> -->
                                    <th>钱包</th>
                                    <td style="color: rgb(245, 108, 108)">
                                        {{ dataContainer.tablemoney.userMoney ? `${dataContainer.tablemoney.userMoney}元`
                                            : '0元' }}
                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:updateMoney')"
                                            @click="rechargenone()">修改金额</el-button>
                                    </td>
                                    <th>总收益</th>
                                    <td class="border-rt">
                                        {{ dataContainer.tableData.commission ? dataContainer.tableData.commission :
                                            '0元' }}
                                    </td>
                                </tr>
                                <!-- <tr>
                                    <th>openId</th>
                                    <td>
                                        {{ dataContainer.tableData.openId ? dataContainer.tableData.openId : '未绑定' }}
                                    </td>
                                    <th>直属佣金比例</th>
                                    <td>{{ dataContainer.tableData.zhiRate ? `${dataContainer.tableData.zhiRate}` : '0'
                                        }}
                                        <el-button size="small" style="color: var(--nf-brand);background: #fff;border: none;"
                                            @click="ageChangeU()">修改</el-button>
                                    </td>
                                    <th>非直属佣金比例</th>
                                    <td class="border-rt"> 
                                        <span v-if="dataContainer.tableData.feiRate">
                                            {{ dataContainer.tableData.feiRate ? dataContainer.tableData.feiRate : '0' }}
                                        </span>
                                        <el-button  size ="small" style="color: var(--nf-brand);background: #fff;border: none;"
                                            @click="ageChangeU()">修改</el-button>
                                    </td>
                                </tr> -->
                                <tr>
                                    <th>邀请人邀请码</th>
                                    <td>
                                        <span style="">{{ dataContainer.tableData.inviterUserCode }}</span>
                                        <!-- <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button> -->
                                    </td>
                                    <th>佣金比例</th>
                                    <td>
                                        {{ dataContainer.tableData.commissionRate ?
                                            `${dataContainer.tableData.commissionRate}` : '0'
                                        }}
                                        <el-button size="small" style="color: var(--nf-brand);background: #fff;border: none;"
                                            @click="ageChangeU()">修改</el-button>
                                    </td>

                                    <th>图像</th>
                                    <td class="border-rt">
                                        <img v-if="dataContainer.tableData.avatar" :src="dataContainer.tableData.avatar"
                                            width="80" height="80" />
                                        <img v-else src="../../../assets/logo.png" alt="" width="80" height="80">

                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button>
                                    </td>
                                </tr>
                                <tr>
                                    <th>微信名称</th>
                                    <td>
                                        {{
                                            dataContainer.tableData.userName ? dataContainer.tableData.userName
                                                : dataContainer.tableData.nickName
                                        }}
                                        <!-- {{ dataContainer.tableData.nickName }} -->
                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button>
                                    </td>
                                    <th>手机号</th>
                                    <td>
                                        <span v-if="dataContainer.tableData.phone">
                                            {{ dataContainer.tableData.phone ? dataContainer.tableData.phone : '未绑定' }}
                                        </span>

                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button>
                                    </td>
                                    <th>用户状态</th>
                                    <td class=" border-rt">
                                        <span v-if="dataContainer.tableData.status == 1">正常</span>
                                        <span v-if="dataContainer.tableData.status == 2"
                                            style="color: #f56c6c;">禁用</span>
                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:updateStatus')"
                                            @click="stateChange(dataContainer.tableData)">更改状态</el-button>
                                    </td>

                                </tr>
                                <tr>
                                    <th>支付宝姓名</th>
                                    <td>
                                        <span
                                            v-if="dataContainer.tableData.withdrawalAccounts && dataContainer.tableData.withdrawalAccounts.length > 0">{{
                                                dataContainer.tableData.withdrawalAccounts ?
                                                    dataContainer.tableData.withdrawalAccounts[0].accountName
                                                    : '未绑定' }}</span>

                                    </td>
                                    <th>支付宝账号</th>
                                    <td>
                                        <span
                                            v-if="dataContainer.tableData.withdrawalAccounts && dataContainer.tableData.withdrawalAccounts.length > 0">{{
                                                dataContainer.tableData.withdrawalAccounts ?
                                                    dataContainer.tableData.withdrawalAccounts[0].accountNumber
                                                    : '未绑定' }}</span>

                                    </td>
                                    <th>收款二维码</th>
                                    <td class="border-rt">
                                        <div style="display:flex;flex-wrap: wrap;">
                                            <div style="margin: 2px;"
                                                v-if="dataContainer.tableData.wxWithdrawalAccount">
                                                <el-popover placement="top-start" title="" trigger="hover">
                                                    <img style="width: 50px; height: 50px"
                                                        :src="dataContainer.tableData.wxWithdrawalAccount"
                                                        slot="reference">
                                                    <img style="width: 300px; height: 300px"
                                                        :src="dataContainer.tableData.wxWithdrawalAccount" alt="">
                                                </el-popover>
                                            </div>
                                        </div>
                                    </td>

                                </tr>
                                <tr>
                                    <!-- <th>是否实名</th>
                                    <td>
                                        <span v-if="dataContainer.tableData.isAuthentication == 1">待审核</span>
                                        <span v-else-if="dataContainer.tableData.isAuthentication == 2">已实名</span>
                                        <span v-else-if="dataContainer.tableData.isAuthentication == 3">审核未通过</span>
                                        <span v-else>未实名</span>
                                    </td> -->
                                    <th>性别</th>
                                    <td>
                                        <span v-if="dataContainer.tableData.sex == 0">未设置</span>
                                        <span v-if="dataContainer.tableData.sex == 1">男</span>
                                        <span v-if="dataContainer.tableData.sex == 2">女</span>
                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button>
                                    </td>
                                    <th>年龄</th>
                                    <td>
                                        <span v-if="dataContainer.tableData.age">{{ dataContainer.tableData.age
                                        }}</span>
                                        <span v-else>未设置</span>
                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button>
                                    </td>
                                    <th>渠道</th>
                                    <td class="border-rt">
                                        {{ dataContainer.tableData.platform }}
                                    </td>
                                </tr>
                                <!-- <tr>
                                    <th>个性签名</th>
                                    <td>
                                        <span>{{ dataContainer.tableData.signature ? dataContainer.tableData.signature :
                                            '暂无' }}</span>
                                        <el-button size="small" style="margin-left: 10px;"
                                            :disabled="!isAuth('userList:update')" @click="ageChangeU()">修改</el-button>
                                    </td>
                                    <th>是否是会员</th>
                                    <td>
                                        <span v-if="dataContainer.tableData.isVip == 1">是</span>
                                        <span v-else>否</span>
                                    </td>
                                    <th>会员到期时间</th>
                                    <td class="border-rt">
                                        {{ dataContainer.tableData.vipEndTime }}
                                    </td>
                                </tr> -->
                                <tr>
                                    <th class="border-bt"></th>
                                    <td class="border-bt"> {{ dataContainer.tableData.createTime }}</td>
                                    <th class="border-bt"></th>
                                    <td class="border-bt"></td>
                                    <th class="border-bt"></th>
                                    <td class="border-bt border-rt"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="钱包明细" name="second">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table :data="dataContainer.walletData.records" v-loading="dataContainer.tableDataLoading"
                    style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="title" label="标题"></el-table-column>
                    <el-table-column prop="content" label="内容"></el-table-column>
                    <el-table-column prop="money" label="金额" width="100">
                        <template #default="scope">
                            <span style="color: #f56c6c;" v-if="scope.row.type == 2">- {{ scope.row.money }}</span>
                            <span style="color: #006600;" v-if="scope.row.type == 1">+ {{ scope.row.money }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeQbmx" @current-change="handleCurrentChangeQbmx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.walletData.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="提现记录" name="third">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.withdrawData.records">
                    <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                    <!-- <el-table-column prop="orderNo" label="订单编号"></el-table-column> -->
                    <el-table-column prop="accountName" label="提现账号名称" width="150">
                        <template #default="scope">
                            <span>{{ scope.row.accountName ? scope.row.accountName : '暂无' }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="accountNumber" label="提现账号" width="100">
                        <template #default="scope">
                            <span>{{ scope.row.accountType == 1 ? scope.row.accountNumber : '暂无' }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="cardFront" label="收款二维码" width="150">
                        <template #default="scope">

                            <div v-if="scope.row.accountType == 2 && scope.row.accountNumber"
                                style="display:flex;flex-wrap: wrap;">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;"
                                            :src="scope.row.accountNumber" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="scope.row.accountNumber" alt="">
                                    </template>
                                </el-popover>
                            </div>
                            <div v-else>
                                暂无图片
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="amount" label="提现金额" width="100"></el-table-column>
                    <el-table-column prop="actualAmount" label="实际到账金额" width="100"></el-table-column>
                    <el-table-column prop="serviceFee" label="平台服务费" width="100"></el-table-column>
                    <el-table-column prop="reason" label="审核说明" width="100"></el-table-column>

                    <el-table-column prop="accountType" label="提现方式" width="100">
                        <template #default="scope">
                            <span v-if="scope.row.accountType == 1">支付宝</span>
                            <span v-else>微信</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="status" label="状态" width="100" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.status == 1">待转账</span>
                            <span v-if="scope.row.status == 2">转账成功</span>
                            <span v-if="scope.row.status == 3">拒绝转账</span>

                        </template>
                    </el-table-column>
                    <el-table-column prop="withdrawTime" label="转账时间" width="180"></el-table-column>
                    <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
                </el-table>

                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeTx" @current-change="handleCurrentChangeTx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.withdrawData.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="充值明细" name="fourth">
                <div class="nf-filter-card user-detail-page__recharge-filter">
                    <div class="nf-filter-card__head">
                        <el-icon><Filter /></el-icon>
                        <span>筛选条件</span>
                    </div>
                    <div class="nf-filter-card__body user-detail-page__recharge-filter-body">
                        <div class="user-detail-page__recharge-filter-item">
                            <span class="user-detail-page__recharge-filter-label">开始时间</span>
                            <el-date-picker v-model="dataContainer.startTime" align="right" type="date"
                                format="YYYY-MM-DD" date-format="MMM DD, YYYY" placeholder="选择开始时间"
                                class="user-detail-page__recharge-filter-input" />
                        </div>
                        <div class="user-detail-page__recharge-filter-item">
                            <span class="user-detail-page__recharge-filter-label">截止时间</span>
                            <el-date-picker v-model="dataContainer.endTime" align="right" type="date"
                                format="YYYY-MM-DD" date-format="MMM DD, YYYY" placeholder="选择截止时间"
                                class="user-detail-page__recharge-filter-input" />
                        </div>
                        <div class="user-detail-page__recharge-filter-actions">
                            <el-button type="primary" @click="timeDate2">查询</el-button>
                        </div>
                    </div>
                </div>
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.rechargeData.list">
                    <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                    <el-table-column prop="orderId" label="充值订单号" width="200"></el-table-column>
                    <el-table-column prop="money" label="充值金额"></el-table-column>
                    <el-table-column prop="userId" label="会员编号"></el-table-column>
                    <el-table-column label="充值方式">
                        <template #default="scope">
                            <span style="color: var(--nf-brand);cursor: pointer;" v-if="scope.row.classify == 1">app微信 </span>
                            <span style="color: var(--nf-brand);cursor: pointer;" v-if="scope.row.classify == 2">微信公众号</span>
                            <span style="color: var(--nf-brand);cursor: pointer;" v-if="scope.row.classify == 3">微信小程序</span>
                            <span style="color: var(--nf-brand);cursor: pointer;" v-if="scope.row.classify == 4">支付宝H5</span>
                            <span style="color: var(--nf-brand);cursor: pointer;" v-if="scope.row.classify == 5">支付宝APP</span>
                            <span style="color: var(--nf-brand);cursor: pointer;" v-if="scope.row.classify == 6">扫码支付</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态">
                        <template #default="scope">
                            <span style="color: #999;cursor: pointer;" v-if="scope.row.state == 0">待支付</span>
                            <span style="color: var(--nf-brand);cursor: pointer;" v-if="scope.row.state == 1">支付成功</span>
                            <span style="color: red;cursor: pointer;" v-if="scope.row.state == 2">支付失败</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="170">
                    </el-table-column>
                    <el-table-column prop="payTime" label="支付时间" width="170">
                        <template #default="scope">
                            <span v-if='scope.row.payTime == null'>暂未支付</span>
                            <span v-else>{{ scope.row.payTime }}</span>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeCzmx" @current-change="handleCurrentChangeCzmx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total,sizes, prev, pager, next,jumper"
                        :total="dataContainer.rechargeData.totalCount">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="团队收益明细" name="five">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table :data="dataContainer.dailituiguangData.records" v-loading="dataContainer.tableDataLoading"
                    style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="title" label="标题"></el-table-column>
                    <el-table-column prop="content" label="内容"></el-table-column>
                    <el-table-column prop="money" label="金额" width="100">
                        <template #default="scope">
                            <span style="color: #f56c6c;" v-if="scope.row.type == 2">- {{ scope.row.money }}</span>
                            <span style="color: #006600;" v-if="scope.row.type == 1">+ {{ scope.row.money }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeQbmx" @current-change="handleCurrentChangeQbmx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.dailituiguangData.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
            <!-- <el-tab-pane label="信用分明细" name="xinyongfenmingxi">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.xinyongfenData.records">
                    <el-table-column prop="id" label="编号" width="80">
                    </el-table-column>
                    <el-table-column prop="title" label="消息标题">
                    </el-table-column>
                    <el-table-column prop="content" label="消息内容">
                    </el-table-column>
                    <el-table-column prop="createAt" label="创建时间">
                    </el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeWdxx" @current-change="handleCurrentChangeWdxx"
                        :page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.xinyongfenData.total">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="保证金管理" name="eight">
                <el-table :data="dataContainer.baozhengjinData.records" v-loading="dataContainer.tableDataLoading"
                    style="width: 100%">
                    <el-table-column fixed prop="id" label="编号" width="80">
                    </el-table-column>
                    <el-table-column prop="money" label="保证金" width="100">
                    </el-table-column>
                    <el-table-column prop="phone" label="手机号" width="150">
                    </el-table-column>
                    <el-table-column prop="title" label="标题">
                    </el-table-column>
                    <el-table-column prop="content" label="内容">
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="150">
                    </el-table-column>
                    <el-table-column prop="type" label="状态" width="100" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.type == 1">已交保证金</span>
                            <span v-if="scope.row.type == 2">已退保证金</span>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeQbmx" @current-change="handleCurrentChangeQbmx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.baozhengjinData.total">
                    </el-pagination>
                </div>
            </el-tab-pane> -->
            <el-tab-pane label="我的订单" name="six">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table :data="dataContainer.fabuData.records" v-loading="dataContainer.tableDataLoading">
                    <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                    <el-table-column prop="giveUserName" label="领取人" fixed="left">
                        <template #default="scope">
                            <span v-if="scope.row.giveUserId" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.giveUserId)">
                                {{ scope.row.giveUserName ? scope.row.giveUserName : '未绑定' }}
                            </span>
                            <span v-else>-</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="typeName" label="商品类型" width="120">
                        <template #default="scope">
                            <div v-for="(item, index) in scope.row.productList" :key="index" style="display: block;">
                                <div
                                    style="display: inline-block;width: 70%;margin-left: 10px;height: 60px;margin: 5px;">
                                    <div
                                        style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;max-height:30px;">
                                        {{ item.giftProductType.name }}</div>

                                </div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="productList" label="商品详情" width="300">
                        <template #default="scope">
                            <div v-for="(item, index) in scope.row.productList" :key="index" style="display: block;">
                                <img v-if="item.giftProduct.coverImg" :src="item.giftProduct.coverImg"
                                    style="width: 60px;height: 60px;float: left;" />
                                <div
                                    style="display: inline-block;width: 70%;margin-left: 10px;height: 60px;margin: 5px;">
                                    <div
                                        style="font-size: 16px;color: #666;font-weight: 600;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;max-height:30px;">
                                        {{ item.giftProduct.name }}</div>
                                    <div
                                        style="font-size: 12px;color: #999;line-height: 20px;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;max-height:50px;">
                                        {{ item.skuDetailJson }} : {{ item.price }}￥ ×
                                        {{ item.num }}
                                    </div>
                                </div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="orderNo" label="订单号"></el-table-column>
                    <el-table-column prop="payMoney" label="支付金额" width="100"></el-table-column>
                    <el-table-column prop="expressName" label="快递名称" width="100"></el-table-column>
                    <el-table-column prop="expressNumber" label="快递单号" width="160">
                        <template #default="scope">
                            {{ scope.row.expressNumber }}
                            <el-button size="small" type="primary" v-if="scope.row.expressNumber" style="margin: 3px;"
                                @click="addChangeU(scope.row)">修改</el-button>
                            <el-button size="small" type="primary" plain v-if="scope.row.expressNumber"
                                style="margin: 3px;" @click="wuliuChangeU(scope.row)">查看物流</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column prop="payWay" label="支付方式" width="100" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.payWay == 1">微信</span>
                            <span v-if="scope.row.payWay == 2">支付宝</span>
                            <span v-if="scope.row.payWay == 3">paypal</span>
                            <span v-if="scope.row.payWay == 4">stripe</span>
                            <span v-if="scope.row.payWay == 5">余额</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="status" label="订单状态" width="100" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.status == 1">待付款</span>
                            <span v-if="scope.row.status == 2">已付款</span>
                            <span v-if="scope.row.status == 3">已发货</span>
                            <span v-if="scope.row.status == 4">已收货</span>
                            <span v-if="scope.row.status == 5">已取消</span>
                            <span v-if="scope.row.status == 6">退款中</span>
                            <span v-if="scope.row.status == 7">已退款</span>
                            <span v-if="scope.row.status == 8">拒绝退款</span>
                            <span v-if="scope.row.status == 9">拼团中</span>
                            <span v-if="scope.row.status == 10">已评价</span>
                            <span v-if="scope.row.status == 11">待赠送</span>
                            <span v-if="scope.row.status == 12">已赠送</span>
                            <span v-if="scope.row.status == 13">已领取</span>
                            <span v-if="scope.row.status == 14">已拒绝</span>
                            <span v-if="scope.row.status == 16">待发货</span>
                            <span v-if="scope.row.status == 17">已开发票</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeQbmx" @current-change="handleCurrentChangeQbmx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.fabuData.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="我的收藏" name="seven">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table :data="dataContainer.jiedanData.records" v-loading="dataContainer.tableDataLoading">
                    <el-table-column fixed prop="id" label="编号" width="80">
                    </el-table-column>
                    <el-table-column prop="giftProduct.name" label="礼物名称"></el-table-column>
                    <el-table-column prop="giftProduct.coverImg" label="商品封面图片">
                        <template #default="scope">
                            <div v-if="scope.row.giftProduct == null || scope.row.giftProduct.coverImg == ''">
                                暂无图片
                            </div>
                            <div v-else>
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;"
                                            :src="scope.row.giftProduct.coverImg" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="scope.row.giftProduct.coverImg"
                                            alt="">
                                    </template>
                                </el-popover>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="img" label="商品图片">
                        <template #default="scope">
                            <div v-if="scope.row.giftProduct == null || scope.row.giftProduct.img == ''">
                                暂无图片
                            </div>
                            <div v-else="scope.row.img.includes(',')" style="display:flex;flex-wrap: wrap;">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                                    v-for="(item, index) in scope.row.giftProduct.img.split(',')" :key="index">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="item" alt="">
                                    </template>
                                </el-popover>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="giftProduct.description" label="商品描述" width="150">
                    </el-table-column>
                    <el-table-column prop="giftProduct.originalPrice" label="原价">
                    </el-table-column>
                    <el-table-column prop="giftProduct.price" label="商品售价">
                    </el-table-column>
                    <el-table-column prop="giftProduct.discountRatio" label="折扣比例">
                    </el-table-column>
                    <el-table-column prop="giftProduct.status" label="商品状态">
                        <template #default="scope">
                            <span scope.row.giftProduct>
                                {{ scope.row.giftProduct.status === 0 ? '上架' : '下架' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="giftProduct.goodPick" label="是否精选好物">
                        <template #default="scope">
                            <span scope.row.giftProduct>
                                {{ scope.row.giftProduct.goodPick === 0 ? '是' : '否' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="giftProduct.specialOffer" label="是否特价商品">
                        <template #default="scope">
                            <span scope.row.giftProduct>
                                {{ scope.row.giftProduct.specialOffer === 0 ? '是' : '否' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="160">
                    </el-table-column>

                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeQbmx" @current-change="handleCurrentChangeQbmx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.jiedanData.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>

            <el-tab-pane label="我收到的礼物" name="nine">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table :data="dataContainer.sendData.records" v-loading="dataContainer.tableDataLoading">
                    <el-table-column fixed prop="id" label="编号" width="80"></el-table-column>
                    <el-table-column prop="shareUserName" label="赠送用户" width="150">
                        <template #default="scope">
                            <span style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
                                {{ scope.row.shareUserName ? scope.row.shareUserName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <!-- <el-table-column prop="typeName" label="商品类型">
                    </el-table-column> -->
                    <el-table-column prop="productList" label="商品详情">
                        <template #default="scope">
                            <div v-for="(item, index) in scope.row.productList" :key="index" style="display: block;">
                                <img v-if="item.giftProduct.coverImg" :src="item.giftProduct.coverImg"
                                    style="width: 60px;height: 60px;float: left;" />
                                <div
                                    style="display: inline-block;width: 70%;margin-left: 10px;height: 60px;margin: 5px;">
                                    <div
                                        style="font-size: 16px;color: #666;font-weight: 600;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;max-height:30px;">
                                        {{ item.giftProduct.name }}</div>
                                    <div style="font-size: 12px;color: #999;line-height: 20px;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;max-height:50px;"
                                        v-if="item.giftProductSku">
                                        {{ item.giftProductSku.detailJson }} : {{ item.giftProductSku.skuPrice }}￥ × 1
                                    </div>
                                </div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="160">
                    </el-table-column>
                </el-table>

                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeQbmx" @current-change="handleCurrentChangeQbmx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.sendData.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
            <!-- <el-tab-pane label="我的优惠券" name="ten">
                <el-table :data="dataContainer.youhuiquanData.records" v-loading="dataContainer.tableDataLoading">
                    <el-table-column fixed prop="id" label="编号" width="80"></el-table-column>
                    <el-table-column prop="couponName" label="优惠券名称"></el-table-column>
                    <el-table-column prop="couponPicture" label="优惠券图片">
                        <template #default="scope">
                            <img :src="scope.row.couponPicture" style="width: 50px; height: 50px;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="money" label="优惠券金额"></el-table-column>
                    <el-table-column prop="couponType" label="优惠券类型">
                        <template #default="scope">
                            <span v-if="scope.row.couponType == 0">全部可用</span>
                            <span v-else-if="scope.row.couponType == 1">即时代驾</span>
                            <span v-else-if="scope.row.couponType == 2">朋友代叫</span>
                            <span v-else-if="scope.row.couponType == 3">预约代驾</span>
                            <div v-if="scope.row.couponType == 4">包时代驾</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="minMoney" label="最低消费"></el-table-column>
                    <el-table-column prop="status" label="使用类型">
                        <template #default="scope">
                            <span v-if="scope.row.status == 0">正常</span>
                            <span v-else-if="scope.row.status == 1">已使用</span>
                            <span v-else-if="scope.row.status == 2">已失效</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="时间"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeQbmx" @current-change="handleCurrentChangeQbmx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.youhuiquanData.total">
                    </el-pagination>
                </div>
            </el-tab-pane> -->
            <!-- <el-tab-pane label="我的消息" name="xiaoxi">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.tousuData.records">
                    <el-table-column prop="id" label="编号" width="80"></el-table-column>
                    <el-table-column prop="title" label="消息标题"></el-table-column>
                    <el-table-column prop="content" label="消息内容"></el-table-column>
                    <el-table-column prop="createAt" label="创建时间"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeWdxx" @current-change="handleCurrentChangeWdxx"
                        :page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.tousuData.total">
                    </el-pagination>
                </div>
            </el-tab-pane> -->
            <el-tab-pane label="我的购物车" name="pinglun">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.autonymData.records">
                    <el-table-column prop="id" label="编号" width="80"></el-table-column>
                    <el-table-column prop="giftProduct.name" label="商品名称" width="200"></el-table-column>
                    <el-table-column prop="giftProduct" label="商品图片">
                        <template #default="scope">
                            <img v-if="scope.row.giftProduct && scope.row.giftProduct.coverImg"
                                :src="scope.row.giftProduct.coverImg" style="width: 50px; height: 50px;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="giftProductSku.detailJson" label="选择的规格"></el-table-column>

                    <el-table-column prop="number" label="数量" width="160"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeWdxx" @current-change="handleCurrentChangeWdxx"
                        :page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.autonymData.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
            <!-- <el-tab-pane label="师傅的评价" name="pinglin1">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.autonymData.records">
                    <el-table-column prop="evaluateId" label="编号" width="80"></el-table-column>
                    <el-table-column prop="indentNumber" label="评价订单号" width="200"></el-table-column>
                    <el-table-column prop="indent" label="师傅昵称" width="150">
                        <template #default="scope">
                            <span v-if="scope.row.riderUser && scope.row.riderUser.nickName"
                                style="color: var(--nf-brand);cursor: pointer;" @click="updates(scope.row.riderUserId)">
                                {{ scope.row.riderUser.nickName ? scope.row.riderUser.nickName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="evaluateMessage" label="评价内容"></el-table-column>
                    <el-table-column prop="satisfactionFlag" label="是否满意" width="160">
                        <template #default="scope">
                            <span v-if="scope.row.satisfactionFlag == '0'">满意</span>
                            <span v-if="scope.row.satisfactionFlag == '1'">不满意</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="评价时间" width="160"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeWdxx" @current-change="handleCurrentChangeWdxx"
                        :page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.autonymData.total">
                    </el-pagination>
                </div>
            </el-tab-pane> -->
            <!-- <el-tab-pane label="收益统计" name="tongji">

                <div>
                    <el-select v-model="dataContainer.flag" style="width:150px;margin-left: 10px;"
                        @change="orderfenxi()">
                        <el-option v-for="(item, index) in dataContainer.flags" :key="index" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>&nbsp;&nbsp;&nbsp;
                    <el-date-picker v-model="dataContainer.info" type="date" placeholder="选择时间" format="YYYY-MM-DD"
                        value-format="YYYY-MM-DD" @change="animeOrder()" />
                </div>
                <div>
                    <el-row>
                        <el-col :span="6" class="cards">
                            <div class="box">
                                <div class="box_num">
                                    <div class="box_color">收益统计</div>
                                    <div class="text_color">
                                        <span v-if="dataContainer.tableData2">{{ dataContainer.tableData2.incomeday ?
                                            dataContainer.tableData2.incomeday : 0 }}</span>元
                                    </div>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="6" class="cards">
                            <div class="box">
                                <div class="box_num">
                                    <div class="box_color">订单数量</div>
                                    <div class="text_color">
                                        <span v-if="dataContainer.tableData2">{{ dataContainer.tableData2.indentCount ?
                                            dataContainer.tableData2.indentCount : 0 }}</span>单
                                    </div>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.tableData3.records">
                    <el-table-column prop="id" label="编号" width="80"></el-table-column>
                    <el-table-column prop="title" label="消息标题"></el-table-column>
                    <el-table-column prop="content" label="消息内容"></el-table-column>
                    <el-table-column prop="createAt" label="创建时间"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeWdxx" @current-change="handleCurrentChangeWdxx"
                        :page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.tableData3.total">
                    </el-pagination>
                </div>
            </el-tab-pane> -->
            <!-- <el-tab-pane label="我的地址" name="dizhi">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.dizhiData.records">
                    <el-table-column prop="addressId" label="编号" width="80"></el-table-column>
                    <el-table-column prop="province" label="省"></el-table-column>
                    <el-table-column prop="city" label="市"></el-table-column>
                    <el-table-column prop="county" label="区"></el-table-column>
                    <el-table-column prop="address" label="详细地址"></el-table-column>
                    <el-table-column prop="lng" label="经度"></el-table-column>
                    <el-table-column prop="lat" label="纬度"></el-table-column>
                    <el-table-column prop="updateTime" label="修改时间"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间"></el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeWdxx" @current-change="handleCurrentChangeWdxx"
                        :page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.dizhiData.total">
                    </el-pagination>
                </div>
            </el-tab-pane> -->

            <el-tab-pane label="我的发票" name="zaixianshichang">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.tableDataZxsc.records">
                    <el-table-column prop="invoiceId" label="编号" width="50" fixed="left"></el-table-column>
                    <el-table-column prop="userEmail" label="邮箱">
                    </el-table-column>
                    <el-table-column prop="userPhone" label="用户电话">
                    </el-table-column>
                    <el-table-column prop="indentNumber" label="订单号" width="170">
                        <template #default="scope">
                            <div v-if="scope.row.indentNumber">
                                <div v-for="(item, index) in scope.row.indentNumber.split(',')">{{ item }} </div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="invoiceType" label="发票类型">
                        <template #default="scope">
                            <span v-if="scope.row.invoiceType == 1">公司</span>
                            <span v-if="scope.row.invoiceType == 2">个人</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="taxPayerNumber" label="税号" width="180">
                    </el-table-column>
                    <el-table-column prop="invoiceMoney" label="发票金额">
                    </el-table-column>
                    <el-table-column prop="bankAddress" label="注册地址">
                    </el-table-column>
                    <el-table-column prop="bankDeposit" label="开户行">
                    </el-table-column>
                    <el-table-column prop="bankNumber" label="银行卡号">
                    </el-table-column>
                    <el-table-column prop="bankPhone" label="注册电话">
                    </el-table-column>
                    <el-table-column prop="invoiceHeader" label="发票抬头" width="180">
                    </el-table-column>
                    <el-table-column prop="invoiceTitle" label="发票标题" width="180">
                    </el-table-column>
                    <el-table-column prop="status" label="发票状态" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.status == 1" style="color:red">待开票</span>
                            <span v-if="scope.row.status == 2" style="color: darkgreen;">已开票</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="170">
                    </el-table-column>
                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeWdxx" @current-change="handleCurrentChangeWdxx"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.tableDataZxsc.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
            <!-- <el-tab-pane label="我的车辆" name="car">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.dongtaiData.records">
                    <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                    <el-table-column prop="userType" label="类型" width="100" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.userType == 1">用户车辆</span>
                            <span v-if="scope.row.userType == 2" style="color: red;">师傅车辆</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="modelName" label="车型" fixed="left"></el-table-column>
                    <el-table-column prop="plateNumber" label="车牌号" width="100"></el-table-column>
                    <el-table-column prop="colourName" label="车辆颜色" width="100"></el-table-column>

                    <el-table-column prop="carImg" label="车辆图片" width="300">
                        <template #default="scope">
                            <div v-if="scope.row.carImg">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                                    v-for="(item, index) in scope.row.carImg.split(',')" :key="index">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="item" alt="">
                                    </template>
                                </el-popover>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="carGasName" label="油品"></el-table-column>

                    <el-table-column prop="createTime" label="创建时间"></el-table-column>

                </el-table>
                <div class="user-detail-page__pagination">
                    <el-pagination @size-change="handleSizeChangeDt" @current-change="handleCurrentChangeDt"
                        :page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total,sizes, prev, pager, next,jumper"
                        :total="dataContainer.dongtaiData.total">
                    </el-pagination>
                </div>
            </el-tab-pane> -->
            <!-- <el-tab-pane label="我的通讯录" name="six">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.friendData.records">
					<el-table-column prop="userId" label="编号" width="100"></el-table-column>
                    <el-table-column prop="userName" label="用户昵称">
                        <template #default="scope">
                            <span style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.userId)">
                                {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="avatar" label="用户图头">
                        <template #default="scope">
                            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="phone" label="手机号" ></el-table-column>
                    
				</el-table>
				<div class="user-detail-page__pagination">
					<el-pagination @size-change="handleSizeChangeHy" @current-change="handleCurrentChangeHy"
						:page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit" :current-page="dataContainer.page"
						layout="total, sizes, prev, pager, next, jumper" :total="dataContainer.friendData.total">
					</el-pagination>
				</div>
            </el-tab-pane>
            <el-tab-pane label="我的群组" name="seven">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.qunzuData">
					<el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                    <el-table-column prop="ownerUserInfo" label="群主昵称" fixed="left">
                        <template #default="scope">
                            <span v-if="scope.row.ownerUserInfo" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.ownerUserId)">
                                {{ scope.row.ownerUserInfo.userName ? scope.row.ownerUserInfo.userName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ownerUserInfo" label="群主头像">
                        <template #default="scope">
                            <img v-if="scope.row.ownerUserInfo && scope.row.ownerUserInfo.avatar"
                                :src="scope.row.ownerUserInfo.avatar" style="width: 60px;height: 60px;" />
                            <span v-else> - </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" label="群名称" fixed="left"></el-table-column>
                    <el-table-column prop="notice" label="群公告" width="100"></el-table-column>
                    <el-table-column prop="qrCode" label="群二维码">
                        <template #default="scope">
                            <img v-if="scope.row.qrCode" :src="scope.row.qrCode" style="width: 60px;height: 60px;" />
                            <span v-else> - </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="noticeImg" label="组公告图片">
                        <template #default="scope">
                            <img v-if="scope.row.noticeImg" :src="scope.row.noticeImg"
                                style="width: 60px;height: 60px;" />
                            <span v-else> - </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="inviteVerify" label="是否开启认证" width="100">
                        <template #default="scope">
                            <span v-if="scope.row.inviteVerify == 1">是</span>
                            <span v-else>否</span>

                        </template>
                    </el-table-column>
                    <el-table-column prop="status" label="状态" width="100" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.status == 1">正常</span>
                            <span v-else>禁用</span>
                            
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100" fixed="right">
                        <template #default="scope">
                            <el-button size="small" type="primary" @click="cyChange(scope.row)" style="margin: 5px;">群组成员</el-button>
                            <el-button size="small" type="primary" @click="sqChange(scope.row)" style="margin: 5px;">入群申请</el-button>
                            <el-button size="small" type="primary" @click="ltChange(scope.row)" style="margin: 5px;">聊天记录</el-button>
                        </template>
                    </el-table-column>
				</el-table>
				<div class="user-detail-page__pagination">
					<el-pagination @size-change="handleSizeChangeLtjl" @current-change="handleCurrentChangeLtjl"
						:page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit" :current-page="dataContainer.page"
						layout="total,sizes, prev, pager, next,jumper" :total="dataContainer.qunzuData.total">
					</el-pagination>
				</div>
                </div>
            </el-tab-pane>
            
            <el-tab-pane label="好友请求" name="nine">
                <div class="nf-table-card user-detail-page__tab-table">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.tousuData.records">
					<el-table-column prop="requestId" label="编号" width="100"></el-table-column>
                    <el-table-column prop="userName" label="用户昵称">
                        <template #default="scope">
                            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.senderUserId)">
                                {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="avatar" label="用户图头">
                        <template #default="scope">
                            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                        </template>
                    </el-table-column>
					<el-table-column prop="message" label="留言">
					</el-table-column>
                    <el-table-column prop="createTime" label="申请时间">
					</el-table-column>
                    <el-table-column prop="status" label="状态" width="100" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.status == 0">待审核</span>
                            <span v-if="scope.row.status == 1">通过</span>
                            <span v-if="scope.row.status == 2">拒绝</span>
                        </template>
                    </el-table-column>
				</el-table>
				<div class="user-detail-page__pagination">
					<el-pagination @size-change="handleSizeChangeWdxx" @current-change="handleCurrentChangeWdxx"
						:page-sizes="[10, 20, 30, 40]" :page-size="dataContainer.limit" :current-page="dataContainer.page"
						layout="total, sizes, prev, pager, next, jumper" :total="dataContainer.tousuData.total">
					</el-pagination>
				</div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="聊天记录" name="ten">
                <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.liaotianData.records">
					<el-table-column prop="id" label="编号" width="100">
                        <template #default="scope">
                            {{ scope.$index +1 }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="senderUserName" label="用户昵称">
                        <template #default="scope">
                            <div v-if="scope.row.senderUserName" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.senderUserId)">
                                {{ scope.row.senderUserName ? scope.row.senderUserName : '未绑定' }}
                            </div>
                            <div v-if="scope.row.receiverUserName" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.receiverUserId)">
                                {{ scope.row.receiverUserName ? scope.row.receiverUserName : '未绑定' }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="senderAvatar" label="用户图头">
                        <template #default="scope">
                            <img v-if="scope.row.senderAvatar" :src="scope.row.senderAvatar" style="width: 60px;height: 60px;margin: 3px;" />
                            <img v-if="scope.row.receiverAvatar" :src="scope.row.receiverAvatar" style="width: 60px;height: 60px;margin: 3px;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="messageCount" label="信息数量" width="180"></el-table-column>
                    
                    <el-table-column prop="lastMessageTime" label="创建时间" width="180"></el-table-column>
					<el-table-column fixed='right' label="操作" width="150">
						<template #default="scope">
							<el-button type="primary" @click="complainDetails(scope.row)">聊天内容
							</el-button>
						</template>
					</el-table-column>
				</el-table>
				<div class="user-detail-page__pagination">
					<el-pagination @size-change="handleSizeChangeLtjl" @current-change="handleCurrentChangeLtjl"
						:page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit" :current-page="dataContainer.page"
						layout="total,sizes, prev, pager, next,jumper" :total="dataContainer.liaotianData.total">
					</el-pagination>
				</div>
            </el-tab-pane> -->
        </el-tabs>
        <!-- 弹框 -->
        <!-- 修改用户信息 弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleU" title="用户信息" width="80%" center>
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">微信名称：</span>
                <el-input style="width:50%;" v-model="dataContainer.userNameU" type="text"
                    placeholder="请输入微信名称"></el-input>
            </div>
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">手机号：</span>
                <el-input style="width:50%;" v-model="dataContainer.phoneU" type="text" placeholder="请输入手机号"></el-input>
            </div>
            <!-- <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">邀请人邀请码：</span>
                <el-input style="width:50%;" v-model="dataContainer.inviterCodeU" type="text"
                    placeholder="请输入邀请人邀请码"></el-input>
            </div> -->
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">邀请码：</span>
                <el-input style="width:50%;" v-model="dataContainer.invitationCodeU" type="text"
                    placeholder="请输入邀请码"></el-input>
            </div>
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">年龄：</span>
                <el-input style="width:50%;" v-model="dataContainer.ageU" type="number" :min="0" :controls="false"
                    placeholder="请输入年龄"></el-input>
            </div>
            <!-- <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">性别：</span>
                <el-radio-group v-model="dataContainer.sexU" class="ml-4">
                    <el-radio :label="1" size="large">男</el-radio>
                    <el-radio :label="2" size="large">女</el-radio>
                </el-radio-group>
            </div> -->
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">佣金比例：</span>
                <el-input style="width:50%;" v-model="dataContainer.zhiRateU" type="number" :min="0" :controls="false"
                    placeholder="输入佣金比例"></el-input>
            </div>
            <!-- 
				<div style="margin-bottom: 10px;">
					<span style="width: 200px;display: inline-block;text-align: right;">非直属佣金比例：</span>
					<el-input style="width:50%;" v-model="dataContainer.feiRateU" type="number" :min="0" :controls="false"
						placeholder="输入佣金比例"></el-input>
				</div> -->
            <!-- <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">个性签名</span>
                <el-input style="width:50%;" v-model="dataContainer.msgU" type="text" placeholder="请输入个性签名"></el-input>
            </div> -->
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">图像：</span>
                <div style="display: inline-block;">
                    <el-upload class="avatar-uploader" v-model="dataContainer.avatarU"
                        action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                        :on-success="handleAvatarSuccess1">
                        <img v-if="dataContainer.avatarU" :src="dataContainer.avatarU" class="avatar"
                            style="width: 148px;height: 148px;" />
                        <el-icon v-else style="font-size: 28px;color: #D9D9D9">
                            <!-- <Plus /> -->
                        </el-icon>

                        <!-- <i v-else class="el-icon-plus avatar-uploader-icon" style="font-size: 28px;color: #8c939d"></i> -->
                    </el-upload>
                </div>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleU = false" style="margin: 10px;">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 动态评论 弹框 -->
        <el-dialog v-model="dataContainer.dialogTableVisible2" title="动态评论" center width="80%">
            <el-table v-loading="dataContainer.tableDataLoading1" :data="dataContainer.pinglunDatas.records">
                <el-table-column prop="commentId" label="编号" width="80">
                </el-table-column>
                <el-table-column prop="nickName" label="用户昵称">
                    <template #default="scope">
                        <div style="color: var(--nf-brand);cursor: pointer;" @click="updates(scope.row.userId)">
                            {{ scope.row.nickName ? scope.row.nickName : '未绑定' }}
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="imageUrl" label="用户头像">
                    <template #default="scope">
                        <div v-if="scope.row.imageUrl == null || scope.row.imageUrl == ''">
                            暂无图片
                        </div>
                        <div v-else>
                            <el-popover placement="top-start" title="" trigger="hover">
                                <img style="width: 50px; height: 50px" :src="scope.row.imageUrl" alt=""
                                    slot="reference">
                                <img style="width: 200px; height: 200px" :src="scope.row.imageUrl" alt="">
                            </el-popover>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="content" label="信息内容" width="280">
                    <template #default="scope">
                        <div>{{ scope.row.content }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="150">
                </el-table-column>
            </el-table>
            <div class="user-detail-page__pagination">
                <el-pagination @size-change="handleSizeChangeDtpl" @current-change="handleCurrentChangeDtpl"
                    :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit1"
                    :current-page="dataContainer.page1" layout="total,sizes, prev, pager, next"
                    :total="dataContainer.pinglunDatas.total">
                </el-pagination>
            </div>
        </el-dialog>
        <!-- 聊天内容 -->
        <el-dialog title="聊天内容" v-model="dataContainer.dialogFormVisible4" center width="70%">
            <el-table v-loading="dataContainer.tableDataLoading1" :data="dataContainer.huihuaData.records">
                <el-table-column prop="id" label="编号" width="100"></el-table-column>
                <el-table-column prop="senderUserName" label="用户昵称">
                    <template #default="scope">
                        <span v-if="scope.row.senderUserName" style="color: #13ce66;cursor: pointer;"
                            @click="updates(scope.row.senderUserId)">
                            {{ scope.row.senderUserName ? scope.row.senderUserName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="senderAvatar" label="用户图头">
                    <template #default="scope">
                        <img v-if="scope.row.senderAvatar" :src="scope.row.senderAvatar"
                            style="width: 60px;height: 60px;" />
                    </template>
                </el-table-column>
                <el-table-column prop="type" label="内容类型">
                    <template #default="scope">
                        <span v-if="scope.row.type == 1">普通消息</span>
                        <span v-if="scope.row.type == 2">表情</span>
                        <span v-if="scope.row.type == 3">图片</span>
                        <span v-if="scope.row.type == 4">视频</span>
                    </template>
                </el-table-column>
                <el-table-column prop="content" label="聊天内容">
                    <template #default="scope">
                        <span v-if="scope.row.type == 1">{{ scope.row.content }}</span>
                        <span v-if="scope.row.type == 2">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                <template #reference>
                                    <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content" alt="">
                                </template>
                                <template #default style="width: 420px;">
                                    <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                                </template>
                            </el-popover>
                        </span>
                        <span v-if="scope.row.type == 3">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                <template #reference>
                                    <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content" alt="">
                                </template>
                                <template #default style="width: 420px;">
                                    <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                                </template>
                            </el-popover>
                        </span>
                        <span v-if="scope.row.type == 4">
                            <video style="width: 180px;height: 100px;" :src="scope.row.content" type="audio/wav"
                                controls="controls"></video>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
            </el-table>
            <div class="user-detail-page__pagination">
                <el-pagination @size-change="handleSizeChangeLtnr" @current-change="handleCurrentChangeLtnr"
                    :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit1"
                    :current-page="dataContainer.page1" layout="total,sizes, prev, pager, next"
                    :total="dataContainer.huihuaData.total">
                </el-pagination>
            </div>
        </el-dialog>
        <!-- 修改钱包  -->
        <el-dialog title="修改钱包" v-model="dataContainer.dialogFormVisible1" center>
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">修改类型：</span>
                <el-radio-group v-model="dataContainer.type">
                    <el-radio label="incr">添加</el-radio>
                    <el-radio label="decr">减少</el-radio>
                </el-radio-group>
            </div>
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">金额：</span>
                <el-input style="width:50%;" v-model="dataContainer.money" type="number" :min="0" :max="100"
                    :controls="false" placeholder="请输入金额"></el-input>
            </div>
            <template #footer>
                <el-button @click="dataContainer.dialogFormVisible1 = false">取 消</el-button>
                <el-button type="primary" @click="StairNoticeTo1()">确 定</el-button>
            </template>
        </el-dialog>
        <!-- 点赞列表 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleDz" title="点赞列表" width="80%" center>
            <el-table :data="dataContainer.tableDataDz" v-loading="dataContainer.loading" style="width: 100%">
                <el-table-column prop="userId" label="编号" width="100"></el-table-column>
                <el-table-column prop="userName" label="点赞用户">
                    <template #default="scope">
                        <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                            @click="updates(scope.row.userId)">
                            {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="avatar" label="用户图头">
                    <template #default="scope">
                        <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="点赞时间" width="180"></el-table-column>
            </el-table>
        </el-dialog>
        <!-- 评论列表 -->
        <el-dialog v-model="dataContainer.dialogFormVisiblePl" title="评论列表" width="80%" center>
            <el-table :data="dataContainer.tableDataPl" v-loading="dataContainer.loading" style="width: 100%">
                <el-table-column prop="userId" label="编号" width="100"></el-table-column>
                <el-table-column prop="userName" label="评论用户" width="160">
                    <template #default="scope">
                        <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                            @click="updates(scope.row.userId)">
                            {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="avatar" label="用户图头" width="100">
                    <template #default="scope">
                        <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                    </template>
                </el-table-column>
                <el-table-column prop="content" label="评论内容"></el-table-column>
                <el-table-column prop="createTime" label="评论时间" width="180"></el-table-column>
            </el-table>
        </el-dialog>
        <!--群成员 弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleCy" title="群成员列表" width="80%" center>
            <div style="margin: 10px;">
                <div style="display: inline-block;margin: 5px;">
                    <span>用户昵称：</span>
                    <el-input v-model="dataContainer.userNameC" placeholder="请输入用户昵称"
                        @keydown.enter.native="handleSearchCy" style="width: 150px;" />
                </div>
                <div style="display: inline-block;margin: 5px;">
                    <span>手机号：</span>
                    <el-input v-model="dataContainer.phoneC" placeholder="请输入手机号" @keydown.enter.native="handleSearchCy"
                        style="width: 150px;" />
                </div>
                <el-button size="small" type="primary" plain @click="handleSearchCy()">查询</el-button>
                <el-button size="small" type="primary" plain @click="clearSearchCy()">查询</el-button>
            </div>
            <el-table :data="dataContainer.tableDataCy" v-loading="dataContainer.loadingCy" style="width: 100%">
                <el-table-column prop="userId" label="编号" width="100"></el-table-column>
                <el-table-column prop="userName" label="用户昵称">
                    <template #default="scope">
                        <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                            @click="updates(scope.row.userId)">
                            {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="avatar" label="用户图头">
                    <template #default="scope">
                        <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="手机号"></el-table-column>

                <el-table-column prop="createTime" label="入群时间" width="180"></el-table-column>
            </el-table>
        </el-dialog>
        <!--申请信息 弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleSq" title="申请信息" width="80%" center>
            <div style="margin: 10px;">
                <div style="display: inline-block;margin: 5px;">
                    <span>用户昵称：</span>
                    <el-input v-model="dataContainer.userNameC" placeholder="请输入用户昵称"
                        @keydown.enter.native="handleSearchSq" style="width: 150px;" />
                </div>
                <div style="display: inline-block;margin: 5px;">
                    <span>手机号：</span>
                    <el-input v-model="dataContainer.phoneC" placeholder="请输入手机号" @keydown.enter.native="handleSearchSq"
                        style="width: 150px;" />
                </div>
                <el-button size="small" type="primary" plain @click="handleSearchSq()">查询</el-button>
                <el-button size="small" type="primary" plain @click="clearSearchSq()">查询</el-button>
            </div>
            <el-table :data="dataContainer.tableDataSp" v-loading="dataContainer.loadingCy" style="width: 100%">
                <el-table-column prop="userId" label="编号" width="100"></el-table-column>
                <el-table-column prop="userName" label="用户昵称">
                    <template #default="scope">
                        <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                            @click="updates(scope.row.userId)">
                            {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="avatar" label="用户图头">
                    <template #default="scope">
                        <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
            </el-table>
        </el-dialog>
        <!--聊天记录 弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleLt" title="聊天记录" width="80%" center>
            <div style="margin: 10px;">
                <div style="display: inline-block;margin: 5px;">
                    <span>用户昵称：</span>
                    <el-input v-model="dataContainer.userNameC" placeholder="请输入用户昵称"
                        @keydown.enter.native="handleSearchLt" style="width: 150px;" />
                </div>
                <div style="display: inline-block;margin: 5px;">
                    <span>手机号：</span>
                    <el-input v-model="dataContainer.phoneC" placeholder="请输入手机号" @keydown.enter.native="handleSearchLt"
                        style="width: 150px;" />
                </div>
                <div style="display: inline-block;margin: 5px;">
                    <span>内容类型：</span>
                    <el-select v-model="dataContainer.typeC" class="m-2" placeholder="请选择内容类型" style="width: 150px;"
                        @change="handleSearchLt">
                        <el-option v-for="(item, index) in dataContainer.typeData" :key="index" :label="item.label"
                            :value="item.value" />
                    </el-select>
                </div>
                <div style="display: inline-block;margin: 5px;">
                    <span>聊天内容：</span>
                    <el-input v-model="dataContainer.contentC" placeholder="请输入聊天内容"
                        @keydown.enter.native="handleSearchLt" style="width: 150px;" />
                </div>

                <el-button size="small" type="primary" plain @click="handleSearchLt()">查询</el-button>
                <el-button size="small" type="primary" plain @click="clearSearchLt()">查询</el-button>
            </div>
            <el-table :data="dataContainer.tableDataLt" v-loading="dataContainer.loadingCy" style="width: 100%">
                <el-table-column prop="userId" label="编号" width="100"></el-table-column>
                <el-table-column prop="userName" label="用户昵称">
                    <template #default="scope">
                        <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                            @click="updates(scope.row.userId)">
                            {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="avatar" label="用户图头">
                    <template #default="scope">
                        <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                    </template>
                </el-table-column>
                <el-table-column prop="type" label="内容类型">
                    <template #default="scope">
                        <span v-if="scope.row.type == 1">普通消息</span>
                        <span v-if="scope.row.type == 2">表情</span>
                        <span v-if="scope.row.type == 3">图片</span>
                        <span v-if="scope.row.type == 4">视频</span>
                    </template>
                </el-table-column>
                <el-table-column prop="content" label="聊天内容">
                    <template #default="scope">
                        <span v-if="scope.row.type == 1">{{ scope.row.content }}</span>
                        <span v-if="scope.row.type == 2">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                <template #reference>
                                    <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content" alt="">
                                </template>
                                <template #default style="width: 420px;">
                                    <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                                </template>
                            </el-popover>
                        </span>
                        <span v-if="scope.row.type == 3">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                <template #reference>
                                    <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content" alt="">
                                </template>
                                <template #default style="width: 420px;">
                                    <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                                </template>
                            </el-popover>
                        </span>
                        <span v-if="scope.row.type == 4">
                            <video style="width: 180px;height: 100px;" :src="scope.row.content" type="audio/wav"
                                controls="controls"></video>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>
/**
 * 页面例子
 */
import {
    defineComponent,
    onBeforeUnmount,
    ref,
    reactive,
    getCurrentInstance,
    onActivated,
    onMounted,
} from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { Delete, User, Filter } from '@element-plus/icons-vue';
import http from '@/http/request.js';
import userApi from '@/http/user.js';

export default defineComponent({
    components: {
        SvgIcon,
        Delete,
        User,
        Filter,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const dataContainer = reactive({
            page: 1,
            limit: 10,
            page1: 1,
            limit1: 10,
            formLabelWidth: '200px',
            loading: false,
            userNameU: '',
            phoneU: '',
            inviterCodeU: '',
            invitationCodeU: '',
            avatarU: '',
            ageU: '',
            sexU: '',
            zhiRateU: '',
            feiRateU: '',
            msgU: '',

            form: {},
            nowTime: new Date(),
            nowTime_1: new Date().getTime(),
            activeName: 'first',
            tableData: {},
            tablemoney: {},
            dialogFormVisibleU: false,
            tableDataLoading: false,
            withdrawData: {},
            walletData: {},
            rechargeData: {},
            dongtaiData: {},
            pinglunDatas: {},
            tousuData: {},
            dizhiData: {},
            liaotianData: {},
            qunzuData: [],
            friendData: {},
            startTime: '',
            endTime: '',
            talkId: '',
            dialogFormVisible4: false,
            tableDataLoading1: false,
            dialogFormVisible1: false,
            dialogTableVisible2: false,
            chatConversationId: '',
            receiverUserId: '',
            senderUserId: '',
            huihuaData: {},
            type: '',
            money: '',
            dialogFormVisibleDz: false,
            dialogFormVisiblePl: false,
            tableDataDz: [],
            tableDataPl: [],
            dialogFormVisibleCy: false,
            dialogFormVisibleSq: false,
            dialogFormVisibleLt: false,
            loadingCy: false,
            pageC: 1,
            limitC: 10,
            userNameC: '',
            phoneC: '',
            typeC: '',
            contentC: '',
            tableDataSq: [],
            tableDataCy: [],
            loadingLt: [],
            typeData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '普通消息',
                    value: 1
                },
                {
                    label: '表情',
                    value: 2
                },
                {
                    label: '图片',
                    value: 3
                },
                {
                    label: '视频',
                    value: 4
                }
            ],
            id: '',
            stas: 1,
            classifys: 1,
            xinyongfenData: {},
            fabuData: {},
            jiedanData: {},
            sendData: {},
            autonymData: {},
            youhuiquanData: {},
            tableData2: {},
            tableData3: {},
            dizhiData: {},
            tableDataZxsc: {},
            dailituiguangData: {},
            baozhengjinData: {},



            complaintState: '',
            statesnum: [{
                label: '全部',
                value: ''
            },
            {
                label: '可申诉',
                value: 1
            },
            {
                label: '不可申诉',
                value: 2
            },
            {
                label: '审核中',
                value: 3
            },
            {
                label: '审核未通过',
                value: 4
            },
            {
                label: '审核已通过',
                value: 5
            },
            ],
            indentNumber: '',
            types: 1,
            userid1: '',
            userid2: '',
            youhunajuan: '',
            statusyouhunjuan: '',
            statusyouhunjuanoptions: [{
                value: 0,
                label: '正常'
            }, {
                value: 1,
                label: '已使用'
            }, {
                value: 2,
                label: '已失效'
            }],
            youhunajuanoptions: [{
                //优惠券类型 0全部可用 1即时代 2朋友代叫 3预约代
                value: 0,
                label: '全部可用'
            }, {
                value: 1,
                label: '即时代驾'
            }, {
                value: 2,
                label: '朋友代叫'
            }, {
                value: 3,
                label: '预约代驾'
            }, {
                value: 4,
                label: '包时代驾'
            }],
            flag: 'day',
            flags: [{
                value: 'day',
                label: '按天查询'
            }, {
                value: 'month',
                label: '按月查询'
            }, {
                value: 'year',
                label: '按年查询'
            }],
            info: getNowTime()



        });
        /**
         * 数据初始化
         */

        function initData() {
            let params = route.params;
            if (!params.sign) return;
            dataContainer.form = {
                id: params.sign,
            };
        }
        initData();

        function prev() {
            router.back()
        }
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '用户详情') {
                dataSelect();
            }
            if (event.srcElement.innerText == '钱包明细') {
                walletSelect();
            }
            if (event.srcElement.innerText == '提现记录') {
                withdrawSelect();
            }
            if (event.srcElement.innerText == '充值明细') {
                rechargeSelect();
            }
            if (event.srcElement.innerText == '我的消息') {
                tousuSelect();
            }
            if (event.srcElement.innerText == '团队收益明细') {
                dataContainer.classifys = 1
                dailituiguangSelect();
            }
            if (event.srcElement.innerText == '保证金管理') {
                baozhengjinSelect();
            }
            if (event.srcElement.innerText == '我的订单') {
                InformationSelect();
            }
            if (event.srcElement.innerText == '我的收藏') {
                InformationSelect1();
            }
            if (event.srcElement.innerText == '我收到的礼物') {
                sendSelect();
            }
            if (event.srcElement.innerText == '我的优惠券') {
                youhuiquanSelect();
            }
            if (event.srcElement.innerText == '我的购物车') {
                dataContainer.types = 1
                autonymSelect();
            }
            if (event.srcElement.innerText == '师傅的评价') {
                dataContainer.types = 2
                autonymSelect();
            }
            if (event.srcElement.innerText == '收益统计') {
                taskData();
            }
            if (event.srcElement.innerText == '我的地址') {
                dizhiSelect();
            }
            if (event.srcElement.innerText == '信用分明细') {
                xinyongfenSelect();
            }
            if (event.srcElement.innerText == '我的发票') {
                sfzxscSelect();
            }


            if (event.srcElement.innerText == '我的车辆') {
                dongtaiSelect();
            }
            // if (event.srcElement.innerText == '我的通讯录') {
            //     haoyouSelect();
            // }
            // if (event.srcElement.innerText == '我的群组') {
            //     qunzuSelect();
            // }


            // if (event.srcElement.innerText == '好友请求') {
            //     qingqiuSelect();
            // }
            // if (event.srcElement.innerText == '聊天记录') {
            //     liaotianSelect();
            // }

        }

        /** 获取用户详情数据 */
        function dataSelect() {
            let userId = route.query.userId
            http.get('admin/user/info', {
                params: {
                    userId: userId
                }
            }, {
            }).then((res) => {
                console.log('============', res)
                dataContainer.loading = false;
                if (res.code == 0) {
                    let returnData = res.data
                    dataContainer.tablemoney = returnData
                    dataContainer.tableData = returnData
                }
            })
        }
        console.log('========1111====', route.query.userId)
        dataSelect();
        /** 修改信息弹框 */
        function ageChangeU() {
            dataContainer.userNameU = dataContainer.tableData.userName
            dataContainer.phoneU = dataContainer.tableData.phone
            dataContainer.inviterCodeU = dataContainer.tableData.inviterUserCode
            dataContainer.invitationCodeU = dataContainer.tableData.invitationCode
            dataContainer.avatarU = dataContainer.tableData.avatar
            dataContainer.ageU = dataContainer.tableData.age
            dataContainer.sexU = dataContainer.tableData.sex
            dataContainer.zhiRateU = dataContainer.tableData.commissionRate
            dataContainer.feiRateU = dataContainer.tableData.feiRate
            dataContainer.msgU = dataContainer.tableData.signature
            dataContainer.dialogFormVisibleU = true
            console.log(dataContainer.avatarU)
        }
        /** 修改性别 */
        function StairNoticeToU() {
            let userId = route.query.userId
            http.post('admin/user/update', {

                userId: userId,
                inviterUserCode: dataContainer.inviterCodeU,
                avatar: dataContainer.avatarU,
                userName: dataContainer.userNameU,
                phone: dataContainer.phoneU,
                invitationCode: dataContainer.invitationCodeU,
                age: dataContainer.ageU,
                sex: dataContainer.sexU,
                commissionRate: dataContainer.zhiRateU,
                feiRate: dataContainer.feiRateU,
                msg: dataContainer.msgU,
            }, {}).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                if (res.code == 0) {
                    ElMessage({
                        message: '修改成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisibleU = false
                            dataSelect()
                        }
                    })
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'warning',
                        onClose: () => {
                            dataSelect()
                        }
                    })
                }
            })
        }
        /** 修改年龄 弹框 */
        function ageChange(userId, age) {

        }
        /** 更改状态 */
        function stateChange(val) {

            if (val.status == 1) {
                dataContainer.stas = 2

            } else {
                dataContainer.stas = 1

            }


            ElMessageBox.confirm(`确定要更改用户状态吗?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {

                http.post('adminerrand/updateStatus', {
                    userId: val.userId,
                    status: dataContainer.stas
                }, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then((res) => {
                    console.log(res)
                    dataContainer.loading = false;
                    if (res.code == 0) {
                        ElMessage({
                            message: '修改成功',
                            type: 'success',
                            onClose: () => {
                                dataSelect()
                            }
                        })
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'warning',
                            onClose: () => {
                                dataSelect()
                            }
                        })
                    }
                })
            })
        }
        function ageChangeYj(age, index) {

        }
        /** 修改自动回复 */
        function msgChange(userId, msg) {

        }
        /** 获取用户提现记录 */
        function withdrawSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            var datas = {
                userId: userId,
                page: dataContainer.page,
                limit: dataContainer.limit,
            }
            http.post('admin/withdrawRecord/page', {
                // params: {
                userId: userId,
                page: dataContainer.page,
                limit: dataContainer.limit,

                // }
            }
                , {}).then((res) => {
                    console.log(res)
                    dataContainer.tableDataLoading = false;
                    let returnData = res.data
                    dataContainer.withdrawData = returnData
                })
        }
        /** 获取钱包明细 */
        function walletSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/wallet/detail', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.walletData = returnData
            })
        }
        /** 获取充值明细 */
        function rechargeSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.post('admin/user/payDetails', {
                // params: {
                userId: userId,
                page: dataContainer.page,
                limit: dataContainer.limit,
                startTime: dataContainer.startTime,
                endTime: dataContainer.endTime,
                // }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                if (res.code == 0) {
                    let returnData = res.data
                    dataContainer.rechargeData = returnData
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'warning',
                        onClose: () => {
                            dataSelect()
                        }
                    })
                }
            })
        }
        // 信用分明细
        function xinyongfenSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/creditRecord/getCreditRecordList', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    riderUserName: '',
                    riderUserId: userId,
                    riderPhone: '',

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.xinyongfenData = returnData
            })
        }
        // 我的订单
        function InformationSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/order/page', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.fabuData = returnData
            })
        }
        // 我的收藏
        function InformationSelect1() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/userCollect/page', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.jiedanData = returnData
            })
        }
        // 我收到的礼物
        function sendSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/order/received', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    status: '',

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.sendData = returnData
            })
        }
        // 我的购物车
        function autonymSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            if (dataContainer.types == 1) {
                dataContainer.userid1 = userId
                dataContainer.userid2 = ''
            } else {
                dataContainer.userid1 = ''
                dataContainer.userid2 = userId
            }
            http.get('admin/cart/page', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    // satisfactionFlag: dataContainer.userid1,
                    // indentNumber: '',
                    // nickName: '',
                    // riderName: '',
                    // riderUserId: dataContainer.userid2,

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.autonymData = returnData
            })
        }
        // 我的优惠券
        function youhuiquanSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/userCoupon/list', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    couponType: dataContainer.youhunajuan,
                    status: dataContainer.statusyouhunjuan,

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.youhuiquanData = returnData
            })

        }
        //收益统计
        function taskData() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/couponUser/getMyCouponList', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    date: '',
                    dateType: '',

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.tableData2 = returnData
                dataContainer.tableData3 = returnData.pageUtils;
            })
        }
        // 订单分析选择日期
        function animeOrder() {
            taskData();
        }
        // 订单分析年月日
        function orderfenxi(value) {
            dataContainer.page = 1
            dataContainer.flag = value
            taskData();

        }
        // 我的地址
        function dizhiSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/address/getAddressList', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    date: '',
                    dateType: '',

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.dizhiData = returnData
            })
        }
        // 我的发票
        function sfzxscSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/invoice/adminGetIndentList', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.tableDataZxsc = returnData
            })
        }
        // 团队收益明细
        function dailituiguangSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.post('admin/referral/commission/page', {
                // params: {
                userId: userId,
                page: dataContainer.page,
                limit: dataContainer.limit,
                // classify: dataContainer.classifys

                // }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.dailituiguangData = returnData
            })
        }
        // 保证金管理
        function baozhengjinSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('adminerrand/selectCashDeposit', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    type: '',
                    phone: '',

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.baozhengjinData = returnData
            })
        }
        // 获取时间
        function getNowTime() {
            const now = new Date();
            const year = now.getFullYear(); //得到年份
            const month = now.getMonth(); //得到月份
            const date = now.getDate(); //得到日期
            const hh = now.getHours() < 10 ? "0" + now.getHours() : now.getHours();
            const mm = now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes();
            const ss = now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds();
            console.log(month)
            const month1 = month + 1;
            const month2 = month1.toString().padStart(2, "0");
            const date1 = date.toString().padStart(2, "0");
            var defaultDate = `${year}-${month2}-${date1}`;
            // var defaultDate = `${year}-${month}-${date} ${hh}:${mm}:${ss}`;

            return defaultDate;
        }
        getNowTime();








        /**  我的车辆*/
        function dongtaiSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            let pages = dataContainer.page - 1
            http.get('admin/car/selectCarListByUserId', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    userType: '',
                    userId: userId,
                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.dongtaiData = returnData
            })
        }
        /**  谈论列表*/
        function talkAbout(row) {
            dataContainer.page1 = 1
            dataContainer.talkId = row.talkId
            pinglunSelects(row.talkId)
            dataContainer.dialogTableVisible2 = true
        }
        /**  获取动态评论数据*/
        function pinglunSelects(talkId) {
            dataContainer.tableDataLoading1 = true
            http.get('gambit/selectCommentPage', {
                params: {
                    talkId: talkId,
                    page: dataContainer.page1 - 1,
                    size: dataContainer.limit1,
                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading1 = false;
                let returnData = res.data
                dataContainer.pinglunDatas = returnData
            })
        }
        /**  删除动态*/
        function advertdeletes(row) {
            ElMessageBox.confirm(`确定删除此条信息?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                http.post('gambit/deleteTalkById', {
                    params: {
                        talkId: row.talkId
                    }
                }, {}).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        ElMessage({
                            message: '操作成功',
                            type: 'success',
                            onClose: () => {
                                dongtaiSelect()
                            }
                        })
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'warning',
                            onClose: () => {
                            }
                        })
                    }
                })
            })
        }
        /**  删除评论*/
        function pldeletes(row) {
            ElMessageBox.confirm(`确定删除此条信息?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                http.post('gambit/deleteCommentById', {
                    params: {
                        commentId: row.commentId
                    }
                }, {}).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        ElMessage({
                            message: '操作成功',
                            type: 'success',
                            onClose: () => {
                                pinglunSelects(dataContainer.talkId)
                            }
                        })
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'warning',
                            onClose: () => {
                            }
                        })
                    }
                })
            })
        }
        /**  我的通讯录*/

        function haoyouSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/friend/page', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.friendData = returnData
            })
        }

        /**  我的群组*/
        function qunzuSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/group/getMyGroupList', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.qunzuData = returnData
            })
        }
        /** 群成员 弹框 */
        function cyChange(row) {
            dataContainer.id = row.id
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataSelectCy();
            dataContainer.dialogFormVisibleCy = true

        }
        /** 群成员 数据 */
        function dataSelectCy() {
            dataContainer.loadingCy = true
            http.get('admin/group/groupUserList', {
                params: {
                    groupId: dataContainer.id,
                    userName: dataContainer.userNameC,
                    phone: dataContainer.phoneC,
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loadingCy = false;
                dataContainer.tableDataCy = {}
                let returnData = res.data
                dataContainer.tableDataCy = returnData
            })
        }
        function handleSearchCy() {
            dataSelectCy();
        }
        function clearSearchCy() {
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataSelectCy();
        }
        /** 申请信息 弹框 */
        function sqChange(row) {
            dataContainer.id = row.id
            dataSelectSq();
            dataContainer.dialogFormVisibleSq = true

        }
        /** 申请信息 数据 */
        function dataSelectSq() {
            dataContainer.loadingCy = true
            http.get('admin/group/groupUserList', {
                params: {
                    groupId: dataContainer.id,
                    userName: dataContainer.userNameC,
                    phone: dataContainer.phoneC,
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loadingCy = false;
                dataContainer.tableDataSq = {}
                let returnData = res.data
                dataContainer.tableDataSq = returnData
            })
        }
        function handleSearchSq() {
            dataSelectSq();
        }
        function clearSearchSq() {
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''

            dataSelectSq();
        }

        /** 聊天记录 弹框 */
        function ltChange(row) {
            dataContainer.id = row.id
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataContainer.typeC = ''
            dataContainer.contentC = ''
            dataContainer.pageC = 1
            dataContainer.dialogFormVisibleLt = true
        }
        /** 聊天记录 数据 */
        function dataSelectLt() {
            dataContainer.loadingCy = true
            http.get('admin/history/page', {
                params: {
                    page: dataContainer.pageC,
                    limit: dataContainer.limitC,
                    groupId: dataContainer.id,
                    userName: dataContainer.userNameC,
                    phone: dataContainer.phoneC,
                    targetType: 2,
                    type: dataContainer.typeC,
                    content: dataContainer.contentC,
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loadingCy = false;
                dataContainer.tableDataLt = {}
                let returnData = res.data
                dataContainer.tableDataLt = returnData
            })
        }
        function handleSearchLt() {
            dataSelectLt();
        }
        function clearSearchLt() {
            dataContainer.pageC = 1
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataContainer.typeC = ''
            dataContainer.contentC = ''
            dataSelectLt();
        }

        /**  我的消息*/
        function tousuSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('message/selectMessageByUserId', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    state: '',
                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.tousuData = returnData
            })
        }

        /**  好友请求*/
        function qingqiuSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.get('admin/friendRequest/list', {
                params: {
                    userId: userId,
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.tousuData = returnData
            })
        }


        /**  聊天记录*/
        function liaotianSelect() {
            dataContainer.tableDataLoading = true
            let userId = route.query.userId
            http.post('admin/history/chatHistory', {
                // params: {
                page: dataContainer.page,
                limit: dataContainer.limit,
                userId: userId,
                // }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false;
                let returnData = res.data
                dataContainer.liaotianData = returnData
            })
        }
        /**  聊天内容*/
        function complainDetails(row) {
            dataContainer.page1 = 1
            dataContainer.dialogFormVisible4 = true
            dataContainer.receiverUserId = row.receiverUserId
            dataContainer.senderUserId = row.senderUserId
            neirongSelect()
        }
        /**  获取聊天内容列表*/
        function neirongSelect() {
            dataContainer.tableDataLoading1 = true
            http.post('admin/history/userChatHistory', {
                // params: {
                receiverUserId: dataContainer.receiverUserId,
                senderUserId: dataContainer.senderUserId,
                page: dataContainer.page1,
                limit: dataContainer.limit1,
                // }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading1 = false;
                let returnData = res.data
                dataContainer.huihuaData = returnData
            })
        }
        // 修改钱包
        function rechargenone() {
            dataContainer.type = ''
            dataContainer.money = ''
            dataContainer.dialogFormVisible1 = true
        }
        function StairNoticeTo1() {
            let userId = route.query.userId
            if (dataContainer.type == '') {
                ElMessage({
                    message: '请选择修改类型',
                    type: 'warning',
                    onClose: () => {
                    }
                })

                return
            }
            if (dataContainer.money == '') {
                ElMessage({
                    message: '请输入金额',
                    type: 'warning',
                    onClose: () => {
                    }
                })
                return
            }
            http.post('admin/user/updateMoney', {
                // params: {
                userId: userId,
                money: dataContainer.money,
                operation: dataContainer.type
                // }
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataSelect()
                            dataContainer.dialogFormVisible1 = false
                        }
                    })
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'warning',
                        onClose: () => {
                        }
                    })
                }
            })

        }
        /** 跳转用户详情 */
        function updates(userId, querys) {
            router.push({
                name: 'userDetail1',
                query: {
                    userId: userId,
                },
                params: {
                    sign: userId || new Date().getTime(),
                    querys,
                },
            });
        }
        /**  -------- */
        function timeDate2() {
            rechargeSelect()
        }
        function handleAvatarSuccess1(file, fileList) {
            ataContainer.avatarU = file.data.path
        }
        /** 点赞用户 弹框 */
        function dianzanChange(row) {

            dataContainer.tableDataDz = row.imPostsLikeDataList
            dataContainer.dialogFormVisibleDz = true

        }
        /** 评论用户 弹框 */
        function pinglunChange(row) {

            dataContainer.tableDataPl = row.imPostsCommentDataList
            dataContainer.dialogFormVisiblePl = true
        }
        /**  分页事件*/
        function handleSizeChangeQbmx(val) {
            dataContainer.limit = val
            walletSelect()
        }
        function handleCurrentChangeQbmx(val) {
            dataContainer.page = val
            walletSelect()
        }
        function handleSizeChangeTx(val) {
            dataContainer.limit = val
            withdrawSelect()
        }
        function handleCurrentChangeTx(val) {
            dataContainer.page = val
            withdrawSelect()
        }
        function handleSizeChangeCzmx(val) {
            dataContainer.limit = val
            rechargeSelect()
        }
        function handleCurrentChangeCzmx(val) {
            dataContainer.page = val
            rechargeSelect()
        }
        function handleSizeChangeDt(val) {
            dataContainer.limit = val
            dongtaiSelect()
        }
        function handleCurrentChangeDt(val) {
            dataContainer.page = val
            dongtaiSelect()
        }
        function handleSizeChangeDtpl(val) {
            dataContainer.limit1 = val
            pinglunSelects(dataContainer.talkId)
        }
        function handleCurrentChangeDtpl(val) {
            dataContainer.page1 = val
            pinglunSelects(dataContainer.talkId)
        }
        function handleSizeChangeWdxx(val) {
            dataContainer.limit = val
            tousuSelect()
        }
        function handleCurrentChangeWdxx(val) {
            dataContainer.page = val
            tousuSelect()
        }
        function handleSizeChangeHy(val) {
            dataContainer.limit = val
            haoyouSelect()
        }
        function handleCurrentChangeHy(val) {
            dataContainer.page = val
            haoyouSelect()
        }

        function handleSizeChangeLtjl(val) {
            dataContainer.limit = val
            liaotianSelect()
        }
        function handleCurrentChangeLtjl(val) {
            dataContainer.page = val
            liaotianSelect()
        }
        function handleSizeChangeLtnr(val) {
            dataContainer.limit1 = val
            neirongSelect(dataContainer.chatConversationId)
        }
        function handleCurrentChangeLtnr(val) {
            dataContainer.page1 = val
            neirongSelect(dataContainer.chatConversationId)
        }
        function isAuth(res) {
            return userApi.isAuth(res)
        }
        return {
            isAuth,
            dataContainer,
            initData,
            prev,
            handleClick,
            dataSelect,
            ageChangeU,
            StairNoticeToU,
            ageChange,
            stateChange,
            ageChangeYj,
            msgChange,
            withdrawSelect,
            walletSelect,
            rechargeSelect,
            timeDate2,
            dongtaiSelect,
            talkAbout,
            advertdeletes,
            pldeletes,
            pinglunSelects,
            handleAvatarSuccess1,
            tousuSelect,
            liaotianSelect,
            qunzuSelect,
            complainDetails,
            neirongSelect,
            rechargenone,
            StairNoticeTo1,
            qingqiuSelect,
            cyChange,
            sqChange,
            ltChange,
            dataSelectCy,
            handleSearchCy,
            clearSearchCy,
            dataSelectSq,
            handleSearchSq,
            clearSearchSq,
            dataSelectLt,
            handleSearchLt,
            clearSearchLt,
            haoyouSelect,
            xinyongfenSelect,
            InformationSelect,
            InformationSelect1,
            sendSelect,
            autonymSelect,
            youhuiquanSelect,
            taskData,
            dizhiSelect,
            sfzxscSelect,
            dailituiguangSelect,
            baozhengjinSelect,
            orderfenxi,
            animeOrder,
            getNowTime,


            handleSizeChangeQbmx,
            handleCurrentChangeQbmx,
            handleSizeChangeTx,
            handleCurrentChangeTx,
            handleSizeChangeCzmx,
            handleCurrentChangeCzmx,
            handleSizeChangeDt,
            handleCurrentChangeDt,
            handleSizeChangeDtpl,
            handleCurrentChangeDtpl,
            handleSizeChangeWdxx,
            handleCurrentChangeWdxx,
            handleSizeChangeLtjl,
            handleCurrentChangeLtjl,
            handleSizeChangeLtnr,
            handleCurrentChangeLtnr,
            dianzanChange,
            pinglunChange,
            updates,
            handleSizeChangeHy,
            handleCurrentChangeHy,


        };
    },
});
</script>

<style lang="scss" scoped>
.user-detail-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.user-detail-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.user-detail-page__back {
    display: inline-block;
    margin-bottom: var(--nf-space-xs);
    font-size: 13px;
    color: var(--nf-brand);
    text-decoration: none;
    cursor: pointer;

    &:hover {
        color: var(--nf-brand-deep);
    }
}

.user-detail-page__summary {
    margin-bottom: var(--nf-space-sm);
}

.user-detail-page__summary-body {
    display: flex;
    flex-wrap: wrap;
    gap: var(--nf-space-md);
}

.user-detail-page__summary-item {
    display: flex;
    flex-direction: column;
    gap: 4px;
    min-width: 120px;
}

.user-detail-page__summary-label {
    font-size: 12px;
    color: var(--nf-text-secondary);
}

.user-detail-page__summary-value {
    font-size: 14px;
    color: var(--nf-text-primary);
    font-weight: 500;
}

.user-detail-page__status--danger {
    color: #f56c6c;
}

.user-detail-page__tabs {
    margin-bottom: 0;

    :deep(.el-tabs__header) {
        margin-bottom: 0;
    }

    :deep(.el-tabs__item.is-active) {
        color: var(--nf-brand);
    }

    :deep(.el-tabs__active-bar) {
        background-color: var(--nf-brand);
    }
}

.user-detail-page__detail-card,
.user-detail-page__tab-table {
    margin-top: 0;
    margin-bottom: 0;
}

.user-detail-page__recharge-filter {
    margin-bottom: var(--nf-space-sm);
}

.user-detail-page__recharge-filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.user-detail-page__recharge-filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.user-detail-page__recharge-filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.user-detail-page__recharge-filter-input {
    width: 160px;
}

.user-detail-page__recharge-filter-actions {
    display: flex;
    align-items: center;
    gap: var(--nf-space-sm);
}

.user-detail-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.user-detail-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.detailtabel h2 {
    margin-top: 0;
}

.detailtabel .table_main table {
    width: 100%;
    border-collapse: collapse;
}

.detailtabel .table_main {
    border: 1px solid var(--nf-border);
    border-radius: var(--nf-radius-sm);
    overflow: hidden;
}

.detailtabel table tr {
    border-bottom: 1px solid var(--nf-border);
}

.detailtabel table tr th {
    background-color: var(--nf-sidebar-bg-alt, #f7f8fa);
    padding: 12px 16px;
    border-right: 1px solid var(--nf-border);
    border-bottom: 1px solid var(--nf-border);
    width: 120px;
    white-space: nowrap;
}

.detailtabel table tr td {
    padding: 12px 16px;
    border-right: 1px solid var(--nf-border);
    border-bottom: 1px solid var(--nf-border);
    background: var(--nf-card-bg);
    color: var(--nf-text-primary);
}

.detailtabel table th {
    color: var(--nf-text-primary);
    font-weight: 600;
    font-size: 13px;
    line-height: 1.5;
}

.border-rt {
    border-right: none !important;
}

.border-bt {
    border-bottom: none !important;
}

.el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    border-color: var(--nf-brand);
    width: 148px;
    height: 148px;
}

.el-upload:hover {
    border-color: var(--nf-brand);
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 148px;
    height: 148px;
    line-height: 148px;
    text-align: center;
}

.avatar {
    width: 148px;
    height: 148px;
    display: block;
}
</style>
