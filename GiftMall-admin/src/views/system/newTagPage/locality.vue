<template>
    <div class="locality-page">
        <div class="nf-page-hero locality-page__hero">
            <span class="nf-page-hero__badge">卡片管理</span>
            <h2 class="nf-page-hero__title">卡片与分类配置</h2>
            <p class="nf-page-hero__desc">管理卡片列表及卡片分类，支持按状态、分类筛选与启用开关</p>
        </div>

        <div class="nf-filter-card locality-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>{{ dataContainer.activeName === 'first' ? '筛选条件' : '快捷操作' }}</span>
            </div>
            <div class="nf-filter-card__body locality-page__filter-body">
                <template v-if="dataContainer.activeName === 'first'">
                    <div class="locality-page__filter-item">
                        <span class="locality-page__filter-label">状态：</span>
                        <el-select v-model="dataContainer.statusT" placeholder="请选择状态"
                            class="locality-page__filter-input" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.statusData" :key="index"
                                :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="locality-page__filter-item">
                        <span class="locality-page__filter-label">分类：</span>
                        <el-select v-model="dataContainer.inviteVerifyT" placeholder="请选择分类"
                            class="locality-page__filter-input" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.tableDataClassPage" :key="index"
                                :label="item.cardCategoryName" :value="item.cardCategoryId" />
                        </el-select>
                    </div>
                    <div class="locality-page__filter-actions">
                        <el-button type="primary" @click="handleSearch()">查询</el-button>
                        <el-button @click="clearSearch()">重置</el-button>
                        <el-button type="primary" @click="addChangeU()"
                            :disabled="!isAuth('locality:add')">添加</el-button>
                    </div>
                </template>
                <template v-else>
                    <div class="locality-page__filter-actions">
                        <el-button type="primary" @click="handleSearch()">刷新</el-button>
                        <el-button type="primary" @click="addChangeC()"
                            :disabled="!isAuth('locality:add')">添加</el-button>
                    </div>
                </template>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="locality-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="卡片列表" name="first">
                <div class="nf-table-card locality-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="cardId" label="编号" width="100"></el-table-column>
                        <el-table-column prop="cardCategoryName" label="分类">
                        </el-table-column>
                        <el-table-column prop="cardImg" label="图片">
                            <template #default="scope">
                                <img v-if="scope.row.cardImg" :src="scope.row.cardImg"
                                    class="locality-page__thumb" />
                                <span v-else> - </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="sort" label="排序" width="100"></el-table-column>
                        <el-table-column prop="status" label="状态" width="100" fixed="right">
                            <template #default="scope">
                                <el-switch v-model="scope.row.status" class="ml-2"
                                    :active-value="dataContainer.active" :inactive-value="dataContainer.inactive"
                                    @change="changeBtn(scope.row)"
                                    style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="updateTime" label="修改时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100" fixed="right">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                    class="locality-page__action-btn"
                                    :disabled="!isAuth('locality:update')">修改</el-button>
                                <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                                    class="locality-page__action-btn"
                                    :disabled="!isAuth('locality:delete')">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="locality-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="卡片分类" name="second">
                <div class="nf-table-card locality-page__table">
                    <el-table :data="dataContainer.tableDataClass.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="cardCategoryId" label="编号" width="100"></el-table-column>
                        <el-table-column prop="cardCategoryName" label="分类名称"></el-table-column>
                        <el-table-column prop="sort" label="排序"></el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeC(scope.row)"
                                    class="locality-page__action-btn"
                                    :disabled="!isAuth('locality:update')">修改</el-button>
                                <el-button size="small" type="danger" @click="handleDeleteC(scope.row)"
                                    class="locality-page__action-btn"
                                    :disabled="!isAuth('locality:delete')">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="locality-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

            <!-- 添加/修改 卡片 弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">分类：</span>
                    <el-select v-model="dataContainer.cardCategoryId" class="m-2" placeholder="请选择分类"
                        style="width: 50%;">
                        <el-option v-for="(item, index) in dataContainer.tableDataClassPage" :key="index"
                            :label="item.cardCategoryName" :value="item.cardCategoryId" />
                    </el-select>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">排序：</span>
                    <el-input style="width:50%;" v-model="dataContainer.sort" type="text"
                        placeholder="请输入排序"></el-input>
                </div>

                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">图像：</span>
                    <div style="display: inline-block;">
                        <el-upload class="avatar-uploader" v-model="dataContainer.cardImg"
                            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess1">
                            <img v-if="dataContainer.cardImg" :src="dataContainer.cardImg" class="avatar"
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
            <!-- 添加/修改 分类 弹框  -->
            <el-dialog v-model="dataContainer.dialogFormVisibleCy" :title="dataContainer.titles" width="80%" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">分类名称：</span>
                    <el-input style="width:50%;" v-model="dataContainer.name" type="text"
                        placeholder="请输入分类名称"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">排序：</span>
                    <el-input style="width:50%;" v-model="dataContainer.sort" type="text"
                        placeholder="请输入排序"></el-input>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleU = false"
                            style="margin: 10px;">取消</el-button>
                        <el-button type="primary" @click="StairNoticeToC" style="margin: 10px;">确认</el-button>
                    </span>
                </template>
            </el-dialog>
            <!--申请信息 弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleSq" title="申请信息" width="80%" center>
                <div style="margin: 10px;">
                    <div style="display: inline-block;margin: 5px;">
                        <span>用户昵称：</span>
                        <el-input v-model="dataContainer.userNameC" placeholder="请输入用户昵称"
                            @keydown.enter.native="handleSearchSq" style="width: 150px;" />
                    </div>
                    <!-- <div style="display: inline-block;margin: 5px;">
                        <span>手机号：</span>
                        <el-input v-model="dataContainer.phoneC" placeholder="请输入手机号"
                            @keydown.enter.native="handleSearchSq" style="width: 150px;" />
                    </div> -->
                    <el-button size="small" type="primary" plain @click="handleSearchSq()">查询</el-button>
                    <el-button size="small" type="primary" plain @click="clearSearchSq()">查询</el-button>
                </div>
                <el-table :data="dataContainer.tableDataSq" v-loading="dataContainer.loadingCy" style="width: 100%">
                    <el-table-column prop="userId" label="编号" width="100"></el-table-column>
                    <el-table-column prop="userName" label="用户昵称">
                        <template #default="scope">
                            <span v-if="scope.row.userName" class="locality-page__link"
                                @click="updates(scope.row.userId)">
                                {{ scope.row.userName ? scope.row.userName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="userAvatar" label="用户图头">
                        <template #default="scope">
                            <img :src="scope.row.userAvatar" style="width: 60px;height: 60px;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
                    <el-table-column prop="description" label="审核说明" width="280"></el-table-column>
                    <el-table-column label="状态" width="100" fixed="right">
                        <template #default="scope">
                            <div v-if="scope.row.status == 1">待验证</div>
                            <div v-if="scope.row.status == 2">同意</div>
                            <div v-if="scope.row.status == 3">拒绝</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="100" fixed="right">
                        <template #default="scope">
                            <el-button size="small" type="danger" v-if="scope.row.status == 1"
                                @click="shenqingBtn(scope.row)" style="margin: 5px;">审核</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>
            <!--聊天记录 弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleLt" title="聊天记录" width="80%" center>
                <div style="margin: 10px;">
                    <div style="display: inline-block;margin: 5px;">
                        <span>用户昵称：</span>
                        <el-input v-model="dataContainer.userNameC" placeholder="请输入用户昵称"
                            @keydown.enter.native="handleSearchLt" style="width: 150px;" />
                    </div>
                    <!-- <div style="display: inline-block;margin: 5px;">
                        <span>手机号：</span>
                        <el-input v-model="dataContainer.phoneC" placeholder="请输入手机号"
                            @keydown.enter.native="handleSearchLt" style="width: 150px;" />
                    </div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>内容类型：</span>
                        <el-select v-model="dataContainer.typeC" class="m-2" placeholder="请选择内容类型" style="width: 150px;"
                            @change="handleSearchLt">
                            <el-option v-for="(item, index) in dataContainer.typeData" :key="index" :label="item.label"
                                :value="item.value" />
                        </el-select>
                    </div> -->
                    <div style="display: inline-block;margin: 5px;">
                        <span>聊天内容：</span>
                        <el-input v-model="dataContainer.contentC" placeholder="请输入聊天内容"
                            @keydown.enter.native="handleSearchLt" style="width: 150px;" />
                    </div>

                    <el-button size="small" type="primary" plain @click="handleSearchLt()">查询</el-button>
                    <el-button size="small" type="primary" plain @click="clearSearchLt()">重置</el-button>
                </div>
                <el-table :data="dataContainer.tableDataLt.records" v-loading="dataContainer.loadingCy">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="senderUserName" label="用户昵称">
                        <template #default="scope">
                            <span v-if="scope.row.senderUserName" class="locality-page__link"
                                @click="updates(scope.row.senderUserId)">
                                {{ scope.row.senderUserName ? scope.row.senderUserName : '未绑定' }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="senderAvatar" label="用户图头">
                        <template #default="scope">
                            <img :src="scope.row.senderAvatar" style="width: 60px;height: 60px;" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="type" label="内容类型">
                        <template #default="scope">
                            <span v-if="scope.row.type == 1">普通消息</span>
                            <span v-if="scope.row.type == 2">表情</span>
                            <span v-if="scope.row.type == 3">图片</span>
                            <span v-if="scope.row.type == 4">视频</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="content" label="聊天内容">
                        <template #default="scope">
                            <span v-if="scope.row.type == 1">{{ scope.row.content }}</span>
                            <span v-if="scope.row.type == 2">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content"
                                            alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                                    </template>
                                </el-popover>
                            </span>
                            <span v-if="scope.row.type == 3">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content"
                                            alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                                    </template>
                                </el-popover>
                            </span>
                            <span v-if="scope.row.type == 4">
                                <video style="width: 180px;height: 100px;" :src="scope.row.content" type="audio/wav"
                                    controls="controls"></video>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                </el-table>
                <div style="text-align: center;margin-top: 10px;">
                    <el-pagination @size-change="handleSizeChangeLt" @current-change="handleCurrentChangeLt"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.tableDataLt.total">
                    </el-pagination>
                </div>
            </el-dialog>
            <!-- 审核 弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleSh" title="审核" width="80%" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">审核结果：</span>
                    <el-radio-group v-model="dataContainer.status" class="ml-4">

                        <el-radio :label="2">同意</el-radio>
                        <el-radio :label="3">拒绝</el-radio>
                    </el-radio-group>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">审核说明：</span>
                    <el-input style="width:50%;" v-model="dataContainer.description" type="textarea" rows="4"
                        placeholder="请输入审核说明"></el-input>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleSh = false"
                            style="margin: 10px;">取消</el-button>
                        <el-button type="primary" @click="StairNoticeToSh" style="margin: 10px;">确认</el-button>
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
            page: 1,
            limit: 10,
            classify: 1,
            dialogFormVisibleU: false,
            titles: '添加',
            id: 0,
            cardImg: "",
            cardCategoryId: '',
            sort: 0,
            description: "",
            state: 0,
            routeUrl: '',
            active: 0,
            inactive: 1,
            userNameT: '',
            nameT: '',
            statusT: '',
            inviteVerifyT: '',
            statusData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '正常',
                    value: 1
                },
                {
                    label: '禁用',
                    value: 2
                }
            ],
            inviteVerifyData: [],
            dialogFormVisibleCy: false,
            dialogFormVisibleSq: false,
            dialogFormVisibleLt: false,
            loadingCy: false,
            pageC: 1,
            limitC: 10,
            userNameC: '',
            phoneC: '',
            typeC: '',
            contentC: '',
            tableDataSq: [],
            tableDataCy: [],
            loadingLt: [],
            typeData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '普通消息',
                    value: 1
                },
                {
                    label: '表情',
                    value: 2
                },
                {
                    label: '图片',
                    value: 3
                },
                {
                    label: '视频',
                    value: 4
                }
            ],
            status: 1,
            description: '',
            dialogFormVisibleSh: false,
            userId: '',
            groupRequestsId: '',
            tableDataLt: {},
            tableDataClass: {},
            tableDataClassPage: []




        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '卡片列表') {
                dataSelect();
            }
            if (event.srcElement.innerText == '卡片分类') {
                dataSelectCy();
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
            dataContainer.inviteVerifyT = ''
            dataContainer.statusT = ''
            dataContainer.nameT = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('admin/card/list', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    cardCategoryId: dataContainer.inviteVerifyT,
                    status: dataContainer.statusT,
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
        dataSelectCypage()
        /**  分页事件*/
        function handleSizeChange(val) {
            dataContainer.limit = val
            dataSelect()
        }
        function handleCurrentChange(val) {
            dataContainer.page = val
            dataSelect()
        }
        function handleSizeChangeLt(val) {
            dataContainer.limit = val
            dataSelectLt()
        }
        function handleCurrentChangeLt(val) {
            dataContainer.page = val
            dataSelectLt()
        }
        function changeBtn(row) {
            http.get('admin/card/enable', {
                params: {
                    cardId: row.cardId,
                    status: row.status,
                }
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
         * 添加、修改 卡片弹框
         */
        function addChangeU(row) {
            if (row) {
                dataContainer.titles = '修改'
                dataContainer.id = row.cardId
                dataContainer.cardCategoryId = row.cardCategoryId
                dataContainer.cardImg = row.cardImg
                dataContainer.sort = row.sort
            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.cardCategoryId = ''
                dataContainer.cardImg = ''
                dataContainer.sort = ''
            }
            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.cardImg = file.data.path
        }
        function StairNoticeToU() {

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/card/save'
            } else {
                dataContainer.routeUrl = 'admin/card/update'
            }
            http.post(dataContainer.routeUrl, {
                cardId: dataContainer.id,
                cardCategoryId: dataContainer.cardCategoryId,
                cardImg: dataContainer.cardImg,
                sort: dataContainer.sort,
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
            http.get('admin/card/delete', {
                params: {
                    cardId: data.cardId
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
        /** 跳转用户 */
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
        /** 群成员 弹框 */
        function cyChange(row) {
            dataContainer.id = row.id
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataSelectCy();
            dataContainer.dialogFormVisibleCy = true

        }

        /** 分类 数据 */
        function dataSelectCy() {
            dataContainer.loadingCy = true
            http.get('admin/cardCategory/list', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    cardCategoryName: '',
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loadingCy = false;
                dataContainer.tableDataCy = {}
                let returnData = res.data
                dataContainer.tableDataClass = returnData
            })
        }
        function dataSelectCypage() {
            http.get('admin/cardCategory/list', {
                params: {
                    page: 1,
                    limit: 50,
                    cardCategoryName: '',
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.tableDataCy = []
                let returnData = res.data
                dataContainer.tableDataClassPage = returnData.records
            })
        }
        /**
        * 添加、修改 分类弹框
        */
        function addChangeC(row) {
            if (row) {
                dataContainer.titles = '修改'
                dataContainer.id = row.cardCategoryId
                dataContainer.name = row.cardCategoryName
                dataContainer.sort = row.sort
            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.name = ''
                dataContainer.sort = ''
            }
            dataContainer.dialogFormVisibleCy = true
        }
        function StairNoticeToC() {
            if (dataContainer.name == '') {
                ElNotification({
                    title: '提示',
                    message: '请输入分类名称',
                    duration: 0,
                    type: 'warning'
                })
                return
            }
            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/cardCategory/save'
            } else {
                dataContainer.routeUrl = 'admin/cardCategory/update'
            }
            http.post(dataContainer.routeUrl, {
                cardCategoryId: dataContainer.id,
                cardCategoryName: dataContainer.name,
                sort: dataContainer.sort,
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisibleCy = false
                            dataSelectCy()
                        }
                    })
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'warning',
                        onClose: () => {
                        }
                    })
                }
            })
        }
        /** 删除 分类 */
        function handleDeleteC(data) {
            ElMessageBox.confirm('确定删除数据?', '提示', {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                distinguishCancelAndClose: true,
            }).then(() => {
                http.get('admin/cardCategory/delete', {
                    params: {
                        cardCategoryId: data.cardCategoryId
                    }
                }, {}).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        messageSuccess('删除成功');
                        dataSelectCy();
                    } else {
                        messageError(res.msg);
                    }
                })
            })
        }
        function handleSearchCy() {
            dataSelectCy();
        }
        function clearSearchCy() {
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataSelectCy();
        }
        /** 踢出群聊 */
        function tqDelete(data) {
            http.post('admin/group/kickOutGroup', {
                groupId: dataContainer.id,
                targetUserId: data.userId
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    messageSuccess('操作成功');
                    dataSelectCy();
                } else {
                    messageError(res.msg);
                }
            })
        }
        /** 申请信息 弹框 */
        function sqChange(row) {
            dataContainer.id = row.id
            dataSelectSq();
            dataContainer.dialogFormVisibleSq = true

        }
        /** 申请信息 数据 */
        function dataSelectSq() {
            dataContainer.loadingCy = true
            http.get('admin/group/groupRequestsList', {
                params: {
                    groupId: dataContainer.id,
                    userName: dataContainer.userNameC,
                    phone: dataContainer.phoneC,
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loadingCy = false;
                dataContainer.tableDataSq = []
                let returnData = res.data
                dataContainer.tableDataSq = returnData
            })
        }
        function handleSearchSq() {
            dataSelectSq();
        }
        function clearSearchSq() {
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''

            dataSelectSq();
        }
        function shenqingBtn(row) {
            dataContainer.groupRequestsId = row.id
            dataContainer.userId = row.userId
            dataContainer.status = ''
            dataContainer.description = ''
            dataContainer.dialogFormVisibleSh = true
        }
        function StairNoticeToSh() {

            http.post('admin/group/updateGroupRequests', {
                groupRequestsId: dataContainer.groupRequestsId,
                userId: dataContainer.userId,
                status: dataContainer.status,
                description: dataContainer.description,
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisibleSh = false
                            dataSelectSq()
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


        /** 聊天记录 弹框 */
        function ltChange(row) {
            dataContainer.id = row.id
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataContainer.typeC = ''
            dataContainer.contentC = ''
            dataContainer.pageC = 1
            dataContainer.dialogFormVisibleLt = true
            dataSelectLt();
        }
        /** 聊天记录 数据 */
        function dataSelectLt() {
            dataContainer.loadingCy = true
            http.get('admin/history/groupHistory', {
                params: {
                    page: dataContainer.pageC,
                    limit: dataContainer.limitC,
                    // groupId: dataContainer.id,
                    userName: dataContainer.userNameC,
                    // phone: dataContainer.phoneC,
                    // targetType:2,
                    // type:dataContainer.typeC,
                    content: dataContainer.contentC,
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loadingCy = false;
                dataContainer.tableDataLt = {}
                let returnData = res.data
                dataContainer.tableDataLt = returnData
            })
        }
        function handleSearchLt() {
            dataSelectLt();
        }
        function clearSearchLt() {
            dataContainer.pageC = 1
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataContainer.typeC = ''
            dataContainer.contentC = ''
            dataSelectLt();
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
            clearSearch,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            handleAvatarSuccess1,
            StairNoticeToU,
            handleDelete,
            updates,
            cyChange,
            sqChange,
            ltChange,
            dataSelectCy,
            handleSearchCy,
            clearSearchCy,
            dataSelectSq,
            handleSearchSq,
            clearSearchSq,
            dataSelectLt,
            handleSearchLt,
            clearSearchLt,
            tqDelete,
            shenqingBtn,
            StairNoticeToSh,
            handleSizeChangeLt,
            handleCurrentChangeLt,

            dataSelectCypage,
            addChangeC,
            StairNoticeToC,
            handleDeleteC,

        };
    },
});
</script>



<style lang="scss" scoped>
.locality-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.locality-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.locality-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.locality-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.locality-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.locality-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.locality-page__filter-input {
    width: 150px;
}

.locality-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.locality-page__tabs {
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

.locality-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.locality-page__action-btn {
    margin: 4px 0;
}

.locality-page__thumb {
    width: 60px;
    height: 60px;
}

.locality-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.locality-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
