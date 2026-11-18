<template>
	<view style="padding-bottom: 200rpx;">
		<!-- 2.0.19支持autoBack，默认为false -->
		<up-navbar title="送TA礼物" :autoBack="true" :placeholder="true"
			bgColor="linear-gradient(180deg, #FFE3ED 30%, #FFE9F1 100%)">
		</up-navbar>

		<view class="bgColor">
			<view class="headbox">
				<view class="headbox_title">
					选择卡面
					<text>左滑右滑选择喜欢的卡面</text>
				</view>
				<view class="swiperbox">
					<cht-swiper :imgList="imgList" :autoplay="false" @swiperChange="selectedBanner" />
				</view>
			</view>
		</view>
		<view class="title">选择礼物</view>
		<view class="listbox">
			<view v-for="(item,index) in list" :key="index" class="listbox_Item" @click.stop="goDetail(item)">
				<image :src="item.coverImg?item.coverImg:'/static/logo.png'" mode="aspectFill">
				</image>
				<view class="listbox_ItemInfo">
					<view class="">
						<view class="listbox_ItemInfoTitle">{{item.name}}</view>
						<view class="listbox_ItemInfoTit">{{item.description}}</view>
					</view>
					<view class="listbox_ItemInfobot">
						<view class="listbox_ItemInfobot_price">¥{{item.price}}</view>
						<view class="" @click.stop="openSku(item)">
							<up-icon name="plus-circle-fill" color="#F6317A" size="28"></up-icon>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 加载数据 -->
		<up-loadmore :status="status" v-if="list.length>0" />
		<!-- 暂无数据 -->
		<empty v-if="list.length==0"></empty>

		<view class="taber">
			<view class="taber_shopping">
				<view class="taber_shopping_gwc">
					<image src="/my/static/shopping.png"></image>
					<view class="taber_shopping_gwcIcon">
						<up-badge max="99" :value="productNum" bgColor="#F6317A"></up-badge>
					</view>
				</view>
				<view class="taber_shopping_price">
					<text>¥</text>{{shoppingPrice}}
				</view>
			</view>
			<view class="taber_btn" @click="goNav('/my/Commodity/shoppingCart')">
				<image src="/my/static/paylw.png"></image>
				<text>购买礼物</text>
			</view>
		</view>

		<!-- 商品规格选择 -->
		<up-popup :show="show" @close="show = false" :round="12" :closeable="true">
			<view class="popupbox">
				<view class="infobox">
					<image :src="skuImage" mode="aspectFill">
					</image>
					<view class="infobox_Itm">
						<view class="infobox_ItmTitle">{{skuItem.name}}</view>

						<view class="flex align-center justify-between">
							<view class="infobox_ItmPrice"><text>¥</text>{{skuPrice}}</view>
							<view class="infobox_ItmNum" v-if="skuNumber">库存<text>{{skuNumber}}</text></view>
						</view>
					</view>
				</view>

				<view class="skubox">
					<view v-for="(item,index) in attrValue" :key="index">
						<view class="skubox_title">{{item.value}}：</view>
						<view class="skuboxLabel">
							<view v-for="(ite,ind) in item.detail" :key="ind" class="skuboxLabel_Item"
								@click='checkState(ite,index,ind)'
								:class="item.goodsId == index && item.attrId == ind && ite.state != ''?'skuboxLabel_ItemAct':''">
								{{ite.name}}
							</view>
							<view class="skuboxLabel_Item" style="border: 0;height: 0;"></view>
							<view class="skuboxLabel_Item" style="border: 0;height: 0;"></view>
						</view>
					</view>

					<view class="skuboxNum">
						<view class="skuboxNum_title">数量：</view>
						<view class="skuboxNum_box">
							<view class="skuboxNum_box_add" @click.stop="changeNum('decrease',valueNum)">-</view>
							<view class="skuboxNum_box_Input">{{valueNum}}</view>
							<view class="skuboxNum_box_jian" @click.stop="changeNum('add',valueNum)">+
							</view>
						</view>
					</view>
				</view>

				<view class="btnbox">
					<view class="btnbox_add" @click="AddShoppingCart">
						<image src="/my/static/addgwc.png"></image>
						<text>加入购物车</text>
					</view>
					<view class="btnbox_pay" @click="clickOrder">
						<image src="/my/static/paylw.png"></image>
						<text>立即购买</text>
					</view>
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
		getCurrentInstance
	} from 'vue';
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom
	} from '@dcloudio/uni-app';
	import chtSwiper from '@/components/cht-swiper/cht-swiper.vue'
	const {
		proxy
	} = getCurrentInstance();
	const valueNum = ref(2);
	const imgList = reactive([]);
	const show = ref(false);
	const cardCategoryId = ref('');
	const cardId = ref('');
	const cardImg = ref('');

	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	//加载状态
	const status = ref('loadmore');
	const list = reactive([]);

	const skuItem = ref({});
	const skuPrice = ref(0); //规格价格
	const skuImage = ref(''); //规格图片
	const skuId = ref(''); //规格id
	const skuNumber = ref(''); //规格库存
	const attrValue = ref([]); //规格列表
	const checkStateList = ref([]);
	const checkString = ref('');
	const CheckattrValue = ref(false); //规格是否选择完成

	const totalPrice = ref(0);
	const payShow = ref(false);
	const openList = reactive([]); //支付类型
	const openWay = ref('');
	const orderId = ref('');
	const orderNo = ref('');

	const shoppingPrice = ref(0); //购物车价格
	const productNum = ref(0); //购物车数量


	onLoad(option => {

		if (option.cardCategoryId) {
			cardCategoryId.value = option.cardCategoryId
			getBanner();
		}
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
		getShoppingDetail();
	});
	// 获取购物车详情
	const getShoppingDetail = () => {
		proxy.$http.getT('/app/cart/info').then(res => {
			if (res.code == 0) {
				shoppingPrice.value = res.data.totalPrice
				productNum.value = res.data.productNum
			}
		})
	};
	// 跳转购物车
	const goNav = (url) => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url
		})
	};
	const selectedBanner = (index) => {
		cardId.value = imgList[index].cardId
		cardImg.value = imgList[index].cardImg
		console.log(cardId.value)
		page.value = 1;
		getlist();
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

	// 获取分类卡片列表
	const getBanner = () => {
		proxy.$http.getT('/app/card/listCard', {
			cardCategoryId: cardCategoryId.value
		}).then(res => {
			if (res.code == 0) {
				imgList.splice(0, imgList.length, ...res.data.records)
				cardId.value = imgList[0].cardId
				cardImg.value = imgList[0].cardImg
				page.value = 1;
				getlist();
			}
		})
	};
	// 获取列表
	const getlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			status: 0, //商品状态(默认0正常 1下架)
			cardId: cardId.value
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
	// 选择商品 商品详情规格
	const openSku = (ite) => {
		CheckattrValue.value = false;
		valueNum.value = 1
		attrValue.value = []
		proxy.$http.getT('/app/product/info?id=' + ite.id).then(res => {
			if (res.code == 0) {
				skuItem.value = res.data
				skuPrice.value = res.data.price
				skuImage.value = res.data.coverImg
				if (res.data.giftProductAttrList && res.data.giftProductAttrList.length > 0) {
					attrValue.value = [];
					res.data.giftProductAttrList[0].giftProductAttrValueList.forEach(item => {
						let data = {
							name: ''
						}
						checkStateList.value.push(data);
						let detail = [];
						item.detail.split(',').forEach(d => {
							let data = {
								name: '',
								state: ''
							}
							data.name = d;
							detail.push(data);
						});
						item.detail = detail;
						item.attrId = '';
						item.goodsId = '';
						attrValue.value.push(item);

					})
					console.log(attrValue)
				} else {
					let skuItem = res.data.giftProductSkuList[0]
					skuPrice.value = skuItem.skuPrice
					skuNumber.value = skuItem.stock;
					skuId.value = skuItem.id;
					skuImage.value = skuItem.skuImg;
					CheckattrValue.value = true;
				}
				show.value = true
			}
		})

	};
	// 选择规格
	const checkState = (item, index, ind) => {
		valueNum.value = 1
		attrValue.value[index].detail.forEach(d => {
			d.state = '';
		})
		checkStateList.value[index].name = item.name;
		attrValue.value[index].goodsId = index;
		attrValue.value[index].attrId = ind;
		attrValue.value[index].detail[ind].state = '123';

		checkString.value = '';
		checkStateList.value.forEach(d => {
			if (d.name) {
				if (checkString.value) {
					checkString.value = checkString.value + ',' + d.name;
				} else {
					checkString.value = d.name;
				}

			}
		});
		for (var i = 0; i < skuItem.value.giftProductSkuList.length; i++) {
			let d = skuItem.value.giftProductSkuList[i];
			if (d.detailJson == checkString.value) {
				if (d.stock > 0) {
					skuPrice.value = d.skuPrice
					skuNumber.value = d.stock;
					skuId.value = d.id;
					skuImage.value = d.skuImg;
					CheckattrValue.value = true;
					break;
				} else {
					skuNumber.value = 0;
					CheckattrValue.value = false;
					uni.showToast({
						title: '库存不足请选择其他的类型',
						icon: 'none'
					})
				}
			} else {
				CheckattrValue.value = false;
			}
		}
	};
	// 加入购物车
	const AddShoppingCart = () => {
		if (!CheckattrValue.value) {
			uni.showToast({
				title: '请选择正确的商品规格',
				icon: 'none'
			})
			return;
		}
		if (skuNumber.value < valueNum.value) {
			uni.showToast({
				title: '库存不足',
				icon: 'none'
			})
			return
		}
		CheckattrValue.value = false;
		checkStateList.value = []
		let userId = uni.getStorageSync('userId');
		let data = {
			userId: userId, //用户id
			productId: skuItem.value.id, //商品id
			skuId: skuId.value, //商品skuId
			num: valueNum.value, //商品数量
		}
		proxy.$http.postJson('/app/cart/save', data).then(res => {
			if (res.code == 0) {
				show.value = false
				getShoppingDetail();
				uni.showToast({
					title: '加入成功',
					icon: 'none'
				})
			}
		})
	};
	// 商品加减
	const changeNum = (v, item) => {
		if (v === 'add') {
			valueNum.value += 1
		} else if (v === 'decrease') {
			if (valueNum.value > 1) {
				valueNum.value -= 1;
			} else {
				uni.showToast({
					title: '下单商品数量不能小于1',
					icon: 'none'
				})
			}
		}
		// totalAmt();
	};
	// 下单
	const clickOrder = () => {
		if (!CheckattrValue.value) {
			uni.showToast({
				title: '请选择正确的商品规格',
				icon: 'none'
			})
			return;
		}
		if (skuNumber.value < valueNum.value) {
			uni.showToast({
				title: '库存不足',
				icon: 'none'
			})
			return
		}
		CheckattrValue.value = false;
		checkStateList.value = []
		totalPrice.value = parseFloat(skuPrice.value * valueNum.value).toFixed(2)
		console.log(totalPrice.value, skuPrice.value, valueNum.value)

		let data = {
			userId: uni.getStorageSync('userId'), //用户id
			description: '', //订单备注
			number: valueNum.value, //商品个数
			payMoney: totalPrice.value, //支付金额
			productId: skuItem.value.id, //商品id
			typeId: skuItem.value.typeId, //商品类型id
			price: skuPrice.value, //商品价格
			skuId: skuId.value, //商品skuid
			skuDetailJson: checkString.value, //商品sku信息
			orderType: 1, //订单类型（1普通订单 2拼团 3秒杀）
			userCouponsId: '', //用户优惠券id
			userCouponMoney: '', //优惠券优惠金额
			blessing: '', //祝福语
			cardId: cardId.value, //卡片id
			cardImg: cardImg.value //卡片图片地址
		}

		proxy.$http.postJson('/app/order/save', data).then(res => {
			if (res.code == 0) {
				show.value = false
				orderId.value = res.data.id
				orderNo.value = res.data.orderNo
				payShow.value = true
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
					clientId: '19'
				},
				extensions: {
					orderNo: orderNo.value,
				},
			}
			// #ifndef APP
			data.wxPayParam.tradeType = 'JSAPI'
			data.wxPayParam.openId = uni.getStorageSync('openId')
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
							url: '/my/order/detail?orderId=' + orderId.value,
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
						url: '/my/order/detail?orderId=' + orderId.value,
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
	onPullDownRefresh(() => {
		page.value = 1;
		getlist();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getlist();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F5F5F5;
	}

	.bgColor {
		width: 100%;

		// padding: 30rpx;
		background: linear-gradient(180deg, #FFE9F1 40%, #FFFFFF 100%);

		.headbox {
			width: 100%;
			background: linear-gradient(0deg, #F5F5F5 0%, #FFFFFF 100%);
			border-radius: 30rpx 30rpx 0rpx 0rpx;

			.headbox_title {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
				padding: 59rpx 30rpx 0rpx;

				text {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 24rpx;
					color: #999999;
				}
			}

			.swiperbox {
				width: 100%;
			}
		}
	}

	.title {
		margin: 30rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		font-size: 32rpx;
		color: #333333;
	}

	.listbox {

		.listbox_Item {
			background: #FFFFFF;
			border-radius: 24rpx;
			margin: 20rpx 30rpx;
			padding: 30rpx;
			display: flex;

			image {
				width: 223rpx;
				height: 223rpx;
				border-radius: 10rpx;
			}

			.listbox_ItemInfo {
				flex: 1;
				margin-left: 20rpx;
				height: 223rpx;
				display: flex;
				justify-content: space-between;
				flex-direction: column;

				.listbox_ItemInfoTitle {
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 32rpx;
					color: #333333;
					display: -webkit-box;
					-webkit-line-clamp: 2;
					/*显示3行*/
					-webkit-box-orient: vertical;
					overflow: hidden;
				}

				.listbox_ItemInfoTit {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 24rpx;
					color: #999999;
				}

				.listbox_ItemInfobot {
					display: flex;
					align-items: center;
					justify-content: space-between;

					.listbox_ItemInfobot_price {
						font-family: DIN;
						font-weight: bold;
						font-size: 38rpx;
						color: #F6317A;
					}
				}
			}
		}
	}

	.taber {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 99;
		background: #FFFFFF;
		padding: 20rpx 30rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;

		.taber_shopping {
			display: flex;
			align-items: flex-end;

			.taber_shopping_gwc {
				position: relative;

				image {
					width: 44rpx;
					height: 44rpx;
				}

				.taber_shopping_gwcIcon {
					position: absolute;
					top: -8rpx;
					right: -6rpx;
				}
			}

			.taber_shopping_price {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 36rpx;
				color: #333333;
				margin-left: 20rpx;

				text {
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 28rpx;
					color: #333333;
				}
			}
		}

		.taber_btn {
			display: flex;
			align-items: center;
			justify-content: center;
			width: 287rpx;
			height: 80rpx;
			background: #F6317A;
			border-radius: 40rpx;

			image {
				width: 42rpx;
				height: 42rpx;
			}

			text {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 28rpx;
				color: #FFFFFF;
				margin-left: 20rpx;
			}
		}
	}

	.popupbox {
		padding: 30rpx;

		.infobox {
			display: flex;
			width: 100%;

			image {
				width: 209rpx;
				height: 202rpx;
				border-radius: 16rpx;
			}

			.infobox_Itm {
				flex: 1;
				margin-left: 15rpx;
				height: 202rpx;
				display: flex;
				justify-content: space-between;
				flex-direction: column;

				.infobox_ItmTitle {
					width: 420rpx;
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 32rpx;
					color: #333333;
				}

				.infobox_ItmPrice {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 48rpx;
					color: #F6317A;

					text {
						font-weight: 400;
						font-size: 22rpx;
					}
				}

				.infobox_ItmNum {
					font-size: 28rpx;
					color: #999999;

				}
			}
		}


		.skubox {
			margin-top: 30rpx;

			.skubox_title {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 30rpx;
				color: #333333;
			}

			.skuboxLabel {
				display: flex;
				flex-wrap: wrap;
				align-items: center;
				justify-content: space-between;

				.skuboxLabel_Item {
					margin-top: 20rpx;
					width: 205rpx;
					height: 76rpx;
					background: #F6F6F6;
					border-radius: 38rpx;
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 26rpx;
					color: #333333;
					display: flex;
					align-items: center;
					justify-content: center;
					border: 1px solid #F6F6F6;
				}

				.skuboxLabel_ItemAct {
					background: #FFE9F1;
					border: 1px solid #F6317A;
					color: #F6317A;

				}
			}

			.skuboxNum {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-top: 30rpx;

				.skuboxNum_title {
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 30rpx;
					color: #333333;
				}

				.skuboxNum_box {
					display: flex;
					align-items: center;

					.skuboxNum_box_add {
						width: 62rpx;
						height: 62rpx;
						background: #F6F6F6;
						border-radius: 4rpx;
						display: flex;
						align-items: center;
						justify-content: center;
					}

					.skuboxNum_box_Input {
						width: 124rpx;
						height: 62rpx;
						background: #F6F6F6;
						border-radius: 4rpx;
						display: flex;
						align-items: center;
						justify-content: center;
						margin: 0 4rpx;
					}

					.skuboxNum_box_jian {
						width: 62rpx;
						height: 62rpx;
						background: #F6F6F6;
						border-radius: 4rpx;
						display: flex;
						align-items: center;
						justify-content: center;
					}
				}
			}
		}

		.btnbox {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 100rpx;

			.btnbox_add {
				width: 333rpx;
				height: 88rpx;
				background: #FFFFFF;
				border-radius: 44rpx;
				border: 1px solid #F6317A;

				display: flex;
				align-items: center;
				justify-content: center;

				image {
					width: 44rpx;
					height: 44rpx;
				}

				text {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 28rpx;
					color: #F6317A;
					margin-left: 10rpx;
				}
			}

			.btnbox_pay {
				width: 333rpx;
				height: 88rpx;
				background: #F6317A;
				border-radius: 44rpx;
				display: flex;
				align-items: center;
				justify-content: center;


				image {
					width: 44rpx;
					height: 44rpx;
				}

				text {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 28rpx;
					color: #FFFFFF;
					margin-left: 10rpx;
				}
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