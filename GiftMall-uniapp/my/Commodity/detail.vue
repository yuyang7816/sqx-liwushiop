<template>
	<view style="padding-bottom:200rpx;" v-if="order">
		<view class="swiper_box">
			<swiper class="screen-swiper" :circular="true" :autoplay="true" interval="2500" duration="800">
				<swiper-item v-for="(item,index) in order.img" :key="index" @click="saveImg(order.img,index)">
					<image :src="item?item:'/static/logo.png'" class="screen-swiper" mode="aspectFill"></image>
				</swiper-item>
			</swiper>
		</view>

		<view class="head">
			<view class="headTop">
				<view class="headTop_title">{{order.name}}</view>
				<view class="headTop_share" @click="shareApplink">
					<image src="/my/static/detail/shareIcon.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="headCot">
				<view class="headCot_price">
					<view class="headCot_price_new">
						¥<text>{{order.price}}</text>
					</view>
					<view class="headCot_price_old">¥{{order.originalPrice}}</view>
				</view>
				<view class="headCot_sale">已售{{order?order.sales:0}}单</view>
			</view>
			<view class="headLine"></view>
			<view class="headbot">
				<view class="headbot_title">
					<image src="/my/static/detail/tit.png"></image>
				</view>
				<view class="headbotItem">
					<image src="/my/static/detail/duiIcon.png"></image>
					<text>正品保障</text>
				</view>
				<view class="headbotItem">
					<image src="/my/static/detail/duiIcon.png"></image>
					<text>假一赔三</text>
				</view>
				<view class="headbotItem">
					<image src="/my/static/detail/duiIcon.png"></image>
					<text>平台担保</text>
				</view>
			</view>
		</view>
		<!-- 赠送流程 -->
		<view class="Process">
			<view class="Process_Head">
				<view class="Process_Headline"></view>
				<view class="Process_HeadTitle">赠送流程</view>
			</view>
			<view class="Processbox">
				<view class="Processbox_Item">
					<image src="/my/static/detail/gmlp.png" mode="widthFix"></image>
					<view class="Processbox_ItemTite">购买礼品</view>
				</view>
				<view class="Processbox_Line"></view>
				<view class="Processbox_Item">
					<image src="/my/static/detail/wxlq.png" mode="widthFix"></image>
					<view class="Processbox_ItemTite">微信赠送</view>
				</view>
				<view class="Processbox_Line"></view>
				<view class="Processbox_Item">
					<image src="/my/static/detail/hylq.png" mode="widthFix"></image>
					<view class="Processbox_ItemTite">好友领取</view>
				</view>
				<view class="Processbox_Line"></view>
				<view class="Processbox_Item">
					<image src="/my/static/detail/kdlp.png" mode="widthFix"></image>
					<view class="Processbox_ItemTite">快递礼品</view>
				</view>
			</view>
		</view>

		<!-- 规格 保障 -->
		<view class="boxType">
			<view class="boxType_box">
				<view class="boxType_boxSku">
					<view class="boxType_boxSku_Title">规格</view>
					<view class="boxType_boxSku_Tit">未选择</view>
				</view>
				<!-- <image src="/static/image/my/huiright.png"></image> -->
			</view>
			<view class="boxType_box margin-top">
				<view class="boxType_boxSku">
					<view class="boxType_boxSku_Title">保障</view>
					<view class="boxType_boxSku_Tit">假一赔十，极速退款</view>
				</view>
			</view>
		</view>
		<!-- 商品详情 -->
		<view class="boxDetail" v-if="order">
			<view class="boxDetail_Title">商品详情</view>
			<view v-for="(item,index) in order.img" :key="index" @click="saveImg(order.img,index)">
				<image :src="item" mode="widthFix"></image>
			</view>

		</view>


		<view class="taber">
			<view class="taberleft">
				<view class="taberIcon" @click="clickCollect">
					<image src="/my/static/detail/scIcon.png" v-if="!isCollect"></image>
					<image src="/my/static/detail/scActIcon.png" v-else></image>
					<view class="taberIcon_Tit">收藏</view>
				</view>
				<view class="taberIcon" @click="goShoppingCart">
					<image src="/my/static/detail/gwc.png"></image>
					<view class="taberIcon_Tit">购物车</view>
				</view>
			</view>
			<view class="taberBtn">
				<view class="taberBtn_add" @click="clickSku(1)">加入购物车</view>
				<view class="taberBtn_pay" @click="clickSku(2)">立即购买</view>
			</view>
		</view>

		<up-popup :show="show" @close="show = false" :round="12" :closeable="true">
			<view class="popupbox">
				<view class="infobox">
					<image :src="skuImage?skuImage:'/static/logo.png'" mode="aspectFill"></image>
					<view class="infobox_Itm">
						<view class="infobox_ItmTitle">{{order.name}}</view>
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

				<view class="btnbox" @click="submitSku">{{orderType==1?'加入购物车':'下一步' }}</view>
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
					<view class="tabbtn" @click="clickOrder">立即支付 ¥{{totalPrice}}</view>
					<!-- <view class="tabbtn" @click="goNav('/my/card/send')">立即支付 ¥248.73</view> -->
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
	const {
		proxy
	} = getCurrentInstance();
	const order = ref();
	const show = ref(false);
	const valueNum = ref(1);
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
	const checkboxValue = ref(false);
	const id = ref('');
	const isCollect = ref(false);

	const skuPrice = ref(0); //规格价格
	const skuImage = ref(''); //规格图片
	const skuId = ref(''); //规格id
	const skuNumber = ref(''); //规格库存
	const attrValue = ref([]); //规格列表
	const checkStateList = ref([]);
	const checkString = ref('');
	const CheckattrValue = ref(false); //规格是否选择完成
	const orderType = ref('');

	const cardType = reactive([{
		cardCategoryName: '全部图案',
		cardCategoryId: ''
	}]); //卡片分类
	const cardCategoryId = ref('');
	const cardlist = reactive([]);
	const cardIndex = ref(-1);
	const cardId = ref('');
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const status = ref('loadmore'); //加载状态
	const totalPrice = ref(0);
	const payShow = ref(false);
	const openList = reactive([]); //支付类型
	const openWay = ref('');
	const orderId = ref(''); //订单id
	const orderNo = ref(''); //订单编号
	const url = ref('');

	onLoad(option => {
		if (option.id) {
			id.value = option.id
			getDetailInfo(); //获取商品详情
			let token = uni.getStorageSync('token');
			if (token) {
				getGoodsCollect(); //获取商品是否收藏
				getCardType(); //获取卡片分类
				getCardlist(); //获取卡片列表
			}

		}
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
		proxy.$http.getT('/app/common/type/25').then(ress => {
			if (ress.code === 0) {
				if (ress.data && ress.data.value) {
					url.value = ress.data.value;
				} else {
					url.value = proxy.$publicYuMing();
				}
			}
		});
		//#endif
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
	// 分享
	const shareApplink = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		let relationId = uni.getStorageSync('invitationCode');
		uni.showModal({
			title: '文案推广',
			content: order.value.name + relationId + '\n' + url.value + '/?inviterCode=' + relationId,
			showCancel: true,
			cancelText: '关闭',
			confirmText: '一键复制',
			confirmColor: '#F6317A',
			success: res => {
				if (res.confirm) {
					uni.setClipboardData({
						data: tuiguang1.value + relationId + '\n' + url.value + '/?inviterCode=' +
							relationId,
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
	// 跳转购物车
	const goShoppingCart = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/Commodity/shoppingCart'
		})
	};
	// 点击收藏
	const clickCollect = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		let userId = uni.getStorageSync('userId');

		if (!isCollect.value) {
			let data = {
				productId: id.value,
				userId: userId,
			}
			proxy.$http.postJson('/app/userCollect/save', data).then(res => {
				if (res.code == 0) {
					getGoodsCollect();
				}
			})
		} else {
			let data = {
				productId: id.value,
				userId: userId,
			}
			proxy.$http.getT('/app/userCollect/delete', data).then(res => {
				if (res.code == 0) {
					getGoodsCollect();
				}
			})
		}
	};
	// 获取商品是否收藏
	const getGoodsCollect = () => {
		let userId = uni.getStorageSync('userId');
		proxy.$http.getT('/app/userCollect/check?productId=' + id.value + '&userId=' + userId).then(res => {
			if (res.code == 0) {
				isCollect.value = res.data
			}
		})
	};
	//详情图片放大
	const saveImg = (imgs, index) => {
		let imgArr = imgs
		// //预览图片
		uni.previewImage({
			urls: imgArr,
			current: imgArr[index]
		});
	};
	// 重置规格
	const chongzhiSku = () => {
		skuPrice.value = order.value.price
		skuImage.value = order.value.coverImg
		console.log(order.value.giftProductAttrList)
		if (order.value.giftProductAttrList && order.value.giftProductAttrList.length > 0) {
			attrValue.value = [];
			order.value.giftProductAttrList[0].giftProductAttrValueList.forEach(item => {
				let data = {
					name: ''
				}
				checkStateList.value.push(data);
				let detail = [];
				console.log(typeof item.detail)
				if (typeof item.detail == 'object') {
					item.detail.forEach(d => {
						detail.push(d);
					});
					item.detail = detail;
					item.attrId = '';
					item.goodsId = '';
					attrValue.value.push(item);
				} else {
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
				}
			})
			console.log(attrValue)
		} else {

		}
	};
	// 获取商品详情
	const getDetailInfo = () => {
		proxy.$http.getT('/app/product/info?id=' + id.value).then(res => {
			if (res.code == 0) {
				if (res.data.img) {
					res.data.img = res.data.img.split(',')
				}
				order.value = res.data
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

			}
		})
	};
	//商品规格
	const clickSku = (type) => { //type 1 购物车 2 购买下单
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		chongzhiSku();
		orderType.value = type
		cardId.value = ''
		cardIndex.value = -1;
		valueNum.value = 1
		attrValue.value.forEach(d => {
			d.goodsId = '';
			d.attrId = '';
		})
		if (!order.value.giftProductAttrList) {
			CheckattrValue.value = true;
		}

		checkString.value = '';


		show.value = true
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
		for (var i = 0; i < order.value.giftProductSkuList.length; i++) {
			let d = order.value.giftProductSkuList[i];
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
	// 选择规格  打开卡片弹框
	const submitSku = () => {
		console.log(CheckattrValue.value)
		if (CheckattrValue.value) {
			if (skuNumber.value < valueNum.value) {
				uni.showToast({
					title: '库存不足',
					icon: 'none'
				})
				return
			}
			if (orderType.value == 1) {
				AddShoppingCart(); //加入购物车
			} else {
				totalPrice.value = parseFloat(skuPrice.value * valueNum.value).toFixed(2)
				console.log(totalPrice.value, skuPrice.value, valueNum.value)
				show.value = false
				showCard.value = true
			}
			CheckattrValue.value = false;
			checkStateList.value = []
		} else {
			uni.showToast({
				title: '请选择正确的商品规格',
				icon: 'none'
			})
		}

	};
	// 加入购物车
	const AddShoppingCart = () => {
		let userId = uni.getStorageSync('userId');
		let data = {
			userId: userId, //用户id
			productId: id.value, //商品id
			skuId: skuId.value, //商品skuId
			num: valueNum.value, //商品数量
		}
		proxy.$http.postJson('/app/cart/save', data).then(res => {
			if (res.code == 0) {
				show.value = false
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
	// 卡片分类切换
	const clickCardType = (e) => {
		console.log(e)
		cardCategoryId.value = e.cardCategoryId
		page.value = 1;
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
		cardId.value = item.cardId
	};
	// 获取卡片列表
	const getCardlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			cardCategoryId: cardCategoryId.value
		}
		proxy.$http.getT('/app/card/listCard', data).then(res => {
			if (res.code == 0) {
				if (res.code == 0) {
					pages.value = res.data.pages
					if (page.value < pages.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}
					if (page.value === 1) {
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
		if (page.value < pages.value) {
			page.value += 1
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
		page.value = 1
		getCardlist()

	};
	// 下单
	const clickOrder = () => {
		if (!cardId.value) {
			uni.showToast({
				title: '请选择卡面',
				icon: 'none'
			})
			return
		}
		let cardImg = cardlist[cardIndex.value].cardImg
		let data = {
			userId: uni.getStorageSync('userId'), //用户id
			description: '', //订单备注
			number: valueNum.value, //商品个数
			payMoney: totalPrice.value, //支付金额
			productId: id.value, //商品id
			typeId: order.value.typeId, //商品类型id
			price: skuPrice.value, //商品价格
			skuId: skuId.value, //商品skuid
			skuDetailJson: checkString.value, //商品sku信息
			orderType: 1, //订单类型（1普通订单 2拼团 3秒杀）
			userCouponsId: '', //用户优惠券id
			userCouponMoney: '', //优惠券优惠金额
			blessing: '', //祝福语
			cardId: cardId.value, //卡片id
			cardImg: cardImg //卡片图片地址
		}

		proxy.$http.postJson('/app/order/save', data).then(res => {
			if (res.code == 0) {
				showCard.value = false
				totalPrice.value = res.data.payMoney
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
	// 跳转登录
	const goLoginInfo = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
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

	.swiper_box {
		width: 100%;

		.screen-swiper {
			width: 100%;
			height: 500rpx;
		}
	}

	.head {
		background: #FFFFFF;
		padding: 30rpx 30rpx 20rpx;

		.headTop {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.headTop_title {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
			}

			.headTop_share {
				image {
					width: 30rpx;
					height: 31rpx;
				}
			}
		}

		.headCot {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 20rpx;

			.headCot_price {
				display: flex;
				align-items: flex-end;

				.headCot_price_new {
					font-family: DIN;
					font-weight: bold;
					font-size: 28rpx;
					color: #F6317A;

					text {
						font-size: 38rpx;
					}
				}

				.headCot_price_old {
					font-family: DIN;
					font-weight: 500;
					font-size: 28rpx;
					color: #CCCCCC;
					text-decoration-line: line-through;
					margin-left: 20rpx;
				}
			}

			.headCot_sale {
				font-family: PingFang SC;
				font-weight: 500;
				font-size: 24rpx;
				color: #999999;
			}
		}

		.headLine {
			width: 100%;
			height: 1rpx;
			background: #F5F5F5;
			margin: 15rpx 0;
		}

		.headbot {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 15rpx;

			.headbot_title {
				image {
					width: 92rpx;
					height: 29rpx;
				}
			}

			.headbotItem {
				display: flex;
				align-items: center;

				text {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 24rpx;
					color: #333333;
					margin-left: 6rpx;
				}

				image {
					width: 33rpx;
					height: 32rpx;
				}
			}
		}
	}

	.Process {
		background: #FFFFFF;
		margin: 20rpx 0;
		padding: 24rpx 30rpx;

		.Process_Head {
			display: flex;
			align-items: center;

			.Process_Headline {
				width: 6rpx;
				height: 28rpx;
				background: #F6317A;
				border-radius: 3rpx;
			}

			.Process_HeadTitle {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 32rpx;
				color: #2C2C2C;
				margin-left: 8rpx;
			}
		}

		.Processbox {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 20rpx;

			.Processbox_Item {
				text-align: center;

				image {
					width: 54rpx;
					height: 54rpx;
				}

				.Processbox_ItemTite {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 24rpx;
					color: #666666;
				}
			}

			.Processbox_Line {
				width: 63rpx;
				border-top: 1px dashed #999999;
				position: relative;
				top: -20rpx;
			}
		}
	}

	.boxType {
		background: #FFFFFF;
		margin: 20rpx 0;
		padding: 30rpx;

		.boxType_box {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.boxType_boxSku {
				display: flex;
				align-items: center;

				.boxType_boxSku_Title {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 24rpx;
					color: #999999;
					margin-right: 20rpx;
				}

				.boxType_boxSku_Tit {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 26rpx;
					color: #333333;
				}

			}

			image {
				width: 28rpx;
				height: 28rpx;
			}
		}
	}

	.boxDetail {
		background: #FFFFFF;

		.boxDetail_Title {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 28rpx;
			color: #2C2C2C;
			padding: 20rpx;
			text-align: center;
		}

		image {
			width: 100%;
		}
	}

	.taber {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 9;
		display: flex;
		align-items: center;
		justify-content: space-between;
		background: #FFFFFF;
		padding: 20rpx 30rpx 20rpx 10rpx;

		.taberleft {
			display: flex;
			align-items: center;
			justify-content: space-between;
			flex: 1;

			.taberIcon {
				width: 90rpx;
				text-align: center;

				image {
					width: 44rpx;
					height: 42rpx;
				}
			}

			.taberIcon_Tit {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 24rpx;
				color: #999999;
			}
		}

		.taberBtn {
			display: flex;
			align-items: center;
			margin-left: 20rpx;

			.taberBtn_add {
				width: 250rpx;
				height: 80rpx;
				background: #FFE5EF;
				border-radius: 40rpx 0rpx 0rpx 40rpx;
				display: flex;
				align-items: center;
				justify-content: center;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 28rpx;
				color: #F6317A;
			}

			.taberBtn_pay {
				width: 250rpx;
				height: 80rpx;
				background: #F6317A;
				border-radius: 0rpx 40rpx 40rpx 0rpx;
				display: flex;
				align-items: center;
				justify-content: center;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 28rpx;
				color: #FFFFFF;
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
			font-family: Source Han Sans CN;
			font-weight: 400;
			font-size: 28rpx;
			color: #FFFFFF;
			width: 686rpx;
			height: 88rpx;
			background: #F6317A;
			border-radius: 44rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			margin-top: 100rpx;
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