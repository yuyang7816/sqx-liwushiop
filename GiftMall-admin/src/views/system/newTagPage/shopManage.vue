<template>
    <div class="page-container">
        <el-tabs v-model="dataContainer.activeName" class="demo-tabs" @tab-click="handleClick">
            <el-tab-pane label="动态列表" name="first">
                <div style="margin: 20px;">
                    <div style="display: inline-block;margin: 5px;">
                        <span>发布用户</span>
                        <el-input v-model="dataContainer.userNameT" placeholder="请输入发布用户"
                            @keydown.enter.native="handleSearch" style="width: 150px;" />
                    </div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>位置：</span>
                        <el-input v-model="dataContainer.locationT" placeholder="请输入位置"
                            @keydown.enter.native="handleSearch" style="width: 150px;" />
                    </div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>权限设置：</span>
                        <el-select v-model="dataContainer.privacyT" class="m-2" placeholder="请选择权限设置"
                            style="width: 150px;" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.statusData" :key="index"
                                :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <el-button size="small" type="primary" plain @click="handleSearch()">查询</el-button>
                    <el-button size="small" type="warning" plain @click="clearSearch()">重置</el-button>
                    <!-- <el-button size="small" type="primary" @click="addChangeU()">添加</el-button> -->
                </div>
                <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                    <el-table-column prop="userName" label="发布用户" fixed="left">
                        <template #default="scope">
                            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.userId)">
                                {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="avatar" label="头像" width="100">
                        <template #default="scope">
                            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="imgUrlList" label="图片" width="300">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                                v-for="(item, index) in scope.row.imgUrlList" :key="index">
                                <template #reference>
                                    <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                                </template>
                                <template #default style="width: 420px;">
                                    <img style="width: 400px; height: auto;" :src="item" alt="">
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>
                    <el-table-column prop="videoUrlList" label="视频" width="210">
                        <template #default="scope">
                            <div v-for="(item, index) in scope.row.videoUrlList" :key="index">
                                <video style="width: 180px;height: 100px;" :src="item" type="audio/wav"
                                    controls="controls"></video>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="location" label="位置" width="100"></el-table-column>
                    <el-table-column prop="content" label="文字描述" width="300"></el-table-column>
                    <el-table-column prop="privacy" label="权限设置" width="100" fixed="right">
                        <template #default="scope">
                            <span v-if="scope.row.privacy == 0">仅自己可见</span>
                            <span v-if="scope.row.privacy == 1">仅好友</span>
                            <span v-if="scope.row.privacy == 2">公开</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100" fixed="right">
                        <template #default="scope">
                            <el-button size="small" type="primary" @click="dianzanChange(scope.row)"
                                style="margin: 5px;">点赞列表</el-button>
                            <el-button size="small" type="primary" @click="pinglunChange(scope.row)"
                                style="margin: 5px;">评论列表</el-button>
                            <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                                style="margin: 5px;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="text-align: center;margin-top: 10px;">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.tableData.total">
                    </el-pagination>
                </div>
            </el-tab-pane>

            <!-- 添加/修改弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">名称：</span>
                    <el-input style="width:50%;" v-model="dataContainer.name" type="text"
                        placeholder="请输入名称"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">链接：</span>
                    <el-input style="width:50%;" v-model="dataContainer.url" type="text" placeholder="请输入链接"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">描述：</span>
                    <el-input style="width:50%;" v-model="dataContainer.description" type="text"
                        placeholder="请输入描述"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">排序：</span>
                    <el-input style="width:50%;" v-model="dataContainer.sort" type="text"
                        placeholder="请输入排序"></el-input>
                </div>

                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">图像：</span>
                    <div style="display: inline-block;">
                        <el-upload class="avatar-uploader" v-model="dataContainer.imageUrl"
                            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess1">
                            <img v-if="dataContainer.imageUrl" :src="dataContainer.imageUrl" class="avatar"
                                style="width: 148px;height: 148px;" />
                            <SvgIcon v-else style="width:23px;height:23px;color: #efefef;" name="svg:plus.svg">
                            </SvgIcon>
                            <!-- <Plus v-else style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"/> -->
                            <!-- <Edit style="width: 1em; height: 1em; margin-right: 8px" /> -->

                        </el-upload>
                    </div>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleU = false"
                            style="margin: 10px;">取消</el-button>
                        <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
                    </span>
                </template>
            </el-dialog>
            <!-- 点赞列表 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleDz" title="点赞列表" width="80%" center>
                <el-table :data="dataContainer.tableDataDz" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="userId" label="编号" width="100"></el-table-column>
                    <el-table-column prop="userName" label="点赞用户">
                        <template #default="scope">
                            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.userId)">
                                {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="avatar" label="用户图头">
                        <template #default="scope">
                            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
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
                            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.userId)">
                                {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="avatar" label="用户图头" width="100">
                        <template #default="scope">
                            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="content" label="评论内容"></el-table-column>
                    <el-table-column prop="createTime" label="评论时间" width="180"></el-table-column>
                </el-table>
            </el-dialog>
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
export default defineComponent({
    components: {
        SvgIcon,
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
            tableDataDz: [],
            tableDataPl: [],
            userNameT: '',
            locationT: '',
            privacyT: '',
            statusData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '仅自己可见',
                    value: 0
                },
                {
                    label: '仅好友',
                    value: 1
                },
                {
                    label: '公开',
                    value: 2
                }
            ],


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '动态列表') {

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
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('admin/posts/page', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    userName: dataContainer.userNameT,
                    location: dataContainer.locationT,
                    privacy: dataContainer.privacyT,
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
                dataContainer.routeUrl = 'admin/base/banner/save'
            } else {
                dataContainer.routeUrl = 'admin/base/banner/update'
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

        };
    },
});
</script>



<style lang="scss" scoped>
.new-tag-page-view {
    display: flex;
    flex-direction: column;
    width: 100%;
    justify-content: center;
    align-items: center;

    >.container {
        // background-color: white;
        width: 100%;
        max-width: 1200px;
        height: fit-content;
        border-radius: 12px;
        padding: 60px 15px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        align-items: center;
        background-image: url(https://cn.bing.com/th?id=OHR.MtPrevostDuncan_ZH-CN2333619635_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp);
        background-size: cover;
        background-repeat: no-repeat;
        box-shadow: 1px 1px 3px 0 rgba(0, 0, 0, 0.319);
        background-position: center top;

        >.title {
            background: -webkit-linear-gradient(120deg, var(--nf-brand-lite2) 30%, var(--nf-brand));
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
            font-size: 50px;
            margin-bottom: 55px;
            letter-spacing: 5px;
            font-weight: bold;
        }

        >.search {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            align-items: center;
            width: 100%;
            margin-bottom: 30px;

            :deep(.el-input) {
                width: 100%;
                max-width: 700px;
                height: 50px;
                border: 2px solid rgb(3, 3, 3);
                box-shadow: 1px 1px 3px 0 rgba(0, 0, 0, 0.319);
                border-radius: 12px;
                overflow: hidden;
                font-size: 15px;
                padding: 0 15px;
                backdrop-filter: blur(12px);
                background-color: #ffffff3d;

                .el-input__wrapper {
                    box-shadow: none !important;
                    padding: 0 !important;

                    input {
                        color: rgb(255, 255, 255);
                    }
                }

                .el-input__suffix {
                    .el-icon {
                        font-size: 20px !important;
                        color: #3c3c3c !important;
                    }
                }
            }
        }

        >.item-list {
            display: flex;
            flex-direction: row;
            justify-content: center;
            flex-wrap: wrap;

            >.item {
                padding: 30px 30px;

                >.container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    position: relative;
                    cursor: pointer;
                    color: white;

                    &:hover {
                        >.icon {
                            transform: scale(1.1);
                            box-shadow: 3px 3px rgba(0, 0, 0, 0.1);
                        }

                        >.name {
                            color: #5240ff;
                            font-weight: bold;
                        }
                    }

                    >.icon {
                        width: 60px;
                        height: 60px;
                        display: flex;
                        flex-direction: row;
                        align-items: center;
                        justify-content: center;
                        background-color: rgb(240, 240, 240);
                        border-radius: 50%;
                        transition: all 0.2s;
                        border: 2px solid rgb(3, 3, 3);
                        box-sizing: border-box;
                        box-shadow: 1px 1px 1px 0 rgba(0, 0, 0, 0.653);
                        color: rgb(56, 56, 56);
                    }

                    >.name {
                        transition: all 0.2s;
                        width: 150px;
                        text-align: center;
                        position: absolute;
                        top: calc(100% + 10px);
                    }
                }
            }
        }
    }
}
</style>
