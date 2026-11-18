<template>
	<view style="padding-bottom: 120rpx;">
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
	const typeId = ref('');
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const list = reactive([]);
	const status = ref('loadmore'); //加载状态

	onLoad(option => {
		if (option.name) {
			uni.setNavigationBarTitle({
				title: option.name
			})
		}
		if (option.id) {
			typeId.value = option.id
			getlist(); //商品列表
		}
	});
	onShow(() => {

	});
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

	// 获取列表
	const getlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			status: 0, //商品状态(默认0正常 1下架)
			typeId: typeId.value
		}
		proxy.$http.getT('/app/product/page', data).then(res => {
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
		page.value = 1
		getlist();
	});
	onReachBottom(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getlist();
		}
	});
</script>

<style lang="scss">
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

					display: -webkit-box;
					-webkit-box-orient: vertical;
					-webkit-line-clamp: 1;
					/* 显示的行数 */
					overflow: hidden;
					text-overflow: ellipsis;
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