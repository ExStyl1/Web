<template>
  <div id="leftInputs" class="column">
    <!-- Основной контейнер с окантовкой -->
    <div id="inputWrapper">
      <!-- Форма ввода -->
      <form id="Input" @submit.prevent="trySendData">
        <!-- X -->
        <div style="color: #61dafb">
          <b>x - координата точки по оси ox</b>
          <fieldset>
            <div v-for="(value, index) in xOptions" :key="index">
              <input
                  type="checkbox"
                  :id="'x' + (index + 1)"
                  :value="value"
                  name="x"
                  class="xButton"
                  v-model="selectedX"
              />
              <label :for="'x' + (index + 1)">{{ value }}</label><br />
            </div>
          </fieldset>
          <label id="xError" class="errorLabel">{{ xError }}</label>
        </div>
        <!-- Y -->
        <p style="color: #61dafb">
          <b>y - координата точки по оси oy (от -5 до 3)</b><br />
          <label for="yInput" class="errorLabel">{{ yError }}</label>
          <input
              type="text"
              id="yInput"
              min="-5"
              max="5"
              v-model="y"
          /><br />
        </p>
        <!-- R -->
        <div style="color: #61dafb">
          <b>R - величина R на графике</b>
          <fieldset>
            <div v-for="(value, index) in rOptions" :key="index">
              <input
                  type="checkbox"
                  :id="'r' + (index + 1)"
                  :value="value"
                  name="r"
                  class="rButton"
                  v-model="selectedR"
              />
              <label :for="'r' + (index + 1)">{{ value }}</label><br />
            </div>
          </fieldset>
          <label id="rError" class="errorLabel">{{ rError }}</label>
        </div>
      </form>
      <!-- Кнопки управления -->
      <div id="controls">
        <input type="button" id="submitForm" value="Отправить" @click="trySendData" />
        <form @submit.prevent="tryClear">
          <input type="submit" id="clear" value="Очистить" />
        </form>
        <input type="button" id="navi" value="Выйти" @click="toHomePage" />
      </div>
    </div>
    <!-- Сообщение об ошибке формы -->
    <label id="formError" class="errorLabel">{{ formError }}</label>
  </div>
</template>

<script>
import {computed, ref, watch} from "vue";
import { useDataStore } from "@/stores/data"; // Импортируем Pinia store
import axios from "axios";
import Cookies from "js-cookie";
import { useRouter } from "vue-router";

export default {
  name: "InputsForm",
  setup() {
    const dataStore = useDataStore();
    const router = useRouter();

    // Связываем состояние store с локальными переменными через computed
    const selectedX = ref([]); // Массив для выбранных значений X
    const y = computed({
      get: () => dataStore.y,
      set: (value) => dataStore.updateY(value),
    });
    const selectedR = ref([]); // Массив для выбранных значений R

    watch(
        selectedR,
        (newSelectedR) => {
          if (newSelectedR.length > 0) {
            // Обновляем значение R в dataStore для первого элемента массива
            dataStore.updateR(newSelectedR[0]);
          } else {
            // Если массив пуст, очищаем значение R в dataStore
            dataStore.updateR(null);
          }
        },
        { immediate: true } // Немедленно вызвать callback при монтировании компонента
    );
    // Ошибки
    const xError = ref("");
    const yError = ref("");
    const rError = ref("");
    const formError = ref("");

    // Опции для чекбоксов
    const xOptions = [-4, -3, -2, -1, 0, 1, 2, 3, 4];
    const rOptions = [-4, -3, -2, -1, 0, 1, 2, 3, 4];

    // Метод отправки данных
    const trySendData = async () => {
      let valid = true;

      // Валидация X
      if (selectedX.value.length === 0) {
        valid = false;
        xError.value = "Выберите хотя бы одно значение для x";
      } else {
        xError.value = "";
      }

      // Валидация Y
      if (!String(y.value).match(/^(?:-?[0-2][.,]\d+|-4[.,]\d+|(?:-[1-5]|[0-3])([.,]0+)?)$/)) {
        valid = false;
        yError.value = "y - значение должно быть от -5 до 3";
      } else {
        yError.value = "";
      }

      // Валидация R
      if (selectedR.value.length === 0) {
        valid = false;
        rError.value = "Выберите хотя бы одно значение для R";
      } else {
        rError.value = "";
      }

      if (valid) {
        // Отправляем данные для каждого выбранного X и R
        for (const xValue of selectedX.value) {
          for (const rValue of selectedR.value) {
            const data = {
              x: xValue,
              y: y.value,
              r: rValue,
            };
            console.log("Отправляемые данные:", data); // Логирование данных
            try {
              const response = await axios.post(
                  "http://localhost:42900/web4/server/result/add-to-db",
                  data,
                  { withCredentials: true }
              );
              if (response.status !== 201) {
                formError.value = response.data;
              } else {
                formError.value = "";
              }
            } catch (e) {
              console.error("Ошибка при отправке данных:", e.response?.data || e.message);
              formError.value = "Ошибка при отправке данных. Проверьте введенные значения.";
            }
          }
        }
      }
    };

    // Метод очистки данных
    const tryClear = async () => {
      try {
        const response = await axios.post(
            "http://localhost:42900/web4/server/result/clear",
            {},
            { withCredentials: true }
        );
        if (response.status !== 200) {
          document.getElementById("formError").innerHTML = response.data;
        }
      } catch (e) {
        console.log("Error: " + e);
      }
    };

    // Метод выхода
    const toHomePage = () => {
      Cookies.remove("token");
      router.push("/front/");
    };

    return {
      selectedX,
      y,
      selectedR,
      xError,
      yError,
      rError,
      formError,
      xOptions,
      rOptions,
      trySendData,
      tryClear,
      toHomePage,
    };
  },
};
</script>

<style scoped>
#leftInputs {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* Окантовка */
#inputWrapper {
  border: 2px solid #61dafb; /* Голубая рамка */
  border-radius: 10px; /* Закругленные углы */
  padding: 20px; /* Внутренние отступы */
  background: #444444; /* Темно-серый фон */
  box-sizing: border-box; /* Учитываем отступы в ширине */
}

#Input {
  display: flex;
  flex-direction: column;
  gap: 15px; /* Расстояние между элементами формы */
}

#controls {
  display: flex;
  gap: 10px; /* Расстояние между кнопками */
  justify-content: center; /* Выравнивание по центру */
  margin-top: 15px; /* Отступ сверху */
}

.errorLabel {
  color: red;
}

#submitForm,
#clear,
#navi {
  background: #61dafb;
  border: 2px solid #61dafb;
  border-radius: 5px;
  padding: 10px 20px;
  color: #ffffff;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s ease;
}

#submitForm:hover,
#clear:hover,
#navi:hover {
  background: #33bfff;
}

#navi {
  background: #ffffff;
  border: 2px solid #61dafb;
  color: #333333;
}
</style>