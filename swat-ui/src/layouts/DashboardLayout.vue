<template>
  <v-app v-if="!isLoading">
    <v-app-bar app>
      <v-btn icon @click="toggleNavDrawer">
        <v-icon color="icon" title="Menu">mdi-menu</v-icon>
      </v-btn>
      <v-toolbar-title>
        <v-avatar image="@/assets/swat3.png" class="logo"></v-avatar>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon @click="logout" size="large" color="icon" title="Logout">mdi-logout</v-icon>
      </v-btn>

    </v-app-bar>

    <v-navigation-drawer v-model="navDrawer" class="nav-drawer" expand-on-hover rail>
      <v-list nav>
        <v-list-item v-for="item in items" :key="item.title" :to="item.to" :prepend-icon="item.icon" :title="item.title"
          link router exact class="nav-tab" color="secondary">
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
  <v-app v-else>
    <v-overlay />
  </v-app>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app';

const app = useAppStore();
const router = useRouter()
const navDrawer = ref(false);
const items = ref([])
const isLoading = ref(false);

const init = () => {
  const roles = computed(() => app.roles);
  isLoading.value = true;
  console.log(roles)

  items.value.push(
    { title: "Home", icon: "mdi-home", to: "/" },
    { title: "Archive", icon: "mdi-archive-eye", to: "/archive" },
    { title: "Notifications", icon: "mdi-alert", to: "/notifications" },

  );

  if (roles.value.at(0).toString() == "ROLE_ADMIN") {
    items.value.push({ title: 'Admin', icon: 'mdi-account-cog', to: '/admin' });
  }
  isLoading.value = false;
}

function toggleNavDrawer() {
  navDrawer.value = !navDrawer.value;
}

function logout() {
  app.expireUser();
  router.push('/login')
}

onMounted(() => {
  init();
})

</script>

<style scoped>
.logo {
  margin-left: 10px;
}

.nav-drawer {
  max-width: 240px;
}

.nav-tab {
  margin-bottom: 40px;
  text-align: center;
}
</style>