<template>
  <div>
    <top-bar></top-bar>
    <div class="container">
      <!-- 搜索框 -->
      <div class="search-toolbar">
        <div class="so-toolbar">
          <div class="toolbar-main">
            <div class="search-box">
              <input type="text" placeholder="vue的常用指令" v-model="key" />
              <button id="search" @click="searchBlogs">搜索</button>
            </div>
          </div>
        </div>
      </div>
      <!-- 搜索展示 -->
      <div class="main">
        <!-- 搜索结果 -->
        <div class="main-lt">
          <div class="list-container">
            <BlogSearchArticleItem
              :searchkey="key"
              :blogsearchlist="blogSearchList"
            />
          </div>
        </div>

        <div class="main-rt">
          <div class="box related-list">
            <h3>全站热搜榜</h3>
            <ul>
              <li v-for="(item, index) in relatedList" :key="index" @click="relatedSearch(item)">
                <span>{{ index + 1 }}</span>
                <a>{{ item }}</a>
              </li>
            </ul>
          </div>
          <div class="box">关于我们-{{ key }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/content/topbar/TopBar";
import BlogSearchArticleItem from "@/views/blogSearch/childComps/BlogSearchArticleItem";

import InfiniteLoading from "vue-infinite-loading";

export default {
  components: {
    TopBar,
    BlogSearchArticleItem,
    InfiniteLoading,
  },
  data() {
    return {
      key: this.$store.state.searchKey,
      blogSearchList: [],
      relatedList: [
        "蓝桥杯历年真题",
        "计算机毕业设计",
        "微信小程序商城搭建",
        "数据可视化工具",
        "彻底搞懂背包问题",
        "队列的基本操作",
        "迷宫问题求解",
        "前端练手项目合集",
        "凯撒密码实现",
        "数据库从入门到精通",
      ],
    };
  },
  watch: {
    key(a, b) {
      //a是value的新值，b是旧值
      this.key = a;
    },
  },
  methods: {
    // 搜索博客
    searchBlogs() {
      // 状态保持清除后刷新页面
      window.location.reload();
      // 保存key
      this.$store.commit("copySearchKey", this.key);
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));

      this.$axios.get("/blog/search?key=" + this.key).then((res) => {
        console.log("加载后再次点击搜索", res);
        this.blogSearchList = res.data.data;
      });
    },
    // 点击热搜榜搜索博客
    relatedSearch(key) {
      this.key = key;
      // 状态保持清除后刷新页面
      window.location.reload();
      // 保存key
      this.$store.commit("copySearchKey", this.key);
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));
    }
  },
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  min-height: calc(100vh - 48px);
  background-color: #f2f2f2;
}

/* 搜索框开始 */
.so-toolbar {
  display: block;
  width: 100%;
  background: #fff;
  box-shadow: 0 4px 14px 0 rgb(0 0 0 / 5%);
  padding: 8px 0;
}
.so-toolbar .toolbar-main {
  width: 1382px;
  /* background-color: pink; */
  margin: 0 auto;
}
.so-toolbar .toolbar-main .search-box {
  width: 1034px;
  display: flex;
  background-color: palegreen;
}
.so-toolbar .toolbar-main .search-box input {
  flex: 1;
  line-height: inherit;
  padding: 8px 30px 8px 0;
  outline: 0;
  color: #000;
  font-size: 14px;
  text-indent: 16px;
  vertical-align: top;
  text-overflow: ellipsis;
  white-space: nowrap;
  border: 1px solid rgba(0, 0, 0, 0.06);
  border-radius: 2px 0 0 2px;
  background: #f5f6f7;
}
.so-toolbar .toolbar-main .search-box button {
  float: right;
  width: 120px;
  height: 44px;
  border: 0 none;
  background-color: #16a0f8;
  color: #ffffff;
  border-radius: 0 2px 2px 0;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.2s ease-in;
}
/* 搜索框结束 */

.main {
  width: 1382px;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding-top: 10px;
}

/* 左边博客开始 */
.main-lt {
  width: 1034px;
}
/*item*/

/* 左边博客结束 */

/* 右边推荐开始 */
.main-rt .box {
  width: 338px;
  background-color: #ffffff;
  margin-bottom: 10px;
  /* padding: 0 16px; */
}

/* 全站热搜榜开始 */
.related-list h3 {
  font-size: 18px;
  height: 48px;
  font-weight: 400;
  /* line-height: 48px; */
  padding: 12px 16px;
  border-bottom: 1px solid #f0f0f2;
}
.related-list ul {
  padding: 16px;
  list-style: none;
}
.related-list ul li {
  display: flex;
  width: 100%;
  max-width: 100%;
  align-items: center;
  margin-bottom: 16px;
}
.related-list ul li span {
  flex: 0 0 16px;
  text-align: center;
  font-size: 14px;
  margin-right: 8px;
}
.related-list ul li:nth-child(1) {
  color: #fe2d46;
}
.related-list ul li:nth-child(2) {
  color: #f60;
}
.related-list ul li:nth-child(3) {
  color: #faa90e;
}
.related-list ul li a {
  display: block;
  flex: 1;
  color: #222226;
  cursor: pointer;
}
.related-list ul li a:hover {
  color: #fc5531;
}
/* 全站热搜榜结束 */

/* 右边推荐结束 */
</style>
