<template>
    <div class="recruit-page">
        <div class="nf-page-hero recruit-page__hero">
            <span class="nf-page-hero__badge">推广申请</span>
            <h2 class="nf-page-hero__title">推广申请列表</h2>
            <p class="nf-page-hero__desc">查看用户推广申请记录，支持按状态、姓名与电话筛选并审核</p>
        </div>

        <div class="nf-filter-card recruit-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body recruit-page__filter-body">
                <div class="recruit-page__filter-item">
                    <span class="recruit-page__filter-label">状态：</span>
                    <el-select v-model="dataContainer.statusT" class="recruit-page__filter-input"
                        placeholder="请选择状态" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statesnum" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="recruit-page__filter-item">
                    <span class="recruit-page__filter-label">姓名：</span>
                    <el-input v-model="dataContainer.nickNameT" placeholder="请输入姓名"
                        @keydown.enter.native="handleSearch" class="recruit-page__filter-input" />
                </div>
                <div class="recruit-page__filter-item">
                    <span class="recruit-page__filter-label">电话：</span>
                    <el-input v-model="dataContainer.phoneT" placeholder="请输入电话"
                        @keydown.enter.native="handleSearch" class="recruit-page__filter-input" />
                </div>
                <div class="recruit-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">查询</el-button>
                    <el-button @click="clearChangeU()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="recruit-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="投诉列表" name="first">
                <div class="nf-table-card recruit-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column fixed prop="extensionId" label="编号" width="50">
                        </el-table-column>
                        <el-table-column prop="realName" label="姓名">
                            <template #default="scope">
                                <span class="recruit-page__link" @click="updates(scope.row.userId)"
                                    v-text="scope.row.realName"></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="phone" label="电话">
                        </el-table-column>
                        <el-table-column prop="addressDetail" label="地址">
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间">
                        </el-table-column>
                        <el-table-column prop="remarks" label="审核理由">
                        </el-table-column>
                        <el-table-column prop="createTime" label="状态">
                            <template #default="scope">
                                <span class="recruit-page__status" v-if="scope.row.status == 1">待审核</span>
                                <span class="recruit-page__status" v-if="scope.row.status == 2">已通过</span>
                                <span class="recruit-page__status" v-if="scope.row.status == 3">已拒绝</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="100" fixed="right">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="refuseClick(scope.row)"
                                    :disabled="!isAuth('recruitList:shenhe')" plain
                                    v-if="scope.row.status == 1">审核</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="recruit-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 审核 弹框 -->
        <el-dialog title="审核" v-model="dataContainer.dialogFormVisible" center>
            <div style="margin-bottom: 10px;">
                <span style="width: 200px;display: inline-block;text-align: right;">审核结果：</span>
                <el-radio-group v-model="dataContainer.status" class="ml-4">
                    <el-radio :label="2">通过</el-radio>
                    <el-radio :label="3">拒绝</el-radio>
                </el-radio-group>
            </div>
            <div style="margin-bottom: 10px;" v-if="dataContainer.status==2">
                <span style="width: 200px;display: inline-block;text-align: right;">一级推广佣金：</span>
                <el-input style="width:50%;" v-model="dataContainer.divideScale" type="text"
                    placeholder="请输入一级推广佣金分成比例（例:0.2即20%）"></el-input>
            </div>
            <div style="margin-bottom: 10px;" v-if="dataContainer.status==2">
                <span style="width: 200px;display: inline-block;text-align: right;">二级推广佣金：</span>
                <el-input style="width:50%;" v-model="dataContainer.divideTwoScale" type="text"
                    placeholder="请输入二级推广佣金分成比例（例:0.2即20%）"></el-input>
            </div>
            <div style="margin-bottom: 10px;" v-if="dataContainer.status==3">
                <span style="width: 200px;display: inline-block;text-align: right;position: relative;top: -70px;">审核理由：</span>
                <el-input style="width:50%;" v-model="dataContainer.remarks" type="textarea" :rows="4"
                    placeholder="请输入审核理由">
                </el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="refuseto()">确 定</el-button>
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
            tableDataClass:{},
            page: 1,
            limit: 10,
            classify: 1,
            dialogFormVisibleU: false,
            titles: '添加',
            id: 0,
            illegal: "",
            forfeitMoney: 0,
            
            state: 0,
            routeUrl:'',
            active:1,
            inactive:2,
            dialogFormVisible:false,
            content:'',
            helpTakeId:'',
            indentNumbers:'',
            complaintId:'',
            checkCertification:'',      
            status:'',
            remarks:'',
            divideScale:'',
            divideTwoScale:'',
            extensionId:'',  
            statesnum: [{
						value: -1,
						label: '全部'
					},
					{
						value: 1,
						label: '待审核'
					}, {
						value: 2,
						label: '已通过'
					}, {
						value: 3,
						label: '已拒绝'
					}
				],
            statusT:'',
            nickNameT:'',
            phoneT:'', 


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '投诉列表') {
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
            http.get('admin/userExtension/getExtensionList', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    realName: dataContainer.nickNameT,
                    phone:dataContainer.phoneT,
                    status:dataContainer.statusT,
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
        
       
        //拒绝
      function refuseClick(rows) {
        dataContainer.status = ''
        dataContainer.remarks = ''
        dataContainer.divideScale = ''
        dataContainer.divideTwoScale = ''
        dataContainer.extensionId = rows.extensionId
        dataContainer.dialogFormVisible = true
      }
      function refuseto() {
        if (dataContainer.status == '') {
            ElNotification({
                title: '提示',
                message: '请选择审核结果',
                duration: 1800,
                 type: 'warning'
            })
          return
        }
        if (dataContainer.status == 2){
            if (dataContainer.divideScale == '') {
            ElNotification({
                title: '提示',
                message: '请输入一级推广佣金',
                duration: 0,
                 type: 'warning'
            })
          return
        }
        if (dataContainer.divideTwoScale == '') {
            ElNotification({
                title: '提示',
                message: '请输入二级推广佣金',
                duration: 1800,
                 type: 'warning'
            })
          return
        }
        }
        if (dataContainer.status == 3){
            if (dataContainer.remarks == '') {
            ElNotification({
                title: '提示',
                message: '请输入审核理由',
                duration: 1800,
                 type: 'warning'
            })
          return
        }
        }
         

        
            http.post('admin/userExtension/examine', {
                status: dataContainer.status,
                extensionId: dataContainer.extensionId,
                remarks: dataContainer.remarks,
                divideScale: dataContainer.divideScale,
                divideTwoScale: dataContainer.divideTwoScale,
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
            changeBtn,
            refuseClick,
            refuseto,
            updates,



        };
    },
});
</script>



<style lang="scss" scoped>
.recruit-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.recruit-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.recruit-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.recruit-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.recruit-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.recruit-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.recruit-page__filter-input {
    width: 150px;
}

.recruit-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.recruit-page__tabs {
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

.recruit-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.recruit-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.recruit-page__status {
    color: var(--nf-brand);
    cursor: pointer;
}

.recruit-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
