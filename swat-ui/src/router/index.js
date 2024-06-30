import { createRouter, createWebHistory } from "vue-router";
import { setupLayouts } from "virtual:generated-layouts";
import { useAppStore } from "@/stores/app";

const routes = setupLayouts([
  {
    path: "/",
    component: () => import("../layouts/DashboardLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("../pages/HomePage.vue"),
        meta: { requiresAuth: true },
      },
      {
        path: "archive",
        component: () => import("../pages/ArchivePage.vue"),
        meta: { requiresAuth: true },
      },
      {
        path: "notifications",
        component: () => import("../pages/NotificationsPage.vue"),
        meta: { requiresAuth: true },
      },
      {
        path: "admin",
        component: () => import("../pages/AdminPage.vue"),
        meta: { requiresAuth: true },
      }
    ],
  },
  {
    path: "/login",
    component: () => import("../pages/LoginPage.vue"),
    meta: { requiresAuth: false },
  },
]);

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAppStore();
  const isAuthenticated = authStore.isAuthenticated;

  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!isAuthenticated) {
      next({ path: "/login" });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
