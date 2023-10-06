<template>
	<div id="csdn-toolbar">
		<div class="toolbar-inside">
			<div class="toolbar-container">
				<div class="toolbar-container-left">
					<div class="toolbar-logo toolbar-subMenu-box csdn-toolbar-fl">
						<a
							data-report-click='{"spm":"3001.4476"}'
							data-report-query="spm=3001.4476"
							href="/"
						>
							<img id="icon" title="首页" src="../../../assets/img/long-logo.png"/>
						</a>
					</div>
					<ul id="head-nav" class="toolbar-menus csdn-toolbar-fl">
						<li class="" title="技术问题，有问必答">
							<a
								data-report-click='{"mod":"popu_336","dest":"http://scblogs.cn/","spm":"3001.4492"}'
								data-report-query="spm=3001.4492"
								href="/"
							>
								问答
								<!-- 校园风采 -->
							</a>
						</li>

						<li class="" title="找到志同道合的伙伴">
							<a
								@click="ToHeart()"
								href="javascript:"
							>
								动态
								<!-- 关于我们 -->
							</a>
						</li>

						<li class="" title="看看美丽的校园">
							<a
								data-report-click='{"mod":"popu_336","dest":"http://scblogs.cn/","spm":"3001.4482"}'
								data-report-query="spm=3001.4482"
								href="/"
							>
								校园风采
							</a>
						</li>

						<li class="" title="当前院校">
							<a
								href="javascript:"
								@click="centerDialogVisible = true">
								<el-popover
									placement="top"
									width="500"
									v-model="visible"
									:key="showschool"
								>
									<el-row>
										<el-col :span="8">
											<el-select
												v-model="provinceId"
												@focus="getProvinces"
												placeholder="请选择省份"
											>
												<el-option
													v-for="item in provinceList"
													:key="item.id"
													:label="item.name"
													:value="item.id"
												>
												</el-option>
											</el-select>
										</el-col>
										<el-col :span="8">
											<el-select
												v-model="cityId"
												@focus="getCities"
												placeholder="请选择城市"
											>
												<el-option
													v-for="item in cityList"
													:key="item.id"
													:label="item.name"
													:value="item.id"
												>
												</el-option>
											</el-select>
										</el-col>
										<el-col :span="8">
											<el-select
												v-model="schoolCode"
												@focus="getSchools"
												placeholder="请选择学校"
											>
												<el-option
													v-for="item in schoolList"
													:key="item.id"
													:label="item.name"
													:value="item.code"
												>
												</el-option>
											</el-select>
										</el-col>
									</el-row>
									<div style="text-align: right; margin: 10px">
										<el-button size="mini" type="text" @click="Cancel()">取消</el-button>
										<el-button type="primary" size="mini" @click="ChangeSchool()">确定</el-button>
									</div>
									<el-text slot="reference">长沙大学</el-text>
								</el-popover>
								<!-- 资源分享 -->
							</a>
						</li>

					</ul>
				</div>

				<!-- 搜索框 -->
				<div class="toolbar-container-middle" :style="{'display': isShowInput}">
					<div class="toolbar-search onlySearch">
						<div class="toolbar-search-container">
							<span class="icon-fire" style="display: none"></span>
							<!--  搜索博客  -->
							<input
								id="toolbar-search-input"
								autocomplete="off"
								type="text"
								v-model="key"
								value=""
								placeholder="迁移学习分析"
								style="text-indent: 12px"
							/>
							<button id="toolbar-search-button" @click="goSearchBlogs()">
								<!--                <button id="toolbar-search-button" @click="searchBlogs()">-->
								<i></i><span>搜索</span>
							</button>
							<input
								type="password"
								autocomplete="new-password"
								readonly=""
								disabled="true"
								style="
                  display: none;
                  position: absolute;
                  left: -9999999px;
                  width: 0;
                  height: 0;
                "
							/>
						</div>
					</div>
				</div>

				<div class="toolbar-container-right">
					<div class="toolbar-btns onlyUser">
						<div
							class="toolbar-btn toolbar-btn-login csdn-toolbar-fl"
							v-if="!isShowAvatar"
						>
							<router-link to="/login"><a>登录/注册</a></router-link>
						</div>

						<!-- 显示头像 下拉菜单开始 -->
						<div class="test-div csdn-toolbar-fl">
							<el-dropdown @command="handleCommand">
                <span class="el-dropdown-link">
                  <a class="test-div csdn-toolbar-fl" v-if="isShowAvatar">
                    <img :src="avatarUrl" alt="" class="test-img"
												 style="border: 1px solid #e8e8ed;margin-right: 10px;"/>
                  </a>
                </span>
								<el-dropdown-menu slot="dropdown">
									<el-dropdown-item command="a">个人中心</el-dropdown-item>
									<el-dropdown-item command="b" divided
									>退出登录
									</el-dropdown-item
									>
								</el-dropdown-menu>
							</el-dropdown>
						</div>
						<!-- 显示头像 下拉菜单结束 -->
						<div class="toolbar-btn toolbar-btn-write csdn-toolbar-fl">
							<a
								data-report-click='{"spm":"3001.7765"}'
								data-report-query="spm=3001.7765"
								class="btn-write-new"
								@click="goBlogEdit()"
								style="cursor: pointer"
							></a>
						</div>


					</div>
				</div>
				<!--        <div>{{key}}</div>-->
				<!--        <div>|</div>-->
				<!--        <div>{{$store.state.searchKey}}</div>-->
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: "TopBar",
	data() {
		return {
			// 信息
			userMessage: {},
			// 头像
			avatarUrl: require("../../../assets/img/home/default_avatar.jpg"),
			isShowAvatar: false,
			// 搜索内容
			key: "",
			// 是否显示搜索框
			isShowInput: "block",
			path: this.$route.fullPath,
			// 弹出框显示
			showschool: 0,
			myschool: "",//用户学校
			visible: false,
			provinceId: "", // 省份编码
			provinceList: [],//省份列表数据
			schoolList: [],//学校列表
			schoolCode: "",
			cityId: "",//城市编码
			cityList: [],//城市列表
			config: {
				headers: {
					token: localStorage.getItem('token')
				}
			}
		};
	},

	components: {},
	created() {
		this.avatarUrl = window.localStorage.avatarUrl;
		this.isShowAvatar = window.localStorage.isShowAvatar;
		console.log(this.$route.fullPath)
		this.showInput();
		// this.GetMySchool()
	},
	mounted() {
		this.bus.$on("userMessage", (data) => {
			this.avatarUrl = JSON.parse(data).avatarUrl;
			this.isShowAvatar = true;
			this.userMessage = data;
			window.localStorage.avatarUrl = this.avatarUrl;
			window.localStorage.isShowAvatar = this.isShowAvatar;
			window.localStorage.userMessage = this.userMessage;
		});
	},
	// 监听
	watch: {
		'$route'(to, from) {
			this.path = to.fullPath;
			console.log(this.path)
			this.showInput();
		},
		provinceId: {
			handler(newName, oldName) {
				this.cityId = ''
				this.schoolCode = ''
			},
			immediate: true
		},
		cityId: {
			handler(newName, oldName) {
				this.schoolCode = ''
			},
			immediate: true
		}
	},
	methods: {
		//取消切换用户学校
		Cancel() {
			this.visible = !this.visible
			this.provinceId = ''
			this.cityId = ''
			this.schoolCode = ''
			// this.showschool++
		},
		//获取用户学校
		GetMySchool() {
			this.myschool = JSON.parse(localStorage.getItem('userMessage')).schoolName
		},
		//修改用户学校
		ChangeSchool() {
			let formdata = new FormData()
			formdata.append('code', this.schoolCode)
			this.$axios.put('/user/school/code', formdata, this.config).then(res => {
				// if()
			})
			this.visible = false
		},
		// 获取省份
		getProvinces() {
			this.$axios.get("/resource/province").then((res) => {
				this.provinceList = res.data.data;
				console.log("返回的省份数据", this.provinceList, "选中的省份id", this.provinceId)
			});

		},
		// 获取城市
		getCities() {
			if (this.provinceId === '') {
				// console.log("请先选取省份")
				this.$message({
					showClose: true,
					message: "请先选取省份",
					type: "warning",
				});
			} else {
				this.$axios
					.get("/resource/city?provinceId=" + this.provinceId)
					.then((res) => {
						this.cityList = res.data.data;
					});
			}
		},
		// 获取学校
		getSchools() {
			if (this.cityId === '') {
				// console.log("请先选取城市")
				this.$message({
					showClose: true,
					message: "请先选取城市",
					type: "warning",
				});
			} else {
				this.$axios
					.get("/resource/university?cityId=" + this.cityId)
					.then((res) => {
						this.schoolList = res.data.data;
					});
			}
		},
		// 显示搜索框
		showInput() {
			if (this.path === '/blog/search') {
				this.isShowInput = "none";
			} else {
				this.isShowInput = "block";
			}
		},
		// 跳转表白墙页面
		ToHeart() {
			this.$router.push('/HeartSay')
		},
		// 跳转搜索博客
		goSearchBlogs() {
			this.$store.commit('copySearchKey', this.key)
			this.$router.push('/blog/search')
		},

		// 退出登录
		handleCommand(command) {
			if (command === "a") {
				var routeUrl = this.$router.resolve({
					name: 'UserHome',
					params: {userId: JSON.parse(localStorage.getItem("userMessage")).id}
				})
				window.open(routeUrl.href, '_blank');
			}
			if (command === "b") {
				console.log("退出登录");
				this.$message({
					showClose: true,
					message: "已退出登录",
					type: "success",
				});
				// 清除状态保持
				window.localStorage.clear();
				window.sessionStorage.clear();
				// 状态保持清除后刷新页面
				window.location.reload();

				// this.$axios.get("/login/logout").then((res) => {
				//   console.log(res);
				//   this.$message({
				//     showClose: true,
				//     message: "已退出登录",
				//     type: "success",
				//   });
				//
				//
				// });
			}
		},
		// 点击跳转发布文章页面
		goBlogEdit() {
			// console.log(window.localStorage.isShowAvatar)
			if (window.localStorage.isShowAvatar === undefined) {
				this.$message({
					showClose: true,
					message: "请先登录哦~",
					type: "warning",
				});
			} else {
				this.$router.push({path: "/blog/add"});
				// window.open("/blog/add", "_blank");
			}
		},
	},
};
</script>

<style>
@import "../../../assets/css/content/topbar/topbar.css";

.test-div {
	height: 48px;
}

#csdn-toolbar {
	/*position: fixed;*/
	top: 0px;
	left: 0px;
	z-index: 100;
	width: 100%;
}

#csdn-toolbar
.toolbar-inside
.toolbar-container
.toolbar-container-right
.onlyUser
.test-img {
	display: block;
	width: 32px;
	height: 32px;
	border-radius: 50%;
	margin-top: 8px;
}

.el-dropdown-link {
	cursor: pointer;
}

#icon {
	display: inline-block;
	wdith: 100px;
	height: 30px;
	margin-top: 5px;
}

#head-nav {

}
</style>
