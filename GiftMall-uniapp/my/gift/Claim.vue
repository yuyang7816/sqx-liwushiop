<template>
	<view style="padding-bottom: 120rpx;">
		<view class="bgColorTop">
			<view class="state">
				<image src="/my/static/yzs.png" mode="widthFix"></image>
				<text>待领取...</text>
			</view>

			<view v-if="orderInfo.productList">
				<view class="boxInfo" v-for="(item,index) in orderInfo.productList" :key="index">
					<image :src="item.giftProduct?item.giftProduct.coverImg:'/static/logo.png'" mode="aspectFill">
					</image>
					<view class="boxInfo_Cont">
						<view class="boxInfo_ContTitle">{{item.giftProduct.name}}</view>
						<view class="boxInfo_ContPrice"><text>¥</text>{{orderInfo.payMoney}}</view>
					</view>
				</view>
			</view>
		</view>
		<view class="boxInfoUser" v-if="orderInfo.status==13||orderInfo.status==16">
			<view class="boxInfoUser_Item ">
				<view class="boxInfoUser_ItemTitle " v-if="orderInfo.giveUserName">领取人: {{orderInfo.giveUserName}}
				</view>
				<view class="boxInfoUser_ItemTitle_price"><text>¥</text>{{orderInfo.payMoney}}</view>
				
			</view>
		
		</view>
		<view class="cardbox" v-if="orderInfo.cardImg">
			<view class="cardbox_Item padding-bottom-xl">
				<view class="cardbox_Item_Title">所选卡面</view>
				<view class="cardbox_Item_Tit flex justify-end">
					<image :src="orderInfo.cardImg?orderInfo.cardImg:'/static/logo.png'" mode="aspectFill"></image>
				</view>
			</view>
			<view class="cardbox_Item" v-if="blessing">
				<view class="cardbox_Item_Title">祝福</view>
				<view class="cardbox_Item_Tit">{{blessing}}</view>
			</view>
		</view>

		<!-- <view class="orderbox">
			<view class="orderbox_title">订单信息</view>
			<view class="orderbox_Item">
				<view class="orderbox_ItemTitle">订单编号</view>
				<view class="orderbox_ItemTit">{{orderInfo.orderNo}}
					<text @click="copyClick(orderInfo.orderNo)">复制</text>
				</view>
			</view>
			<view class="orderbox_Item">
				<view class="orderbox_ItemTitle">下单时间</view>
				<view class="orderbox_ItemTit">{{orderInfo.createTime}}</view>
			</view>
		</view> -->

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

		<view class="taber">
			<view class="linebtn " @click="goIndex()">返回主页</view>
			<view class="shibtn" @click="bindClaim()">立即领取</view>

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
	const orderId = ref('');
	const orderInfo = ref({});
	const shareId = ref('');


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
	
	const blessing = ref('')
	



	onLoad(option => {
		console.log(option,'optionoptionoption')
		// #ifdef MP-WEIXIN
		if (option.scene) {
			const scene = decodeURIComponent(option.scene);
			console.log(scene,'scenescene')
			uni.setStorageSync('inviterCode', scene.split(',')[0]);
			uni.setStorageSync('shareId', scene.split(',')[1]);
			blessing.value= scene.split(',')[3]
		}
		// #endif

		// 获取邀请码保存到本地
		if (option.inviterCode) {
			uni.setStorageSync('inviterCode', option.inviterCode);
		}
		if (option.shareId) {
			uni.setStorageSync('shareId', option.shareId);
		}
		if (option.valueWord) {
			blessing.value= option.valueWord
		}

		if (option.orderId) {
			orderId.value = option.orderId
			uni.showLoading({
				title: '加载中...'
			})
		}


	});
	onShow(() => {

		if (orderId.value) {
			getDetail()
		}
	});
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
	// 领取礼物
	const ClaimGift = () => {
		proxy.$http.postJson('/app/order/obtain', {
			userId: uni.getStorageSync('userId'), //接收人id
			shareId: uni.getStorageSync('shareId'), //分享id
		}).then(res => {
			if (res.code == 0) {
				uni.removeStorageSync('shareId')
			}
		})
	};
	// 领取礼物
	const bindClaim = () => {
		if (uni.getStorageSync('token')) {

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

			proxy.$http.postJson('/app/order/obtain', {
				userId: uni.getStorageSync('userId'), //接收人id
				shareId: uni.getStorageSync('shareId'), //分享id

				consignee: userName.value,
				phone: phone.value,
				addressDetail: address.value,
				province: province.value,
				city: city.value,
				district: district.value,
				blessing:blessing.value,
				orderNo:orderInfo.value.orderNo
			}).then(res => {
				if (res.code == 0) {
					uni.removeStorageSync('shareId')
					uni.showToast({
						title: '领取成功',
						icon: 'none'
					})
					setTimeout(() => {
						uni.setStorageSync('orderIndex', 2)
						uni.switchTab({
							url: '/pages/order/index'
						})
					}, 600)

				} else {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			})
		} else {
			uni.navigateTo({
				url: '/pages/public/login?orderId=' + orderId.value
			})
		}
	};
	// 返回首页
	const goIndex = () => {
		uni.switchTab({
			url: '/pages/index/index'
		})
	};

	// 获取订单详情
	const getDetail = () => {
		proxy.$http.getT('/app/order/info?orderId=' + orderId.value).then(res => {
			if (res.code == 0) {
				orderInfo.value = res.data
			}
			uni.hideLoading()

		})
	};
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
</script>

<style lang="scss">
	page {
		background: #F5F5F5;
	}

	.bgColorTop {
		width: 100%;
		// height: 260rpx;
		background: linear-gradient(180deg, #FFE3ED 50%, #F5F5F5 80%);
	}

	.state {
		display: flex;
		align-items: center;
		padding: 0 30rpx 20rpx;

		image {
			width: 44rpx;
			height: 44rpx;
		}

		text {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 32rpx;
			color: #333333;
			margin-left: 6rpx;
		}
	}

	.boxInfo {
		background: #FFFFFF;
		border-radius: 24rpx;
		margin: 20rpx 30rpx;
		padding: 30rpx;
		display: flex;

		image {
			width: 200rpx;
			height: 200rpx;
			border-radius: 12rpx;
		}

		.boxInfo_Cont {
			flex: 1;
			margin-left: 15rpx;
			display: flex;
			justify-content: space-between;
			flex-direction: column;

			.boxInfo_ContTitle {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
			}

			.boxInfo_ContPrice {
				font-family: PingFang SC;
				font-weight: bold;
				font-size: 48rpx;
				color: #333333;

				text {
					font-size: 22rpx;
				}
			}
		}
	}

	.boxInfoUser {
		background: #FFFFFF;
		border-radius: 24rpx;
		margin: 0rpx 30rpx 20rpx;
		padding: 30rpx;

		.boxInfoUser_Item {
			display: flex;
			align-items: center;
			justify-content: space-between;


			.boxInfoUser_ItemTitle {
				font-weight: 500;
				font-size: 28rpx;
				color: #333333;

				.boxInfoUser_ItemTitle_price {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 32rpx;
					color: #333333;
					margin-left: 10rpx;

					text {
						font-size: 22rpx;
					}
				}
			}

			.boxInfoUser_ItemTit {
				font-weight: 400;
				font-size: 26rpx;
				color: #999999;

				text {
					color: #F6317A;
				}
			}
		}
	}

	.cardbox {
		background: #FFFFFF;
		border-radius: 24rpx;
		margin: 20rpx 30rpx;
		padding: 30rpx;

		.cardbox_Item {
			display: flex;
			justify-content: space-between;

			.cardbox_Item_Title {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 26rpx;
				color: #999999;
			}

			.cardbox_Item_Tit {
				width: 460rpx;

				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 26rpx;
				color: #333333;

				image {
					width: 333rpx;
					height: 200rpx;
					border-radius: 24rpx;

				}
			}
		}
	}

	.orderbox {
		background: #FFFFFF;
		border-radius: 24rpx;
		margin: 20rpx 30rpx;
		padding: 30rpx;

		.orderbox_title {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 32rpx;
			color: #333333;
		}

		.orderbox_Item {
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding-top: 30rpx;

			.orderbox_ItemTitle {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 28rpx;
				color: #999999;

			}

			.orderbox_ItemTit {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 26rpx;
				color: #333333;
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

	.taber {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		z-index:999;
		padding: 20rpx 30rpx;
		display: flex;
		align-items: center;
		justify-content: flex-end;
		background: #FFFFFF;

		.linebtn {
			flex: 1;
			height: 70rpx;
			border-radius: 35rpx;
			border: 1px solid #CCCCCC;
			font-family: PingFang SC;
			font-weight: 500;
			font-size: 28rpx;
			color: #999999;
			display: flex;
			align-items: center;
			justify-content: center;
			margin-right: 10rpx;
		}

		.shibtn {
			flex: 1;
			height: 70rpx;
			background: #F6317A;
			border-radius: 35rpx;
			font-family: PingFang SC;
			font-weight: 500;
			font-size: 28rpx;
			color: #FFFFFF;
			display: flex;
			align-items: center;
			justify-content: center;
			margin-left: 10rpx;
		}
	}

	.addbox {
		background: #FFFFFF;
		border-radius: 24rpx;
		border: 1px solid #CCCCCC;
		padding: 10rpx 30rpx 30rpx;
		margin: 20rpx 30rpx;

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
</style>