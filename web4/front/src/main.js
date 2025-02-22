import { createApp } from "vue";
import App from "./App.vue";
import router from "./router"; // Импортируем маршрутизатор
import { createPinia } from "pinia"; // Импортируем Pinia для управления состоянием

const app = createApp(App);

// Инициализация Pinia
const pinia = createPinia();
app.use(pinia);

// Инициализация маршрутизатора
app.use(router);

// Монтирование приложения
app.mount("#app");