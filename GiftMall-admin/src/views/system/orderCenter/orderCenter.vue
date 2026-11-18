<template>
    <div class="order-center-page">
        <div class="nf-page-hero order-center-page__hero">
            <span class="nf-page-hero__badge">订单中心</span>
            <h2 class="nf-page-hero__title">订单列表</h2>
            <p class="nf-page-hero__desc">查看与管理礼品订单，支持按状态、下单用户与订单号筛选及发货操作</p>
        </div>

        <div class="nf-filter-card order-center-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body order-center-page__filter-body">
                <div class="order-center-page__filter-item">
                    <span class="order-center-page__filter-label">订单状态：</span>
                    <el-select v-model="dataContainer.statusT" placeholder="请选择订单状态"
                        class="order-center-page__filter-input" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statusList" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="order-center-page__filter-item">
                    <span class="order-center-page__filter-label">下单用户</span>
                    <el-input v-model="dataContainer.userNameT" placeholder="请输入下单用户"
                        @keydown.enter.native="handleSearch" class="order-center-page__filter-input" />
                </div>
                <div class="order-center-page__filter-item">
                    <span class="order-center-page__filter-label">订单号：</span>
                    <el-input v-model="dataContainer.locationT" placeholder="请输入订单号"
                        @keydown.enter.native="handleSearch" class="order-center-page__filter-input" />
                </div>
                <div class="order-center-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">查询</el-button>
                    <el-button @click="clearSearch()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="order-center-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="订单列表" name="first">
                <div class="nf-table-card order-center-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                        <el-table-column prop="userName" label="下单用户" fixed="left">
                            <template #default="scope">
                                <span v-if="scope.row.userName" class="order-center-page__link"
                                    @click="updates(scope.row.userId)">
                                    {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="giveUserName" label="领取人" fixed="left">
                            <template #default="scope">
                                <span v-if="scope.row.giveUserId" class="order-center-page__link"
                                    @click="updates(scope.row.giveUserId)">
                                    {{ scope.row.giveUserName ? scope.row.giveUserName : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="typeName" label="商品类型" width="120">
                            <template #default="scope">
                                <div v-for="(item, index) in scope.row.productList" :key="index"
                                    class="order-center-page__product-row">
                                    <div class="order-center-page__product-type">
                                        <div class="order-center-page__product-type-name">
                                            {{ item.giftProductType.name }}</div>
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="productList" label="商品详情" width="300">
                            <template #default="scope">
                                <div v-for="(item, index) in scope.row.productList" :key="index"
                                    class="order-center-page__product-row">
                                    <img v-if="item.giftProduct.coverImg" :src="item.giftProduct.coverImg"
                                        class="order-center-page__product-img" />
                                    <div class="order-center-page__product-info">
                                        <div class="order-center-page__product-name">
                                            {{ item.giftProduct.name }}</div>
                                        <div class="order-center-page__product-sku">
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
                                <el-button size="small" type="primary" v-if="scope.row.expressNumber"
                                    class="order-center-page__action-btn" @click="addChangeU(scope.row)">修改</el-button>
                                <el-button size="small" type="primary" plain v-if="scope.row.expressNumber"
                                    class="order-center-page__action-btn"
                                    @click="wuliuChangeU(scope.row)">查看物流</el-button>
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
                        <el-table-column label="操作" width="100" fixed="right">
                            <template #default="scope">
                                <el-button size="small" type="primary" v-if="scope.row.status == 16"
                                    class="order-center-page__action-btn"
                                    @click="addChangeU(scope.row)">发货</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="order-center-page__pagination">
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
            <div class="order-center-page__form-row">
                <span class="order-center-page__form-label">快递名称：</span>
                <el-input class="order-center-page__form-input" v-model="dataContainer.name" type="text"
                    placeholder="请输入快递名称"></el-input>
            </div>
            <div class="order-center-page__form-row">
                <span class="order-center-page__form-label">快递号：</span>
                <el-input class="order-center-page__form-input" v-model="dataContainer.url" type="text"
                    placeholder="请输入快递号"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleU = false">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToU">确认</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 点赞列表 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleDz" title="点赞列表" width="80%" center>
            <el-table :data="dataContainer.tableDataDz" v-loading="dataContainer.loading" style="width: 100%">
                <el-table-column prop="userId" label="编号" width="100"></el-table-column>
                <el-table-column prop="userName" label="点赞用户">
                    <template #default="scope">
                        <span v-if="scope.row.userName" class="order-center-page__link"
                            @click="updates(scope.row.userId)">
                            {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="avatar" label="用户图头">
                    <template #default="scope">
                        <img :src="scope.row.avatar" class="order-center-page__avatar" />
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
                        <span v-if="scope.row.userName" class="order-center-page__link"
                            @click="updates(scope.row.userId)">
                            {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="avatar" label="用户图头" width="100">
                    <template #default="scope">
                        <img :src="scope.row.avatar" class="order-center-page__avatar" />
                    </template>
                </el-table-column>
                <el-table-column prop="content" label="评论内容"></el-table-column>
                <el-table-column prop="createTime" label="评论时间" width="180"></el-table-column>
            </el-table>
        </el-dialog>
        <!-- 物流弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleWl" title="物流信息" width="80%" center>
            <div class="order-center-page__logistics-head">
                <img :src="dataContainer.tableDataWl.logo" class="order-center-page__logistics-logo" />
                <div class="order-center-page__logistics-meta">快递名称：{{ dataContainer.tableDataWl.expName }}</div>
                <div class="order-center-page__logistics-meta">快递单号：{{ dataContainer.tableDataWl.number }}</div>
            </div>
            <el-steps direction="vertical" :active="active" :finish-status="type">
                <el-step v-for="(item, index) in dataContainer.tableDataWl.list" :key="index" :title="item.status"
                    :icon="CircleCheck">
                    <template #description>
                        <div>{{ item.time }}</div>
                    </template>
                </el-step>
            </el-steps>
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
            dialogFormVisibleWl: false,
            titles: '添加',
            id: 0,
            name: "",
            imageUrl: "",
            url: "",
            sort: 0,
            description: "",
            state: 0, //订单状态1 待付款 2 已付款 3 已发货 4 已收货 5 已取消 6 退款中 7 已退款 8 拒绝退款 9 拼团中 10 已评价 11 待赠送 12 已赠送
            routeUrl: '',
            active: 1,
            inactive: 2,
            tableDataDz: [],
            tableDataPl: [],
            userNameT: '',
            locationT: '',
            privacyT: '',
            statusT: '',
            statusData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '普通订单',
                    value: 1
                },
                {
                    label: '拼团',
                    value: 2
                },
                {
                    label: '秒杀',
                    value: 3
                }
            ],
            statusList: [ // 1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送 13已领取 14已拒绝 16待发货 17已开发票
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '待付款',
                    value: 1
                },
                {
                    label: '已付款',
                    value: 2
                },
                {
                    label: '已发货',
                    value: 3
                },
                {
                    label: '已收货',
                    value: 4
                },
                {
                    label: '已取消',
                    value: 5
                },
                {
                    label: '退款中',
                    value: 6
                },
                {
                    label: '已退款',
                    value: 7
                },
                // {
                //     label: '拒绝退款',
                //     value: 8
                // },
                // {
                //     label: '拼团中',
                //     value: 9
                // },
                // {
                //     label: '已评价',
                //     value: 10
                // },
                {
                    label: '待赠送',
                    value: 11
                },
                {
                    label: '已赠送',
                    value: 12
                },
                {
                    label: '已领取',
                    value: 13
                },
                {
                    label: '已拒绝',
                    value: 14
                },
                {
                    label: '待发货',
                    value: 16
                },
                // {
                //     label: '已开发票',
                //     value: 17
                // },
            ],
            tableDataWl: {
                list: [],
            },


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
            dataContainer.userNameT = ''
            dataContainer.locationT = ''
            dataContainer.privacyT = ''
            dataContainer.statusT = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('admin/order/page', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    userName: dataContainer.userNameT,
                    orderNo: dataContainer.locationT,
                    orderType: dataContainer.privacyT,
                    status: dataContainer.statusT,
                    startTime: '',
                    endTime: '',

                }

                // classify: dataContainer.classify,
                // state: '',
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
            if (row.expressNumber) {
                dataContainer.titles = '修改快递'
                dataContainer.name = row.expressName
                dataContainer.url = row.expressNumber
            } else {
                dataContainer.titles = '发货'
                dataContainer.name = ''
                dataContainer.url = ''
            }

            dataContainer.id = row.id


            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.imageUrl = file.data.path
        }
        function StairNoticeToU() {
            if (dataContainer.name === '') {
                ElNotification({
                    title: '提示',
                    message: '请输入快递名称',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.url === '') {
                ElNotification({
                    title: '提示',
                    message: '请输入快递单号',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.titles == '修改快递') {

                dataContainer.routeUrl = 'admin/order/updateExpress'
            } else {
                dataContainer.routeUrl = 'admin/order/express'
            }


            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                expressName: dataContainer.name,
                expressNumber: dataContainer.url,

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
        // 查看物流
        function wuliuChangeU(row) {
            dataContainer.url = row.expressNumber
            http.get('admin/order/getLogistics', {
                params: {
                    expressNumber: dataContainer.url,
                }
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    dataContainer.tableDataWl = res.data.result
                    dataContainer.dialogFormVisibleWl = true
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

        return {
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
            wuliuChangeU,

        };
    },
});
</script>



<style lang="scss" scoped>
.order-center-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.order-center-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.order-center-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.order-center-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.order-center-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.order-center-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.order-center-page__filter-input {
    width: 150px;
}

.order-center-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.order-center-page__tabs {
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

.order-center-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.order-center-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.order-center-page__action-btn {
    margin: 3px;
}

.order-center-page__product-row {
    display: block;
}

.order-center-page__product-type {
    display: inline-block;
    width: 70%;
    margin-left: 10px;
    height: 60px;
    margin: 5px;
}

.order-center-page__product-type-name {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    overflow: hidden;
    max-height: 30px;
}

.order-center-page__product-img {
    width: 60px;
    height: 60px;
    float: left;
}

.order-center-page__product-info {
    display: inline-block;
    width: 70%;
    margin-left: 10px;
    height: 60px;
    margin: 5px;
}

.order-center-page__product-name {
    font-size: 16px;
    color: #666;
    font-weight: 600;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    overflow: hidden;
    max-height: 30px;
}

.order-center-page__product-sku {
    font-size: 12px;
    color: #999;
    line-height: 20px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    max-height: 50px;
}

.order-center-page__avatar {
    width: 60px;
    height: 60px;
}

.order-center-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.order-center-page__form-row {
    margin-bottom: var(--nf-space-sm);
    display: flex;
    align-items: center;
}

.order-center-page__form-label {
    width: 200px;
    flex-shrink: 0;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
}

.order-center-page__form-input {
    width: 50%;
}

.order-center-page__logistics-head {
    margin-bottom: var(--nf-space-sm);
}

.order-center-page__logistics-logo {
    width: 80px;
    margin: 10px 0;
}

.order-center-page__logistics-meta {
    display: inline-block;
    margin: 10px;
    font-size: 16px;
}
</style>
