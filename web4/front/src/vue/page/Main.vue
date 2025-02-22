<template>
  <div class="container">
    <!-- Левая колонка: График и форма ввода -->
    <div class="left-column">
      <div class="row">
        <SVGGraph :items="items" :r="r" @svg-click="svgClick" />
      </div>
      <div id="wrapper" class="row">
        <InputsForm />
      </div>
    </div>
    <!-- Правая колонка: Таблица -->
    <div class="right-column">
      <div class="row">
        <ResultTable :items="items" />
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted, computed } from "vue";
import { useRouter } from "vue-router";
import { useDataStore } from "@/stores/data.js"; // Импортируем Pinia Store
import axios from "axios";
import InputsForm from "../component/InputsForm.vue";
import ResultTable from "../component/Table.vue";
import SVGGraph from "../component/Graph.vue";

export default {
  name: "Main",
  components: {
    SVGGraph,
    InputsForm,
    ResultTable,
  },
  setup() {
    const router = useRouter();
    const dataStore = useDataStore();

    // Реактивные переменные
    const screenWidth = ref(window.innerWidth);
    const items = computed(() => dataStore.items); // Получаем данные из Pinia Store
    const r = computed(() => dataStore.r);         // Получаем значение R из Pinia Store

    // Обработка изменения размера окна
    const handleResize = () => {
      screenWidth.value = window.innerWidth;
    };

    // Проверка токена
    const checkToken = () => {
      const cookies = document.cookie.split(";").map((s) => s.trim());
      const tokenCookie = cookies.find((cookie) => cookie.startsWith("token="));
      if (!tokenCookie) {
        router.push("/front");
      }
    };

    // Получение данных с сервера
    const fetchData = async () => {
      try {
        const response = await axios.post("http://localhost:42900/web4/server/result/view-all");
        if (response.status === 202) {
          dataStore.updateItems(response.data); // Обновляем данные в Pinia Store
        }
      } catch (error) {
        console.error(error);
      }
    };

    // Интервал для обновления данных
    let interval;
    onMounted(() => {
      checkToken();
      fetchData();
      interval = setInterval(fetchData, 5000);
      window.addEventListener("resize", handleResize);
    });

    onUnmounted(() => {
      clearInterval(interval);
      window.removeEventListener("resize", handleResize);
    });

    return {
      screenWidth,
      items,
      r,
    };
  },
};
</script>

<style>
/* Общие настройки */
body {
  background: #333333; /* Темно-серый фон */
  margin: 0;
  font-family: Arial, sans-serif;
  color: #ffffff; /* Белый текст */
}

/* Контейнер */
.container {
  display: flex; /* Используем Flexbox для расположения колонок */
  gap: 20px; /* Отступ между колонками */
  width: 100%; /* Ширина контейнера */
  margin: 0 auto; /* Центрируем контейнер по горизонтали */
  padding: 20px; /* Внутренние отступы */
  box-sizing: border-box; /* Учитываем отступы в ширине */
}

/* Левая колонка */
.left-column {
  display: flex;
  flex-direction: column; /* Элементы в колонку */
  gap: 20px; /* Отступ между элементами */
  flex: 1; /* Занимает 1 часть ширины */
  min-width: 400px; /* Минимальная ширина для левой колонки */
}

/* Правая колонка */
.right-column {
  display: flex;
  flex-direction: column; /* Элементы в колонку */
  gap: 20px; /* Отступ между элементами */
  flex: 1; /* Занимает 1 часть ширины */
  min-width: 400px; /* Минимальная ширина для правой колонки */
}

/* График */
#centerGraphic {
  background: #444444; /* Светло-серый фон */
  border: 2px solid #61dafb; /* Голубая граница */
  border-radius: 10px;
  padding: 20px;
  height: 300px; /* Высота графика */
  width: 93%; /* Ширина графика */
  display: flex;
  justify-content: center; /* Центрирование по горизонтали */
  align-items: center; /* Центрирование по вертикали */
  color: #61dafb; /* Голубой текст */
}

/* Форма */
#Input {
  border-radius: 10px;
  padding: 20px;
  background: #444444; /* Светло-серый фон */
  color: #61dafb; /* Голубой текст */
  width: 100%;
  box-sizing: border-box;
}

/* Fieldset для радиокнопок */
fieldset {
  border: none; /* Убираем границу */
  display: flex;
  flex-wrap: wrap; /* Перенос на новую строку при необходимости */
  gap: 10px; /* Отступ между кнопками */
  justify-content: center; /* Центрируем кнопки */
  margin-bottom: 15px; /* Отступ снизу */
}

/* Радиокнопки */
input[type="checkbox"] {
  accent-color: #61dafb; /* Голубой акцент */
  margin: 0;
}

/* Метки для радиокнопок */
label {
  font-size: 14px;
  color: #ffffff; /* Белый текст */
  cursor: pointer; /* Курсор указывает, что метку можно нажать */
}

/* Текстовое поле */
#yInput {
  border: 2px solid #cccccc; /* Серая граница */
  border-radius: 5px;
  padding: 5px;
  width: 100%;
  background: #ffffff; /* Белый фон */
  color: #333333; /* Темно-серый текст */
  box-sizing: border-box;
}

/* Кнопка отправки */
#submitForm {
  background: #61dafb; /* Голубой фон */
  border: 2px solid #61dafb; /* Голубая граница */
  border-radius: 5px;
  padding: 10px 20px;
  color: #ffffff; /* Белый текст */
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s ease; /* Плавный переход цвета */
}

#submitForm:hover {
  background: #33bfff; /* Темнее голубой при наведении */
}

/* Таблица */
table {
  width: 100%;
  border-collapse: collapse; /* Убираем двойные границы */
  background: #ffffff; /* Светло-серый фон */
  color: #000000; /* Черный текст */
  border-radius: 10px;
  overflow: hidden; /* Для закругления углов таблицы */
}

th,
td {
  text-align: center;
  padding: 10px;
  border: 1px solid #000000; /* Серая граница */
}

/* Кнопка очистки */
#clear {
  background: #61dafb; /* Голубой фон */
  border: 1px solid #61dafb; /* Голубая граница */
  border-radius: 10px;
  padding: 10px;
  cursor: pointer;
  color: #ffffff; /* Белый текст */
}

/* Навигация */
#navi {
  color: #333333; /* Темно-серый текст */
  background: #ffffff; /* Белый фон */
  border: 2px solid #61dafb; /* Голубая граница */
  border-radius: 10px;
  padding: 10px;
  text-decoration: none; /* Убираем подчеркивание ссылки */
}

/* Сообщения об ошибках */
.errorLabel {
  color: #ff0000; /* Красный текст */
  font-size: 12px;
  margin-top: 5px;
}

/* Заголовки */
b {
  font-size: 16px;
  color: #61dafb; /* Голубой текст */
  margin-bottom: 10px;
  display: block;
}



/* Планшетный режим (ширина >= 778px и < 1237px) */
@media (min-width: 778px) and (max-width: 1236px) {
  .container {
    flex-direction: column; /* Колонки друг под другом */
  }

  .left-column,
  .right-column {
    min-width: auto; /* Убираем фиксированную ширину */
    width: 100%; /* Занимают всю ширину */
  }
}

/* Десктопный режим (ширина >= 1237px) */
@media (min-width: 1237px) {
  .container {
    flex-direction: row; /* Две колонки рядом */
  }

  .left-column,
  .right-column {
    min-width: 400px; /* Минимальная ширина колонок */
  }
}

/* Мобильный режим (ширина < 778px) */
@media (max-width: 777px) {
  .container {
    flex-direction: column; /* Колонки друг под другом */
  }

  .left-column,
  .right-column {
    min-width: auto; /* Убираем фиксированную ширину */
    width: 100%; /* Занимают всю ширину */
  }

  /* Уменьшаем размеры элементов */
  #centerGraphic {
    height: 300px; /* Меньшая высота графика */
  }

  #Input {
    padding: 15px; /* Меньшие отступы */
  }

  table {
    font-size: 14px; /* Меньший размер текста */
  }
}
</style>


