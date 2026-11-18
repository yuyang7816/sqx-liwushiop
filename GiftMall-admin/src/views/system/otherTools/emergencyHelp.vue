<template>
    <div class="emergency-help-page">
        <div class="nf-page-hero emergency-help-page__hero">
            <span class="nf-page-hero__badge">紧急求助</span>
            <h2 class="nf-page-hero__title">求助列表</h2>
            <p class="nf-page-hero__desc">查看用户紧急求助记录，支持播放求助语音、查看地址与坐标，并处理待办求助</p>
        </div>

        <div class="nf-filter-card emergency-help-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body emergency-help-page__filter-body">
                <div class="emergency-help-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">刷新</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="emergency-help-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="求助列表" name="first">
                <div class="nf-table-card emergency-help-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column fixed prop="id" label="编号" width="80">
                        </el-table-column>
                        <el-table-column prop="userName" label="求助人昵称" width="100">
                            <template #default="scope">
                                <span class="emergency-help-page__link" @click="updates(scope.row.userId)">
                                    {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="image" label="求助语音" width="400">
                            <template #default="scope">
                                <audio v-if="scope.row.image != '' && scope.row.image != null"
                                    :src="scope.row.image" type="audio/wav" controls="controls"></audio>
                                <span v-else>暂无语音</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="content" label="地址" width="150">
                            <template #default="scope">
                                <span>{{ scope.row.content ? scope.row.content : '-' }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="type" label="经度">
                        </el-table-column>
                        <el-table-column prop="platform" label="纬度">
                        </el-table-column>
                        <el-table-column prop="createAt" label="求助时间" width="160"></el-table-column>
                        <el-table-column label="处理状态" width="160" fixed="right">
                            <template #default="scope">
                                <span v-if="scope.row.url == '1'" class="emergency-help-page__status--done">已处理</span>
                                <span v-else class="emergency-help-page__status--pending">待处理</span>
                                <el-button v-if="scope.row.url !== '1'" size="small" type="danger"
                                    class="emergency-help-page__action-btn"
                                    :disabled="!isAuth('emergencyHelp:chuli')" @click="classdelete(scope.row)">处理
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="emergency-help-page__pagination">
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
            classify: 1,
            url: "",
            couponName: '',
            phone: '',
            status: '',
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

            if (event.srcElement.innerText == '求助列表') {
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
            dataContainer.couponName = ''
            dataContainer.phone = ''
            dataContainer.status = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('message/page/9/' + dataContainer.page + '/' + dataContainer.limit, {
                params: {
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
        function classdelete(row) {
            ElMessageBox.confirm(`确定处理该求助?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                http.post('message/auditMessage', {
                    ids: row.id,
                    status: 1,
                    auditContent: '',

                }, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        messageSuccess('操作成功');
                        dataSelect();
                    } else {
                        messageError(res.msg);
                    }
                })
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

        function isAuth(res) {
            return userApi.isAuth(res)
        }


        return {
            isAuth,
            dataContainer,

            handleClick,
            handleSearch,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            clearSearch,
            classdelete,
            updates,

        };
    },
});
</script>



<style lang="scss" scoped>
.emergency-help-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.emergency-help-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.emergency-help-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.emergency-help-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.emergency-help-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.emergency-help-page__tabs {
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

.emergency-help-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.emergency-help-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.emergency-help-page__status--done {
    color: #13ce66;
}

.emergency-help-page__status--pending {
    color: #ff4949;
}

.emergency-help-page__action-btn {
    margin-left: 8px;
}

.emergency-help-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
