<template>
  <div class="container">
    <div class="header">
      <span style="color:aqua">用户创作排行榜</span>
      <button @click="changeIsShow1" class="button1 active">当日排行</button>
      <button @click="changeIsShow2" class="button1">七日排行</button>
      <button @click="changeIsShow3" class="button1">月排行</button>
    </div>
    <div class="main" style="height: 100%">
      <!-- <One1 v-if="isshow == 'one'"></One1>
      <Seven1 v-else-if="isshow == 'seven'"></Seven1>
      <Month1 v-else-if="isshow == 'month'"></Month1> -->
      <Table :data="table"></Table>
    </div>
    <router-link to="/RankingListPlus1">查看更多&nbsp;></router-link>
  </div>
</template>

<script>
import Table from "@/components/common/table/Table.vue";
// import One1 from "@/components/P_user/RankingList1/One1.vue";
// import Seven1 from "@/components/P_user/RankingList1/Seven1.vue";
// import Month1 from "@/components/P_user/RankingList1/Month1.vue";
export default {
  data() {
    return {
      isshow: "one",
      table: {
        column: [
          {
            prop: "date",
            label: "日期",
            width: 100,
          },
          {
            prop: "name",
            label: "姓名",
            width: 100,
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
            address: "上海市",
          },
          {
            date: "2016-05-04",
            name: "王小虎",
            address: "上海市",
          },
          {
            date: "2016-05-01",
            name: "王小虎",
            address: "上海市",
          },
          {
            date: "2016-05-03",
            name: "王小虎",
            address: "上海市",
          },
          {
            date: "2016-05-03",
            name: "王小虎",
            address: "上海市",
          },
          {
            date: "2016-05-03",
            name: "王小虎",
            address: "上海市",
          },
        ],
      },
    };
  },
  methods: {
    changeIsShow1() {
      this.isshow = "one";
      var arr=document.getElementsByClassName("button1");
      arr[0].classList.add("active");
      arr[1].classList.remove("active");
      arr[2].classList.remove("active");
    },
    changeIsShow2() {
      this.isshow = "seven";
      var arr=document.getElementsByClassName("button1");
      arr[1].classList.add("active");
      arr[0].classList.remove("active");
      arr[2].classList.remove("active");
    },
    changeIsShow3() {
      this.isshow = "month";
      var arr=document.getElementsByClassName("button1");
      arr[2].classList.add("active");
      arr[0].classList.remove("active");
      arr[1].classList.remove("active");
    },
  },
  created() {
    this.$store.commit("changeRanking", "min");
  },
  components: { Table },
};
</script>

<style scoped>
.header {
  width: 100%;
  height: 100px;
  background: url("@/assets/img/rank.jpg");
  background-size: 100% 100%;
}
span {
  font-size: 20px;
  font-family: "YouYuan";
  line-height: 100px;
  margin-left: 5px;
}
button {
  font-size: 10px;
  background-color: #fff;
  border: none;
  border-radius: 10px;
  margin-left: 15px;
}
button:hover {
  cursor: pointer;
}
.active{
  background-color: bisque;
}
</style>