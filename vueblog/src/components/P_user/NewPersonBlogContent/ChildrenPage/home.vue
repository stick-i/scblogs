<template>
	<div class="contentright">
		<div class="F-1"
			 v-for="(item,index) in List"
			 :key="item.id"
			 @click="TurnToShow(item.id)">

			<div class="BlogContent-a">
				<div class="BlogContent-1">{{ item.title }}</div>
				<div class="BlogContent-2">{{ item.description }}</div>
				<div class="BlogContent-3">
					{{ item.releaseTime }}
					<span>{{ item.viewNum }}阅读 .</span>
					<span>{{ item.likeNum }}点赞 .</span>
					<span>{{ item.commentNum }}评论 .</span>
					<span>{{ item.collectionNum }}收藏</span>
				</div>
			</div>
			<div ref="frame" v-if="item.coverImage!=null && item.coverImage.trim().length > 10"
				 class="BlogContent-image">
				<img :src="item.coverImage" >
				<!--				:class="GetSuitableCrop(item.coverImage)"-->
			</div>
		</div>
		<!-- 给下拉刷新预留位置 -->
		<infinite-loading
			spinner="spiral"
			@infinite="infiniteHandler"
			:distance="200"
			class="infinite-loading-wrap">
			<div slot="spinner">别催，正在加载中，等会儿！</div>
			<div slot="no-more">别划了，到底了^_^</div>
			<div slot="no-results">看啥看，你啥都没写！</div>
			<div slot="error" slot-scope="{ trigger }">
				Error Data, click <a href="javascript:;" @click="trigger">here</a> toretry
			</div>
		</infinite-loading>
	</div>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";

export default {
	name: 'Home',
	components:{
		InfiniteLoading,
	},
	data() {
		return {
			//博客列表显示部分
			List: [],
			config: {
				params: {status: "0", page: "0"},
				headers: {
					'token': localStorage.getItem('token')
				}
			},
		}
	},
	async created() {
		await this.GetData()
		console.log("获取到的博客列表数据", this.List)
	},
	mounted() {

	},
	methods: {
		// 初始化获取数据
		async GetData() {
			this.List = []
			await this.$axios.get("/blog/list", this.config).then(res => {
				this.List = res.data.data.records
				// 将传参的页数标记增大1
				this.config.params.page++
			})
		},
		// 底部刷新函数
		async infiniteHandler($state) {
			// 个人博客列表数据获取
			await this.$axios
				.get("/blog/list", this.config)
				.then((res) => {
					if (res.data.data.records.length > 0) {
						this.config.params.page++;  // 下一页
						this.List = this.List.concat(res.data.data.records);
						$state.loaded();
					} else {
						$state.complete();
					}
				})
		},
		// 跳转去显示博客的详情
		TurnToShow(id) {
			var routeUrl = this.$router.resolve({name: 'BlogDetail', params: {blogId: id}})
			window.open(routeUrl.href, '_blank');
		},
		//获取图片宽高
		GetSuitableCrop(imgurl) {
			// 创建实例对象
			let img = new Image()
			// 图片地址
			img.src = imgurl
			// 打印
			console.log("捕获到的元素", this.$refs)
			console.log("this元素", this.imgheight,this.imgwidth)
			return (img.width / this.$refs.frame.offsetWidth) > (img.height / this.$refs.frame.offsetHeight) ? 'imgA' : 'imgB';
		},
	}
}
</script>
<style scoped>
.contentright {
	width: 990px;
	/* overflow: hidden; */
}

.contentright .F-1 {
	width: 100%;
	height: 200px;
	margin-bottom: 20px;
	border-radius: 5px;
	padding: 20px 20px;
	display: flex;
	align-items: center;
	background: white;
	transition: all 0.15s linear;
	cursor: pointer;
	overflow: hidden;
}

.contentright .F-1:hover {
	transform: scale(1.01);
	background: rgb(247, 247, 247);
}

.contentright .F-1 .BlogContent-image {
	width: 200px;
	height: 150px;
	border-radius: 10px;
	overflow: hidden;
	display: flex;
	box-sizing: border-box;
}
.contentright .F-1 .BlogContent-image img {
	width: 100%;
	/*height: auto;*/
	object-fit: cover;
}
.contentright .F-1 .BlogContent-image imgB {
	height: 100%;
}
.contentright .F-1 .BlogContent-a {
	width:750px;
	height: 150px;
}

.contentright .F-1 .BlogContent-a .BlogContent-1 {
	/* width: 100%; */
	height: 60px;
	line-height: 60px;
	font-size: 28px;
	font-weight: 600;
	color: black;
}

.contentright .F-1 .BlogContent-a .BlogContent-2 {
	/* width: 100%; */
	min-height: 40px;
	line-height: 30px;
	font-size: 20px;
	font-weight: 500;
	color: black;
}

.contentright .F-1 .BlogContent-a .BlogContent-3 {
	/* width: 100%; */
	height: 50px;
	line-height: 50px;
	font-size: 18px;
	font-weight: 400;
	color: black;
}

.contentright .F-1 .BlogContent-a .BlogContent-3 span {
	margin: 0 10px;
}
</style>
