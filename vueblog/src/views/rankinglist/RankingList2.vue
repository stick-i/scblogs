<template>
  <div class="container">
    <div class="header">
      <span style="color:aqua">博客热度排行榜</span>
      <button @click="changeIsShow1" class="button2 active">当日排行</button>
      <button @click="changeIsShow2" class="button2">七日排行</button>
      <button @click="changeIsShow3" class="button2">月排行</button>
    </div>
    <div class="main" style="height: 100%">
      <!-- <One2 v-if="isshow == 'one'"></One2>
      <Seven2 v-else-if="isshow == 'seven'"></Seven2>
      <Month2 v-else-if="isshow == 'month'"></Month2> -->
     <Table :data="table"></Table>
      <router-link to="/RankingListPlus2">查看更多&nbsp;></router-link>
    </div>
  </div>
</template>

<script>
import Table from '@/components/common/table/Table.vue';
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
      var arr=document.getElementsByClassName("button2");
      arr[0].classList.add("active");
      arr[1].classList.remove("active");
      arr[2].classList.remove("active");
    },
    changeIsShow2() {
      this.isshow = "seven";
      var arr=document.getElementsByClassName("button2");
      arr[1].classList.add("active");
      arr[0].classList.remove("active");
      arr[2].classList.remove("active");
    },
    changeIsShow3() {
      this.isshow = "month";
      var arr=document.getElementsByClassName("button2");
      arr[2].classList.add("active");
      arr[0].classList.remove("active");
      arr[1].classList.remove("active");
    },
  },
  created(){
    this.$store.commit("changeRanking","min");
  },
  components: { Table},
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