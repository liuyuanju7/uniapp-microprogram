<template>
	<view>
		<view @click.stop="maskTouchend()">
			<love-btn></love-btn>
		</view>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapState,
		mapMutations
	} from 'vuex';
	export default {
		data() {
			return {
				showAddpopup: false,
				touchNum: 0,
				touchTimer: null,
				misss: [{
					messageContent: '',
					userBase: {
						nickname: ''
					}
				}],
				missPage: 1,
				missData: {
					loveId: this.getLoveStartId,
					messageContent: '想你',
					messageType: 1,
				},
				showStoryPopup:false
			};
		},
		onLoad() {
		},
		onShow() {

		},
		computed: {
			...mapGetters(['getLoveStartId', 'getLoginUser'])
		},
		methods: {
			...mapMutations(['modifyLastSendDate']),
			maskTouchend(e) {
				let that = this;
				this.touchNum++;
				if (this.touchTimer) {
					return;
				}
				this.touchTimer = setTimeout(() => {
					if (this.touchNum == 1) {
						console.log('单击');
						that.$root.$refs.addStoryPopup.showStoryPopup = true;
					}
					if (this.touchNum > 1) {
						console.log('双击');
						that.fastSendMiss();
					}
					this.touchNum = 0;
					clearTimeout(this.touchTimer);
					this.touchTimer = null;
				}, 200);
			},
			fastSendMiss: function() {
				if (!this.$pig.user.isLogin()) {
					return;
				}
				this.missData.loveId = this.getLoveStartId;
				//保存思念留言
				this.$u.api.saveMessage(this.missData).then(res => {
					this.modifyLastSendDate(new Date());
					this.$root.$refs.uToast.show({
						title: '思念脑电波发送成功!',
						type: 'error '
					});
				});
			},
		}
	};
</script>
<style lang="scss" scoped></style>