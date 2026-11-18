<template>
	<view style="padding-bottom: 200rpx;">
		<!-- 2.0.19支持autoBack，默认为false -->
		<up-navbar leftIcon="" height="50" :bgColor="!isTop?'#FFEEF4':'#FFFFFF'" :placeholder="true"
			:safeAreaInsetTop="true">
			<template #center>
				<view class="u-nav-slot navbarContent">
					<view class="navbarContent_pay" :class="current==1?'navbarContentAct':''" @click="bindNavbar(1)">
						我购买的</view>
					<view class="navbarContent_Received" :class="current==2?'navbarContentAct':''"
						@click="bindNavbar(2)">我收到的</view>
				</view>
			</template>
		</up-navbar>
		<!-- <up-navbar leftIcon="" :bgColor="!isTop?'linear-gradient(180deg, #FFE3ED 0%, #FFEEF4 100%)':'#FFFFFF'"
			:placeholder="true" :safeAreaInsetTop="true">

		</up-navbar> -->


		<!-- #ifdef H5 -->
		<up-sticky customNavHeight="0">
		<!-- #endif -->
			<!-- #ifndef H5 -->
			<up-sticky customNavHeight="94">
			<!-- #endif -->
				<view :class="!isTop?'bgColor':'bg' ">
					<view class="" v-if="current==1">
						<up-tabs :list="listTab" :current="listTabIndex" @click="clickTab" :activeStyle="activeStyle"
							:inactiveStyle="inactiveStyle" lineColor="#F6317A" :scrollable="true"
							:itemStyle="itemStyle"></up-tabs>
					</view>
					<view class="" v-if="current==2">
						<up-tabs :list="listTabpay" @click="clickTabPay" :current="listTabpayIndex"
							:activeStyle="activeStyle" :itemStyle="itemStyle" :inactiveStyle="inactiveStyle"
							lineColor="#F6317A" :scrollable="false"></up-tabs>
					</view>
				</view>
			</up-sticky>

			<view class="listbox" :style="!isTop?'z-index: 998;':'z-index: 9;'">
				<view class="listboxItem" v-for="(item,index) in list" :key="index" @click.stop="goDetail(item)">
					<view class="listboxItem_Head">
						<view class="listboxItem_Head_Data">{{item.createTime}}</view>
						<!-- * 订单状态（1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送 13已领取 14已拒绝 16待发货 17已开发票 ） -->
						<view class="listboxItem_Head_State" v-if="item.status==1">待付款</view>
						<view class="listboxItem_Head_State" v-if="item.status==2">已付款</view>
						<view class="listboxItem_Head_State" v-if="item.status==11">待赠送</view>
						<view class="listboxItem_Head_State" v-if="item.status==12">已赠送</view>
						<view class="listboxItem_Head_State" v-if="item.status==14">已拒绝</view>
						<view class="listboxItem_Head_State" v-if="item.status==13">已领取</view>
						<view class="listboxItem_Head_State" v-if="item.status==16">待发货</view>
						<view class="listboxItem_Head_State" v-if="item.status==3">待收货</view>
						<view class="listboxItem_Head_State" v-if="item.status==4">已完成</view>
						<view class="listboxItem_Head_State" v-if="item.status==5">已取消</view>
						<view class="listboxItem_Head_State" v-if="item.status==7">已退款</view>
					</view>
					<block v-if="item.productList">
						<view class="listboxItem_content" v-for="(goods,ind) in item.productList" :key="ind">
							<image :src="goods.giftProduct?goods.giftProduct.coverImg:'/static/logo.png'"
								mode="aspectFill">
							</image>
							<view class="listboxItem_content_info">
								<view class="listboxItem_content_infoTitle">{{goods.giftProduct.name}}</view>

								<view class="listboxItem_content_infoSku">{{goods.skuDetailJson}} <text>
										x{{goods.num}}</text></view>
							</view>
						</view>
					</block>

					<view class="listboxItem_line"></view>
					<view class="flex justify-between">
						<view class="listboxItem_price">
							<text>¥{{item.payMoney}}</text>
						</view>
						<view class="flex ">
							<view class="listboxItem_btn" v-if="current==1&&item.status==11"
								@click.stop="userGift(item)">
								送给好友</view>
							<view class="listboxItem_btnshi " v-if="current==2&&item.status==13">添加地址</view>
							<!-- <view class="listboxItem_btn margin-left" v-if="current==2&&item.status==16"
							@click.stop="cancelGift(item)">拒绝礼物</view> -->
							<view class="listboxItem_btn" v-if="current==2&&item.status==3"
								@click.stop="confirmOrder(item)">确认收货</view>
							<view class="listboxItem_btn" v-if="current==2&&item.status==16" @click.stop="goIndex()">回礼
							</view>
							<view class="listboxItem_btnshi margin-left" v-if="current==1&&item.status==1">立即支付</view>
							<view class="listboxItem_btnhui margin-left" v-if="current==1&&item.status==1"
								@click.stop="cancelOrder(item)">取消订单</view>
						</view>
					</view>
				</view>
			</view>
			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		getCurrentInstance
	} from 'vue';
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom,
		onPageScroll
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	onPageScroll(e => {
		// console.log(e)
		if (e.scrollTop > 0) {
			isTop.value = true
		} else {
			isTop.value = false
		}
	});
	const isTop = ref(false);
	const current = ref(1);
	// 订单状态（1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送 13已领取 14已拒绝）
	const listTab = reactive([{
		status: '',
		name: '全部'
	}, {
		status: '1',
		name: '待付款'
	}, {
		status: '11',
		name: '待赠送'
	}, {
		status: '16',
		name: '待发货'
	}, {
		status: '3',
		name: '待收货'
	}, {
		status: '4',
		name: '已完成'
	}, {
		status: '5',
		name: '已取消'
	}, {
		status: '7',
		name: '已退款'
	}]);
	const listTabIndex = ref(0);
	const listTabpay = reactive([{
		name: '全部'
	}, {
		status: '16',
		name: '待发货'
	}, {
		status: '3',
		name: '待收货'
	}, {
		status: '4',
		name: '已完成'
	}]);
	const listTabpayIndex = ref(0);
	const activeStyle = ref({
		'fontWeight': 'bold',
		'fontSize': '30rpx',
		'color': ' #333333'
	});
	const inactiveStyle = ref({
		'fontWeight': '500',
		'fontSize': '26rpx',
		'color': ' #999999'
	});
	const itemStyle = ref({
		'paddingLeft': '30rpx',
		'paddingRight': '30rpx',
		'height': '70rpx'
	});
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const list = reactive([]);
	const status = ref('loadmore'); //加载状态




	onLoad(option => {

	});
	onShow(() => {
		
		if (uni.getStorageSync('orderIndex')) {
			current.value = uni.getStorageSync('orderIndex')
			listTabIndex.value = 0
			listTabpayIndex.value = 0
			uni.removeStorageSync('orderIndex')
		}
		
		if (uni.getStorageSync('token')) {
			page.value = 1;
			getOrderlist();
		} else {
			list.length = 0
		}
	});
	// 跳转首页
	const goIndex = () => {
		uni.switchTab({
			url: '/pages/index/index'
		})
	};
	// 顶部切换  1我购买的 2 我收到的
	const bindNavbar = (index) => {
		current.value = index
		if (uni.getStorageSync('token')) {
			listTabIndex.value = 0
			listTabpayIndex.value = 0
			page.value = 1
			getOrderlist();
		} else {
			list.length = 0
		}

	};
	//我购买的 tab栏位切换
	const clickTab = (e) => {
		console.log(e)
		if (uni.getStorageSync('token')) {
			listTabIndex.value = e.index
			page.value = 1
			getOrderlist();
		} else {
			list.length = 0
		}
	};
	//我收到的 tab栏位切换
	const clickTabPay = (e) => {
		console.log(e)
		if (uni.getStorageSync('token')) {
			listTabpayIndex.value = e.index
			page.value = 1
			getOrderlist();
		} else {
			list.length = 0
		}

	};
	// 跳转详情
	const goDetail = (e) => {
		if (current.value == 1) {
			uni.navigateTo({
				url: '/my/order/detail?orderId=' + e.id
			})
		} else {
			if (e.status == 13) {
				uni.navigateTo({
					url: '/my/order/ReceivedDetail?orderId=' + e.id
				})
			} else {
				uni.navigateTo({
					url: '/my/order/detail?orderId=' + e.id
				})
			}

		}

	};
	onPullDownRefresh(() => {
		let token = uni.getStorageSync('token');
		if (token) {
			page.value = 1;
			getOrderlist();
		} else {
			uni.stopPullDownRefresh()
		}

	});
	onReachBottom(() => {

		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getOrderlist();
		}
	});
	const getOrderlist = () => {
		let userId = uni.getStorageSync('userId');
		// 我购买的
		if (current.value == 1) {

			let state = listTab[listTabIndex.value].status?listTab[listTabIndex.value].status:''
			let data = {
				page: page.value,
				limit: limit.value,
				userId: userId,
				status: state,
			}

			proxy.$http.getT('/app/order/purchased', data).then(res => {
				if (res.code == 0) {
					pages.value = res.data.pages
					if (page.value < pages.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}
					if (page.value === 1) {
						list.splice(0, list.length, ...res.data.records);
					} else {
						list.push(...res.data.records);
					}
					status.value = 'nomore';
				}
				status.value = 'nomore';
				uni.stopPullDownRefresh();
			})
		} else {
			let state = listTabpay[listTabpayIndex.value].status?listTabpay[listTabpayIndex.value].status:''
			let data = {
				page: page.value,
				limit: limit.value,
				userId: userId,
				status: state,
			}
			proxy.$http.getT('/app/order/received', data).then(res => {
				if (res.code == 0) {
					pages.value = res.data.pages
					if (page.value < pages.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}
					if (page.value === 1) {
						list.splice(0, list.length, ...res.data.records);
					} else {
						list.push(...res.data.records);
					}
					status.value = 'nomore';
				}
				status.value = 'nomore';
				uni.stopPullDownRefresh();
			})
		}
	};
	const goLoginInfo = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
	};
	// 取消订单
	const cancelOrder = (e) => {
		console.log(e)
		uni.showModal({
			title: '温馨提示',
			content: '确定取消订单吗？',
			showCancel: true,
			cancelText: '取消',
			confirmText: '确认',
			success: res => {
				if (res.confirm) {
					uni.showLoading({
						title: '取消中...'
					})
					proxy.$http.getT('/app/order/cancel?orderId=' + e.id)
						.then(res => {
							if (res.code == 0) {
								uni.showToast({
									title: '订单取消成功'
								});
								setTimeout(function() {
									page.value = 1;
									getOrderlist();
								}, 100)

							} else {
								uni.hideLoading();
								uni.showModal({
									showCancel: false,
									title: '提示',
									content: res.msg
								});
							}
						});
				}
			}
		});
	};
	// 送给好友
	const userGift = (e) => {
		if (e.cardId) {
			uni.navigateTo({
				url: '/my/card/send?orderId=' + e.id
			})
		} else {
			uni.navigateTo({
				url: '/my/order/detail?orderId=' + e.id
			})
		}

	};
	// 确认收货
	const confirmOrder = (e) => {
		uni.showModal({
			title: '温馨提示',
			content: '确认收货吗？',
			showCancel: true,
			cancelText: '取消',
			confirmText: '确认',
			success: res => {
				if (res.confirm) {
					uni.showLoading({
						title: '加载中...'
					})
					proxy.$http.getT('/app/order/confirm?id=' + e.id)
						.then(res => {
							if (res.code == 0) {
								uni.showToast({
									title: '订单已完成'
								});
								setTimeout(function() {
									page.value = 1;
									getOrderlist();
								}, 100)

							} else {
								uni.hideLoading();
								uni.showModal({
									showCancel: false,
									title: '提示',
									content: res.msg
								});
							}
						});
				}
			}
		});
	};
	// 拒绝礼物
	const cancelGift = (e) => {
		uni.showModal({
			title: '温馨提示',
			content: '确认拒绝礼物吗？',
			showCancel: true,
			cancelText: '取消',
			confirmText: '确认',
			success: res => {
				if (res.confirm) {
					uni.showLoading({
						title: '加载中...'
					})
					proxy.$http.getT('/app/order/refuse?id=' + e.id)
						.then(res => {
							if (res.code == 0) {
								uni.showToast({
									title: '订单已拒绝'
								});
								setTimeout(function() {
									page.value = 1;
									getOrderlist();
								}, 100)

							} else {
								uni.hideLoading();
								uni.showModal({
									showCancel: false,
									title: '提示',
									content: res.msg
								});
							}
						});
				}
			}
		});
	};
</script>

<style lang="scss">
	page {
		background: #F5F5F5;
	}


	.navbarContent {
		display: flex;
		align-items: center;
		border: 1rpx solid #F6317A;
		border-radius: 35rpx;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #F6317A;



		.navbarContentAct {
			background: #F6317A !important;
			color: #FFFFFF !important;
			font-size: 32rpx !important;
		}

		.navbarContent_pay {
			width: 183rpx;
			height: 69rpx;
			border-radius: 35rpx 0rpx 0rpx 35rpx;
			display: flex;
			align-items: center;
			justify-content: center;
		}

		.navbarContent_Received {
			width: 183rpx;
			height: 69rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			border-radius: 0rpx 35rpx 35rpx 0rpx;
		}
	}

	.bg {
		background: #FFFFFF;
		padding-bottom: 20rpx;
	}

	.bgColor {
		background: linear-gradient(0deg, #FFFFFF 20%, #FFEEF4 100%);
		height: 150rpx;
	}

	.listbox {
		position: relative;
		top: -80rpx;


		.listboxItem {
			background: #FFFFFF;
			border-radius: 16rpx;
			margin: 20rpx 30rpx;
			padding: 30rpx;

			.listboxItem_Head {
				display: flex;
				align-items: center;
				justify-content: space-between;

				.listboxItem_Head_Data {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 24rpx;
					color: #999999;
				}

				.listboxItem_Head_State {
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 26rpx;
					color: #F6317A;
				}
			}

			.listboxItem_content {
				display: flex;
				margin-top: 15rpx;

				image {
					width: 140rpx;
					height: 140rpx;
					border-radius: 10rpx;
				}

				.listboxItem_content_info {
					flex: 1;
					margin-left: 20rpx;

					.listboxItem_content_infoTitle {
						font-family: Source Han Sans CN;
						font-weight: 500;
						font-size: 28rpx;
						color: #333333;
					}

					.listboxItem_content_infoSku {
						font-family: Source Han Sans CN;
						font-weight: 400;
						font-size: 24rpx;
						color: #999999;
					}
				}
			}

			.listboxItem_line {
				background: #E6E6E6;
				width: 100%;
				height: 1px;
				margin: 20rpx 0;
			}

			.listboxItem_price {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 24rpx;
				color: #999999;

				text {
					font-size: 32rpx;
					color: #F6317A;
				}
			}

			.listboxItem_btn {
				width: 150rpx;
				height: 50rpx;
				background: #FFFFFF;
				border-radius: 25rpx;
				border: 1px solid #EF276A;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 24rpx;
				color: #F6317A;
				display: flex;
				align-items: center;
				justify-content: center;
			}

			.listboxItem_btnshi {
				width: 150rpx;
				height: 50rpx;
				background: #F6317A;
				border-radius: 25rpx;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 24rpx;
				color: #FFFFFF;
				display: flex;
				align-items: center;
				justify-content: center;
			}

			.listboxItem_btnhui {
				width: 150rpx;
				height: 50rpx;
				background: #F5F5F5;
				border-radius: 25rpx;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 24rpx;
				color: #666666;
				display: flex;
				align-items: center;
				justify-content: center;
			}
		}
	}
</style>