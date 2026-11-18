<template>
    <div class="shop-amend-page">
        <div class="nf-page-hero shop-amend-page__hero">
            <span class="nf-page-hero__badge">团购商品</span>
            <h2 class="nf-page-hero__title">修改商品</h2>
            <p class="nf-page-hero__desc">编辑商品基本信息、图片、规格与 SKU 属性</p>
        </div>

        <div class="nf-filter-card shop-amend-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><ArrowLeft /></el-icon>
                <span>页面导航</span>
            </div>
            <div class="nf-filter-card__body shop-amend-page__filter-body">
                <a class="shop-amend-page__back" href="#" @click.prevent="prev">
                    <Back class="shop-amend-page__back-icon" />
                    返回上一页
                </a>
            </div>
        </div>

        <div class="nf-table-card shop-amend-page__form">
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">商品名称:</span>
                <el-input class="shop-amend-page__form-input" v-model="dataContainer.name" type="text"
                    placeholder="请输入商品名称"></el-input>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">商品类型：</span>
                <el-select v-model="dataContainer.typeId" class="shop-amend-page__form-input" placeholder="请选择商品类型">
                    <el-option v-for="(item, index) in dataContainer.typeList" :key="index" :label="item.name"
                        :value="item.id" />
                </el-select>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">送礼场景：</span>
                <el-select v-model="dataContainer.sceneId" class="shop-amend-page__form-input" placeholder="请选择送礼场景">
                    <el-option v-for="(item, index) in dataContainer.sceneList" :key="index" :label="item.name"
                        :value="item.id" />
                </el-select>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">送礼对象：</span>
                <el-select v-model="dataContainer.targetId" class="shop-amend-page__form-input" placeholder="请选择送礼对象：">
                    <el-option v-for="(item, index) in dataContainer.targetList" :key="index" :label="item.name"
                        :value="item.id" />
                </el-select>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">商品封面图片：</span>
                <div class="shop-amend-page__upload-wrap">
                    <el-upload class="avatar-uploader shop-amend-page__uploader" v-model="dataContainer.coverImg"
                        action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                        :on-success="handleAvatarSuccess1">
                        <img v-if="dataContainer.coverImg" :src="dataContainer.coverImg"
                            class="shop-amend-page__avatar" />
                        <SvgIcon v-else class="shop-amend-page__upload-icon" name="svg:plus.svg"></SvgIcon>
                    </el-upload>
                </div>
            </div>
            <div class="shop-amend-page__form-row shop-amend-page__form-row--images">
                <span class="shop-amend-page__form-label">商品图片：</span>
                <div class="shop-amend-page__images-wrap">
                    <div class="imgs" v-for="(item, index) in dataContainer.img" :key="index">
                        <img width="100%" class="images" height="100%" :src="item" alt="">
                        <span class="dels">
                            <Delete class="el-icon-delete shop-amend-page__delete-icon"
                                @click="clear2(index)" />
                        </span>
                    </div>
                    <div class="imgs shop-amend-page__uploader">
                        <el-upload action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleUploadSuccess4" :on-progress="onprogress2">
                            <el-progress v-if="percentage2 > 0 && percentage2 < 100" type="circle"
                                :percentage="percentage2"></el-progress>
                            <SvgIcon class="shop-amend-page__upload-icon" name="svg:plus.svg"></SvgIcon>
                        </el-upload>
                    </div>
                </div>
            </div>

            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">商品售价：</span>
                <el-input class="shop-amend-page__form-input" v-model="dataContainer.price" type="text"
                    placeholder="请输入商品售价"></el-input>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">原价：</span>
                <el-input class="shop-amend-page__form-input" v-model="dataContainer.originalPrice" type="text"
                    placeholder="请输入原价"></el-input>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">折扣比例：</span>
                <el-input class="shop-amend-page__form-input" v-model="dataContainer.discountRatio" type="text"
                    placeholder="请输入折扣比例，如0.1"></el-input>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">商品描述:</span>
                <el-input class="shop-amend-page__form-input" v-model="dataContainer.description" type="text"
                    placeholder="请输入商品描述"></el-input>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">商品状态：</span>
                <el-radio-group v-model="dataContainer.status" class="shop-amend-page__radio-group">
                    <el-radio :label="0" size="large">正常</el-radio>
                    <el-radio :label="1" size="large">下架</el-radio>
                </el-radio-group>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">是否精选好物：</span>
                <el-radio-group v-model="dataContainer.goodPick" class="shop-amend-page__radio-group">
                    <el-radio :label="0" size="large">是</el-radio>
                    <el-radio :label="1" size="large">不是</el-radio>
                </el-radio-group>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">是否特价商品：</span>
                <el-radio-group v-model="dataContainer.specialOffer" class="shop-amend-page__radio-group">
                    <el-radio :label="0" size="large">是</el-radio>
                    <el-radio :label="1" size="large">不是</el-radio>
                </el-radio-group>
            </div>
            <div class="shop-amend-page__form-row">
                <span class="shop-amend-page__form-label">是否包邮：</span>
                <el-radio-group v-model="dataContainer.positage" @change="agreeChange" class="shop-amend-page__radio-group">
                    <el-radio :label="0">是</el-radio>
                    <el-radio :label="1">否</el-radio>
                    <el-input v-if="dataContainer.priceshow" class="shop-amend-page__postage-input"
                        placeholder="请输入邮费" type="number" v-model="dataContainer.postagePrice" autosize></el-input>
                </el-radio-group>
            </div>

            <div class="shop-amend-page__form-row shop-amend-page__form-row--spec">
                <span class="shop-amend-page__form-label">商品规格：</span>
                <div class="shop-amend-page__spec-wrap">
                    <el-radio-group v-model="dataContainer.guige" @change="guigeChange" class="shop-amend-page__radio-group">
                        <el-radio :label="0">单规格</el-radio>
                        <el-radio :label="1" v-if="dataContainer.isExpress != 2">多规格</el-radio>
                    </el-radio-group>
                    <div v-if="dataContainer.guigeshow" class="shop-amend-page__spec-select">
                        <el-select v-model="dataContainer.ruleId" placeholder="请选择商品规格"
                            class="shop-amend-page__spec-select-input" @change="selectTrigger(dataContainer.ruleId)">
                            <el-option v-for="(item, index) in dataContainer.specifdata" :key="item.index"
                                :label="item.ruleName" :value="item.id">
                            </el-option>
                        </el-select>
                    </div>

                    <div v-if="dataContainer.ruleValue" class="shop-amend-page__rule-block"
                        v-loading="dataContainer.tableDataLoading3">
                        <div class="ruleitem" v-for="(item, index) in dataContainer.ruleValue" :key="index">
                            <div>{{ item.value }} <span @click="deleterule(index)">
                                    <delete class="shop-amend-page__rule-delete" />
                                </span></div>
                            <div>
                                <el-tag :key="tag" v-for="(tag, j) in item.detail.split(',')" closable
                                    :disable-transitions="false" @close="handleClose(tag, j, item)">
                                    {{ tag }}
                                </el-tag>
                                <el-input class="input-new-tag" v-model="dataContainer.inputValues[index]"
                                    ref="saveTagInput" placeholder="请输入属性名"
                                    @keyup.enter.native="handleInputConfirm(index, item)">
                                </el-input>
                                <el-button class="button-new-tag" size="small" @click="handleInputConfirm(index, item)">添加
                                </el-button>
                            </div>
                        </div>
                        <div class="shop-amend-page__rule-actions" v-if="dataContainer.ruleshow">
                            <div v-if="dataContainer.isshow == true" class="btn_specif">
                                <el-button type="primary" size="small" @click="btnToclick()">添加新规格</el-button>
                                <el-button type="success" size="small" @click="create()">立即生成</el-button>
                            </div>
                        </div>
                        <div class="shop-amend-page__rule-add">
                            <div v-if="dataContainer.isshow == false" class="shop-amend-page__rule-add-form">
                                <div class="shop-amend-page__rule-add-item">
                                    <span class="shop-amend-page__rule-add-label">规格：</span>
                                    <el-input class="shop-amend-page__rule-add-input" v-model="dataContainer.releobject.value"
                                        placeholder="例:颜色"></el-input>
                                </div>
                                <div class="shop-amend-page__rule-add-item">
                                    <span class="shop-amend-page__rule-add-label">规格值：</span>
                                    <el-input class="shop-amend-page__rule-add-input" v-model="dataContainer.releobject.detail"
                                        placeholder="例:黑色"></el-input>
                                </div>
                                <div class="shop-amend-page__rule-add-item">
                                    <el-button type="primary" size="small" @click="speciTo()">确 定</el-button>
                                    <el-button size="small" @click="speciTotwo()">取 消</el-button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="shop-amend-page__attr-block" v-if="dataContainer.propshow"
                        v-loading="dataContainer.tableDataLoading1">
                        <span class="shop-amend-page__form-label shop-amend-page__form-label--inline">商品属性：</span>
                        <div class="property_table shop-amend-page__property-table">
                        <table style="width: 100%;">
                            <tbody>
                                <tr style="display:flex;">
                                    <th style="width:120px;text-align: left;"
                                        v-for="(item, index) in dataContainer.headerData" :key="index">{{ item }}</th>
                                </tr>
                            </tbody>
                        </table>
                        <el-table :data="dataContainer.valueData">
                            <!-- <el-table-column v-for="(item, index) in dataContainer.detailJson" :key="index"
                                :prop="item[index]" width="120">
                            </el-table-column> -->
                            <el-table-column v-if="dataContainer.detailJson.length == 1" prop="value0" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 2" prop="value0" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 2" prop="value1" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 3" prop="value1" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 3" prop="value1" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 3" prop="value2" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 4" prop="value0" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 4" prop="value1" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 4" prop="value2" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 4" prop="value3" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 5" prop="value0" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 5" prop="value1" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 5" prop="value2" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 5" prop="value3" width="120">
                            </el-table-column>
                            <el-table-column v-if="dataContainer.detailJson.length == 5" prop="value4" width="120">
                            </el-table-column>
                            <el-table-column prop="skuImg" width="100">
                                <template #default="scope">
                                    <div class="imgWrap shop-amend-page__sku-upload">
                                        <el-upload class="avatar-uploader shop-amend-page__sku-uploader"
                                            v-model="scope.row.skuImg"
                                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload"
                                            :show-file-list="false" :on-success="handleAvatarSuccess">
                                            <img v-if="scope.row.skuImg" :src="scope.row.skuImg" class="avatar shop-amend-page__sku-img"
                                                @click="dataContainer.curRowIndex = scope.$index" />
                                            <i v-else class="el-icon-plus avatar-uploader-icon"
                                                @click="dataContainer.curRowIndex = scope.$index"></i>
                                        </el-upload>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="skuOriginPrice" width="120">
                                <template #default="scope">
                                    <div>
                                        <el-input type="text" v-model="scope.row.skuOriginPrice"
                                            style="width:80px;font-size:14px;">
                                        </el-input>
                                    </div>
                                </template>
                            </el-table-column>
                            <!-- <el-table-column prop="memberPrice" width="150">
                                <template #default="scope">
                                    <div>
                                        <el-input type="text" v-model="scope.row.memberPrice"
                                            style="width:80px;font-size:14px;">
                                        </el-input>
                                    </div>
                                </template>
                            </el-table-column> -->
                            <el-table-column prop="skuPrice" width="120">
                                <template #default="scope">
                                    <div>
                                        <el-input type="text" v-model="scope.row.skuPrice"
                                            style="width:80px;font-size:14px;">
                                        </el-input>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="stock" width="120">
                                <template #default="scope">
                                    <div>

                                        <el-input type="text" v-if="dataContainer.isExpress == 2" disabled
                                            v-model="scope.row.stock" style="width:60px;font-size:14px;">
                                        </el-input>
                                        <el-input type="text" v-else v-model="scope.row.stock"
                                            style="width:60px;font-size:14px;">
                                        </el-input>
                                    </div>

                                </template>
                            </el-table-column>
                            <el-table-column width="120">
                                <template #default="scope">
                                    <el-button :disabled="!isAuth('userList:delete')" size="mini" type="danger"
                                        @click="deleter(scope.$index)">删除
                                    </el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        </div>
                    </div>
                    <div class="shop-amend-page__attr-block" v-if="dataContainer.propshow1"
                        v-loading="dataContainer.tableDataLoading2">
                        <span class="shop-amend-page__form-label shop-amend-page__form-label--inline">商品属性：</span>
                        <div class="property_table shop-amend-page__property-table">
                        <!-- <table style="width: 100%;">
                            <tbody>
                                <tr style="display:flex;">
                                    <th style="width:150px;text-align: left;"
                                        v-for="(item, index) in dataContainer.headerData2" :key="index">{{ item }}</th>
                                </tr>
                            </tbody>

                        </table> -->
                        <el-table :data="dataContainer.valueData2">
                            <el-table-column prop="skuImg" label="图片" width="150">
                                <template #default="scope">
                                    <div class="imgWrap shop-amend-page__sku-upload">
                                        <el-upload class="avatar-uploader shop-amend-page__sku-uploader"
                                            v-model="scope.row.skuImg"
                                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload"
                                            :show-file-list="false" :on-success="handleAvatarSuccess3">
                                            <img v-if="scope.row.skuImg" :src="scope.row.skuImg" class="avatar shop-amend-page__sku-img"
                                                @click="dataContainer.curRowIndex = scope.$index" />
                                            <i v-else class="el-icon-plus avatar-uploader-icon"
                                                @click="dataContainer.curRowIndex = scope.$index"></i>
                                        </el-upload>
                                    </div>
                                </template>
                            </el-table-column>
                            <!-- <el-table-column prop="skuOriginalPrice" width="150">
                                <template #default="scope">
                                    <div>
                                        <el-input type="text" v-model="scope.row.skuOriginalPrice"
                                            style="width:80px;font-size:14px;">
                                        </el-input>
                                    </div>
                                </template>
                            </el-table-column> -->
                            <el-table-column prop="skuOriginPrice" label="原价" width="150">
                                <template #default="scope">
                                    <div>
                                        <el-input type="text" v-model="scope.row.skuOriginPrice"
                                            style="width:80px;font-size:14px;">
                                        </el-input>
                                    </div>
                                </template>
                            </el-table-column>
                            <!-- <el-table-column prop="memberPrice" width="150">
                                <template #default="scope">
                                    <div>
                                        <el-input type="text" v-model="scope.row.memberPrice"
                                            style="width:80px;font-size:14px;">
                                        </el-input>
                                    </div>
                                </template>
                            </el-table-column> -->
                            <el-table-column prop="skuPrice" label="售价" width="150">
                                <template #default="scope">
                                    <div>
                                        <el-input type="text" v-model="scope.row.skuPrice"
                                            style="width:80px;font-size:14px;">
                                        </el-input>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="stock" label="库存" width="150">
                                <template #default="scope">
                                    <div>

                                        <el-input type="text" v-if="dataContainer.isExpress == 2" disabled
                                            v-model="scope.row.stock" style="width:60px;font-size:14px;">
                                        </el-input>
                                        <el-input type="text" v-else v-model="scope.row.stock"
                                            style="width:60px;font-size:14px;">
                                        </el-input>
                                    </div>

                                </template>
                            </el-table-column>
                        </el-table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="shop-amend-page__actions">
                <el-button @click="prev()">返回上一页</el-button>
                <el-button type="primary" @click="StairNoticeToU()">确认</el-button>
            </div>
        </div>
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
import { ArrowLeft } from '@element-plus/icons-vue';
import {
    regionData,
    codeToText,
} from 'element-china-area-data'
export default defineComponent({
    components: {
        SvgIcon,
        ArrowLeft,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const url = ''

        const getCodeToText = (codeStr) => {
            let codeArray = codeStr.split(",");
            let area = codeToText[codeArray[0]] + codeToText[codeArray[1]] + codeToText[codeArray[2]];
            return area;
        }
        const dataContainer = reactive({
            loading: false,
            activeName: 'first',
            tableData: {},
            dialogFormVisibleU: false,
            routeUrl: '',
            name: "", //商品名称
            description: "", //商品描述
            coverImg: "", //商品封面图片
            img: [], //商品图片
            price: 1, //商品价格(售价)
            status: 1, //商品状态(默认0正常 1下架)
            goodPick: 1, //是否精选好物 0是 1不是
            specialOffer: 1, //是否特价商品 0是 1不是
            originalPrice: 1, //原价
            discountRatio: 0.5, //折扣比例 0.1表示10%1折
            typeId: '', //商品类型id
            sceneId: '', //送礼场景id
            targetId: '', //送礼对象id
            giftProductAttrList: [], //商品规格
            giftProductSkuList: [], //商品sku
            typeList: [],
            sceneList: [],
            targetList: [],
            percentage2: 0,
            guige: 1,
            specifdata: [],
            ruleId: '',
            propshow: false,
            guigeshow: true,
            propshow1: false,
            ruleshow: false,
            tableDataLoading1: false,
            tableDataLoading2: false,
            tableDataLoading3: false,
            ruleValue: [],
            attr: [],
            attrs: [],
            headerData: [],
            headerData2: [],
            valueData: [],
            valueData2: [],
            detailJson: [],
            inputValues: {
                inputValue0: '',
                inputValue1: '',
                inputValue2: '',
                inputValue3: '',
                inputValue4: '',
                inputValue5: ''
            },
            sku: [],
            releobject: {
                value: '',
                detail: ''
            },
            curRowIndex: 0,
            statusmain2: [{
                value: 1,
                label: '上架'
            },
            {
                value: 2,
                label: '下架'
            }
            ],
            positage: 0,
            isExpress: '',
            postagePrice: '',
            isExpressmain: [{
                value: 1,
                label: '普通商品需要发货'
            },
            {
                value: 2,
                label: '虚拟商品无需发货'
            },
            {
                value: 3,
                label: '门店核销无需发货'
            },
            {
                value: 4,
                label: '充值商品无需发货'
            }
            ],
            priceshow: false,
            isShow: false,
            id: '',
            goodsId: '',//商品规格id






        });
        // 获取商品详情
        function infoSelect() {
            dataContainer.id = route.query.id
            http.get('admin/product/info', {
                params: {
                    id: dataContainer.id
                }
            }, {
            }).then((res) => {
                console.log(res)
                let returnData = res.data;
                dataContainer.id = returnData.id
                dataContainer.name = returnData.name
                dataContainer.description = returnData.description
                dataContainer.coverImg = returnData.coverImg
                if (returnData.img) {
                    dataContainer.img = returnData.img.split(',')
                } else {
                    dataContainer.img = []
                }

                dataContainer.price = returnData.price
                dataContainer.status = returnData.status
                dataContainer.goodPick = returnData.specialOffer
                dataContainer.specialOffer = returnData.specialOffer
                dataContainer.originalPrice = returnData.originalPrice
                dataContainer.discountRatio = returnData.discountRatio
                dataContainer.typeId = returnData.typeId
                dataContainer.sceneId = returnData.sceneId
                dataContainer.targetId = returnData.targetId
                dataContainer.attr = returnData.giftProductAttrList
                // dataContainer.sku = returnData.giftProductSkuList

                if (returnData.giftProductAttrList && returnData.giftProductAttrList.length > 0) {

                    dataContainer.isshow = true
                    dataContainer.ruleshow = true
                    dataContainer.guigeshow = true
                    dataContainer.propshow1 = false
                    dataContainer.guige = 1
                    dataContainer.attrName = returnData.giftProductAttrList[0].attrName
                    dataContainer.attrValue = returnData.giftProductAttrList[0].giftProductAttrValueList
                    dataContainer.ruleId = returnData.giftProductAttrList[0].ruleId
                    // ``````````````````
                    dataContainer.goodsId = returnData.giftProductAttrList[0].ruleId
                    // dataContainer.id = returnData.giftProductAttrList[0].id
                    Triggerselect()
                    create2()



                } else {
                    // dataContainer.attrValue = returnData.attr.giftProductAttrValueList
                    dataContainer.guige = 0
                    // this.echo(this.id)
                    dataContainer.ruleId = 0
                    dataContainer.sku = returnData.giftProductSkuList


                    dataContainer.guigeshow = false
                    dataContainer.ruleshow = false
                    dataContainer.valueData2 = returnData.giftProductSkuList
                    dataContainer.propshow = false
                    dataContainer.propshow1 = true
                    dataContainer.isshow = true
                }
            })
        }
        infoSelect();
        // 获取 规格详情
        function Triggerselect() {
            http.get('admin/rule/info', {
                params: {
                    'id': dataContainer.goodsId
                }
            }, {
            }).then((res) => {
                console.log(res)
                let returnData = res.data
                if (returnData.giftProductRuleValueList.length > 0) {
                    for (var i in returnData.giftProductRuleValueList) {
                        var datas = {
                            detail: returnData.giftProductRuleValueList[i].detail,
                            ruleId: returnData.giftProductRuleValueList[i].ruleId,
                            value: returnData.giftProductRuleValueList[i].value,
                        }
                        dataContainer.ruleValue.push(datas)
                    }
                }
                // dataContainer.ruleValue = returnData.giftProductRuleValueList
                dataContainer.attrName = returnData.ruleName
                dataContainer.ruleshow = true
            })

        }
        // 获取 商品的规格详情
        function create2() {
            http.get('admin/product/formatAttr', {
                params: {
                    'productId': dataContainer.id
                }
            }, {
            }).then((res) => {
                console.log(res)
                let returnData = res.data
                dataContainer.headerData = returnData.header
                dataContainer.valueData = returnData.value
                // dataContainer.valueshow = true
                // dataContainer.valueshow1 = false
                dataContainer.propshow = true
                dataContainer.propshow1 = false
                dataContainer.sku = returnData.value
                dataContainer.detailJson = returnData.value[0].detailJson.split(',')
            })
        }

        /** 获取列表数据 */
        function dataSelect() {
            // 商品分类
            http.get('admin/productType/listNoPage', {
                params: {}
            }, {
            }).then((res) => {
                console.log(res)

                dataContainer.typeList = []
                let returnData = res.data
                dataContainer.typeList = returnData
            })
            // 商品规格
            http.get('admin/rule/list', {
                params: {}
            }, {
            }).then((res) => {
                console.log(res)

                dataContainer.specifdata = []
                let returnData = res.data
                dataContainer.specifdata = returnData
            })
            // 送礼场景
            http.get('admin/scene/list', {
                params: {
                    page: 1,
                    limit: 50,
                }
            }, {
            }).then((res) => {
                console.log(res)

                dataContainer.sceneList = []
                let returnData = res.data
                dataContainer.sceneList = returnData.records
            })
            // 送礼对象
            http.get('admin/target/list', {
                params: {
                    page: 1,
                    limit: 50,
                }
            }, {
            }).then((res) => {
                console.log(res)

                dataContainer.targetList = []
                let returnData = res.data
                dataContainer.targetList = returnData.records
            })
        }
        dataSelect()



        function handleAvatarSuccess1(file, fileList) {
            dataContainer.coverImg = file.data.path
        }
        function handleUploadSuccess4(file, fileList) {
            dataContainer.img.push(file.data.path)
        }
        function onprogress2(event, file, fileList) {
            console.log('event, file, fileList', parseInt(event.percent))
            dataContainer.percentage2 = parseInt(event.percent)
        }
        function clear2(index) {
            dataContainer.img.splice(index, 1);
        }
        function StairNoticeToU() {



            if (dataContainer.name === '') {
                ElNotification({
                    title: '提示',
                    message: '请输入商品名称',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.description === '') {
                ElNotification({
                    title: '提示',
                    message: '请输入商品描述',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.coverImg == '') {
                ElNotification({
                    title: '提示',
                    message: '请上传商品封面图片',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.img.length == 0) {
                ElNotification({
                    title: '提示',
                    message: '请上传商品图片',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.typeId === '') {
                ElNotification({
                    title: '提示',
                    message: '请选择商品类型',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.sceneId === '') {
                ElNotification({
                    title: '提示',
                    message: '请选择送礼场景',
                    type: 'warning',
                })
                return
            }
            if (dataContainer.targetId === '') {
                ElNotification({
                    title: '提示',
                    message: '请选择送礼对象',
                    type: 'warning',
                })
                return
            }


            // if (dataContainer.titles == '添加') {
            // dataContainer.routeUrl = 'admin/product/save'
            // } else {
            dataContainer.routeUrl = 'admin/product/update'
            // }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                name: dataContainer.name, //商品名称
                description: dataContainer.description, //商品描述
                coverImg: dataContainer.coverImg, //商品封面图片
                img: dataContainer.img.toString(), //商品图片
                price: dataContainer.price, //商品价格(售价)
                status: dataContainer.status, //商品状态(默认0正常 1下架)
                goodPick: dataContainer.goodPick, //是否精选好物 0是 1不是
                specialOffer: dataContainer.specialOffer, //是否特价商品 0是 1不是
                originalPrice: dataContainer.originalPrice, //原价
                discountRatio: dataContainer.discountRatio, //折扣比例 0.1表示10%1折
                typeId: dataContainer.typeId, //商品类型id
                sceneId: dataContainer.sceneId, //送礼场景id
                targetId: dataContainer.targetId, //送礼对象id
                giftProductAttrList: dataContainer.attr, //商品规格
                giftProductSkuList: dataContainer.sku, //商品sku

            }, {
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            // dataContainer.dialogFormVisibleU = false
                            router.back()
                            // dataSelect()
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
        // 查询商品规格
        function selectTrigger(val) {
            dataContainer.tableDataLoading1 = true
            dataContainer.tableDataLoading3 = true
            dataContainer.ruleshow = true
            dataContainer.isshow = true
            http.get('admin/rule/info', {
                params: {
                    id: val,
                }
            }, {
            }).then((res) => {
                console.log(res)

                if (dataContainer.isExpress == 2) {
                    for (var i in res.data.value) {
                        res.data.value[i].stock = 0
                    }
                }
                let returnData = res.data;
                dataContainer.ruleValue = []
                if (returnData.giftProductRuleValueList.length > 0) {
                    for (var i in returnData.giftProductRuleValueList) {
                        var datas = {
                            detail: returnData.giftProductRuleValueList[i].detail,
                            ruleId: returnData.giftProductRuleValueList[i].ruleId,
                            value: returnData.giftProductRuleValueList[i].value,
                        }
                        dataContainer.ruleValue.push(datas)
                    }
                }


                dataContainer.attrName = returnData.ruleName

                dataContainer.ruleId = returnData.id
                dataContainer.tableDataLoading3 = false
                create()

            })

        }
        // 是否多规格
        function guigeChange(val) {
            if (val == 1) {
                dataContainer.ruleId = ''
                dataContainer.guigeshow = true
                dataContainer.propshow1 = false
            } else {
                dataContainer.ruleId = 0
                dataContainer.guigeshow = false
                dataContainer.propshow1 = true
                dataContainer.propshow = false
                dataContainer.ruleshow = false
                dataContainer.attr = []
                dataContainer.ruleValue = []
                guigeclect()
            }
        }
        // 单规格
        function guigeclect() {
            dataContainer.tableDataLoading2 = true
            if (dataContainer.originalPrice == '') {
                dataContainer.originalPrice = '0'
            }
            if (dataContainer.price == '') {
                dataContainer.price = '0'
            }


            http.post('admin/product/genSingleSku', {
                // params: {
                originalPrice: dataContainer.originalPrice,
                price: dataContainer.price,
                // memberPrice: dataContainer.memberPrice,
                coverImg: dataContainer.coverImg,
                // }
            }, {
            }).then((res) => {
                console.log(res)

                if (dataContainer.isExpress == 2) {
                    for (var i in res.data.value) {
                        res.data.value[i].stock = 0
                    }
                }
                let returnData = res.data;
                dataContainer.headerData2 = returnData.header
                dataContainer.valueData2 = returnData.value
                dataContainer.propshow = false
                dataContainer.propshow1 = true
                dataContainer.sku = returnData.value
                dataContainer.tableDataLoading2 = false
            })
        }
        // 是否包邮
        function agreeChange(val) {
            if (val == 1) {
                dataContainer.priceshow = true
                dataContainer.postagePrice = ''
            } else {
                dataContainer.priceshow = false
                dataContainer.postagePrice = 0
            }
        }
        function handleChangeF(value) {
            console.log('value', value)
            if (value == 2) {
                dataContainer.ruleId = 0
                dataContainer.guige = 0
                dataContainer.guigeshow = false
                dataContainer.propshow1 = true
                dataContainer.propshow = false
                dataContainer.attr = []
                dataContainer.ruleValue = []
                guigeclect()
            }
        }
        // 删除未生成的 规格属性
        function deleterule(index) {
            dataContainer.ruleValue.splice(index, 1);
        }
        // 删除 规格属性值
        function handleClose(tag, j, item) {
            var detailarr = item.detail.split(',')
            detailarr.splice(j, 1);
            if (detailarr.length < 1) {
                ElMessage({
                    message: '规格值至少要有一个',
                    type: 'warning',
                    onClose: () => {
                    }
                })
                return
            } else {
                item.detail = String(detailarr)
            }
        }
        // 添加规格属性值
        function handleInputConfirm(index, item) {
            let inputValue = dataContainer.inputValues[index];
            if (inputValue) {
                var detailarr = item.detail.split(',')
                detailarr.push(inputValue)
                for (var i = 0; i < detailarr.length; i++) {
                    for (var j = 0; j < detailarr.length; j++) {
                        if (detailarr[i] == detailarr[j] && i != j) {
                            detailarr.splice(j, 1);
                        }
                    }
                }
                item.detail = String(detailarr)
                dataContainer.inputValues[index] = '';
            }
            dataContainer.inputVisible = false;
        }
        // 生成新规格
        function btnToclick() {
            dataContainer.isshow = false
            dataContainer.releobject.value = ''
            dataContainer.releobject.detail = ''
        }
        // 立即生产规格
        function create() {
            dataContainer.tableDataLoading1 = true
            dataContainer.attr = []
            dataContainer.attr.push({
                attrName: dataContainer.attrName,
                giftProductAttrValueList: dataContainer.ruleValue,
                ruleId: dataContainer.ruleId
            })
            if (dataContainer.originalPrice == '') {
                dataContainer.originalPrice = '0'
            }
            if (dataContainer.price == '') {
                dataContainer.price = '0'
            }
            if (dataContainer.memberPrice == '') {
                dataContainer.memberPrice = '0'
            }
            http.post('admin/product/genSku', {
                // params: {
                originalPrice: dataContainer.originalPrice,
                price: dataContainer.price,
                // memberPrice: dataContainer.memberPrice,
                coverImg: dataContainer.coverImg,
                productAttr: dataContainer.attr[0],
                // }
            }, {
            }).then((res) => {
                if (dataContainer.isExpress == 2) {
                    for (var i in res.data.value) {
                        res.data.value[i].stock = 0
                    }
                }
                let returnData = res.data;
                dataContainer.headerData = returnData.header
                dataContainer.valueData = returnData.value
                dataContainer.propshow = true
                dataContainer.propshow1 = false
                dataContainer.sku = returnData.value
                dataContainer.detailJson = returnData.value[0].detailJson.split(',')
                dataContainer.tableDataLoading1 = false

            })

        }
        // 确定 新增规格
        function speciTo() {
            if (dataContainer.releobject.value == '') {

                ElMessage({
                    message: '请输入规格',
                    type: 'warning',
                    onClose: () => {
                    }
                })
                return
            }
            if (dataContainer.releobject.detail == '') {
                ElMessage({
                    message: '请输入规格值',
                    type: 'warning',
                    onClose: () => {
                    }
                })
                return
            }
            dataContainer.ruleValue.push({
                value: dataContainer.releobject.value,
                detail: dataContainer.releobject.detail
            });
            dataContainer.isshow = true
            dataContainer.releobject.value = ''
            dataContainer.releobject.detail = ''
        }
        // 取消 新增规格
        function speciTotwo() {
            dataContainer.isshow = true
            dataContainer.ruleshow = true
            dataContainer.releobject.value = ''
            dataContainer.releobject.detail = ''
        }
        // 上传图片
        function handleAvatarSuccess(file) {
            if (dataContainer.curRowIndex == 0) {
                dataContainer.skuImg = file.data.path;
                dataContainer.valueData[0].skuImg = dataContainer.skuImg
                return
            } else {
                dataContainer.skuImg = file.data.path;
                dataContainer.valueData[dataContainer.curRowIndex].skuImg = dataContainer.skuImg
            }
        }
        // 图标上传
        function handleAvatarSuccess3(file) {
            dataContainer.skuImg = file.data.path;
            dataContainer.valueData2[0].skuImg = dataContainer.skuImg
        }
        // 删除已生成的 商品属性
        function deleter(index) {
            let length = dataContainer.sku.length
            if (length == 1) {
                ElMessage({
                    message: '属性至少要有一个',
                    type: 'warning',
                    onClose: () => {
                    }
                })

                return
            } else {
                dataContainer.sku.splice(index, 1);
            }
        }


        /** 获取列表数据 */

        function isAuth(res) {
            return userApi.isAuth(res)
        }
        // 返回上一页
        function prev() {
            router.back()
        }

        return {
            isAuth,
            prev,
            dataContainer,
            dataSelect,
            infoSelect,
            StairNoticeToU,
            handleAvatarSuccess1,
            handleUploadSuccess4,
            onprogress2,
            clear2,
            guigeChange,
            guigeclect,
            selectTrigger,
            agreeChange,
            handleChangeF,
            deleterule,
            handleClose,
            handleInputConfirm,
            btnToclick,
            create,
            speciTo,
            speciTotwo,
            handleAvatarSuccess,
            handleAvatarSuccess3,
            deleter,
            Triggerselect,
            create2,


        };
    },
});
</script>



<style lang="scss" scoped>
.shop-amend-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.shop-amend-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.shop-amend-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.shop-amend-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.shop-amend-page__back {
    display: inline-flex;
    align-items: center;
    gap: var(--nf-space-xs);
    font-size: 14px;
    color: var(--nf-brand);
    text-decoration: none;
    cursor: pointer;

    &:hover {
        color: var(--nf-brand-deep);
    }
}

.shop-amend-page__back-icon {
    width: 1em;
    height: 1em;
}

.shop-amend-page__form {
    margin-bottom: 0;
}

.shop-amend-page__form-row {
    margin-bottom: var(--nf-space-sm);
    display: flex;
    flex-wrap: wrap;
    align-items: flex-start;
}

.shop-amend-page__form-row--images {
    align-items: flex-start;
}

.shop-amend-page__form-row--spec {
    flex-direction: column;
}

.shop-amend-page__form-label {
    width: 200px;
    flex-shrink: 0;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
    line-height: 32px;
    padding-right: var(--nf-space-sm);
    box-sizing: border-box;
}

.shop-amend-page__form-label--inline {
    line-height: 32px;
}

.shop-amend-page__form-input {
    width: 50%;
    min-width: 200px;
}

.shop-amend-page__radio-group {
    line-height: 32px;
}

.shop-amend-page__postage-input {
    width: 200px;
    margin-left: var(--nf-space-sm);
}

.shop-amend-page__upload-wrap {
    display: inline-block;
    vertical-align: top;
}

.shop-amend-page__uploader {
    width: 148px;
    height: 148px;
    border: 1px dashed var(--nf-border);
    border-radius: var(--nf-radius-sm);
    text-align: center;
    line-height: 148px;
    color: var(--nf-text-placeholder);
}

.shop-amend-page__avatar {
    width: 148px;
    height: 148px;
    object-fit: cover;
    display: block;
}

.shop-amend-page__upload-icon {
    width: 23px;
    height: 23px;
    color: var(--nf-text-placeholder);
}

.shop-amend-page__delete-icon {
    width: 1em;
    height: 1em;
    margin-right: 8px;
}

.shop-amend-page__images-wrap {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    flex: 1;
    gap: var(--nf-space-sm);
}

.shop-amend-page__spec-wrap {
    flex: 1;
    min-width: 0;
}

.shop-amend-page__spec-select {
    margin-top: var(--nf-space-sm);
    margin-left: var(--nf-space-md);
}

.shop-amend-page__spec-select-input {
    width: 200px;
}

.shop-amend-page__rule-block {
    margin-top: var(--nf-space-md);
    margin-left: var(--nf-space-lg);
}

.shop-amend-page__rule-delete {
    width: 1em;
    height: 1em;
    margin-right: 8px;
    cursor: pointer;
    color: var(--nf-brand);
}

.shop-amend-page__rule-actions {
    margin-top: var(--nf-space-md);
}

.shop-amend-page__rule-add {
    margin-top: var(--nf-space-sm);
}

.shop-amend-page__rule-add-form {
    margin-top: var(--nf-space-md);
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.shop-amend-page__rule-add-item {
    display: inline-flex;
    align-items: center;
    gap: var(--nf-space-xs);
}

.shop-amend-page__rule-add-label {
    width: 80px;
    text-align: right;
    font-size: 14px;
    color: var(--nf-text-primary);
}

.shop-amend-page__rule-add-input {
    width: 160px;
}

.shop-amend-page__attr-block {
    display: flex;
    margin-top: var(--nf-space-md);
    flex-wrap: wrap;
}

.shop-amend-page__property-table {
    flex: 1;
    min-width: 0;
    overflow-x: auto;
}

.shop-amend-page__sku-upload {
    width: 60px;
    height: 60px;
    background-color: var(--nf-sidebar-bg-alt);
    border: 1px dashed var(--nf-border);
    border-radius: var(--nf-radius-sm);
    text-align: center;
    line-height: 60px;
}

.shop-amend-page__sku-uploader {
    width: 60px;
    height: 60px;
}

.shop-amend-page__sku-img {
    border-radius: var(--nf-radius-sm);
    width: 60px;
    height: 60px;
    object-fit: cover;
}

.shop-amend-page__actions {
    display: flex;
    justify-content: center;
    gap: var(--nf-space-sm);
    margin-top: var(--nf-space-md);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.imgs {
    position: relative;
    border-radius: var(--nf-radius-sm);
    width: 148px;
    height: 148px;
    display: inline-block;
}

.dels {
    position: absolute;
    top: 0;
    left: 0;
    display: none;
}

.dels .el-icon-delete {
    line-height: 148px;
    padding-left: 58px;
    font-size: 25px;
    color: #fff;
}

.imgs:hover .dels {
    width: 100%;
    height: 100%;
    background: #000;
    display: block;
    opacity: 0.5;
    line-height: 148px;
}

.ruleitem {
    padding-left: var(--nf-space-sm);
    margin-top: var(--nf-space-sm);
}

.ruleitem .ruleicon {
    position: relative;
    top: 3px;
    left: 2px;
    width: 1.2em;
    height: 1.2em;
}

.el-tag + .el-tag {
    margin-left: var(--nf-space-sm);
}

.button-new-tag {
    height: 32px;
    line-height: 31px;
    padding-top: 0;
    padding-bottom: 0;
    border-radius: 4px;
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    background-color: var(--nf-sidebar-bg-alt);
    position: relative;
    left: -5px;
    border: 1px solid var(--nf-border);
}

.input-new-tag {
    width: 120px;
    margin-left: var(--nf-space-sm);
    vertical-align: bottom;
}

:deep(.el-input--small .el-input__inner) {
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
}

:deep(.el-table--enable-row-transition .el-table__body td) {
    text-align: center;
}

:deep(.imgWrap .avatar-uploader .el-upload) {
    width: 60px;
}

:deep(.el-table .cell) {
    text-align: center !important;
}

:deep(.el-tag--medium) {
    margin-right: var(--nf-space-sm);
}

:deep(.el-radio__input.is-checked .el-radio__inner) {
    border-color: var(--nf-brand);
    background: var(--nf-brand);
}

:deep(.el-radio__input.is-checked + .el-radio__label) {
    color: var(--nf-brand);
}
</style>
