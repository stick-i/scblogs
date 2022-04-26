<template>
  <div class="blogDetail">
    <TopBar/>
    <el-card class="box-card">
      <div class="text item">
        <h2>{{blogDetail.title}}</h2>
        <el-divider></el-divider>
        <div v-html="blogDetail.content"></div>
      </div>
    </el-card>
  </div>
</template>

<script>
  import TopBar from "@/components/content/topbar/TopBar";
  export default {
    name: "BlogDetail",
    components:{
      TopBar
    },
    data() {
      return {
        blogDetail:{
          id:1,
          title:'标题',
          content:'内容',
        }
      };
    },
    created() {
      const blogId = this.$route.params.blogId;
      console.log("传输到的文章ID为",blogId,this.$route)
      const _this = this;
      this.$axios.get("/blog/blog",{params:{id:blogId}}).then(res => {
        console.log("内容显示页面获取到的所有数据",res);
        const blog = res.data.data;
        _this.blogDetail.id = blog.info.id;
        _this.blogDetail.title = blog.info.title;
        _this.blogDetail.content = blog.content.content;
      })

    },
    methods: {
    }

  }
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .box-card {
    width: 1380px;
    margin: 10px auto;
  }
</style>
