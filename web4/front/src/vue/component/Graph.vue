<template>
  <div id="centerGraphic" class="column">
    <svg
        xmlns="http://www.w3.org/2000/svg"
        width="300"
        height="300"
        id="graph"
        @click="svgClick"
    >
      <!-- Оси координат -->
      <line x1="150" y1="0" x2="150" y2="300" stroke="white" />
      <line x1="300" y1="150" x2="290" y2="155" stroke="white" />
      <line x1="300" y1="150" x2="290" y2="145" stroke="white" />
      <line x1="0" y1="150" x2="300" y2="150" stroke="white" />
      <line x1="150" y1="0" x2="145" y2="10" stroke="white" />
      <line x1="150" y1="0" x2="155" y2="10" stroke="white" />

      <!-- Метки осей -->
      <line x1="210" y1="145" x2="210" y2="155" stroke="white" />
      <line x1="270" y1="145" x2="270" y2="155" stroke="white" />
      <line x1="90" y1="145" x2="90" y2="155" stroke="white" />
      <line x1="30" y1="145" x2="30" y2="155" stroke="white" />
      <line x1="145" y1="270" x2="155" y2="270" stroke="white" />
      <line x1="145" y1="210" x2="155" y2="210" stroke="white" />
      <line x1="145" y1="90" x2="155" y2="90" stroke="white" />
      <line x1="145" y1="30" x2="155" y2="30" stroke="white" />

      <text x="280" y="145" fill="white">x</text>
      <text x="155" y="10" fill="white">y</text>

      <!-- Фигуры для положительного R -->
      <rect v-if="r > 0" x="150" y="150" width="60" height="120" fill="#61dafb" />
      <polygon v-if="r > 0" points="30,150 150,150 150,210" fill="#61dafb" />
      <path
          v-if="r > 0"
          d="M90 150 A 60 60, 0, 0, 1, 150 90 L 150 150 Z"
          fill="#61dafb"
          stroke="none"
      />

      <!-- Фигуры для отрицательного R -->
      <rect v-if="r < 0" x="90" y="30" width="60" height="120" fill="#61dafb" />
      <polygon v-if="r < 0" points="150,30 150,150 210,150" fill="#61dafb" />
      <path
          v-if="r < 0"
          d="M210 150 A 60 60, 0, 0, 1, 150 210 L 150 150 Z"
          fill="#61dafb"
          stroke="none"
      />

      <!-- Точки -->
      <circle
          v-for="(item, index) in items"
          :key="index"
          :cx="convertX(item.x, r)"
          :cy="convertY(item.y, r)"
          r="2"
          :fill="getDotColor(item.x, item.y, r)"
      />
    </svg>
    <p>
      <label id="svgError" class="errorLabel" for="graph"></label>
    </p>
  </div>
</template>

<script>
import { computed } from "vue";
import { useDataStore } from "@/stores/data"; // Импортируем Pinia Store
import { convertX, convertY, getDotColor } from "@/js/dot";
import axios from "axios";

export default {
  emits: ["svg-click"],
  name: "SVGGraph",
  props: {
    items: Array, // Массив точек
    r: String,    // Значение R
    svgClick: Function, // Обработчик клика по SVG
  },
  setup(props) {
    const dataStore = useDataStore();

    // Обработка клика по SVG
    const svgClick = async (event) => {
      if (!props.r) {
        const svgErrorElement = document.getElementById("svgError");
        if (svgErrorElement) {
          svgErrorElement.innerHTML =
              "Задайте значение R, чтобы отправить точку нажатием на график";
        }
        return;
      }

      const svg = document.getElementById("graph");
      const point = svg.createSVGPoint();
      point.x = event.clientX;
      point.y = event.clientY;
      const transformedPoint = point.matrixTransform(svg.getScreenCTM().inverse());

      const data = {
        x: 0,
        y: 0,
        r: props.r,
      };

      // Преобразование координат
      if (transformedPoint.x < 150) {
        data.x = (-1.25 + (transformedPoint.x / 150) * 1.25) * Math.abs(parseInt(props.r));
      } else {
        data.x = ((transformedPoint.x - 150) / 120) * Math.abs(parseInt(props.r));
      }

      if (transformedPoint.y < 150) {
        data.y = (1.25 - transformedPoint.y / 120) * Math.abs(parseInt(props.r));
      } else {
        data.y = (-(transformedPoint.y - 150) / 120) * Math.abs(parseInt(props.r));
      }

      try {
        const response = await axios.post(
            "http://localhost:42900/web4/server/result/add-to-db-click",
            data,
            {withCredentials: true}
        );

        if (response.status !== 201) {
          const formErrorElement = document.getElementById("formError");
          if (formErrorElement) {
            formErrorElement.innerHTML = response.data;
          }
        } else {
          const formErrorElement = document.getElementById("formError");
          if (formErrorElement) {
            formErrorElement.innerHTML = "";
          }

          // Обновляем данные в Pinia Store
          dataStore.updateItems([...dataStore.items, response.data]);
        }
      } catch (error) {
        console.error(error);
      }

    };


    return {
      svgClick,
      convertX,
      convertY,
      getDotColor,
    };
  },
};
</script>

<style scoped>
#centerGraphic {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.errorLabel {
  color: red;
}
</style>