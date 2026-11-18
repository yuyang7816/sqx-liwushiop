<template>
	<view>
		<view class=" bgColor">
			<view class="userInfo">
				<image :src="avatar?avatar:'/static/logo.png'" @click="goNav('/pages/my/userInfo')"></image>
				<view class="userInfo_box" v-if="isLogin">
					<view class="userInfo_boxinfo">
						<view class="userInfo_boxinfo_title">{{userName}}</view>
						<view class="userInfo_boxinfo_id">ID：{{invitationCode}}</view>
					</view>
				</view>
				<view class="userInfo_login" v-else @click="goLogin">登录</view>
			</view>


			<view class="userItem">
				<view class="userItem_box" @click="goNav('/my/gird/shoucang')">
					<view class="userItem_box_num">{{collectNum?collectNum:'0'}}</view>
					<view class="userItem_box_tit">我的收藏</view>
				</view>
				<view class="userItem_box" @click="goOrder">
					<view class="userItem_box_num">{{receiveGiftNum?receiveGiftNum:'0'}}</view>
					<view class="userItem_box_tit">我收到的礼物</view>
				</view>
				<view class="userItem_box" @click="goNav('/my/Commodity/shoppingCart')">
					<view class="userItem_box_num">{{cartNum?cartNum:'0'}}</view>
					<view class="userItem_box_tit">我的购物车</view>
				</view>
			</view>


		</view>


		<view class="setbox">
			<view class="setbox_tit">常用服务</view>
			<view class="setbox_Item" @click="goNav('/my/invoice/index')">
				<view class="setbox_ItemBox">
					<image src="/static/image/my/fp.png"></image>
					<text class="">开具发票</text>
				</view>
				<image src="/static/image/my/huiright.png"></image>
			</view>
			<view class="setbox_Item" @click="goNav('/pages/my/invitationUser')">
				<view class="setbox_ItemBox">
					<image src="/static/image/my/yao.png"></image>
					<text class="">邀请好友</text>
				</view>
				<image src="/static/image/my/huiright.png"></image>
			</view>
			<view class="setbox_Item" @click="goNav('/my/wallet/index')">
				<view class="setbox_ItemBox">
					<image src="/static/image/my/qianbao.png"></image>
					<text class="">我的钱包</text>
				</view>
				<image src="/static/image/my/huiright.png"></image>
			</view>
			<view class="setbox_Item" @click="goNav('/my/team/index')">
				<view class="setbox_ItemBox">
					<image src="/static/image/my/team.png"></image>
					<text class="">团队分销</text>
				</view>
				<image src="/static/image/my/huiright.png"></image>
			</view>
			<view class="setbox_Item" @click="goChat">
				<view class="setbox_ItemBox">
					<image src="/static/image/my/kefu.png"></image>
					<text class="">联系客服</text>
				</view>
				<image src="/static/image/my/huiright.png"></image>
			</view>
			<view class="setbox_Item" @click="goNav('/my/setting/feedback')">
				<view class="setbox_ItemBox">
					<image src="/static/image/my/fk.png"></image>
					<text class="">问题反馈</text>
				</view>
				<image src="/static/image/my/huiright.png"></image>
			</view>

			<view class="setbox_Item" @click="goNav('/my/setting/index')">
				<view class="setbox_ItemBox">
					<image src="/static/image/my/set.png"></image>
					<text class="">设置中心</text>
				</view>
				<image src="/static/image/my/huiright.png"></image>
			</view>

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
	// 用户 userId
	const userId = ref('');
	// 用户 token
	const token = ref('');
	// 用户信息
	const invitationCode = ref(''); //邀请码
	const userName = ref(''); //用户昵称
	const avatar = ref(''); //用户头像
	const isLogin = ref(false); //是否登录
	const cartNum = ref(0); //购物车数量
	const collectNum = ref(0); //我的收藏
	const receiveGiftNum = ref(0); //我收到的礼物
	

	onLoad(option => {

	});
	onShow(() => {
		userId.value = uni.getStorageSync('userId');
		if (userId.value) {
			isLogin.value = true
			invitationCode.value = uni.getStorageSync('invitationCode');
			userName.value = uni.getStorageSync('userName');
			getUserInfo();
			getMyData();
			
		} else {
			isLogin.value = false
			userId.value = '';
			userName.value = '';
			avatar.value = '';
			invitationCode.value = '';
			cartNum.value = 0
			collectNum.value = 0
			receiveGiftNum.value = 0
		}

	});
	// 获取顶部统计数据
	const getMyData = () => {
		let data = {
			userId: userId.value
		}
		proxy.$http.getT("/app/index/myData", data).then(res => {
			if (res.code == 0) {
				cartNum.value = res.data.cartNum
				collectNum.value = res.data.collectNum
				receiveGiftNum.value = res.data.receiveGiftNum
			}
		});
	};
	// 跳转礼物
	const goOrder = () => {
		if (uni.getStorageSync('token')) {
			uni.setStorageSync('orderIndex', 2)
			uni.switchTab({
				url: '/pages/order/index'
			})
		} else {
			uni.showModal({
				title: '提示',
				content: '您还未登录,请先登录',
				success: function(res) {
					if (res.confirm) {
						console.log('用户点击确定');
						uni.navigateTo({
							url: '/pages/public/login'
						})
					} else if (res.cancel) {
						console.log('用户点击取消');
					}
				}
			})
		}
	};
	//页面跳转
	const goNav = (url) => {
		if (uni.getStorageSync('token')) {
			uni.navigateTo({
				url
			})
		} else {
			uni.showModal({
				title: '提示',
				content: '您还未登录,请先登录',
				success: function(res) {
					if (res.confirm) {
						console.log('用户点击确定');
						uni.navigateTo({
							url: '/pages/public/login'
						})
					} else if (res.cancel) {
						console.log('用户点击取消');
					}
				}
			})
		}
	};
	// 跳转登录
	const goLogin = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
	};
	// 获取用户信息
	const getUserInfo = () => {
		let data = {
			userId: userId.value
		}
		proxy.$http.getT("/app/user/info", data).then(res => {
			if (res.code == 0) {
				invitationCode.value = res.data.invitationCode
				userName.value = res.data.userName ? res.data.userName : res.data.nickName
				avatar.value = res.data.avatar
				// money.value = res.data.money ? res.data.money : 0 //余额
				// couponCount.value = res.data.couponCount ? res.data.couponCount : 0 //优惠券
				// creditScore.value = res.data.score ? res.data.score : 0 //信用分
				uni.setStorageSync('avatar', avatar.value)
				uni.setStorageSync('userName', userName.value)
				uni.setStorageSync('invitationCode', invitationCode.value)
				uni.setStorageSync('commissionRate',res.data.commissionRate)
			}
		});
	};
	// 联系客服
	const goChat = () => {
		let that = this
		let SelKeFu = uni.getStorageSync('SelKeFu')
		if (Number(SelKeFu) == 2) {
			// #ifdef MP-WEIXIN
			try {
				wx.openCustomerServiceChat({
					extInfo: {
						url: uni.getStorageSync('SelKeFuLink')
					},
					corpId: uni.getStorageSync('SelKeFuAppId'),
					success(res) {},
					fail(res) {
						console.error(res)
					}
				})
			} catch (error) {
				console.error("catchcatch" + error)
				uni.showToast({
					title: '请更新至微信最新版本'
				});
			}
			// #endif
			// #ifndef MP-WEIXIN
			let url = uni.getStorageSync('SelKeFuLink');

			if (url.indexOf('/pages/') !== -1 || url.indexOf('/my/') !== -1) {
				uni.navigateTo({
					url
				});
			} else {
				//#ifndef H5
				uni.navigateTo({
					url: '/pages/index/webView?url=' + url
				});
				//#endif
				//#ifdef H5
				window.location.href = url;
				//#endif
			}
			// #endif
		} else if (Number(SelKeFu) == 1) {
			uni.makePhoneCall({
				phoneNumber: uni.getStorageSync('kefuPhone')//仅为示例
			});
		} else if (Number(SelKeFu) == 3) {
			let token = uni.getStorageSync('token');
			if (token) {
				uni.navigateTo({
					url: '/my/setting/customer'
				});
			} else {
				goLogin();
			}
		}
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #F5F5F5;
	}

	.bgColor {
		background: linear-gradient(0deg, #F5F5F5 0%, #FFE3ED 40%);
		height: 300rpx;
	}

	.userInfo {
		display: flex;
		align-items: center;

		padding: 0 30rpx;

		image {
			width: 96rpx;
			height: 96rpx;
			border-radius: 55%;
		}

		.userInfo_login {
			font-weight: bold;
			font-size: 34rpx;
			color: #333333;
			font-size: 34rpx;
			margin-left: 15rpx;
		}

		.userInfo_box {
			flex: 1;
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-left: 15rpx;


			.userInfo_boxinfo {
				.userInfo_boxinfo_title {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 32rpx;
					color: #000000;
				}

				.userInfo_boxinfo_id {
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 24rpx;
					color: #999999;
				}
			}

		}
	}

	.userItem {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 30rpx 50rpx 30rpx;


		.userItem_box {
			text-align: center;
			margin-top: 10rpx;

			.userItem_box_num {
				font-family: DINPro;
				font-weight: 500;
				font-size: 36rpx;
				color: #333333;
			}

			.userItem_box_tit {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 26rpx;
				color: #666666;
			}
		}
	}

	.setbox {
		background: #FFFFFF;
		border-radius: 24rpx 24rpx 0 0;
		// margin: 20rpx 30rpx;
		padding: 0 30rpx 10rpx;
		position: relative;
		top: -60rpx;

		.setbox_tit {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 32rpx;
			color: #333333;
			padding-top: 30rpx;
		}

		.setbox_Item {
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 25rpx 0;

			.setbox_ItemBox {
				display: flex;
				align-items: center;

				image {
					width: 40rpx;
					height: 40rpx;
				}

				text {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 26rpx;
					color: #333333;
					margin-left: 6rpx;
				}
			}

			image {
				width: 30rpx;
				height: 30rpx;
			}
		}
	}
</style>