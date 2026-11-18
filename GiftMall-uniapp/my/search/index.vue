<template>
	<view class="">
		<up-sticky bgColor="#ffffff">
			<view class="searchbox">
				<up-search placeholder="搜索需要的礼物" v-model="keyword" bgColor="#F5F5F5" @change="change"></up-search>
			</view>
		</up-sticky>

		<view class="Hotbox" v-if="!isTrue">

			<view class="flex align-center justify-between" v-if="historylist.length!=0">
				<view class="Hotbox_title">历史搜索</view>
				<view @click="deteHistory()">
					<image src="/my/static/dete.png" style="width: 35rpx;height: 35rpx;" mode="widthFix"></image>
				</view>
			</view>
			<view class="HotboxItem">
				<view class="HotboxItem_box" v-for="(item,index) in historylist" :key="index" @click="clickKey(item.value)">
					{{item.value}}
				</view>
			</view>

			<view class="Hotbox_title" v-if="hotlist.length!=0">热门搜索</view>
			<view class="HotboxItem">
				<view class="HotboxItem_box" v-for="(item,index) in hotlist" :key="index" @click="clickKey(item.keyword)">
					{{item.keyword}}
				</view>
			</view>
		</view>
		<view class="" v-else>
			<view class="listbox_Itembox">
				<view v-for="(item,index) in list" :key="index" class="Itembox" @click="goDetail(item)">
					<image :src="item.coverImg?item.coverImg:'/static/logo.png'" mode="aspectFill">
					</image>
					<view class="Itembox_info">
						<view class="Itembox_infoTitle">{{item.name}}</view>
						<view class="Itembox_infocont">
							<view class="Itembox_infocontPrice">¥{{item.price}}</view>
							<view class="Itembox_infocontAdd">
								<up-icon name="plus-circle-fill" color="#F6317A" size="28"></up-icon>
							</view>
						</view>
						<view class="Itembox_info_num">
							<view class="Itembox_info_numOldprice">¥{{item.originalPrice}}</view>
							<view class="Itembox_info_numSale">已售{{item.sales}}</view>
						</view>
					</view>
				</view>
			</view>
			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>
		</view>
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
		onReachBottom
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	const keyword = ref('');
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const list = reactive([]);
	const status = ref('loadmore'); //加载状态
	const isTrue = ref(false);
	const hotlist = reactive([]);
	const historylist = reactive([])

	onLoad(option => {
		getHistory();
		getHot();
	});
	onShow(() => {

	});
	// 选择搜索
	const clickKey = (e) => {
		keyword.value = e
		isTrue.value = true
		page.value = 1
		getlist();
	};
	// 搜索
	const change = (e) => {
		// console.log(e)
		if (e) {
			isTrue.value = true
			keyword.value = e
			page.value = 1
			getlist();
		} else {
			isTrue.value = false
			getHistory();
			getHot();
		}
	};
	// 删除历史搜索
	const deteHistory = () => {
		proxy.$http.getT('/app/searchHistory/clear').then(res => {
			if (res.code == 0) {
				uni.showToast({
					title: '删除成功',
					icon: 'none'
				})
				getHistory();
			}
		})
	};
	// 跳转商品详情
	const goDetail = (item) => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/Commodity/detail?id=' + item.id
		})
	};
	// 热门搜索
	const getHot = () => {
		proxy.$http.getT('/app/searchHistory/hot').then(res => {
			if (res.code == 0) {
				hotlist.splice(0, hotlist.length, ...res.data);
			}
		})
	};
	// 历史搜索
	const getHistory = () => {
		proxy.$http.getT('/app/searchHistory/list').then(res => {
			if (res.code == 0) {
				historylist.splice(0, historylist.length, ...res.data);
			}
		})
	};
	// 获取列表
	const getlist = () => {
		let data = {
			// page: page.value,
			// limit: limit.value,
			// status: 0, //商品状态(默认0正常 1下架)
			keyword: keyword.value
		}
		proxy.$http.getT('/app/product/search', data).then(res => {
			if (res.code == 0) {
				list.splice(0, list.length, ...res.data);
				// pages.value = res.data.pages
				// if (page.value < pages.value) {
				// 	status.value = 'loadmore';
				// } else {
				// 	status.value = 'nomore';
				// }
				// if (page.value === 1) {
				// 	list.splice(0, list.length, ...res.data.records);
				// } else {
				// 	list.push(...res.data.records);
				// }
				// status.value = 'nomore';
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		})
	};
	// 跳转登录
	const goLoginInfo = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
	};
	onPullDownRefresh(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		if (keyword.value) {
			page.value = 1;
			getlist();
		} else {
			uni.stopPullDownRefresh()
		}
	});
	onReachBottom(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		if (keyword.value) {
			if (page.value < pages.value) {
				page.value += 1
				status.value = 'loading';
				getlist();
			}
		}

	});
</script>

<style lang="scss">
	page {
		background: #FFFFFF;
	}

	.searchbox {
		padding: 20rpx 30rpx;
	}

	.Hotbox {
		margin: 30rpx;

		.Hotbox_title {
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 28rpx;
			color: #333333;
		}

		.HotboxItem {
			display: flex;
			flex-wrap: wrap;
			align-items: center;
			margin-top: 20rpx;

			.HotboxItem_box {
				height: 60rpx;
				background: #F5F5F5;
				border-radius: 8rpx;
				font-family: PingFang SC;
				font-weight: 500;
				font-size: 24rpx;
				color: #333333;
				display: flex;
				align-items: center;
				justify-content: center;
				padding: 0 20rpx;
				margin-right: 30rpx;
				margin-bottom: 20rpx;
			}
		}
	}

	.listbox_Itembox {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		margin: 0 30rpx;

		.Itembox {
			width: 333rpx;
			background: #FFFFFF;
			border-radius: 16rpx;
			margin-bottom: 20rpx;

			image {
				width: 333rpx;
				height: 331rpx;
				border-radius: 16rpx 16rpx 0rpx 0rpx;
			}

			.Itembox_info {
				padding: 20rpx;

				.Itembox_infoTitle {
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 32rpx;
					color: #1A1A1A;
				}

				.Itembox_infocont {
					display: flex;
					align-items: center;
					justify-content: space-between;
					margin: 10rpx 0;

					.Itembox_infocontPrice {
						font-family: DIN;
						font-weight: bold;
						font-size: 38rpx;
						color: #F6317A;
					}

					.Itembox_infocontAdd {}
				}

				.Itembox_info_num {
					display: flex;
					align-items: center;
					justify-content: space-between;

					.Itembox_info_numOldprice {
						font-family: DIN;
						font-weight: 500;
						font-size: 28rpx;
						color: #CCCCCC;
						text-decoration-line: line-through;
					}

					.Itembox_info_numSale {
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 24rpx;
						color: #CCCCCC;
					}
				}
			}
		}
	}
</style>