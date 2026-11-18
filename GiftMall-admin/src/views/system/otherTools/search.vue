<template>
    <div class="search-page">
        <div class="nf-page-hero search-page__hero">
            <span class="nf-page-hero__badge">搜索记录</span>
            <h2 class="nf-page-hero__title">用户搜索记录</h2>
            <p class="nf-page-hero__desc">查看用户搜索历史，支持按搜索用户与搜索内容筛选查询</p>
        </div>

        <div class="nf-filter-card search-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body search-page__filter-body">
                <div class="search-page__filter-item">
                    <span class="search-page__filter-label">搜索用户：</span>
                    <el-input v-model="dataContainer.nickNameT" placeholder="请输入搜索用户"
                        @keydown.enter.native="handleSearch" class="search-page__filter-input" />
                </div>
                <div class="search-page__filter-item">
                    <span class="search-page__filter-label">搜索内容：</span>
                    <el-input v-model="dataContainer.indentNumberT" placeholder="请输入搜索内容"
                        @keydown.enter.native="handleSearch" class="search-page__filter-input" />
                </div>
                <div class="search-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">查询</el-button>
                    <el-button @click="clearChangeU()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="search-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="搜索记录" name="first">
                <div class="nf-table-card search-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="80">
                        </el-table-column>
                        <el-table-column prop="indent" label="搜索用户" width="150">
                            <template #default="scope">
                                <span v-if="scope.row.userName" class="search-page__link"
                                    @click="updates(scope.row.userId)">
                                    {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="value" label="搜索内容">
                        </el-table-column>
                        <el-table-column prop="createTime" label="搜索时间" width="160">
                        </el-table-column>
                    </el-table>
                    <div class="search-page__pagination">
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

            if (event.srcElement.innerText == '搜索记录') {
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
            http.post('admin/searchHistory/page', {
                // params: {
                page: dataContainer.page,
                limit: dataContainer.limit,
                userName: dataContainer.nickNameT,
                // satisfactionFlag: dataContainer.statusT,
                value: dataContainer.indentNumberT,
                // riderName: dataContainer.riderNameT,
                // }
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
.search-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.search-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.search-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.search-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.search-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.search-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.search-page__filter-input {
    width: 150px;
}

.search-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.search-page__tabs {
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

.search-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.search-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.search-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
