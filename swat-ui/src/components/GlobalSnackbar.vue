<template>
    <v-snackbar
      v-model="snackbar.show"
      :color="snackbar.color"
      :timeout="snackbar.timeout"
      class="snackbar-top-custom"
      location="top">
      <p class="text-center" :style="getTextColor()">{{ snackbar.message }}</p>
    </v-snackbar>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { useAppStore } from '@/stores/app';
  
  const store = useAppStore();
  const snackbar = computed(() => store.snackbar);

  function getTextColor() {
    if(snackbar.value.color.toString() == "primary") {
      return "color: black !important"
    }
    else {
      return "color: white !important"
    }
  }

  </script>

<style scoped>
.snackbar-top-custom {
  top: 70px;  
}
</style>