<template>
  <div class="container">
    <top-bar></top-bar>
    <!--    <swiper></swiper>-->

    <div class="content">
      <div class="article-left">

        <div class="tabs">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="关注" name="first">关注</el-tab-pane>
            <el-tab-pane label="推荐" name="second">推荐</el-tab-pane>
            <el-tab-pane label="资讯" name="third">资讯</el-tab-pane>
            <el-tab-pane label="热榜" name="fourth">热榜</el-tab-pane>
          </el-tabs>
        </div>

        <div class="article-main" v-for="item in 10">
          <div class="article-item">
            <div class="article-title">
              <a href="#">
                <span>易烊千玺易烊千玺易烊千玺</span>
              </a>
            </div>
            <div class="article-content-item">
              <div class="article-img-left">
                <a href="#">
                  <img src="../../assets/img/home/003.jpg" alt="">
                </a>
              </div>
              <div class="article-content-right">
                <div class="article-content">
                  啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦
                </div>
                <div class="article-evaluation">
                  <div>赞 踩 作者：啦啦啦</div>
                </div>
              </div>
            </div>
          </div>
<!--          <hr>-->
        </div>
      </div>
      <div class="resou">
        <div class="">文章</div>
      </div>
    </div>
  </div>
</template>
<script>
  import TopBar from "../../components/content/topbar/TopBar"
  import Swiper from "@/components/common/swiper/Swiper";

  export default {
    name: "Home",
    components: {
      Swiper,
      TopBar
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
    .content {
        margin: 0 auto 0 auto;
        width: 1380px;
        box-sizing: border-box;
        position: relative;
        display: flex;
        justify-content: space-between;
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


    .content .article-left {
        width: 996px;
    }

    .content .article-left .article-main {
        /*width: 100%;*/
        /*height: 150px;*/
        /*background-color: #f2f2f2;*/
        border-radius: 5px;
        margin-right: 15px;
        /*margin-top: 10px;*/
        /*border-bottom: 1px solid #f2f2f2;*/
        /*padding-bottom: 15px;*/
        border-bottom: 1px solid #f0f0f2;
        padding: 15px 10px 0;
    }

    .content .resou {
        /*width: 1000px;*/
        /*width: 100%;*/
        box-sizing: border-box;
        width: 336px;
        height: 300px;
        /*background-color: #f2f2f2;*/
        border: 1px solid #f0f0f2;
        border-radius: 5px;
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

    .article-img-left {
        margin-right: 16px;
    }
    .article-img-left a {
        position: relative;
        border-radius: 2px;
        width: 134px;
        height: 84px;
        display: inline-block;
        background-size: cover;
        background-position: 50%;
        border: 1px solid #f5f6f7;
        overflow: hidden;
        background: #f5f6f7;
    }

    .article-img-left img {
        /*width: 132px;*/
        /*height: 100px;*/
        width: 100%;
        left: 50%;
        top: 50%;
        -webkit-transform: translate(-50%,-50%);
        transform: translate(-50%,-50%);
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
        flex-direction: column;
        justify-content: space-between;
        /*margin-top: 4px;*/
        font-size: 14px;
        font-weight: 400;
        min-height: 6px;
        min-height: 40px;
        color: #555666;
    }

    /*评价*/
    .article-evaluation {
        display: flex;
        align-items: center;
    }
</style>