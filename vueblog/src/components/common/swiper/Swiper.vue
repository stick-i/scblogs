<template>
  <div class="swiper">
    <div class="swiper-inner">
      <el-carousel :interval="4000" type="card" height="300px">
        <el-carousel-item>
          <img src="../../../assets/img/home/swiper/1.jpg" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../../../assets/img/home/swiper/2.jpg" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../../../assets/img/home/swiper/3.jpg" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../../../assets/img/home/swiper/4.jpg" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../../../assets/img/home/swiper/5.jpg" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
  export default {
    name: ""
  }
</script>

<style scoped>
    .swiper{
        margin-top: 15px;
        display: flex;
        justify-content: center;
    }
    .swiper .swiper-inner .el-carousel{
        width: 1500px;
    }
     .swiper .swiper-inner .el-carousel .el-carousel-item {
        border-radius: 100px;
    }
</style>