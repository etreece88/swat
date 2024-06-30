<template>
  <v-app v-if="!isLoading">
    <v-main>
      <global-snackbar />
      <SignupDialog v-model="signupDialog"></SignupDialog>
      <router-view />
    </v-main>

    <AppFooter />
  </v-app>
  <v-app v-else>
    <div>
      <div class="loading-overlay">
        <v-app-bar app class="flex text-center">
          <v-toolbar-title class="justify-center align-center">
            <v-avatar image="@/assets/swat3.png" class="logo mr-1"></v-avatar>
            Space Weather Alerts and Tracking
          </v-toolbar-title>
        </v-app-bar>
        <v-progress-circular
          indeterminate
          color="icon"
          size="64"
        ></v-progress-circular>
      </div>
    </div>
  </v-app>
</template>

<script setup>
import { onBeforeMount } from "vue";
import { useAppStore } from "@/stores/app";

const app = useAppStore();


const init = async () => {
  app.loadUserFromSession();

  if (!app.user) {
    console.log("Unable to load user.");
  }
};

onBeforeMount(() => {
  init();
});
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Montserrat&display=swap");

html,
body {
  font-family: "Orbitron", sans-serif;
}

#app {
  font-family: "Orbitron", sans-serif;
}

html {
  overflow: hidden !important;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

html::-webkit-scrollbar {
  width: 0;
  height: 0;
}

.v-data-table {
  border: 0.25px solid #fcab10;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #00171f;
  /* Light overlay */
  z-index: 10;
  /* High z-index to cover other elements */
}
</style>