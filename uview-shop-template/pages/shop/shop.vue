<template>
	<view id="item" class="u-wrap">

		<view class="u-menu-wrap">
			<scroll-view scroll-y scroll-with-animation class="u-tab-view menu-scroll-view" :scroll-top="scrollTop">
				<view v-for="(item,index) in tabbar" :key="index" class="u-tab-item"
					:class="[current==index ? 'u-tab-item-active' : '']" :data-current="index"
					@tap.stop="swichMenu(index,item.id)">
					<text class="u-line-1">{{item.title}}</text>
				</view>
			</scroll-view>
			<block>
				<scroll-view scroll-y class="right-box" :refresher-enabled='true'>
					<view class="goods_flex" style="width: 100%;">
						<view class="goods_list" v-for="(item,index) in lists" :key="index">
							<view class="goods_item" style="" @click="shopDetail(item.id)">
								<view class="list-img">
									<u-image :lazy-load="true" width="100%" height="250rpx"
										:src="item.img" mode="scaleToFill"></u-image>
								</view>
								<view class="goods-title">
									<u-text :lines="1" :text="item.title"></u-text>
								</view>
								<view class="goods-title2 ">
									<u-text :lines="1" color="#888888" text="111"></u-text>
								</view>
								<view class="goods-gm" style="text-align: right;">
									<text class="price">￥{{item.money}}</text><text class="del">￥{{item.shi_money}}</text>
								
								</view>
								<!-- <view class="goods-gm">
									<view style="float: right;right: 5rpx;">
										<u-button type="error" size="mini" text="购买"></u-button>
									</view>
								</view> -->
							</view>
						</view>
					
					
					</view>
					<view style="margin: 30rpx;">
						<u-loadmore :status="status" @loadmore="shangla" />
					</view>
				</scroll-view>
			</block>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {

				tabbar: [],
				scrollTop: 0, //tab标题的滚动条位置
				current: 0, // 预设当前项的值
				menuHeight: 0, // 左边菜单的高度
				menuItemHeight: 0, // 左边菜单item的高度
				lists: [

				],
				is_list: false,
				page: 1,
				group: 1,
				status: 'loadmore',
			};
		},
		onReady() {

		},
		onLoad() {
			this.getInfo()
		},
		methods: {
			getInfo() {
				var that = this;
				this.status = 'loading'
				this.common.post('index/getShopList', {
					page: this.page,
					group: this.group
				}, function(res) {
					console.log(res)
					if (res.code == 200) {
						that.lists = that.lists.concat(res.data.shop);
						that.tabbar = res.data.group;

						if (res.data.shop.length == 0) {
							that.status = 'nomore'
						} else {
							that.status = 'loadmore'
						}
					}
				}, "加载中")
			},
			shopDetail(id) {
				uni.navigateTo({
					url: "/pages/shop/goodsDetail/goodsDetail?id=" + id
				})
			},
			// 点击左边的栏目切换
			async swichMenu(index, group) {
				if (index == this.current) return;
				this.current = index;
				// 如果为0，意味着尚未初始化
				if (this.menuHeight == 0 || this.menuItemHeight == 0) {
					await this.getElRect('menu-scroll-view', 'menuHeight');
					await this.getElRect('u-tab-item', 'menuItemHeight');
				}
				// 将菜单菜单活动item垂直居中
				this.scrollTop = index * this.menuItemHeight + this.menuItemHeight / 2 - this.menuHeight / 2;

				this.group = group;
				this.page = 1;
				this.lists = [];
				this.getInfo();

			},
			// 获取一个目标元素的高度
			getElRect(elClass, dataVal) {
				new Promise((resolve, reject) => {
					const query = uni.createSelectorQuery().in(this);
					query.select('.' + elClass).fields({
						size: true
					}, res => {
						// 如果节点尚未生成，res值为null，循环调用执行
						if (!res) {
							setTimeout(() => {
								this.getElRect(elClass);
							}, 10);
							return;
						}
						this[dataVal] = res.height;
					}).exec();
				})
			},
			//页面相关事件处理函数--监听用户下拉动作
			onPullDownRefresh: function() {
				this.nomore = false;
				//延时为了看效果
				setTimeout(() => {
					this.page = 1;
					this.lists = [];
					this.getInfo();
					uni.stopPullDownRefresh();

				}, 200)
			},

			// 页面上拉触底事件的处理函数
			onReachBottom: function() {
				// if (this.nomore) return;
				this.page = this.page + 1;
				this.getInfo();
			},
			shangla: function() {
				// if (this.nomore) return;
				this.page = this.page + 1;
				this.getInfo();
			},

		}
	}
</script>

<style lang="scss">
	.u-wrap {
		height: calc(100vh);
		/* #ifdef H5 */
		height: calc(100vh - var(--window-top));
		/* #endif */
		background-color: #FFF;
		display: flex;
		flex-direction: column;
	}



	.u-menu-wrap {
		flex: 1;
		display: flex;
		overflow: hidden;
		border-top: 1rpx solid #f0f0f0;
	}



	.u-tab-view {
		width: 200rpx;
		height: 100%;
	}

	.u-tab-item {
		height: 110rpx;
		background: #f6f6f6;
		box-sizing: border-box;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 26rpx;
		color: #444;
		font-weight: 400;
		line-height: 1;
	}

	.u-tab-item-active {
		position: relative;
		color: #000;
		font-size: 30rpx;
		font-weight: 600;
		background: #fff;
	}

	.u-tab-item-active::before {
		content: "";
		position: absolute;
		border-left: 4px solid red;
		height: 32rpx;
		left: 0;
		top: 39rpx;
	}

	.u-tab-view {
		height: 100%;
	}

	.right-box {
		background-color: rgb(250, 250, 250);
	}
</style>
<style lang="scss">
	//列表
	.item-box {
		padding: 20rpx 10rpx;

		display: flex;
		flex-wrap: wrap;

		&>view {
			margin-bottom: 20rpx;
		}

		.item {
			background-color: #FFF;
			border-radius: 20rpx;
			box-sizing: border-box;
		}

		.type-list {
			width: 100%;
			padding: 30rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;

			.img {
				flex-shrink: 0;
			}

			.msg {
				width: 100%;
				height: 230rpx;
				padding: 0 20rpx;
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				align-items: flex-start;

				.title {
					overflow: hidden;
					text-overflow: ellipsis;
					display: -webkit-box;
					-webkit-line-clamp: 2;
					-webkit-box-orient: vertical;
				}

				.price {
					width: 100%;
					display: flex;
					justify-content: space-between;
					align-items: flex-start;

					.original-price {
						text {
							text-decoration: line-through;
						}
					}

					.original-price,
					.sell-num {
						font-size: 24rpx;
						color: #888;
					}

					.present-price {
						color: #ff3232;
						font-size: 24rpx;
						font-weight: bold;

						.num {
							font-size: 34rpx;
						}
					}

					.sell-num {
						text {
							padding: 0 10rpx;
						}
					}
				}
			}
		}

		.type-block {
			width: 48%;
			display: flex;
			flex-wrap: wrap;
			border-radius: 8rpx;
			overflow: hidden;

			.img {
				width: 100%;
			}

			.msg {
				height: 150rpx;
				padding: 30rpx 20rpx;
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				box-sizing: border-box;

				.title {
					overflow: hidden;
					text-overflow: ellipsis;
					display: -webkit-box;
					-webkit-line-clamp: 2;
					-webkit-box-orient: vertical;
				}

				.price {
					width: 100%;
					display: flex;
					justify-content: space-between;
					align-items: flex-start;

					.original-price {
						text {
							text-decoration: line-through;
						}
					}

					.original-price,
					.sell-num {
						font-size: 24rpx;
						color: #888;
					}

					.present-price {
						color: #ff3232;
						font-size: 24rpx;
						font-weight: bold;

						.num {
							font-size: 34rpx;
						}
					}

					.sell-num {
						text {
							padding: 0 10rpx;
						}
					}
				}
			}
		}
	}

	.list {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.block {
		display: flex;
		justify-content: space-between;
	}

	.uview-popup {
		color: #333;
		font-size: 24rpx;

		.title {
			width: 100%;
			padding: 20rpx;
			color: #888;
			background-color: rgba(141, 141, 141, 0.1);
		}

		.postage {
			padding: 20rpx;

			view {
				margin-top: 20rpx;

				.tips {
					display: inline-block;
					width: 100rpx;
					padding: 12rpx 8rpx;
					font-size: 24rpx;
					text-align: center;
					border-radius: 8rpx;
					background-color: rgba(144, 144, 144, 0.1);
					box-sizing: border-box;
				}
			}

			border-bottom: 1rpx solid rgba(144, 144, 144, 0.1);
		}

		.price {
			padding: 20rpx;

			view {
				margin-top: 40rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;

				// box-sizing: border-box;
				.u-input {
					height: 50rpx;
					margin: 0 4rpx;
					background-color: rgba(144, 144, 144, 0.1);
					border-radius: 50rpx;
				}

				view {
					margin: 0;
				}
			}

			border-bottom: 1rpx solid rgba(144, 144, 144, 0.1);
		}

		.all-types {
			padding: 20rpx;

			view {
				margin-top: 30rpx;
				display: flex;
				justify-content: space-between;
				flex-wrap: wrap;
				align-items: center;

				.tips {
					display: inline-block;
					width: 30%;
					padding: 12rpx 8rpx;
					font-size: 24rpx;
					text-align: center;
					border-radius: 8rpx;
					background-color: rgba(144, 144, 144, 0.1);
					box-sizing: border-box;
				}
			}
		}

		.btns {
			width: 100%;
			position: absolute;
			bottom: 60rpx;
			display: flex;
			justify-content: center;
			align-items: center;

			view {
				display: inline-block;
				padding: 10rpx 60rpx;
				font-size: 32rpx;
				color: #666;
			}

			.reset {
				border: 1rpx solid rgba(144, 144, 144, 0.5);
				border-right: 1rpx solid transparent;
				border-radius: 50rpx 0 0 50rpx;
			}

			.enter {
				background-color: #ff3232;
				border: 1rpx solid #ff3232;
				border-right: 1rpx solid transparent;
				border-radius: 0 50rpx 50rpx 0;
				color: #fff;
			}
		}

		.active {
			color: #ff3232 !important;
		}
	}
</style>
