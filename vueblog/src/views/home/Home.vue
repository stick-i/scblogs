<template>
  <div class="container">
    <top-bar></top-bar>
    <swiper></swiper>
    <div class="content">
      <div class="article-left">
        <div class="article-main" v-for="item in 10">
          <div class="article-item">文章</div>
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
  }
</script>

<style>
    .content {
        margin: 0 auto 0 auto;
        max-width: 1500px;
        /*padding: 0 0.9rem;*/
        box-sizing: border-box;
        position: relative;

        display: flex;
        justify-content: space-between;
    }
    .content .article-left{
        width: 100%;
    }
.content .article-left .article-main{
    /*width: 100%;*/
    height: 150px;
    background-color:#f2f2f2;
    border-radius: 5px;
    margin-right: 15px;
    margin-bottom: 10px;
}
.content .resou {
    /*width: 1000px;*/
    /*width: 100%;*/
    box-sizing: border-box;
    width: 400px;
    height: 300px;
    background-color:#f2f2f2;
    border-radius: 5px;
}
</style>