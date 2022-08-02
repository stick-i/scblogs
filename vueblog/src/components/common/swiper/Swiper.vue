<template>
  <div class="swiper">
    <!-- 右边轮播图 -->
    <div class="main-right">
      <el-carousel indicator-position="outside" height="380px">
        <el-carousel-item v-for="(item,index) in 6" :key="item">
					<router-link
						:to="{ name: 'BlogDetail', params: { blogId: blogList[index].id } }"
						target="_blank"
					>
					<img class="swiperImg" :src="blogList[index].coverImage?blogList[index].coverImage:require('../../../assets/img/home/003.jpg')" alt="" />
					</router-link>
				</el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
	data(){
		return {
			page: 1,
			blogList:[],
		}
	},
	created(){
		this.getData()
	},
	methods:{
		getData(){
			this.$axios
				.get("/blog/list/recommend?page=" + this.page, {
					headers: { token: localStorage.getItem("token") },
				})
				.then((res) => {
					if (res.data.data.records.length) {
						this.blogList = this.blogList.concat(res.data.data.records);
					}
				});
		}
	}
};
</script>

<style scoped>
/* 轮播图开始 */
.main-right {
  /*width: 510px;*/
  width: 100%;
}
.swiperImg{
	width:100%;
	height: 100%;
}
/* 轮播图结束 */
</style>
