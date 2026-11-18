<template>
	<view>
		<!-- 发票类型 -->
		<up-sticky offset-top="0" zIndex="9999">
			<view class="selectTypes-box">
				<view class="selectTypes-boxTab">
					<view :class="{active:current==index}" @click="bindTab(index,item.id)" class="selectTypes-item"
						v-for="(item,index) in tabs" :key="index">
						{{item.name}}
					</view>
				</view>
				<view class="shgaixuan" @click="qiehuan">
					<up-icon name="list" color="#333333" size="28"></up-icon>
				</view>
			</view>
		</up-sticky>


		<!-- 筛选下拉 -->
		<up-popup :show="show" mode="top" @close="show = false" zIndex="98" round="12">
			<view class="selectDown">
				<view class="selectDown-box">
					<view class="selectDown-box-title">时间范围</view>
					<view class="selectDown-box-time ">
						<view class="selectDown-box-time-l " @click="showStartTime=true">
							{{startTime?startTime:'开始时间'}}
						</view>
						<view class="margin-lr">至</view>
						<view class="selectDown-box-time-l " @click="showEndTime=true">
							{{endtime?endtime:'结束时间'}}
						</view>
					</view>
				</view>

				<view class="selectDown-box">
					<view class="selectDown-box-title">订单编号</view>
					<view class="selectDown-box-orderno ">
						<up-input placeholder="请填写纳税人识别号" border="none" v-model="orderNo"
							input-align="left"></up-input>
					</view>
				</view>
				<view class="selectDown-btn">
					<view class="selectDown-btn-l " @click="qiehuan">重置</view>
					<view class="selectDown-btn-r " @click="submit">确定</view>
				</view>
			</view>
		</up-popup>


		<!-- 可开发票列表 -->
		<view class="fpList ">

			<view class="fpListItem " v-for="(item,index) in orderList" :key="index">
				<view class="fpListItem_info ">
					<view class="fpListItem_infoCheck">
						<checkbox-group @change="checkboxChange(item)">
							<checkbox :checked="item.checked" shape="circle" activeBackgroundColor="#F6317A"
								iconColor="#FFFFFF" activeBorderColor="#FFFFFF" style="transform:scale(0.8)"
								:value="index" />
						</checkbox-group>
					</view>
					<view class="fpListItem_infoCont" @click.stop="gototx(item.orderNo,item.payMoney)">
						<view class="fpListItem_infoContTop margin-bottom-xs">
							<view class="fpListItem_infoContTop_data" v-if="item.invoiceStatus==0">未开票</view>
							<view class="fpListItem_infoContTop_data" v-if="item.invoiceStatus==1">待开票 </view>
							<view class="fpListItem_infoContTop_data" v-if="item.invoiceStatus==2">已开票</view>
							<view class="fpListItem_infoContTop_data" v-if="item.invoiceStatus==3">已拒绝</view>
							<text class="fpListItem_infoContTop_data">{{item.createTime}}</text>
						</view>
						<view v-for="(goods,ind) in item.productList" :key="ind" >
							<view class="fpListItem_infoContSAdd">
								<span>
									<text></text>
								</span>
								{{goods.giftProduct.name}}
							</view>
							<view class="fpListItem_infoCont_sku" v-if="goods.giftProductSku">{{goods.giftProductSku.detailJson}}</view>
						</view>
						
						
						<view class="fpListItem_infoCont_orderno">{{item.orderNo}}
						<text @click.stop="copyClick(item.orderNo)">复制</text>
						</view>
					</view>
				</view>
				<view class="fpListItem_Price">
					<text>{{item.payMoney}}</text>元
				</view>
			</view>

		</view>

		<!-- 加载数据 -->
		<up-loadmore :status="status" v-if="orderList.length>0" />
		<!-- 暂无数据 -->
		<empty v-if="orderList.length==0"></empty>


		<!-- 底部操作栏 -->
		<view class="select ">
			<view class="select-se ">
				已选<text>{{count}}</text>个记录，共<text>{{price.toFixed(2)}}</text>元
			</view>
			<view class="select-box">
				<view class="">
					<up-checkbox label="本页全选" name="agree" usedAlone v-model:checked="selectAll" shape="circle"
						activeColor="#F6317A" @change="checkboxChanges" size="24" labelColor="#333333">
					</up-checkbox>
				</view>
				<view class="select-boxBtn " v-if="ids.length==0">
					开发票
				</view>
				<view @click="gotokp()" class="select-boxBtn " style="background: #F6317A;" v-if="ids.length>0">
					开发票
				</view>
			</view>
		</view>

		<!-- 开始时间 -->
		<up-datetime-picker :show="showStartTime" v-model="startTimevalue" mode="date"
			@confirm="confirmStartTime"></up-datetime-picker>

		<!-- 开始时间 -->
		<up-datetime-picker :show="showEndTime" v-model="endTimevalue" mode="date"
			@confirm="confirmEndTime"></up-datetime-picker>
	</view>
</template>

<script setup>
	import {
		ref,
		watch,
		reactive,
		getCurrentInstance
	} from 'vue';
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom,
	} from '@dcloudio/uni-app';
	import {
		timeFormat
	} from '@/uni_modules/uview-plus';
	const {
		proxy
	} = getCurrentInstance();
	const orderList = reactive([]);
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	//加载状态
	const status = ref('loadmore');

	const tabs = reactive([{
		id: 1,
		name: '已完成订单'
	}]);
	const current = ref(0);
	const show = ref(false);
	const startTime = ref('');
	const startTimevalue = ref(Date.now());
	const showStartTime = ref(false);
	const showEndTime = ref(false);
	const endtime = ref('');
	const endTimevalue = ref(Date.now());
	const orderNo = ref('');
	const count = ref(0);
	const price = ref(0);
	const selectAll = ref(false);
	const ids = reactive([]);

	//监听选择变化计算总价
	watch(ids, (newVal, oldVal) => {
		price.value = 0
		orderList.map(item => {
			if (item.checked == true) {
				price.value += item.payMoney
			}
		})
	}, {
		deep: true
	});
	onLoad(option => {

	});
	onShow(() => {
		ids.length=0
		selectAll.value =  false
		page.value = 1
		getOrderList();
	});
	// 一键复制
	const copyClick = (copy) => {
		uni.setClipboardData({
			data: copy,
			success: function(res) {
				uni.getClipboardData({
					success: function(res) {
						uni.showToast({
							title: "复制成功",
						});
					},
				});
			},
		});
	};
	const gototx=(id,price)=>{
		uni.navigateTo({
			url: '/my/invoice/kaipiao?indentNumber=' + id + '&price=' + price
		})
	};
	// 多个开票
	const gotokp = () => {
		let id = ids.join(',')
		uni.navigateTo({
			url: '/my/invoice/kaipiao?indentNumber=' + id + '&price=' + price.value
		})
	};
	// 点击筛选
	const qiehuan = () => {
		startTime.value = ''
		endtime.value = ''
		orderNo.value = ''
		show.value = !show.value
	};
	//选择开始时间
	const confirmStartTime = (e) => {
		let time = timeFormat(e.value, 'yyyy-mm-dd');
		console.log(time)
		startTime.value = time
		showStartTime.value = false
	};
	// 选择结束时间
	const confirmEndTime = (e) => {
		let time = timeFormat(e.value, 'yyyy-mm-dd');
		console.log(time)
		endtime.value = time
		showEndTime.value = false
	};
	// 点击筛选数据
	const submit = () => {
		show.value = false
		page.value = 1
		getOrderList();
	};
	// 顶部tab 切换筛选
	const bindTab = (index, id) => {
		current.value = index
		page.value = 1
		orderList.value = []
		console.log(orderList.value)
		getPrice()
		startTime.value = ''
		endtime.value
		getOrderList();
	};
	//计算单数与价格
	const getPrice = () => {
		selectAll.value = false
		orderList.value.map(item => {
			item.checked = false
		})
		ids.value = []
		count.value = ids.length
		price.value = 0
	};
	// 选中某个复选框时，由checkbox时触发
	const checkboxChange = (e) => {
		e.checked = !e.checked
		if (ids.indexOf(e.orderNo) == -1) { //如果没有选中则把goodsid增加到数组中
			ids.push(e.orderNo)
		} else { //如果找到了则把goodsid删除
			ids.splice(ids.indexOf(e.orderNo), 1)
		}
		if (ids.length == orderList.length) { //判断是否全部选中
			selectAll.value = true
		} else {
			selectAll.value = false
		}
		count.value = ids.length

	};
	//全选
	const checkboxChanges = (e) => {
		ids.splice(0, ids.length);
		if (selectAll.value == false) { //全选（把总复选框选中并把id赋给数组）
			orderList.map(item => {
				item.checked = true
				ids.push(item.orderNo)
			})
		} else { //全不选
			orderList.map(item => {
				item.checked = false
			})
		}
		count.value = ids.length
		price.value = 0
		orderList.map(item => {
			if (item.checked == true) {
				price.value += item.indentMoney
			}
		})
	};
	//订单数据
	const getOrderList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			indentType: 1,
			startTime: startTime.value,
			endTime: endtime.value,
			userId: uni.getStorageSync('userId'),
			indentNumber: orderNo.value,
		}
		proxy.$http.getT("/app/invoice/getIndentList", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				res.data.records.map(item => {
					item.checked = false
				})
				if (page.value === 1) {
					orderList.splice(0, orderList.length, ...res.data.records);
				} else {
					orderList.push(...res.data.records);
				}
				status.value = 'nomore';
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		});
	};
	onPullDownRefresh(() => {
		page.value = 1;
		getOrderList();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getOrderList();
		}
	});
</script>

<style lang="scss">
	.selectTypes-box {
		display: flex;
		align-items: center;
		justify-content: space-between;
		background: #FFFFFF;
		padding: 20rpx 30rpx;
		position: relative;
		z-index: 99;

		.selectTypes-boxTab {
			display: flex;
			align-items: center;

			.selectTypes-item {
				background-color: #f2f2f2;
				padding: 5px 10px 5px 10px;
				border-radius: 6px;
				margin-right: 20rpx;
				font-size: 12px;
			}

			.active {
				background-color: #FFE9F1 !important;
				color: #F6317A;
			}
		}

		.shgaixuan {}
	}

	.selectDown {
		padding-top: 180rpx;

		.selectDown-box {
			padding: 0 30rpx;

			.selectDown-box-title {
				font-weight: 500;
				font-size: 30rpx;
				color: #333333;
			}

			// 订单编号
			.selectDown-box-orderno {
				background: #f2f2f2;
				border-radius: 12rpx;
				padding: 10rpx 30rpx;
				margin: 30rpx 0;
			}

			// 时间
			.selectDown-box-time {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin: 20rpx 0;

				.selectDown-box-time-l {
					flex: 1;
					height: 35px;
					background-color: #f2f2f2;
					border-radius: 8px;
					display: flex;
					align-items: center;
					justify-content: center;
				}
			}
		}

		.selectDown-btn {
			display: flex;
			align-items: center;
			justify-content: space-between;
			width: 100%;
			height: 80rpx;
			margin-top: 30rpx;

			.selectDown-btn-l {
				width: 50%;
				height: 100%;
				background-color: #f2f2f2;
				border-radius: 0 0 0 10px;
				display: flex;
				align-items: center;
				justify-content: center;
			}

			.selectDown-btn-r {
				width: 50%;
				background-color: #F6317A;
				color: #fff;
				height: 100%;
				border-radius: 0 0 10px 0;
				display: flex;
				align-items: center;
				justify-content: center;
			}
		}
	}

	::v-deep .uni-checkbox-input {
		border-radius: 55px !important;
	}

	.fpList {

		.fpListItem {

			background: #FFFFFF;
			margin: 20rpx 30rpx 0;
			border-radius: 24rpx;
			padding: 30rpx;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.fpListItem_info {
				display: flex;
				align-items: center;
				flex: 1;

				.fpListItem_infoCont {
					flex: 1;

					.fpListItem_infoContTop {
						display: flex;
						align-items: center;
						justify-content: space-between;

						.fpListItem_infoContTop_state {
							font-family: Source Han Sans CN;
							font-weight: 500;
							font-size: 28rpx;
							color: #F6317A;
						}

						.fpListItem_infoContTop_data {
							font-family: PingFang SC;
							font-weight: 500;
							font-size: 24rpx;
							color: #999999;
						}
					}

					.fpListItem_infoContSAdd {
						span {
							display: inline-block;
							width: 10px;
							height: 10px;
							background-color: #f69393;
							border-radius: 50%;
							position: relative;


							text {
								display: inline-block;
								width: 5px;
								height: 5px;
								background-color: #fff;
								border-radius: 50%;
								position: absolute;
								top: 50%;
								left: 50%;
								-webkit-transform: translate(-50%, -50%);
								transform: translate(-50%, -50%);

							}
						}

						font-weight: 400;
						font-size: 28rpx;
						color: #000000;
					}
					.fpListItem_infoCont_sku{
						font-weight: 400;
						font-size: 26rpx;
						color: #666666;
						margin-left: 20rpx;
					}
					.fpListItem_infoCont_orderno{
						font-weight: 400;
						font-size: 28rpx;
						color: #000000;
						display: flex;
						align-items: center;
						
						text {
							font-family: PingFang SC;
							font-weight: 500;
							font-size: 18rpx;
							color: #999999;
							width: 50rpx;
							height: 26rpx;
							background: #FFFFFF;
							border-radius: 4rpx;
							border: 1px solid #CCCCCC;
							display: flex;
							align-items: center;
							justify-content: center;
							margin-left: 20rpx;
						}
					}

				}
			}

			.fpListItem_Price {
				display: flex;
				align-items: center;
				margin-left: 20rpx;

				text {
					font-size: 30rpx;
					font-weight: 700;
					margin-right: 5rpx;
				}
			}
		}
	}

	.select {
		width: 100%;
		position: fixed;
		bottom: 0;
		background-color: #FFFFFF;
		z-index: 1;

		.select-se {
			display: flex;
			align-items: center;
			height: 50rpx;
			background-color: #f2f2f2;
			font-size: 24rpx;
			padding: 0 30rpx;

			text {
				margin: 0 10rpx;
				color: #f69cb9;
			}
		}

		.select-box {
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 15rpx 30rpx;

			.select-boxBtn {
				display: flex;
				align-items: center;
				justify-content: center;
				width: 240rpx;
				height: 80rpx;
				border-radius: 60rpx;
				background-color: #dbdbdb;
				color: #FFFFFF;
			}
		}
	}
</style>