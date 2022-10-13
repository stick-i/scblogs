<template>
    <div class="container">
      <TopBar></TopBar>
      <div class="header">
        <div class="left-header">用户创作榜</div>
        <div class="right-header">
          <img src="@/assets/img/long-logo.png" alt="" />
        </div>
      </div>
      <div class="nav">
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="当日榜" name="one">
            <One1></One1>
          </el-tab-pane>
          <el-tab-pane label="七日榜" name="seven">
            <Seven1></Seven1>
          </el-tab-pane>
          <el-tab-pane label="月榜" name="month">
            <Month1></Month1>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </template>
  
  <script>
  import TopBar from "@/components/content/topbar/TopBar.vue";
  import One1 from "@/components/P_user/RankingList1/One1.vue";
  import Seven1 from "@/components/P_user/RankingList1/Seven1.vue";
  import Month1 from "@/components/P_user/RankingList1/Month1.vue";
  export default {
    data() {
      return {
        activeName: "one",
      };
    },
    methods: {
    },
    created(){
      this.$store.commit("changeOne1","max");
      this.$store.commit("changeSeven1","max");
      this.$store.commit("changeMonth1","max");
    },
    components: { TopBar, One1, Seven1, Month1 },
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
  </style>