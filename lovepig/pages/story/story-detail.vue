<template>
	<view class="content">
		<view class="banner" auto-focus>
			<image class="banner-img" :src="banner.image_url"  mode="aspectFit"></image>
			<view class="title-area">
				<text class="title-text">{{banner.title}}</text>
			</view>

		</view>
		<view class=" u-flex">
			<u-avatar class="story-u-avatar" size="80"  :src="banner.avatar"></u-avatar>
			<view class="">
				<view class="u-main-color u-font-xl u-padding-bottom-5">
				{{banner.author}}
				</view>
				<view class="u-tips-color u-font-sm">
					{{banner.datetime}}
				</view>
			</view>

		</view>
		<view class="article-content">
			<mp-html :content="content" />
		</view>
		<view class="comment-wrap"></view>
	</view>
</template>

<script>
	import htmlParser from '@/common/html-parser'

	const FAIL_CONTENT = '<p>获取信息失败1</p>';

	function parseImgs(nodes) {
		nodes.forEach(node => {
			if (
				node.name === 'img' &&
				node.attrs &&
				node.attrs['data-img-size-val']
			) {
				const sizes = node.attrs['data-img-size-val'].split(',')
				const width = uni.upx2px(720 * 0.9)
				const height = parseInt(width * (sizes[1] / sizes[0]))
				node.attrs.style = `width:${width};height:${height};`
			}
			if (Array.isArray(node.children)) {
				parseImgs(node.children)
			}
		})
		return nodes
	}

	export default {
		data() {
			return {
				banner: {},
				content: ""
			}
		},
		onShareAppMessage() {
			return {
				title: this.banner.title,
				path: '/pages/detail/detail?query=' + JSON.stringify(this.banner)
			}
		},
		onLoad(event) {
			// 目前在某些平台参数会被主动 decode，暂时这样处理。

			// this.load(event.query);
			this.banner = {
				image_url: 'https://www.ysext.com/upload/default/20200530/1e40bb3acaaec91c2c5e96f0a18dd576.png',
				title: '1111目前在某些平台参数会被主动目前在某些平台参数会被主动目前在某些平台参数会被主动目前在某些平台参数会被主动',
				source: '2222',
				datetime: '2002-09-09'
			}
			this.content = "<div class='content' id='content'>			<p>	<strong>七律 匆匆<a href='http://www.meiwenjx.com/renshengganwu/' target='_blank'><u>人生</u></a>悠悠过</strong></p><p>	路阻昭关夜白头，淮南称帝失王侯。</p><p>	悬梁可把诗书读，刺股难将富贵求。</p><p>	彭祖修形欢百载，潜龟息气享千秋。</p><p>	人生莫效波涛急，且作山泉细细流。</p><p>	<strong>七律　玉米</strong><span style='position:relative;left:-10000px;' class='w'>（<a href='http://www.meiwenjx.com' target='_blank'>美文精选网</a>：www.meiwenjx.com）</span></p><p>	一颗金星自上苍，安家落户在山乡。</p><p>	根餐雨露姿含秀，叶沐阳光蕊吐芳。</p><p>	夫妇连心通体绿，儿孙返祖遍身黄。</p><p>	虽为大地平凡果，却是人间活命粮。</p><p>	<strong>七律 牛日牛说</strong><span style='position:relative;left:-10000px;' class='w'>（<a href='http://www.meiwenjx.com' target='_blank'>美文精选网</a>：www.meiwenjx.com）</span></p><p>	耕田莫笑老夫痴，撒满人间尽是诗。</p><p>	自幼山岗驱老虎，成年大漠斗雄狮。</p><p>	当车李耳云端驾，作马刘皇战阵驰。</p><p>	俞瑞弹琴虽不懂，常怀犄角挂书时。</p><p>	(注:1.初生牛犊不怕虎。2.李耳:太上老君骑青牛。3.刘皇:汉武帝刘秀骑牛冲阵。4.俞瑞:伯牙。5.见典故李密之牛角挂书。)&nbsp;</p><p>	<strong>七律 听《二泉映月》吊阿炳</strong></p><p>	双弦作伴四飘流，一曲悲歌泣到头。</p><p>	<a href='http://www.meiwenjx.com/zhutimeiwen/mengxiangmeiwen/' target='_blank'><u>梦想</u></a>难寻唯诅咒，光明向往剩祈求。</p><p>	拉干世上辛酸泪，道尽人间爱恨愁。</p><p>	莫说昙花生命短，长存绝唱耀千秋。</p><p>	<strong>七律 风雨高陵说泾渭</strong></p><p>	雨骤风狂未放晴，轻车快马又登程。</p><p>	晨经峻岭沙丘塌，午过幽林布谷惊。</p><p>	滚滚云低天暗淡，滔滔浪急界分明。</p><p>	心安我似高陵水，泾渭同流自浊清。</p><p>	<strong>七律 壶口瀑布遐思</strong></p><p>	洪流滚滚自云巅，顽石层层瀑布悬。</p><p>	峡谷幽深难见底，浪花飞舞却生烟。</p><p>	将来骤雨山风送，欲去游人倩影连。</p><p>	我别黄河思不绝，几时锦鲤可升天？</p></div>";
		},
		methods: {
			load(e) {
				var p = decodeURIComponent(e);
				try {
					this.banner = JSON.parse(p);
				} catch (error) {
					this.banner = JSON.parse(p);
				}

				uni.setNavigationBarTitle({
					title: this.banner.title
				});

				this.getDetail();
			},
			getDetail() {
				uni.request({
					url: 'https://unidemo.dcloud.net.cn/api/news/36kr/' + this.banner.post_id,
					success: (result) => {
						let content = FAIL_CONTENT
						if (result.statusCode == 200) {
							content = result.data.content
						}
						const nodes = htmlParser(content);
						// #ifdef APP-PLUS-NVUE
						parseImgs(nodes)
						// #endif
						this.content = nodes
					}
				});
			}
		}
	}
</script>

<style>
	/* #ifndef APP-PLUS */
	page {
		min-height: 100%;
	}

	/* #endif */

	.banner {
		height: 180px;
		position: relative;
		background-color: #ccc;
		flex-direction: row;
		overflow: hidden;
	}

	.banner-img {
		flex: 1;
		filter: blur(10px);
		-webkit-filter: blur(10px);
	}

	.title-area {
		position: absolute;
		font-weight: bold;
		left: 15px;
		right: 15px;
		bottom: 15px;
		z-index: 11;
	}

	.title-text {
		font-size: 16px;
		font-weight: 400;
		line-height: 20px;
		lines: 2;
		color: $love-main-color;
		overflow: hidden;
	}


	.story-u-avatar {
		margin: 20rpx;
	}


	.article-content {

		font-size: 14px;
		padding: 0rpx 15px 0 15px;
		margin-bottom: 15px;
		overflow: hidden;
	}

	/* #ifdef H5 */

	.article-content {
		line-height: 1.8;
	}

	.article-content img {
		max-width: 100%;
	}

	/* #endif */
</style>
