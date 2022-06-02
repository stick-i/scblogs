<template>
  <div class="contain">
      <!-- 动态编辑页面 -->
      <div class="head">
          <TopBar></TopBar>
      </div>
      <div class="editpart">
          <Dynamic></Dynamic>
      </div>
  </div>
</template>

<script>
import TopBar from '@/components/content/topbar/TopBar.vue'
import Dynamic from '@/components/P_user/Dynamic/dynamicedit.vue'

export default {
        components:{
        TopBar,
        Dynamic
    }
}
</script>

<style scoped>
.contain{
    width: 100%;
    min-height: 100vh;
    background: rgb(247,248,249);
}
.editpart{
    width: 1300px;
    margin: 0 auto;
    padding-top: 70px;
}
</style>