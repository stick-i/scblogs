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
            <!-- <One1></One1> -->
            <Table :data="table" style="border-"></Table>
          </el-tab-pane>
          <el-tab-pane label="七日榜" name="seven">
            <!-- <Seven1></Seven1> -->
            <Table :data="table"></Table>
          </el-tab-pane>
          <el-tab-pane label="月榜" name="month">
            <!-- <Month1></Month1> -->
            <Table :data="table"></Table>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </template>
  
  <script>
import Table from "@/components/common/table/Table.vue";
import TopBar from "@/components/content/topbar/TopBar.vue";
  export default {
    data() {
      return {
        activeName: "one",
        table: {
        column: [
          {
            prop: "date",
            label: "日期",
            width: 180,
          },
          {
            prop: "name",
            label: "姓名",
            width: 180,
          },
          {
            prop: "address",
            label: "地址",
          },
        ],
        pageNum: 1,
        pageSize: 10,
        total: 6,
        data: [
          {
            date: "2016-05-02",
            name: "王小虎",
            address: "上海市普陀区金沙江路 1518 弄",
          },
          {
            date: "2016-05-04",
            name: "王小虎",
            address: "上海市普陀区金沙江路 1517 弄",
          },
          {
            date: "2016-05-01",
            name: "王小虎",
            address: "上海市普陀区金沙江路 1519 弄",
          },
          {
            date: "2016-05-03",
            name: "王小虎",
            address: "上海市普陀区金沙江路 1516 弄",
          },
          {
            date: "2016-05-03",
            name: "王小虎",
            address: "上海市普陀区金沙江路 1516 弄",
          },
          {
            date: "2016-05-03",
            name: "王小虎",
            address: "上海市普陀区金沙江路 1516 弄",
          },
          {
            date: "2016-05-03",
            name: "王小虎",
            address: "上海市普陀区金沙江路 1516 弄",
          },
        ],
      },
      };
    },
    methods: {
    },
    created(){
      this.$store.commit("changeRanking","max");
    },
    components: { Table, TopBar },
  };
  </script>
  
  <style scoped>
  .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color: beige;
  }
  .header {
    width: 1080px;
    height: 200px;
    display: flex;
    justify-content: space-around;
    /* margin-left: 80px; */
  }
  .header .left-header {
    width: 200px;
    height: 200px;
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
    background-color: blue;
  }
  .nav {
    width: 1080px;
    height: 100%;
    margin-top: 10px;
  }
  </style>