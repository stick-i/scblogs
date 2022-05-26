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

              </div>
              <div class="option">
                  <ul>
                      <li>
                          <span>图片</span>
                          <i class="iconfont icon-tupian"></i>

                      </li>
                      <li><button>发布动态</button></li>
                  </ul>
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
            textarea:""
        }
    }
}
</script>

<style scoped>
.body{
    width: 100%;
    background: rgb(220, 27, 27);
    border-radius: 10px;
}
.contain .box{
    width: 100%;
    height: 100%;
    padding: 50px;
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
    height: 200px;
}
.boxdown .option{
    width: 100%;
    height: 100px;
}
.boxdown .option ul{
    width: 100%;
    height:100%;
    display: flex;
    align-items: center;
}
.boxdown .option ul li{
    font-size: 22px;
    font-weight: 600;
    margin:0 20px;
    display: flex;
}
.boxdown .option ul li i{
    vertical-align: middle;
}
.iconfont{
    font-size: 40px;
}
.boxdown .option ul li button{
    width: 200px;
    height: 50px;
    border: none;
    border-radius: 10px;
    background: rgb(22, 160, 248);
    color: white;
    transition: all 0.2s ease-in-out;
}
.boxdown .option ul li button:hover{
    box-shadow: 0 0 10px 10px grey ;
    transform: scale(1.1);
}
.boxdown .option ul li button:active{
    transform: scale(0.9);
}
</style>
