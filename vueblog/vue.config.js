const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
const webpack = require("webpack")
module.exports = {

  devServer: {
    proxy: {
      '/api': {
        target: 'https://local.sticki.cn/api/v1',// 后端接口
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '/api': ''
        }
      }
    }
  }
}
