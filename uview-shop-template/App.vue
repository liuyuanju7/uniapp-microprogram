<style lang="scss">
	/* #ifndef APP-NVUE */
	@import "uview-ui/index.scss";
	@import './style/iconfont.scss';
	@import "./style/common.scss";

	page {
		background-color: #f5f5f5;
	}

	// 隐藏滚动条
	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	/* #endif */
	.price {
		color: #f7403f;
		font-size: 40rpx;
		font-weight: 650;
	}

	.del {
		color: #c0c4cc;
		text-decoration: line-through
	}

	// /* 商品列表 */ 一行2个

	.goods_flex {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap
	}

	.goods_list {
		width: 50%;
		padding: 6rpx;
		align-items: center;
		justify-content: center;
		position: relative;
		flex-direction: column;
		box-sizing: border-box;
		display: flex;

	}

	.goods_item {
		width: 100%;
		// height: 480rpx;
		padding: 10rpx;
		background-color: #FFFFFF;


	}
	.goods-title {
		width: 100%;
		
		padding: 8rpx;
		font-weight: bolder;
	}
	
	.goods-title2 {
		width: 100%;
		
		padding: 5rpx;
		// color: $uni-color-paragraph!important;
	}
	.goods-price {
		float: right;
		padding-bottom: 15rpx;
	}
	//底部固定
	.footer{
		       padding: 0 10px 0 16px;
		       position: fixed;
		       bottom: 0;
		       width: 100%;
		       box-sizing: border-box;
		       background-color: #fff;
		       z-index: 277;
		       border-top: 1px solid #f0f0f0;
		       height: 100rpx;
		      
	}
</style>
<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
	page {
		background-color: #f8f9f9;
	}

	.m_top {
		margin-top: 20rpx;
	}

	.m_list_bg {
		background-color: #FFFFFF;
		padding: 10rpx;
		border-radius: 10rpx;

	}

	.m_card {
		margin-right: 12rpx;
		margin-left: 12rpx;
	}
</style>
