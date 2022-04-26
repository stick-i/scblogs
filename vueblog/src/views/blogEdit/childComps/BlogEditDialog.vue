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
        <el-form-item label="文章封面：" prop="desc">
          <!-- 文章封面图片上传 -->
          <el-upload action="#" list-type="picture-card" :auto-upload="false">
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{ file }">
              <img
                class="el-upload-list__item-thumbnail"
                :src="file.url"
                alt=""
              />
              <span class="el-upload-list__item-actions"
                ><span
                  class="el-upload-list__item-preview"
                  @click="handlePictureCardPreview(file)"
                >
                  <i class="el-icon-zoom-in"></i>
                </span>
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleDownload(file)"
                >
                  <i class="el-icon-download"></i>
                </span>
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="" />
          </el-dialog>
        </el-form-item>

        <!-- 摘要 -->
        <el-form-item
          label="文章摘要："
          prop="description"
          style="margin-top: 5px"
        >
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <!-- 标签开始 -->
        <!-- <el-form-item label="文章标签：" prop="desc">
          <el-tag
            :key="tag"
            v-for="tag in dynamicTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)"
            >{{ tag }}
          </el-tag>
          <el-input
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
          >
          </el-input>
          <el-button
            v-else
            class="button-new-tag"
            size="small"
            @click="showInput"
            >+ 添加文章标签</el-button
          >
        </el-form-item> -->
        <!-- 标签结束 -->

        <!-- <el-form-item label="文章类型：" prop="resource">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="原创"></el-radio>
            <el-radio label="转载"></el-radio>
          </el-radio-group>
        </el-form-item> -->

        <el-form-item label="发布形式：" prop="status">
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
      disabled: false,
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
          this.$axios
            .post("/blog-console/blog", qs.stringify(this.ruleForm),
              { headers: { 'token': sessionStorage.getItem("token") } }
            )
            .then((res) => {
              console.log(res)
              if (res.data.code == 200 && res.data.status == true) {
                this.$message({
                  showClose: true,
                  message: "发布成功~",
                  type: "success",
                });
              }
            });
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
    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      console.log(file);
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
