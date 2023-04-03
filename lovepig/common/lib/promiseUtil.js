//承诺纪念日的工具类
const repeatList = [{
		value: '1O',
		label: '就一次'
	},
	{
		value: '1D',
		label: '每天'
	},
	{
		value: '1W',
		label: '每周'
	},
	{
		value: '1M',
		label: '每月'
	},
	{
		value: '1Y',
		label: '每年'
	}
];
const remindList = [{
		value: '-1',
		label: '不提醒'
	},
	{
		value: '0',
		label: '当天'
	},
	{
		value: '1',
		label: '提前一天'
	},
	{
		value: '3',
		label: '提前三天'
	},
	{
		value: '7',
		label: '提前一周'
	}
];
/**
 * 将承诺重复周期转成文字
 * 
 * @param {Object} repeat
 */
function promiseRepeatText(repeat) {
	// console.info(repeat);
	let data= repeatList.find(item => item.value == repeat)
	return data.label;
};
/**
 * 将提醒时间转换成文字
 * @param {Object} remind
 */
function promiseRemindText(remind) {
	// console.info(remind);
	let data= remindList.find(item => item.value == remind)
	// console.info(data);
	return data.label;
};
export default {
	repeatList,
	remindList,
	promiseRepeatText,
	promiseRemindText,
}
