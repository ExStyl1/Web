import { defineStore } from "pinia";

export const useDataStore = defineStore("data", {
    state: () => ({
        items: [],
        r: "",
        x: "",
        y: ""
    }),
    actions: {
        updateItems(newItems) {
            this.items = newItems;
        },
        updateR(value) {
            this.r = value;
        },
        updateX(value) {
            this.x = value;
        },
        updateY(value) {
            this.y = value;
        }
    }
});