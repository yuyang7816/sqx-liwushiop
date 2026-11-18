<template>
    <div class="banner-page">
        <div class="nf-page-hero banner-page__hero">
            <span class="nf-page-hero__badge">首页装修</span>
            <h2 class="nf-page-hero__title">Banner 与分类管理</h2>
            <p class="nf-page-hero__desc">管理首页轮播图、首页分类、商城轮播及分享图配置</p>
        </div>

        <div class="nf-filter-card banner-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body banner-page__filter-body">
                <el-button type="primary" @click="handleSearch()">刷新</el-button>
                <el-button type="primary" @click="addChangeU()"
                    :disabled="!isAuth('bannerList:add')">添加</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="banner-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="首页轮播图" name="first">
                <div class="nf-table-card banner-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100"></el-table-column>
                        <el-table-column prop="name" label="名称"></el-table-column>
                        <el-table-column prop="imageUrl" label="图片">
                            <template #default="scope">
                                <img :src="scope.row.imageUrl" class="banner-page__thumb" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="url" label="链接" width="100"></el-table-column>
                        <el-table-column prop="description" label="描述" width="100"></el-table-column>
                        <el-table-column prop="sort" label="顺序" width="100"></el-table-column>
                        <el-table-column prop="url" label="状态" width="100">
                            <template #default="scope">
                                <el-switch v-model="scope.row.state" class="ml-2"
                                    :active-value="dataContainer.active"
                                    :inactive-value="dataContainer.inactive" @change="changeBtn(scope.row)" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="banner-page__action-btn"
                                    :disabled="!isAuth('bannerList:update')"
                                    @click="addChangeU(scope.row)">修改</el-button>
                                <el-button size="small" type="danger" class="banner-page__action-btn"
                                    :disabled="!isAuth('bannerList:delete')"
                                    @click="handleDelete(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="banner-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="首页分类" name="second">
                <div class="nf-table-card banner-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100"></el-table-column>
                        <el-table-column prop="name" label="名称"></el-table-column>
                        <el-table-column prop="imageUrl" label="图片">
                            <template #default="scope">
                                <img :src="scope.row.imageUrl" class="banner-page__thumb" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="url" label="链接" width="100"></el-table-column>
                        <el-table-column prop="description" label="描述" width="100"></el-table-column>
                        <el-table-column prop="sort" label="顺序" width="100"></el-table-column>
                        <el-table-column prop="url" label="状态" width="100">
                            <template #default="scope">
                                <el-switch v-model="scope.row.state" class="ml-2"
                                    :active-value="dataContainer.active"
                                    :inactive-value="dataContainer.inactive" @change="changeBtn(scope.row)" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="banner-page__action-btn"
                                    :disabled="!isAuth('bannerList:update')"
                                    @click="addChangeU(scope.row)">修改</el-button>
                                <el-button size="small" type="danger" class="banner-page__action-btn"
                                    :disabled="!isAuth('bannerList:delete')"
                                    @click="handleDelete(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="banner-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="商城首页轮播图" name="shopFirst">
                <div class="nf-table-card banner-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100"></el-table-column>
                        <el-table-column prop="name" label="名称"></el-table-column>
                        <el-table-column prop="imageUrl" label="图片">
                            <template #default="scope">
                                <img :src="scope.row.imageUrl" class="banner-page__thumb" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="url" label="链接" width="100"></el-table-column>
                        <el-table-column prop="description" label="描述" width="100"></el-table-column>
                        <el-table-column prop="sort" label="顺序" width="100"></el-table-column>
                        <el-table-column prop="url" label="状态" width="100">
                            <template #default="scope">
                                <el-switch v-model="scope.row.state" class="ml-2"
                                    :active-value="dataContainer.active"
                                    :inactive-value="dataContainer.inactive" @change="changeBtn(scope.row)" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="banner-page__action-btn"
                                    :disabled="!isAuth('bannerList:update')"
                                    @click="addChangeU(scope.row)">修改</el-button>
                                <el-button size="small" type="danger" class="banner-page__action-btn"
                                    :disabled="!isAuth('bannerList:delete')"
                                    @click="handleDelete(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="banner-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="分享图" name="three">
                <div class="nf-table-card banner-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100"></el-table-column>
                        <el-table-column prop="name" label="名称"></el-table-column>
                        <el-table-column prop="imageUrl" label="图片">
                            <template #default="scope">
                                <img :src="scope.row.imageUrl" class="banner-page__thumb" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="url" label="链接" width="100"></el-table-column>
                        <el-table-column prop="description" label="描述" width="100"></el-table-column>
                        <el-table-column prop="sort" label="顺序" width="100"></el-table-column>
                        <el-table-column prop="url" label="状态" width="100">
                            <template #default="scope">
                                <el-switch v-model="scope.row.state" class="ml-2"
                                    :active-value="dataContainer.active"
                                    :inactive-value="dataContainer.inactive" @change="changeBtn(scope.row)" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="banner-page__action-btn"
                                    :disabled="!isAuth('bannerList:update')"
                                    @click="addChangeU(scope.row)">修改</el-button>
                                <el-button size="small" type="danger" class="banner-page__action-btn"
                                    :disabled="!isAuth('bannerList:delete')"
                                    @click="handleDelete(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="banner-page__pagination">
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
            <div class="banner-page__form-row">
                <span class="banner-page__form-label">名称：</span>
                <el-input class="banner-page__form-input" v-model="dataContainer.name" type="text"
                    placeholder="请输入名称"></el-input>
            </div>
            <div class="banner-page__form-row">
                <span class="banner-page__form-label">链接：</span>
                <el-input class="banner-page__form-input" v-model="dataContainer.url" type="text"
                    placeholder="请输入链接"></el-input>
            </div>
            <div class="banner-page__form-row">
                <span class="banner-page__form-label">描述：</span>
                <el-input class="banner-page__form-input" v-model="dataContainer.description" type="text"
                    placeholder="请输入描述"></el-input>
            </div>
            <div class="banner-page__form-row">
                <span class="banner-page__form-label">排序：</span>
                <el-input class="banner-page__form-input" v-model="dataContainer.sort" type="text"
                    placeholder="请输入排序"></el-input>
            </div>
            <div class="banner-page__form-row">
                <span class="banner-page__form-label">图像：</span>
                <div class="banner-page__upload-wrap">
                    <el-upload class="avatar-uploader banner-page__uploader" v-model="dataContainer.imageUrl"
                        action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                        :on-success="handleAvatarSuccess1">
                        <img v-if="dataContainer.imageUrl" :src="dataContainer.imageUrl"
                            class="banner-page__upload-preview" />
                        <SvgIcon v-else class="banner-page__upload-icon" name="svg:plus.svg"></SvgIcon>
                    </el-upload>
                </div>
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
            tableData: [],
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


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '首页轮播图') {
                dataContainer.classify = 1
                dataSelect();
            }
            if (event.srcElement.innerText == '首页分类') {
                dataContainer.classify = 2
                dataSelect();
            }
            if (event.srcElement.innerText == '商城首页轮播图') {
                dataContainer.classify = 3
                dataSelect();
            }

            if (event.srcElement.innerText == '分享图') {
                dataContainer.classify = 5
                dataSelect();
            }
        }
        /**
         * 搜索
         */
        function handleSearch() {
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.post('admin/banner/page', {
                // params: {
                page: dataContainer.page,
                limit: dataContainer.limit,
                classify: dataContainer.classify,
                name: '',
                // }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.tableData = []
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
            http.post('admin/banner/updateState', {

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
                dataContainer.id = row.id
                dataContainer.name = row.name
                dataContainer.imageUrl = row.imageUrl
                dataContainer.url = row.url
                dataContainer.sort = row.sort
                dataContainer.description = row.description
                dataContainer.state = row.state
            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.name = ''
                dataContainer.imageUrl = ''
                dataContainer.url = ''
                dataContainer.sort = ''
                dataContainer.description = ''
                dataContainer.state = ''
            }
            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.imageUrl = file.data.path
        }
        function StairNoticeToU() {

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/banner/save'
            } else {
                dataContainer.routeUrl = 'admin/banner/update'
            }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                name: dataContainer.name,
                imageUrl: dataContainer.imageUrl,
                classify: dataContainer.classify,
                url: dataContainer.url,
                sort: dataContainer.sort,
                description: dataContainer.description,
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
            ElMessageBox.confirm('确定删除数据?', '提示', {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                distinguishCancelAndClose: true,
            }).then(() => {
                http.post('admin/banner/delete', {
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
            })
        }
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
            handleAvatarSuccess1,
            StairNoticeToU,
            handleDelete,

        };
    },
});
</script>



<style lang="scss" scoped>
.banner-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.banner-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.banner-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.banner-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.banner-page__tabs {
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

.banner-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.banner-page__thumb {
    width: 60px;
    height: 60px;
    object-fit: cover;
    border-radius: var(--nf-radius-sm);
    border: 1px solid var(--nf-border);
}

.banner-page__action-btn {
    margin: 4px 0;
}

.banner-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.banner-page__form-row {
    margin-bottom: var(--nf-space-sm);
}

.banner-page__form-label {
    width: 200px;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
}

.banner-page__form-input {
    width: 50%;
}

.banner-page__upload-wrap {
    display: inline-block;
    vertical-align: top;
}

.banner-page__uploader {
    width: 148px;
    height: 148px;
    border: 1px dashed var(--nf-border);
    border-radius: var(--nf-radius-sm);
    text-align: center;
    line-height: 148px;
    cursor: pointer;
    transition: border-color 0.2s;

    &:hover {
        border-color: var(--nf-brand);
    }
}

.banner-page__upload-preview {
    width: 148px;
    height: 148px;
    object-fit: cover;
    border-radius: var(--nf-radius-sm);
}

.banner-page__upload-icon {
    width: 23px;
    height: 23px;
    color: var(--nf-text-secondary);
}
</style>
