<template>
  <div class="content">
        <div class="top">
            <div class="top-mid">
                  <el-carousel :interval="4000" type="card" height="200px">
                    <el-carousel-item v-for="item in ImgList" :key="item">
                    <h3 class="medium"><img :src="item.url" alt=""></h3>
                    </el-carousel-item>
                  </el-carousel>
            </div>
        </div>
  </div>
</template>

<script>
export default {
    data(){
        return{
            // 图片列表
            ImgList:[{url:'	https://tse4-mm.cn.bing.net/th/id/OIP-C.fEGS9-0niA…5NDgHaEo?w=252&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7'},
            {url:'https://tse2-mm.cn.bing.net/th/id/OIP-C.GT2zANOHAKepCbh8r5ln5gHaEo?w=252&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7'},
            {url:'https://tse2-mm.cn.bing.net/th/id/OIP-C.0djKnv1b1TfLAMwsXe2auAHaE8?w=297&h=198&c=7&r=0&o=5&dpr=1.25&pid=1.7'},
            {url:'https://tse1-mm.cn.bing.net/th/id/OIP-C.gXj-VGesce447WU0kfQLyQHaF7?w=207&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7'},
            {url:'https://tse4-mm.cn.bing.net/th/id/OIP-C.8q8ED6qvOyjiKdPJaEhSqwHaDd?w=321&h=163&c=7&r=0&o=5&dpr=1.25&pid=1.7'},
            {url:'https://tse3-mm.cn.bing.net/th/id/OIP-C.lw9VQtXdtTeKCHUud3MSggHaEo?w=295&h=184&c=7&r=0&o=5&dpr=1.25&pid=1.7'},
            {url:'https://tse4-mm.cn.bing.net/th/id/OIP-C.j0h_hKAHdTr2sP6sAtHPegHaEo?w=300&h=187&c=7&r=0&o=5&dpr=1.25&pid=1.7'},
            {url:'https://tse2-mm.cn.bing.net/th/id/OIP-C.zUJQytZNokug1-j4GPDsvAHaEq?w=273&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7'},
            {url:'https://tse1-mm.cn.bing.net/th/id/OIP-C.ZuMwywkRKG0d6csdHHwp_wHaEo?w=253&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7'}]
        }
    }
}
</script>

<style>
.top{
    width: 100%;
    /* height: 50px; */
    background: white;
}
.top-mid{
    margin: 0 auto;
    width: 1280px ;
    height: 100%;
    /* line-height: 50px; */
    background: rgb(242, 133, 24);
    text-align: center;
    color: white;
    font-size: 26px;
    font-weight: 800;
}
  .el-carousel__item h3 {
    color: #fc622b;
    font-size: 14px;
    opacity: 1;
    line-height: 200px;
    margin: 0;
  }
  .h3  img{
      width: 400px;
      height: 300px;
  }
  .el-carousel__item:nth-child(2n) {
  }
  
  .el-carousel__item:nth-child(2n+1) {
  }
</style>