<template>
    <div class="classify-admin-page">
        <div class="nf-page-hero classify-admin-page__hero">
            <span class="nf-page-hero__badge">商品分类</span>
            <h2 class="nf-page-hero__title">分类列表</h2>
            <p class="nf-page-hero__desc">管理团购商品分类信息，支持新增、修改与删除</p>
        </div>

        <div class="nf-filter-card classify-admin-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body classify-admin-page__filter-body">
                <el-button type="primary" @click="handleSearch()">刷新</el-button>
                <el-button type="primary" @click="addChangeU()"
                    :disabled="!isAuth('classifyAdmin:add')">新增</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="classify-admin-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="分类列表" name="first">
                <div class="nf-table-card classify-admin-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="80">
                        </el-table-column>
                        <el-table-column prop="name" label="分类名称">
                        </el-table-column>
                        <el-table-column prop="img" label="图片">
                            <template #default="scope">
                                <div v-if="scope.row.img == null || scope.row.img == ''">
                                    暂无图片
                                </div>
                                <div v-else>
                                    <el-popover placement="top-start" title="" trigger="hover"
                                        popper-style="width:420px;">
                                        <template #reference>
                                            <img class="classify-admin-page__thumb" :src="scope.row.img" alt="">
                                        </template>
                                        <template #default>
                                            <img class="classify-admin-page__preview" :src="scope.row.img" alt="">
                                        </template>
                                    </el-popover>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="sort" label="排序" width="150">
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="170">
                        </el-table-column>
                        <el-table-column label="操作" width="160">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="classify-admin-page__action-btn"
                                    @click="addChangeU(scope.row)"
                                    :disabled="!isAuth('classifyAdmin:update')">修改</el-button>
                                <el-button size="small" type="danger" class="classify-admin-page__action-btn"
                                    @click="handleDelete(scope.row)"
                                    :disabled="!isAuth('classifyAdmin:delete')">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="classify-admin-page__pagination">
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
            <div class="classify-admin-page__form-row">
                <span class="classify-admin-page__form-label">分类名称:</span>
                <el-input class="classify-admin-page__form-input" v-model="dataContainer.name" type="text"
                    placeholder="请输入分类名称"></el-input>
            </div>
            <div class="classify-admin-page__form-row">
                <span class="classify-admin-page__form-label">图片：</span>
                <div class="classify-admin-page__upload-wrap">
                    <el-upload class="avatar-uploader classify-admin-page__uploader" v-model="dataContainer.img"
                        action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                        :on-success="handleAvatarSuccess1">
                        <img v-if="dataContainer.img" :src="dataContainer.img" class="classify-admin-page__avatar" />
                        <SvgIcon v-else class="classify-admin-page__upload-icon" name="svg:plus.svg">
                        </SvgIcon>
                    </el-upload>
                </div>
            </div>
            <div class="classify-admin-page__form-row">
                <span class="classify-admin-page__form-label">排序：</span>
                <el-input class="classify-admin-page__form-input" v-model="dataContainer.sort" type="text"
                    placeholder="请输入排序"></el-input>
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
import { ref, defineComponent, reactive, toRef } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { messageSuccess, messageError } from '@/action/messagePrompt';
import { findTag, deleteTags } from '@/action/tagListTools';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import { Filter } from '@element-plus/icons-vue';
import {
    // provinceAndCityData,
    regionData,
    codeToText,
    // provinceAndCityDataPlus,
    // regionDataPlus

} from 'element-china-area-data'
export default defineComponent({
    components: {
        SvgIcon,
        Filter,
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
            routeUrl: '',
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
            nickNameT: '',
            phoneT: '',
            dialogFormVisibleSh: false,
            id: '',
            name: '',
            img: '',
            sort: '',
            parentId: 0,



        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '分类列表') {
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
            http.get('admin/productType/list', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,

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

        /**
         * 添加、修改弹框
         */
        function addChangeU(row) {
            if (row) {

                dataContainer.titles = '修改'
                dataContainer.id = row.id
                if (row.img) {
                    dataContainer.img = row.img
                } else {
                    dataContainer.img = ''
                }

                dataContainer.name = row.name
                dataContainer.sort = row.sort
            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.img = ''
                dataContainer.name = ''
                dataContainer.sort = ''
            }
            dataContainer.dialogFormVisibleU = true

        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.img = file.data.path
        }
        function StairNoticeToU() {
            if (dataContainer.name === '') {
                ElNotification({
                    title: '提示',
                    message: '请输入分类名称',
                    type: 'warning',
                })
                return
            }

            if (dataContainer.img == '') {
                ElNotification({
                    title: '提示',
                    message: '请上传图片',
                    type: 'warning',
                })
                return
            }


            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/productType/save'
            } else {
                dataContainer.routeUrl = 'admin/productType/update'
            }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                img: dataContainer.img,
                name: dataContainer.name,
                sort: dataContainer.sort,
                parentId: dataContainer.parentId,
            }, {
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
                http.get('admin/productType/delete', {
                    params: {
                        id: data.id
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
            StairNoticeToU,
            handleDelete,
            handleAvatarSuccess1,

        };
    },
});
</script>



<style lang="scss" scoped>
.classify-admin-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.classify-admin-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.classify-admin-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.classify-admin-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.classify-admin-page__tabs {
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

.classify-admin-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.classify-admin-page__action-btn {
    margin: 4px 0;
}

.classify-admin-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.classify-admin-page__thumb {
    width: 50px;
    height: 50px;
    margin: 3px;
    object-fit: cover;
    border-radius: var(--nf-radius-sm);
}

.classify-admin-page__preview {
    width: 400px;
    height: auto;
    display: block;
}

.classify-admin-page__form-row {
    margin-bottom: var(--nf-space-sm);
}

.classify-admin-page__form-label {
    width: 200px;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
}

.classify-admin-page__form-input {
    width: 50%;
}

.classify-admin-page__upload-wrap {
    display: inline-block;
    vertical-align: top;
}

.classify-admin-page__uploader {
    width: 148px;
    height: 148px;
    border: 1px dashed var(--nf-border);
    border-radius: var(--nf-radius-sm);
    text-align: center;
    line-height: 148px;
    color: var(--nf-text-placeholder);
}

.classify-admin-page__avatar {
    width: 148px;
    height: 148px;
    object-fit: cover;
    display: block;
}

.classify-admin-page__upload-icon {
    width: 23px;
    height: 23px;
    color: var(--nf-text-placeholder);
}
</style>
