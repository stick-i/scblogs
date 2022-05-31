<template>
  <div class="body">
      <div class="box">
          <div class="boxup">
            <el-input
                class="textedit"
                type="textarea"
                placeholder="你有什么想说的吗？"
                v-model="textarea"
                :rows="15"
                resize="none"
                maxlength="1000"
                show-word-limit
                size="medium"
                >
            </el-input>
          </div>
          <div class="boxdown">
              <div class="optionshow">
                <div class="imgshow">
                    <el-upload
                        action="https://jsonplaceholder.typicode.com/posts/"
                        list-type="picture-card"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove"
                        :auto-upload="false"
                        :multiple="true"
                        :on-change="ShowChange"
                        :file-list="fileList"
                        :http-request="HandleUpload"
                        >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                    <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                </div>
              </div>
              <div class="option">
                  <ul>
                      <li v-for="item in iconList" :key="item.id" @click="Options(item.id)">
                          <i class="iconfont" :class="item.icon"></i>
                          <span>{{item.option}}</span>
                      </li>
                  </ul>
                <button>发布动态</button>
              </div>
          </div>
      </div>
  </div>
</template>

<script>
export default {
    name:'Dynamic',
    data(){
        return {
            textarea:"",
            iconList:[{icon:' icon-tupian',option:'图片',id:1}],
            // 预览的照片地址
            dialogImageUrl: '',
            dialogVisible: false,
            fileList:[]
        }
    },
    methods:{
        Options(id){
                switch(id){
                    case 1:
                        this.AddPicture();
                        break
                }
        },
        AddPicture(){

        },
        // 点击放大照片预览
         handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        // 点击删除照片
        handleRemove(file, fileList) {
            // 此时已经删除完成
            console.log("删除完成时的的文件列表",fileList);
        },
        // 文件状态改变的时候显示文件列表
        ShowChange(file,fileList){
            this.fileList=fileList
        },
        // 点击后手动上传动态
        HandleUpload(){
            let fromdata=new fromdata()
            this.fileList.map(item=>{
                fromdata.append('',item.raw)
            })
            
        }
    }
}
</script>

<style scoped>
.body{
    width: 100%;
    background: white;
    border-radius: 10px;
}
.contain .box{
    width: 100%;
    height: 100%;
    padding: 20px 15px 20px;
}
.contain .box .boxup >>> .textedit{
    font-size: 22px;
    font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
    line-height: 1.7;
}
.boxdown{
    width: 100%;

}
.boxdown .optionshow{
    width: 100%;
    min-height: 200px;
    margin-top: 20px;
}
.boxdown .option{
    width: 100%;
    height: 100px;
    display: flex;
    align-items: center;
}
.boxdown .option ul{
    width: 100%;
    height:100%;
    display: flex;
    align-items: center;
}
.boxdown .option ul li{
    font-size: 20px;
    font-weight: 600;
    margin:0 20px;
    display: flex;
    cursor: pointer;
    align-items: center;
}
.boxdown .option ul li:hover{
    color: rgb(22, 160, 248);
}
.boxdown .option ul li i{
    vertical-align: middle;
}
.boxdown .option ul li span{
    margin-left:10px;
}
.iconfont{
    font-size: 32px;
}
.boxdown .option  button{
    width: 200px;
    height: 50px;
    border: none;
    border-radius: 10px;
    background: rgb(22, 160, 248);
    color: white;
    transition: all 0.2s ease-in-out;
}
.boxdown .option  button:hover{
    box-shadow: 0 0 10px  grey;
    transform: scale(1.1);
}
.boxdown .option  button:active{
    transform: scale(0.9);
}
</style>
