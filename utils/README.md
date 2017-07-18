# 说明  

### statusbar：  
	设置状态栏工具类，设置状态栏颜色、透明等状态 适用于DrawerLayout 

### HtmlUtil.java：  
	根据url动态生成加载css、js标签  

### Divider.java
	一个封装的ItemDecoration，包括显示纯色divider、显示图片divider、divider的上下左右的间距、宽高设置
[深入浅出 RecyclerView](https://blog.kymjs.com/code/2016/07/10/01/ "开源实验室")

### RecyclerItemClickListener.java
	一个 RecyclerView 使用的 OnItemClickListener
[深入浅出 RecyclerView](https://blog.kymjs.com/code/2016/07/10/01/ "开源实验室")

### android-gif-drawable
	一个使了用jni的gif显示控件。优点：高效；缺点：使用jni，体积增大
[android-gif-drawable](https://github.com/koral--/android-gif-drawable "android-gif-drawable")

### GifImageView
	一个逐帧解析的gif显示控件。优点：只有几个类，体积很小；缺点：逐帧解析，较耗费系统资源，且速度慢。
[GifImageView](https://github.com/felipecsl/GifImageView "GifImageView")

### ParseImageType
	一个根据图片字节数组获取图片类型的方法
[支持gif的图片预览控件](https://blog.kymjs.com/code/2015/10/18/01/ "开源实验室")   

### gradle生成时间,精确到分

	static def releaseTime() {
    	return new Date().format("yyMMddHHmm", TimeZone.getTimeZone("Asia/Shanghai"))
	}
