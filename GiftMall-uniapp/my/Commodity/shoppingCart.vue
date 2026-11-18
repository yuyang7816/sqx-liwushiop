<template>
	<view style="padding-bottom: 250rpx;">
		<!-- 2.0.19支持autoBack，默认为false -->
		<up-navbar :title="'购物车'+'('+numTotal+')'" @rightClick="rightClick" :autoBack="true" :placeholder="true">
		</up-navbar>
		<view class="" style="width: 686rpx;margin: 0 auto;">

			<up-checkbox-group v-model="checkedList" @change="checkboxChange">
				<view class="list" v-for="(item,index) in list" :key="index">
					<view class="listTop">
						<!-- 	<checkbox @click="checkboxChange(index)" :checked="item.checkboxValue" style="transform:scale(0.8)"
					activeBackgroundColor="#F6317A" iconColor="#FFFFFF" /> -->
						<up-checkbox shape="circle" activeColor="#F6317A" :name="item.id"></up-checkbox>
						<view class="listbox" @click.stop="goDetail(item)">
							<image :src="item.giftProduct?item.giftProduct.coverImg:'/static/logo.png'" mode="aspectFill">
							</image>
							<view class="listbox_Cot">
								<view class="listbox_CotTop">
									<view class="listbox_CotTitle">{{item.giftProduct.name}}</view>
									<view class="listbox_CotTop_dete" @click.stop="deleteItem(item)">
										<image src="/my/static/dete.png"></image>
									</view>
								</view>
								<view class="listbox_CotSku" v-if="item.giftProductSku">
									{{item.giftProductSku.detailJson}}
								</view>
								<view class="listbox_Cotbot">
									<view class="listbox_Cotbot_price">
										¥<text>{{item.giftProduct.price}}</text>
									</view>
									<view class="listbox_Cotbot_numberbox">
										<view class="listbox_Cotbot_numberbox_add"
											@click.stop="changeNum('decrease',item)">
											-
										</view>
										<view class="listbox_Cotbot_numberbox_Input">{{item.number}}</view>
										<view class="listbox_Cotbot_numberbox_jian" @click.stop="changeNum('add',item)">
											+
										</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</up-checkbox-group>
		</view>

		<!-- 加载数据 -->
		<up-loadmore :status="status" v-if="list.length>0" />
		<!-- 暂无数据 -->
		<empty v-if="list.length==0"></empty>

		<view class="taber">
			<view class="taber_checkbox">
				<up-checkbox label="全选" name="agree" usedAlone v-model:checked="aloneChecked" shape="circle"
					activeColor="#F6317A" @change="changeAll" size="24" labelColor="#333333">
				</up-checkbox>
			</view>
			<view class="taber_price">
				<view class="taber_priceTotal">合计：{{totalPrice}}</view>
				<!-- <view class="taber_price_yhq">
					<view class="taber_price_yhqMoney">已减：￥20</view>
					<view class="taber_price_yhqTit">优惠明细
						<up-icon name="arrow-up" color="#F6317A" size="12"></up-icon>
					</view>
				</view> -->
			</view>
			<view class="taber_btn" @click="submit()">结算</view>
		</view>


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
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		getCurrentInstance,
		computed
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

	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const list = reactive([]);
	const status = ref('loadmore'); //加载状态
	const numTotal = ref(0);
	const iconStyle = ref({
		'background': '#F9F9F9'
	})
	const aloneChecked = ref(false);
	const totalPrice = ref(0);

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

	const payShow = ref(false);
	const openList = reactive([]); //支付类型
	const openWay = ref('');
	const orderId = ref('');
	const orderNo = ref('');

	const checkedList = ref([]);

	onLoad(option => {
		page.value = 1
		getShoppinglist();

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

	});
	const rightClick = () => {
		uni.navigateBack()
	};
	// 购物车选择卡片
	const changeCard = (index) => {
		console.log(index)
		cardId.value = ''
		cardImg.value = ''
		cardIndex.value = -1
		showCard.value = true
		shopCardIndex.value = index

	};
	// 购物车卡片确认选择
	const clickOrder = () => {
		if (!cardId.value) {
			uni.showToast({
				title: '请选择卡面',
				icon: 'none'
			})
			return
		}
		list[shopCardIndex.value].cardId = cardId.value
		list[shopCardIndex.value].cardImg = cardImg.value
		showCard.value = false
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
	// 跳转商品详情
	const goDetail = (item) => {
		uni.navigateTo({
			url: '/my/Commodity/detail?id=' + item.productId
		})
	};
	const checkboxChange = (n) => {
		console.log('change', n);
		checkedList.value = n

		totalAmt();
	};
	// 购物车加减
	const changeNum = (v, item) => {
		if (v === 'add') {
			item.number += 1
		} else if (v === 'decrease') {
			item.number -= 1;

			if (item.number === 0) {
				// 删除购物车商品
				uni.showToast({
					title: '删除购物车商品'
				})
				deleteItem(item);
			}
		}
		changeNumber(item); //修改数量
		totalAmt(); //计算价格
	};
	//删除商品(使用filter更安全)
	const deleteItem = (item) => {
		// list.value = list.value.filter(ite => ite.id !== item.id);
		proxy.$http.getT('/app/cart/delete?id=' + item.id).then(res => {
			if (res.code == 0) {
				uni.showToast({
					title: '删除成功',
					icon: 'none'
				})
				page.value = 1
				getShoppinglist();
			}
		})
	};
	// 修改数量
	const changeNumber = (item) => {
		proxy.$http.getT('/app/cart/changeNumber?id=' + item.id + '&number=' + item.number).then(res => {
			if (res.code == 0) {

			}
		})
	};
	// 全选
	const changeAll = (e) => {
		console.log(e)
		let arr = []
		list.forEach(d => {
			arr.push(d.id)
		})
		aloneChecked.value = !aloneChecked.value
		if (aloneChecked.value) {
			checkedList.value = arr
		} else {
			checkedList.value = []
		}

		totalAmt();
	};
	// 计算总价
	const totalAmt = () => {
		let ttl = 0;
		// console.log(list)
		list.forEach((item, index) => {
			checkedList.value.forEach((ite, ind) => {
				if (ite == item.id) {
					ttl += item.giftProduct.price * item.number;
				}
			})
		});
		totalPrice.value = ttl
	};
	// 获取购物车列表
	const getShoppinglist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			status: 0, //商品状态(默认0正常 1下架)
			userId: uni.getStorageSync('userId'),
		}

		proxy.$http.getT('/app/cart/page', data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				numTotal.value = res.data.total
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				res.data.records.forEach(d => {
					d.checkboxValue = false
				})
				if (page.value === 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
				}
				status.value = 'nomore';
				totalPrice.value = 0
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		})
	};
	onPullDownRefresh(() => {
		page.value = 1;
		getShoppinglist();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getShoppinglist();
		}
	});
	// 购物车结算
	const submit = () => {
		if (totalPrice.value == 0) {
			uni.showToast({
				title: '请选择下单商品',
				icon: 'none'
			})
			return
		}
		let arr = []
		list.forEach(d => {
			checkedList.value.forEach((ite, ind) => {
				if (ite == d.id) {
					let productInfoList = {
						productId: d.giftProduct.id, //商品id
						num: d.number, //商品个数
						productPrice: d.giftProduct.price, //商品价格
						typeId: d.giftProduct.typeId, //商品类型id
						skuId: d.skuId, //商品skuid
						skuDetailJson: d.giftProductSku? d.giftProductSku.detailJson:''
					}
					arr.push(productInfoList)
				}
			})
		})
		console.log(arr, '=======')
		let data = {
			userId: uni.getStorageSync('userId'),
			payMoney: totalPrice.value, //支付金额
			orderType: 1, //订单类型（1普通订单 2拼团 3秒杀）
			productInfoList: arr
		}
		console.log(data, '=======')
		proxy.$http.postJson('/app/order/saveList', data).then(res => {
			if (res.code == 0) {
				orderId.value = res.data.id
				orderNo.value = res.data.orderNo
				payShow.value = true
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		})
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
				amount: totalPrice.value,
				wxPayParam: {
					// clientId: 'sys_h5_domain'
					clientId: '19',
					openId: uni.getStorageSync('openId')
				},
				extensions: {
					orderNo: orderNo.value,

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
								title: '订单支付成功'
							});
							setTimeout(function() {
								uni.hideLoading();
								uni.redirectTo({
									url: '/my/order/detail?orderId=' + orderId
										.value,
								})
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
				amount: totalPrice.value,
				aliPayParam: {
					// clientId: 'sys_h5_domain'
					clientId: '19'
				},
				extensions: {
					orderNo: orderNo.value,
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
										uni.redirectTo({
											url: '/my/order/detail?orderId=' + orderId
												.value,
										})
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
						uni.redirectTo({
							url: '/my/order/index',
						})
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
					uni.redirectTo({
						url: '/my/order/index',
					})
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

	::v-deep .uni-checkbox-input {
		border-radius: 55% !important;
	}

	.list {
		width: 100%;

		margin-top: 20rpx;
		// margin: 20rpx;
		padding: 30rpx;
		background: #FFFFFF;
		border-radius: 24rpx;

		.listBot {
			display: flex;
			align-items: center;
			justify-content: flex-end;
			margin-top: 30rpx;
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 28rpx;
			color: #757575;

			.listBotImage {
				display: flex;
				align-items: center;

				image {
					width: 260rpx;
					height: 180rpx;
					border-radius: 24rpx;
					margin-right: 10rpx;
				}
			}
		}

		.listTop {
			display: flex;
			align-items: center;
		}

		.listbox {
			display: flex;
			margin-left: 10rpx;
			flex: 1;

			image {
				width: 163rpx;
				height: 163rpx;
				border-radius: 12rpx;
			}

			.listbox_Cot {
				height: 163rpx;
				flex: 1;
				margin-left: 10rpx;
				display: flex;
				justify-content: space-between;
				flex-direction: column;

				.listbox_CotTop {
					display: flex;
					justify-content: space-between;

					.listbox_CotTitle {
						font-family: Source Han Sans CN;
						font-weight: 500;
						font-size: 28rpx;
						color: #333333;
						display: -webkit-box;
						-webkit-line-clamp: 2;
						/*显示3行*/
						-webkit-box-orient: vertical;
						overflow: hidden;
					}

					.listbox_CotTop_dete {
						image {
							width: 30rpx;
							height: 30rpx;
						}
					}
				}

				.listbox_CotSku {
					font-size: 24rpx;
					color: #999999;
				}

				.listbox_Cotbot {
					display: flex;
					align-items: flex-end;
					justify-content: space-between;

					.listbox_Cotbot_price {
						font-family: PingFang SC;
						font-weight: bold;
						font-size: 24rpx;
						color: #F6317A;

						text {
							font-size: 40rpx;
						}
					}

					.listbox_Cotbot_numberbox {
						background: #FFFFFF;
						border: 1px solid #F2F2F2;
						display: flex;
						align-items: center;

						.listbox_Cotbot_numberbox_add {
							width: 46rpx;
							height: 46rpx;
							background: #F9F9F9;
							border-radius: 6rpx 0rpx 0rpx 6rpx;
							border: 1rpx solid #F2F2F2;
							display: flex;
							align-items: center;
							justify-content: center;
						}

						.listbox_Cotbot_numberbox_Input {
							width: 58rpx;
							height: 46rpx;
							background: #FFFFFF;

							display: flex;
							align-items: center;
							justify-content: center;
							font-family: PingFang SC;
							font-weight: bold;
							font-size: 24rpx;
							color: #333333;
						}

						.listbox_Cotbot_numberbox_jian {
							width: 46rpx;
							height: 46rpx;
							background: #F2F2F2;
							border-radius: 0rpx 6rpx 6rpx 0rpx;

							display: flex;
							align-items: center;
							justify-content: center;
						}
					}
				}
			}
		}
	}

	.taber {
		position: fixed;
		bottom: 0;
		background: #FFFFFF;
		padding: 20rpx 0;
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: space-between;

		.taber_checkbox {
			margin-left: 10px;
		}

		.taber_price {

			.taber_priceTotal {
				font-family: PingFang SC;
				font-weight: bold;
				font-size: 28rpx;
				color: #F6317A;
			}

			.taber_price_yhq {
				display: flex;
				align-items: center;

				.taber_price_yhqMoney {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 22rpx;
					color: #333333;
				}

				.taber_price_yhqTit {
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 22rpx;
					color: #F6317A;
					display: flex;
					align-items: center;
					margin-left: 15rpx;
				}
			}
		}

		.taber_btn {
			margin-right: 46rpx;
			width: 170rpx;
			height: 68rpx;
			background: #F6317A;
			border-radius: 34rpx;
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