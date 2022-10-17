<template>
    <div class="container">
      <TopBar></TopBar>
      <div class="header">
        <div class="left-header">博客热度排行榜</div>
        <div class="right-header">
          <img src="@/assets/img/long-logo.png" alt="" />
        </div>
      </div>
      <div class="nav">
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="当日榜" name="one">
            <One2></One2>
          </el-tab-pane>
          <el-tab-pane label="七日榜" name="seven">
            <Seven2></Seven2>
          </el-tab-pane>
          <el-tab-pane label="月榜" name="month">
            <Month2></Month2>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </template>
  
  <script>
  import TopBar from "@/components/P_user/TopBar/TopBar.vue";
  import One2 from "@/components/P_user/RankingList2/One2.vue";
  import Seven2 from "@/components/P_user/RankingList2/Seven2.vue";
  import Month2 from "@/components/P_user/RankingList2/Month2.vue";
  export default {
    data() {
      return {
        activeName: "one",
      };
    },
    methods: {},
    created() {
      this.$store.commit("changeRanking", "max");
    },
    components: { TopBar, One2, Seven2, Month2 },
  };
  </script>
  
  <style scoped>
  * {
    margin: 0;
    padding: 0;
  }
  .container {
    width: 100%;
    height: 100%;
  }
  .header {
    width: 1080px;
    height: 200px;
    margin: 20px auto;
  }
  .header .left-header {
    width: 200px;
    height: 200px;
    float: left;
    background-color: palevioletred;
    border-radius: 15px;
    color: #fff;
    font-size: 28px;
    /* 垂直居中 */
    line-height: 200px;
    /* 水平居中 */
    text-align: center;
  }
  .header .right-header {
    width: 860px;
    height: 200px;
    float: right;
    background-color: blue;
  }
  .nav {
    width: 1080px;
    height: 100%;
    margin: 20px auto;
  }
  /* .main {
    width: 1080px;
    height: 100px;
    margin: 20px auto;
    background-color: aqua;
  } */
  </style>