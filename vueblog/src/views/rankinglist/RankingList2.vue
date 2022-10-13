<template>
  <div class="container">
    <div class="header">
      <span>博客热度排行榜</span>
      <button @click="changeIsShow1" ref="button1" style="background-color:bisque">当日排行</button>
      <button @click="changeIsShow2" ref="button2">七日排行</button>
      <button @click="changeIsShow3" ref="button3">月排行</button>
    </div>
    <div class="main" style="height: 500px">
      <One2 v-if="isshow == 'one'"></One2>
      <Seven2 v-else-if="isshow == 'seven'"></Seven2>
      <Month2 v-else-if="isshow == 'month'"></Month2>
      <router-link to="/RankingListPlus2">查看更多&nbsp;></router-link>
    </div>
  </div>
</template>

<script>
import One2 from "@/components/P_user/RankingList2/One2.vue";
import Seven2 from "@/components/P_user/RankingList2/Seven2.vue";
import Month2 from "@/components/P_user/RankingList2/Month2.vue";
export default {
  data() {
    return {
      isshow: "one",
    };
  },
  methods: {
    changeIsShow1() {
      this.isshow = "one";
      this.$refs.button1.style.backgroundColor='bisque';
      this.$refs.button2.style.backgroundColor='#fff';
      this.$refs.button3.style.backgroundColor='#fff';
    },
    changeIsShow2() {
      this.isshow = "seven";
      this.$refs.button1.style.backgroundColor='#fff';
      this.$refs.button2.style.backgroundColor='bisque';
      this.$refs.button3.style.backgroundColor='#fff';
    },
    changeIsShow3() {
      this.isshow = "month";
      this.$refs.button1.style.backgroundColor='#fff';
      this.$refs.button2.style.backgroundColor='#fff';
      this.$refs.button3.style.backgroundColor='bisque';
    },
  },
  created(){
    this.$store.commit("changeOne2","min");
    this.$store.commit("changeSeven2","min");
    this.$store.commit("changeMonth2","min");
  },
  components: { One2, Seven2, Month2 },
};
</script>

<style scoped>
.header {
  width: 100%;
  height: 100px;
  background: url("@/assets/img/newPersonBlog/tip.webp");
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
</style>