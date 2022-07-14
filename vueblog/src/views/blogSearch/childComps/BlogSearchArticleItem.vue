<template>
  <div>
    <!--{{key}}-->
    <!-- 文章item -->
    <div class="list-item" v-for="(item, index) in blogSearchList" :key="index">
      <div class="item">
        <!-- 标题 -->
        <div class="item-hd">
          <h3 class="title">
            <router-link
              :to="{ name: 'BlogDetail', params: { blogId: item.id } }"
              target="_blank"
            >
              <span id="max" v-html="item.title" ></span>
            </router-link>
          </h3>
        </div>
        <!-- 摘要、浏览、图片 -->
        <div class="item-bd">
          <div class="item-bd_cont">
            <div class="bdc-lt">
              <!-- 摘要 -->
              <p class="row1" v-html="item.description"></p>
              <!-- 浏览 -->
              <div class="row2">
                <div class="row2-lt">
                  <!-- 浏览量 -->
                  <img
                    class="info-img"
                    src="../../../assets/img/blogSearch/view.png"
                    alt=""
                  />
                  <div class="info">{{ item.viewNum }}</div>
                  <!-- 点赞量 -->
                  <img
                    class="info-img"
                    src="../../../assets/img/blogSearch/good.png"
                    alt=""
                  />
                  <div class="info">{{ item.likeNum }}</div>
                  <!-- 评论量 -->
                  <div>评论量无</div>
                </div>
                <div class="row2-rt">
                  <div class="author">{{ item.author }}</div>
                  <span>{{ item.releaseTime }}</span>
                </div>
              </div>
            </div>
            <div class="bdc-rt">
              <!-- 封面 -->
              <div class="img-slider">
                <img :src="item.coverImage" alt="" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 下拉刷新开始 -->
    <infinite-loading
      spinner="spiral"
      @infinite="infiniteHandler"
      :distance="200"
      class="infinite-loading-wrap"
    >
      <div slot="spinner" style="color: #999aaa;margin:10px 0;">Loading...</div>
      <div slot="no-more" style="color: #999aaa;margin:10px 0;">暂无更多数据</div>
      <div slot="no-results" style="color: #999aaa;margin:10px 0;">抱歉，没有找到相关内容</div>
      <div slot="error" slot-scope="{ trigger }">
        Error Data, click <a href="javascript:;" @click="trigger">here</a> toretry
      </div>
    </infinite-loading>
    <!-- 下拉刷新开始 -->
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading'

export default {
	name: "",
	props: ["searchkey", "blogsearchlist"], //接收searchkey值
	data() {
		return {
			page: 1,
			key: this.searchkey,
			blogSearchList: [],
			// blogSearchList: this.blogsearchlist,
		};
	},
	components: {
		InfiniteLoading
	},
	watch: {
		searchkey(a, b) {
			//a是value的新值，b是旧值
			this.key = a;
		},
    blogsearchlist(a, b) {
      this.blogSearchList = a;
    },
  },
  created() {
    // this.goApi();
  },
  methods: {
    // goApi() {
    //   this.$axios.get("/blog/search?key=" + this.key).then((res) => {
    //     console.log("数据第一次加载", res);
    //     this.blogSearchList = res.data.data;
    //   });
    // },
    infiniteHandler($state) {
      this.$axios
        .get("/blog/content/search",{params:{key:this.key,page:this.page}},{
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res)
          if(res.data.data.records.length) {
            this.page +=1;  // 下一页
            this.blogSearchList = this.blogSearchList.concat(res.data.data.records);
            console.log("下拉刷新",this.blogSearchList)
            // console.log(this.blogSearchList)
            $state.loaded();
          }else {
            $state.complete();
          }
        })
    },
  },
};
</script>

<style scoped>
/* item开始 */
.list-item {
  border-bottom: 1px solid #ededed;
  background: #fff;
}
.list-item .item {
  padding: 16px 24px;
}
.item .item-hd {
  width: 100%;
  line-height: 22px;
  margin-bottom: 4px;
}
.item-hd .title {
  font-size: 18px;
  vertical-align: top;
}
.item-hd .title span {
  color: #222226;
}
.item-hd .title span:hover {
  color: #fc5531;
}
.item-bd .item-bd_cont {
  display: flex;
  align-items: flex-start;
  flex-shrink: 0;
  color: #555666;
  line-height: 24px;
  overflow-x: auto;
}
.item-bd .item-bd_cont .bdc-lt {
  min-height: 74px;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.item-bd .item-bd_cont .bdc-lt .row1 {
  color: #999aaa;
}
.item-bd .item-bd_cont .bdc-lt .row2 {
  margin-top: 8px;
  display: flex;
  height: 20px;
  justify-content: space-between;
  align-items: center;
  color: #999aaa;
  line-height: 16px;
}
.item-bd .item-bd_cont .bdc-lt .row2 .row2-lt {
  display: flex;
  /* align-items: center; */
}
.item-bd .item-bd_cont .bdc-lt .row2 .row2-lt .info-img {
  width: 16px;
  height: 16px;
  margin-right: 6px;
}
.item-bd .item-bd_cont .bdc-lt .row2 .row2-lt .info {
  margin-right: 20px;
}
.item-bd .item-bd_cont .bdc-lt .row2 .row2-rt {
  display: flex;
}
.item-bd .item-bd_cont .bdc-lt .row2 .row2-rt .author {
  width: 100px;
}

.item-bd .item-bd_cont .bdc-rt {
  position: relative;
  width: 128px;
  height: 74px;
  margin-left: 24px;
  background-color: skyblue;
}
.item-bd .item-bd_cont .bdc-rt .img-slider {
  width: 100%;
  height: 100%;
}
.item-bd .item-bd_cont .bdc-rt .img-slider img {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
  max-height: none;
  margin: 0 auto;
  background-image: url("../../../assets/img/home/003.jpg");
  background-size: cover;
}

>>> .item em {
	color: #f73131;
	font-style: normal;
}
/* item结束 */
</style>
