<template>
  <div id="rightTable">
    <table id="results">
      <caption><b>Результаты</b></caption>
      <thead>
      <tr>
        <th scope="col">№</th>
        <th scope="col">Попадание</th>
        <th scope="col">x</th>
        <th scope="col">y</th>
        <th scope="col">R</th>
        <th scope="col">Автор</th>
      </tr>
      </thead>
      <tbody id="resultBody">
      <tr v-for="item in items" :key="item.id">
        <td>{{ item.id }}</td>
        <td>{{ item.hit.toString() }}</td>
        <td>{{ item.x }}</td>
        <td>{{ item.y }}</td>
        <td>{{ item.r }}</td>
        <td>{{ item.author }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    items: Array // Массив результатов
  }
};
</script>

<style scoped>
#rightTable {
  width: 100%;
  overflow-x: auto; /* Добавляем прокрутку для маленьких экранов */
}

table {
  width: 100%;
  border-collapse: collapse;
  background: #ffffff;
  color: #000000;
  border-radius: 10px;
  overflow: hidden;
}

th,
td {
  text-align: center;
  padding: 10px;
  border: 1px solid #000000;
}

caption {
  caption-side: top;
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
}
</style>