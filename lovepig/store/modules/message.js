import {
	DateUtil,
	format
} from "../../common/DateUtil"

export default {
	state: {
		lastSendDate: '',
	},
	getters: {
		isTodayend: state => {
			// console.info(state.lastSendDate)
			return state.lastSendDate === format(new Date(), DateUtil.yyyyMMdd)
		},
	},
	mutations: {
		modifyLastSendDate(state, payload) {
			state.lastSendDate = format(payload, DateUtil.yyyyMMdd);
		},
	},
	actions: {

	}
}