import { createRouter, createWebHistory } from "vue-router";
import Home from "@/vue/page/Head.vue"; // Путь к компоненту Home
import Main from "@/vue/page/Main.vue"; // Путь к компоненту Main

const routes = [
    {
        path: "/front",
        name: "Home",
        component: Home
    },
    {
        path: "/main",
        name: "Main",
        component: Main
    }
];

const router = createRouter({
    history: createWebHistory(), // Используем HTML5 History API
    routes
});

export default router;