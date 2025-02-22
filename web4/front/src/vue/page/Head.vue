<template>
  <div id="container" class="App">
    <header id="header">
      Шевелев Антон Викторович P3214 311414
    </header>
    <div class="App-header">
      <div id="clock">
        <div id="date"></div>
        <div id="time"></div>
      </div>
      <LoginForm />
    </div>
  </div>
</template>

<script>
import LoginForm from "../component/LoginForm.vue"; // Импортируем компонент LoginForm
import "../../js/clock"; // Подключаем JavaScript для часов

export default {
  name: "Home",
  components: {
    LoginForm // Регистрируем дочерний компонент
  },
  mounted() {
    // Если нужно выполнить дополнительную логику при монтировании компонента
  }
};
</script>

<style>
/* Общий шрифт для всей страницы */
body {
  font-family: 'Roboto', sans-serif;
}

/* Заголовок */
header {
  color: #ffffff; /* Белый текст */
  text-align: center;
}

/* Форма регистрации */
#formSignIn {
  color: #333333; /* Темно-серый текст */
  background: #f5f5f5; /* Светло-серый фон */
  border-radius: 10px;
  border: solid #cccccc; /* Серая граница */
  padding: 20px;
}

/* Форма входа */
#formLogin {
  color: #333333; /* Темно-серый текст */
  background: #ffffff; /* Белый фон */
  border-radius: 10px;
  border: solid #cccccc; /* Серая граница */
  margin: 5px;
  padding: 20px;
}

/* Поля ввода логина и пароля */
#login, #password {
  border: solid #cccccc; /* Серая граница */
  border-radius: 10px;
  padding: 10px;
  margin: 5px 0;
  width: 100%;
  box-sizing: border-box;
}


/* Часы */
#clock {
  color: #ffffff; /* Белый текст */
  text-align: center;
  margin-top: 20px;
}

/* Контейнер для полей ввода */
#loginInputs {
  vertical-align: top;
}

/* Форма входа */
#loginForm {
  color: #ffffff; /* Белый текст */
  margin: 5px;
}

/* Основной контейнер приложения */
.App {
  background: #333333; /* Темно-серый фон */
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: calc(10px + 2vmin);
}

/* Шапка приложения */
.App-header {
  text-align: center;
  background-color: #333333; /* Темно-серый фон */
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding-top: 2cm;
  font-size: calc(10px + 2vmin);
}

/* Ссылки */
.App-link {
  color: #ffffff; /* Белый текст */
  text-decoration: none;
}
</style>


