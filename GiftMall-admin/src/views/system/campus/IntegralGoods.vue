<template>
    <div class="integral-goods-page">
        <div class="nf-page-hero integral-goods-page__hero">
            <span class="nf-page-hero__badge">充值配置</span>
            <h2 class="nf-page-hero__title">充值金额配置</h2>
            <p class="nf-page-hero__desc">管理用户充值档位金额，支持新增、修改与删除</p>
        </div>

        <div class="nf-filter-card integral-goods-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body integral-goods-page__filter-body">
                <el-button type="primary" @click="handleSearch()">刷新</el-button>
                <el-button type="primary" @click="addChangeU()"
                    :disabled="!isAuth('IntegralGoods:add')">新增</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="integral-goods-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="配置列表" name="first">
                <div class="nf-table-card integral-goods-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="180" fixed="left">
                        </el-table-column>
                        <el-table-column prop="amount" label="充值金额">
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间">
                        </el-table-column>
                        <el-table-column label="操作" width="160">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="integral-goods-page__action-btn"
                                    @click="addChangeU(scope.row)"
                                    :disabled="!isAuth('IntegralGoods:update')">修改</el-button>
                                <el-button size="small" type="danger" class="integral-goods-page__action-btn"
                                    @click="handleDelete(scope.row)"
                                    :disabled="!isAuth('IntegralGoods:delete')">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="integral-goods-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 添加/修改弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
            <div class="integral-goods-page__form-row">
                <span class="integral-goods-page__form-label">充值金额:</span>
                <el-input class="integral-goods-page__form-input" v-model="dataContainer.money" type="text"
                    placeholder="请输入充值金额"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleU = false">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToU">确认</el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 用户列表 弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleUse" title="优惠券列表" width="70%" center>
            <div class="integral-goods-page__dialog-toolbar">
                <el-button type="primary" @click="selectU()">刷新</el-button>
            </div>
            <el-table v-loading="dataContainer.tableDataLoading" :data="dataContainer.userData.records">
                <el-table-column prop="couponId" label="编号">
                </el-table-column>
                <el-table-column prop="couponName" label="优惠券名称">
                </el-table-column>
                <el-table-column prop="money" label="优惠券面值" width="100">
                </el-table-column>
                <el-table-column prop="minMoney" label="优惠券最低消费" width="150">
                </el-table-column>
                <el-table-column prop="validDays" label="优惠券有效期限(天)" width="150">
                    <template #default="scope">
                        <span>{{ scope.row.validDays == 0 ? '永久' : scope.row.validDays }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="操作">
                    <template #default="scope">
                        <el-button class="button-new-tag" size="small" @click="closes2(scope.row)">选择</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="integral-goods-page__dialog-pagination">
                <el-pagination @size-change="handleSizeChangeU" @current-change="handleCurrentChangeU"
                    :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit1"
                    :current-page="dataContainer.page1" layout="total, sizes, prev, pager, next, jumper"
                    :total="dataContainer.userData.total">
                </el-pagination>
            </div>
        </el-dialog>
    </div>
</template>
<script>
/**
 * 页面例子
 */
import { ref, defineComponent, reactive, toRef } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { Filter } from '@element-plus/icons-vue';
import SvgIcon from '@/components/svgIcon/index.vue';
import { messageSuccess, messageError } from '@/action/messagePrompt';
import { findTag, deleteTags } from '@/action/tagListTools';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import {
    regionData,
    codeToText,
} from 'element-china-area-data'
export default defineComponent({
    components: {
        SvgIcon,
        Filter
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const url = ''
        const cascaderAddr = ref(regionData);
        const multipleTableRef = ref()
        const getCodeToText = (codeStr) => {
            let codeArray = codeStr.split(",");
            let area = codeToText[codeArray[0]] + codeToText[codeArray[1]] + codeToText[codeArray[2]];
            return area;
        }
        const dataContainer = reactive({
            loading: false,
            activeName: 'first',
            tableData: {},
            page: 1,
            limit: 10,
            classify: -1,
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
            statesnum: [{
                value: -1,
                label: '全部'
            },
            {
                value: 0,
                label: '待审核'
            }, {
                value: 1,
                label: '审核成功'
            }, {
                value: 2,
                label: '拒绝'
            }
            ],
            statusT: '',
            nickNameT: '',
            phoneT: '',
            dialogFormVisibleSh: false,
            checkCertificationMessage: '',
            checkCertification: '',

            userId: '',
            phone: '',
            userName: '',

            couponId: '',
            couponName: '',
            rechargeId: '',
            money: '',
            couponNum: '',
            sort: '',

            isEnable: '',
            couponType: '',
            couponNum: '',
            userNameList: [],
            dialogFormVisibleUse: false,
            tableDataLoading: false,
            userData: {},
            inviterCode: '',
            page1: 1,
            limit1: 10,
            userIdList: [],
            userNameList: [],
            xzList: [],






        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '配置列表') {
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

        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.post('admin/rechargeConfig/page', {
                page: dataContainer.page,
                limit: dataContainer.limit,
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
        /**  分页事件*/
        function handleSizeChange(val) {
            dataContainer.limit = val
            dataSelect()
        }
        function handleCurrentChange(val) {
            dataContainer.page = val
            dataSelect()
        }
        function handleSizeChangeU(val) {
            dataContainer.limit1 = val
            dataSelect1()
        }
        function handleCurrentChangeU(val) {
            dataContainer.page1 = val
            dataSelect1()
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
                dataContainer.couponId = row.id

                dataContainer.money = row.amount
            } else {
                dataContainer.titles = '添加'
                dataContainer.couponId = ''
                dataContainer.money = ''
            }
            dataContainer.dialogFormVisibleU = true

        }
        // 清空优惠券
        function qingkongBtn() {
            dataContainer.couponId = ''
            dataContainer.couponName = ''
        }
        function StairNoticeToU() {
            if (dataContainer.money === '') {
                ElNotification({
                    title: '提示',
                    message: '请输入充值金额',
                    type: 'warning',
                })
                return
            }

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/rechargeConfig/addConfig'
            } else {
                dataContainer.routeUrl = 'admin/rechargeConfig/updateConfig'
            }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.couponId,
                amount: dataContainer.money,
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
            ElMessageBox.confirm(`确定删除数据?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                http.get('admin/rechargeConfig/deleteConfig', {
                    params: {
                        rechargeId: data.rechargeId
                    }
                }, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        messageSuccess('删除成功');
                        dataSelect();
                    } else {
                        messageError(res.msg);
                    }
                })
            })
        }

        // 打开用户列表
        function couponIdBtn1() {
            dataContainer.page1 = 1
            dataSelect1()
            dataContainer.dialogFormVisibleUse = true
        }
        // 获取用户数据列表
        function dataSelect1() {
            dataContainer.tableDataLoading = true
            http.get('admin/coupon/getCouponPageList', {
                params: {
                    page: dataContainer.page1,
                    limit: dataContainer.limit1,

                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.userData = {}
                dataContainer.tableDataLoading = false
                if (res.code == 0) {
                    dataContainer.userData = res.data
                }
            })
        }
        function selectU() {
            dataContainer.page1 = 1
            dataSelect1()
        }

        // 确定选择优惠券
        function closes2(rows) {
            dataContainer.couponName = rows.couponName
            dataContainer.couponId = rows.couponId
            dataContainer.dialogFormVisibleUse = false
        }
        /** 获取列表数据 */

        function isAuth(res) {
            return userApi.isAuth(res)
        }


        return {
            isAuth,
            dataContainer,
            addChangeU,
            handleClick,
            handleSearch,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            StairNoticeToU,
            handleDelete,
            couponIdBtn1,
            dataSelect1,
            selectU,
            closes2,
            handleSizeChangeU,
            handleCurrentChangeU,
            qingkongBtn,

        };
    },
});
</script>



<style lang="scss" scoped>
.integral-goods-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.integral-goods-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.integral-goods-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.integral-goods-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.integral-goods-page__tabs {
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

.integral-goods-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.integral-goods-page__action-btn {
    margin: 4px 0;
}

.integral-goods-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.integral-goods-page__form-row {
    margin-bottom: var(--nf-space-sm);
}

.integral-goods-page__form-label {
    width: 200px;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
}

.integral-goods-page__form-input {
    width: 50%;
}

.integral-goods-page__dialog-toolbar {
    display: flex;
    justify-content: flex-end;
    margin-bottom: var(--nf-space-sm);
}

.integral-goods-page__dialog-pagination {
    display: flex;
    justify-content: flex-end;
    margin-top: var(--nf-space-sm);
}
</style>
