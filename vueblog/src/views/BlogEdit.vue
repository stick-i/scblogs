<template>
  <div>
    <div class="m-content">
      <div class="top-container">
        <div class="article-bar">
          <div class="btn-goback">
            <i class="el-icon-caret-left"></i>
            <div class="text">文&nbsp;章&nbsp;管&nbsp;理</div>
          </div>
          <div class="bar-input">

            <input
              type="text"
              placeholder="请输入文章标题"
              v-model="ruleForm.title"
              prop="title"
            />

          </div>
          <div class="btn">
            <button class="btn-save">保存草稿</button>
<!--            <button class="btn-publish" @click="submitForm()">-->
              <button class="btn-publish" @click="submitForm()">
              发布文章
            </button>
          </div>
          <div class="bar-img">
            <a href="#"
              ><img src="../assets/img/home/default_avatar.jpg" alt=""
            /></a>
          </div>
        </div>
      </div>

<!--      <el-form-->
<!--        :model="ruleForm"-->
<!--        :rules="rules"-->
<!--        ref="ruleForm"-->
<!--        class="demo-ruleForm"-->
<!--      >-->
        <!-- label-width="100px" -->
        <!-- <el-form-item label="标题" prop="title">
          <el-input
            v-model="ruleForm.title"
            placeholder="请输入文章标题"
          ></el-input>
        </el-form-item> -->

        <!-- <el-form-item label="摘要" prop="description">
          <el-input
            type="textarea"
            v-model="ruleForm.description"
            placeholder="请输入文章摘要"
          ></el-input>
        </el-form-item> -->

        <!-- markdown编辑器  -->
<!--        <el-form-item prop="content">-->
          <mavon-editor
            v-model="ruleForm.content"
            style="height: calc(100vh - 56px)"
          ></mavon-editor>
<!--        </el-form-item>-->

        <!--        <el-form-item>-->
        <!--          <el-button type="primary" @click="submitForm('ruleForm')"-->
        <!--            >立即创建</el-button-->
        <!--          >-->
        <!--          <el-button @click="resetForm('ruleForm')">重置</el-button>-->
        <!--        </el-form-item>-->
<!--      </el-form>-->


      <Dialog/>



    </div>
  </div>
</template>

<script>
import qs from "qs";

import Dialog from "@/components/content/blogEdit/Dialog";

export default {
  name: "",
  components:{
    Dialog
  },
  data() {
    return {
      ruleForm: {
        id:'',
        title: "",
        description: "11",
        content: "",
        status:0
      },
      // rules: {
      //   title: [
      //     { required: true, message: "请输入标题", trigger: "blur" },
      //     {
      //       min: 3,
      //       max: 25,
      //       message: "长度在 3 到 25 个字符",
      //       trigger: "blur",
      //     },
      //   ],
      //   description: [
      //     { required: true, message: "请输入摘要", trigger: "blur" },
      //   ],
      //   content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      // },
    };
  },
  methods: {

    submitForm() {
      if (this.ruleForm.title.length === 0) {
        this.$message({
          showClose: true,
          message: "请填写标题",
          type: "warning",
        });
      } else if (this.ruleForm.content.length === 0) {
        this.$message({
          showClose: true,
          message: "请填写内容",
          type: "warning",
        });
      } else {
        this.$axios
            .post("/blog-console/blog", qs.stringify(this.ruleForm),{withCredentials : true})
            .then((res) => {
              console.log(res);
            })
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style>
.m-content {
  /*text-align: center;*/
}
/*顶部*/
.top-container {
  width: 100%;
  height: 56px;
  background-color: #f3f3f3;
}
/*.article-bar*/
.article-bar {
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.article-bar .btn-goback {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-left: 24px;
  margin-right: 10px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  color: #6b6b6b;
  /* background-color: pink; */
}
.article-bar .btn-goback .text {
  width: 88px;
}
.article-bar .bar-input {
  width: 100%;
}
.article-bar .bar-input input {
  border: 1px solid #ccc;
  height: 36px;
  width: 100%;
  margin-left: 4px;
  border-radius: 18px;
  padding: 8px;
  padding-left: 16px;
  box-sizing: border-box;
  outline: none;
}
.article-bar .btn {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.article-bar .btn button {
  text-transform: none;
  cursor: pointer;
}
.article-bar .btn .btn-save {
  width: 98px;
  height: 36px;
  margin-right: 16px;
  padding: 0 16px;
  font-size: 16px;
  color: #fc5531;
  border: 1px solid #fc5531;
  border-radius: 18px;
  white-space: nowrap;
  background-color: #fff;
  margin-left: 20px;
}
.article-bar .btn .btn-publish {
  width: 98px;
  height: 36px;
  padding: 0 16px;
  font-size: 16px;
  color: #fff;
  border: none;
  border-radius: 18px;
  white-space: nowrap;
  background: #fc5531;
}
.article-bar .bar-img img {
  height: 38px;
  width: 38px;
  border-radius: 16px;
  border: 1px solid #f0f0f2;
  /*border: 1px solid black;*/
  margin-left: 20px;
  margin-right: 40px;
}

/* 表单 */
/*.demo-ruleForm {*/
/*  !* margin-top: 20px; *!*/
/*  !* height: 700px; *!*/
/*  background-color: pink;*/
/*}*/
.demo-ruleForm .el-form-item {
  margin-bottom: 0px;
}
</style>
