<template>
    <div class="couponuser-page">
        <div class="nf-page-hero couponuser-page__hero">
            <span class="nf-page-hero__badge">优惠券领取</span>
            <h2 class="nf-page-hero__title">领取记录</h2>
            <p class="nf-page-hero__desc">查看用户优惠券领取记录，支持按优惠券名称、手机号与使用状态筛选</p>
        </div>

        <div class="nf-filter-card couponuser-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body couponuser-page__filter-body">
                <div class="couponuser-page__filter-item">
                    <span class="couponuser-page__filter-label">优惠券名称</span>
                    <el-input v-model="dataContainer.couponName" placeholder="请输入优惠券名称"
                        @keydown.enter.native="handleSearch" class="couponuser-page__filter-input" />
                </div>
                <div class="couponuser-page__filter-item">
                    <span class="couponuser-page__filter-label">手机号</span>
                    <el-input v-model="dataContainer.phone" placeholder="请输入手机号"
                        @keydown.enter.native="handleSearch" class="couponuser-page__filter-input" />
                </div>
                <div class="couponuser-page__filter-item">
                    <span class="couponuser-page__filter-label">状态</span>
                    <el-select v-model="dataContainer.status" class="couponuser-page__filter-input"
                        placeholder="请选择状态" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statusData" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="couponuser-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">查询</el-button>
                    <el-button @click="clearSearch()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="couponuser-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="优惠券领取信息" name="first">
                <div class="nf-table-card couponuser-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="80">
                        </el-table-column>
                        <el-table-column prop="couponName" label="优惠券名称" width="150">
                        </el-table-column>
                        <el-table-column prop="couponPicture" label="优惠券图片">
                            <template #default="scope">
                                <img v-if="scope.row.couponPicture" :src="scope.row.couponPicture" alt=""
                                    width="40" height="40">
                            </template>
                        </el-table-column>
                        <el-table-column prop="userName" label="领取用户">
                            <template #default="scope">
                                <span v-if="scope.row.userName" class="couponuser-page__link"
                                    @click="updates(scope.row.userId)">{{ scope.row.userName }}</span>
                                <span v-else>-</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="avatar" label="用户头像">
                            <template #default="scope">
                                <img class="couponuser-page__avatar" :src="scope.row.avatar" alt="">
                            </template>
                        </el-table-column>
                        <el-table-column prop="phone" label="手机号">
                        </el-table-column>
                        <el-table-column prop="money" label="优惠券面值">
                        </el-table-column>
                        <el-table-column prop="minMoney" label="优惠券最低消费">
                        </el-table-column>
                        <el-table-column prop="expirationTime" label="优惠券失效时间" width="150">
                        </el-table-column>
                        <el-table-column label="状态">
                            <template #default="scope">
                                <span v-if="scope.row.status == 0" class="couponuser-page__link">未使用</span>
                                <span v-if="scope.row.status == 1">已使用</span>
                                <span v-if="scope.row.status == 2">已过期</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="150">
                        </el-table-column>
                    </el-table>
                    <div class="couponuser-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>
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
            url: "",
            couponName:'',
            phone:'',
            status:'',
            statusData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '未使用',
                    value: 0
                },
                {
                    label: '已使用',
                    value: 1
                },
                {
                    label: '已过期',
                    value: 2
                }
            ],

        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '优惠券领取信息') {
                dataContainer.classify = 1
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
        function clearSearch(){
            dataContainer.page = 1
            dataContainer.couponName = ''
            dataContainer.phone = ''
            dataContainer.status = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('admin/couponUser/getMyCouponList', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    couponName: dataContainer.couponName,
                    phone: dataContainer.phone,
                    status: dataContainer.status,
                }
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
                        state:row.state,
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
                    }else{
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

        return {
            dataContainer,

            handleClick,
            handleSearch,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            clearSearch,

        };
    },
});
</script>



<style lang="scss" scoped>
.couponuser-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.couponuser-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.couponuser-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.couponuser-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.couponuser-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.couponuser-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.couponuser-page__filter-input {
    width: 150px;
}

.couponuser-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.couponuser-page__tabs {
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

.couponuser-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.couponuser-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.couponuser-page__avatar {
    width: 50px;
    height: 50px;
    margin: 3px;
}

.couponuser-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
