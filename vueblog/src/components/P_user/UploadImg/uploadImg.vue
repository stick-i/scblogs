<template>
  <div>
       <el-upload
          :action="uploadImgUrl"
          list-type="picture-card"
          :on-success="handleUploadSuccess"
          :on-change="upData"
          :before-upload="handleBeforeUpload"
          :limit="limit"
          :on-error="handleUploadError"
          :on-exceed="handleExceed"
          name="scrm-files"
          :on-remove="handleRemove"
          :show-file-list="true"

          :auto-upload="autoUpload"
          :on-preview="handlePictureCardPreview"
          :class="{ hide: this.fileList.length > 0 }"
          ref="upload">
          <i class="el-icon-plus"></i>
      </el-upload>
	  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
  </div>
</template>
<script>
// import axios from "axios"; //数据请求
export default {
  data() {
    return {
      limit: 1,		   // 最大上传数量
      fileSize: 5,	   // 上传图片的最大限制（MB）
      uploadImgUrl: 'https://local.sticki.cn/api/v1/user/avatar'  , // 上传图片的接口地址
      fileType: ["png", "jpg", "jpeg"], // 上传类型
      fileList:[],
      autoUpload:false,
	  config:{headers:{'Content-Type': 'multipart/form-data','token':localStorage.getItem('token')}},
	  formdata:""
    };
  },
 methods: {
    // 上传成功回调
    	handleUploadSuccess(res) {
      		this.fileList.push({ name: res.fileName, url: res.data });
      		this.loading.close();
    	},
    	// 上传前loading加载,判断所上传照片是否合乎规范
    	handleBeforeUpload(file) {
        console.log("执行顺序2,所拥有的数据",file)
      		let isImg = false;
      		if (this.fileType.length) {
        		let fileExtension = "";
        		if (file.name.lastIndexOf(".") > -1) {
          			fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        		}
        		isImg = this.fileType.some((type) => {
          			if (file.type.indexOf(type) > -1) return true;
          			if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          			return false;
        		});
      		} else {
        		isImg = file.type.indexOf("image") > -1;
      		}
      		if (!isImg) {this.$message.error(`文件格式不正确, 请上传${this.fileType.join("/")}图片格式文件!`);
        		return false;
      		}
      		if (this.fileSize) {
        		const isLt = file.size / 1024 / 1024 < this.fileSize;
        		if (!isLt) {
          			this.$message.error(`上传头像图片大小不能超过 ${this.fileSize} MB!`);
          			return false;
        		}
      		}
    	},
       // 更新用户头像信息
    //   upData(file,fileList){
    //     console.log("执行顺序1",file, fileList)
    //         let formdata = new FormData()
    //         fileList.map(item => { //fileList本来就是数组，就不用转为真数组了
    //           formdata.append("avatarFile", item.raw)  //将每一个文件图片都加进formdata
    //         })
    //         this.$axios.put("/user/avatar", formdata,this.config)
	// 		.then(
	// 			res => { console.log("接口调用返回数据",res)
	// 		})
    //   },
		upData(file,fileList){
			console.log("执行顺序1",file,"文件数组", fileList)
			console.log("qua",this.fileList)
				let formdata = new FormData()
				fileList.map(item => { //fileList本来就是数组，就不用转为真数组了
				formdata.append("avatarFile", item.raw)  //将每一个文件图片都加进formdata
				})
				console.log(formdata)
			this.formdata=formdata
				// this.submitUpload(formdata)
		},
		submitUpload(){
			console.log("上传的图片数据",this,this.formdata)
			this.$axios.put("/user/avatar",this.formdata,this.config)
			.then(
				res => { console.log("上传头像接口调用返回数据",res)
				if(res.data.status==true){
					 this.$message({
						message: '头像修改成功',
						type: 'success'
						});
					this.bus.$emit('refreshUserInfo')
				}else{
					 this.$message({
						message: '头像修改失败',
						type: 'error'
						});
					}
			})
		},
    	// 上传失败
    	handleUploadError() {
      		this.$message({
        		type: "error",
        		message: "上传失败",
      		});
      		this.loading.close();
    	},
    	// 文件个数超出
    	handleExceed() {
      		this.$message.error(`上传文件数量不能超过 ${this.limit} 个!`);
    	},
    	// 删除图片
    	handleRemove() {
            console.log("触发了删除图片的函数")
      		this.fileList = [];
    	},
    	// 预览
    	handlePictureCardPreview(file) {
        console.log("上传文件的url",file.url)
      		this.dialogImageUrl = file.url;
      		this.dialogVisible = true;
    	},
       handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      }
 }
}
</script>
<style scoped>
::v-deep.hide .el-upload--picture-card {
  display: none;
}
el-upload{

}
</style>
