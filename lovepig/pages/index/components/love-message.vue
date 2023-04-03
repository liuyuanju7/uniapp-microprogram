<template>
	<view class="message-wrapper">
		<view class="message-item" v-for="(msg, index) in misss" :key="index"
			:style="'transform:rotate('+$pig.base.random(-20, 20)+'deg);'">
			<view class="message-content">{{ msg.messageContent }}</view>
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
				misss: [{
					messageContent: '',
					userBase: {
						nickname: ''
					}
				}],
			};
		},
		computed: {
			...mapGetters(['getLoveStartId'])
		},
		created() {
			this.init();
		},
		methods: {
			...mapMutations(['modifyLastSendDate']),
			init() {
				//查询思念留言
				this.$u.api
					.queryMessageList({
						loveId: this.getLoveStartId,
						messageType: 1,
						pageSize: 10
					})
					.then(res => {
						console.info(res);
						this.misss = res.data;
						if (this.misss.length != 0) {
							this.modifyLastSendDate(new Date(this.misss[0].createTime));
						}

					});
			},
			generateNumber() {

			}
		}
	};
</script>
<style lang="scss" scoped>
	.message-wrapper {
		margin-top: 30rpx;
		display: flex;
		flex-wrap: wrap;
		text-align: center;
		justify-content: space-around;
		overflow: hidden;
	}

	.message-item {
		background-color: rgba(255, 255, 255, .9);
		padding: 20rpx 30rpx;
		border: 4rpx dashed lightpink;
		margin: 30rpx 50rpx;
		border-radius: 10rpx;
		// position: absolute;


	}
</style>