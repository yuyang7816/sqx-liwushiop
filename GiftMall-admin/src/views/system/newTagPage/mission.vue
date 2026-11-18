<template>
    <div class="mission-page">
        <div class="nf-page-hero mission-page__hero">
            <span class="nf-page-hero__badge">订单中心</span>
            <h2 class="nf-page-hero__title">订单列表与管理</h2>
            <p class="nf-page-hero__desc">查看与管理代驾订单，支持多条件筛选、派单转单、退款及订单状态操作</p>
        </div>

        <div class="nf-filter-card mission-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body mission-page__filter-body">
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">状态：</span>
                    <el-select v-model="dataContainer.indentState" placeholder="请选择状态"
                        class="mission-page__filter-input" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statusData" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">订单类型：</span>
                    <el-select v-model="dataContainer.indentType" placeholder="请选择订单类型"
                        class="mission-page__filter-input" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.homeData1" :key="index" :label="item.label"
                            :value="item.value" />
                    </el-select>
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">订单号：</span>
                    <el-input v-model="dataContainer.indentNumber" placeholder="请输入订单号"
                        @keydown.enter.native="handleSearch" class="mission-page__filter-input" />
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">下单手机号：</span>
                    <el-input v-model="dataContainer.shipUserPhone" placeholder="请输入下单手机号"
                        @keydown.enter.native="handleSearch" class="mission-page__filter-input" />
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">师傅昵称：</span>
                    <el-input v-model="dataContainer.riderNickName" placeholder="请输入师傅昵称"
                        @keydown.enter.native="handleSearch" class="mission-page__filter-input" />
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">师傅手机号：</span>
                    <el-input v-model="dataContainer.riderPhone" placeholder="请输入师傅手机号"
                        @keydown.enter.native="handleSearch" class="mission-page__filter-input" />
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">乘客电话：</span>
                    <el-input v-model="dataContainer.customPhone" placeholder="请输入乘客电话"
                        @keydown.enter.native="handleSearch" class="mission-page__filter-input" />
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">下单类型：</span>
                    <el-select v-model="dataContainer.addType" placeholder="请选择下单类型"
                        class="mission-page__filter-input" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statesnum2" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">价格计算方式：</span>
                    <el-select v-model="dataContainer.moneyType" placeholder="请选择价格计算方式"
                        class="mission-page__filter-input" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statesnum3" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">日期类型：</span>
                    <el-select v-model="dataContainer.flag" placeholder="请选择状态"
                        class="mission-page__filter-input" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.flags" :key="index" :label="item.label"
                            :value="item.value" />
                    </el-select>
                </div>
                <div class="mission-page__filter-item">
                    <span class="mission-page__filter-label">日期：</span>
                    <el-input v-model="dataContainer.info" placeholder="请输入位置"
                        @keydown.enter.native="handleSearch" class="mission-page__filter-input" />
                </div>
                <div class="mission-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">查询</el-button>
                    <el-button @click="clearSearch()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="mission-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="订单列表" name="first">
                <div class="nf-table-card mission-page__table">
                <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column fixed prop="indentId" label="编号" width="80">
                    </el-table-column>
                    <!-- <el-table-column prop="cityName" label="代理城市" v-if="isAuth('citys:shezhidaili')"></el-table-column> -->
                    <el-table-column prop="indentId" label="下单人昵称" width="120">
                        <template #default="scope">
                            <div class="mission-page__link" v-if="scope.row.addType != 3"
                                @click="updates(scope.row.userId)">
                                {{ scope.row.shipUserName }}
                            </div>
                            <div v-else>平台下单</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="shipUserPhone" label="下单手机号"></el-table-column>
                    <el-table-column prop="indentType" label="订单类型">
                        <template #default="scope">
                            <div v-if="scope.row.indentType == 1">代泊</div>
                            <div v-if="scope.row.indentType == 2">代取车</div>
                            <div v-if="scope.row.indentType == 3">接机</div>
                            <div v-if="scope.row.indentType == 4">送机</div>

                        </template>
                    </el-table-column>
                    <el-table-column prop="indentNumber" label="订单号"></el-table-column>
                    <el-table-column prop="shipAddressDetail" label="乘客信息" width="200">
                        <template #default="scope">
                            <div>
                                <div>乘客姓名: {{ scope.row.customName ? scope.row.customName : '-' }} </div>
                                <div>乘客电话: {{ scope.row.customPhone ? scope.row.customPhone : '-' }}</div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="shipAddressDetail" label="下单人信息" width="200">
                        <template #default="scope">
                            <div>
                                <div>下单人昵称: {{ scope.row.shipUserName ? scope.row.shipUserName : '-' }} </div>
                                <div>下单人电话: {{ scope.row.shipUserPhone ? scope.row.shipUserPhone : '-' }}</div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="deilveryAddressDetail" label="下单地址" width="200">
                        <template #default="scope">
                            <div>
                                {{ scope.row.shipProvince }}{{ scope.row.shipCity }}{{ scope.row.shipDistrict }}{{
                                    scope.row.shipAddress }}
                            </div>
                            <div>目的经纬度：{{ scope.row.shipAddressLatitude }} {{ scope.row.shipAddressLongitude }}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="deilveryAddressDetail" label="目的地址" width="200">
                        <template #default="scope">
                            <div>
                                {{ scope.row.deliveryProvince }}{{ scope.row.deliveryCity }}{{
                                    scope.row.deliveryDistrict }}{{ scope.row.deliveryAddress }}
                            </div>
                            <div>目的经纬度：{{ scope.row.deliveryAddressLatitude }} {{ scope.row.deliveryAddressLongitude }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="shipAddressDetail" label="师傅信息" width="200">
                        <template #default="scope">
                            <div>
                                <div>师傅姓名: {{ scope.row.riderNickName ? scope.row.riderNickName : '-' }} </div>
                                <div>师傅电话: {{ scope.row.riderPhone ? scope.row.riderPhone : '-' }}</div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="voiceIntroduce" label="语音" width="350">
                        <template #default="scope">
                            <div v-if="scope.row.recordings">
                                <el-button size="small" type="primary" plain @click="audicBtn(scope.row.recordings)"
                                    style="margin: 3px;">查看音频</el-button>
                            </div>
                            <span v-else>暂无语音</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="distance" label="预估总路程(公里)"></el-table-column>
                    <el-table-column prop="indentBasicsMoney" label="基础价格">
                    </el-table-column>
                    <el-table-column prop="userScaleMoney" label="上级抽佣金额">
                        <template #default="scope">
                            <div style="color: #f56c6c;cursor: pointer;" @click="updates(scope.row.userScaleUserId)">
                                {{ scope.row.userScaleMoney ? scope.row.userScaleMoney : '0' }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="userTwoMoney" label="上上级抽佣金额">
                        <template #default="scope">
                            <div style="color: #f56c6c;cursor: pointer;" @click="updates(scope.row.userTwoUserId)">
                                {{ scope.row.userTwoMoney ? scope.row.userTwoMoney : '0' }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="platformMoney" label="平台收入">
                    </el-table-column>
                    <el-table-column prop="riderRewardMoney" label="师傅奖励金额">
                    </el-table-column>
                    <el-table-column prop="riderMoney" label="师傅收入">
                    </el-table-column>
                    <el-table-column prop="pingRate" label="平台分成比例">
                    </el-table-column>
                    <!-- <el-table-column prop="agentMoney" label="代理收入金额" v-if="isAuth('citys:shezhidaili')">
			</el-table-column> -->
                    <el-table-column prop="couponMoney" label="优惠券抵扣金额">
                    </el-table-column>
                    <el-table-column prop="indentMoney" label="支付金额">
                        <template #default="scope">
                            <div style="color: #f56c6c;">
                                {{ scope.row.indentMoney ? scope.row.indentMoney : '0' }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="refundMoney" label="退款金额">
                        <template #default="scope">
                            <div style="color: #f56c6c;">
                                {{ scope.row.refundMoney ? scope.row.refundMoney : '0' }}
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="riderFine" label="师傅取消订单罚金">
                    </el-table-column>
                    <el-table-column prop="hopeTime" label="预约时间" width="160">
                    </el-table-column>
                    <el-table-column prop="remarks" label="备注" width="200">
                        <template #default="scope">
                            <span>{{ scope.row.remarks }}</span>
                            <!-- <el-button size="mini" type="primary" plain
						v-if="scope.row.state!=5&&scope.row.state!=3&&scope.row.state!=4" @click="updateBz(scope.row)"
						style="margin:5px;">修改</el-button> -->
                        </template>
                    </el-table-column>
                    <el-table-column prop="modeOfPayment" label="支付方式">
                        <template #default="scope">
                            <div v-if="scope.row.modeOfPayment == 1">微信</div>
                            <div v-else-if="scope.row.modeOfPayment == 2">支付宝</div>
                            <div v-else-if="scope.row.modeOfPayment == 0">余额</div>
                            <div v-else-if="scope.row.modeOfPayment == 4">线下</div>
                            <div v-else>-</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="payUser" label="付款对象">
                        <template #default="scope">
                            <div v-if="scope.row.payUser == 1">乘车人</div>
                            <div v-if="scope.row.payUser == 0">下单人</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="addType" label="下单类型">
                        <template #default="scope">
                            <div v-if="scope.row.addType == 1">用户下单</div>
                            <div v-if="scope.row.addType == 2">司机下单</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="moneyType" label="价格计算方式">
                        <template #default="scope">
                            <div v-if="scope.row.moneyType == 1">包时订单</div>
                            <div v-if="scope.row.moneyType == 2">按时长收费</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="timeNum" label="时间数" width="160">
                    </el-table-column>
                    <el-table-column prop="startWaitTime" label="开始等待时间" width="160">
                    </el-table-column>
                    <el-table-column prop="endWaitTime" label="结束等待时间" width="160">
                    </el-table-column>
                    <el-table-column prop="waitTime" label="等待总时长(分钟)" width="160">
                    </el-table-column>
                    <el-table-column prop="freeWaitTime" label="免费等待时长" width="160">
                    </el-table-column>
                    <el-table-column prop="waitMoney" label="等待总费用" width="160">
                    </el-table-column>
                    <el-table-column prop="flightNumber" label="航班号" width="160">
                    </el-table-column>
                    <el-table-column prop="flightDetails" label="航班详情" width="250">
                        <template #default="scope">
                            <span v-if="scope.row.flightDetails">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <div
                                            style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;">
                                            <div>航班号：{{ scope.row.flightDetails.FLIGHT_ID }}</div>
                                            <div>航空公司：{{ scope.row.flightDetails.FLIGHT_AIRWAYS_CH }}</div>
                                            <div>起飞机场名称：{{ scope.row.flightDetails.START_AIRPORT_CH }}</div>
                                            <div>机场代码：{{ scope.row.flightDetails.START_AIRPORT_EN }}</div>
                                            <div>出发城市：{{ scope.row.flightDetails.START_CITY }}</div>
                                            <div>起飞时间：{{ scope.row.flightDetails.START_TIME }}</div>
                                            <div>实际起飞时间：{{ scope.row.flightDetails.ACTUAL_START_TIME }}</div>
                                            <div>起飞日期：{{ scope.row.flightDetails.START_DATE }}</div>
                                            <div>航站楼：{{ scope.row.flightDetails.START_TERMINAL_EN }}</div>
                                            <div>到达机场名称：{{ scope.row.flightDetails.END_AIRPORT_CH }}</div>
                                            <div>到达机场代码：{{ scope.row.flightDetails.END_AIRPORT_EN }}</div>
                                            <div>到达城市：{{ scope.row.flightDetails.END_CITY }}</div>
                                            <div>到达时间：{{ scope.row.flightDetails.END_TIME }}</div>
                                            <div>实际到达时间：{{ scope.row.flightDetails.ACTUAL_END_TIME }}</div>
                                            <div>到达日期：{{ scope.row.flightDetails.END_DATE }}</div>
                                        </div>
                                    </template>
                                    <template #default style="width: 420px;">
                                        <div>航班号：{{ scope.row.flightDetails.FLIGHT_ID }}</div>
                                        <div>航空公司：{{ scope.row.flightDetails.FLIGHT_AIRWAYS_CH }}</div>
                                        <div>起飞机场名称：{{ scope.row.flightDetails.START_AIRPORT_CH }}</div>
                                        <div>机场代码：{{ scope.row.flightDetails.START_AIRPORT_EN }}</div>
                                        <div>出发城市：{{ scope.row.flightDetails.START_CITY }}</div>
                                        <div>起飞时间：{{ scope.row.flightDetails.START_TIME }}</div>
                                        <div>实际起飞时间：{{ scope.row.flightDetails.ACTUAL_START_TIME }}</div>
                                        <div>起飞日期：{{ scope.row.flightDetails.START_DATE }}</div>
                                        <div>航站楼：{{ scope.row.flightDetails.START_TERMINAL_EN }}</div>
                                        <div>到达机场名称：{{ scope.row.flightDetails.END_AIRPORT_CH }}</div>
                                        <div>到达机场代码：{{ scope.row.flightDetails.END_AIRPORT_EN }}</div>
                                        <div>到达城市：{{ scope.row.flightDetails.END_CITY }}</div>
                                        <div>到达时间：{{ scope.row.flightDetails.END_TIME }}</div>
                                        <div>实际到达时间：{{ scope.row.flightDetails.ACTUAL_END_TIME }}</div>
                                        <div>到达日期：{{ scope.row.flightDetails.END_DATE }}</div>
                                    </template>
                                </el-popover>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="flightDate" label="航班日期" width="160">
                    </el-table-column>
                    <el-table-column prop="estimateMoneyRange" label="最低-最高起价" width="160">
                    </el-table-column>
                    <el-table-column prop="parkingFee" label="停车费" width="160">
                    </el-table-column>
                    <el-table-column prop="receivingTime" label="接单时间" width="160">
                    </el-table-column>
                    <el-table-column prop="finishTime" label="完成时间" width="160">
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="160">
                    </el-table-column>
                    <el-table-column prop="indentState" label="状态" fixed="right">
                        <template #default="scope">
                            <div v-if="scope.row.indentState == 0">待接单</div>
                            <div v-if="scope.row.indentState == 1">已接单</div>
                            <div v-if="scope.row.indentState == 2">进行中</div>
                            <div v-if="scope.row.indentState == 3">待支付</div>
                            <div v-if="scope.row.indentState == 4">已完成</div>
                            <div v-if="scope.row.indentState == 5">已取消</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="200" fixed="right">
                        <template #default="scope">
                            <!-- <el-button size="small" type="primary" @click="dianzanChange(scope.row)"
                        style="margin: 5px;">点赞列表</el-button>
                    <el-button size="small" type="primary" @click="pinglunChange(scope.row)"
                        style="margin: 5px;">评论列表</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                        style="margin: 5px;">删除</el-button> -->
                            <el-button size="small" type="primary" plain v-if="scope.row.indentState == 0"
                                @click="paidan(scope.row, 1)" :disabled="!isAuth('mission:add')"
                                style="margin: 3px;">派单</el-button>
                            <el-button size="small" type="primary" plain
                                v-if="scope.row.indentState == 1 || scope.row.indentState == 2"
                                @click="paidan(scope.row, 2)" style="margin: 3px;">转单</el-button>
                            <el-button size="small" type="warning" plain @click="dituguihua(scope.row)"
                                style="margin: 3px;">线路规划</el-button>
                            <el-button size="small" type="primary" v-if="scope.row.indentState == 1"
                                @click="zhuandan(scope.row)" :disabled="!isAuth('mission:kaishi')"
                                style="margin: 3px;">开始服务</el-button>
                            <el-button size="small" type="primary" v-if="scope.row.indentState == 2"
                                @click="wancheng(scope.row)" :disabled="!isAuth('mission:wancheng')"
                                style="margin: 3px;">师傅完成</el-button>
                            <el-button size="small" type="info" v-if="Number(scope.row.indentState) == 1"
                                style="margin: 3px;" :disabled="!isAuth('mission:quxiao')"
                                @click="compile(scope.row)">师傅取消订单
                            </el-button>
                            <el-button size="small" type="primary" @click="jiashiBtn(scope.row)"
                                style="margin: 3px;">加时记录</el-button>
                            <el-button size="small" type="warning"
                                v-if="scope.row.indentState != 4 && scope.row.indentState != 5 && scope.row.riderUserId"
                                @click="wanchengOr(scope.row)" style="margin: 3px;">完成订单</el-button>
                            <el-button size="small" type="primary" plain @click="audicBtn(scope.row.recordings)"
                                style="margin: 3px;">查看音频</el-button>
                            <el-button style="margin:3px;" size="small" type="warning" @click="tuikuanBtn(scope.row)"
                                :disabled="!isAuth('mission:quxiao')"
                                v-if="scope.row.indentState != 5 && scope.row.indentState != 3">退全款
                            </el-button>
                            <el-button style="margin:3px;" size="small" type="warning" @click="tuikuanBtnBf(scope.row)"
                                :disabled="!isAuth('mission:quxiao')"
                                v-if="scope.row.indentState != 5 && scope.row.indentState != 3">部分退款
                            </el-button>
                            <el-button style="margin:5px;" size="small" type="info"
                                :disabled="!isAuth('mission:quxiao')"
                                v-if="scope.row.indentState != 5 && scope.row.indentState != 4"
                                @click="quxiaoBtn(scope.row)" plain>取消订单
                            </el-button>

                        </template>
                    </el-table-column>
                </el-table>
                <div class="mission-page__pagination">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.tableData.total">
                    </el-pagination>
                </div>
                </div>
            </el-tab-pane>
        </el-tabs>

            <!-- 师傅列表 -->
            <el-dialog v-model="dataContainer.dialogFormVisible" title="师傅列表" width="80%" center>
                <div style="margin:2% 0;display: inline-block;">
                    <span>手机号:</span>
                    <el-input style="width: 150px;" @keydown.enter.native="selectU" clearable placeholder="请输入手机号"
                        v-model="dataContainer.phoneU"></el-input>
                </div>
                <div style="margin:2% 0;display: inline-block;">
                    <span>昵称:</span>
                    <el-input style="width: 150px;" @keydown.enter.native="selectU" clearable placeholder="请输入昵称"
                        v-model="dataContainer.campus"></el-input>
                </div>
                <div style="display: inline-block;">
                    <el-button style="margin-left:15px;" size="small" type="primary" icon="document" @click="selectU">查询
                    </el-button>
                    <el-button style="margin-left:15px;" size="small" type="primary" icon="document" @click="cleansU">重置
                    </el-button>
                </div>
                <el-table :data="dataContainer.userData" v-loading="dataContainer.tableDataLoadingU"
                    style="width: 100%">

                    <el-table-column prop="userId" label="编号" width="80">
                    </el-table-column>
                    <el-table-column prop="nickName" label="用户昵称" width="120">
                        <template #default="scope">
                            　<span class="mission-page__link" @click="updates(scope.row.userId)"
                                v-text="scope.row.nickName"></span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="avatar" label="用户头像" width="100">
                        <template #default="scope">
                            　　<img v-if="scope.row.avatar && scope.row.avatar != ''" :src="scope.row.avatar" width="40"
                                height="40" />
                            <span v-else>暂无图片</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="phone" label="手机号">
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="180">
                    </el-table-column>
                    <el-table-column label="操作" prop="id" width="100" fixed='right' align="center">
                        <template #default="scope">
                            <el-button size="small" type="primary" @click="userBtns(scope.row)">确认
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>
            <!-- 评论列表 -->
            <el-dialog v-model="dataContainer.dialogFormVisiblePl" title="评论列表" width="80%" center>
                <div style="text-align: right;margin-right: 36px;">
                    <el-button size="small" type="primary" @click="jaishiAdd()">添加
                    </el-button>
                </div>
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
            <!-- 添加/修改弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">名称：</span>
                    <el-input style="width:50%;" v-model="dataContainer.name" type="text"
                        placeholder="请输入名称"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">链接：</span>
                    <el-input style="width:50%;" v-model="dataContainer.url" type="text" placeholder="请输入链接"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">描述：</span>
                    <el-input style="width:50%;" v-model="dataContainer.description" type="text"
                        placeholder="请输入描述"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">排序：</span>
                    <el-input style="width:50%;" v-model="dataContainer.sort" type="text"
                        placeholder="请输入排序"></el-input>
                </div>

                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">图像：</span>
                    <div style="display: inline-block;">
                        <el-upload class="avatar-uploader" v-model="dataContainer.imageUrl"
                            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess1">
                            <img v-if="dataContainer.imageUrl" :src="dataContainer.imageUrl" class="avatar"
                                style="width: 148px;height: 148px;" />
                            <SvgIcon v-else style="width:23px;height:23px;color: #efefef;" name="svg:plus.svg">
                            </SvgIcon>
                            <!-- <Plus v-else style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"/> -->
                            <!-- <Edit style="width: 1em; height: 1em; margin-right: 8px" /> -->

                        </el-upload>
                    </div>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleU = false"
                            style="margin: 10px;">取消</el-button>
                        <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
                    </span>
                </template>
            </el-dialog>
            <!-- 订单语音 -->
            <el-dialog title="订单语音" v-model="dataContainer.dialogFormVisibleAudic" center width="80%">
                <div v-for="(item, index) in dataContainer.recordings" :key="index"
                    style="display: inline-block;margin: 6px 15px;">
                    <audio :id="'myAudio' + index" duration :src="item" type="audio/wav" controls
                        preload="metadata"></audio>

                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleAudic = false">取 消</el-button>
                    </span>
                </template>
            </el-dialog>
            <!-- 部分退款 -->
            <el-dialog title="部分退款" v-model="dataContainer.dialogFormVisibleBftk" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">订单总金额：</span>
                    <span style="color: brown;"> ¥ {{ dataContainer.xiangmujin }}</span>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">退款金额：</span>
                    <el-input style="width:50%;" v-model="dataContainer.content" type="number" placeholder="请输入备注">
                    </el-input>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleBftk = false">取 消</el-button>
                        <el-button type="primary" @click="replyNoticeTk()">确 定</el-button>
                    </span>
                </template>
            </el-dialog>
    </div>
</template>
<script>
/**
 * 页面例子
 */
import { defineComponent, reactive, toRef } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { messageSuccess, messageError } from '@/action/messagePrompt';
import { findTag, deleteTags } from '@/action/tagListTools';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import { Filter } from '@element-plus/icons-vue';
export default defineComponent({
    components: {
        SvgIcon,
        Filter,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const url = ''
        const dataContainer = reactive({
            loading: false,
            activeName: 'first',
            tableData: {},
            page: 1,
            limit: 10,
            classify: 1,
            dialogFormVisibleU: false,
            dialogFormVisibleDz: false,
            dialogFormVisiblePl: false,
            titles: '添加',
            id: 0,
            name: "",
            imageUrl: "",
            url: "",
            sort: 0,
            description: "",
            state: 0,
            routeUrl: '',
            active: 1,
            inactive: 2,
            tableDataDz: [],
            tableDataPl: [],
            userNameT: '',
            locationT: '',
            privacyT: '',
            statusData: [  //0 待接单 1 已接单 2 进行中 3 待支付 4 已完成 5 已取消
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '待接单',
                    value: 0
                },
                {
                    label: '已接单',
                    value: 1
                },
                {
                    label: '进行中',
                    value: 2
                },
                {
                    label: '待支付',
                    value: 3
                },
                {
                    label: '已完成',
                    value: 4
                },
                {
                    label: '已取消',
                    value: 5
                },
            ],
            typeIndex: '',
            ordersId: '',
            page1: 1,
            limit1: 10,
            campus: '',
            phoneU: '',
            laundryIdU: '',
            dialogFormVisible: false,
            tableDataLoadingU: false,
            userData: {},
            userDataJl: {},
            cityId: '',
            moneyType: '', //价格计算方式 1包时订单 2按时长收费
            addType: '', //下单类型 1:用户下单 2:司机下单
            statesnum2: [{
                value: '',
                label: '全部'
            },
            {
                value: 1,
                label: '用户下单'
            }, {
                value: 2,
                label: '司机下单'
            },
            {
                value: 3,
                label: '平台下单'
            },
            ],
            statesnum3: [{
                value: '',
                label: '全部'
            },
            {
                value: 1,
                label: '包时订单'
            }, {
                value: 2,
                label: '按时长收费'
            },
            ],
            dataList: [],
            dialogFormVisibleJsAdd: false,
            timeId: '',
            // ------
            dialogFormVisibleAdd: false,
            recordings: [],
            dialogFormVisibleAudic: false,

            zongjine: 0,
            dacefei: 0,
            xiangmujin: 0,
            dialogFormVisibleBftk: false,
            content: '',
            citys: [],
            citys2: [],
            homeData1: [{
                title: '全部',
                id: ''
            }, {
                title: '即时代驾',
                id: 1
            },
            {
                title: '朋友代叫',
                id: 2
            },
            {
                title: '预约代驾',
                id: 3
            },
            {
                title: '包时代驾',
                id: 4
            },
            ],
            statesnum2: [{
                value: '',
                label: '全部'
            },
            {
                value: 1,
                label: '用户下单'
            }, {
                value: 2,
                label: '司机下单'
            },
            {
                value: 3,
                label: '平台下单'
            },
            ],
            statesnum3: [{
                value: '',
                label: '全部'
            },
            {
                value: 1,
                label: '包时订单'
            }, {
                value: 2,
                label: '按时长收费'
            },
            ],
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
            flag: '',
            info: '',
            parentId: '',
            cityId: '',
            indentState: '',
            indentType: '',
            indentNumber: '',
            shipUserPhone: '',
            riderNickName: '',
            riderPhone: '',
            customPhone: '',
            addType: '',
            moneyType: '',








        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '订单列表') {

                dataSelect();
            }

        }
        /**
         * 搜索
         */
        function handleSearch() {
            dataContainer.page = 1
            dataSelect();
        }
        function clearSearch() {
            dataContainer.page = 1
            dataContainer.flag = ''
            dataContainer.info = ''
            dataContainer.parentId = ''
            dataContainer.cityId = ''
            dataContainer.indentState = ''
            dataContainer.indentType = ''
            dataContainer.indentNumber = ''
            dataContainer.shipUserPhone = ''
            dataContainer.riderNickName = ''
            dataContainer.riderPhone = ''
            dataContainer.customPhone = ''
            dataContainer.addType = ''
            dataContainer.moneyType = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.post('adminerrand/findAllIndent', {
                // params: {
                page: dataContainer.page,
                limit: dataContainer.limit,
                indentType: dataContainer.indentType,
                deliveryUserPhone: dataContainer.phoneT,
                indentState: dataContainer.indentState,
                date: dataContainer.info,
                dateType: dataContainer.flag,
                riderNickName: dataContainer.riderNickName,
                riderPhone: dataContainer.riderPhone,
                indentNumber: dataContainer.indentNumber,
                shipUserPhone: dataContainer.shipUserPhone,
                customPhone: dataContainer.customPhone,
                cityId: dataContainer.cityId,
                moneyType: dataContainer.moneyType,
                addType: dataContainer.addType,

                // userName: dataContainer.userNameT,
                // location: dataContainer.locationT,
                // privacy: dataContainer.privacyT,
                // }

                // classify: dataContainer.classify,
                // state: '',
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
                console.log(res)


                dataContainer.loading = false;
                dataContainer.tableData = {}
                let returnData = res.data
                dataContainer.tableData = returnData
                for (var i in dataContainer.tableData.records) {
                    if (dataContainer.tableData.records[i].flightDetails) {
                        var a = dataContainer.tableData.records[i].flightDetails.toString()
                        var b = JSON.parse(a)
                        dataContainer.tableData.records[i].flightDetails = b
                        console.log('key', b)
                    }

                }
            })
        }
        dataSelect();
        dataSelectCity();
        /**  分页事件*/
        function handleSizeChange(val) {
            dataContainer.limit = val
            dataSelect()
        }
        function handleCurrentChange(val) {
            dataContainer.page = val
            dataSelect()
        }
        function changeBtn(row) {
            http.post('admin/base/banner/updateState', {

                id: row.id,
                state: row.state,
            }, {}).then((res) => {
                console.log(res)
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
        }
        /**
         * 添加、修改弹框
         */
        function addChangeU(row) {
            if (row) {
                dataContainer.titles = '修改'
                dataContainer.id = row.id
                dataContainer.name = row.name
                dataContainer.imageUrl = row.imageUrl
                dataContainer.url = row.url
                dataContainer.sort = row.sort
                dataContainer.description = row.description
                dataContainer.state = row.state
            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.name = ''
                dataContainer.imageUrl = ''
                dataContainer.url = ''
                dataContainer.sort = ''
                dataContainer.description = ''
                dataContainer.state = ''
            }
            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.imageUrl = file.data.path
        }
        function StairNoticeToU() {

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/base/banner/save'
            } else {
                dataContainer.routeUrl = 'admin/base/banner/update'
            }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                name: dataContainer.name,
                imageUrl: dataContainer.imageUrl,
                classify: dataContainer.classify,
                url: dataContainer.url,
                sort: dataContainer.sort,
                description: dataContainer.description,
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
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
        /** 删除 */
        function handleDelete(data) {
            http.get('admin/posts/delete', {
                params: {
                    postsId: data.id,
                    userId: data.userId,
                }

            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    messageSuccess('删除成功');
                    dataSelect();
                } else {
                    messageError(res.msg);
                }
            })
        }
        /** 跳转用户详情 */
        function updates(userId, querys) {
            router.push({
                name: 'userDetail',
                query: {
                    userId: userId,
                },
                params: {
                    sign: userId || new Date().getTime(),
                    querys,
                },
            });
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
        // 派单弹框
        function paidan(row, index) {
            dataContainer.typeIndex = index
            dataContainer.ordersId = row.indentId
            dataContainer.page1 = 1
            dataContainer.campus = ''
            dataContainer.phoneU = ''
            dataContainer.laundryIdU = ''
            dataContainer.dialogFormVisible = true
            dataSelectUser()
        }
        // 获取数据列表
        function dataSelectUser() {
            dataContainer.tableDataLoadingU = true
            http.get('user/getRiderList', {
                params: {
                    page: dataContainer.page1,
                    limit: dataContainer.limit1,
                    nickName: dataContainer.campus,
                    phone: dataContainer.phoneU,

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoadingU = false;
                let returnData = res.data
                dataContainer.userData = returnData
            })


        }
        // 查询
        function selectU() {
            dataContainer.page = 1
            dataSelectUser()
        }
        // 重置
        function cleansU() {
            dataContainer.page1 = 1
            dataContainer.campus = ''
            dataContainer.phoneU = ''
            dataContainer.laundryIdU = ''
            dataSelectUser()
        }
        // 确认选择
        function userBtns(row) {
            var datas = {}
            datas.indentId = dataContainer.ordersId
            if (dataContainer.typeIndex == 1) {
                var url = 'adminerrand/distributionOrder'
                datas.userId = row.userId
            } else {
                var url = 'adminerrand/transferOfOrder'
                datas.riderUserId = row.userId
            }
            http.post(url, datas, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisible = false
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
        // 线路规划弹框
        function dituguihua(row) {
            const item = JSON.stringify(row)
            router.push({
                name: 'dituguihua',
                query: {
                    item: item,
                },
                // params: {
                //     sign: userId || new Date().getTime(),
                //     querys,
                // },
            });
        }
        // 开始服务
        function zhuandan(row) {
            http.post('adminerrand/riderStartDrive', {
                userId: row.riderUserId,
                indentId: row.indentId,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
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
                        }
                    })
                }
            })

        }
        // 师傅完成
        function wancheng(row) {
            http.post('adminerrand/riderDelivery', {
                userId: row.riderUserId,
                indentId: row.indentId,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
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
                        }
                    })
                }
            })
        }
        function compile(row) {
            http.post('adminerrand/riderCancelIndent', {
                userId: row.riderUserId,
                indentId: row.indentId,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
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
                        }
                    })
                }
            })
        }
        // 加时记录
        function jiashiBtn(row) {
            dataContainer.page1 = 1
            dataContainer.cityId = row.cityId
            dataContainer.ordersId = row.indentId
            dataSelectJs()
            dataContainer.dialogFormVisibleJs = true
        }
        function dataSelectJs() {
            dataContainer.tableDataLoadingU = true;
            http.get('admin/indentTimeRecord/getIndentTimeRecordList', {
                params: {
                    page: dataContainer.page1,
                    limit: dataContainer.limit1,
                    indentId: dataContainer.indentId,

                }
            }, {}).then((res) => {
                console.log(res)
                dataContainer.tableDataLoadingU = false;
                let returnData = res.data
                dataContainer.userDataJl = returnData
            })

        }
        function handleSizeChangeJl(val) {
            dataContainer.limit1 = val;
            dataSelectJs()
        }
        function handleCurrentChangeJl(val) {
            dataContainer.page1 = val;
            dataSelectJs()
        }
        // 添加加时
        function jaishiAdd() {

            http.get('admin/packageTime/getPackageTimeList', {
                params: {
                    page: 1,
                    limit: 30,
                    cityId: dataContainer.cityId,

                }
            }, {}).then((res) => {
                console.log(res)

                let returnData = res.data
                dataContainer.dataList = returnData
            })
            dataContainer.timeId = ''
            dataContainer.dialogFormVisibleJsAdd = true

        }
        // 确认添加
        function changeSh() {
            http.post('admin/indentTimeRecord/indentAddTime', {
                timeId: dataContainer.timeId,
                indentId: dataContainer.indentId,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    dataContainer.dialogFormVisibleJsAdd = false
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataSelectJs()
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
        // 删除加时
        function jaishiDel(row) {
            http.get('admin/indentTimeRecord/indentRemoveTime', {
                params: {
                    recordId: row.recordId,

                }
            }, {}).then((res) => {
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataSelectJs()
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
        // 完成订单
        function wanchengOr(row) {
            ElMessageBox.alert('确定完成订单?', '提示', {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                callback: () => {
                    http.get('indent/accomplishOrders', {
                        params: {
                            indentId: row.indentId,

                        }
                    }, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).then((res) => {
                        if (res.code == 0) {
                            ElMessage({
                                message: '操作成功',
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
                                }
                            })
                        }
                    })
                },
            })
        }
        // 音频弹框
        function audicBtn(recordings) {
            if (recordings) {
                dataContainer.recordings = recordings.split(',')
            } else {
                dataContainer.recordings = []
            }

            dataContainer.dialogFormVisibleAudic = true
        }
        // 退款
        function tuikuanBtn(row) {
            ElMessageBox.alert('确定退款吗?', '提示', {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                callback: () => {
                    http.post('adminerrand/adminRefundIndent', {
                        params: {
                            indentId: row.indentId,
                            refundMoney: row.indentMoney

                        }
                    }, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).then((res) => {
                        if (res.code == 0) {
                            ElMessage({
                                message: '操作成功',
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
                                }
                            })
                        }
                    })
                },
            })
        }
        // 部分退款弹框
        function tuikuanBtnBf(row) {
            dataContainer.content = ''
            dataContainer.ordersId = row.indentId
            dataContainer.zongjine = Number(row.indentMoney)
            dataContainer.dacefei = row.taxiMoney
            if (row.refundMoney) {
                dataContainer.xiangmujin = Number(row.indentMoney) - Number(row.refundMoney)
            } else {
                dataContainer.xiangmujin = Number(row.indentMoney)
            }

            dataContainer.dialogFormVisibleBftk = true
        }
        // 确定退款
        function replyNoticeTk(row) {
            if (dataContainer.content === '') {
                ElMessage({
                    message: '请输入退款金额',
                    type: 'warning',
                    onClose: () => {
                    }
                })
                return
            } else if (dataContainer.content <= 0 || dataContainer.content >= dataContainer.zongjine) {

                ElMessage({
                    message: '请输入有效的金额',
                    type: 'warning',
                    onClose: () => {
                    }
                })
                return
            }
            ElMessageBox.alert('确定退款吗?', '提示', {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                callback: () => {
                    http.post('adminerrand/adminRefundIndent', {
                        params: {
                            indentId: dataContainer.indentId,
                            refundMoney: dataContainer.indentMoney

                        }
                    }, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).then((res) => {
                        if (res.code == 0) {
                            ElMessage({
                                message: '操作成功',
                                type: 'success',
                                onClose: () => {
                                    dataSelect()
                                    dataContainer.dialogFormVisibleBftk = false
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
                },
            })
        }
        // 获取城市省份列表
        function dataSelectCity() {
            http.get('admin/cityInfo/getCityInfoList', {
                params: {
                    parentId: 0,
                    cityName: '',

                }
            }, {}).then((res) => {
                console.log(res)
                let returnData = res.data
                dataContainer.citys = returnData.records
            })

        }

        // 获取城市
        function dataSelectCity2() {
            http.get('admin/cityInfo/getCityInfoList', {
                params: {
                    parentId: dataContainer.parentId,
                    cityName: '',

                }
            }, {}).then((res) => {
                console.log(res)
                let returnData = res.data
                dataContainer.citys2 = returnData.records
            })

        }


        function isAuth(res) {
            return userApi.isAuth(res)
        }
        // 取消订单
        function quxiaoBtn(row) {
            ElMessageBox.alert('确定取消订单吗?', '提示', {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                callback: () => {
                    http.post('adminerrand/adminCancelIndent', {
                        // params: {
                        indentId: row.indentId,

                        // }
                    }, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).then((res) => {
                        if (res.code == 0) {
                            ElMessage({
                                message: '操作成功',
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
                                }
                            })
                        }
                    })
                },
            })
        }


        return {
            isAuth,
            dataContainer,
            addChangeU,
            handleClick,
            handleSearch,
            clearSearch,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            handleAvatarSuccess1,
            StairNoticeToU,
            handleDelete,
            updates,
            dianzanChange,
            pinglunChange,
            paidan,
            dataSelectUser,
            selectU,
            cleansU,
            userBtns,
            dituguihua,
            zhuandan,
            wancheng,
            compile,
            jiashiBtn,
            dataSelectJs,
            handleSizeChangeJl,
            handleCurrentChangeJl,
            jaishiAdd,
            changeSh,
            jaishiDel,
            wanchengOr,
            audicBtn,
            tuikuanBtn,
            tuikuanBtnBf,
            replyNoticeTk,
            dataSelectCity,
            dataSelectCity2,
            quxiaoBtn,

        };
    },
});
</script>



<style lang="scss" scoped>
.mission-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.mission-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.mission-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.mission-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.mission-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.mission-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.mission-page__filter-input {
    width: 150px;
}

.mission-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.mission-page__tabs {
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

.mission-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.mission-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.mission-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
