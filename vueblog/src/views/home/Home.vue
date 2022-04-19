<template>
  <div class="container">
    <top-bar class="top-bar"></top-bar>
    <div class="top-content">
      <div class="main">123</div>
    </div>
<!--        <swiper></swiper>-->

    <div class="content">
      <!-- 左边-->
      <div class="article-left">
        <div class="tabs">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="关注" name="first">
              <article-item v-for="item in 3"></article-item>
            </el-tab-pane>
            <el-tab-pane label="推荐" name="second">
              <article-item v-for="item in 5"></article-item>
            </el-tab-pane>
            <el-tab-pane label="资讯" name="third">资讯</el-tab-pane>
            <el-tab-pane label="热榜" name="fourth">热榜</el-tab-pane>
          </el-tabs>
        </div>
      </div>
      <!-- 右边-->
      <div class="article-right">
<!--        卡片1-->
        <div class="template">
          <div class="template-title">
            <span>作者推荐</span>
          </div>
          <div class="template-cont">
            <div class="recommendation" v-for="item in 5">
              <a target="_blank" href="#" class="recommendation-img">
                <img src="../../assets/img/home/default_avatar.jpg" alt="">
              </a>
              <div class="recommendation-cont">
                <div><a target="_blank" href="#">大哥</a></div>
                <div class="intro">此人很懒，什么都没有写</div>
              </div>
              <div class="recommendation-btn">
                关注
              </div>
            </div>
          </div>
        </div>
<!--        卡片2-->
        <div class="template">
          <div class="template-title">
            <span>热门推荐</span>
          </div>
          <div class="template-cont">
            <div class="hot" v-for="item in 5">
              <a target="_blank" href="#" class="hot-img">
                <img src="../../assets/img/home/003.jpg" alt="">
              </a>
              <div class="hot-cont">
                <a target="_blank" href="#">
                  <div>【JS】求斐波那契数列中的奇数之和</div>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import TopBar from "../../components/content/topbar/TopBar"
  import Swiper from "@/components/common/swiper/Swiper";
  import ArticleItem from "@/components/content/home_article/ArticleItem";

  export default {
    name: "Home",
    components: {
      Swiper,
      TopBar,
      ArticleItem
    },
    data() {
      return{
        activeName: 'second'
      }
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      }
    }
  }
</script>

<style>
    .top-content {
        width: 100%;
        height: 300px;
        background-color: hsla(0,0%,98%,.8);
        /*background-color: #fc5531;*/
    }
    .top-content .main {
        width: 1380px;
        margin: 0 auto;
        /*background-color: #fff;*/
    }


    /*右边卡片*/
    .content {
        margin: 0 auto 0 auto;
        width: 1380px;
        box-sizing: border-box;
        position: relative;
        display: flex;
        justify-content: space-between;
        padding-top: 16px;
    }

    .content .template {
        box-sizing: border-box;
        width: 336px;
        /*height: 300px;*/
        border: 1px solid #f0f0f2;
        border-radius: 5px;
        margin-bottom: 16px;
    }

    .template-title {
        height: 48px;
        padding: 0 24px;
        font-size: 18px;
        font-weight: 500;
        line-height: 48px;
        color: #222226;
        border-bottom: 1px solid #e8e8ed;
    }

    .template-cont {
        padding: 24px 24px 8px;
    }

    /*右边卡片2*/
    .hot {
        display: flex;
        align-items: center;
        height: 58px;
        margin-bottom: 16px;
    }
    .hot img {
        width: 100px;
        height: 58px;
        -o-object-fit: cover;
        object-fit: cover;
        border-radius: 2px;
        margin-right: 8px;
    }
    .hot .hot-cont {
        /*margin-top: 6px;*/
        font-size: 14px;
        font-weight: 400;
        color: #222226;
        line-height: 22px;
    }
    .hot .hot-cont div {
        color: #222226;
    }

    /*右边卡片1*/

     .recommendation {
        display: flex;
        align-items: center;
        height: 44px;
        font-size: 14px;
        margin-bottom: 16px;
    }
     .recommendation recommendation-img {
        position: relative;
    }
     .recommendation img {
        width: 44px;
        height: 44px;
        border-radius: 22px;
        border: 1px solid #e8e8ed;
        margin-right: 8px;
    }
    .recommendation .recommendation-cont {
        flex: 1;
    }
    .recommendation .recommendation-cont a {
        font-weight: 400;
        color: #222226;
    }
    .recommendation-cont .intro {
        font-size: 12px;
        color: #999aaa;
    }
    .recommendation .recommendation-btn {
        cursor: pointer;
        text-align: center;
        width: 48px;
        height: 24px;
        background: #fff;
        line-height: 24px;
        border-radius: 12px;
        border: 1px solid #ccccd8;
        font-size: 12px;
        margin-left: 16px;
    }

    /*tabs标签页*/
    .tabs .el-tabs__item {
        padding: 0 28px;
        color: #999aaa;
    }
    .tabs .el-tabs__item:hover {
        color: #222226;
    }
    .tabs .el-tabs__item.is-active {
        color: #222226;;
    }
    .tabs .el-tabs__active-bar {
        background-color: #222226;
    }
    .tabs .el-tabs__nav-wrap::after {
        height: 1px;
    }
    .tabs .el-tabs--top {
        padding-left: 10px;
    }

    .content .article-left {
        width: 996px;
    }


</style>