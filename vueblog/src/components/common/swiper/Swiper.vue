<template>
  <div class="swiper">
    <!-- 右边轮播图 -->
    <div class="main-right">
      <el-carousel indicator-position="outside" height="380px">
        <el-carousel-item v-for="item in 6" :key="item">
          <h3>{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
};
</script>

<style scoped>
/* 轮播图开始 */
.main-right {
  /*width: 510px;*/
  width: 100%;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

/* 轮播图结束 */
</style>
