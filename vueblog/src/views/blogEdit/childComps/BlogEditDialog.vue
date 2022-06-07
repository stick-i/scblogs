<template>
  <div class="dialog">
    <el-dialog
      title="发布文章"
      :visible.sync="dialogFormVisible"
      @close="closeDialog"
    >
      <!-- 表单  -->
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="110px"
        class="demo-ruleForm"
      >
        <el-form-item label="文章封面：" prop="file">
          <!-- 文章封面图片上传开始 -->
          <el-upload
            ref="uploadxls"
            action="/blog-console/blog"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            :headers="headersObj"
            :auto-upload="false"
            :before-upload="beforeupload"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="" />
          </el-dialog>
          <!-- 文章封面图片上传结束 -->
        </el-form-item>

        <!-- 摘要 -->
        <el-form-item
          label="文章摘要："
          prop="description"
          style="margin-top: 5px"
        >
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="发布形式：" prop="status" style="margin-top: 10px;">
          <el-radio-group v-model="ruleForm.status">
            <el-radio :label="1">公开</el-radio>
            <el-radio :label="3">私密</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import qs from "qs";
export default {
  props: {
    blogTitle: String,
    blogContent: String,
		blogContentHtml:String,
    dialogShow: {
      type: Boolean,
      default: false,
    },
  },
  name: "",
  data() {
    return {
      dialogFormVisible: this.dialogShow,
      // 表单
      ruleForm: {
        id: "",
        title: this.blogTitle,
        description: "",
        coverImage: null,
        content: this.blogContent,
				contentHtml: this.blogContentHtml,
        status: 1,
      },
      rules: {
        description: [
          { required: true, message: "请填写文章摘要", trigger: "blur" },
        ],
      },

      // 标签
      dynamicTags: ["前端", "JavaScript", "html"],
      inputVisible: false,
      inputValue: "",

      // 图片上传
      dialogImageUrl: "",
      dialogVisible: false,

      // 图片上传添加
      fileList: "",
      headersObj: { token: localStorage.getItem("token") }, // 必要
      ids: [],
      formData: new FormData(), // 两个方法共用
      // 图片上传添加结束
    };
  },
  mounted() {},
  watch: {
    dialogShow(val) {
      this.dialogFormVisible = val;
    },
  },
  methods: {
    closeDialog() {
      this.$emit("dialogShowChange", false);
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let that = this;
          that.formData = new FormData();
          this.formData.append('id',this.ruleForm.id);
          this.formData.append('title',this.ruleForm.title);
          this.formData.append('description',this.ruleForm.description);
          this.formData.append('content',this.ruleForm.content);
					this.formData.append('contentHtml',this.ruleForm.contentHtml);
          this.formData.append('status',this.ruleForm.status);

          this.$refs.uploadxls.submit();    // 提交表单
          // this.$refs.uploadxls.clearFiles();    // 清空图片数据

          this.$axios.post('/blog/console/blog',this.formData,{
                headers: { token: localStorage.getItem("token") },
              }).then(res=>{
            console.log(res)
						// console.log(this.ruleForm.contentHtml)
            if(res.data.code == 200&& res.data.status==true) {
              // this.$message({
              //   showClose: true,
              //   message: "发布成功~",
              //   type: "success",
              // });
              this.$router.push("/blog/publish");
            }
          })

          this.formData = "";   // 清空formData数据


        } else {
          console.log("error submit!!");
          return false;
        }
      });

    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 标签开始
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },
    // 标签结束

    // 图片上传开始
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      console.log('图片路径',this.dialogImageUrl);
      this.dialogVisible = true;
    },
    beforeupload(file) {
      console.log('将图片添加到formData中保存',file);
      this.formData.append("coverImage", file);  //将图片添加到formData中保存
      return false;   //阻止自动上传
    },
    // 图片上传结束
  },
};
</script>

<style scoped>
/* 标签开始 */
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
/* 标签结束 */
</style>
