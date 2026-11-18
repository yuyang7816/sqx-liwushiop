<template>
    <div class="feedback-page">
        <div class="nf-page-hero feedback-page__hero">
            <span class="nf-page-hero__badge">反馈中心</span>
            <h2 class="nf-page-hero__title">反馈列表</h2>
            <p class="nf-page-hero__desc">查看用户端与师傅端反馈记录，支持按平台与用户邮箱/QQ 筛选</p>
        </div>

        <div class="nf-filter-card feedback-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body feedback-page__filter-body">
                <div class="feedback-page__filter-item">
                    <span class="feedback-page__filter-label">平台：</span>
                    <el-select v-model="dataContainer.statusT" class="feedback-page__filter-input"
                        placeholder="请选择平台" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statesnum" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="feedback-page__filter-item">
                    <span class="feedback-page__filter-label">用户邮箱/QQ：</span>
                    <el-input v-model="dataContainer.nickNameT" placeholder="请输入用户邮箱/QQ"
                        @keydown.enter.native="handleSearch" class="feedback-page__filter-input" />
                </div>
                <div class="feedback-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">查询</el-button>
                    <el-button @click="clearChangeU()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="feedback-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="反馈列表" name="first">
                <div class="nf-table-card feedback-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="feedbackId" label="编号" width="80">
                        </el-table-column>
                        <el-table-column prop="feedType" label="平台" width="180">
                            <template #default="scope">
                                <div>
                                    <span v-if="scope.row.feedType==1">用户端</span>
                                    <span v-if="scope.row.feedType==2">师傅端</span>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="userEmail" label="用户邮箱/QQ" width="250">
                            <template #default="scope">
                                <span class="feedback-page__link" @click="updates(scope.row.userId)">
                                    {{ scope.row.userEmail ? scope.row.userEmail : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="feedbackMessage" label="内容">
                        </el-table-column>
                        <el-table-column prop="feedbackScore" label="评分">
                            <template #default="scope">
                                <el-rate clearable v-if="scope.row.feedbackScore" v-model="scope.row.feedbackScore"
                                    disabled show-score text-color="#ff9900" />
                                <span v-else>暂无评分</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="feedbackTime" label="反馈时间" width="160">
                        </el-table-column>
                    </el-table>
                    <div class="feedback-page__pagination">
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
						value: '',
						label: '全部'
					},
					{
					value: 1,
					label: '用户端'
				},
				{
					value: 2,
					label: '师傅端'
				}],
            statusT:'',
            nickNameT:'',
            phoneT:'', 


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '反馈列表') {
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
        function clearChangeU(){
            dataContainer.page = 1
            dataContainer.nickNameT = ''
            dataContainer.phoneT = ''
            dataContainer.statusT = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true 
            http.get('adminerrand/selectUserFeedback', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    userEmail: dataContainer.nickNameT,
                    feedType:dataContainer.statusT,
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
                query:{
                    userId:userId,
                },
                params: {
                    sign: userId || new Date().getTime(),
                    querys,
                },
            });
        }


        
        function isAuth(res){
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
.feedback-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.feedback-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.feedback-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.feedback-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.feedback-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.feedback-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.feedback-page__filter-input {
    width: 150px;
}

.feedback-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.feedback-page__tabs {
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

.feedback-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.feedback-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.feedback-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
