<template>
  <div>
    <div class="m-content">
      <div class="top-container">
        <div class="article-bar">
          <!-- <div class="btn-goback"> -->
          <router-link :to="{ name: 'ContentManagement' }" class="btn-goback">
            <i class="el-icon-caret-left"></i>
            <div class="text">文&nbsp;章&nbsp;管&nbsp;理</div>
          </router-link>
          <!-- </div> -->
          <div class="bar-input">
            <input
              type="text"
              placeholder="请输入文章标题"
              v-model="ruleForm.title"
              prop="title"
            />
          </div>
          <div class="btn">
            <button class="btn-save" @click="saveForm()">保存草稿</button>
            <button class="btn-publish" @click="submitForm()">发布文章</button>
          </div>
          <div class="bar-img">
            <a href="#">
              <img :src="avatarUrl" alt="" style="border: 1px solid #e8e8ed" />
            </a>
          </div>
        </div>
      </div>

      <!-- markdown编辑器  -->
      <mavon-editor
        v-model="ruleForm.content"
        ref="md"
        @imgAdd="imgAdd"
        @imgDel="imgDel"
        style="height: calc(100vh - 56px)"
      ></mavon-editor>

      <!-- 弹窗 -->
      <div v-if="dialogShow">
        <blog-edit-dialog
          :blogTitle="ruleForm.title"
          :blogContent="ruleForm.content"
          :blogContentHtml="ruleForm.contentHtml"
          :dialogShow="dialogShow"
          @dialogShowChange="dialogShowChange"
        ></blog-edit-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import BlogEditDialog from "@/views/blogEdit/childComps/BlogEditDialog";
import qs from "qs";

export default {
  name: "",
  components: {
    BlogEditDialog,
  },
  data() {
    return {
      // 目录开始
      tocs: [],
      // 目录结束
      avatarUrl: "",
      ruleForm: {
        id: "",
        title: "",
        description: "",
        content: "",
        contentHtml: "",
        status: 2,
      },
      dialogShow: false,
    };
  },
  created() {
    // 显示头像
    this.avatarUrl = window.localStorage.avatarUrl;
  },
  methods: {
    // md文档开始
    // 将图片上传到服务器，返回地址替换到md中
    imgAdd(pos, $file) {
      var _this = this;
      var formdata = new FormData();
      formdata.append("file", $file);
      this.$axios
        .post("/blog/console/image", formdata, {
          headers: { token: localStorage.getItem("token") },
        })
        .then((response) => {
          // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
          if (response.status === 200) {
            var url = response.data.data;
            _this.$refs.md.$img2Url(pos, url);
          }
          console.log(response);
        });
    },
    imgDel(pos) {},
    // md文档结束
    dialogShowChange(val) {
      this.dialogShow = val;
    },
    // 保存文章
    saveForm() {
      this.$axios
        .post("/blog/console/blog", qs.stringify(this.ruleForm), {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          if (res.data.code == 200 && res.data.status == true) {
            this.$message({
              showClose: true,
              message: "保存成功~",
              type: "success",
            });
          }
        });
    },
    // 发布文章
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
        this.ruleForm.contentHtml = this.$refs.md.d_render;
        // console.log(this.ruleForm.contentHtml)
        this.dialogShow = true;
      }
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
  border-radius: 5px;
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
  color: #16a0f8;
  border: 1px solid #16a0f8;
  border-radius: 5px;
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
  border-radius: 5px;
  white-space: nowrap;
  background: #16a0f8;
}
.article-bar .bar-img img {
  height: 38px;
  width: 38px;
  border-radius: 19px;
  border: 1px solid #f0f0f2;
  /*border: 1px solid black;*/
  margin-left: 20px;
  margin-right: 40px;
}

.demo-ruleForm .el-form-item {
  margin-bottom: 0px;
}
</style>
