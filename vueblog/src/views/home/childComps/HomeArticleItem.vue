<template>
  <div>
    <div class="article-main" v-for="(item, index) in blogList" :key="index">
      <div class="article-item">
        <div class="article-title">
          <a href="#">
            <span>
              <router-link
                :to="{ name: 'BlogDetail', params: { blogId: item.id } }"
                target="_blank"
              >
                {{ item.title }}
              </router-link>
            </span>
          </a>
        </div>
        <div class="article-content-item">
          <div class="article-img-left">
            <router-link
              :to="{ name: 'BlogDetail', params: { blogId: item.id } }"
              target="_blank"
            >
              <img :src="item.coverImage" alt="" />
            </router-link>
          </div>
          <div class="article-content-right">
            <router-link
              :to="{ name: 'BlogDetail', params: { blogId: item.id } }"
              target="_blank"
            >
              <div class="article-content">
                {{ item.description }}
              </div>
            </router-link>
            <div class="article-evaluation">
              <div class="article-good" @click="addLikeNum(item.id, index)">
                <!--登录显示-->
                <img v-if="isShow"
                  :src="
                    userAction[item.id].like
                      ? require('../../../assets/img/home/good_active.png')
                      : require('../../../assets/img/home/good.png')
                  "
                  alt=""
                />
                <!--未登录显示-->
                <img v-if="!isShow" src="../../../assets/img/home/good.png" alt="">
                {{ item.likeNum }} <span>赞</span>
              </div>
              <div class="article-author">
                作者：<span>{{ item.author }}</span>
              </div>
              <div class="article-date">
                发布时间：<span>{{ item.releaseTime }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--infinite-loading这个组件要放在列表的底部，滚动的盒子里面-->
    <infinite-loading
      spinner="spiral"
      @infinite="infiniteHandler"
      :distance="200"
      class="infinite-loading-wrap"
    >
      <div slot="spinner">Loading...</div>
      <div slot="no-more">No more Data</div>
      <div slot="no-results">No results Data</div>
      <div slot="error" slot-scope="{ trigger }">
        Error Data, click
        <a href="javascript:;" @click="trigger">here</a> toretry
      </div>
    </infinite-loading>
  </div>
</template>

<script>
import qs from "qs";

import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "",
  data() {
    return {
      page: 1,
      blogList: [],
      blogIdForm: {
        blogId: "",
      },
      userAction: {},
      isLike:false,
      isShow:false,
    };
  },
  components: {
    InfiniteLoading,
  },
  mounted() {},
  computed:{
    // blogList1:function (){
    //   return this.blogList.filter(function (item) {
    //     return item.isShow
    //   })
    // }
  },
  methods: {
    // 点赞
    addLikeNum(id, index) {
      this.blogIdForm.blogId = id;
      this.$axios
        .post("/action/blog/like", qs.stringify(this.blogIdForm), {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          if(res.data.code == 400 && res.data.status == false) {
            this.$message({
              showClose: true,
              message: "请先登录哦~",
              type: "warning",
            });
          };
          if(res.data.code == 200 && res.data.status == true) {
            if (this.userAction[id].like == false) {
              this.blogList[index].likeNum++;
              this.userAction[id].like = true
            } else {
              this.blogList[index].likeNum--;
              this.userAction[id].like = false
            }
          }

        });
    },
    async infiniteHandler($state) {
      this.$axios
        .get("/blog/list?page=" + this.page, {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          // console.log(res.data.data.userAction)
          if(res.data.data.userAction == null) {
            this.isShow = false
          } else {
            this.isShow = true;
          }
          if (res.data.data.records.length) {
            this.page += 1; // 下一页
            this.blogList = this.blogList.concat(res.data.data.records);
            this.userAction = {
              ...this.userAction,
              ...res.data.data.userAction,
            };
            console.log(this.blogList);
            $state.loaded();
          } else {
            $state.complete();
          }
        });
    },
  },
};
</script>

<style scoped>
.article-main {
  border-radius: 5px;
  margin-right: 15px;
  border-bottom: 1px solid #f0f0f2;
  /*padding: 15px 10px 0;*/
  padding: 15px 0px 0;
}

.article-main:hover {
  background-color: #fafafa;
}

.article-title a {
  margin-bottom: 10px;
  font-size: 18px;
  font-weight: 700;
  color: #222226;
  overflow: hidden;
  white-space: normal;
  word-break: break-word;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  line-height: 25px;
}
.article-title a span:hover {
  /*color: pink;*/
  text-decoration: underline;
}

.article-img-left {
  margin-right: 16px;
}
.article-img-left a {
  position: relative;
  border-radius: 2px;
  width: 134px;
  height: 84px;
  display: inline-block;
  background-position: 50%;
  border: 1px solid #f5f6f7;
  overflow: hidden;
  background: #f5f6f7;
  background-image: url("../../../assets/img/home/003.jpg");
  background-size: cover;
}

.article-img-left img {
  /*width: 132px;*/
  /*height: 100px;*/
  width: 100%;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  height: auto;
  position: absolute;
}

/*图片文章并排显示*/
.article-content-item {
  display: flex;
  padding-bottom: 15px;
}
.article-content-right {
  display: flex;
  flex: 1;
  flex-direction: column;
  justify-content: space-between;
  /*margin-top: 4px;*/
  font-size: 14px;
  font-weight: 400;
  min-height: 6px;
  min-height: 40px;
  color: #555666;
}
.article-content-right .article-content {
  color: #555666;
  font-size: 14px;
  font-weight: 400;
  line-height: 22px;
}

/*评价*/
.article-evaluation {
  display: flex;
  align-items: center;
}
.article-evaluation img {
  width: 16px;
  height: 16px;
  margin-right: 3px;
}
.article-evaluation .article-good {
  display: flex;
  align-items: center;
  margin-right: 30px;
}
.article-evaluation .article-author {
  margin-right: 30px;
}
</style>
