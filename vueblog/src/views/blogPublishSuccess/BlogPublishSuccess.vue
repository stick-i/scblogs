<template>
  <div class="blog-publish-success">
    <PublishSuccess/>
  </div>
</template>

<script>
  import PublishSuccess from "@/views/blogPublishSuccess/childComps/PublishSuccess";
  export default {
    name: "",
    components:{
      PublishSuccess,
    }
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

</style>
