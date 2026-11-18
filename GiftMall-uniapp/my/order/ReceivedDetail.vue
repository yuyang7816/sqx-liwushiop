<template>
	<view>
		<view class="bgColor"> </view>
		<view class="box">
			<view class="boxImage">
				<image :src="orderInfo.cardImg?orderInfo.cardImg:'/static/logo.ong'" mode="aspectFill">
				</image>
			</view>
			<view class="cardText">{{orderInfo.blessing}}</view>
			<view class="order_title" v-if="orderInfo.giftProduct">{{orderInfo.giftProduct.name}}</view>
			<view class="addbox">
				<view class="addboxItem">
					<view class="addboxItem_tite">收件人</view>
					<view class="addboxItem_Input">
						<up-input placeholder="收件人姓名" border="none" v-model="userName"></up-input>
					</view>
				</view>
				<view class="addboxItem">
					<view class="addboxItem_tite">手机号</view>
					<view class="addboxItem_Input">
						<up-input placeholder="收件人手机号" border="none" v-model="phone"></up-input>
					</view>
				</view>
				<view class="addboxItem">
					<view class="addboxItem_tite">所在地区</view>
					
					<view class="addboxItem_Input" @click="openMap">
						<!-- <up-input placeholder="请选择省/市/区" border="none" v-model="city"></up-input> -->
						<view v-if="provinces">{{provinces}}</view>
						<view v-else class="input-placeholder">请选择省/市/区</view>
						<up-icon name="arrow-down" color="#999999" size="14"></up-icon>
					</view>
				</view>
				<view class="addboxItem" style="border: none;">
					<view class="addboxItem_tite">详细地址</view>
					<view class="addboxItem_Input">
						<up-input placeholder="请填写详细地址" border="none" v-model="address"></up-input>
					</view>
				</view>
			</view>
		</view>
		<view class="btn" @click="submit" v-if="orderInfo.status==13||orderInfo.status==16">确认地址</view>
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

	const userName = ref('');
	const phone = ref('');
	const provinces = ref('');
	const address = ref('');
	const userId = ref('');
	const province = ref('');
	const city = ref('');
	const district = ref('');
	const latitude = ref('');
	const longitude = ref('');

	const value = ref('');
	const orderId = ref('');
	const orderInfo = ref({});

	onLoad(option => {
		userId.value = uni.getStorageSync('userId');
		if (option.orderId) {
			orderId.value = option.orderId
			uni.showLoading({
				title: '加载中...'
			})
			getDetail()
		}
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
	// 获取订单详情
	const getDetail = () => {
		proxy.$http.getT('/app/order/info?orderId=' + orderId.value).then(res => {
			if (res.code == 0) {
				orderInfo.value = res.data
				userName.value = res.data.giveUserName
				phone.value = res.data.phone
				provinces.value =  res.data.province + res.data.city + res.data.district
				address.value = res.data.addressDetail
				
				latitude.value = res.data.lat
				longitude.value =res.data.lng
				province.value = res.data.province
				city.value = res.data.city
				district.value = res.data.district
				
			}
			uni.hideLoading()

		})
	};
	// 选择位置
	const openMap = () => {
		let that = this
		uni.chooseLocation({
			success(ret) {
				console.log(ret, '=======')
				let data = {
					lat: ret.latitude,
					lng: ret.longitude,
				}
				proxy.$http.getT('/app/map/getByLatAndLng', data).then(res => {
					if (res.code == 0) {
						latitude.value = ret.latitude
						longitude.value = ret.longitude
						address.value = ret.name
						province.value = res.data.province
						city.value = res.data.city
						district.value = res.data.district
						provinces.value = res.data.province + res.data.city + res.data.district
					} else {
						uni.showToast({
							title: res.msg,
							icon: 'none'
						})
					}
				})
			},
			fail() {
				uni.showToast({
					title: '拉起地图失败',
					icon: 'none'
				})
			}
		})
	};
	// 确认地址
	const submit = () => {
		if (!userName.value) {
			uni.showToast({
				title: '请输入收件人姓名',
				icon: 'none'
			})
			return
		}
		if (!phone.value) {
			uni.showToast({
				title: '请输入收件人手机号',
				icon: 'none'
			})
			return
		}
		if (!provinces.value) {
			uni.showToast({
				title: '请选择省市区',
				icon: 'none'
			})
			return
		}
		if (!address.value) {
			uni.showToast({
				title: '请填写详细地址',
				icon: 'none'
			})
			return
		}
		uni.hideLoading({
			title: '提交中...'
		})
		let data = {
			orderId: orderId.value,
			userId: userId.value,
			userName: userName.value,
			phone: phone.value,
			// provinces: provinces.value,
			address: address.value,
			province: province.value,
			city: city.value,
			district: district.value,
			lat: latitude.value,
			lng: longitude.value
		}
		proxy.$http.postJson('/app/order/use', data).then(res => {
			if (res.code == 0) {
				// getDetail()
				uni.navigateBack()
			}
			uni.hideLoading()

		})
	};
</script>

<style lang="scss">
	page {
		background: #F5F5F5;
	}

	.bgColor {
		background: linear-gradient(180deg, #FFE3ED 0%, #FFFFFF 100%);
		padding: 30rpx;
		height: 200rpx;
	}



	.box {
		background: #FFFFFF;
		border-radius: 24rpx;
		margin-top: 30rpx;
		padding: 150rpx 30rpx 30rpx;
		position: relative;
		top: -200rpx;

		.boxImage {
			width: 292rpx;
			height: 176rpx;
			border-radius: 24rpx;
			background: #FFFFFF;
			display: flex;
			align-items: center;
			justify-content: center;
			position: absolute;
			top: -60rpx;
			left: 0;
			right: 0;
			z-index: 9;
			margin: 0 auto;

			image {
				width: 282rpx;
				height: 166rpx;
				border-radius: 24rpx;
			}
		}

		.cardText {
			height: 180rpx;
			background: #FFFFFF;
			border-radius: 24rpx;
			border: 1rpx solid #CCCCCC;
			padding: 20rpx;
			font-weight: 400;
			font-size: 26rpx;
			color: #333333;
			line-height: 44rpx;
		}

		.order_title {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 32rpx;
			color: #333333;
			margin: 30rpx 0;
		}

		.addbox {
			background: #FFFFFF;
			border-radius: 24rpx;
			border: 1px solid #CCCCCC;
			padding: 10rpx 30rpx 30rpx;
			margin-top: 20rpx;

			.addboxItem {
				display: flex;
				align-items: center;
				border-bottom: 1rpx solid #F0F0F0;
				padding: 40rpx 0 30rpx;


				.addboxItem_tite {
					width: 150rpx;
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 28rpx;
					color: #333333;
				}

				.addboxItem_Input {
					flex: 1;
					display: flex;
					align-items: center;
					justify-content: space-between;

					.input-placeholder {
						font-weight: 400;
						font-size: 26rpx !important;
						color: #999999 !important;
					}
				}
			}
		}
	}

	.btn {
		margin: 0 30rpx;
		height: 88rpx;
		background: #F6317A;
		border-radius: 44rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		font-size: 26rpx;
		color: #FFFFFF;
		display: flex;
		align-items: center;
		justify-content: center;
	}
</style>