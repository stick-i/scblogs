<template>
  <div>
    <el-card class="box-card">
      <div class="title">博客发布成功</div>
      <img src="../../../assets/img/blogPublishSuccess/publish.png" alt="" class="img-publish">
      <div class="btn-box">
				<router-link to="/">
					<div class="btn">返回首页</div>
				</router-link>
        <div class="btn">内容管理</div>
        <div class="btn">查看文章</div>
        <router-link to="/blog/add" target="_blank">
          <div class="btn">再写一篇</div>
        </router-link>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "",
  }
</script>

<style scoped>
  .box-card {
    width: 480px;
  }
  .title {
    font-size: 18px;
    font-weight: 500;
    /*margin-bottom: 120px;*/
  }
  .img-publish {
    width: 300px;
    margin-top: 20px;
    margin-bottom: 20px;
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
    color: #555666;
  }
  .btn-box .btn:hover {
    border: 1px solid #555666;
  }
</style>
