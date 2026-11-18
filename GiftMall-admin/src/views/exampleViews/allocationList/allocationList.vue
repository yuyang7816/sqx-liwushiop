<template>
    <div class="allocation-page">
        <div class="nf-page-hero allocation-page__hero">
            <span class="nf-page-hero__badge">系统配置</span>
            <h2 class="nf-page-hero__title">全局参数管理</h2>
            <p class="nf-page-hero__desc">管理平台全局系统参数、支付渠道、短信及各类业务配置项</p>
        </div>

        <div class="nf-filter-card allocation-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body allocation-page__filter-body">
                <el-button type="primary" @click="handleSearch">刷新数据</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="allocation-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="全局系统配置" name="first">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="服务费配置" name="thirdly">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="微信配置" name="fifth">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="支付宝配置" name="sixth">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="短信配置" name="seventh">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="红包配置" name="hongbao">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="APP消息推送配置" name="appxiaoxi">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="图片配置" name="image">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="开关配置" name="kaiguan">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="协议配置" name="xieyi">
                <div class="nf-table-card allocation-page__table">
                <el-table :data="dataContainer.tableData" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100"></el-table-column>
                    <el-table-column prop="min" label="类型" width="300"></el-table-column>
                    <el-table-column prop="value" label="内容">
                        <template #default="scope">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

                                <div style="width: 400px;">{{ scope.row.value }}</div>
                                <template #reference>
                                    <div style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;"
                                        slot="reference">{{ scope.row.value }}</div>
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createAt" label="创建时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="100">
                        <template #default="scope">
                            <el-button size="small" type="primary" class="allocation-page__action-btn" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('allocationList:update')">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 添加/修改弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">类型：</span>
                    <el-input style="width:50%;" v-model="dataContainer.min" type="text" placeholder="请输入类型"></el-input>
                </div>
                <div style="margin-bottom: 10px;position: relative;" v-if="dataContainer.condition == 'xieyi'">
                    <span
                        style="width: 200px;display: inline-block;text-align: right;position: absolute;top: 0;">内容：</span>
                    <!-- <el-input  style="width:50%;" v-model="dataContainer.value" type="text"
                        placeholder="请输入描述"></el-input> -->
                    <div style="width:50%;display: inline-block;margin-left: 200px;">
                        <QuillEditor v-model:content="dataContainer.value" contentType="html" :options="editorOptions"
                            @ready="onEditorReady" />
                    </div>

                </div>
                <div style="margin-bottom: 10px;" v-else-if="dataContainer.condition == 'image'">
                    <span style="width: 200px;display: inline-block;text-align: right;">图片：</span>
                    <div style="display: inline-block;">
                        <el-upload class="avatar-uploader" v-model="dataContainer.value"
                            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess1">
                            <img v-if="dataContainer.value" :src="dataContainer.value" class="avatar"
                                style="width: 148px;height: 148px;" />
                            <SvgIcon v-else style="width:23px;height:23px;color: #efefef;" name="svg:plus.svg">
                            </SvgIcon>
                        </el-upload>
                    </div>
                </div>
                <div style="margin-bottom: 10px;" v-else-if="dataContainer.condition == 'kaiguan'">
                    <span style="width: 200px;display: inline-block;text-align: right;">内容：</span>
                    <el-radio-group v-model="dataContainer.value" class="ml-4">
                        <el-radio label="是">是</el-radio>
                        <el-radio label="否">否</el-radio>
                    </el-radio-group>
                </div>
                <div style="margin-bottom: 10px;" v-else>
                    <span style="width: 200px;display: inline-block;text-align: right;">描述：</span>
                    <el-input style="width:50%;" v-model="dataContainer.value" type="text"
                        placeholder="请输入描述"></el-input>
                </div>


                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleU = false"
                            style="margin: 10px;">取消</el-button>
                        <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
                    </span>
                </template>
            </el-dialog>
    </div>
</template>
<script>
/**
 * 页面例子
 */
import { defineComponent, reactive, toRef, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { messageSuccess, messageError } from '@/action/messagePrompt';
import { findTag, deleteTags } from '@/action/tagListTools';
import http from '@/http/request.js';
import { QuillEditor } from '@vueup/vue-quill';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import userApi from '@/http/user.js';

import { Filter } from '@element-plus/icons-vue';

export default defineComponent({
    components: {
        SvgIcon,
        QuillEditor,
        Filter,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const url = ''
        const formData = ref({
            id: null,
            CONT: null
        });
        const editorOptions = {
            modules: {
                toolbar: [
                    ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线
                    [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
                    [{ align: [] }], // 对齐方式
                    [{ size: ['small', false, 'large', 'huge'] }], // 字体大小
                    [{ font: [] }], // 字体种类
                    [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
                    [{ direction: 'ltl' }], // 文本方向
                    [{ direction: 'rtl' }], // 文本方向
                    [{ indent: '-1' }, { indent: '+1' }], // 缩进
                    [{ list: 'ordered' }, { list: 'bullet' }], // 有序、无序列表
                    [{ script: 'sub' }, { script: 'super' }], // 上标/下标
                    ['blockquote', 'code-block'], // 引用  代码块
                    ['clean'], // 清除文本格式
                    ['link', 'image', 'video'], // 链接、图片、视频
                ]
            }
        }

        // 用于存储 Quill 实例
        let quillInstance = null;

        // 图片上传处理函数
        const handleImageUpload = () => {
            const input = document.createElement('input');
            input.type = 'file';
            input.accept = 'image/*';
            input.click();

            input.onchange = async (e) => {
                console.log('图片图片', e);
                const file = e.target.files[0];
                if (file) {

                    try {
                        // 上传图片axios
                        const response = await uploadImg(file);
                        const imageUrl = response;
                        console.log('response---',)
                        // insertImageToEditor(imageUrl);

                    } catch (error) {
                        console.error('图片上传失败', error);
                    }
                }
            };
        };

        // 文件上传处理函数
        const handleFileUpload = () => {
            const input = document.createElement('input');
            input.type = 'file';
            input.accept = '*/*'; // 接受所有类型的文件 .docx,.pdf
            input.click();

            input.onchange = async (e) => {
                const file = e.target.files[0];
                if (file) {
                    const maxSize = 10 * 1024 * 1024; // 10MB
                    if (file.size > maxSize) {
                        toast(t('sys.file-error-msg'), 'error');
                        return;
                    }
                    try {
                        const response = await uploadFile(file);
                        const fileUrl = response.data.url; // 假设返回的文件 URL 在 response.data
                        insertFileToEditor(fileUrl, file.name); // 插入文件链接到编辑器
                    } catch (error) {
                        console.error('文件上传失败', error);
                    }
                }
            };
        };

        // 将上传的图片插入编辑器
        const insertImageToEditor = (imageUrl) => {

            if (quillInstance) {
                const range = quillInstance.getSelection();
                quillInstance.insertEmbed(range.index, 'image', imageUrl);
            }
        };

        // 将上传的文件插入编辑器
        const insertFileToEditor = (fileUrl, fileName) => {
            if (quillInstance) {
                const range = quillInstance.getSelection();
                const link = `<a href="${fileUrl}" target="_blank">${fileName}</a>`;
                quillInstance.clipboard.dangerouslyPasteHTML(range.index, link); // 插入文件链接
            }
        };

        // 在编辑器准备好后，获取 Quill 实例
        const onEditorReady = (quill) => {
            quillInstance = quill; // 保存 quill 实例
            const toolbar = quill.getModule('toolbar');
            toolbar.addHandler('image', handleImageUpload); // 替换默认的图片上传行为
            toolbar.addHandler('link', handleFileUpload); // 添加自定义文件上传处理
        };
        const dataContainer = reactive({
            loading: false,
            activeName: 'first',
            condition: 'xitong',
            tableData: [],
            page: 1,
            limit: 10,
            classify: 1,
            dialogFormVisibleU: false,
            titles: '添加',
            id: '',
            paramKey: "",
            conditionFrom: "",
            paramTypeDesc: "",
            value: '',
            type: "",
            createAt: 0,
            routeUrl: '',
            active: 1,
            inactive: 2,


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)



            if (event.srcElement.innerText == '全局系统配置') {
                dataContainer.condition = 'xitong'
                dataSelect();
            }
            if (event.srcElement.innerText == '服务费配置') {
                dataContainer.condition = 'fuwufei'
                dataSelect();
            }
            if (event.srcElement.innerText == '微信配置') {
                dataContainer.condition = 'weixin'
                dataSelect();
            }
            if (event.srcElement.innerText == '支付宝配置') {
                dataContainer.condition = 'zhifubao'
                dataSelect();
            }
            if (event.srcElement.innerText == '短信配置') {
                dataContainer.condition = 'duanxin'
                dataSelect();
            }
            if (event.srcElement.innerText == '红包配置') {
                dataContainer.condition = 'hongbao'
                dataSelect();
            }
            if (event.srcElement.innerText == 'APP消息推送配置') {
                dataContainer.condition = 'appxiaoxi'
                dataSelect();
            }
            if (event.srcElement.innerText == '图片配置') {
                dataContainer.condition = 'image'
                dataSelect();
            }
            if (event.srcElement.innerText == '开关配置') {
                dataContainer.condition = 'kaiguan'
                dataSelect();
            }
            if (event.srcElement.innerText == '协议配置') {
                dataContainer.condition = 'xieyi'
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
            http.get('common/type/condition/' + dataContainer.condition, {

                // page: dataContainer.page,
                // limit: dataContainer.limit,
                // name: '',
                // classify: dataContainer.classify,
                // state: '',
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
            http.post('sys/config/list', {

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

            dataContainer.titles = '修改'
            dataContainer.id = row.id
            dataContainer.min = row.min
            dataContainer.conditionFrom = row.conditionFrom
            dataContainer.max = row.max
            dataContainer.value = row.value
            dataContainer.type = row.type
            dataContainer.createAt = row.createAt
            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.value = file.data.path
        }
        function StairNoticeToU() {


            dataContainer.routeUrl = 'common/update'
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                min: dataContainer.min,
                value: dataContainer.value,
                createAt: dataContainer.createAt,
                type: dataContainer.type,
                conditionFrom: dataContainer.conditionFrom,
                max: dataContainer.max,
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
            http.post('admin/base/banner/delete', {
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
        }
        function uploadImg(file) {
            const formData = new FormData();
            formData.append('file', file);
            http.post("file/upload", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then((res) => {
                console.log(res)
                insertImageToEditor(res.data);

            });
        }
        function uploadFile(file) {
            const formData = new FormData();
            formData.append('file', file);
            http.post("/upload/file", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
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
            editorOptions,
            onEditorReady,
            handleImageUpload,
            insertFileToEditor,
            uploadImg,
            uploadFile,

        };
    },
});
</script>



<style lang="scss" scoped>
.allocation-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.allocation-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.allocation-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.allocation-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.allocation-page__tabs {
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

.allocation-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.allocation-page__action-btn {
    margin: 4px 0;
}

.allocation-page__cell-clamp {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    overflow: hidden;
    max-height: 80px;
}

.editor-container {
    width: 50%;
    max-height: 100%;
    overflow-y: auto;
    border: 1px solid var(--nf-border);
}
</style>
