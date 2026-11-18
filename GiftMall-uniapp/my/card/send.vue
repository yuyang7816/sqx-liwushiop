<template>
	<view>
		<view class="cardImage">
			<image :src="orderInfo.cardImg" mode="aspectFill">
			</image>
		</view>
		<view class="cardText">
			<up-textarea v-model="valueWord" placeholder="请输入内容" border="none" height="100"></up-textarea>
			<view class="huanh">
				<image src="/my/static/huanyig.png"></image>
				<text @click="getBlessingWords()">换一个</text>
			</view>
		</view>
		<view class="Tiptext" v-if="hour">温馨提示:分享链接有效时间{{hour}}小时</view>

		<!-- #ifdef MP-WEIXIN -->
		<button open-type="share">
			<view class="btn">送给TA</view>
		</button>
		<!-- #endif -->
		<!-- #ifndef MP-WEIXIN -->
		<view class="btn" @click="shareH5 ">送给TA</view>
		<!-- #endif -->
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
		onShareAppMessage,
		onShareTimeline
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	const valueWord = ref('山河远阔，人间星河，无一是你，无一不是你。愿你所愿皆所求，所求皆所得，所得皆所想。');
	const id = ref('');
	const orderInfo = ref({});
	const url = ref('');
	const invitationCode = ref('');
	const shareH5Url = ref('');
	const hour = ref('');
	const shareId = ref('');


	/* 分享给好友 */
	onShareAppMessage(() => {
		return {
			title: valueWord.value, // 分享卡的标题
			path: '/my/gift/Claim?inviterCode=' + invitationCode.value + '&shareId=' + shareId.value +
				'&orderId=' + id.value+ '&valueWord=' + valueWord.value, // 用户点击后打开的页面及参数
			imageUrl: orderInfo.value.cardImg, // 分享卡的图片，可选
			success(ree) {
				console.log(ree)
			},
			finally(err) {
				console.log(err)
			}
		}
	});

	/* 分享到朋友圈 */
	onShareTimeline(() => {
		return {
			title: valueWord.value,
			path: '/my/gift/Claim?inviterCode=' + invitationCode.value + '&shareId=' + shareId.value +
				'&orderId=' + id.value + '&valueWord=' + valueWord.value,
			imageUrl: orderInfo.value.cardImg
		}
	});
	onLoad(option => {
		invitationCode.value = uni.getStorageSync('invitationCode');
		// 分享链接有效期（单位：小时）	324
		proxy.$http.getT('/app/common/type/324').then(res => {
			if (res.code === 0) {
				if (res.data && res.data.value) {
					hour.value = res.data.value;
				}
			}
		});
		// #ifndef H5
		proxy.$http.getT('/app/common/type/25').then(res => {
			if (res.code === 0) {
				if (res.data && res.data.value) {
					url.value = res.data.value;
				}
			}
		});
		//#endif
		// #ifdef H5
		proxy.$http.getT('/app/common/type/21').then(ress => {
			if (ress.code === 0) {
				if (ress.data && ress.data.value) {
					url.value = ress.data.value
					shareH5Url.value = url.value + '/my/gift/Claim?inviterCode=' + invitationCode.value +
						'&shareId=' + shareId.value + '&orderId=' + id.value+ '&valueWord=' + valueWord.value
				} else {
					url.value = proxy.$publicYuMing();
					shareH5Url.value = url.value + '//my/gift/Claim?inviterCode=' + invitationCode.value +
						'&shareId=' + shareId.value + '&orderId=' + id.value+ '&valueWord=' + valueWord.value
				}
			}
		});
		//#endif
		// gift/Claim
		getBlessingWords(); //随机获取祝福语
		if (option.orderId) {
			id.value = option.orderId
			uni.showLoading({
				title: '加载中...'
			})
			getDetail()
		}
	});
	onShow(() => {

	});
	// h5链接
	const shareH5 = () => {
		let relationId = invitationCode.value;
		uni.showModal({
			title: '文案推广',
			content: valueWord.value + relationId + '\n' + url.value + '/my/gift/Claim?inviterCode=' +
				relationId + '&shareId=' + shareId.value + '&orderId=' + id.value+ '&valueWord=' + valueWord.value,
			showCancel: true,
			cancelText: '关闭',
			confirmText: '一键复制',
			confirmColor: '#F6317A',
			success: res => {
				if (res.confirm) {
					uni.setClipboardData({
						data: valueWord.value + relationId + '\n' + url.value +
							'/my/gift/Claim/?inviterCode=' +
							relationId + '&shareId=' + shareId.value + '&orderId=' + id.value+ '&valueWord=' + valueWord.value,
						success: function() {
							console.log('success');
							uni.showToast({
								title: '复制成功',
								icon: 'none'
							})
						}
					});
				}
			}
		});
	};

	const getShareId = () => {
		let data = {
			userId: uni.getStorageSync('userId'), //用户id
			orderNo: orderInfo.value.orderNo, //订单编号
			blessing: valueWord.value //祝福语
		}
		proxy.$http.postJson('/app/order/share', data).then(res => {
			if (res.code == 0) {
				shareId.value = res.data.id
			}
			uni.hideLoading()

		})
	};
	// 获取详情
	const getDetail = () => {
		proxy.$http.getT('/app/order/info?orderId=' + id.value).then(res => {
			if (res.code == 0) {
				orderInfo.value = res.data
				getShareId()
			}
			uni.hideLoading()

		})
	};
	// 获取祝福语
	const getBlessingWords = () => {
		proxy.$http.getT('/app/blessing/random').then(res => {
			if (res.code == 0) {
				valueWord.value = res.data.content
			}
			uni.hideLoading()

		})
	}
</script>

<style lang="scss">
	page {
		background: #FFFFFF;
	}

	.cardImage {
		margin: 30rpx 40rpx;
		height: 400rpx;
		border-radius: 24rpx;

		image {
			width: 666rpx;
			height: 400rpx;
			border-radius: 24rpx;
		}
	}

	.cardText {
		margin: 0 40rpx;
		background: #FFFFFF;
		border-radius: 24rpx;
		border: 1px solid #CCCCCC;
		padding: 20rpx 15rpx;

		.huanh {
			display: flex;
			align-items: center;
			justify-content: flex-end;

			image {
				width: 35rpx;
				height: 35rpx;
			}

			text {
				font-weight: 400;
				font-size: 26rpx;
				color: #333333;
				margin-left: 10rpx;
			}
		}
	}

	::v-deep .u-textarea {
		padding: 0 !important;
	}

	.uni-textarea-textarea {
		font-weight: 400;
		font-size: 26rpx;
		color: #333333;
		line-height: 44rpx;
	}

	.btn {
		margin: 40rpx 30rpx;
		height: 80rpx;
		background: #F6317A;
		border-radius: 40rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		font-size: 28rpx;
		color: #FFFFFF;
		display: flex;
		align-items: center;
		justify-content: center;
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 9;


	}

	.Tiptext {
		font-size: 26rpx;
		margin: 20rpx 30rpx;
		letter-spacing: 2rpx;
	}
</style>