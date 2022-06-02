<template>
  <div class="contain">
      <!-- 表白墙部分 -->
      <!-- 头部导航部分 -->
      <TopBar></TopBar>
      <div class="body">
          <HeartSay></HeartSay>
      </div>
  </div>
</template>

<script>
import HeartSay from '@/components/P_user/HeartSay/heartsay.vue'
import TopBar from '@/components/content/topbar/TopBar.vue'
export default {
    components:{
        HeartSay,
        TopBar
    }
}
</script>

<style scoped>
.contain{
    width: 100%;
    min-height: 100vh;
    background: rgb(247,248,249);
}
.body{
    width: 1300px;
    margin: 0 auto;
    padding-top: 60px;
}
</style>