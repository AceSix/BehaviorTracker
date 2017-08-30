select
shop.ShopName,
shoplocation.longitude,
shoplocation.latitude
from shoplocation
INNER JOIN shop
on shoplocation.ShopID=shop.ShopID
where shop.CITY like "北京%"
and shop.ShopID like "AD%"

INSERT INTO ShopComment(score,deliveryTime,shopname,shopid,label,userName,deliveryTime0,commentTime,mtWmPoiId,content,praiseDish)
VALUES(2, 124, "遇见榴莲千层蛋糕专门店", "16985157", "难吃", "WPI854055419","2小时4分钟送达", "2017-2-7", "664207", "超级超级超级难吃！千万别买！什么玩意啊，还叫lady m？！亏你好意思写这个名字？自己多**自己不知道吗？真的是相当难吃！那些好评都是刷出来的吧？这东西能吃？还什么跟美国lady m一样？**！我爸妈吃了都说以后不要买这种劣质蛋糕店的东西，再食物中毒了，也太难吃了吧，奉劝各位，看见这个名字吸引过来的都别上当了，跟lady m*关系没有，蛋糕盒子盘子什么的都是不知道哪儿买的破东西，lady m的标也没有，所以要是为了照相也没必要了，一无是处的**玩意，全家一人尝了一口就扔了，奶油肯定也是劣质奶油，这玩意是违规生产的吧？符合国家安检规定吗？", null)