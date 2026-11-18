<template>
    <div class="comment-list-page">
        <div class="nf-page-hero comment-list-page__hero">
            <span class="nf-page-hero__badge">评论记录</span>
            <h2 class="nf-page-hero__title">评论列表</h2>
            <p class="nf-page-hero__desc">查看订单评价记录，支持按满意度、评价用户、师傅昵称与订单号筛选</p>
        </div>

        <div class="nf-filter-card comment-list-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body comment-list-page__filter-body">
                <div class="comment-list-page__filter-item">
                    <span class="comment-list-page__filter-label">是否满意：</span>
                    <el-select v-model="dataContainer.statusT" class="comment-list-page__filter-input"
                        placeholder="请选择是否满意" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statesnum" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="comment-list-page__filter-item">
                    <span class="comment-list-page__filter-label">评价用户：</span>
                    <el-input v-model="dataContainer.nickNameT" placeholder="请输入评价用户"
                        @keydown.enter.native="handleSearch" class="comment-list-page__filter-input" />
                </div>
                <div class="comment-list-page__filter-item">
                    <span class="comment-list-page__filter-label">师傅昵称：</span>
                    <el-input v-model="dataContainer.riderNameT" placeholder="请输入师傅昵称"
                        @keydown.enter.native="handleSearch" class="comment-list-page__filter-input" />
                </div>
                <div class="comment-list-page__filter-item">
                    <span class="comment-list-page__filter-label">评价订单号：</span>
                    <el-input v-model="dataContainer.indentNumberT" placeholder="请输入评价订单号"
                        @keydown.enter.native="handleSearch" class="comment-list-page__filter-input" />
                </div>
                <div class="comment-list-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">查询</el-button>
                    <el-button @click="clearChangeU()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="comment-list-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="评论列表" name="first">
                <div class="nf-table-card comment-list-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="evaluateId" label="编号" width="80">
                        </el-table-column>
                        <el-table-column prop="indentNumber" label="评价订单号" width="200">
                        </el-table-column>
                        <el-table-column prop="indent" label="评价用户" width="150">
                            <template #default="scope">
                                <span v-if="scope.row.user && scope.row.user.nickName"
                                    class="comment-list-page__link" @click="updates(scope.row.userId)">
                                    {{ scope.row.user.nickName ? scope.row.user.nickName : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="indent" label="师傅昵称" width="150">
                            <template #default="scope">
                                <span v-if="scope.row.riderUser && scope.row.riderUser.nickName"
                                    class="comment-list-page__link" @click="updates(scope.row.riderUserId)">
                                    {{ scope.row.riderUser.nickName ? scope.row.riderUser.nickName : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="evaluateMessage" label="评价内容">
                        </el-table-column>
                        <el-table-column prop="satisfactionFlag" label="是否满意" width="160">
                            <template #default="scope">
                                <span v-if="scope.row.satisfactionFlag == '0'">满意</span>
                                <span v-if="scope.row.satisfactionFlag == '1'">不满意</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="评价时间" width="160">
                        </el-table-column>
                    </el-table>
                    <div class="comment-list-page__pagination">
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
            statesnum: [{
                value: null,
                label: '全部'
            },
            {
                value: 0,
                label: '满意'
            },
            {
                value: 1,
                label: '不满意'
            }],
            statusT: null,
            nickNameT: '',
            indentNumberT: '',
            riderNameT: '',



        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '评论列表') {
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
        function clearChangeU() {
            dataContainer.page = 1
            dataContainer.nickNameT = ''
            dataContainer.indentNumberT = ''
            dataContainer.riderNameT = ''
            dataContainer.statusT = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('adminevaluate/selectEvaluate', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    nickName: dataContainer.nickNameT,
                    satisfactionFlag: dataContainer.statusT,
                    indentNumber: dataContainer.indentNumberT,
                    riderName: dataContainer.riderNameT,
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


        function isAuth(res) {
            return userApi.isAuth(res)
        }


        return {
            isAuth,
            dataContainer,
            handleClick,
            handleSearch,
            clearChangeU,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            updates,



        };
    },
});
</script>



<style lang="scss" scoped>
.comment-list-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.comment-list-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.comment-list-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.comment-list-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.comment-list-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.comment-list-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.comment-list-page__filter-input {
    width: 150px;
}

.comment-list-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.comment-list-page__tabs {
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

.comment-list-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.comment-list-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.comment-list-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
