<template>
  <div id="loginInputs" @submit.prevent="action">
    <form id="loginForm" method="POST">
      <label>Логин</label><br/>
      <input type="text" id="login" v-model="login"/><br/>
      <label>Пароль</label><br/>
      <input type="password" id="password" v-model="password"/><br/>
      <input name="login" type="submit" id="formLogin" value="Войти" @click="tryLogin"/>
      <input name="sign in" type="submit" id="formSignIn" value="Зарегистрироваться" @click="trySignIn"/>
    </form>
    <p style="color: red">{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const router = useRouter();
    const login = ref('');
    const password = ref('');
    const message = ref('');

    onMounted(() => {
      document.cookie.split(';').forEach(s => {
        if (s.trim().match(/^token=.{36}$/)) {
          router.push("/main");
        }
      });
      document.getElementById('login').value = '';
    });

    const action = async (e) => {
      if (e.target.submitter.name === 'login') {
        return tryLogin(e);
      } else {
        return trySignIn(e);
      }
    };

    const tryLogin = async (e) => {
      e.preventDefault();

      const data = {
        login: login.value,
        password: password.value
      };

      if (login.value === '' || password.value === '') {
        message.value = "Заполните все поля";
        return;
      }

      try {
        const response = await axios.post("http://localhost:42900/web4/server/user/login", data, {withCredentials: true});
        if (response.status === 202) {
          message.value = response.data;
        } else {
          router.push("/main");
        }
      } catch (error) {
        console.log('Error:', error);
      }
    };

    const trySignIn = async (e) => {
      e.preventDefault();

      const data = {
        login: login.value,
        password: password.value
      };

      if (login.value === '' || password.value === '') {
        message.value = "Заполните все поля";
        return;
      }

      try {
        const response = await axios.post("http://localhost:42900/web4/server/user/sign-in", data, {withCredentials: true});
        if (response.status === 202) {
          message.value = response.data;
        } else {
          router.push("/main");
        }
      } catch (error) {
        console.log('Error:', error);
      }
    };

    return {
      login,
      password,
      message,
      action,
      tryLogin,
      trySignIn
    };
  }
};
</script>