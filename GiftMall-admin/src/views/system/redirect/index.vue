<template>
    <DefinScrollbar height="100%" :showUpBt="true">
        <div class="home-page">
            <!-- hero + 筛选 -->
            <div class="home-page__top">
                <div class="nf-page-hero home-page__hero">
                    <span class="nf-page-hero__badge">数据中心</span>
                    <h2 class="nf-page-hero__title">运营数据概览</h2>
                    <p class="nf-page-hero__desc">实时掌握用户增长、订单与收入核心指标</p>
                </div>
                <div class="nf-filter-card home-page__filter">
                    <div class="nf-filter-card__head">
                        <el-icon><Filter /></el-icon>
                        <span>筛选条件</span>
                    </div>
                    <div class="nf-filter-card__body home-page__filter-body">
                        <el-select
                            v-model="dataContainer.flag"
                            class="home-page__select"
                            @change="orderfenxi()"
                        >
                            <el-option
                                v-for="(item, index) in dataContainer.flags"
                                :key="index"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                        <el-date-picker
                            v-model="dataContainer.info"
                            type="date"
                            placeholder="选择时间"
                            format="YYYY-MM-DD"
                            value-format="YYYY-MM-DD"
                            @change="animeOrder()"
                        />
                    </div>
                </div>
            </div>

            <!-- KPI 四列 · 用户指标 -->
            <div class="nf-kpi-grid">
                <div class="nf-kpi-card home-kpi">
                    <div class="home-kpi__icon home-kpi__icon--brand">
                        <el-icon><User /></el-icon>
                    </div>
                    <div class="home-kpi__body">
                        <div class="home-kpi__label">总用户数</div>
                        <div class="home-kpi__value">
                            <span>{{ dataContainer.tableData.totalUserCount ? dataContainer.tableData.totalUserCount : 0 }}</span>人
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card home-kpi">
                    <div class="home-kpi__icon home-kpi__icon--green">
                        <el-icon><Plus /></el-icon>
                    </div>
                    <div class="home-kpi__body">
                        <div class="home-kpi__label">今日新增</div>
                        <div class="home-kpi__value">
                            <span>{{ dataContainer.tableData.todayUserCount ? dataContainer.tableData.todayUserCount : 0 }}</span>人
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card home-kpi">
                    <div class="home-kpi__icon home-kpi__icon--blue">
                        <el-icon><Calendar /></el-icon>
                    </div>
                    <div class="home-kpi__body">
                        <div class="home-kpi__label">本月新增</div>
                        <div class="home-kpi__value">
                            <span>{{ dataContainer.tableData.monthUserCount ? dataContainer.tableData.monthUserCount : 0 }}</span>人
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card home-kpi">
                    <div class="home-kpi__icon home-kpi__icon--purple">
                        <el-icon><TrendCharts /></el-icon>
                    </div>
                    <div class="home-kpi__body">
                        <div class="home-kpi__label">本年新增</div>
                        <div class="home-kpi__value">
                            <span>{{ dataContainer.tableData.yearUserCount ? dataContainer.tableData.yearUserCount : 0 }}</span>人
                        </div>
                    </div>
                </div>
            </div>

            <!-- KPI 四列 · 订单/收入 -->
            <div class="nf-kpi-grid">
                <div class="nf-kpi-card home-kpi">
                    <div class="home-kpi__icon home-kpi__icon--orange">
                        <el-icon><ShoppingCart /></el-icon>
                    </div>
                    <div class="home-kpi__body">
                        <div class="home-kpi__label">今日接单订单</div>
                        <div class="home-kpi__value">
                            <span>{{ dataContainer.tableData.dayOrderCount ? dataContainer.tableData.dayOrderCount : 0 }}</span>单
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card home-kpi">
                    <div class="home-kpi__icon home-kpi__icon--brand">
                        <el-icon><Money /></el-icon>
                    </div>
                    <div class="home-kpi__body">
                        <div class="home-kpi__label">今日收入</div>
                        <div class="home-kpi__value">
                            <span>{{ dataContainer.tableData.dayAmount ? dataContainer.tableData.dayAmount : 0 }}</span>元
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card home-kpi">
                    <div class="home-kpi__icon home-kpi__icon--green">
                        <el-icon><Wallet /></el-icon>
                    </div>
                    <div class="home-kpi__body">
                        <div class="home-kpi__label">本月收入</div>
                        <div class="home-kpi__value">
                            <span>{{ dataContainer.tableData.monthAmount ? dataContainer.tableData.monthAmount : 0 }}</span>元
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card home-kpi">
                    <div class="home-kpi__icon home-kpi__icon--blue">
                        <el-icon><Coin /></el-icon>
                    </div>
                    <div class="home-kpi__body">
                        <div class="home-kpi__label">本年收入</div>
                        <div class="home-kpi__value">
                            <span>{{ dataContainer.tableData.yearAmount ? dataContainer.tableData.yearAmount : 0 }}</span>元
                        </div>
                    </div>
                </div>
            </div>

            <!-- 双列明细 -->
            <div class="nf-detail-cols">
                <div class="nf-table-card home-detail">
                    <div class="home-detail__head">
                        <el-icon><UserFilled /></el-icon>
                        <span>用户分析</span>
                    </div>
                    <div class="nf-stat-tile-grid">
                        <div class="nf-stat-tile home-stat-tile">
                            <div class="home-stat-tile__label">总用户统计</div>
                            <div class="home-stat-tile__value">
                                <span>{{ dataContainer.userStat.totalCount ? dataContainer.userStat.totalCount : 0 }}</span>人
                            </div>
                        </div>
                    </div>
                </div>
                <div class="nf-table-card home-detail">
                    <div class="home-detail__head">
                        <el-icon><ShoppingCart /></el-icon>
                        <span>订单统计</span>
                    </div>
                    <div class="nf-stat-tile-grid">
                        <div class="nf-stat-tile home-stat-tile">
                            <div class="home-stat-tile__label">总订单量</div>
                            <div class="home-stat-tile__value">
                                <span>{{ dataContainer.withdrawDataZf.totalCount ? dataContainer.withdrawDataZf.totalCount : 0 }}</span>笔
                            </div>
                        </div>
                        <div
                            class="nf-stat-tile home-stat-tile"
                            v-for="(item, index) in dataContainer.withdrawDataZf.orderStatisticsList"
                            :key="index"
                        >
                            <div class="home-stat-tile__label">{{ item.desc }}订单</div>
                            <div class="home-stat-tile__value">
                                <span>{{ item.count ? item.count : 0 }}</span>笔
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </DefinScrollbar>
</template>
<script>
/**
 * 首页
 */
import { defineComponent, reactive, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import DefinScrollbar from '@/components/definScrollbar.vue';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import {
    User,
    UserFilled,
    Plus,
    Calendar,
    TrendCharts,
    ShoppingCart,
    Money,
    Wallet,
    Coin,
    Filter,
} from '@element-plus/icons-vue';

// import EchartContainer from '@/components/echartContainer.vue';
// import * as echarts from 'echarts';

export default defineComponent({
    components: {
        SvgIcon,
        DefinScrollbar,
        User,
        UserFilled,
        Plus,
        Calendar,
        TrendCharts,
        ShoppingCart,
        Money,
        Wallet,
        Coin,
        Filter,
        // EchartContainer,
    },
    setup() {
        const dataContainer = reactive({
            name: import.meta.env.VITE_APP_name,
            loading: false,
            activeName: 'first',
            totalUsers: '',
            tableData: {},
            userStat: {},
            chongzhiData: {},
            flag: 'day',
            flags: [
                {
                    value: 'day',
                    label: '按天查询'
                }, {
                    value: 'month',
                    label: '按月查询'
                }, {
                    value: 'year',
                    label: '按年查询'
                }
            ],
            info: getNowTime(),
            withdrawDataZf: {
                orderStatisticsList: [],
            },
            zongMoney: 0,


        });
        function isAuth(res) {
            return userApi.isAuth(res)
        }

        // 获取时间
        function getNowTime() {
            const now = new Date();
            const year = now.getFullYear(); //得到年份
            const month = now.getMonth(); //得到月份
            const date = now.getDate(); //得到日期
            const hh = now.getHours() < 10 ? "0" + now.getHours() : now.getHours();
            const mm = now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes();
            const ss = now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds();
            console.log(month)
            const month1 = month + 1;
            const month2 = month1.toString().padStart(2, "0");
            const date1 = date.toString().padStart(2, "0");
            var defaultDate = `${year}-${month2}-${date1}`;
            // var defaultDate = `${year}-${month}-${date} ${hh}:${mm}:${ss}`;

            return defaultDate;
        }
        getNowTime();

        // 选择日期类型
        function orderfenxi() {
            userData()
            rechSelect()
        }
        // 选择日期
        function animeOrder() {
            userData()
            rechSelect()
        }
        // 用户统计
        function _getData() {
            http.get('admin/index/indexStatistics', {}).then((res) => {
                console.log(res.data)
                if (res.code == 0) {
                    dataContainer.tableData = res.data;

                }
            })
        };
        // 用户分析
        function userData() {
            const datas = {
                findDate: dataContainer.info,
                findType: dataContainer.flag
            }
            http.post('admin/index/userStatistics', datas, {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then((res) => {
                console.log(res.data)
                if (res.code == 0) {
                    dataContainer.userStat = res.data;
                }
            })
        };
        //订单分析
        function rechSelect() {
            // const datas = {
            //     data: dataContainer.info,
            //     type: dataContainer.flag
            // }
            http.post('admin/index/orderStatistics', {

                findDate: dataContainer.info,
                findType: dataContainer.flag,
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then((res) => {
                console.log(res.data)
                dataContainer.withdrawDataZf = {}
                let returnData = res.data
                dataContainer.withdrawDataZf = returnData
            })
        };

        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)
            if (event.srcElement.innerText == '用户分析') {
                dataContainer.info = getNowTime()
                userData()
            }
            if (event.srcElement.innerText == '订单统计') {
                dataContainer.info = getNowTime()
                rechSelect()
            }
        };
        _getData();
        userData();
        rechSelect();
        return {
            dataContainer,
            _getData,
            userData,
            handleClick,
            getNowTime,
            orderfenxi,
            animeOrder,
            isAuth,

        };
    },
});


</script>



<style lang="scss" scoped>
.home-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.home-page__top {
    display: grid;
    grid-template-columns: 1fr 320px;
    gap: var(--nf-space-sm);
    margin-bottom: var(--nf-space-md);
    align-items: stretch;
}

.home-page__hero {
    margin-bottom: 0;
}

.home-page__filter {
    margin-bottom: 0;
    display: flex;
    flex-direction: column;
}

.home-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.home-page__select {
    width: 140px;
}

/* KPI card internals */
.home-kpi {
    display: flex;
    align-items: center;
    gap: var(--nf-space-md);
}

.home-kpi__icon {
    flex-shrink: 0;
    width: 44px;
    height: 44px;
    border-radius: var(--nf-radius-sm);
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;

    &--brand {
        background: var(--nf-brand-lite);
        color: var(--nf-brand);
    }

    &--green {
        background: #e8f8ef;
        color: #27ae60;
    }

    &--blue {
        background: #e8f0fe;
        color: #3b82f6;
    }

    &--purple {
        background: #f0e8fe;
        color: #8b5cf6;
    }

    &--orange {
        background: #fef3e8;
        color: #f59e0b;
    }
}

.home-kpi__body {
    flex: 1;
    min-width: 0;
}

.home-kpi__label {
    font-size: 13px;
    color: var(--nf-text-secondary);
    margin-bottom: 4px;
}

.home-kpi__value {
    font-size: 13px;
    color: var(--nf-text-regular);

    span {
        font-size: 22px;
        font-weight: 600;
        color: var(--nf-brand);
        margin-right: 2px;
    }
}

/* Detail columns */
.home-detail__head {
    display: flex;
    align-items: center;
    gap: var(--nf-space-xs);
    font-size: 14px;
    font-weight: 600;
    color: var(--nf-text-primary);
    margin-bottom: var(--nf-space-md);
    padding-bottom: var(--nf-space-sm);
    border-bottom: 1px solid var(--nf-border);

    .el-icon {
        color: var(--nf-brand);
        font-size: 16px;
    }
}

.home-stat-tile__label {
    font-size: 12px;
    color: var(--nf-text-secondary);
    margin-bottom: 6px;
}

.home-stat-tile__value {
    font-size: 12px;
    color: var(--nf-text-regular);

    span {
        font-size: 18px;
        font-weight: 600;
        color: var(--nf-text-primary);
        margin-right: 2px;
    }
}

@media (max-width: 1200px) {
    .home-page__top {
        grid-template-columns: 1fr;
    }
}

@media (max-width: 768px) {
    .nf-kpi-grid {
        grid-template-columns: repeat(2, 1fr);
    }

    .nf-detail-cols {
        grid-template-columns: 1fr;
    }
}
</style>
