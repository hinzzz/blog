(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-2af8"],{ACvt:function(e,t,o){},KTR0:function(e,t,o){"use strict";var n=o("ACvt");o.n(n).a},c11S:function(e,t,o){"use strict";var n=o("fN6i");o.n(n).a},fN6i:function(e,t,o){},ntYl:function(e,t,o){"use strict";o.r(t);var n={name:"Login",data:function(){return{loginForm:{username:localStorage.getItem("username"),password:localStorage.getItem("password")},isRememb:!0,loginRules:{username:[{required:!0,trigger:"blur",validator:function(e,t,o){""===t?o(new Error("用户名不能为空")):o()}}],password:[{required:!0,trigger:"blur",validator:function(e,t,o){t.length<6?o(new Error("密码不能小于6位")):o()}}]},loading:!1,pwdType:"password",redirect:void 0}},watch:{$route:{handler:function(e){this.redirect=e.query&&e.query.redirect},immediate:!0}},methods:{showPwd:function(){"password"===this.pwdType?this.pwdType="":this.pwdType="password"},handleLogin:function(){var e=this;this.$refs.loginForm.validate(function(t){if(!t)return console.log("error submit!!"),!1;e.loading=!0,e.$store.dispatch("Login",e.loginForm).then(function(t){e.loading=!1,localStorage.setItem("username",e.loginForm.username),e.isRememb&&localStorage.setItem("password",e.loginForm.password),e.$router.push({path:e.redirect||"/"})}).catch(function(){e.loading=!1})})}}},r=(o("c11S"),o("KTR0"),o("ZrdR")),s=Object(r.a)(n,function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"login-container"},[o("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{model:e.loginForm,rules:e.loginRules,"auto-complete":"on","label-position":"left"}},[o("h3",{staticClass:"title"},[e._v("用户登录")]),e._v(" "),o("el-form-item",{attrs:{prop:"username"}},[o("span",{staticClass:"svg-container"},[o("svg-icon",{attrs:{"icon-class":"user"}})],1),e._v(" "),o("el-input",{attrs:{name:"username",type:"text","auto-complete":"on",placeholder:"用户名"},model:{value:e.loginForm.username,callback:function(t){e.$set(e.loginForm,"username",t)},expression:"loginForm.username"}})],1),e._v(" "),o("el-form-item",{attrs:{prop:"password"}},[o("span",{staticClass:"svg-container"},[o("svg-icon",{attrs:{"icon-class":"password"}})],1),e._v(" "),o("el-input",{attrs:{type:e.pwdType,name:"password","auto-complete":"on",placeholder:"密码"},nativeOn:{keyup:function(t){return"button"in t||!e._k(t.keyCode,"enter",13,t.key,"Enter")?e.handleLogin(t):null}},model:{value:e.loginForm.password,callback:function(t){e.$set(e.loginForm,"password",t)},expression:"loginForm.password"}}),e._v(" "),o("span",{staticClass:"show-pwd",on:{click:e.showPwd}},[o("svg-icon",{attrs:{"icon-class":"eye"}})],1)],1),e._v(" "),o("el-form-item",{staticStyle:{border:"0",background:"rgba(0, 0, 0, 0)","padding-left":"10px","margin-bottom":"10px","margin-top":"-12px"}},[o("el-checkbox",{model:{value:e.isRememb,callback:function(t){e.isRememb=t},expression:"isRememb"}},[o("span",{staticStyle:{color:"white"}},[e._v("记住密码")])])],1),e._v(" "),o("el-form-item",[o("el-button",{staticStyle:{width:"100%"},attrs:{loading:e.loading,type:"primary"},nativeOn:{click:function(t){return t.preventDefault(),e.handleLogin(t)}}},[e._v("\n        登录\n      ")])],1)],1)],1)},[],!1,null,"ec980712",null);s.options.__file="index.vue";t.default=s.exports}}]);