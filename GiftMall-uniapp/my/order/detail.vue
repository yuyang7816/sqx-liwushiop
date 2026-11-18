<template>
	<view style="padding-bottom: 120rpx;">
		<view class="bgColorTop">
			<view class="state">
				<image src="/my/static/yzs.png" mode="widthFix"></image>
				<text v-if="orderInfo.status==1">待付款...</text>
				<text v-if="orderInfo.status==2">已付款...</text>
				<text v-if="orderInfo.status==3">待收货...</text>
				<text v-if="orderInfo.status==11">待赠送...</text>
				<text v-if="orderInfo.status==12">已赠送...</text>
				<text v-if="orderInfo.status==14">已拒绝...</text>
				<text v-if="orderInfo.status==13">已领取...</text>
				<text v-if="orderInfo.status==16">待发货...</text>
				<text v-if="orderInfo.status==4">已完成...</text>
				<text v-if="orderInfo.status==5">已取消...</text>
				<text v-if="orderInfo.status==7">已退款...</text>

			</view>
			<view class="addbox" v-if="orderInfo.addressDetail">
				<view class="addbox_title">收货人信息</view>
				<view class="addbox_bot">
					<view class="margin-right-xs">收货地址:</view>
					<view class="addbox_cot">
						<view class="addbox_cotTitle">
							{{orderInfo.provinces}}{{orderInfo.city}}{{orderInfo.district}}{{orderInfo.addressDetail}}
						</view>
						<view>{{orderInfo.consignee}} <text>{{orderInfo.phone}}</text></view>
					</view>
					<!-- <view class="addbox_updete" @click="goUpdeteAdd(orderInfo)">修改</view> -->
				</view>
			</view>
			<view v-if="orderInfo.productList">
				<view class="boxInfo" v-for="(item,index) in orderInfo.productList" :key="index">
					<image :src="item.giftProduct?item.giftProduct.coverImg:'/static/logo.png'" mode="aspectFill">
					</image>
					<view class="boxInfo_Cont">
						<view class="boxInfo_ContTitle">{{item.giftProduct.name}}</view>
						<view class="boxInfo_ContSku">{{item.skuDetailJson}} <text>
								x{{item.num}}</text></view>
						<view class="boxInfo_ContPrice"><text>¥</text>{{Number(item.price)*Number(item.num)}}
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="boxInfoUser" v-if="orderInfo.giveUserName">
			<view class="boxInfoUser_Item ">
				<view class="boxInfoUser_ItemTitle " v-if="orderInfo.giveUserName">领取人: {{orderInfo.giveUserName}}
				</view>
				<view class="boxInfoUser_ItemTitle_price"><text>¥</text>{{orderInfo.payMoney}}</view>
				<!-- <view class="boxInfoUser_ItemTit">已领取 </view> -->
			</view>

		</view>

		<view class="cardbox" v-if="orderInfo.cardImg">
			<view class="cardbox_Item padding-bottom-xl">
				<view class="cardbox_Item_Title">所选卡面</view>
				<view class="cardbox_Item_Tit flex justify-end">
					<image :src="orderInfo.cardImg?orderInfo.cardImg:'/static/logo.png'" mode="aspectFill"></image>
				</view>
			</view>
			<view class="cardbox_Item" v-if="orderInfo.blessing">
				<view class="cardbox_Item_Title">祝福</view>
				<view class="cardbox_Item_Tit">{{orderInfo.blessing}}</view>
			</view>
		</view>

		<view class="orderbox">
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
			<view class="orderbox_Item" v-if="orderInfo.expressName">
				<view class="orderbox_ItemTitle">物流名称</view>
				<view class="orderbox_ItemTit">{{orderInfo.expressName}}</view>
			</view>
			<view class="orderbox_Item" v-if="orderInfo.expressNumber">
				<view class="orderbox_ItemTitle">物流单号</view>
				<view class="orderbox_ItemTit">{{orderInfo.expressNumber}}</view>
			</view>

			<view class="margin-top-sm">
				<up-steps current="0" direction="column">
					<up-steps-item :title="item.status" :desc="item.time" v-for="(item,index) in expressList"
						:key="index">
					</up-steps-item>
				</up-steps>
			</view>
		</view>

		<view class="taber">
			<view class="shibtn" v-if="orderInfo.status==11" @click.stop="userGift()">送给好友</view>
			<view class="shibtn margin-left" v-if="orderInfo.status==1" @click="payShow = true">立即付款</view>
			<view class="shibtn" v-if="orderInfo.status==3" @click="bindExpree">查看物流</view>
			<!-- <view class="linebtn">联系客服</view>
			<view class="linebtn margin-left">取消订单</view>
			 -->
		</view>

		<!-- 支付弹框 -->
		<up-popup :show="payShow" @close="payShow = false" :round="12" :closeable="true">
			<view class="popup_pay">
				<view class="popup_payTit">支付方式</view>
				<view class="popup_payType" v-for="(item,index) in openList" :key='index'>
					<view class="popup_payType_left">
						<image :src="item.image" mode="aspectFill"></image>
						<text>{{item.text}}</text>
					</view>
					<radio-group name="openWay">
						<label class="tui-radio">
							<radio color="#F6317A" :checked="openWay == item.id ? true : false"
								@click='selectWayPay(item)' />
						</label>
					</radio-group>
				</view>
				<view class="pay_btn" @click="pay()">确认支付</view>
			</view>
		</up-popup>


		<up-popup :show="showCard" @close="showCard = false" :round="12" :closeable="true">
			<view class="popupboxCard">
				<view class="CardTitle">选择卡面</view>
				<view class="Cardbox">
					<up-tabs scrollable :list="cardType" @click="clickCardType" lineColor="transparent"
						:activeStyle="activeStyle" :inactiveStyle="inactiveStyle" keyName="cardCategoryName"></up-tabs>
				</view>
				<view class="CardImage">
					<scroll-view scroll-y="true" class="scroll-Y" :refresher-enabled="true"
						refresher-background="#FFFFFF" :refresher-triggered="refresherTriggered"
						@refresherrefresh="upper" @scrolltolower="lower">
						<view class="flex justify-between flex-wrap">
							<view v-for="(item,index) in cardlist" :key="index" @click="clickCard(item,index)"
								class="scroll-view-item">
								<image :src="item.cardImg" mode="aspectFill"></image>
								<view class="CardImage_check" v-if="index === cardIndex">
									<radio color="#F6317A" :checked="index === cardIndex ? true : false"
										style="transform:scale(0.8)" />
								</view>
							</view>
						</view>
						<!-- 加载数据 -->
						<up-loadmore :status="status" v-if="cardlist.length>0" />
						<!-- 暂无数据 -->
						<empty v-if="cardlist.length==0"></empty>
					</scroll-view>
				</view>
				<view class="Cardbtn">
					<view class="tabbtn" @click="clickOrder">确认</view>
				</view>
			</view>
		</up-popup>
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
	const payShow = ref(false);
	const openList = reactive([]); //支付类型
	const openWay = ref('');

	const showCard = ref(false);
	const inactiveStyle = ref({
		'height': '64rpx',
		'background': '#FFFFFF',
		'borderRadius': '32rpx',
		'border': '1px solid #E6E6E6',
		'padding': '0 25rpx',
		'fontWeight': '400',
		'fontSize': '26rpx',
		'color': '#666666',
		'display': ' inline-flex',
		'align-items': 'center',
		'justify-content': 'center',
	});
	const activeStyle = ref({
		'height': '64rpx',
		'padding': '0 25rpx',
		'borderRadius': '32rpx',
		'background': '#FFE9F1',
		'border': '1px solid #FFE9F1',
		'color': '#F6317A',
		'display': ' inline-flex',
		'align-items': 'center',
		'justify-content': 'center',
	});
	const refresherTriggered = ref(false); //设置当前下拉刷新状态，true 表示下拉刷新已经被触发，false 表示下拉刷新未被触发
	const cardType = reactive([{
		cardCategoryName: '全部图案',
		cardCategoryId: ''
	}]); //卡片分类
	const cardCategoryId = ref('');
	const cardlist = reactive([]);
	const cardIndex = ref(-1);
	const cardId = ref('');
	const cardImg = ref('');
	const pageCard = ref(1);
	const pagesCard = ref(1);
	const limitCard = ref(10);
	const shopCardIndex = ref('');
	const status = ref('loadmore'); //加载状态

	const expressList = reactive([]);

	onLoad(option => {
		if (option.orderId) {
			orderId.value = option.orderId
			uni.showLoading({
				title: '加载中...'
			})
		}
		getCardType(); //获取卡片分类
		getCardlist(); //获取卡片列表

		let payType = []

		// #ifdef APP-PLUS
		payType = [{
			image: '../../static/image/zhifubao.png',
			text: '支付宝支付',
			id: 1
		}, {
			image: '../../static/image/icon_weixin.png',
			text: '微信支付',
			id: 2
		}, {
			image: '../../static/image/lingian.png',
			text: '余额支付',
			id: 3
		}];
		openWay.value = 1;
		// #endif

		// #ifdef MP-WEIXIN
		payType = [{
			image: '../../static/image/icon_weixin.png',
			text: '微信支付',
			id: 2
		}, {
			image: '../../static/image/lingian.png',
			text: '余额支付',
			id: 3
		}];
		openWay.value = 2;
		// #endif

		// #ifdef H5
		let target = navigator.userAgent.toLowerCase();
		let isWeixin = (/micromessenger/.test(target)) ? true : false;
		if (isWeixin) {
			payType = [{
				image: '../../static/image/icon_weixin.png',
				text: '微信支付',
				id: 2
			}, {
				image: '../../static/image/lingian.png',
				text: '余额支付',
				id: 3
			}];
			openWay.value = 2;
		} else {
			payType = [{
				image: '../../static/image/zhifubao.png',
				text: '支付宝支付',
				id: 1
			}, {
				image: '../../static/image/lingian.png',
				text: '余额支付',
				id: 3
			}];
			openWay.value = 1;
		}
		// #endif
		openList.splice(0, openList.length, ...payType)
	});
	onShow(() => {
		if (orderId.value) {
			getDetail()
		}
	});
	
	// 查看物流
	const bindExpree = () => {
		let data = {
			expressNumber: orderInfo.value.expressNumber,
		}
		proxy.$http.getT('/app/order/getLogistics', data).then(res => {
			if (res.code == 0) {
				expressList.splice(1, expressList.length, ...res.data.result.list)
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none',
					duration:2000
				})
			}
		})
	};
	// 选择卡片
	const clickOrder = () => {
		// console.log('修改',cardId.value,cardImg.value)
		if (!cardId.value) {
			uni.showToast({
				title: '请选择卡面',
				icon: 'none'
			})
			return
		}
		let data = {
			userId: uni.getStorageSync('userId'),
			orderId: orderId.value,
			cardId: cardId.value,
			cardImg: cardImg.value
		}
		proxy.$http.getT('/app/order/bingCard', data).then(res => {
			if (res.code == 0) {
				uni.showToast({
					title: '绑定成功',
					icon: 'none'
				})
				uni.navigateTo({
					url: '/my/card/send?orderId=' + orderId.value
				})
			} else {
				uni.showToast({
					title: res.mgs,
					icon: 'none'
				})
			}
		})

	};
	// 卡片分类切换
	const clickCardType = (e) => {
		console.log(e)
		cardCategoryId.value = e.cardCategoryId
		pageCard.value = 1;
		getCardlist();
	};
	// 获取卡片分类
	const getCardType = () => {
		proxy.$http.getT('/app/cardCategory/listCardCategory').then(res => {
			if (res.code == 0) {
				cardType.splice(1, cardType.length, ...res.data.records)
			}
		})
	};

	// 选择卡片
	const clickCard = (item, index) => {
		console.log(item, index)
		cardIndex.value = index
		cardImg.value = item.cardImg
		cardId.value = item.cardId
	};
	// 获取卡片列表
	const getCardlist = () => {
		let data = {
			page: pageCard.value,
			limit: limitCard.value,
			cardCategoryId: cardCategoryId.value
		}

		proxy.$http.getT('/app/card/listCard', data).then(res => {
			if (res.code == 0) {
				if (res.code == 0) {
					pagesCard.value = res.data.pages
					if (pageCard.value < pagesCard.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}
					if (pageCard.value === 1) {
						cardlist.splice(0, cardlist.length, ...res.data.records);
					} else {
						cardlist.push(...res.data.records);
					}
					status.value = 'nomore';
				}
				status.value = 'nomore';
				uni.stopPullDownRefresh();
			}
		})
	};
	// 触发自定义底部加载
	const lower = () => {
		console.log('触发自定义底部加载')
		if (pageCard.value < pagesCard.value) {
			pageCard.value += 1
			status.value = 'loading';
			getCardlist();
		} else {
			uni.showToast({
				title: '已经到底了~',
				icon: 'none'
			})
		}
	};
	// 触发自定义下拉刷新
	const upper = () => {
		console.log('触发自定义下拉刷新')
		refresherTriggered.value = true
		status.value = 'loading';
		pageCard.value = 1
		getCardlist()

	};
	// 修改地址信息
	const goUpdeteAdd = (e) => {
		uni.navigateTo({
			url: '/my/order/ReceivedDetail?orderId=' + e.id
		})
	};
	// 送给好友
	const userGift = () => {
		console.log(orderInfo.value.cardImg)
		if (orderInfo.value.cardImg) {
			uni.navigateTo({
				url: '/my/card/send?orderId=' + orderId.value
			})
		} else {
			showCard.value = true
		}
	}
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
		console.log(copy)
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
			fail(err) {
				console.log(err)
			}
		});
	};

	// 支付方式切换
	const selectWayPay = (item) => {
		openWay.value = item.id;
	};
	const pay = () => {
		if (openWay.value == 0) {
			uni.showToast({
				title: '请选择支付方式!',
				icon: 'none'
			})
			return;
		}
		payShow.value = false
		if (openWay.value == 2) { //微信

			let data = {
				businessType: 'order',
				redirectUri: '/my/order/detail?orderId=' + orderId.value,
				amount: orderInfo.value.payMoney,
				wxPayParam: {
					// clientId: 'sys_h5_domain'
					clientId: '19',
					openId: uni.getStorageSync('openId')
				},
				extensions: {
					orderNo: orderInfo.value.orderNo,

				},
			}
			// #ifndef APP
			data.wxPayParam.tradeType = 'JSAPI'
			// #endif
			// #ifdef APP
			data.wxPayParam.tradeType = 'APP'
			// #endif
			proxy.$http.postJson('/app/wxpay/pay', data).then(res => {
				if (res.code == 0) {
					// #ifdef MP-WEIXIN
					uni.requestPayment({
						provider: 'wxpay',
						timeStamp: res.data.timeStamp,
						nonceStr: res.data.nonceStr,
						package: res.data.packageValue,
						signType: res.data.signType,
						paySign: res.data.paySign,
						success: function(ret) {
							uni.hideLoading();
							uni.showToast({
								title: '支付成功',
								icon: 'none'
							});
							setTimeout(function() {
								getDetail()
							}, 100)
						},
						fail: function(err) {
							uni.hideLoading();
							uni.showToast({
								title: '支付失败',
								icon: 'none'
							})
						}
					});
					// #endif
					// #ifdef H5
					callPay(res.data);
					// #endif
					// #ifdef APP
					isCheckPay(res.code, 'wxpay', JSON.stringify(res.data));
					// #endif

				} else {
					uni.hideLoading()
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			})


		} else if (openWay.value == 1) { //支付宝
			let data = {
				businessType: 'order',
				redirectUri: '/my/order/detail?orderId=' + orderId.value,
				amount: orderInfo.value.payMoney,
				aliPayParam: {
					// clientId: 'sys_h5_domain'
					clientId: '19'
				},
				extensions: {
					orderNo: orderInfo.value.orderNo,
				},
			
			}
			// #ifdef H5
			data.tradeType = 'WEB'
			proxy.$http.postJson('/app/alipay/wapPay', data).then(res => {
				if (res.code == 0) {
					uni.hideLoading()
					const div = document.createElement('div')
					div.innerHTML = res.data //此处form就是后台返回接收到的数据
					document.body.appendChild(div)
					document.forms[0].submit()
				} else {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			})
			// #endif
			// #ifdef APP
			data.tradeType = 'APP'
			proxy.$http.postJson('/app/alipay/appPay', data).then(res => {
				if (res.code == 0) {
					isCheckPay(res.code, 'alipay', res.data);
				} else {
					uni.hideLoading()
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			})
			// #endif
		} else if (openWay.value == 3) { //零钱
			let data = {
				orderId: orderId.value,
				userId: uni.getStorageSync('userId'),
				paymentWay: 'local', ////支付渠道 wechat: 微信 ali: 支付宝 local: 零钱支付
				paymentType: '', //1 //支付类型 1App支付 2Web支付 3jsApi
			}
			uni.showModal({
				title: '温馨提示',
				content: '确定使用余额支付？',
				showCancel: true,
				cancelText: '取消',
				confirmText: '确认',
				success: res => {
					if (res.confirm) {
						uni.showLoading({
							title: '支付中...'
						})
						proxy.$http.postJson('/app/order/pay', data)
							.then(res => {
								if (res.code == 0) {
									uni.showToast({
										title: '订单支付成功'
									});
									setTimeout(function() {
										uni.hideLoading();
										getDetail()
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

		}
	};
	const callPay = (response) => {
		if (typeof WeixinJSBridge === "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady(response), false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady(response));
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady(response));
			}
		} else {
			onBridgeReady(response);
		}
	};
	const onBridgeReady = (response) => {
		WeixinJSBridge.invoke(
			'getBrandWCPayRequest', {
				"appId": response.appid, //公众号名称，由商户传入
				"timeStamp": response.timestamp, //时间戳，自1970年以来的秒数
				"nonceStr": response.noncestr, //随机串
				"package": response.package,
				"signType": response.signType, //微信签名方式：
				"paySign": response.sign //微信签名
			},
			function(res) {
				console.log(res, '/*-/*-/*-')
				if (res.err_msg === "get_brand_wcpay_request:ok") {
					// 使用以上方式判断前端返回,微信团队郑重提示：
					//res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
					uni.showLoading({
						title: '支付成功'
					});
					setTimeout(function() {
						uni.hideLoading();
						getDetail()
					}, 100)
				} else {
					uni.hideLoading();
				}
				WeixinJSBridge.log(response.err_msg);
			}
		);
	};
	const isCheckPay = (status, name, order) => {
		if (status == 0) {
			setPayment(name, order);
		} else {
			uni.hideLoading();
			uni.showToast({
				title: '支付信息有误',
				icon: 'none'
			});
		}
	};
	const setPayment = (name, order) => {
		uni.requestPayment({
			provider: name,
			orderInfo: order, //微信、支付宝订单数据
			success: function(res) {
				uni.hideLoading();
				uni.showLoading({
					title: '支付成功'
				});
				setTimeout(function() {
					uni.hideLoading();
					getDetail()
				}, 1000);
			},
			fail: function(err) {
				console.log(err)
				uni.hideLoading();
			},
			complete() {
				uni.hideLoading();
			}
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

	.addbox {
		background: #FFFFFF;
		border-radius: 24rpx;
		margin: 20rpx 30rpx;
		padding: 30rpx;

		.addbox_title {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 30rpx;
			color: #333333;
		}

		.addbox_bot {
			display: flex;
			justify-content: space-between;
			font-family: Source Han Sans CN;
			font-weight: 400;
			font-size: 26rpx;
			color: #333333;

			.addbox_cot {
				flex: 1;

				.addbox_cotTitle {
					// width: 200px;
					// display: block;
					// overflow: hidden;
					// text-overflow: ellipsis;
					// white-space: nowrap;
				}

				.addbox_cotTit {
					text {
						margin-left: 20rpx;
					}
				}
			}

			.addbox_updete {
				width: 105rpx;
				height: 52rpx;
				border-radius: 26rpx;
				border: 1px solid #CCCCCC;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 26rpx;
				color: #666666;
				display: flex;
				align-items: center;
				justify-content: center;
			}
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

			.boxInfo_ContSku {
				font-family: PingFang SC;
				font-size: 26rpx;
				color: #999999;

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
		padding: 20rpx 30rpx;
		display: flex;
		align-items: center;
		justify-content: flex-end;
		background: #FFFFFF;

		.linebtn {
			width: 190rpx;
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
		}

		.shibtn {
			width: 190rpx;
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
		}
	}

	.popupboxCard {
		padding: 30rpx 30rpx 150rpx;

		.CardTitle {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 32rpx;
			color: #333333;
		}

		.Cardbox {
			width: calc(750rpx - 60rpx);
			margin-top: 20rpx;
		}

		.CardImage {
			margin-top: 30rpx;

			.scroll-Y {
				height: 500rpx;

				.scroll-view-item {
					padding: 10rpx 10rpx 0 0;
					width: 333rpx;
					height: 200rpx;
					border-radius: 24rpx;
					margin-bottom: 20rpx;
					position: relative;

					image {
						width: 333rpx;
						height: 200rpx;
						border-radius: 24rpx;
					}

					.CardImage_check {
						position: absolute;
						top: -6rpx;
						right: -10rpx;
						z-index: 9;
					}
				}
			}
		}

		.Cardbtn {
			position: fixed;
			bottom: 0;
			left: 0;
			right: 0;
			z-index: 99;
			background: #FFFFFF;
			padding: 20rpx 30rpx;

			.tabbtn {
				height: 88rpx;
				background: #F6317A;
				border-radius: 44rpx;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 28rpx;
				color: #FFFFFF;
				display: flex;
				align-items: center;
				justify-content: center;
			}
		}
	}


	/* 支付弹框 */
	.popup_pay {
		padding: 30rpx;

		.popup_payTit {
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 32rpx;
			color: #030303;
			text-align: center;
			margin-bottom: 30rpx;
		}

		.popup_payType {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.popup_payType_left {
				display: flex;
				align-items: center;
				margin-bottom: 40rpx;

				image {
					width: 55rpx;
					height: 55rpx;
					border-radius: 50rpx;
				}

				text {
					margin-left: 15rpx;
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 28rpx;
					color: #030303;

				}
			}
		}


		.pay_btn {
			height: 90rpx;
			background: #F6317A;
			border-radius: 45rpx;
			font-family: PingFang SC;
			font-weight: 800;
			font-size: 32rpx;
			color: #FFFFFF;
			display: flex;
			align-items: center;
			justify-content: center;
			margin-top: 20rpx;
		}
	}
</style>