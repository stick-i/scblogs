<template>
  <div class="blog-publish-success">
    <el-card class="box-card">
      <div class="title">博客发布成功</div>
      <div class="btn-box">
        <div class="btn">内容管理</div>
        <div class="btn">查看文章</div>
        <div class="btn">再写一篇</div>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: ""
  }
</script>

<style scoped>
  .blog-publish-success {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    background-color: #f2f2f2;
  }
  .box-card {
    width: 480px;
  }
  .title {
    font-size: 18px;
    font-weight: 500;
    margin-bottom: 120px;
  }
  .btn-box {
    display: flex;
    justify-content: space-around;
  }
  .btn-box .btn {
    height: 32px;
    width: 100px;
    border: 1px solid #ccccd8;
    border-radius: 15px;
    line-height: 30px;
    cursor: pointer;
  }
  .btn-box .btn:hover {
    border: 1px solid #555666;
  }
</style>
