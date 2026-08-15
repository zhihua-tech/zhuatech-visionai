/* Copyright 2026 上海如静知华信息科技有限公司 */
export const domain={
 code:'VISIONAI',systemName:'知华机器视觉质检平台',englishName:'AI VISUAL INSPECTION',theme:{primary:'#4b556b',dark:'#263041',accent:'#d09246'},
 workspace:'质量中心 / 视觉质量组',fieldWorkspace:'二号工厂 / 终检工位',period:'2026-08-15 · 生产实时',liveText:'工位状态于 10:38 更新',fieldContextLabel:'当前批次',fieldContext:'LOT-260815-A17',fieldUser:'叶宁',fieldRole:'视觉质检员',adminUser:'顾清',adminRole:'质量平台主管',
 adminTitle:'视觉质量运营中心',adminBreadcrumb:'机器视觉 / 全线质量',adminSubtitle:'统一管理视觉工位、检测模型、缺陷门槛与人工复核，确保每次判定可追溯。',exportAction:'导出质量日报',createAction:'新建检测任务',
 chartTitle:'自动判定覆盖趋势',chartSubtitle:'实际覆盖率 / 质量目标',chartLabels:['08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00'],loadTitle:'产线缺陷负荷',loadSubtitle:'待复核样本占质检能力',recordsTitle:'重点视觉质检任务',recordsSubtitle:'按关键缺陷、低置信和交付批次排序',issueTitle:'需要关注的质量事件',issueSubtitle:'涉及隔离、模型漂移或工位异常',
 recordName:'质检任务',itemName:'检测方案',unitName:'质量班组',batchName:'模型版本',planName:'样本',doneName:'已判定',exceptionName:'缺陷',unitLabel:'件',
 listBreadcrumb:'视觉质量 / 质检任务',listSubtitle:'管理图像采集、模型判定、人工复核、批次隔离和质量放行。',listSummary:[['视觉工位','18'],['今日检测','12,860'],['待复核','46',true],['自动判定','96.4%']],tabs:['全部','待处理','进行中','待确认','已归档'],
 fieldBreadcrumb:'质检工作台 / 终检工位',fieldTitle:'视觉复核工作台',fieldSubtitle:'当前队列 18 件 · 关键缺陷 2 件 · 平均复核 42 秒',fieldSecondary:'查看缺陷图谱',reportAction:'提交复核结论',fieldNoticeTitle:'相机与光源稳定',fieldNotice:'采集完整率 99.7%',
 steps:['图像采集','模型推理','规则门禁','人工复核','批次放行'],documentAction:'查看检验规范',printAction:'打印隔离标签',resourceCardTitle:'视觉资源状态',resourceValueLabel:'今日推理量',resourceHealthLabel:'工位健康度',quickSubtitle:'常用质检入口',
 quickActions:[['样本复核','/shopfloor/report','缺陷位置、类别与证据'],['检测方案','/shopfloor/material','产品、工位和质量门槛'],['视觉工位','/shopfloor/resources','相机、光源和模型版本'],['质量升级','/shopfloor/andon','关键缺陷与批次隔离']],
 reportDefaults:[6,1],reportTitle:'视觉复核反馈',reportSubtitle:'记录接受、驳回、缺陷类型和处置意见。',reportSuccess:'视觉质检反馈已提交',reportPlaceholder:'填写缺陷位置、判定依据、复核结论和处置要求',reportFootnote:'关键缺陷结论需质量工程师二次确认',ruleTitle:'视觉判定质量门禁',ruleSubtitle:'VISION-AI · V1.0',rules:[['关键缺陷','禁止自动放行'],['低置信样本','进入人工复核'],['模型升级','灰度评测后发布'],['误判复盘','每日',true]],fieldTotals:[['18','待复核样本'],['2','关键缺陷'],['42秒','平均复核'],['99.7%','采集完整率']],
 adminMenus:[['/admin','home','视觉质量中心'],['/admin/work-orders','order','质检任务'],['/admin/samples','box','检测方案'],['/admin/schedule','calendar','批次计划'],['/admin/methods','process','缺陷规则'],['/admin/reviews','quality','样本复核'],['/admin/resources','machine','视觉工位'],['/admin/report','chart','质量分析']],
 fieldMenus:[['/shopfloor','home','复核工作台'],['/shopfloor/report','report','样本复核'],['/shopfloor/tasks','order','我的队列'],['/shopfloor/material','box','检测方案'],['/shopfloor/resources','machine','工位状态'],['/shopfloor/andon','risk','质量升级',2]],
 moduleTitles:{tasks:['我的复核队列','查看置信度、缺陷和批次时限'],material:['检测方案台账','查看产品、缺陷字典和放行门槛'],resources:['视觉工位中心','管理相机、光源、边缘节点和模型'],andon:['质量事件升级','提交关键缺陷、批次隔离和工位故障'],samples:['检测方案台账','维护产品版本、视角和质量规则'],schedule:['批次质检计划','协调产线、切型和模型准备'],methods:['缺陷规则','维护缺陷等级、阈值与路由'],reviews:['样本复核','保留人工判定和模型差异'],report:['视觉质量分析','分析误判、漏判和缺陷趋势']},
 tagline:'让每一张质检图像，都对应清晰的质量结论',storyTitle:'模型负责发现异常，<br/>质量人员负责最终放行',storyText:'把机器视觉的速度与人工质量判断结合起来，保留图像、模型、规则和复核证据。',pattern:[2,4,7,10,13,16,19,22,25,28,31],loginStats:[['18','视觉工位'],['96.4%','自动判定率'],['46','待复核样本']],loginTitle:'视觉质量运营中心',adminDemo:'工位 / 模型 / 质量',fieldDemo:'样本 / 复核 / 放行'
}
export const records=[
 {no:'VI-260815-018',name:'铝壳表面划伤检测',code:'CASE-SCRATCH',unit:'终检一组',group:'二号工厂',plan:3200,done:2860,exception:28,due:'10:50',batch:'surface-v3.2',status:'进行中',progress:89,priority:'加急'},
 {no:'VI-260815-021',name:'PCB 焊点完整性检测',code:'PCB-SOLDER',unit:'电子质量组',group:'一号工厂',plan:1800,done:1216,exception:17,due:'11:30',batch:'solder-v2.8',status:'待确认',progress:68,priority:'关注'},
 {no:'VI-260815-026',name:'标签字符与条码校验',code:'LABEL-OCR',unit:'包装质量组',group:'物流中心',plan:4600,done:980,exception:11,due:'14:00',batch:'ocr-v4.1',status:'待处理',progress:21,priority:'加急'},
 {no:'VI-260814-015',name:'密封圈缺口检测',code:'SEAL-GAP',unit:'过程质量组',group:'三号工厂',plan:2400,done:2400,exception:8,due:'08-14',batch:'seal-v2.4',status:'已归档',progress:100,priority:'正常'},
 {no:'VI-260815-031',name:'注塑件色差与缩水',code:'MOLD-COLOR',unit:'来料质量组',group:'供应质量',plan:960,done:620,exception:14,due:'15:30',batch:'mold-v1.9',status:'进行中',progress:65,priority:'关注'}
]
export const resources=[{code:'CAM-01',name:'二号线面阵相机组',unit:'视觉工程组',status:'运行中',health:99,value:'8',valueUnit:'台',note:'曝光和焦距保持稳定'},{code:'EDGE-02',name:'边缘推理节点集群',unit:'AI 平台组',status:'运行中',health:96,value:'38',valueUnit:'ms',note:'P95 推理时延低于门槛'},{code:'MODEL-03',name:'表面缺陷模型',unit:'质量算法组',status:'预警',health:88,value:'3.2',valueUnit:'版',note:'深色外壳误报率上升 1.8%'}]
export const reviews=[{no:'RV-260815-032',title:'铝壳关键划伤样本 #A1842',type:'放行复核',detail:'置信度 97% · 顾清',result:'待确认'},{no:'RV-260815-011',title:'PCB 虚焊样本 #P0931',type:'人工复核',detail:'3 个焊点 · 叶宁',result:'通过'},{no:'RV-260814-018',title:'深色外壳反光误报',type:'模型漂移',detail:'近两小时 26 件',result:'异常'}]
export const adminMetrics=[['今日检测','12,860','较昨日同期 +8.2%','blue'],['自动判定率','96.4%','人工复核 46 件','green'],['关键缺陷','7','已隔离 3 个批次','red'],['工位可用率','98.8%','1 个光源待维护','orange']]
export const fieldMetrics=[['复核队列','18','2 件关键缺陷','blue'],['今日已复核','74','一致率 93.2%','green'],['质量升级','2','等待工程师确认','orange'],['采集完整率','99.7%','无图像丢失','slate']]
export const chartActual=[89,91,92,93,94,95,95,96,96],chartTarget=[92,92,93,93,94,95,96,97,97]
export const loads=[['铝壳外观线',84,'待复核 18 件'],['电子焊接线',72,'待复核 12 件'],['包装识别线',66,'待复核 9 件'],['注塑来料线',52,'待复核 7 件']]
export const issues=[['质量','铝壳批次发现 2 件关键划伤','批次已自动转入隔离区','待确认'],['模型','深色外壳误报率连续上升','建议回退到稳定阈值','处理中'],['设备','包装线三号光源亮度衰减','计划午休窗口更换','已排期']].map(x=>({type:x[0],title:x[1],detail:x[2],status:x[3]}))
