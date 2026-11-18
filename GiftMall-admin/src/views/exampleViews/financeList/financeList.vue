<template>
    <div class="finance-list-page">
        <div class="nf-page-hero finance-list-page__hero">
            <span class="nf-page-hero__badge">财务中心</span>
            <h2 class="nf-page-hero__title">提现与充值管理</h2>
            <p class="nf-page-hero__desc">管理用户提现审核、提现/充值统计与充值记录，支持多维度筛选查询</p>
        </div>

        <div class="nf-filter-card finance-list-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body finance-list-page__filter-body">
                <template v-if="dataContainer.activeName === 'first'">
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">用户昵称：</span>
                        <el-input v-model="dataContainer.userNameT" placeholder="请输入用户昵称"
                            @keydown.enter.native="handleSearch" class="finance-list-page__filter-input" />
                    </div>
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">状态：</span>
                        <el-select v-model="dataContainer.statusT" placeholder="请选择状态"
                            class="finance-list-page__filter-input" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.statusData" :key="index"
                                :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">提现方式：</span>
                        <el-select v-model="dataContainer.inviteVerifyT" placeholder="请选择提现方式"
                            class="finance-list-page__filter-input" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.inviteVerifyData" :key="index"
                                :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">开始时间：</span>
                        <el-date-picker v-model="dataContainer.startTime" type="datetime" placeholder="选择时间"
                            format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
                    </div>
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">结束时间：</span>
                        <el-date-picker v-model="dataContainer.endTime" type="datetime" placeholder="选择时间"
                            format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
                    </div>
                    <div class="finance-list-page__filter-actions">
                        <el-button type="primary" @click="handleSearch()">查询</el-button>
                        <el-button @click="clearSearch()">重置</el-button>
                    </div>
                </template>
                <template v-else-if="dataContainer.activeName === 'second' || dataContainer.activeName === 'three'">
                    <div class="finance-list-page__filter-item">
                        <el-select v-model="dataContainer.flag" class="finance-list-page__filter-input"
                            @change="orderfenxi()">
                            <el-option v-for="(item, index) in dataContainer.flags" :key="index"
                                :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="finance-list-page__filter-item">
                        <el-date-picker v-model="dataContainer.info" type="date" placeholder="选择时间"
                            format="YYYY-MM-DD" value-format="YYYY-MM-DD" @change="animeOrder()" />
                    </div>
                </template>
                <template v-else-if="dataContainer.activeName === 'four'">
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">用户昵称：</span>
                        <el-input v-model="dataContainer.userNameT" placeholder="请输入用户昵称"
                            @keydown.enter.native="handleSearchZf" class="finance-list-page__filter-input" />
                    </div>
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">状态：</span>
                        <el-select v-model="dataContainer.statusT" placeholder="请选择状态"
                            class="finance-list-page__filter-input" @change="handleSearchZf">
                            <el-option v-for="(item, index) in dataContainer.statusDataZ" :key="index"
                                :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">支付方式：</span>
                        <el-select v-model="dataContainer.inviteVerifyT" placeholder="请选择支付方式"
                            class="finance-list-page__filter-input" @change="handleSearchZf">
                            <el-option v-for="(item, index) in dataContainer.inviteVerifyDataZ" :key="index"
                                :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">开始时间：</span>
                        <el-date-picker v-model="dataContainer.startTime" type="date" placeholder="选择时间"
                            format="YYYY-MM-DD" date-format="MMM DD, YYYY" time-format="HH:mm" />
                    </div>
                    <div class="finance-list-page__filter-item">
                        <span class="finance-list-page__filter-label">结束时间：</span>
                        <el-date-picker v-model="dataContainer.endTime" type="date" placeholder="选择时间"
                            format="YYYY-MM-DD" date-format="MMM DD, YYYY" time-format="HH:mm" />
                    </div>
                    <div class="finance-list-page__filter-actions">
                        <el-button type="primary" @click="handleSearchZf()">查询</el-button>
                        <el-button @click="clearSearchZf()">重置</el-button>
                    </div>
                </template>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="finance-list-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="提现管理" name="first">
                <div class="nf-table-card finance-list-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column fixed prop="id" label="编号" width="80">
                        </el-table-column>
                        <el-table-column prop="userName" label="提现用户" width="150">
                            <template #default="scope">
                                <span class="finance-list-page__link" @click="updates(scope.row.userId)">
                                    {{ scope.row.userName ? scope.row.userName : '-' }}
                                </span>
                            </template>
                        </el-table-column>
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
                                    class="finance-list-page__qrcode-wrap">
                                    <el-popover placement="top-start" title="" trigger="hover"
                                        popper-style="width:420px;">
                                        <template #reference>
                                            <img class="finance-list-page__qrcode-thumb"
                                                :src="scope.row.accountNumber" alt="">
                                        </template>
                                        <template #default>
                                            <img class="finance-list-page__qrcode-preview"
                                                :src="scope.row.accountNumber" alt="">
                                        </template>
                                    </el-popover>
                                </div>
                                <div v-else>暂无图片</div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="accountType" label="提现方式" width="100">
                            <template #default="scope">
                                <span v-if="scope.row.accountType == 1">支付宝</span>
                                <span v-else-if="scope.row.accountType == 2">微信</span>
                                <span v-else>暂无数据</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="amount" label="提现金额" width="100">
                        </el-table-column>
                        <el-table-column prop="serviceFee" label="手续费" width="100">
                        </el-table-column>
                        <el-table-column prop="actualAmount" label="实际到账金额" width="100">
                        </el-table-column>
                        <el-table-column prop="status" label="状态" width="100" fixed="right">
                            <template #default="scope">
                                <span class="finance-list-page__link" v-if="scope.row.status == 1">待转账</span>
                                <span v-if="scope.row.status == 2">已转账</span>
                                <span v-if="scope.row.status == 3">已拒绝</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="reason" label="拒绝原因" width="220">
                        </el-table-column>
                        <el-table-column prop="createTime" label="申请时间" width="170">
                        </el-table-column>
                        <el-table-column prop="withdrawTime" label="转账/拒绝时间" width="180">
                        </el-table-column>
                        <el-table-column label="操作" width="100" fixed="right">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                    v-if="scope.row.status == 1" :disabled="!isAuth('financeList:transfer')"
                                    class="finance-list-page__action-btn">审核</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="finance-list-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="提现统计" name="second">
                <div class="nf-kpi-grid finance-list-page__kpi">
                    <div class="nf-kpi-card finance-kpi">
                        <div class="finance-kpi__icon finance-kpi__icon--brand">
                            <el-icon><Money /></el-icon>
                        </div>
                        <div class="finance-kpi__body">
                            <div class="finance-kpi__label">累计提现金额</div>
                            <div class="finance-kpi__value">
                                <span>{{ dataContainer.withdrawData.totalAmount ?
                                    dataContainer.withdrawData.totalAmount : '0' }}</span>元
                            </div>
                        </div>
                    </div>
                    <div class="nf-kpi-card finance-kpi">
                        <div class="finance-kpi__icon finance-kpi__icon--blue">
                            <el-icon><Document /></el-icon>
                        </div>
                        <div class="finance-kpi__body">
                            <div class="finance-kpi__label">提现次数</div>
                            <div class="finance-kpi__value">
                                <span>{{ dataContainer.withdrawData.totalCount ?
                                    dataContainer.withdrawData.totalCount : '0' }}</span>笔
                            </div>
                        </div>
                    </div>
                    <div class="nf-kpi-card finance-kpi">
                        <div class="finance-kpi__icon finance-kpi__icon--purple">
                            <el-icon><Wallet /></el-icon>
                        </div>
                        <div class="finance-kpi__body">
                            <div class="finance-kpi__label">待转账次数</div>
                            <div class="finance-kpi__value">
                                <span>{{ dataContainer.withdrawData.waitCount ?
                                    dataContainer.withdrawData.waitCount : '0' }}</span>笔
                            </div>
                        </div>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="充值统计" name="three">
                <div class="nf-kpi-grid finance-list-page__kpi">
                    <div class="nf-kpi-card finance-kpi">
                        <div class="finance-kpi__icon finance-kpi__icon--brand">
                            <el-icon><Money /></el-icon>
                        </div>
                        <div class="finance-kpi__body">
                            <div class="finance-kpi__label">充值总金额</div>
                            <div class="finance-kpi__value">
                                <span>{{ dataContainer.zongMoney ? dataContainer.zongMoney : 0 }}</span>元
                            </div>
                        </div>
                    </div>
                    <div class="nf-kpi-card finance-kpi" v-for="(item, index) in dataContainer.withdrawDataZf"
                        :key="index">
                        <div class="finance-kpi__icon finance-kpi__icon--green">
                            <el-icon><Wallet /></el-icon>
                        </div>
                        <div class="finance-kpi__body">
                            <div class="finance-kpi__label">{{ item.desc }}充值金额</div>
                            <div class="finance-kpi__value">
                                <span>{{ item.amount ? item.amount : '0' }}</span>元
                            </div>
                        </div>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="充值记录" name="four">
                <div class="nf-table-card finance-list-page__table">
                    <el-table :data="dataContainer.tableDataZf.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                        <el-table-column prop="userName" label="用户昵称" fixed="left">
                            <template #default="scope">
                                <span v-if="scope.row.userName" class="finance-list-page__link"
                                    @click="updates(scope.row.userId)">
                                    {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="orderNo" label="订单编号"></el-table-column>
                        <el-table-column prop="amount" label="充值金额" width="100"></el-table-column>
                        <el-table-column prop="platform" label="充值方式" width="100">
                            <template #default="scope">
                                <span v-if="scope.row.platform == 1">支付宝</span>
                                <span v-if="scope.row.platform == 2">微信</span>
                                <span v-if="scope.row.platform == 3">paypal支付</span>
                                <span v-if="scope.row.platform == 4">stripe支付</span>
                                <span v-if="scope.row.platform == 5">余额</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="state" label="状态" width="100" fixed="right">
                            <template #default="scope">
                                <span v-if="scope.row.state == 0">待支付</span>
                                <span v-if="scope.row.state == 1">支付成功</span>
                                <span v-if="scope.row.state == 2">支付失败</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="payTime" label="支付时间" width="180"></el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                    </el-table>
                    <div class="finance-list-page__pagination">
                        <el-pagination @size-change="handleSizeChangeZf" @current-change="handleCurrentChangeZf"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableDataZf.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
            <div class="finance-list-page__form-row">
                <span class="finance-list-page__form-label">审核结果：</span>
                <el-radio-group v-model="dataContainer.state" class="ml-4">
                    <el-radio :label="2">通过</el-radio>
                    <el-radio :label="3">拒绝</el-radio>
                </el-radio-group>
            </div>
            <div class="finance-list-page__form-row">
                <span class="finance-list-page__form-label">审核说明：</span>
                <el-input class="finance-list-page__form-textarea" v-model="dataContainer.description"
                    type="textarea" rows="4" placeholder="请输入审核说明"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleU = false">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToU">确认</el-button>
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
import { Filter, Money, Wallet, Document } from '@element-plus/icons-vue';
export default defineComponent({
    components: {
        SvgIcon,
        Filter,
        Money,
        Wallet,
        Document,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const url = ''
        const dataContainer = reactive({
            loading: false,
            activeName: 'first',
            tableData: {},
            tableDataZf: {},
            page: 1,
            limit: 10,
            classify: 1,
            dialogFormVisibleU: false,
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
            userNameT: '',
            nameT: '',
            statusT: '',
            inviteVerifyT: '',
            zhifubaoT: '',
            startTime: '',
            endTime: '',
            statusData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '待审核',
                    value: 1
                },
                {
                    label: '转账成功',
                    value: 2
                },
                {
                    label: '拒绝转账',
                    value: 3
                }
            ],
            inviteVerifyData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '支付宝',
                    value: 1
                },
                {
                    label: '微信',
                    value: 2
                }
            ],
            inviteVerifyDataZ: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '支付宝',
                    value: 1
                },
                {
                    label: '微信',
                    value: 2
                },
            ],
            statusDataZ: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '待支付',
                    value: 0
                },
                {
                    label: '支付成功',
                    value: 1
                },
                {
                    label: '支付失败',
                    value: 2
                }
            ],
            chongzhiData: {},
            withdrawData: {},
            withdrawDataZf: [],
            zongMoney: 0,
            flag: 'day',
            flags: [
                {
                    value: 'day',
                    label: '按天查询'
                }, {
                    value: 'month',
                    label: '按月查询'
                }, {
                    value: 'year',
                    label: '按年查询'
                }
            ],
            info: getNowTime(),



        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)
            dataContainer.userNameT = ''
            dataContainer.inviteVerifyT = ''
            dataContainer.statusT = ''
            dataContainer.nameT = ''
            dataContainer.zhifubaoT = ''
            dataContainer.startTime = ''
            dataContainer.endTime = ''
            dataContainer.page = 1
            if (event.srcElement.innerText == '提现管理') {

                dataSelect();
            }
            if (event.srcElement.innerText == '提现统计') {
                dataContainer.flag = 'day'
                dataContainer.info = getNowTime()
                dataSelectTj();
            }
            if (event.srcElement.innerText == '充值统计') {
                dataContainer.flag = 'day'
                dataContainer.info = getNowTime()
                dataSelectZftj();
            }
            if (event.srcElement.innerText == '充值记录') {

                dataSelectZf();
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
            dataContainer.userNameT = ''
            dataContainer.inviteVerifyT = ''
            dataContainer.statusT = ''
            dataContainer.nameT = ''
            dataContainer.zhifubaoT = ''
            dataContainer.startTime = ''
            dataContainer.endTime = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.post('admin/withdrawRecord/page', {
                page: dataContainer.page,
                limit: dataContainer.limit,
                userName: dataContainer.userNameT,
                accountType: dataContainer.inviteVerifyT,
                status: dataContainer.statusT,
                orderNumber: dataContainer.nameT,

                startTime: dataContainer.startTime,
                endTime: dataContainer.endTime,
                phone: dataContainer.zhifubaoT,
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.tableData = {}
                let returnData = res.data
                dataContainer.tableData = returnData
            })
        }
        dataSelect()
        /** 获取 提现统计 数据 */
        function dataSelectTj() {
            dataContainer.loading = true
            http.post('admin/withdrawRecord/statistics', {

                findDate: dataContainer.info,
                findType: dataContainer.flag,

            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.withdrawData = {}
                let returnData = res.data
                dataContainer.withdrawData = returnData
            })
        }
        /** 获取 充值统计 数据 */
        function dataSelectZftj() {
            dataContainer.loading = true
            http.post('admin/index/rechargeStatistics', {

                findDate: dataContainer.info,
                findType: dataContainer.flag,

            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.withdrawDataZf = []
                dataContainer.zongMoney = 0
                for (const i in res.data.rechargeRecords) {
                    if (Number(res.data.rechargeRecords[i].amount) > 0) {
                        dataContainer.zongMoney = dataContainer.zongMoney + Number(res.data.rechargeRecords[i].amount)
                    }
                }
                let returnData = res.data
                dataContainer.withdrawDataZf = returnData.rechargeRecords

            })
        }
        /** 获取充值记录数据 */
        function dataSelectZf() {
            dataContainer.loading = true
            http.post('admin/index/rechargeRecord', {

                page: dataContainer.page,
                limit: dataContainer.limit,
                userName: dataContainer.userNameT,
                orderNo: dataContainer.nameT,
                platform: dataContainer.inviteVerifyT,
                tradeType: '',
                startTime: dataContainer.startTime,
                endTime: dataContainer.endTime,
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.tableDataZf = {}
                let returnData = res.data
                dataContainer.tableDataZf = returnData
            })
        }
        /**
        * 搜索
        */
        function handleSearchZf() {
            dataContainer.page = 1
            dataSelectZf();
        }
        function clearSearchZf() {
            dataContainer.page = 1
            dataContainer.userNameT = ''
            dataContainer.inviteVerifyT = ''
            dataContainer.statusT = ''
            dataContainer.nameT = ''
            dataContainer.zhifubaoT = ''
            dataContainer.startTime = ''
            dataContainer.endTime = ''
            dataSelectZf();
        }
        /**  分页事件*/
        function handleSizeChange(val) {
            dataContainer.limit = val
            dataSelect()
        }
        function handleCurrentChange(val) {
            dataContainer.page = val
            dataSelect()
        }
        function handleSizeChangeZf(val) {
            dataContainer.limit = val
            dataSelectZf()
        }
        function handleCurrentChangeZf(val) {
            dataContainer.page = val
            dataSelectZf()
        }
        function changeBtn(row) {
            http.post('admin/group/updateStatus', {

                id: row.id,
                status: row.status,
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
            dataContainer.titles = '审核'
            dataContainer.id = row.id
            dataContainer.description = ''
            dataContainer.state = ''

            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.imageUrl = file.data.path
        }
        function StairNoticeToU() {

            if (dataContainer.state == '') {
                ElMessage({
                    message: '请选择审核结果',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.state == 3) {
                if (dataContainer.description == '') {
                    ElMessage({
                        message: '拒绝原因不能为空',
                        type: 'warning',
                    })
                    return
                }

            }

            http.post('admin/withdrawRecord/confirm', {
                id: dataContainer.id,
                status: dataContainer.state,
                reason: dataContainer.description,
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
            http.post('admin/base/banner/delete', {
                id: data.id
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
        // 选择日期类型
        function orderfenxi() {
            dataSelectTj()
            dataSelectZftj()
        }
        // 选择日期
        function animeOrder() {
            dataSelectTj()
            dataSelectZftj()
        }
        // 获取时间
        function getNowTime() {
            const now = new Date();
            const year = now.getFullYear();
            const month = now.getMonth();
            const date = now.getDate();
            const hh = now.getHours() < 10 ? "0" + now.getHours() : now.getHours();
            const mm = now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes();
            const ss = now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds();
            console.log(month)
            const month1 = month + 1;
            const month2 = month1.toString().padStart(2, "0");
            const date1 = date.toString().padStart(2, "0");
            var defaultDate = `${year}-${month2}-${date1}`;

            return defaultDate;
        }
        getNowTime();
        function isAuth(res) {
            return userApi.isAuth(res)
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
            handleSizeChangeZf,
            handleCurrentChangeZf,
            changeBtn,
            handleAvatarSuccess1,
            StairNoticeToU,
            handleDelete,
            updates,
            dataSelectZf,
            handleSearchZf,
            clearSearchZf,
            orderfenxi,
            animeOrder,
            getNowTime,
            dataSelectTj,
            dataSelectZftj,

        };
    },
});
</script>



<style lang="scss" scoped>
.finance-list-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.finance-list-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.finance-list-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.finance-list-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.finance-list-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.finance-list-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.finance-list-page__filter-input {
    width: 150px;
}

.finance-list-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.finance-list-page__tabs {
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

.finance-list-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.finance-list-page__kpi {
    margin-top: 0;
}

.finance-list-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.finance-list-page__qrcode-wrap {
    display: flex;
    flex-wrap: wrap;
}

.finance-list-page__qrcode-thumb {
    width: 50px;
    height: 50px;
    margin: 3px;
}

.finance-list-page__qrcode-preview {
    width: 400px;
    height: auto;
}

.finance-list-page__action-btn {
    margin: 5px;
}

.finance-list-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.finance-list-page__form-row {
    margin-bottom: 10px;
    display: flex;
    align-items: flex-start;
    gap: var(--nf-space-sm);
}

.finance-list-page__form-label {
    width: 200px;
    flex-shrink: 0;
    text-align: right;
    line-height: 32px;
}

.finance-list-page__form-textarea {
    width: 50%;
}

.finance-kpi {
    display: flex;
    align-items: center;
    gap: var(--nf-space-md);
}

.finance-kpi__icon {
    flex-shrink: 0;
    width: 44px;
    height: 44px;
    border-radius: var(--nf-radius-sm);
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;

    &--brand {
        background: var(--nf-brand-lite);
        color: var(--nf-brand);
    }

    &--green {
        background: #e8f8ef;
        color: #27ae60;
    }

    &--blue {
        background: #e8f0fe;
        color: #3b82f6;
    }

    &--purple {
        background: #f0e8fe;
        color: #8b5cf6;
    }
}

.finance-kpi__body {
    flex: 1;
    min-width: 0;
}

.finance-kpi__label {
    font-size: 13px;
    color: var(--nf-text-secondary);
    margin-bottom: 4px;
}

.finance-kpi__value {
    font-size: 13px;
    color: var(--nf-text-regular);

    span {
        font-size: 22px;
        font-weight: 600;
        color: var(--nf-brand);
        margin-right: 2px;
    }
}

@media (max-width: 768px) {
    .finance-list-page__kpi {
        grid-template-columns: repeat(2, 1fr);
    }
}
</style>
